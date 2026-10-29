const {test,expect}=require('@playwright/test');
const {start,press,waitScreen,readState}=require('./helpers/vm.cjs');
const {ready}=require('./helpers/equipment.cjs');
test.use({actionTimeout:10000});
test('Native character information opens actual equipment and skill descriptions',async({page})=>{
  const errors=[];page.on('pageerror',e=>errors.push(e.message));
  const frame=await start(page);await ready(frame);
  await press(page,'Enter');await waitScreen(frame,'df');
  for(let i=0;i<15&&(await readState(frame)).screen==='df';i++){await page.waitForTimeout(400);await press(page,'e');}
  await waitScreen(frame,'com/mic/smsgame/suquan/z');await press(page,'q');
  await frame.locator('#web-player-name').fill('TRANGBI');await frame.getByRole('button',{name:'Xong',exact:true}).click();
  await waitScreen(frame,'com/mic/smsgame/suquan/aa');await ready(frame);
  await frame.evaluate(async()=>{for(const id of [0,3,4]){for(let i=0;i<3;i++)await LoanV2.purchase(id);await LoanV2.equip(id);}});
  // Invoke the actual map Info entry via a scheduled Java context. No synthetic DOM state.
  await frame.evaluate(async()=>LoanVM.invoke('com/mic/smsgame/suquan/aa.b.()V',[LoanV2.current().address]));
  await expect.poll(()=>frame.evaluate(()=>LoanV2.state().dialog)).toBe('ad');
  await frame.locator('.v2-open button').click();
  await expect(frame.locator('.v2-panel h2')).toContainText('TRANG BỊ NHÂN VẬT');
  await frame.locator('[data-equipment-id="4"]').click();
  await expect(frame.locator('.v2-detail')).toContainText('Bí Kíp Quyển Hạ');
  await frame.getByRole('button',{name:'Chiêu thức',exact:true}).click();
  await expect(frame.locator('.v2-skill-desc')).toHaveCount(9);
  const cards=frame.locator('.v2-skill-desc');
  await expect(cards.nth(0)).toContainText('Cần 12 Mana');
  await expect(cards.nth(1)).toContainText('Cần 24 Mana');
  await expect(cards.nth(2)).toContainText('Cần 36 Mana');
  await expect(cards.nth(2)).toContainText('Thêm 1 lượt đi');
  await expect(cards.nth(0)).toContainText('Hoàn 10 Mana');
  await expect(cards.nth(0)).toContainText('5% HP hiện tại');
  await frame.locator('body').screenshot({path:'analysis/verification/ver2/equipment-info-skills.png'});
  await press(page,'e');await expect(frame.locator('.v2-panel')).toBeHidden();
  expect(await frame.evaluate(()=>LoanV2.state().dialog)).toBe('ad');
  expect(errors).toEqual([]);
});
