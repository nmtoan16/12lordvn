const {test,expect}=require('@playwright/test');
const fs=require('node:fs');
const {equipmentBattle,captureBattle}=require('./helpers/equipment-battle.cjs');
test.use({actionTimeout:10000});
async function pauseState(page,frame,paused){
  await page.click(paused?'#pause':'#resume');
  // The host sends pause via postMessage. Wait for the VM acknowledgment before
  // taking the frozen reference; a click alone does not finish cross-frame work.
  await expect.poll(()=>frame.evaluate(()=>window.loanPaused)).toBe(paused);
}
test('Awakened gold VFX stay bounded, freeze on pause, and expire after resume',async({page})=>{
  const frame=await equipmentBattle(page,[2,4,5]);
  await pauseState(page,frame,true);
  // Renderer fixture events use the actual frozen loadout and live game canvas.
  await frame.evaluate(()=>{for(let i=0;i<500;i++){LoanV2.emitCombat({type:'damage',amount:i+1,side:i%2});LoanV2.emitCombat({type:'cast',skillId:i%9,side:0});}});
  const capped=await frame.evaluate(()=>LoanVFX.stats());expect(capped.text).toBe(12);expect(capped.particles).toBe(24);
  await pauseState(page,frame,false);await page.waitForTimeout(80);await pauseState(page,frame,true);
  const frozen=await frame.locator('.v2-vfx').evaluate(c=>c.toDataURL());
  await captureBattle(page,frame,'analysis/verification/ver2/vfx-golden-stress.png');
  await page.waitForTimeout(500);expect(await frame.locator('.v2-vfx').evaluate(c=>c.toDataURL())).toBe(frozen);
  await pauseState(page,frame,false);await page.waitForTimeout(1200);
  const expired=await frame.evaluate(()=>LoanVFX.stats());expect(expired.text).toBe(0);expect(expired.particles).toBe(0);
  expect(expired.samples.length).toBeGreaterThan(0);expect(expired.p95).toBeLessThan(8);
  fs.writeFileSync('analysis/verification/ver2/vfx-performance.json',JSON.stringify({fixture:'500 renderer events in a real tutorial battle; excludes original VM frame cost',capped,expired},null,2));
});
