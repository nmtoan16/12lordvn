const {test,expect}=require('@playwright/test');
const fs=require('node:fs'),path=require('node:path');
const {enterBattle,snapshot}=require('./helpers/battle.cjs');
const {waitScreen,press}=require('./helpers/vm.cjs');
test.use({actionTimeout:15000});
test('save and Continue in the same VM preserves the latest frozen loadout and barrier',async({page})=>{
 const errors=[];page.on('pageerror',e=>errors.push(e.message));
 const frame=await enterBattle(page,async frame=>{
   await frame.evaluate(async()=>{for(const id of [2,3,5]){await LoanV2.purchase(id);await LoanV2.equip(id);}});
 });
 await frame.evaluate(async()=>{
   const H=LoanJavaHooks,c=H.get(LoanV2.current().address,'com/mic/smsgame/suquan/ab','e','Lbu;'),m=H.get(c,'bu','s','Lcq;'),fighters=J2ME.getArrayFromAddr(H.get(m,'cq','f','[Lbn;'));
   await LoanVM.invoke('bu.a.(Lbn;I)V',[c,fighters[0],10]);
   LoanJavaHooks.observe('bu.a.([BI)V',{after(call){if(call.args[1])H.set(call.args[0],'bu','v','I',100);}});
 });
 const before=await snapshot(frame),url=frame.url();
 await frame.evaluate(async()=>{
   const H=LoanJavaHooks,c=H.get(LoanV2.current().address,'com/mic/smsgame/suquan/ab','e','Lbu;');
   H.set(c,'bu','v','I',8);
   // The original save-and-exit command creates the snapshot and opens the menu.
   await LoanVM.invoke('bu.b.(II)V',[c,0,-1]);
 });
 await waitScreen(frame,'com/mic/smsgame/suquan/ac');
 await press(page,'Enter');await waitScreen(frame,'com/mic/smsgame/suquan/ab');
 const after=await snapshot(frame);
 expect(frame.url()).toBe(url);expect(after.id).toBe(before.id);
 expect(after.activeTiers).toEqual(before.activeTiers);expect(after.barrier).toBe(before.barrier);
 expect(after.emergencyUsed).toBe(before.emergencyUsed);expect(after.foodCarry).toBe(before.foodCarry);
 expect(after.fighters.map(f=>[f.hp,f.mana,f.food,f.maxFood])).toEqual(before.fighters.map(f=>[f.hp,f.mana,f.food,f.maxFood]));
 await frame.evaluate(()=>LoanV2.flush());
 expect(errors).toEqual([]);
});
test('original battle snapshot plus sidecar export/import freezes tiers and never reapplies start bonuses',async({page})=>{
 const errors=[];page.on('pageerror',e=>errors.push(e.message));
 const frame=await enterBattle(page,async frame=>{
   await frame.evaluate(async()=>{for(const id of [2,3,5]){await LoanV2.purchase(id);await LoanV2.equip(id);}});
 });
 await frame.evaluate(async()=>{
   const H=LoanJavaHooks,s=LoanV2.current(),controller=H.get(s.address,'com/mic/smsgame/suquan/ab','e','Lbu;'),model=H.get(controller,'bu','s','Lcq;'),fighters=J2ME.getArrayFromAddr(H.get(model,'cq','f','[Lbn;'));
   await LoanVM.invoke('bu.a.(Lbn;I)V',[controller,fighters[0],10]);
   H.set(controller,'bu','v','I',8);
   await LoanVM.invoke('bu.u.()V',[controller],()=>H.set(controller,'bu','v','I',100));
   await LoanV2.flush();
   // The profile can upgrade while this existing battle keeps its captured tier.
   await LoanV2.purchase(2);
 });
 const before=await snapshot(frame);
 expect(before.activeTiers).toEqual([0,0,1,1,0,1]);
 expect(await frame.evaluate(()=>LoanV2.state().profile.ownedTiers[2])).toBe(2);
 await page.locator('summary').filter({hasText:'Lưu và chuyển tiến trình'}).click();
 const downloading=page.waitForEvent('download');await page.click('#export');const file=path.resolve('analysis/verification/ver2/resume-save.json');await(await downloading).saveAs(file);
 const save=JSON.parse(fs.readFileSync(file));expect(save.variant).toBe('ver2');
 const parsed=require('../runtime/equipment/import.js').validateSaveFiles(save.files);expect(parsed.battleSnapshotMatches).toBe(true);expect(parsed.sidecar.loadout.activeTiers).toEqual([0,0,1,1,0,1]);
 page.once('dialog',d=>d.accept());await page.locator('#save-file').setInputFiles(file);await expect.poll(()=>frame.url()).toContain('restore=');
 await waitScreen(frame,'v');await expect.poll(()=>frame.evaluate(()=>LoanV2.state().ready)).toBe(true);await press(page,'Enter');await waitScreen(frame,'com/mic/smsgame/suquan/ac');
 await frame.evaluate(async()=>{
   LoanJavaHooks.observe('bu.a.([BI)V',{after(c){if(c.args[1])LoanJavaHooks.set(c.args[0],'bu','v','I',100);}});
   await LoanVM.invoke('bv.a.(IIZ)V',[LoanV2.current().router,0,5,0]);
 });
 await waitScreen(frame,'com/mic/smsgame/suquan/ab');const after=await snapshot(frame);
 expect(after.activeTiers).toEqual(before.activeTiers);expect(after.id).toBe(before.id);expect(after.barrier).toBe(before.barrier);expect(after.emergencyUsed).toBe(before.emergencyUsed);expect(after.foodCarry).toBe(before.foodCarry);
 expect(after.fighters.map(f=>[f.hp,f.mana,f.food,f.maxFood])).toEqual(before.fighters.map(f=>[f.hp,f.mana,f.food,f.maxFood]));
 await frame.locator('body').screenshot({path:'analysis/verification/ver2/resumed-battle.png'});expect(errors).toEqual([]);
});
