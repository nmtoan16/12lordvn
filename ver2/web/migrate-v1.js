/* Read v1 only; commit a complete RMS snapshot before starting the ver2 VM. */
'use strict';
(() => {
  const SOURCE='loan12suquan-fs-v1',TARGET='loan12suquan-fs-ver2',STORE='fs4';
  const MARKER='/.loan-ver2-v1-migration',RMS='/RecordStore/';
  function open(name,create=false){
    return new Promise((resolve,reject)=>{
      const request=indexedDB.open(name,create?4:undefined);let absent=false,blocked=false;
      request.onupgradeneeded=event=>{
        if(!create){absent=event.oldVersion===0;request.transaction.abort();return;}
        if(event.oldVersion!==0){request.transaction.abort();return;}
        request.result.createObjectStore(STORE,{keyPath:'pathname'}).createIndex('parentDir','parentDir',{unique:false});
      };
      request.onerror=()=>absent?resolve(null):reject(request.error);
      request.onblocked=()=>{blocked=true;reject(Error('Hãy đóng các tab game khác rồi thử lại.'));};
      request.onsuccess=()=>blocked?request.result.close():resolve(request.result);
    });
  }
  function read(db){return new Promise((resolve,reject)=>{
    if(!db.objectStoreNames.contains(STORE)){resolve([]);return;}
    const tx=db.transaction(STORE,'readonly'),request=tx.objectStore(STORE).getAll();let records;
    request.onsuccess=()=>{records=request.result;};
    tx.oncomplete=()=>resolve(records);tx.onabort=()=>reject(tx.error||Error('Không đọc được bản lưu.'));
  });}
  const hasRms=records=>records.some(record=>record.pathname===MARKER||record.pathname?.startsWith(RMS)&&!record.isDir);
  async function sourceSnapshot(){
    const db=await open(SOURCE);if(!db)return null;
    let records;try{records=(await read(db)).filter(record=>record.pathname==='/RecordStore'||record.pathname?.startsWith(RMS));}finally{db.close();}
    if(!records.some(record=>record.pathname===RMS+'16b.0'))return null;
    const files={};
    for(const record of records){
      if(record.isDir)continue;
      if(![RMS+'16b.0',RMS+'161b.0'].includes(record.pathname)||!(record.data instanceof Blob)||record.size!==record.data.size||record.size>1048576)throw Error('Bản lưu ver1 có cấu trúc chưa hỗ trợ.');
      files[record.pathname]={...record,data:Array.from(new Int8Array(await record.data.arrayBuffer()))};
    }
    if(!files[RMS+'161b.0'])throw Error('Bản lưu ver1 thiếu dữ liệu kho và vàng.');
    const validator=LoanEquipment.saveImport;
    const map=validator.readLogicalRecords(files[RMS+'16b.0'],16).logical;
    const validation=validator.validateSaveFiles(files);
    if(!validation.legacy)throw Error('Dữ liệu ver1 chứa trang bị ver2; hãy dùng chức năng Nhập bản lưu.');
    if(!map.has(100))return null;
    // The map, inventory and optional battle checkpoint belong to one IDB read.
    // Copy the original Blob bytes, never decode/re-encode the game's payload.
    return records;
  }
  function commit(db,records,replace){return new Promise((resolve,reject)=>{
    const tx=db.transaction(STORE,'readwrite'),store=tx.objectStore(STORE),request=store.getAll();let result='existing';
    request.onsuccess=()=>{
      const current=request.result;
      // Check again inside the write transaction: two first-open tabs cannot
      // both import, and an already committed ver2 save is never auto-replaced.
      if(!replace&&hasRms(current))return;
      for(const record of current)if(record.pathname===MARKER||record.pathname==='/RecordStore'||record.pathname?.startsWith(RMS))store.delete(record.pathname);
      if(!current.some(record=>record.pathname==='/'))store.put({pathname:'/',parentDir:null,isDir:true,mtime:Date.now()});
      store.put({pathname:'/RecordStore',parentDir:'/',isDir:true,mtime:Date.now()});
      for(const record of records)store.put(record);
      const data=new Blob([JSON.stringify({source:SOURCE,at:new Date().toISOString()})]);
      store.put({pathname:MARKER,parentDir:'/',isDir:false,size:data.size,mtime:Date.now(),data});
      result='migrated';
    };
    tx.oncomplete=()=>resolve(result);
    tx.onabort=()=>reject(tx.error||Error('Không ghi được bản lưu ver2. Bản lưu trước đó được giữ nguyên.'));
  });}
  async function transfer({replace=false}={}){
    // Prefer existing ver2 without even requiring a readable v1 database.
    let target=await open(TARGET);
    if(target){try{if(!replace&&hasRms(await read(target)))return 'existing';}finally{target.close();}}
    const snapshot=await sourceSnapshot();if(!snapshot)return 'absent';
    target=await open(TARGET,true);
    try{return await commit(target,snapshot,replace);}finally{target.close();}
  }
  window.LoanV1Migration=Object.freeze({transfer});
})();
