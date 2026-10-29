'use strict';
const {test,expect}=require('@playwright/test');
const {start,readState}=require('./helpers/vm.cjs');
const {ready}=require('./helpers/equipment.cjs');
test('purchase durability failure freezes native controls and offers reload recovery',async({page})=>{
 let frame=await start(page);await ready(frame);
 const result=await frame.evaluate(async()=>{
  const original=IDBDatabase.prototype.transaction;
  IDBDatabase.prototype.transaction=function(names,mode,...args){const txn=original.call(this,names,mode,...args);if(this.name===LOAN_VERSION.fs&&mode==='readwrite'){IDBDatabase.prototype.transaction=original;queueMicrotask(()=>txn.abort());}return txn;};
  try{await LoanV2.purchase(0);return false;}catch(error){return error.requiresReload===true;}finally{IDBDatabase.prototype.transaction=original;}
 });
 expect(result).toBe(true);await expect(page.locator('#resume')).toHaveText('Tải lại để phục hồi');
 await expect(page.locator('#export')).toBeDisabled();await expect(page.locator('#import')).toBeDisabled();
 expect(await frame.evaluate(()=>LoanV2.state().ready)).toBe(false);expect(await frame.evaluate(()=>loanPaused)).toBe(true);
 const before=await readState(frame);await frame.evaluate(()=>window.postMessage({app:'loan12',type:'pause',paused:false},location.origin));
 await page.keyboard.press('ArrowDown');expect((await readState(frame)).screenAddress).toBe(before.screenAddress);expect(await frame.evaluate(()=>loanPaused)).toBe(true);
 await page.locator('#resume').click();await expect(page.locator('#start')).toBeEnabled();
 frame=await start(page);await ready(frame);expect(await frame.evaluate(()=>LoanV2.state().ready)).toBe(true);
});
