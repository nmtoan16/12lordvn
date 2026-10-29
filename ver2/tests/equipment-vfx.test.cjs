const {test}=require('node:test');
const assert=require('node:assert/strict');
const {EffectPool}=require('../runtime/equipment/vfx.js');
test('floating labels represent actual damage, heal and block, without zero-hit damage',()=>{
  const pool=new EffectPool();pool.add({type:'damage',amount:8});pool.add({type:'heal',amount:12,side:0});pool.add({type:'block',amount:7});pool.add({type:'damage',amount:0});
  assert.deepEqual(pool.text.map(x=>x.label),['−8','+12','Chắn 7']);
});
test('effect pools stay bounded after 1000 hits and casts',()=>{
  const pool=new EffectPool();for(let i=0;i<1000;i++){pool.add({type:'damage',amount:i});pool.add({type:'cast',skillId:i%9},{gold:true,awakened:true});}
  assert.equal(pool.text.length,12);assert.equal(pool.particles.length,24);pool.clear();assert.equal(pool.text.length+pool.particles.length,0);
});
test('pause freezes visual age and resumed time expires effects',()=>{
  const pool=new EffectPool();pool.add({type:'damage',amount:20});pool.add({type:'cast',skillId:8},{awakened:true});
  pool.step(1000,true);assert.equal(pool.text[0].age,0);assert.equal(pool.particles[0].age,0);
  for(let i=0;i<20;i++)pool.step(50);assert.equal(pool.text.length+pool.particles.length,0);
});
test('only awakened casts create particles; reduced mode and gold remain bounded',()=>{
  const pool=new EffectPool();pool.add({type:'cast',skillId:0});assert.equal(pool.particles.length,0);
  pool.add({type:'cast',skillId:0},{awakened:true});assert.equal(pool.particles.length,6);
  pool.clear();pool.reduced=true;pool.add({type:'cast',skillId:0},{awakened:true,gold:true});assert.equal(pool.particles.length,3);
});
