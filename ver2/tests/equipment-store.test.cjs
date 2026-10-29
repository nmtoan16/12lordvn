'use strict';
const {test}=require('node:test');
const assert=require('node:assert/strict');
const Store=require('../runtime/equipment/store.js');
const copy=value=>value===null?null:new Uint8Array(value);
function memoryPort(initial=new Map(),initialGold=1000){
  let durable=new Map([...initial].map(([key,value])=>[key,copy(value)]));let current=new Map(durable);let gold=initialGold,durableGold=gold,step=0,fault=null;
  const mutate=async fn=>{step++;if(fault?.step===step&&fault.when==='before')throw new Error('Power loss');fn();if(fault?.step===step&&fault.when==='after')throw new Error('Power loss');};
  return {port:{async read(key){return copy(current.get(key)??null);},async write(key,value){await mutate(()=>{if(value===null)current.delete(key);else current.set(key,copy(value));});},async readGold(){return gold;},async setGold(value){await mutate(()=>{gold=value;});},async flush(){await mutate(()=>{durable=new Map([...current].map(([key,value])=>[key,copy(value)]));durableGold=gold;});}},arm(stepNumber,when){step=0;fault={step:stepNumber,when};},restart(){fault=null;step=0;current=new Map([...durable].map(([key,value])=>[key,copy(value)]));gold=durableGold;},snapshot(){return {records:new Map([...durable].map(([key,value])=>[key,copy(value)])),gold:durableGold};}};
}
const options={profileIdFactory:(()=>{let id=0;return()=>`test-profile-${++id}`;})()};
test('legacy save initializes defaults without touching gold or original snapshot',async()=>{
  const original=new Uint8Array([8,6,4]);const mem=memoryPort(new Map([[106,original]]),352);
  const store=Store.createStore(mem.port,options);const profile=await store.init();
  assert.deepEqual(profile.ownedTiers,[0,0,0,0,0,0]);assert.deepEqual(profile.equippedIds,[]);assert.equal(profile.revision,0);
  assert.equal(mem.snapshot().gold,352);assert.deepEqual(mem.snapshot().records.get(106),original);
});
test('profile codec rejects corrupted/future data and init never overwrites it',async()=>{
  const profile=Store.defaultProfile('codec');const bytes=Store.encodeProfile(profile);assert.deepEqual(Store.decodeProfile(bytes),profile);
  const corrupt=new Uint8Array(bytes);corrupt[corrupt.length-1]^=1;assert.throws(()=>Store.decodeProfile(corrupt),error=>error.code==='CHECKSUM');
  const future=new Uint8Array(bytes);future[4]=2;assert.throws(()=>Store.decodeProfile(future),error=>error.code==='SCHEMA_VERSION');
  for(const invalid of [corrupt,future]){const mem=memoryPort(new Map([[120,invalid]]));await assert.rejects(Store.createStore(mem.port,options).init());assert.deepEqual(mem.snapshot().records.get(120),invalid);}
});
test('listed price 800 charges min(gold, price), never auto-equips, and retry is idempotent across reload',async()=>{
  for(const gold of [0,300,800,1000]){
    const mem=memoryPort(new Map(),gold);let store=Store.createStore(mem.port,options);await store.init();
    const result=await store.purchaseNextTier(0,'purchase-one');assert.equal(result.tier,1);assert.equal(result.listedPrice,800);assert.equal(result.charged,Math.min(gold,800));assert.equal(result.goldAfter,Math.max(0,gold-800));assert.deepEqual(result.profile.equippedIds,[]);
    mem.restart();store=Store.createStore(mem.port,options);const retry=await store.purchaseNextTier(0,'purchase-one');assert.equal(retry.replayed,true);assert.equal((await store.getProfile()).ownedTiers[0],1);assert.equal(mem.snapshot().gold,Math.max(0,gold-800));
    await assert.rejects(store.purchaseNextTier(1,'purchase-one'),error=>error.code==='TRANSACTION_CONFLICT');
  }
});
test('serialized purchases, max tier and revision/lock checks preserve a three-item loadout',async()=>{
  const mem=memoryPort();const store=Store.createStore(mem.port,options);await store.init();
  await Promise.all([store.purchaseNextTier(0,'one'),store.purchaseNextTier(0,'two'),store.purchaseNextTier(0,'three')]);
  assert.equal((await store.getProfile()).ownedTiers[0],3);await assert.rejects(store.purchaseNextTier(0,'four'),error=>error.code==='MAX_TIER');
  await store.purchaseNextTier(1,'five');let profile=await store.getProfile();profile=await store.setLoadout([0,1],profile.revision);assert.deepEqual(profile.equippedIds,[0,1]);
  await assert.rejects(store.setLoadout([0],profile.revision-1),error=>error.code==='REVISION_CONFLICT');store.setLocked(true);await assert.rejects(store.setLoadout([],profile.revision),error=>error.code==='LOADOUT_LOCKED');
});
test('recovers every before/after write and flush interruption with exactly one purchase',async()=>{
  for(const when of ['before','after'])for(let step=1;step<=7;step++){
    const mem=memoryPort();let store=Store.createStore(mem.port,options);await store.init();mem.arm(step,when);
    await assert.rejects(store.purchaseNextTier(0,'fault-purchase'),/Power loss/);mem.restart();store=Store.createStore(mem.port,options);await store.init();
    const interim=await store.getProfile();assert.ok((interim.ownedTiers[0]===0&&mem.snapshot().gold===1000)||(interim.ownedTiers[0]===1&&mem.snapshot().gold===200),`${when} ${step}`);
    await store.purchaseNextTier(0,'fault-purchase');assert.equal((await store.getProfile()).ownedTiers[0],1);assert.equal(mem.snapshot().gold,200);assert.equal(mem.snapshot().records.has(122),false);
  }
});
test('confirmed new profile clears extension and battle sidecar, not original records or gold',async()=>{
  const original=new Uint8Array([1,0,6]);const mem=memoryPort(new Map([[106,original],[121,new Uint8Array([1,2,1])]]),555);const store=Store.createStore(mem.port,options);await store.init();await store.purchaseNextTier(0,'old');
  const oldId=(await store.getProfile()).profileId;const gold=mem.snapshot().gold;const fresh=await store.resetProfile();assert.notEqual(fresh.profileId,oldId);assert.deepEqual(fresh.ownedTiers,[0,0,0,0,0,0]);assert.equal(fresh.completedTransactions.length,0);
  assert.equal(mem.snapshot().records.has(121),false);assert.equal(mem.snapshot().records.has(122),false);assert.deepEqual(mem.snapshot().records.get(106),original);assert.equal(mem.snapshot().gold,gold);
});
test('all eighteen SPEC prices and receipts survive a complete upgrade history',async()=>{
  const prices=[[800,2200,5000],[700,2000,4500],[900,2500,5500],[800,2200,4800],[1200,3000,6000],[600,1800,4000]];
  const mem=memoryPort(new Map(),100000),store=Store.createStore(mem.port,options);let spent=0;
  for(let item=0;item<6;item++)for(let tier=1;tier<=3;tier++){
    const receipt=await store.purchaseNextTier(item,`item-${item}-tier-${tier}`);spent+=prices[item][tier-1];assert.equal(receipt.listedPrice,prices[item][tier-1]);assert.equal(receipt.tier,tier);
  }
  mem.restart();const restored=Store.createStore(mem.port,options),profile=await restored.init();assert.deepEqual(profile.ownedTiers,[3,3,3,3,3,3]);assert.equal(profile.completedTransactions.length,18);assert.equal(mem.snapshot().gold,100000-spent);
  const old=await restored.purchaseNextTier(0,'item-0-tier-1');assert.equal(old.replayed,true);assert.equal(mem.snapshot().gold,100000-spent);
});
test('unexpected gold during recovery fails closed without overwriting the conflicting save',async()=>{
  const mem=memoryPort();let store=Store.createStore(mem.port,options);await store.init();mem.arm(2,'after');await assert.rejects(store.purchaseNextTier(0,'conflict'),/Power loss/);mem.restart();
  await mem.port.setGold(333);await mem.port.flush();const before=mem.snapshot();store=Store.createStore(mem.port,options);await assert.rejects(store.init(),error=>error.code==='RECOVERY_CONFLICT');
  assert.equal(mem.snapshot().gold,333);assert.deepEqual(mem.snapshot().records,before.records);
});
test('every interrupted reset recovers to either the original profile or the complete new one',async()=>{
  for(const when of ['before','after'])for(let step=1;step<=7;step++){
    const mem=memoryPort();let store=Store.createStore(mem.port,options);await store.purchaseNextTier(0,'prior');const old=await store.getProfile();const gold=mem.snapshot().gold;
    mem.arm(step,when);await assert.rejects(store.resetProfile(),/Power loss/);mem.restart();store=Store.createStore(mem.port,options);const profile=await store.init();
    assert.ok((profile.profileId===old.profileId&&profile.ownedTiers[0]===1)||(profile.profileId!==old.profileId&&profile.ownedTiers.every(tier=>tier===0)),`${when} ${step}`);
    assert.equal(mem.snapshot().gold,gold);assert.equal(mem.snapshot().records.has(122),false);
  }
});
module.exports={memoryPort};
