'use strict';
const {test,expect}=require('@playwright/test');
const {start}=require('./helpers/vm.cjs');
const {ready}=require('./helpers/equipment.cjs');
// Playwright provides a fresh storage context per test: these faults never
// touch the user's installed Chrome profile or an existing personal save.
for(const kind of ['indexeddb-abort','swallowed-rms'])for(const stage of [1,2,3]){
 test(`real RMS ${kind} at purchase flush ${stage} recovers once after reload`,async({page})=>{
  let frame=await start(page);await ready(frame);
  const failed=await frame.evaluate(async({kind,stage})=>{
   await LoanVM.setGold(1000);await LoanVM.flush();
   if(kind==='swallowed-rms')LoanJavaHooks.observe('l.a.(Ljavax/microedition/rms/RecordStore;I[BII)I',{before(c){if(window.fixtureFailPointer&&c.args[1]===3){c.setArg(1,-12345);window.fixtureFailPointer=false;window.fixturePointerFaults++;}}});
   let count=0;
   const port={...LoanVM,async flush(){
    count++;
    if(count!==stage)return LoanVM.flush();
    if(kind==='swallowed-rms'){
     window.fixturePointerFaults=0;window.fixtureFailPointer=true;
     try{return await LoanVM.flush();}finally{window.fixtureFailPointer=false;}
    }
    const original=IDBDatabase.prototype.transaction;
    IDBDatabase.prototype.transaction=function(names,mode,...args){
     const transaction=original.call(this,names,mode,...args);
     if(this.name===LOAN_VERSION.fs&&mode==='readwrite'){
      IDBDatabase.prototype.transaction=original;
      queueMicrotask(()=>transaction.abort());
     }
     return transaction;
    };
    try{return await LoanVM.flush();}finally{IDBDatabase.prototype.transaction=original;}
   }};
   try{await LoanEquipment.store.createStore(port).purchaseNextTier(0,'fixture-durable-purchase');return {failed:false};}
   catch(error){
    let blocked=false;try{await LoanVM.write(122,null);}catch(retry){blocked=retry===error&&retry.requiresReload===true;}
    return {failed:true,code:error.code||error.name,message:error.message,pointerFaults:window.fixturePointerFaults,blocked};
   }
  },{kind,stage});
  expect(failed.failed).toBe(true);
  expect(failed.blocked).toBe(true);
  if(kind==='swallowed-rms'){expect(failed.code).toBe('RMS_DURABILITY');expect(failed.pointerFaults).toBe(1);}
  await page.reload();frame=await start(page);await ready(frame);
  const recovered=await frame.evaluate(async()=>{
   const store=LoanEquipment.store.createStore(LoanVM),before=await store.init(),goldBefore=await store.getGold();
   const once=await store.purchaseNextTier(0,'fixture-durable-purchase'),twice=await store.purchaseNextTier(0,'fixture-durable-purchase');
   return {before:before.ownedTiers[0],goldBefore,tier:once.profile.ownedTiers[0],gold:await store.getGold(),replayed:twice.replayed,pending:await LoanVM.read(122)};
  });
  expect(recovered.tier).toBe(1);expect(recovered.gold).toBe(200);expect(recovered.replayed).toBe(true);expect(recovered.pending).toBe(null);
  if(stage>1){expect(recovered.before).toBe(1);expect(recovered.goldBefore).toBe(200);}
 });
}
