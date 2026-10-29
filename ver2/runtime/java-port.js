'use strict';
(() => {
  const H=LoanJavaHooks,observed=new Set(),expectedWrites=new Map();
  let durabilityFailure=null;
  const api=window.LoanVM={runtime:null,invoke,read,write,flush,readGold,setGold};
  H.observe('cn.b.()V',{after(c){api.runtime=c.thread.ctx.runtime;window.dispatchEvent(new Event('loan-vm-ready'));}});
  function method(key){const dot=key.indexOf('.'),next=key.indexOf('.',dot+1);return CLASSES.getClass(key.slice(0,dot)).getMethodByNameString(key.slice(dot+1,next),key.slice(next+1));}
  function invoke(key,args=[],convert=value=>value){
    return new Promise((resolve,reject)=>{
      if(!api.runtime){reject(Error('Máy ảo chưa sẵn sàng'));return;}
      if(!observed.has(key)){
        H.observe(key,{after(c){const request=c.thread.ctx.loanRequest;if(request&&c.fp===request.fp){try{request.value=request.convert(c.result);request.done=true;}catch(e){request.error=e;}}}});observed.add(key);
      }
      const mi=method(key);if(!mi){reject(Error('Missing method '+key));return;}
      const previous=typeof $!=='undefined'&&$?.ctx;
      const ctx=new J2ME.Context(api.runtime);
      ctx.setAsCurrentContext();
      ctx.threadAddress=J2ME.allocObject(CLASSES.java_lang_Thread);
      J2ME.setNative(ctx.threadAddress,ctx);
      const thread=J2ME.getHandle(ctx.threadAddress);thread.nativeAlive=true;thread.priority=J2ME.NORMAL_PRIORITY;
      ctx.nativeThread.pushMarkerFrame(J2ME.FrameType.ExitInterpreter);
      ctx.nativeThread.pushFrame(mi);
      let slot=0,index=0;
      if(!mi.isStatic)ctx.nativeThread.frame.setParameter(8,slot++,args[index++]);
      for(let k=1;k<mi.signatureKinds.length;k++){
        const kind=mi.signatureKinds[k],value=args[index++];
        ctx.nativeThread.frame.setParameter(kind,slot++,typeof value==='function'?value():value);
        if(J2ME.isTwoSlot(kind))ctx.nativeThread.frame.setParameter(kind,slot++,args[index++]);
      }
      const request=ctx.loanRequest={fp:ctx.nativeThread.fp,convert,done:false};
      const kill=ctx.kill;
      ctx.kill=function(){kill.call(this);if(request.error)reject(request.error);else if(request.done)resolve(request.value);else reject(Error('Java call failed: '+key));};
      if(previous)previous.setAsCurrentContext();else ctx.clearCurrentContext();
      ctx.start();
    });
  }
  function storage(){const c=CLASSES.getClass('bg');return H.get(api.runtime.SA[c.id],'bg','c','Ldr;',true);}
  async function read(key){return invoke('dr.b.(Ldr;I)[B',[storage(),key],addr=>addr?new Uint8Array(J2ME.getArrayFromAddr(addr)):null);}
  async function write(key,bytes){
    if(durabilityFailure)throw durabilityFailure;
    const expected=bytes===null?null:new Uint8Array(bytes);
    if(expected===null)await invoke('dr.c.(Ldr;I)V',[storage(),key]);
    else await invoke('dr.a.(Ldr;I[B)V',[storage(),key,()=>{const addr=J2ME.newByteArray(expected.length);J2ME.getArrayFromAddr(addr).set(expected);return addr;}]);
    expectedWrites.set(key,expected);
  }
  async function flush(){
    if(durabilityFailure)throw durabilityFailure;
    try {await flushDurable();}
    catch(error){
      // Java may already have discarded dirty buffers. A fresh VM must recover
      // from the committed journal; never let cached AFTER values clear it.
      durabilityFailure=error instanceof Error?error:new Error(String(error));
      durabilityFailure.requiresReload=true;throw durabilityFailure;
    }
  }
  async function flushDurable(){
    const expected=new Map(expectedWrites);
    await invoke('dr.a.(Ldr;)V',[storage()]);
    myflushAll();await new Promise((resolve,reject)=>fs.syncStore(error=>error?reject(error):resolve()));
    if(!expected.size)return;
    // dr/l swallow Java RMS exceptions. Read the committed IndexedDB file,
    // following its active index, rather than trusting dr's in-memory cache.
    const blob=await new Promise((resolve,reject)=>fs.exportStore((value,error)=>error?reject(error):resolve(value)));
    const files=JSON.parse(await blob.text()),parser=LoanEquipment.saveImport;
    let logical;
    try {logical=files[parser.STORE_PATH]?parser.readLogicalRecords(files[parser.STORE_PATH]).logical:new Map();}
    catch(cause){const error=new Error('Không thể kiểm chứng dữ liệu RMS đã lưu.');error.code='RMS_DURABILITY';error.cause=cause;throw error;}
    for(const [key,bytes] of expected){
      const saved=logical.get(key)??null;
      if(bytes===null?saved!==null:!saved||saved.length!==bytes.length||bytes.some((byte,index)=>saved[index]!==byte)){
        const error=new Error('RMS chưa lưu đầy đủ bản ghi '+key+'. Hãy tải lại để phục hồi giao dịch.');error.code='RMS_DURABILITY';error.logicalKey=key;throw error;
      }
    }
    for(const [key,bytes] of expected)if(expectedWrites.get(key)===bytes)expectedWrites.delete(key);
  }
  async function readGold(){const bytes=await read(9);return bytes&&bytes.length>=12?new DataView(bytes.buffer,bytes.byteOffset,bytes.byteLength).getInt32(8):0;}
  async function setGold(value){
    if(!Number.isInteger(value)||value<0||value>2147483647)throw Error('Invalid gold');
    const bytes=await read(9)||new Uint8Array(24);new DataView(bytes.buffer,bytes.byteOffset,bytes.byteLength).setInt32(8,value);await write(9,bytes);
  }
})();
