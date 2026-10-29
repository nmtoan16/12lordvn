'use strict';
const {test}=require('node:test');
const assert=require('node:assert/strict');
const {createHash}=require('node:crypto');
const Store=require('../runtime/equipment/store.js');
const Loadout=require('../runtime/equipment/loadout.js');
const Battle=require('../runtime/equipment/battle-save.js');
const {CombatSession}=require('../runtime/equipment/combat.js');
const snapshot=new Uint8Array([1,0,6,255,0,24]);
function context(){return {battleId:'battle-one',profileId:'profile-one',loadout:Loadout.snapshot([1,2,3,0,0,0],[0,2]),state:{barrierHp:43,emergencyUsed:true,foodRemainder:0.75,castSequence:8,processedTokens:['cast-7','damage-8'],bonusManaApplied:true}};}
test('snapshot fingerprint matches SHA-256 including empty, partial and multi-block input',()=>{
  for(const bytes of [new Uint8Array(),new TextEncoder().encode('abc'),snapshot,new Uint8Array(10000).map((_,i)=>i)])assert.equal(Battle.fingerprintSnapshot(bytes),createHash('sha256').update(bytes).digest('hex'));
});
test('sidecar round-trip preserves frozen loadout and once-only battle state',()=>{
  const source=context(),sidecar=Battle.createSidecar(snapshot,source),decoded=Battle.decodeSidecar(Battle.encodeSidecar(sidecar));
  assert.deepEqual(decoded,sidecar);source.state.barrierHp=1000;assert.equal(decoded.state.barrierHp,43);
  assert.deepEqual(decoded.loadout.activeTiers,[1,0,3,0,0,0]);assert.equal(decoded.state.emergencyUsed,true);assert.equal(decoded.state.bonusManaApplied,true);
});
test('legacy/mismatched resume never reapplies equipment and corrupt future sidecars are not overwritten',async()=>{
  const data=new Map([[106,snapshot]]);const writes=[];const port={async read(key){return data.get(key)??null;},async write(key,bytes){writes.push(key);if(bytes===null)data.delete(key);else data.set(key,bytes);},async flush(){}};
  const manager=Battle.createBattleSave(port);assert.equal((await manager.restore(snapshot)).mode,'legacy');assert.deepEqual(writes,[]);
  await manager.save(snapshot,context());assert.deepEqual(writes,[121]);assert.deepEqual(data.get(106),snapshot);
  const saved=data.get(121);const changed=new Uint8Array(snapshot);changed[0]^=1;assert.equal((await manager.restore(changed)).mode,'mismatch');assert.equal((await manager.restore(snapshot)).mode,'restored');
  const corrupt=new Uint8Array(saved);corrupt[corrupt.length-1]^=1;data.set(121,corrupt);await assert.rejects(manager.restore(snapshot),error=>error.code==='CHECKSUM');assert.deepEqual(data.get(121),corrupt);
  const future=new Uint8Array(saved);future[4]=2;data.set(121,future);await assert.rejects(manager.restore(snapshot),error=>error.code==='SCHEMA_VERSION');
  assert.deepEqual(writes,[121]);
});
test('rejects invalid active tiers, huge/deep/non-JSON state and forged payload with valid CRC',()=>{
  const sidecar=Battle.createSidecar(snapshot,context());sidecar.loadout.activeTiers[1]=2;assert.throws(()=>Battle.encodeSidecar(sidecar),error=>error.code==='BATTLE_LOADOUT');
  const invalid=context();invalid.state.barrierHp=-1;assert.throws(()=>Battle.createSidecar(snapshot,invalid));
  const deep=context();deep.state={};for(let i=0;i<12;i++)deep.state={next:deep.state};assert.throws(()=>Battle.createSidecar(snapshot,deep));
  const fn=context();fn.state.callback=()=>{};assert.throws(()=>Battle.createSidecar(snapshot,fn));
  const forged=Battle.createSidecar(snapshot,context());forged.snapshotFingerprint='bad';assert.throws(()=>Battle.decodeSidecar(Store.encodeRecord('battle',forged)));
});
function combatContext(){
  const result=context(),combat=new CombatSession({tiers:result.loadout.activeTiers,fighters:[{maxHp:1000,maxMana:36,maxFood:1000},{maxHp:800,maxMana:36,maxFood:800}]});
  combat.start();combat.cast({id:'cast:1',side:0,slot:0,skillId:0,payCost:false});combat.damage({id:'hit:2',side:1,amount:50});
  result.state={combat:combat.snapshot(),sequence:2,action:{id:'action:1',side:0},cast:null};return result;
}
test('combat sidecar validates actual engine snapshots and rejects future or malformed nested combat before import',()=>{
  const valid=combatContext();assert.deepEqual(Battle.decodeSidecar(Battle.encodeSidecar(Battle.createSidecar(snapshot,valid))).state,valid.state);
  const changes=[
    state=>state.combat.version=2,
    state=>state.combat.tiers[0]=2,
    state=>state.combat.fighters[0].hp=-1,
    state=>state.combat.fighters[0].mana=1000,
    state=>state.combat.fighters[1].immune='yes',
    state=>state.combat.barrier=1001,
    state=>state.combat.started=1,
    state=>state.combat.foodCarry=4,
    state=>state.combat.results=[['damage:1',{hpLoss:-1}]],
    state=>state.combat.results.find(([key])=>key.startsWith('cast:'))[1].burn=-1,
    state=>state.combat.procs=['burn-cast:1','burn-cast:1'],
    state=>state.combat.casts=[['cast:1',{side:2,slot:0,skillId:0,lifeStealPercent:0}]],
    state=>state.combat.futureField=true,
    state=>state.sequence=-1,
    state=>state.action.side=2
  ];
  for(const change of changes){const invalid=combatContext();change(invalid.state);assert.throws(()=>Battle.createSidecar(snapshot,invalid));}
});
