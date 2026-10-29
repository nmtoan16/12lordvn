const {test,expect}=require('@playwright/test');
const fs=require('node:fs');
const {equipmentShop,equipmentStart,buyTiers,ready}=require('./helpers/equipment.cjs');
test.use({actionTimeout:10000});
test('All 18 upgrades persist through real RMS; MAX and all six card layouts',async({page})=>{
  const errors=[];page.on('pageerror',e=>errors.push(e.message));
  const frame=await equipmentShop(page);
  expect(await frame.evaluate(()=>LoanV2.catalog.map(c=>c.tiers.slice(1).map(t=>t.price)))).toEqual([[800,2200,5000],[700,2000,4500],[900,2500,5500],[800,2200,4800],[1200,3000,6000],[600,1800,4000]]);
  await buyTiers(frame);
  expect(await frame.evaluate(()=>LoanV2.state().profile.ownedTiers)).toEqual([3,3,3,3,3,3]);
  expect(await frame.evaluate(()=>LoanV2.state().gold)).toBe(0);
  expect(await frame.evaluate(()=>LoanV2.state().profile.equippedIds)).toEqual([]);
  for(let id=0;id<6;id++){
    await frame.locator(`[data-equipment-id="${id}"]`).click();
    await expect(frame.getByRole('button',{name:'MAX',exact:true})).toBeDisabled();
    await expect(frame.locator('.v2-detail h3').first()).toContainText((await frame.evaluate(id=>LoanV2.catalog[id].tiers[3].name,id)));
  }
  fs.mkdirSync('analysis/verification/ver2',{recursive:true});
  for(const width of [375,768,1440]){
    await page.setViewportSize({width,height:1000});
    expect(await page.evaluate(()=>document.documentElement.scrollWidth)).toBe(width);
    const metrics=await frame.locator('.v2-panel').evaluate(p=>({w:p.clientWidth,sw:p.scrollWidth,h:p.clientHeight,sh:p.scrollHeight,wrap:[...p.querySelectorAll('button')].filter(b=>b.offsetWidth&&getComputedStyle(b).whiteSpace!=='nowrap').length}));
    expect(metrics.sw).toBe(metrics.w);expect(metrics.sh).toBe(metrics.h);expect(metrics.wrap).toBe(0);
    await page.screenshot({path:`analysis/verification/ver2/equipment-shop-${width}.png`,fullPage:true});
  }
  await page.reload();const restored=await equipmentStart(page);await ready(restored);
  expect(await restored.evaluate(()=>LoanV2.state().profile.ownedTiers)).toEqual([3,3,3,3,3,3]);
  expect(await restored.evaluate(()=>LoanV2.state().gold)).toBe(0);
  expect(errors).toEqual([]);
});
