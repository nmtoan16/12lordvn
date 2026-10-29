// Isolated DOM contract tests. Real JVM shop/combat coverage is separate.
const {test,expect}=require('@playwright/test');
const fs=require('node:fs');
const path=require('node:path');
test('Equipment UI contract: free shortfall, explicit fourth-item replacement and bounded layout',async({page})=>{
  await page.goto('./');
  await page.setContent('<html><body style="position:relative;margin:0;width:240px;height:320px;background:#000"></body></html>');
  await page.addStyleTag({url:new URL('runtime/style/main.css',page.url()).href});
  await page.addStyleTag({url:new URL('runtime/equipment.css',page.url()).href});
  await page.addScriptTag({path:'runtime/equipment/catalog.js'});
  await page.evaluate(()=>{
    const listeners=[];
    window.fixture={screen:'com/mic/smsgame/suquan/q',gold:0,profile:{ownedTiers:[1,1,1,1,0,0],equippedIds:[0,1,2]},purchases:0};
    const update=()=>listeners.forEach(fn=>fn());
    window.LoanV2={state:()=>structuredClone(window.fixture),catalog:window.LoanEquipment.catalog,subscribe:fn=>listeners.push(fn),
      purchase:async id=>{window.fixture.purchases++;window.fixture.profile.ownedTiers[id]++;update();},
      equip:async(id,replace)=>{const ids=window.fixture.profile.equippedIds;if(ids.length>=3){if(replace===undefined)throw Error('Choose a replacement');ids[ids.indexOf(replace)]=id;}else ids.push(id);update();},
      unequip:async id=>{window.fixture.profile.equippedIds=window.fixture.profile.equippedIds.filter(x=>x!==id);update();}};
  });
  await page.addScriptTag({path:'runtime/equipment/info.js'});
  await page.addScriptTag({path:'runtime/equipment-shop.js'});
  await page.getByRole('tab',{name:'[3] Trang Bị'}).click();
  await expect(page.getByRole('button',{name:'Nâng cấp',exact:true})).toBeEnabled();
  await page.getByRole('button',{name:'Nâng cấp',exact:true}).click();
  await expect(page.getByRole('alertdialog')).toContainText('Thực trừ: 0 vàng');
  await page.getByRole('button',{name:'Hủy',exact:true}).click();
  expect(await page.evaluate(()=>fixture.purchases)).toBe(0);
  await page.getByRole('button',{name:'Nâng cấp',exact:true}).click();
  await page.getByRole('button',{name:'Mua',exact:true}).click();
  expect(await page.evaluate(()=>fixture.purchases)).toBe(1);
  expect(await page.evaluate(()=>fixture.profile.ownedTiers[0])).toBe(2);
  await page.locator('[data-equipment-id="3"]').click();
  await page.getByRole('button',{name:'Lắp',exact:true}).click();
  await expect(page.getByRole('alertdialog')).toContainText('Đã mang đủ 3 món');
  await page.getByRole('button',{name:'Hủy',exact:true}).click();
  expect(await page.evaluate(()=>fixture.profile.equippedIds)).toEqual([0,1,2]);
  await page.getByRole('button',{name:'Lắp',exact:true}).click();
  await page.getByRole('button',{name:'Thay Linh Châu',exact:true}).click();
  expect(await page.evaluate(()=>fixture.profile.equippedIds)).toEqual([0,3,2]);
  for(const width of [375,768,1440]){
    await page.setViewportSize({width,height:1000});
    const overflow=await page.locator('.v2-panel').evaluate(p=>({width:p.clientWidth,scrollWidth:p.scrollWidth,height:p.clientHeight,scrollHeight:p.scrollHeight,controls:[...p.querySelectorAll('button')].filter(b=>b.offsetWidth&&getComputedStyle(b).whiteSpace!=='nowrap').map(b=>b.textContent)}));
    expect(overflow.scrollWidth).toBe(overflow.width);expect(overflow.scrollHeight).toBe(overflow.height);expect(overflow.controls).toEqual([]);
  }
  fs.mkdirSync('analysis/verification/ver2',{recursive:true});
  await page.locator('.v2-panel').screenshot({path:path.resolve('analysis/verification/ver2/equipment-ui-fixture.png')});
});
