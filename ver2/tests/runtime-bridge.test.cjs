const test=require('node:test');
const assert=require('node:assert/strict');
const vm=require('node:vm');
const fs=require('node:fs');
const path=require('node:path');

function bridgeHarness(){
  const handlers=new Map(),pending=[];let clock=1000,scheduled=null;
  const parent={postMessage(){}};
  const keys=[];
  class Context{
    constructor(){this.runs=0;this.resumes=0;}
    execute(){this.runs++;}
    resume(){this.resumes++;pending.push(this);Scheduler.processRunningQueue();}
  }
  const Scheduler={processRunningQueue(){
    if(scheduled)return;
    scheduled=()=>{scheduled=null;while(pending.length)pending.shift().execute();};
  }};
  const Native={};
  const window={loanAudioContexts:[],addEventListener(type,fn){handlers.set(type,fn);}};
  const sandbox={window,parent,location:{origin:'http://localhost'},document:{getElementById(){return null;}},
    Native,J2ME:{Context,Scheduler,returnLongValue:value=>value},MIDP:{sendKeyPress:key=>keys.push(['down',key]),sendKeyRelease:key=>keys.push(['up',key])},
    Element:class{},Date:class extends Date{static now(){return clock;}},setInterval(){return 1;},clearInterval(){},setTimeout(){},Blob,
    fs:{},myflushAll(){},console};
  let source=fs.readFileSync(path.resolve('runtime/bridge.js'),'utf8');
  if(process.env.BRIDGE_MUTATION==='1')source=source.replace('if(paused){deferredContexts.add(this);return;}','if(false){deferredContexts.add(this);return;}');
  vm.runInNewContext(source,sandbox,{filename:'runtime/bridge.js'});
  return {Context,keys,Native,
    tick(){const fn=scheduled;scheduled=null;fn?.();},
    advance(ms){clock+=ms;},
    send(data,origin='http://localhost'){handlers.get('message')({origin,source:parent,data:{app:'loan12',...data}});}
  };
}

test('pause freezes an already scheduled Java context and resumes it exactly once',()=>{
  const app=bridgeHarness(),context=new app.Context();
  context.resume(); // A run callback exists before the pause message arrives.
  app.send({type:'pause',paused:true});app.tick();
  assert.equal(context.runs,0,'a previously scheduled callback must not execute Java after pause');
  context.execute(); // Native completions can become runnable during the pause.
  app.send({type:'pause',paused:false});app.tick();
  assert.equal(context.runs,1);
  assert.equal(context.resumes,2,'the deferred context must be enqueued only once on resume');
  app.send({type:'pause',paused:false});app.tick();assert.equal(context.runs,1);
});

test('the Java clock excludes paused wall time, including repeated pause messages',()=>{
  const app=bridgeHarness(),time=()=>app.Native['java/lang/System.currentTimeMillis.()J']();
  assert.equal(time(),1000);app.send({type:'pause',paused:true});app.advance(5000);
  app.send({type:'pause',paused:true});assert.equal(time(),1000);
  app.send({type:'pause',paused:false});assert.equal(time(),1000);
  app.advance(1000);assert.equal(time(),2000);
});

test('pause releases held keys and unrelated origins cannot inject input',()=>{
  const app=bridgeHarness();
  app.send({type:'key',code:-5,down:true},'https://unrelated.example');assert.equal(app.keys.length,0);
  app.send({type:'key',code:-5,down:true});app.send({type:'pause',paused:true});
  assert.deepEqual(app.keys,[['down',-5],['up',-5]]);
  app.send({type:'key',code:-5,down:true});assert.equal(app.keys.length,2);
});
