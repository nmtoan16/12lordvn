/* Read-only validation of exported J2meJS RMS files before IndexedDB import. */
'use strict';
(function(root,factory){
  const common=typeof module==='object'&&module.exports;
  const api=factory(common?require('./store.js'):root.LoanEquipment?.store,common?require('./battle-save.js'):root.LoanEquipment?.battleSave);
  if(common)module.exports=api;
  (root.LoanEquipment||(root.LoanEquipment={})).saveImport=api;
})(typeof globalThis!=='undefined'?globalThis:this,function(Store,Battle){
  if(!Store||!Battle)throw new Error('Load equipment store.js and battle-save.js before import.js.');
  const STORE_PATH='/RecordStore/161b.0',INDEX_SIZE=2048,CHUNK_SIZE=161;
  function fail(code,message){const error=new Error(message);error.code=code;throw error;}
  function fileBytes(file){
    if(!file||file.isDir!==false||!Number.isInteger(file.size)||file.size<40||file.size>1048576||!Array.isArray(file.data)||file.data.length!==file.size)fail('RMS_FILE','Invalid exported 161b RecordStore file.');
    if(file.data.some(byte=>!Number.isInteger(byte)||byte< -128||byte>255))fail('RMS_BYTES','RecordStore data contains invalid bytes.');
    return Uint8Array.from(file.data);
  }
  function parsePhysicalRecords(file){
    const bytes=fileBytes(file),view=new DataView(bytes.buffer);
    if(new TextDecoder().decode(bytes.subarray(0,8))!=='midp-rms')fail('RMS_MAGIC','Invalid MIDP RecordStore signature.');
    const nextId=view.getInt32(12,false),count=view.getInt32(16,false),dataLength=view.getInt32(32,false);
    if(nextId<1||count<0||dataLength<0||dataLength!==bytes.length-40)fail('RMS_LENGTH','Invalid MIDP RecordStore header bounds.');
    const records=new Map();
    // RecordStoreImpl.compactRecords scans at byte 40. RecordStoreUtil rounds
    // each payload up to 8 bytes and adds its recordId/length header (8 bytes).
    for(let offset=40;offset<bytes.length;){
      if(offset+8>bytes.length)fail('RMS_LENGTH','Truncated physical record header.');
      const recordId=view.getInt32(offset,false),length=view.getInt32(offset+4,false);
      if(recordId===0||length<0)fail('RMS_RECORD','Invalid physical RecordStore record.');
      const next=offset+8+Math.ceil(length/8)*8;
      if(next>bytes.length)fail('RMS_LENGTH','Truncated physical RecordStore payload.');
      if(recordId>0){
        if(recordId>=nextId||records.has(recordId))fail('RMS_RECORD','Duplicate/out-of-range physical record ID.');
        records.set(recordId,bytes.slice(offset+8,offset+8+length));
      }
      // Negative IDs are free blocks owned by MIDP, not live records.
      offset=next;
    }
    if(records.size!==count)fail('RMS_RECORD_COUNT','Physical record count does not match the MIDP header.');
    return records;
  }
  function readLogicalRecords(file,chunkSize=CHUNK_SIZE){
    if(![16,CHUNK_SIZE].includes(chunkSize))fail('RMS_CHUNK_SIZE','Unsupported dr chunk size.');
    const physical=parsePhysicalRecords(file),pointer=physical.get(3);
    if(!pointer||pointer.length!==1||![1,2].includes(pointer[0]))fail('RMS_INDEX','Invalid dr active-index pointer.');
    const selectedBank=pointer[0],bank=physical.get(selectedBank);
    if(!bank||bank.length!==INDEX_SIZE||physical.get(3-selectedBank)?.length!==INDEX_SIZE)fail('RMS_INDEX','Missing dr index bank.');
    const view=new DataView(bank.buffer,bank.byteOffset,bank.byteLength),count=view.getInt32(0,false);
    if(count<0||count>Math.floor((bank.length-8)/12))fail('RMS_INDEX','Invalid dr logical record count.');
    const logical=new Map(),usedChunks=new Set();let offset=4;
    for(let index=0;index<count;index++){
      if(offset+12>bank.length)fail('RMS_INDEX','Truncated dr logical descriptor.');
      const key=view.getInt32(offset,false),length=view.getInt32(offset+4,false),chunks=view.getInt32(offset+8,false);offset+=12;
      if(key<0||logical.has(key)||length<0||length>1048576||chunks!==Math.ceil(length/chunkSize)||offset+chunks*4>bank.length)fail('RMS_INDEX','Invalid/duplicate dr logical key or chunk count.');
      const data=new Uint8Array(length);let written=0;
      for(let chunkIndex=0;chunkIndex<chunks;chunkIndex++){
        const physicalId=view.getInt32(offset,false);offset+=4;const chunk=physical.get(physicalId);
        if(physicalId<=3||usedChunks.has(physicalId)||!chunk||chunk.length!==chunkSize)fail('RMS_CHUNK','Missing, aliased or malformed dr payload chunk.');
        usedChunks.add(physicalId);const take=Math.min(chunkSize,length-written);data.set(chunk.subarray(0,take),written);written+=take;
      }
      logical.set(key,data);
    }
    if(offset+4>bank.length)fail('RMS_INDEX','Missing dr free-list count.');
    const freeCount=view.getInt32(offset,false);offset+=4;
    if(freeCount<0||offset+freeCount*4>bank.length)fail('RMS_INDEX','Invalid dr free-list bounds.');
    const freeIds=new Set();
    for(let index=0;index<freeCount;index++){
      const recordId=view.getInt32(offset,false);offset+=4;
      if(recordId<=3||freeIds.has(recordId)||usedChunks.has(recordId)||physical.get(recordId)?.length!==chunkSize)fail('RMS_CHUNK','Invalid/aliased dr free-list record.');
      freeIds.add(recordId);
    }
    return {logical,selectedBank,physicalRecordCount:physical.size};
  }
  function decodeAt(records,key,decode){
    if(!records.has(key))return null;
    try{return decode(records.get(key));}
    catch(error){error.logicalKey=key;throw error;}
  }
  const equal=(a,b)=>JSON.stringify(a)===JSON.stringify(b);
  function validateSaveFiles(files){
    if(!files||typeof files!=='object'||Array.isArray(files))fail('SAVE_FILES','An exported filesystem object is required.');
    if(!Object.prototype.hasOwnProperty.call(files,STORE_PATH))return {legacy:true,storePath:null,logicalKeys:[],profile:null,sidecar:null,journal:null,battleSnapshotMatches:null,pendingRecovery:false};
    const {logical,selectedBank,physicalRecordCount}=readLogicalRecords(files[STORE_PATH]);
    // Follow dr's actual logical mapping. Searching payload magic would miss
    // corrupt headers and accidentally inspect obsolete/orphan record data.
    const profile=decodeAt(logical,Store.KEYS.profile,Store.decodeProfile);
    const sidecar=decodeAt(logical,Store.KEYS.battle,Battle.decodeSidecar);
    const journal=decodeAt(logical,Store.KEYS.journal,Store.decodeJournal);
    if(journal){
      if(!equal(profile,journal.before)&&!equal(profile,journal.after))fail('RECOVERY_CONFLICT','Imported profile does not match its pending transaction journal.');
      if(journal.operation==='purchase'){
        const inventory=logical.get(9);let gold=0;
        if(inventory){
          if(inventory.length<24||inventory.length%4!==0)fail('RMS_GOLD','Invalid original inventory/gold record.');
          gold=new DataView(inventory.buffer,inventory.byteOffset,inventory.byteLength).getInt32(8,false);
        }
        if(gold!==journal.receipt.goldBefore&&gold!==journal.receipt.goldAfter)fail('RECOVERY_CONFLICT','Imported gold does not match its pending purchase.');
      }
    }
    if(sidecar&&sidecar.profileId!==profile?.profileId&&journal?.operation!=='reset')fail('BATTLE_PROFILE','Battle sidecar belongs to a different equipment profile.');
    const snapshot=logical.get(106);
    const battleSnapshotMatches=sidecar?Boolean(snapshot&&Battle.fingerprintSnapshot(snapshot)===sidecar.snapshotFingerprint):null;
    return {legacy:!profile&&!sidecar&&!journal,storePath:STORE_PATH,logicalKeys:Array.from(logical.keys()).sort((a,b)=>a-b),selectedBank,physicalRecordCount,profile,sidecar,journal,battleSnapshotMatches,pendingRecovery:journal!==null};
  }
  return Object.freeze({STORE_PATH,parsePhysicalRecords,readLogicalRecords,validateSaveFiles});
});
