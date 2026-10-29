'use strict';
const {test,expect}=require('@playwright/test');
const {start,press,waitScreen}=require('./helpers/vm.cjs');
const legacy=require('./fixtures/v1-map-save.json');
const V1='loan12suquan-fs-v1',V2='loan12suquan-fs-ver2';
async function seed(page,name,files=legacy){
  await page.evaluate(({name,files})=>new Promise((resolve,reject)=>{
    const request=indexedDB.open(name,4);
    request.onupgradeneeded=()=>request.result.createObjectStore('fs4',{keyPath:'pathname'}).createIndex('parentDir','parentDir',{unique:false});
    request.onerror=()=>reject(request.error);
    request.onsuccess=()=>{const db=request.result,tx=db.transaction('fs4','readwrite'),store=tx.objectStore('fs4');
      for(const [pathname,value] of Object.entries(files))store.put({...value,pathname,...(!value.isDir?{data:new Blob([new Int8Array(value.data)])}:{})});
      tx.oncomplete=()=>{db.close();resolve();};tx.onabort=()=>{db.close();reject(tx.error);};
    };
  }),{name,files});
}
async function read(page,name){return page.evaluate(async name=>{
  const db=await new Promise((resolve,reject)=>{const r=indexedDB.open(name);r.onsuccess=()=>resolve(r.result);r.onerror=()=>reject(r.error);});
  const records=await new Promise((resolve,reject)=>{const tx=db.transaction('fs4','readonly'),r=tx.objectStore('fs4').getAll();r.onsuccess=()=>resolve(r.result);r.onerror=()=>reject(r.error);});db.close();
  return Object.fromEntries(await Promise.all(records.filter(r=>!r.isDir).map(async r=>[r.pathname,{...r,data:Array.from(new Int8Array(await r.data.arrayBuffer()))}])));
},name);}
test('actual v1 Continue transfers to ver2, saves equipment and resumes after reload without changing v1',async({page,context})=>{
  const errors=[];page.on('pageerror',error=>errors.push(error.message));
  await page.goto('./');await seed(page,V1);
  const oldPage=await context.newPage(),old=await start(oldPage,'http://localhost/12suquan/');
  await press(oldPage,'Enter');await waitScreen(old,'com/mic/smsgame/suquan/aa');
  await oldPage.click('#pause');await expect(oldPage.locator('#pause-screen')).toBeVisible();
  await old.evaluate(()=>new Promise(resolve=>fs.syncStore(resolve)));await oldPage.close();
  const before=await read(page,V1);
  const frame=await start(page);
  await expect(page.locator('#status')).toContainText('Đã lấy bản lưu ver1');
  expect(await frame.evaluate(()=>LoanV2.state().profile.ownedTiers)).toEqual([0,0,0,0,0,0]);
  const sourceMap=require('../runtime/equipment/import.js').readLogicalRecords(before['/RecordStore/16b.0'],16).logical;
  // Compare the actual stored name/map/EXP/avatar rather than assuming menu text.
  const current=await read(page,V2),currentMap=require('../runtime/equipment/import.js').readLogicalRecords(current['/RecordStore/16b.0'],16).logical;
  for(const key of [100,103,104,105])expect(currentMap.get(key)).toEqual(sourceMap.get(key));
  await press(page,'Enter');await waitScreen(frame,'com/mic/smsgame/suquan/aa');
  await frame.evaluate(async()=>{await LoanV2.purchase(0);await LoanV2.equip(0);await LoanV2.flush();});
  await page.click('#pause');await expect.poll(()=>frame.evaluate(()=>window.loanPaused)).toBe(true);
  await page.screenshot({path:'analysis/verification/ver2/v1-migration-continued.png',fullPage:true});
  const reopened=await start(page);expect(await reopened.evaluate(()=>LoanV2.state().profile.ownedTiers[0])).toBe(1);
  expect(await reopened.evaluate(()=>LoanV2.state().profile.equippedIds)).toEqual([0]);
  await press(page,'Enter');await waitScreen(reopened,'com/mic/smsgame/suquan/aa');
  expect(await read(page,V1)).toEqual(before);expect(errors).toEqual([]);
});
test('existing ver2 is retained; explicit v1 button cancel keeps it and confirm replaces it',async({page})=>{
  await page.goto('./');await seed(page,V1);await seed(page,V2,{'/RecordStore/existing':{isDir:false,size:3,data:[1,2,3],mtime:1,parentDir:'/RecordStore'}});
  const before=await read(page,V2);expect(await page.evaluate(()=>LoanV1Migration.transfer())).toBe('existing');expect(await read(page,V2)).toEqual(before);
  page.once('dialog',d=>d.dismiss());await page.click('#migrate-v1');expect(await read(page,V2)).toEqual(before);
  page.once('dialog',d=>d.accept());await page.click('#migrate-v1');await expect(page.locator('#status')).toContainText('Đã lấy bản lưu ver1');
  const after=await read(page,V2);expect(after['/RecordStore/existing']).toBeUndefined();expect(after['/RecordStore/16b.0'].data).toEqual(legacy['/RecordStore/16b.0'].data);
  expect(await read(page,V1)).toEqual(Object.fromEntries(Object.entries(after).filter(([key])=>key.startsWith('/RecordStore/'))));
});
test('two concurrent first opens commit exactly one migration',async({page,context})=>{
  await page.goto('./');await seed(page,V1);const other=await context.newPage();await other.goto('./');
  const results=await Promise.all([page.evaluate(()=>LoanV1Migration.transfer()),other.evaluate(()=>LoanV1Migration.transfer())]);
  expect(results.sort()).toEqual(['existing','migrated']);expect((await read(page,V2))['/RecordStore/16b.0'].data).toEqual(legacy['/RecordStore/16b.0'].data);await other.close();
});
test('no v1 checkpoint does not create a v1 database and game can still boot',async({page})=>{
  const frame=await start(page);expect(await frame.evaluate(()=>LoanV2.state().ready)).toBe(true);
  expect(await page.evaluate(async()=>(await indexedDB.databases()).map(db=>db.name))).not.toContain(V1);
});
test('corrupt v1 is rejected before mutating ver2 and the start UI allows retry',async({page})=>{
  await page.goto('./');const corrupt=structuredClone(legacy);corrupt['/RecordStore/16b.0'].data[0]=0;await seed(page,V1,corrupt);
  await page.click('#start');await expect(page.locator('#status')).toContainText('Không chuyển được tiến trình');await expect(page.locator('#start')).toBeEnabled();
  expect(await page.locator('#game').getAttribute('src')).toBeFalsy();
  await seed(page,V2);const before=await read(page,V2);
  page.once('dialog',d=>d.accept());await page.click('#migrate-v1');await expect(page.locator('#status')).toContainText('Không chuyển được tiến trình');expect(await read(page,V2)).toEqual(before);
});
test('aborted migration is atomic, retryable, and never mutates v1',async({page})=>{
  await page.goto('./');await seed(page,V1);await seed(page,V2,{'/RecordStore/old':{isDir:false,size:1,data:[7],parentDir:'/RecordStore'}});
  const before=await read(page,V2),source=await read(page,V1);
  const failed=await page.evaluate(async()=>{
    const put=IDBObjectStore.prototype.put;IDBObjectStore.prototype.put=function(value,...args){const result=put.call(this,value,...args);if(this.transaction.db.name==='loan12suquan-fs-ver2'&&value.pathname==='/.loan-ver2-v1-migration')this.transaction.abort();return result;};
    try{await LoanV1Migration.transfer({replace:true});return false;}catch{return true;}finally{IDBObjectStore.prototype.put=put;}
  });
  expect(failed).toBe(true);expect(await read(page,V2)).toEqual(before);expect(await read(page,V1)).toEqual(source);
  expect(await page.evaluate(()=>LoanV1Migration.transfer({replace:true}))).toBe('migrated');expect(await read(page,V1)).toEqual(source);
});
for(const width of [375,768,1440])test(`migration control fits at ${width}px`,async({page})=>{
  await page.setViewportSize({width,height:1000});await page.goto('./');
  expect(await page.evaluate(()=>document.documentElement.scrollWidth)).toBe(width);
  expect(await page.locator('#migrate-v1').evaluate(button=>getComputedStyle(button).whiteSpace==='nowrap'&&button.scrollWidth<=button.clientWidth)).toBe(true);
  await page.screenshot({path:`analysis/verification/ver2/v1-migration-${width}.png`,fullPage:true});
});
