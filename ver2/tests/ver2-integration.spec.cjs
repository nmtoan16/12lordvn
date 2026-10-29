'use strict';
const {test,expect}=require('@playwright/test');
const fs=require('node:fs');
const {start,press,waitScreen}=require('./helpers/vm.cjs');
const {equipmentStart,ready}=require('./helpers/equipment.cjs');
const SHOP='com/mic/smsgame/suquan/q';
const MENU='com/mic/smsgame/suquan/ac';
test.use({actionTimeout:15000});

async function openOriginalShop(page,frame){
  await frame.evaluate(()=>LoanVM.invoke('bv.a.(IIZ)V',[LoanV2.current().router,2,4,0]));
  await waitScreen(frame,SHOP);
}
async function inventory(frame){
  return frame.evaluate(async()=>{
    await LoanVM.flush();
    const blob=await new Promise((resolve,reject)=>fs.exportStore((value,error)=>error?reject(error):resolve(value)));
    const files=JSON.parse(await blob.text());
    const bytes=LoanEquipment.saveImport.readLogicalRecords(files[LoanEquipment.saveImport.STORE_PATH]).logical.get(9)||new Uint8Array(24);
    const view=new DataView(bytes.buffer,bytes.byteOffset,bytes.byteLength),rms=Array.from({length:6},(_,index)=>view.getInt32(index*4));
    const H=LoanJavaHooks,address=LoanV2.current().address;
    return {rms,cached:Array.from(J2ME.getArrayFromAddr(H.get(address,'com/mic/smsgame/suquan/q','E','[I'))),gold:LoanV2.state().gold,bytes:Array.from(bytes)};
  });
}

test('original consumables confirm/cancel charge min(gold, price) and persist all 9 balance cases',async({page})=>{
  const errors=[];page.on('pageerror',error=>errors.push(error.message));
  let frame=await equipmentStart(page);await openOriginalShop(page,frame);
  const results=[];
  for(const [itemId,price]of [[3,1000],[4,250],[5,500]])for(const balance of [0,Math.floor(price/2),price]){
    await test.step(`item ${itemId}, price ${price}, gold ${balance}`,async()=>{
      await frame.evaluate(async({itemId,balance})=>{
        await LoanVM.setGold(balance);await LoanVM.flush();
        const H=LoanJavaHooks,address=LoanV2.current().address;
        H.set(address,'com/mic/smsgame/suquan/q','z','I',itemId);
        J2ME.getArrayFromAddr(H.get(address,'com/mic/smsgame/suquan/q','E','[I'))[2]=balance;
        window.integrationShop=address;
      },{itemId,balance});
      const before=await inventory(frame);
      // Enter the JAR's own buy dialog and original NO callback before YES.
      await frame.evaluate(()=>LoanVM.invoke('com/mic/smsgame/suquan/q.b.(II)V',[integrationShop,0,-1]));
      await expect.poll(()=>frame.evaluate(()=>Boolean(LoanV2.current().dialog))).toBe(true);
      await frame.evaluate(()=>LoanVM.invoke('com/mic/smsgame/suquan/q.b.(II)V',[integrationShop,0,-5]));
      expect(await inventory(frame)).toEqual(before);
      await frame.evaluate(()=>LoanVM.invoke('com/mic/smsgame/suquan/q.b.(II)V',[integrationShop,0,-1]));
      await expect.poll(()=>frame.evaluate(()=>Boolean(LoanV2.current().dialog))).toBe(true);
      await frame.evaluate(()=>LoanVM.invoke('com/mic/smsgame/suquan/q.b.(II)V',[integrationShop,0,-4]));
      const after=await inventory(frame),expected=before.rms.slice();expected[2]=Math.max(0,balance-price);expected[itemId]+=3;
      expect(after.rms).toEqual(expected);expect(after.cached).toEqual(expected);expect(after.gold).toBe(expected[2]);
      expect(balance-after.rms[2]).toBe(Math.min(balance,price));
      // Closing the success dialog uses the same original callback as cancel.
      await frame.evaluate(()=>LoanVM.invoke('com/mic/smsgame/suquan/q.b.(II)V',[integrationShop,0,-5]));
      results.push({itemId,price,balance,charged:balance-after.rms[2],inventory:after.rms});
    });
  }
  const saved=await inventory(frame);await page.reload();frame=await start(page);await ready(frame);await openOriginalShop(page,frame);
  const restored=await inventory(frame);expect(restored.rms).toEqual(saved.rms);expect(restored.cached).toEqual(saved.cached);expect(restored.gold).toBe(saved.gold);
  expect(errors).toEqual([]);fs.mkdirSync('analysis/verification/ver2',{recursive:true});fs.writeFileSync('analysis/verification/ver2/original-consumables.json',JSON.stringify(results,null,2));
});

