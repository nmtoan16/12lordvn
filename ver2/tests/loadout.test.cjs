'use strict';
const {test}=require('node:test');
const assert=require('node:assert/strict');
const Loadout=require('../runtime/equipment/loadout.js');

test('accepts all 42 unordered loadouts of zero to three owned items',()=>{
  let count=0;
  for(let mask=0;mask<64;mask++){
    const ids=Array.from({length:6},(_,id)=>id).filter(id=>mask&(1<<id));
    if(ids.length>3)continue;
    assert.deepEqual(Loadout.validateLoadout(ids,[1,2,3,1,2,3]),ids);count++;
  }
  assert.equal(count,42);
});
test('rejects duplicates, fourth item, unowned items and invalid tiers',()=>{
  const tiers=[1,0,3,1,2,3];
  for(const ids of [[0,0],[0,2,3,4],[1],[-1],[6],[0.5]])assert.throws(()=>Loadout.validateLoadout(ids,tiers));
  for(const invalid of [[0,0],[0,0,0,0,0,4],[0,0,0,0,0,-1]])assert.throws(()=>Loadout.validateOwnedTiers(invalid));
});
test('fourth item requires explicit replacement and battle snapshot stays frozen',()=>{
  const tiers=[1,2,3,1,2,3];
  assert.throws(()=>Loadout.equip(tiers,[0,1,2],3),error=>error.code==='LOADOUT_FULL');
  assert.deepEqual(Loadout.equip(tiers,[0,1,2],3,1),[0,3,2]);
  assert.deepEqual(Loadout.unequip(tiers,[0,1,2],1),[0,2]);
  const snapshot=Loadout.snapshot(tiers,[0,2]);tiers[0]=3;
  assert.deepEqual(snapshot.activeTiers,[1,0,3,0,0,0]);assert.deepEqual(snapshot.levelsAtStart,[1,2,3,1,2,3]);
  assert.throws(()=>snapshot.activeTiers[0]=3,TypeError);
});
