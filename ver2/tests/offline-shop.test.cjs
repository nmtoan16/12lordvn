'use strict';
const {test}=require('node:test');
const assert=require('node:assert/strict');
const fs=require('node:fs');
const vm=require('node:vm');
const source=()=>fs.readFileSync('runtime/offline-shop.js','utf8');
const names={
  payload:'com/sun/midp/io/j2me/sms/TextObject.setPayloadText.(Ljava/lang/String;)V',
  open:'com/sun/midp/io/j2me/sms/Protocol.open0.(Ljava/lang/String;II)I',
  close:'com/sun/midp/io/j2me/sms/Protocol.close0.(III)I',
  wait:'com/sun/midp/io/j2me/sms/Protocol.waitUntilMessageAvailable0.(II)I',
  receive:'com/sun/midp/io/j2me/sms/Protocol.receive0.(IIILcom/sun/midp/io/j2me/sms/Protocol$SMSPacket;)I',
  send:'com/sun/midp/io/j2me/sms/Protocol.send0.(IILjava/lang/String;II[B)I'
};
function harness(){
  let next=1000,upstreamCalls=0;const arrays=new Map(),strings=new Map(),packets=new Map(),scheduled=[],events=[];
  class ClassInfo{constructor(name,methods){this.name=name;this.methods=methods;}getClassNameSlow(){return this.name;}getMethodByIndex(index){return this.methods[index];}}
  const sandbox={Native:{[names.send](){upstreamCalls++;}},ReplaceMethod:{},i32:new Int32Array(8192),console,TextDecoder,Uint8Array,Map,Set,Promise,Date,Object,Number,String,Math,Error,window:null,location:{origin:'http://localhost'},parent:{postMessage(m){events.push(m);}},setTimeout(fn){scheduled.push(fn);return scheduled.length;},clearTimeout(){},asyncImpl(type,promise){sandbox.lastAsync=promise;},getHandle(addr){return packets.get(addr);},$:{newIOException(message){return new Error(message);}},J2ME:{fromStringAddr:addr=>strings.get(addr),newByteArray(length){const addr=next++;arrays.set(addr,new Int8Array(length));return addr;},getArrayFromAddr:addr=>arrays.get(addr),getClassInfo(){return {getFieldByName(){return {byteOffset:16};}};},toUTF8:s=>s}};
  Object.assign(sandbox.J2ME,{ClassInfo,linkedMethods:[],methodIdToMethodInfoMap:[]});
  sandbox.window=sandbox;vm.createContext(sandbox);vm.runInContext(fs.readFileSync('runtime/java-hooks.js','utf8'),sandbox);vm.runInContext(source(),sandbox);
  return {sandbox,arrays,events,scheduled,names,upstreamCalls:()=>upstreamCalls,string(text){const a=next++;strings.set(a,text);return a;},payload(text,addr=200){sandbox.ReplaceMethod[names.payload](addr,this.string(text));return arrays.get(sandbox.i32[(addr+16)>>2]);},async tick(){while(scheduled.length)scheduled.shift()();await Promise.resolve();},packet(addr=800){const p={};packets.set(addr,p);return p;}};
}
test('retains UTF-16BE buffer and uses original callback flow for shop/lives',()=>{
  const h=harness();const data=h.payload('TESTGAME3 v2 a2 o1 d3 pnull rshop "mWeb" e123');
  assert.equal(new TextDecoder('utf-16be').decode(data),'TESTGAME3 v2 a2 o1 d3 pnull rshop "mWeb" e123');
  assert.equal(h.scheduled.length,0);assert.equal(h.events.length,1);assert.match(h.events[0].message,/miễn phí/i);
});
test('binds the first cold interpreted payload method, without modifying other Java methods',()=>{
  const h=harness();const method={id:21,implKey:names.payload,state:0};const other={id:22,implKey:'other.run.()V',state:0};const ClassInfo=h.sandbox.J2ME.ClassInfo;
  new ClassInfo('other',[other]).getMethodByIndex(0);assert.equal(other.state,0);
  const resolved=new ClassInfo('com/sun/midp/io/j2me/sms/TextObject',[method]).getMethodByIndex(0);assert.equal(resolved.state,1);assert.equal(resolved.fn,h.sandbox.ReplaceMethod[names.payload]);assert.equal(h.sandbox.J2ME.linkedMethods[21],resolved.fn);
});
test('only a bounded valid collectible request queues a local acknowledgment',async()=>{
  const h=harness();h.payload('OTHER v2 a4 rreco10');h.payload('TESTGAME3 a4 rreco3');assert.equal(h.scheduled.length,0);
  const req='TESTGAME3 v2 a4 o0 d3 pnull rreco3 "mWeb" e124';h.payload(req);assert.equal(h.scheduled.length,1);
  const conn=h.sandbox.Native[names.open](0,0,0,19003);h.sandbox.Native[names.wait](0,19003,conn);const waiter=h.sandbox.lastAsync;
  await h.tick();assert.ok(await waiter>0);
  let notifiedAgain=false;h.sandbox.Native[names.wait](0,19003,conn);h.sandbox.lastAsync.then(()=>notifiedAgain=true);await Promise.resolve();assert.equal(notifiedAgain,false);
  const packet=h.packet();assert.ok(h.sandbox.Native[names.receive](0,19003,0,conn,800)>0);
  const reply=new TextDecoder('utf-16be').decode(h.arrays.get(packet.message));assert.match(reply,/^TESTGAME3 a1 /);assert.match(reply,/rreco3/);assert.match(reply,/không đổi thưởng thật/);assert.equal(packet.messageType,2);
});
test('repeated identical purchases each receive acknowledgment; a following send is the same operation',async()=>{
  const h=harness();const req='TESTGAME3 v2 a4 o0 d3 pnull rreco3 "mWeb" e124';const bytes=h.payload(req);const byteAddr=[...h.arrays].find(([,b])=>b===bytes)[0];
  h.sandbox.Native[names.send](0,1,2,h.string('8531'),-1,0,byteAddr);assert.equal(h.scheduled.length,1);
  h.payload(req);assert.equal(h.scheduled.length,2);await h.tick();assert.equal(h.sandbox.loanOfflineTransactions.stats().acknowledgments,2);
  assert.equal(h.sandbox.loanOfflineTransactions.stats().transactions,2);assert.equal(h.upstreamCalls(),0);
});
test('ordinary collectible uses original random award branch and native SMS never reaches upstream',async()=>{
  const h=harness();const conn=h.sandbox.Native[names.open](0,0,0,19003);const req='TESTGAME3 v2 a4 o0 d3 pnull rreco0 "mWeb" e125';h.payload(req);await h.tick();const packet=h.packet();assert.ok(h.sandbox.Native[names.receive](0,19003,0,conn,800)>0);
  assert.match(new TextDecoder('utf-16be').decode(h.arrays.get(packet.message)),/^TESTGAME3 a4 /);
  const bytes=h.payload('TESTGAME3 v2 a2 rshop');const byteAddr=[...h.arrays].find(([,b])=>b===bytes)[0];assert.ok(h.sandbox.Native[names.send](0,conn,2,h.string('8531'),-1,0,byteAddr)>0);assert.equal(h.upstreamCalls(),0);
});
test('unknown or malformed native SMS is blocked; closing releases pending listener',async()=>{
  const h=harness();const conn=h.sandbox.Native[names.open](0,0,0,19003);const bytes=h.payload('not-a-game-request');const byteAddr=[...h.arrays].find(([,b])=>b===bytes)[0];assert.throws(()=>h.sandbox.Native[names.send](0,conn,2,h.string('0900000000'),-1,0,byteAddr),/offline|cục bộ/i);assert.equal(h.upstreamCalls(),0);assert.equal(h.events.length,0);
  h.sandbox.Native[names.wait](0,19003,conn);const waiter=h.sandbox.lastAsync;h.sandbox.Native[names.close](0,19003,conn,1);assert.equal(await waiter,0);
});
test('follows SQMidlet actual SUQUANOLA prefix and inbound port 17530',async()=>{
  const h=harness();const conn=h.sandbox.Native[names.open](0,0,0,17530);h.sandbox.Native[names.wait](0,17530,conn);const waiter=h.sandbox.lastAsync;
  h.payload('SUQUANOLA v2 a4 o0 dOLA p36 rreco9 "mWeb" e126');await h.tick();assert.ok(await waiter>0);const packet=h.packet();assert.ok(h.sandbox.Native[names.receive](0,17530,0,conn,800)>0);
  assert.match(new TextDecoder('utf-16be').decode(h.arrays.get(packet.message)),/^SUQUANOLA a1 /);assert.equal(packet.port,17530);
});