// Change the first chunk of a known logical key through dr's active index;
// changing an arbitrary/orphan magic sequence would not exercise import safety.
function corruptProfile(save,kind){
  const copy=JSON.parse(JSON.stringify(save)),file=copy.files['/RecordStore/161b.0'],bytes=Uint8Array.from(file.data),view=new DataView(bytes.buffer),physical=new Map();
  for(let offset=40;offset<bytes.length;){const id=view.getInt32(offset),length=view.getInt32(offset+4);if(id>0)physical.set(id,{offset:offset+8,length});offset+=8+Math.ceil(length/8)*8;}
  const bank=physical.get(bytes[physical.get(3).offset]),bankView=new DataView(bytes.buffer,bank.offset,bank.length);let descriptor=4,chunk;
  for(let index=0;index<bankView.getInt32(0);index++){
    const key=bankView.getInt32(descriptor),count=bankView.getInt32(descriptor+8);if(key===120){chunk=physical.get(bankView.getInt32(descriptor+12));break;}descriptor+=12+count*4;
  }
  if(!chunk)throw Error('Missing fixture profile');
  if(kind==='checksum')bytes[chunk.offset+12]^=1;else bytes[chunk.offset+4]=2;
  file.data=Array.from(bytes,byte=>byte>127?byte-256:byte);return copy;
}
async function menuIndex(frame){return frame.evaluate(()=>{const H=LoanJavaHooks,menu=H.get(LoanV2.current().address,'com/mic/smsgame/suquan/ac','u','Ldv;');return H.get(menu,'cd','b');});}
async function originalDialogReady(frame,present=true){
  await expect.poll(()=>frame.evaluate(present=>{const current=LoanV2.current(),H=LoanJavaHooks;return Boolean(current.dialog)===present&&!H.get(current.router,'bv','a','Z')&&!H.get(current.router,'bv','l','Z')&&!H.get(current.router,'bv','h','Z');},present),{timeout:10000}).toBe(true);
}

test('corrupt or future equipment import preserves current RMS and resumes the same playable VM',async({page})=>{
  const errors=[];page.on('pageerror',error=>errors.push(error.message));const frame=await equipmentStart(page);
  await frame.evaluate(async()=>{await LoanV2.purchase(0);await LoanV2.equip(0);await LoanVM.setGold(321);await LoanV2.flush();});
  const before=await frame.evaluate(async()=>({profile:LoanV2.state().profile,gold:await LoanVM.readGold(),bytes:Array.from(await LoanVM.read(120)),address:LoanV2.current().address}));
  await page.locator('summary').filter({hasText:'Lưu và chuyển tiến trình'}).click();
  const downloading=page.waitForEvent('download');await page.click('#export');const downloaded=await downloading;
  const stream=await downloaded.createReadStream(),chunks=[];for await(const chunk of stream)chunks.push(chunk);const save=JSON.parse(Buffer.concat(chunks));
  const originalURL=frame.url();
  for(const kind of ['checksum','future-version']){
    page.once('dialog',dialog=>dialog.accept());
    await page.locator('#save-file').setInputFiles({name:`bad-${kind}.json`,mimeType:'application/json',buffer:Buffer.from(JSON.stringify(corruptProfile(save,kind)))});
    await expect(page.locator('#status')).toContainText('Không nhập được bản lưu:');await expect(page.locator('#pause-screen')).toBeHidden();
    await expect.poll(()=>frame.evaluate(()=>LoanV2.state().paused)).toBe(false);
    expect(frame.url()).toBe(originalURL);
    const after=await frame.evaluate(async()=>({profile:LoanV2.state().profile,gold:await LoanVM.readGold(),bytes:Array.from(await LoanVM.read(120)),address:LoanV2.current().address}));expect(after).toEqual(before);
    const index=await menuIndex(frame);await press(page,'ArrowDown');await expect.poll(()=>menuIndex(frame)).not.toBe(index);
  }
  // A reload proves the rejected import did not replace committed IndexedDB.
  await page.reload();const restored=await start(page);await ready(restored);
  expect(await restored.evaluate(()=>LoanV2.state().profile)).toEqual(before.profile);expect(await restored.evaluate(()=>LoanVM.readGold())).toBe(before.gold);expect(errors).toEqual([]);
});

