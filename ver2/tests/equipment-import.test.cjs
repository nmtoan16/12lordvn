'use strict';
const {test}=require('node:test');
const assert=require('node:assert/strict');
const fs=require('node:fs');
const Store=require('../runtime/equipment/store.js');
const Loadout=require('../runtime/equipment/loadout.js');
const Battle=require('../runtime/equipment/battle-save.js');
const Import=require('../runtime/equipment/import.js');
const {CombatSession}=require('../runtime/equipment/combat.js');
function physicalFile(records){
  const size=40+[...records.values()].reduce((sum,bytes)=>sum+8+Math.ceil(bytes.length/8)*8,0),bytes=new Uint8Array(size),view=new DataView(bytes.buffer);
  bytes.set(new TextEncoder().encode('midp-rms'));view.setInt32(12,Math.max(...records.keys())+1);view.setInt32(16,records.size);view.setInt32(20,1);view.setInt32(32,size-40);
  let offset=40;for(const [id,data]of records){view.setInt32(offset,id);view.setInt32(offset+4,data.length);bytes.set(data,offset+8);offset+=8+Math.ceil(data.length/8)*8;}
  return {isDir:false,size,data:Array.from(bytes,byte=>byte>127?byte-256:byte)};
}
function fixture(logical,activeBank=2){
  const bank=new Uint8Array(2048),view=new DataView(bank.buffer),records=new Map([[1,new Uint8Array(2048)],[2,new Uint8Array(2048)],[3,new Uint8Array([activeBank])]]);
  view.setInt32(0,logical.size);let offset=4,next=4;
  for(const [key,bytes]of logical){const count=Math.ceil(bytes.length/161);view.setInt32(offset,key);view.setInt32(offset+4,bytes.length);view.setInt32(offset+8,count);offset+=12;
    for(let index=0;index<count;index++){const chunk=new Uint8Array(161);chunk.set(bytes.subarray(index*161,(index+1)*161));records.set(next,chunk);view.setInt32(offset,next++);offset+=4;}
  }
  view.setInt32(offset,0);records.set(activeBank,bank);return {files:{'/RecordStore/161b.0':physicalFile(records)},records,bank};
}

