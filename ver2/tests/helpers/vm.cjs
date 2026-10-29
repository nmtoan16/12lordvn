const {expect,test}=require('@playwright/test');
const fs=require('node:fs');
const path=require('node:path');
const bootObservations=new WeakMap();
function observeBoot(page){
  if(bootObservations.has(page))return bootObservations.get(page);
  const state={console:[],errors:[],requests:new Map(),navigations:[],failedRequests:[]};
  function retain(list,value){list.push(value);if(list.length>160)list.shift();}
  page.on('console',message=>retain(state.console,{at:Date.now(),type:message.type(),text:message.text().slice(0,1500)}));
  page.on('pageerror',error=>retain(state.errors,{at:Date.now(),stack:error.stack}));
  page.on('request',request=>state.requests.set(request,{at:Date.now(),url:request.url(),type:request.resourceType()}));
  page.on('requestfinished',request=>state.requests.delete(request));
  page.on('requestfailed',request=>{state.requests.delete(request);retain(state.failedRequests,{url:request.url(),error:request.failure()});});
  page.on('framenavigated',frame=>retain(state.navigations,{at:Date.now(),url:frame.url()}));
  bootObservations.set(page,state);return state;
}
async function diagnoseBoot(page,frame,error){
  const observed=observeBoot(page),safe=promise=>Promise.race([promise.catch(error=>({captureError:error.message})),new Promise(resolve=>setTimeout(()=>resolve({captureError:'Diagnostic evaluation timed out'}),1500))]);
  const host=await safe(page.evaluate(()=>({url:location.href,status:document.getElementById('status')?.textContent,coverHidden:document.getElementById('cover')?.hidden,startLabel:document.getElementById('start')?.textContent,frameSrc:document.getElementById('game')?.getAttribute('src')})));
  const runtime=frame?await safe(frame.evaluate(async()=>{
    const safe=fn=>{try{return fn();}catch(error){return {error:error.message};}};
    const statuses=async values=>Promise.all((values||[]).map((value,index)=>Promise.race([Promise.resolve(value).then(()=>({index,state:'resolved'}),error=>({index,state:'rejected',error:String(error)})),new Promise(resolve=>setTimeout(()=>resolve({index,state:'pending'}),0))])));
    const stack=context=>safe(()=>{const frames=[];let fp=context.nativeThread.fp;for(let count=0;count<12&&fp>0;count++){const packed=i32[fp+2],method=J2ME.methodIdToMethodInfoMap[packed&268435455];frames.push({fp,type:packed>>>28,method:method?.implKey,callerPC:i32[fp]});const caller=i32[fp+1];if(caller===fp)break;fp=caller;}return frames;});
    const runtimes=safe(()=>Array.from(window.J2ME?.RuntimeTemplate?.all||[]).map(runtime=>({id:runtime.id,status:runtime.status,contexts:Array.from(runtime.allCtxs||[]).map(context=>({id:context.id,thread:context.threadAddress,stack:stack(context),pc:context.nativeThread?.pc,fp:context.nativeThread?.fp,nativeFrames:context.nativeThread?.nativeFrameCount}))})));
    return {url:location.href,readyState:document.readyState,paused:window.loanPaused,loaded:window.isLoadJarFinished,isIndex:window.isIndex,builtinObjectReady:Boolean(window.CLASSES?.java_lang_Object),jarNames:safe(()=>window.JARStore?.getjars().map(jar=>({name:jar.name||jar.jarName||jar[0],keys:Object.keys(jar)}))),config:safe(()=>({main:config.main,midletClassName:config.midletClassName,jars:config.jars,localjar:config.localjar})),state:safe(()=>window.LoanV2?.state()),startup:window.loanStartup,hookCounts:window.LoanJavaHooks?.counts,runtimes,loading:await statuses(window.loadingPromises),midletLoading:await statuses(window.loadingMIDletPromises),canvas:safe(()=>({width:document.getElementById('canvas')?.width,height:document.getElementById('canvas')?.height})),databaseNames:await safe(()=>indexedDB.databases())};
  })):null;
  const diagnostic={at:new Date().toISOString(),error:error.stack||String(error),host,runtime,frames:page.frames().map(frame=>frame.url()),pendingRequests:[...observed.requests.values()],failedRequests:observed.failedRequests,console:observed.console,consoleErrors:observed.console.filter(message=>message.type==='error'),errors:observed.errors,navigations:observed.navigations};
  let destination;
  try{destination=test.info().outputPath('startup-diagnostics.json');}catch{destination=path.resolve('analysis/verification/ver2',`startup-diagnostics-${Date.now()}.json`);}
  fs.mkdirSync(path.dirname(destination),{recursive:true});fs.writeFileSync(destination,JSON.stringify(diagnostic,null,2));
  console.error('STARTUP_DIAGNOSTICS',destination,JSON.stringify({host,runtime,pendingRequests:diagnostic.pendingRequests,errors:diagnostic.errors}));
  return diagnostic;
}
async function readState(frame){
  return frame.evaluate(()=>{
    function field(address,className,name,signature,isStatic=false){
      const cls=CLASSES.getClass(className),field=cls.getFieldByName(J2ME.toUTF8(name),J2ME.toUTF8(signature),isStatic);
      return i32[(address+field.byteOffset)>>2];
    }
    if(typeof CLASSES==='undefined'||!J2ME.RuntimeTemplate.all)return {};
    // getClass is a loader, not a passive lookup. Loading cz before the builtin
    // JAR initializes java.lang.Object caches incomplete class metadata and
    // can break the later VM boot. Inspect only established runtime metadata.
    if(!window.isLoadJarFinished||!CLASSES.java_lang_Object)return {};
    if(window.LoanVM&&!window.LoanVM.runtime)return {};
    const cls=CLASSES.getClass('cz');
    const runtime=Array.from(J2ME.RuntimeTemplate.all).find(runtime=>runtime.SA[cls.id]);
    if(!runtime)return {};
    const canvas=field(runtime.SA[cls.id],'cz','a','Lcz;',true);if(!canvas)return {};
    const router=field(canvas,'cz','e','Lbv;');if(!router)return {};
    const screen=field(router,'bv','b','Laj;');const dialog=field(router,'bv','d','Lae;');
    const current=screen?J2ME.fromUTF8(J2ME.getClassInfo(screen).utf8Name):null;
    const transition=field(router,'bv','h','Z');
    let menuReady=true;
    if(current==='com/mic/smsgame/suquan/ac'){
      const menu=field(screen,current,'u','Ldv;');
      menuReady=!field(menu,'dv','n','Z');
    }
    let battle=null;
    if(current==='com/mic/smsgame/suquan/ab'){
      const model=field(screen,current,'c','Lcq;'),controller=field(screen,current,'e','Lbu;'),renderer=field(screen,current,'d','Li;');
      const rows=J2ME.getArrayFromAddr(field(model,'cq','e','[[I'));
      const fighters=Array.from(J2ME.getArrayFromAddr(field(model,'cq','f','[Lbn;'))).map(address=>({hp:field(address,'bn','c','I'),mana:field(address,'bn','i','I'),food:field(address,'bn','g','I')}));
      const guide=field(renderer,'i','g','Lw;');
      battle={state:field(controller,'bu','v','I'),turn:field(model,'cq','k','I'),row:field(model,'cq','l','I'),col:field(model,'cq','m','I'),selected:Boolean(field(model,'cq','o','Z')),board:Array.from(rows).slice(2,10).map(row=>Array.from(J2ME.getArrayFromAddr(row)).slice(2,10)),fighters,tutorial:guide?{step:field(guide,'w','k','I'),move:field(guide,'w','c','I'),bubble:Boolean(field(guide,'ds','u','Z'))}:null};
    }
    return {screen:current,screenAddress:screen,transition,menuReady,mapPhase:current==='com/mic/smsgame/suquan/aa'?field(screen,current,'f','I'):null,battle,dialog:dialog?J2ME.fromUTF8(J2ME.getClassInfo(dialog).utf8Name):null,isolates:Array.from(J2ME.RuntimeTemplate.all).length};
  }).catch(()=>({}));
}
async function press(page,key){await page.locator('#game').focus();await page.keyboard.press(key,{delay:120});await page.waitForTimeout(250);}
async function waitScreen(frame,name){await expect.poll(async()=>{const s=await readState(frame);return s.screen===name&&!s.transition&&s.menuReady;},{timeout:25000}).toBe(true);await frame.page().waitForTimeout(150);}
async function start(page,url='./'){
  observeBoot(page);let frame;
  try{
    await page.goto(url);await page.click('#start');
    frame=page.frames().find(f=>f.url().includes('runtime/main.html'))||await new Promise(resolve=>page.on('framenavigated',f=>{if(f.url().includes('runtime/main.html'))resolve(f);}));
    await waitScreen(frame,'v');
    await expect.poll(()=>frame.evaluate(()=>!window.LoanV2||LoanV2.state().ready),{timeout:25000}).toBe(true);
    await press(page,'Enter');await waitScreen(frame,'com/mic/smsgame/suquan/ac');return frame;
  }catch(error){await diagnoseBoot(page,frame,error).catch(diagnosticError=>console.error('STARTUP_DIAGNOSTIC_FAILED',diagnosticError.message));throw error;}
}


module.exports={readState,press,waitScreen,start,observeBoot,diagnoseBoot};
