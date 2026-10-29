const {expect}=require('@playwright/test');
const {start,press,waitScreen,readState}=require('./vm.cjs');
const {ready}=require('./equipment.cjs');
async function equipmentBattle(page,ids=[1,2,5],beforeAttack){
  const frame=await start(page);await ready(frame);
  await press(page,'Enter');await waitScreen(frame,'df');
  for(let i=0;i<15&&(await readState(frame)).screen==='df';i++){await page.waitForTimeout(400);await press(page,'e');}
  await waitScreen(frame,'com/mic/smsgame/suquan/z');await press(page,'q');
  await frame.locator('#web-player-name').fill('HUYEN');await frame.getByRole('button',{name:'Xong',exact:true}).click();
  await waitScreen(frame,'com/mic/smsgame/suquan/aa');await ready(frame);
  await frame.evaluate(async ids=>{for(const id of ids){for(let i=0;i<3;i++)await LoanV2.purchase(id);await LoanV2.equip(id);}},ids);
  let prepared=false;
  for(let i=0;i<35&&(await readState(frame)).screen!=='com/mic/smsgame/suquan/ab';i++){
    const s=await readState(frame);if(s.mapPhase===2)await press(page,'e');else if(s.mapPhase===3){if(beforeAttack&&!prepared){prepared=true;await beforeAttack(frame);}await press(page,'q');}await page.waitForTimeout(350);
  }
  await waitScreen(frame,'com/mic/smsgame/suquan/ab');
  await expect.poll(()=>frame.evaluate(()=>Boolean(LoanV2.state().battle)),{timeout:10000}).toBe(true);
  await frame.evaluate(()=>{
    const h=LoanJavaHooks,s=LoanV2.current(),controller=h.get(s.address,'com/mic/smsgame/suquan/ab','e','Lbu;'),model=h.get(controller,'bu','s','Lcq;');
    window.equipmentBattleFixture={controller,model,fighters:Array.from(J2ME.getArrayFromAddr(h.get(model,'cq','f','[Lbn;'))),events:[]};
    LoanV2.onCombat(event=>equipmentBattleFixture.events.push(event));
  });
  return frame;
}
async function hit(frame,amount){await frame.evaluate(amount=>LoanVM.invoke('bu.a.(Lbn;I)V',[equipmentBattleFixture.controller,equipmentBattleFixture.fighters[0],amount]),amount);}
async function heal(frame,amount){await frame.evaluate(amount=>LoanVM.invoke('bu.b.(Lbn;I)V',[equipmentBattleFixture.controller,equipmentBattleFixture.fighters[0],amount]),amount);}
async function captureBattle(page,frame,path){
  // Keep the VM frozen but remove the host's pause scrim for inspecting HUD pixels.
  const previous=await page.locator('#pause-screen').evaluate(node=>{const value=node.style.visibility;node.style.visibility='hidden';return value;});
  try{await frame.locator('body').screenshot({path});}finally{await page.locator('#pause-screen').evaluate((node,value)=>node.style.visibility=value,previous);}
}
async function renderOneFrame(page){await page.click('#resume');await page.waitForTimeout(90);await page.click('#pause');}
module.exports={equipmentBattle,hit,heal,captureBattle,renderOneFrame};
