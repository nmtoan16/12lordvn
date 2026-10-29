const {test}=require('node:test'),assert=require('node:assert/strict'),fs=require('node:fs'),vm=require('node:vm');
function harness(){class ClassInfo{getMethodByIndex(i){return this.methods[i];}}const s={window:null,Map,WeakMap,Error,Array,ReplaceMethod:{},J2ME:{ClassInfo,linkedMethods:[],methodIdToMethodInfoMap:[]},i32:new Int32Array(100)};s.window=s;vm.runInNewContext(fs.readFileSync('runtime/java-hooks.js','utf8'),s);return s;}
test('hooks compose, run once across suspended frame entry, preserve arguments and return',()=>{
 const s=harness(),h=s.LoanJavaHooks,events=[],t={},m={id:1,implKey:'a.b.(I)I',argumentSlots:2};
 h.observe(m.implKey,{before(c){events.push('before');c.setArg(1,9);},after(c){events.push('after');c.result+=2;}});
 h.observe(m.implKey,{before(){events.push('second');}});
 const cls=new s.J2ME.ClassInfo();cls.methods=[m];cls.getMethodByIndex(0);assert.equal(m.loanHook,true);
 s.i32[2]=7;s.i32[3]=4;h.enter(t,m,10,2);h.enter(t,m,10,2);assert.equal(s.i32[3],9);
 assert.equal(h.leave(t,m,10,40),42);assert.deepEqual(events,['before','second','after']);
 h.enter(t,m,10,2);h.leave(t,m,10,1);assert.equal(h.counts[m.implKey],2);
});
test('two independent replacements bind cold and never remove each other',()=>{
 const s=harness(),h=s.LoanJavaHooks,a=()=>1,b=()=>2;h.replace('a.a.()I',a);h.replace('b.b.()I',b);
 const cls=new s.J2ME.ClassInfo();cls.methods=[{id:1,implKey:'a.a.()I'},{id:2,implKey:'b.b.()I'}];
 assert.equal(cls.getMethodByIndex(0).fn(),1);assert.equal(cls.getMethodByIndex(1).fn(),2);assert.equal(cls.getMethodByIndex(0).state,1);
 assert.throws(()=>h.replace('a.a.()I',b),/conflict/);
});

test('guarded reference returns preserve zero and reach after callbacks once',()=>{
 const s=harness(),h=s.LoanJavaHooks,thread={},method={id:3,implKey:'display.get.(Ljava/lang/Object;)Ljava/lang/Object;',argumentSlots:1,signatureKinds:[8,8]};
 let after=0;h.observe(method.implKey,{before(c){c.skipReturn=true;c.skipValue=0;},after(c){after++;assert.equal(c.result,0);}});
 const call=h.enter(thread,method,10,2);assert.equal(call.skipValue,0);assert.equal(h.enter(thread,method,10,2),call);
 assert.equal(h.leave(thread,method,10,call.skipValue),0);assert.equal(after,1);
});

test('Java exception unwind discards the aborted hook but preserves a catching or suspended frame',()=>{
 const s=harness(),h=s.LoanJavaHooks,events=[];
 s.release=true;s.FrameType={Interpreter:0};
 const source=fs.readFileSync('runtime/bld/j2me.js','utf8');
 const start=source.indexOf('Thread.prototype.exceptionUnwind = function (e) {');
 const end=source.indexOf('Thread.prototype.classInitAndUnwindCheck',start);
 s.Thread=function(){};
 vm.runInNewContext(source.slice(start,end),s);
 const child={id:1,implKey:'child.run.(I)V',argumentSlots:1,exception_table_length:0};
 const parent={id:2,implKey:'parent.run.()V',argumentSlots:0,exception_table_length:1,getExceptionEntryViewByIndex(){return {start_pc:0,end_pc:10,catch_type:0,handler_pc:9};}};
 s.J2ME.methodIdToMethodInfoMap[1]=child;s.J2ME.methodIdToMethodInfoMap[2]=parent;
 h.observe(child.implKey,{before(c){events.push('child:'+c.args[0]);},after(){events.push('child:after');}});
 h.observe(parent.implKey,{before(){events.push('parent:before');},after(){events.push('parent:after');}});
 const thread=new s.Thread();thread.fp=20;thread.pc=3;
 thread.popFrame=()=>{thread.fp=10;thread.pc=4;};
 s.i32[22]=child.id;s.i32[12]=parent.id;s.i32[2]=7;
 h.enter(thread,parent,10,0);h.enter(thread,child,20,2);
 // Re-entering a yielded frame must not execute its hook a second time.
 h.enter(thread,child,20,2);
 thread.exceptionUnwind({_address:99});
 assert.equal(thread.fp,10);assert.equal(thread.pc,9);assert.equal(s.i32[14],99);
 s.i32[2]=42;h.enter(thread,child,20,2);h.leave(thread,child,20,0);
 h.enter(thread,parent,10,0);h.leave(thread,parent,10,0);
 assert.deepEqual(events,['parent:before','child:7','child:42','child:after','parent:after']);
});
