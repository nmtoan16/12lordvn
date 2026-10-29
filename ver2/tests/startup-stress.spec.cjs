'use strict';
const {test,expect}=require('@playwright/test');
const fs=require('node:fs');
const path=require('node:path');

for(const rate of [1,4])test(`ten cold/warm boots at CPU ${rate}x remain ready without runtime errors`,async({page})=>{
  test.setTimeout(240000);
  const cdp=await page.context().newCDPSession(page);await cdp.send('Emulation.setCPUThrottlingRate',{rate});
  let errors=[],requests=new Set();const boots=[];
  page.on('pageerror',error=>errors.push(error.stack));
  page.on('console',message=>{if(message.type()==='error')errors.push(message.text());});
  page.on('request',request=>requests.add(request.url()));
  page.on('requestfinished',request=>requests.delete(request.url()));page.on('requestfailed',request=>requests.delete(request.url()));
  try{
    for(let boot=0;boot<10;boot++){
      errors=[];requests.clear();const began=Date.now();
      await page.goto('./');await page.click('#start');
      let frame;
      await expect.poll(()=>{frame=page.frames().find(frame=>frame.url().includes('runtime/main.html'));return Boolean(frame);},{timeout:10000}).toBe(true);
      let state=null;
      try{
        await expect.poll(async()=>{state=await frame.evaluate(()=>window.LoanV2?{screen:LoanV2.state().screen,ready:LoanV2.state().ready,error:LoanV2.state().error,transition:window.loanStartup?.transitions}:{});return state.screen==='v'&&state.ready;},{timeout:30000,intervals:[100,200,500]}).toBe(true);
      }catch(error){
        const diagnostics=await frame.evaluate(()=>({state:window.LoanV2?.state(),loaded:window.isLoadJarFinished,jarCount:window.JARStore?.getjars().length,readyState:document.readyState,loadingPromises:window.loadingPromises?.length,runtimeCount:window.J2ME?.RuntimeTemplate?.all?.size})).catch(error=>({error:error.message}));
        boots.push({boot,elapsed:Date.now()-began,errors,requests:[...requests],diagnostics});throw error;
      }
      boots.push({boot,cold:boot===0,elapsed:Date.now()-began,state,errors:[...errors]});
      expect(errors).toEqual([]);expect(state.transition).toBe(1);
    }
  }finally{
    const folder=path.resolve('analysis/verification/ver2');fs.mkdirSync(folder,{recursive:true});
    fs.writeFileSync(path.join(folder,`startup-stress-cpu${rate}.json`),JSON.stringify(boots,null,2));
    await cdp.send('Emulation.setCPUThrottlingRate',{rate:1});
  }
});
