const {test,expect}=require('@playwright/test');
const {start}=require('./helpers/vm.cjs');
test('ver2 boot uses its own databases and paths; v1 sentinel survives',async({page})=>{
  const requests=[];
  page.on('request',r=>requests.push(r.url()));
  await page.goto('./');
  await page.evaluate(async()=>{
    await new Promise((resolve,reject)=>{const r=indexedDB.open('loan12suquan-fs-v1',1);r.onupgradeneeded=()=>r.result.createObjectStore('sentinel');r.onerror=()=>reject(r.error);r.onsuccess=()=>{const db=r.result,t=db.transaction('sentinel','readwrite');t.objectStore('sentinel').put('unchanged','progress');t.oncomplete=()=>{db.close();resolve();};};});
  });
  const frame=await start(page);
  expect(frame.url()).toContain('/12suquan/ver2/runtime/');
  const state=await frame.evaluate(async()=>({dbs:(await indexedDB.databases()).map(x=>x.name),keys:Object.keys(localStorage),cfg:LOAN_VERSION}));
  expect(state.dbs).toEqual(expect.arrayContaining(['loan12suquan-fs-ver2','loan12suquan-jars-ver2']));
  expect(state.dbs).not.toContain('loan12suquan-jars-v1');
  expect(state.keys).not.toContain('loan12suquan:lastAppVersion');
  expect(requests.filter(url=>/\.jar|\.js(?:\?|$)|\.png/.test(url)).every(url=>url.includes('/12suquan/ver2/'))).toBe(true);
  expect(await page.evaluate(()=>new Promise(resolve=>{const r=indexedDB.open('loan12suquan-fs-v1');r.onsuccess=()=>{const db=r.result,q=db.transaction('sentinel').objectStore('sentinel').get('progress');q.onsuccess=()=>{db.close();resolve(q.result);};};}))).toBe('unchanged');
});

test('two live v1/ver2 tabs keep RMS independent through purchase, reset and import',async({page,context})=>{
  const oldPage=await context.newPage();
  const original=await start(oldPage,'http://localhost/12suquan/');
  const rms=frame=>frame.evaluate(()=>new Promise(resolve=>fs.exportStore(async blob=>{const files=JSON.parse(await blob.text());resolve(Object.fromEntries(Object.entries(files).filter(([key])=>key.includes('/RecordStore/')).map(([key,value])=>[key,value.data])));}))); 
  const before=await rms(original),frame=await start(page);
  await expect.poll(()=>frame.evaluate(()=>LoanV2.state().ready)).toBe(true);
  await frame.evaluate(async()=>{await LoanV2.purchase(0);await LoanV2.equip(0);await LoanV2.flush();});
  const save=await frame.evaluate(()=>new Promise(resolve=>fs.exportStore(async blob=>resolve({format:'loan12-save-v1',variant:'ver2',gameSha256:'90bf8dc99acba9f0a89da99c58b45c8fdb20240841e5a708e0ccfee58a650ff9',files:JSON.parse(await blob.text())}))));
  await frame.evaluate(async()=>{await LoanVM.invoke('com/mic/smsgame/suquan/f.a.()V');});
  await expect.poll(()=>frame.evaluate(()=>LoanV2.state().ready)).toBe(true);
  await expect.poll(()=>frame.evaluate(()=>LoanV2.state().profile.ownedTiers[0])).toBe(0);
  await page.locator('summary').filter({hasText:'Lưu và chuyển tiến trình'}).click();page.once('dialog',d=>d.accept());
  await page.locator('#save-file').setInputFiles({name:'ver2.json',mimeType:'application/json',buffer:Buffer.from(JSON.stringify(save))});
  await expect.poll(()=>frame.url()).toContain('restore=');
  await expect.poll(()=>frame.evaluate(()=>window.LoanV2?.state().ready).catch(()=>false),{timeout:25000}).toBe(true);
  expect(await frame.evaluate(()=>LoanV2.state().profile.ownedTiers[0])).toBe(1);
  expect(await rms(original)).toEqual(before);await oldPage.close();
});
