/* Equipment records use logical dr keys, never physical RecordStore IDs. */
'use strict';
(function(root,factory){
  const loadout=typeof module==='object'&&module.exports?require('./loadout.js'):root.LoanEquipment?.loadout;
  const api=factory(loadout);
  if(typeof module==='object'&&module.exports)module.exports=api;
  (root.LoanEquipment||(root.LoanEquipment={})).store=api;
})(typeof globalThis!=='undefined'?globalThis:this,function(Loadout){
  if(!Loadout)throw new Error('Load equipment/loadout.js before equipment/store.js.');
  const KEYS=Object.freeze({profile:120,battle:121,journal:122});
  const TYPES=Object.freeze({profile:1,journal:2,battle:3});
  const PRICES=Object.freeze([[800,2200,5000],[700,2000,4500],[900,2500,5500],[800,2200,4800],[1200,3000,6000],[600,1800,4000]].map(Object.freeze));
  const MAX_INT=2147483647,MAX_PAYLOAD=32768;
  const clone=value=>JSON.parse(JSON.stringify(value));
  const equal=(a,b)=>JSON.stringify(a)===JSON.stringify(b);
  function fail(code,message){const error=new Error(message);error.code=code;throw error;}
  function integer(value,min=0,max=MAX_INT,name='Integer'){
    if(!Number.isSafeInteger(value)||value<min||value>max)fail('NUMBER_RANGE',name+' is outside its allowed integer range.');
    return value;
  }
  function identifier(value,name='ID'){
    if(typeof value!=='string'||value.length<1||value.length>128||/[\u0000-\u001f]/.test(value))fail('IDENTIFIER','Invalid '+name+'.');
    return value;
  }
  function crc32(bytes){
    let crc=0xffffffff;
    for(const byte of bytes){crc^=byte;for(let bit=0;bit<8;bit++)crc=(crc>>>1)^((crc&1)?0xedb88320:0);}
    return (crc^0xffffffff)>>>0;
  }
  function encodeRecord(kind,value){
    if(!TYPES[kind])fail('RECORD_KIND','Unknown extension record kind.');
    const payload=new TextEncoder().encode(JSON.stringify(value));
    if(payload.length>MAX_PAYLOAD)fail('RECORD_LENGTH','Extension record is too large.');
    const bytes=new Uint8Array(16+payload.length),view=new DataView(bytes.buffer);
    bytes.set([0x4c,0x31,0x32,0x45,1,TYPES[kind],0,0]);
    view.setUint32(8,payload.length,false);view.setUint32(12,crc32(payload),false);bytes.set(payload,16);
    return bytes;
  }
  function decodeRecord(kind,input){
    if(!ArrayBuffer.isView(input)||input.BYTES_PER_ELEMENT!==1)fail('RECORD_BYTES','A byte array is required.');
    const bytes=new Uint8Array(input.buffer,input.byteOffset,input.byteLength);
    if(bytes.length<16||bytes[0]!==0x4c||bytes[1]!==0x31||bytes[2]!==0x32||bytes[3]!==0x45)fail('RECORD_MAGIC','Invalid extension record header.');
    if(bytes[4]!==1)fail('SCHEMA_VERSION','Unsupported extension schema version '+bytes[4]+'.');
    if(bytes[5]!==TYPES[kind]||bytes[6]!==0||bytes[7]!==0)fail('RECORD_KIND','Unexpected extension record kind or flags.');
    const view=new DataView(bytes.buffer,bytes.byteOffset,bytes.byteLength),length=view.getUint32(8,false);
    if(length>MAX_PAYLOAD||bytes.length!==16+length)fail('RECORD_LENGTH','Extension record length mismatch.');
    const payload=bytes.subarray(16);
    if(view.getUint32(12,false)!==crc32(payload))fail('CHECKSUM','Extension record checksum mismatch.');
    try{return JSON.parse(new TextDecoder('utf-8',{fatal:true}).decode(payload));}
    catch{fail('RECORD_JSON','Extension record is not valid UTF-8 JSON.');}
  }
  function makeProfileId(){
    return typeof crypto!=='undefined'&&crypto.randomUUID?crypto.randomUUID():Date.now().toString(36)+'-'+Math.random().toString(36).slice(2);
  }
  function defaultProfile(profileId=makeProfileId()){
    return {schemaVersion:1,profileId:identifier(profileId,'profile ID'),revision:0,ownedTiers:[0,0,0,0,0,0],equippedIds:[],completedTransactions:[]};
  }
  function validateReceipt(receipt){
    if(!receipt||typeof receipt!=='object')fail('RECEIPT','Invalid purchase receipt.');
    const result={transactionId:identifier(receipt.transactionId,'transaction ID'),itemId:integer(receipt.itemId,0,5),fromTier:integer(receipt.fromTier,0,2),toTier:integer(receipt.toTier,1,3),listedPrice:integer(receipt.listedPrice,1),charged:integer(receipt.charged),goldBefore:integer(receipt.goldBefore),goldAfter:integer(receipt.goldAfter),revision:integer(receipt.revision,1)};
    if(result.toTier!==result.fromTier+1||result.charged!==Math.min(result.goldBefore,result.listedPrice)||result.goldAfter!==result.goldBefore-result.charged)fail('RECEIPT','Purchase receipt does not balance.');
    return result;
  }
  function validateProfile(profile){
    if(!profile||typeof profile!=='object'||profile.schemaVersion!==1)fail('SCHEMA_VERSION','Unsupported equipment profile schema.');
    const ownedTiers=Loadout.validateOwnedTiers(profile.ownedTiers);
    if(!Array.isArray(profile.completedTransactions)||profile.completedTransactions.length>18)fail('RECEIPT','Invalid purchase receipt history.');
    const completedTransactions=profile.completedTransactions.map(validateReceipt),revision=integer(profile.revision),seen=new Set();
    let previousRevision=0;
    for(const receipt of completedTransactions){
      if(seen.has(receipt.transactionId)||receipt.revision<=previousRevision||receipt.revision>revision||receipt.toTier>ownedTiers[receipt.itemId])fail('RECEIPT','Inconsistent purchase receipt history.');
      seen.add(receipt.transactionId);previousRevision=receipt.revision;
    }
    return {schemaVersion:1,profileId:identifier(profile.profileId,'profile ID'),revision,ownedTiers,equippedIds:Loadout.validateLoadout(profile.equippedIds,ownedTiers),completedTransactions};
  }
  function encodeProfile(profile){return encodeRecord('profile',validateProfile(profile));}
  function decodeProfile(bytes){return validateProfile(decodeRecord('profile',bytes));}
  function validateJournal(journal){
    if(!journal||journal.schemaVersion!==1)fail('SCHEMA_VERSION','Unsupported transaction journal schema.');
    if(journal.operation==='reset'){
      const before=journal.before===null?null:validateProfile(journal.before),after=validateProfile(journal.after);
      if(after.revision!==0||after.ownedTiers.some(Boolean)||after.equippedIds.length||after.completedTransactions.length||after.profileId===before?.profileId)fail('JOURNAL','Invalid profile reset target.');
      return {schemaVersion:1,operation:'reset',before,after};
    }
    if(journal.operation!=='purchase')fail('JOURNAL','Unknown journal operation.');
    const before=validateProfile(journal.before),after=validateProfile(journal.after),receipt=validateReceipt(journal.receipt);
    if(before.profileId!==after.profileId||after.revision!==before.revision+1||receipt.revision!==after.revision||before.ownedTiers[receipt.itemId]!==receipt.fromTier)fail('JOURNAL','Invalid purchase revisions or ownership.');
    const expected=clone(before);expected.revision++;expected.ownedTiers[receipt.itemId]++;expected.completedTransactions.push(receipt);
    if(!equal(expected,after))fail('JOURNAL','Purchase journal changes unrelated profile data.');
    return {schemaVersion:1,operation:'purchase',before,after,receipt};
  }
  function encodeJournal(journal){return encodeRecord('journal',validateJournal(journal));}
  function decodeJournal(bytes){return validateJournal(decodeRecord('journal',bytes));}
  function purchaseResult(receipt,profile,replayed){
    return {...clone(receipt),tier:receipt.toTier,replayed,profile:clone(profile)};
  }
  function createStore(port,options={}){
    for(const method of ['read','write','readGold','setGold','flush'])if(typeof port?.[method]!=='function')throw new TypeError('Equipment port requires '+method+'().');
    const priceFor=options.priceFor||((id,tier)=>PRICES[id][tier-1]);
    const idFactory=options.profileIdFactory||makeProfileId;
    let profile=null,loaded=false,locked=false,tail=Promise.resolve();
    function serial(work){
      const task=tail.then(work);tail=task.catch(()=>{loaded=false;});return task;
    }
    async function clearJournal(){await port.write(KEYS.journal,null);await port.flush();}
    async function applyJournal(journal,current){
      if(!equal(current,journal.before)&&!equal(current,journal.after))fail('RECOVERY_CONFLICT','Profile does not match the pending journal. Preserve the save for recovery.');
      if(journal.operation==='purchase'){
        const gold=integer(await port.readGold());
        if(gold!==journal.receipt.goldBefore&&gold!==journal.receipt.goldAfter)fail('RECOVERY_CONFLICT','Gold changed outside the pending purchase. Preserve the save for recovery.');
        if(!equal(current,journal.after)||gold!==journal.receipt.goldAfter){
          await port.setGold(journal.receipt.goldAfter);await port.write(KEYS.profile,encodeProfile(journal.after));await port.flush();
        }
      }else{
        await port.write(KEYS.profile,encodeProfile(journal.after));await port.write(KEYS.battle,null);await port.flush();
      }
      await clearJournal();return journal.after;
    }
    async function ensure(){
      if(loaded)return;
      const bytes=await port.read(KEYS.profile),journalBytes=await port.read(KEYS.journal);
      let current=bytes===null?null:decodeProfile(bytes);
      const journal=journalBytes===null?null:decodeJournal(journalBytes);
      // Decode both records before the first write: corrupt/future saves survive.
      if(journal)current=await applyJournal(journal,current);
      if(current===null){current=defaultProfile(idFactory());await port.write(KEYS.profile,encodeProfile(current));await port.flush();}
      profile=current;loaded=true;
    }
    return Object.freeze({
      init:()=>serial(async()=>{await ensure();return clone(profile);}),
      getProfile:()=>serial(async()=>{await ensure();return clone(profile);}),
      getGold:()=>serial(async()=>{await ensure();return integer(await port.readGold());}),
      recover:()=>serial(async()=>{loaded=false;await ensure();return clone(profile);}),
      setLocked(value){locked=Boolean(value);},
      purchaseNextTier:(itemId,transactionId)=>serial(async()=>{
        await ensure();integer(itemId,0,5);identifier(transactionId,'transaction ID');
        const previous=profile.completedTransactions.find(receipt=>receipt.transactionId===transactionId);
        if(previous){if(previous.itemId!==itemId)fail('TRANSACTION_CONFLICT','Transaction ID already belongs to another item.');return purchaseResult(previous,profile,true);}
        const fromTier=profile.ownedTiers[itemId];if(fromTier===3)fail('MAX_TIER','This equipment is already MAX.');
        const listedPrice=integer(priceFor(itemId,fromTier+1),1),goldBefore=integer(await port.readGold()),charged=Math.min(goldBefore,listedPrice);
        const receipt={transactionId,itemId,fromTier,toTier:fromTier+1,listedPrice,charged,goldBefore,goldAfter:goldBefore-charged,revision:integer(profile.revision+1,1)};
        const after=clone(profile);after.revision=receipt.revision;after.ownedTiers[itemId]++;after.completedTransactions.push(receipt);
        const journal={schemaVersion:1,operation:'purchase',before:profile,after,receipt};
        await port.write(KEYS.journal,encodeJournal(journal));await port.flush();
        await port.setGold(receipt.goldAfter);await port.write(KEYS.profile,encodeProfile(after));await port.flush();
        await clearJournal();profile=after;
        return purchaseResult(receipt,profile,false);
      }),
      setLoadout:(ids,expectedRevision)=>serial(async()=>{
        await ensure();if(locked||options.isLocked?.())fail('LOADOUT_LOCKED','Equipment is locked during this battle.');
        if(integer(expectedRevision)!==profile.revision)fail('REVISION_CONFLICT','Equipment changed while this panel was open.');
        const equippedIds=Loadout.validateLoadout(ids,profile.ownedTiers);if(equal(equippedIds,profile.equippedIds))return clone(profile);
        const after=clone(profile);after.revision=integer(after.revision+1);after.equippedIds=equippedIds;
        await port.write(KEYS.profile,encodeProfile(after));await port.flush();profile=after;return clone(profile);
      }),
      resetProfile:()=>serial(async()=>{
        const bytes=await port.read(KEYS.profile),pending=await port.read(KEYS.journal);
        const before=bytes===null?null:decodeProfile(bytes);if(pending!==null)decodeJournal(pending);
        const after=defaultProfile(idFactory()),journal={schemaVersion:1,operation:'reset',before,after};
        await port.write(KEYS.journal,encodeJournal(journal));await port.flush();
        await port.write(KEYS.profile,encodeProfile(after));await port.write(KEYS.battle,null);await port.flush();
        await clearJournal();profile=after;loaded=true;locked=false;return clone(profile);
      })
    });
  }
  return Object.freeze({KEYS,TYPES,PRICES,MAX_PAYLOAD,crc32,encodeRecord,decodeRecord,defaultProfile,validateProfile,encodeProfile,decodeProfile,validateJournal,encodeJournal,decodeJournal,createStore});
});
