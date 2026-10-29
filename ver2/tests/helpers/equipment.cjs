const {expect}=require('@playwright/test');
const {start,press,waitScreen}=require('./vm.cjs');
async function equipmentStart(page){
  const logs=[];const listener=msg=>{if(msg.type()==='error'||/error|exception|VER2/i.test(msg.text()))logs.push(msg.text());};page.on('console',listener);
  let frame;
  try{frame=await start(page);}catch(error){
    const game=page.frames().find(f=>f.url().includes('runtime/main.html'));
    const diagnostic=game?await game.evaluate(()=>({state:window.LoanV2?.state?.(),ui:typeof window.LoanEquipmentUI,runtimeReady:!!window.LoanVM?.runtime,body:document.body.className})).catch(e=>({error:e.message})):null;
    console.log('EQUIPMENT_BOOT_FAILURE',JSON.stringify({diagnostic,logs:logs.slice(-30)}));throw error;
  }finally{page.off('console',listener);}
  await expect.poll(()=>frame.evaluate(()=>LoanV2.state().ready),{timeout:20000}).toBe(true);return frame;
}
async function equipmentShop(page){
  const frame=await equipmentStart(page);
  await press(page,'ArrowDown');await press(page,'ArrowDown');await press(page,'Enter');
  await waitScreen(frame,'com/mic/smsgame/suquan/q');
  await frame.getByRole('tab',{name:'[3] Trang Bị',exact:true}).click();return frame;
}
async function buyTiers(frame,tier=3){
  await frame.evaluate(async tier=>{for(let id=0;id<6;id++)for(let level=0;level<tier;level++)await LoanV2.purchase(id);},tier);
}
async function ready(frame){await expect.poll(()=>frame.evaluate(()=>LoanV2.state().ready),{timeout:20000}).toBe(true);}
module.exports={equipmentShop,equipmentStart,buyTiers,ready};
