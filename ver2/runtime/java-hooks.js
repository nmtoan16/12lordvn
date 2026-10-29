'use strict';
(() => {
  const hooks=new Map(), replacements=new Map(), frames=new WeakMap(), counts={};
  const fields=new Map();
  function bind(method){
    if(!method)return method;
    method.loanHook=hooks.has(method.implKey);
    const fn=replacements.get(method.implKey);
    if(fn){method.fn=fn;method.state=1;J2ME.linkedMethods[method.id]=fn;}
    return method;
  }
  const original=J2ME.ClassInfo.prototype.getMethodByIndex;
  J2ME.ClassInfo.prototype.getMethodByIndex=function(index){return bind(original.call(this,index));};
  function update(key){for(const m of J2ME.methodIdToMethodInfoMap)if(m?.implKey===key)bind(m);}
  function observe(key,callbacks){
    if(replacements.has(key))throw Error('Hook conflict: '+key);
    const list=hooks.get(key)||[];list.push(callbacks);hooks.set(key,list);update(key);
  }
  function replace(key,fn){
    if(hooks.has(key)||replacements.has(key))throw Error('Hook conflict: '+key);
    replacements.set(key,fn);ReplaceMethod[key]=fn;update(key);
  }
  function enter(thread,method,fp,lp){
    let active=frames.get(thread);if(!active){active=new Map();frames.set(thread,active);}
    if(active.has(fp)){const call=active.get(fp);return call.skipReturn?call:null;}
    const args=Array.from(i32.subarray(lp,lp+method.argumentSlots));
    const call={key:method.implKey,thread,fp,args,data:{},setArg(index,value){i32[lp+index]=value;args[index]=value;}};
    active.set(fp,call);counts[call.key]=(counts[call.key]||0)+1;
    for(const h of hooks.get(call.key)||[])h.before?.(call);
    return call.skipReturn?call:null;
  }
  function leave(thread,method,fp,value){
    const active=frames.get(thread),call=active?.get(fp);
    if(!call)return value;
    active.delete(fp);call.result=value;
    for(const h of hooks.get(call.key)||[])h.after?.(call);
    return call.result;
  }
  // An exceptional exit has no successful return and must not run after hooks.
  // Keep suspended frames intact; the interpreter calls this only when unwinding.
  function discard(thread,fp){frames.get(thread)?.delete(fp);}
  function offset(cls,name,sig,isStatic=false){
    const key=[cls,name,sig,isStatic].join('|');
    if(!fields.has(key)){
      const f=CLASSES.getClass(cls).getFieldByName(J2ME.toUTF8(name),J2ME.toUTF8(sig),isStatic);
      if(!f)throw Error('Missing Java field: '+key);fields.set(key,f.byteOffset);
    }
    return fields.get(key);
  }
  function get(addr,cls,name,sig='I',isStatic=false){return i32[(addr+offset(cls,name,sig,isStatic))>>2];}
  function set(addr,cls,name,sig,value,isStatic=false){i32[(addr+offset(cls,name,sig,isStatic))>>2]=value;}
  function stat(cls,name,sig){const c=CLASSES.getClass(cls);return get($.SA[c.id],cls,name,sig,true);}
  window.LoanJavaHooks={observe,replace,enter,leave,discard,get,set,stat,counts,
    validate(){for(const key of [...hooks.keys(),...replacements.keys()]){const dot=key.indexOf('.'),next=key.indexOf('.',dot+1);const c=CLASSES.getClass(key.slice(0,dot));if(!c.getMethodByNameString(key.slice(dot+1,next),key.slice(next+1)))throw Error('Missing Java method: '+key);}return true;}
  };
})();
