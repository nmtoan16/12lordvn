'use strict';
(() => {
  const H=LoanJavaHooks,E=LoanEquipment,VM=LoanVM;
  const SHOP='com/mic/smsgame/suquan/q',BATTLE='com/mic/smsgame/suquan/ab',MAP='com/mic/smsgame/suquan/aa';
  const listeners=new Set(),combatListeners=new Set();
  let profile={ownedTiers:[0,0,0,0,0,0],equippedIds:[]},gold=0,ready=false,error=null,store=null,busy=false;
  function staticField(cls,name,sig){const c=CLASSES.getClass(cls),addr=VM.runtime?.SA[c.id];return addr?H.get(addr,cls,name,sig,true):0;}
  function javaName(addr){return addr?J2ME.fromUTF8(J2ME.getClassInfo(addr).utf8Name):null;}
  function current(){
    if(!VM.runtime)return {};
    const canvas=staticField('cz','a','Lcz;');if(!canvas)return {};
    const router=H.get(canvas,'cz','e','Lbv;');if(!router)return {};
    const address=H.get(router,'bv','b','Laj;'),dialog=H.get(router,'bv','d','Lae;'),screen=javaName(address);
    return {screen,address,dialog:javaName(dialog),dialogAddress:dialog,router,mapPhase:screen===MAP?H.get(address,MAP,'f'):null};
  }
  function state(){
    const s=current(),battle=window.LoanCombatBridge?.state?.()||null;
    return {...s,ready,error,busy,gold,profile:JSON.parse(JSON.stringify(profile)),battle,paused:Boolean(window.loanPaused),skillIds:battle?.skillIds,
      opponent:window.LoanCombatBridge?.opponent?.(),variant:'ver2'};
  }
  function notify(){for(const fn of listeners)fn(state());}
  function refreshShop(){const s=current();if(s.screen===SHOP){const a=H.get(s.address,SHOP,'E','[I');if(a)J2ME.getArrayFromAddr(a)[2]=gold;}}
  async function run(fn){
    if(!ready)throw Error(error||'Đang đọc tiến trình…');if(busy)throw Error('Giao dịch đang lưu.');
    busy=true;notify();try{const result=await fn();profile=await store.getProfile();gold=await VM.readGold();refreshShop();return result;}
    catch(e){if(e.requiresReload)LoanV2.reportError(e);throw e;}
    finally{busy=false;notify();}
  }
  window.LoanV2={catalog:E.catalog,state,subscribe(fn){listeners.add(fn);return()=>listeners.delete(fn);},
    onCombat(fn){combatListeners.add(fn);return()=>combatListeners.delete(fn);},emitCombat(event){for(const fn of combatListeners)fn(event);},
    current,staticField,notify,profile:()=>profile,store:()=>store,port:VM,
    purchase(id){const transactionId=E.store.defaultProfile().profileId;return run(()=>store.purchaseNextTier(id,transactionId));},
    equip(id,replaceId){return run(async()=>{
      if(current().screen===BATTLE)throw Error('Trang bị đã chốt cho trận này.');
      const ids=[...profile.equippedIds];if(ids.includes(id))return;
      if(!profile.ownedTiers[id])throw Error('Chưa sở hữu trang bị.');
      if(ids.length===3){const at=ids.indexOf(replaceId);if(at<0)throw Error('Chọn một món để thay.');ids[at]=id;}else ids.push(id);
      return store.setLoadout(ids,profile.revision);
    });},
    unequip(id){return run(()=>{if(current().screen===BATTLE)throw Error('Trang bị đã chốt cho trận này.');return store.setLoadout(profile.equippedIds.filter(x=>x!==id),profile.revision);});},
    flush:async()=>{if(store)await store.getProfile();await window.LoanCombatBridge?.flush?.();await VM.flush();},
    reportError(e){error=e.message||String(e);ready=false;if(e.requiresReload)window.dispatchEvent(new CustomEvent('loan-storage-fault',{detail:error}));console.error('VER2',e);notify();}
  };
  window.addEventListener('loan-vm-ready',async()=>{
    try{H.validate();store=E.store.createStore(VM,{priceFor:(id,tier)=>E.catalog[id].tiers[tier].price});await store.init();profile=await store.getProfile();gold=await VM.readGold();await window.LoanCombatBridge?.loadSidecar?.();ready=true;notify();}
    catch(e){LoanV2.reportError(e);}
  });
  // Observe inventory writes so the visible balance also tracks original loot,
  // consumables and simulated SMS purchases, without polling RMS every frame.
  for(const sig of ['(Ldr;I[B)V','(Ldr;I[BII)V'])H.observe('dr.a.'+sig,{before(c){
    if(c.args[1]!==9||!c.args[2])return;const bytes=J2ME.getArrayFromAddr(c.args[2]),off=sig==='(Ldr;I[BII)V'?c.args[3]:0;
    if(bytes.length>=off+12){gold=new DataView(bytes.buffer,bytes.byteOffset+off,bytes.length-off).getInt32(8);}
  }});
  H.observe(SHOP+'.b.(II)V',{before(c){
    if(c.args[2]!==-4)return;
    const addr=c.args[0],id=H.get(addr,SHOP,'z'),prices=J2ME.getArrayFromAddr(H.get(addr,SHOP,'w','[I')),inventory=J2ME.getArrayFromAddr(H.get(addr,SHOP,'E','[I'));
    if(prices[id]>0){c.data.purchase={inventory,gold:inventory[2],price:prices[id]};inventory[2]=Math.max(inventory[2],prices[id]);}
  },after(c){if(c.data.purchase){const p=c.data.purchase;p.inventory[2]=gold=Math.max(0,p.gold-p.price);notify();}}});
  H.observe('com/mic/smsgame/suquan/f.a.()V',{after(){
    ready=false;
    Promise.resolve().then(async()=>{if(!store)return;await store.resetProfile();profile=await store.getProfile();gold=await VM.readGold();window.LoanCombatBridge?.reset?.();ready=true;notify();}).catch(LoanV2.reportError);
  }});
  setInterval(()=>{if(ready)notify();},200);
})();