test('reads the original 16-byte map allocator and rejects mismatched chunk sizes',()=>{
  const file=require('./fixtures/v1-map-save.json')['/RecordStore/16b.0'];
  const map=Import.readLogicalRecords(file,16).logical;
  assert.deepEqual(Array.from(map.get(104)),[0,4,84,79,65,78]);
  assert.equal(map.get(100).length,262);assert.equal(new DataView(map.get(103).buffer).getInt32(0),10);
  assert.throws(()=>Import.readLogicalRecords(file),error=>['RMS_INDEX','RMS_CHUNK'].includes(error.code));
  assert.throws(()=>Import.readLogicalRecords(file,0),error=>error.code==='RMS_CHUNK_SIZE');
});
test('reads original browser export without writes or assuming L12E byte offsets',()=>{
  const raw=fs.readFileSync('analysis/verification/browser-save.json','utf8'),save=JSON.parse(raw);const result=Import.validateSaveFiles(save.files);
  assert.equal(result.storePath,'/RecordStore/161b.0');assert.ok(result.logicalKeys.includes(9));assert.equal(fs.readFileSync('analysis/verification/browser-save.json','utf8'),raw);
});
test('validates split profile, battle sidecar and active bank while ignoring inactive/orphan payloads',()=>{
  const profile=Store.defaultProfile('import');const snapshot=new Uint8Array([1,0,6]);const sidecar=Battle.createSidecar(snapshot,{battleId:'b',profileId:'import',loadout:Loadout.snapshot(profile.ownedTiers,[]),state:{barrierHp:0,emergencyUsed:false}});
  const f=fixture(new Map([[9,new Uint8Array(24)],[106,snapshot],[120,Store.encodeProfile(profile)],[121,Battle.encodeSidecar(sidecar)]]));
  f.records.get(1).set([76,49,50,69,255]);f.records.set(99,new Uint8Array([76,49,50,69,255]));f.files['/RecordStore/161b.0']=physicalFile(f.records);
  const result=Import.validateSaveFiles(f.files);assert.deepEqual(result.profile,profile);assert.equal(result.battleSnapshotMatches,true);assert.deepEqual(result.sidecar,sidecar);assert.deepEqual(result.logicalKeys,[9,106,120,121]);
});
test('known logical mappings catch corrupted magic, future schemas and missing chunks',()=>{
  const good=Store.encodeProfile(Store.defaultProfile('bad'));
  for(const change of ['magic','version','checksum']){const bytes=new Uint8Array(good);if(change==='magic')bytes[0]=0;else if(change==='version')bytes[4]=2;else bytes[bytes.length-1]^=1;const f=fixture(new Map([[120,bytes]]));assert.throws(()=>Import.validateSaveFiles(f.files),`${change} must be rejected`);}
  const f=fixture(new Map([[120,good]]));f.records.delete(4);f.files['/RecordStore/161b.0']=physicalFile(f.records);assert.throws(()=>Import.validateSaveFiles(f.files),error=>error.code==='RMS_CHUNK');
});
test('rejects corrupt physical boundaries, duplicate logical IDs, wrong chain size and invalid pointer',()=>{
  const profile=Store.encodeProfile(Store.defaultProfile('layout'));
  for(const change of ['boundary','duplicate','chain','pointer']){
    const f=fixture(new Map([[9,new Uint8Array(24)],[120,profile]]));const view=new DataView(f.bank.buffer);
    if(change==='duplicate')view.setInt32(20,9); // second descriptor starts after first 16-byte descriptor.
    if(change==='chain')view.setInt32(12,2);
    if(change==='pointer')f.records.set(3,new Uint8Array([0]));
    f.files['/RecordStore/161b.0']=physicalFile(f.records);
    if(change==='boundary')f.files['/RecordStore/161b.0'].data.pop();
    assert.throws(()=>Import.validateSaveFiles(f.files),change);
  }
});
test('no RMS is a valid legacy save; mismatched battle is explicit and no record is deleted',()=>{
  assert.equal(Import.validateSaveFiles({}).legacy,true);
  const profile=Store.defaultProfile('import'),sidecar=Battle.createSidecar(new Uint8Array([1]),{battleId:'b',profileId:'import',loadout:Loadout.snapshot(profile.ownedTiers,[]),state:{}});
  const f=fixture(new Map([[106,new Uint8Array([2])],[120,Store.encodeProfile(profile)],[121,Battle.encodeSidecar(sidecar)]]));const before=JSON.stringify(f.files);assert.equal(Import.validateSaveFiles(f.files).battleSnapshotMatches,false);assert.equal(JSON.stringify(f.files),before);
});
test('pending journal is accepted only for recoverable gold/profile combinations',()=>{
  const before=Store.defaultProfile('pending'),receipt={transactionId:'pending-one',itemId:0,fromTier:0,toTier:1,listedPrice:800,charged:300,goldBefore:300,goldAfter:0,revision:1};
  const after=structuredClone(before);after.revision=1;after.ownedTiers[0]=1;after.completedTransactions.push(receipt);
  const journal={schemaVersion:1,operation:'purchase',before,after,receipt};
  for(const profile of [before,after])for(const gold of [300,0]){
    const inventory=new Uint8Array(24);new DataView(inventory.buffer).setInt32(8,gold);
    const f=fixture(new Map([[9,inventory],[120,Store.encodeProfile(profile)],[122,Store.encodeJournal(journal)]]));assert.equal(Import.validateSaveFiles(f.files).pendingRecovery,true);
  }
  const inventory=new Uint8Array(24);new DataView(inventory.buffer).setInt32(8,99);const bad=fixture(new Map([[9,inventory],[120,Store.encodeProfile(before)],[122,Store.encodeJournal(journal)]]));assert.throws(()=>Import.validateSaveFiles(bad.files),error=>error.code==='RECOVERY_CONFLICT');
});
test('future/corrupt schemas in either sidecar or journal are rejected through their logical key',()=>{
  const profile=Store.defaultProfile('side'),sidecar=Battle.createSidecar(new Uint8Array([1]),{battleId:'b',profileId:'side',loadout:Loadout.snapshot(profile.ownedTiers,[]),state:{}});
  const reset={schemaVersion:1,operation:'reset',before:profile,after:Store.defaultProfile('after')};
  for(const [key,encoded]of [[121,Battle.encodeSidecar(sidecar)],[122,Store.encodeJournal(reset)]])for(const change of ['magic','version']){
    const bad=new Uint8Array(encoded);bad[change==='magic'?0:4]=change==='magic'?0:2;
    const f=fixture(new Map([[120,Store.encodeProfile(profile)],[key,bad]]));assert.throws(()=>Import.validateSaveFiles(f.files),error=>error.logicalKey===key);
  }
});
test('import rejects a future or malformed nested combat snapshot even when outer CRC and RMS mapping are valid',()=>{
  const profile=Store.defaultProfile('combat-import'),snapshot=new Uint8Array([1,0,6]);
  const combat=new CombatSession({tiers:profile.ownedTiers,fighters:[{maxHp:100,maxFood:100},{maxHp:100,maxFood:100}]});combat.start();
  const context={battleId:'b',profileId:profile.profileId,loadout:Loadout.snapshot(profile.ownedTiers,[]),state:{combat:combat.snapshot(),sequence:0,action:null,cast:null}};
  for(const mutation of [sidecar=>sidecar.state.combat.version=2,sidecar=>sidecar.state.combat.fighters[0].hp=101]){
    const sidecar=Battle.createSidecar(snapshot,context);mutation(sidecar);
    const encoded=Store.encodeRecord('battle',sidecar),f=fixture(new Map([[120,Store.encodeProfile(profile)],[121,encoded],[106,snapshot]]));
    const before=JSON.stringify(f.files);assert.throws(()=>Import.validateSaveFiles(f.files),error=>error.logicalKey===121);assert.equal(JSON.stringify(f.files),before);
  }
});