test('original New Game NO retains equipment and YES clears profile, sidecar and original checkpoint',async({page})=>{
  const errors=[];page.on('pageerror',error=>errors.push(error.message));let frame=await equipmentStart(page);
  const before=await frame.evaluate(async()=>{
    await LoanV2.purchase(0);await LoanV2.equip(0);await LoanV2.purchase(2);await LoanV2.equip(2);await LoanVM.setGold(777);
    // Only setup is synthetic: an existing original checkpoint flag and a
    // sidecar. Selection, both dialog answers and reset run through the JAR.
    await LoanVM.invoke('com/mic/smsgame/suquan/f.a.([B)V',[()=>{const addr=J2ME.newByteArray(4);J2ME.getArrayFromAddr(addr).set([78,69,87,49]);return addr;}]);
    const snapshot=new Uint8Array([106,1,2,3]);await LoanVM.write(106,snapshot);
    await LoanEquipment.battleSave.createBattleSave(LoanVM).save(snapshot,{battleId:'new-game-fixture',profileId:LoanV2.profile().profileId,loadout:LoanEquipment.loadout.snapshot(LoanV2.profile().ownedTiers,LoanV2.profile().equippedIds),state:{barrierHp:0,emergencyUsed:false}});
    await LoanV2.flush();
    const result={profile:LoanV2.state().profile,gold:await LoanVM.readGold(),profileBytes:Array.from(await LoanVM.read(120)),sidecar:Array.from(await LoanVM.read(121)),snapshot:Array.from(await LoanVM.read(106))};
    await LoanVM.invoke('bv.a.(IIZ)V',[LoanV2.current().router,2,2,0]);return result;
  });
  await waitScreen(frame,MENU);await press(page,'ArrowDown');await press(page,'Enter');
  await originalDialogReady(frame);await press(page,'e');
  await originalDialogReady(frame,false);await waitScreen(frame,MENU);
  const cancelled=await frame.evaluate(async()=>({profile:LoanV2.state().profile,gold:await LoanVM.readGold(),profileBytes:Array.from(await LoanVM.read(120)),sidecar:Array.from(await LoanVM.read(121)),snapshot:Array.from(await LoanVM.read(106))}));expect(cancelled).toEqual(before);
  await press(page,'Enter');await originalDialogReady(frame);await press(page,'q');await waitScreen(frame,'df');
  await expect.poll(()=>frame.evaluate(oldId=>{const state=LoanV2.state();return state.ready&&state.profile.profileId!==oldId&&state.profile.ownedTiers.every(tier=>tier===0);},before.profile.profileId)).toBe(true);
  const after=await frame.evaluate(async()=>{
    await LoanV2.flush();const marker=await LoanVM.invoke('com/mic/smsgame/suquan/f.c.()Z');
    return {profile:LoanV2.state().profile,gold:await LoanVM.readGold(),sidecar:await LoanVM.read(121),snapshot:await LoanVM.read(106),journal:await LoanVM.read(122),originalCheckpoint:marker};
  });
  expect(after.profile.profileId).not.toBe(before.profile.profileId);expect(after.profile.ownedTiers).toEqual([0,0,0,0,0,0]);expect(after.profile.equippedIds).toEqual([]);expect(after.profile.completedTransactions).toEqual([]);expect(after.gold).toBe(0);expect(after.sidecar).toBe(null);expect(after.snapshot).toBe(null);expect(after.journal).toBe(null);expect(after.originalCheckpoint).toBe(0);
  await page.reload();frame=await start(page);await ready(frame);expect(await frame.evaluate(()=>LoanV2.state().profile)).toEqual(after.profile);expect(errors).toEqual([]);
});
