'use strict';
const {test}=require('node:test'),assert=require('node:assert/strict'),fs=require('node:fs'),vm=require('node:vm'),path=require('node:path');
const {createRequire}=require('node:module');
// Mutate an in-memory copy only: never change the running app or parent source.
function load(file,from,to){let source=fs.readFileSync(file,'utf8');if(from){assert.ok(source.includes(from),'mutation anchor exists');source=source.replace(from,to);}const sandbox={module:{exports:{}},window:{},require:createRequire(path.resolve(file))};vm.runInNewContext(source,sandbox,{filename:file});return {module:sandbox.module.exports,window:sandbox.window};}
function killed(file,from,to,oracle){oracle(load(file));assert.throws(()=>oracle(load(file,from,to)),{name:'AssertionError'});}
test('mutation: allowing four equipped items is killed by the loadout contract',()=>{
 killed('runtime/equipment/loadout.js','ids.length>3','ids.length>4',({module:rules})=>assert.throws(()=>rules.validateLoadout([0,1,2,3],[1,1,1,1,1,1])));
});
test('mutation: removing event deduplication is killed by repeated-hit HP assertion',()=>{
 killed('runtime/equipment/combat.js','if (this._results.has(key)) return this._results.get(key);','',({module:{CombatSession}})=>{const battle=new CombatSession({tiers:[0,0,0,0,0,0],fighters:[{hp:1000,maxHp:1000},{hp:1000,maxHp:1000}]});const hit={id:'same-hit',side:0,source:'sword',amount:100};battle.damage(hit);battle.damage(hit);assert.equal(battle.fighters[1].hp,900);});
});
test('mutation: v1 filesystem namespace is killed by the isolation contract',()=>{
 killed('runtime/ver2-config.js',"fs: 'loan12suquan-fs-ver2'","fs: 'loan12suquan-fs-v1'",({window:{LOAN_VERSION}})=>assert.equal(LOAN_VERSION.fs,'loan12suquan-fs-ver2'));
});
test('mutation: repeating an observer on continuation is killed by the once-per-frame assertion',()=>{
 const file='runtime/java-hooks.js',source=fs.readFileSync(file,'utf8'),anchor='if(active.has(fp))';assert.ok(source.includes(anchor));
 function oracle(code){class ClassInfo{getMethodByIndex(){}}const s={window:null,J2ME:{ClassInfo,linkedMethods:[],methodIdToMethodInfoMap:[]},ReplaceMethod:{},i32:new Int32Array(100)};s.window=s;vm.runInNewContext(code,s);let calls=0;const h=s.LoanJavaHooks,t={},m={implKey:'a.b.()V',argumentSlots:1};h.observe(m.implKey,{before(){calls++;}});h.enter(t,m,10,2);h.enter(t,m,10,2);h.leave(t,m,10,0);assert.equal(calls,1);}
 oracle(source);assert.throws(()=>oracle(source.replace(anchor,'if(false)')),{name:'AssertionError'});
});
