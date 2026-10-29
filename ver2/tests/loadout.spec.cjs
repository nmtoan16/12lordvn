const {test,expect}=require('@playwright/test');
const {press}=require('./helpers/vm.cjs');
const {equipmentShop,equipmentStart,buyTiers,ready}=require('./helpers/equipment.cjs');
const {equipmentBattle}=require('./helpers/equipment-battle.cjs');
test.use({hasTouch:true,actionTimeout:10000});
test('Real loadout uses three slots: touch cancel, keyboard explicit replacement, virtual keys and reload',async({page})=>{
  const frame=await equipmentShop(page);await buyTiers(frame,1);
  for(let id=0;id<3;id++){
    await frame.locator(`[data-equipment-id="${id}"]`).tap();
    await frame.getByRole('button',{name:'Lắp',exact:true}).tap();
    await expect.poll(()=>frame.evaluate(()=>LoanV2.state().profile.equippedIds.length)).toBe(id+1);
  }
  await frame.locator('[data-equipment-id="3"]').tap();await frame.getByRole('button',{name:'Lắp',exact:true}).tap();
  await expect(frame.getByRole('alertdialog')).toContainText('Đã mang đủ 3 món');
  await frame.getByRole('button',{name:'Hủy',exact:true}).tap();
  expect(await frame.evaluate(()=>LoanV2.state().profile.equippedIds)).toEqual([0,1,2]);
  await frame.getByRole('button',{name:'Lắp',exact:true}).tap();
  await press(page,'ArrowDown');await press(page,'q');
  await expect.poll(()=>frame.evaluate(()=>LoanV2.state().profile.equippedIds)).toEqual([0,3,2]);
  await frame.getByRole('button',{name:'Tháo',exact:true}).tap();
  await expect.poll(()=>frame.evaluate(()=>LoanV2.state().profile.equippedIds)).toEqual([0,2]);
  await page.locator('[data-key="-7"]').tap();
  await expect(frame.locator('.v2-panel')).toBeHidden();
  await press(page,'3');await expect(frame.locator('.v2-panel')).toBeVisible();
  await press(page,'1');await expect(frame.locator('.v2-panel')).toBeHidden();
  await page.locator('[data-key="42"]').tap();await expect(frame.locator('.v2-panel')).toBeVisible();
  await page.locator('[data-key="35"]').tap();await expect(frame.locator('.v2-panel')).toBeHidden();
  await page.reload();const restored=await equipmentStart(page);await ready(restored);
  expect(await restored.evaluate(()=>LoanV2.state().profile.equippedIds)).toEqual([0,2]);
});
test('Prematch loadout shows opponent, keeps native preparation and snapshots the chosen three',async({page})=>{
  let inspected=false;
  const frame=await equipmentBattle(page,[0,1,2],async frame=>{
    inspected=true;await frame.evaluate(()=>LoanV2.purchase(3));
    const before=await frame.evaluate(async()=>({screen:LoanV2.current().address,phase:LoanV2.current().mapPhase,inventory:Array.from(await LoanVM.read(9)||[])}));
    await frame.locator('.v2-open button').tap();
    await expect(frame.locator('.v2-panel')).toContainText('Đối thủ:');
    await expect(frame.locator('.v2-panel')).toContainText('HP ');
    await frame.locator('[data-equipment-id="3"]').tap();await frame.getByRole('button',{name:'Lắp',exact:true}).tap();
    await frame.getByRole('button',{name:'Thay Bảo Giáp',exact:true}).tap();
    await expect.poll(()=>frame.evaluate(()=>LoanV2.state().profile.equippedIds)).toEqual([0,1,3]);
    await page.screenshot({path:'analysis/verification/ver2/loadout-prematch.png',fullPage:true});
    await frame.getByRole('button',{name:'Đóng [E]',exact:true}).tap();
    const after=await frame.evaluate(async()=>({screen:LoanV2.current().address,phase:LoanV2.current().mapPhase,inventory:Array.from(await LoanVM.read(9)||[])}));
    expect(after).toEqual(before);
  });
  expect(inspected).toBe(true);
  expect(await frame.evaluate(()=>LoanV2.state().battle.activeTiers)).toEqual([3,3,0,1,0,0]);
});
