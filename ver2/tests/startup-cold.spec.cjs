'use strict';
const {test,expect}=require('@playwright/test');
const fs=require('node:fs');
const path=require('node:path');
const {start}=require('./helpers/vm.cjs');

for(const rate of [1,4])test(`ten independent cold browser contexts at CPU ${rate}x boot once`,async({browser},info)=>{
  test.setTimeout(300000);const boots=[];
  try{
    for(let boot=0;boot<10;boot++){
      const context=await browser.newContext({baseURL:info.project.use.baseURL,viewport:{width:1440,height:1000}});
      const page=await context.newPage(),cdp=await context.newCDPSession(page);await cdp.send('Emulation.setCPUThrottlingRate',{rate});
      const began=Date.now();
      try{
        const frame=await start(page);const state=await frame.evaluate(()=>({ready:LoanV2.state().ready,screen:LoanV2.state().screen,startup:window.loanStartup}));
        boots.push({boot,elapsed:Date.now()-began,state});expect(state.screen).toBe('com/mic/smsgame/suquan/ac');expect(state.startup.transitions).toBe(1);
      }catch(error){boots.push({boot,elapsed:Date.now()-began,error:error.message});throw error;}
      finally{await context.close();}
    }
  }finally{const folder=path.resolve('analysis/verification/ver2');fs.mkdirSync(folder,{recursive:true});fs.writeFileSync(path.join(folder,`startup-cold-cpu${rate}.json`),JSON.stringify(boots,null,2));}
});

test('state polling does not load game classes before a delayed builtin JAR initializes',async({page})=>{
  const errors=[];page.on('pageerror',error=>errors.push(error.message));page.on('console',message=>{if(message.type()==='error')errors.push(message.text());});
  await page.route('**/runtime/java/classes.jar',async route=>{await new Promise(resolve=>setTimeout(resolve,1500));await route.continue();});
  const frame=await start(page);
  expect(await frame.evaluate(()=>LoanV2.state().ready)).toBe(true);expect(errors).toEqual([]);
});
