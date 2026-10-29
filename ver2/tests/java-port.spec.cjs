const {test,expect}=require('@playwright/test');
const {start}=require('./helpers/vm.cjs');
test('Java continuation port roundtrips logical RMS and preserves original keys',async({page})=>{
 const frame=await start(page);
 const result=await frame.evaluate(async()=>{
  const before=await LoanVM.read(9),empty=await LoanVM.read(124),gold=await LoanVM.readGold();
  await LoanVM.write(124,new Uint8Array([71,69,65,82,1,2,3]));await LoanVM.flush();
  const back=await LoanVM.read(124),after=await LoanVM.read(9);
  await LoanVM.write(124,null);await LoanVM.flush();
  return {before:before&&Array.from(before),after:after&&Array.from(after),empty,gold,back:Array.from(back),deleted:await LoanVM.read(124),contexts:LoanVM.runtime.contexts?.length};
 });
 expect(result.empty).toBe(null);expect(result.back).toEqual([71,69,65,82,1,2,3]);expect(result.after).toEqual(result.before);expect(result.deleted).toBe(null);expect(result.gold).toBeGreaterThanOrEqual(0);
});
