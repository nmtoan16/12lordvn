'use strict';
(() => {
  const origin = location.origin;
  function notify(type, data={}) { if (parent!==window) parent.postMessage({app:'loan12',type,...data},origin); }
  const keys={ArrowUp:-1,ArrowDown:-2,ArrowLeft:-3,ArrowRight:-4,w:-1,s:-2,a:-3,d:-4,Enter:-5,' ':-5,q:-6,e:-7,Escape:-7,'*':42,'#':35};
  for(let n=0;n<10;n++)keys[n]=48+n;
  const held=new Set();
  let muted=false,paused=false,pauseAt=0,pauseOffset=0,storageFault=false;
  // A scheduler callback may already be queued when pause is clicked. Defer at
  // the execution boundary so that callback can drain without running Java or
  // leaving the scheduler's private processQueueScheduled flag stuck true.
  const deferredContexts=new Set();
  const executeContext=J2ME.Context.prototype.execute;
  J2ME.Context.prototype.execute=function(){
    if(paused&&!this.loanRequest){deferredContexts.add(this);return;}
    return executeContext.call(this);
  };
  Native['java/lang/System.currentTimeMillis.()J']=function(){return J2ME.returnLongValue((paused?pauseAt:Date.now())-pauseOffset);};
  function audio(){window.loanAudioMuted=paused||muted;for(const ctx of window.loanAudioContexts||[]){if(ctx.state==='closed')continue;const promise=paused||muted?ctx.suspend():ctx.resume();promise?.catch(()=>{});}}
  function release(){for(const key of held)MIDP.sendKeyRelease(key);held.clear();}
  function key(code,down){if(paused)return;if(window.LoanEquipmentUI?.handleKey(code,down))return;if(down&&window.LoanV2&&!LoanV2.state().ready)return;if(down){audio();if(!held.has(code)){held.add(code);MIDP.sendKeyPress(code);}}else if(held.delete(code))MIDP.sendKeyRelease(code);}
  function flush(){if(!storageFault&&typeof myflushAll==='function')myflushAll();}
  function pause(value){
    if(storageFault&&!value)return;
    if(value===paused)return;release();
    if(value){pauseAt=Date.now();paused=true;flush();}
    else{
      pauseOffset+=Date.now()-pauseAt;paused=false;
      for(const context of deferredContexts)context.resume();
      deferredContexts.clear();
      J2ME.Scheduler.processRunningQueue();
    }
    audio();
    window.loanPaused=paused;window.LoanV2?.notify();
  }
  window.addEventListener('loan-storage-fault',event=>{
    storageFault=true;pause(true);
    notify('storagefault',{message:'Không thể ghi tiến trình. Game đã tạm dừng; hãy tải lại để phục hồi giao dịch. '+event.detail});
  });
  window.addEventListener('message',event=>{
    if(event.origin!==origin||event.source!==parent||event.data?.app!=='loan12')return;
    const data=event.data;
    if(data.type==='key'&&Number.isInteger(data.code)&&[-7,-6,-5,-4,-3,-2,-1,35,42,48,49,50,51,52,53,54,55,56,57].includes(data.code))key(data.code,data.down);
    else if(data.type==='release')release();
    else if(data.type==='pause')pause(Boolean(data.paused));
    else if(data.type==='mute'){muted=Boolean(data.muted);audio();}
    else if(data.type==='flush')flush();
    else if(data.type==='export'){
      Promise.resolve().then(async()=>{await window.LoanV2?.flush();flush();const blob=await new Promise((resolve,reject)=>fs.exportStore((blob,error)=>error?reject(error):resolve(blob)));notify('save',{save:{format:'loan12-save-v1',variant:'ver2',equipmentSchema:1,gameSha256:'90bf8dc99acba9f0a89da99c58b45c8fdb20240841e5a708e0ccfee58a650ff9',createdAt:new Date().toISOString(),files:JSON.parse(await blob.text())}});}).catch(e=>{if(e.requiresReload)LoanV2.reportError(e);notify('status',{message:e.message});});
    }else if(data.type==='import'){
      try{window.LoanEquipment?.saveImport.validateSaveFiles(data.save.files);}catch(e){notify('importfailed',{message:'Không nhập được bản lưu: '+e.message});return;}
      pause(true);fs.importStore(new Blob([JSON.stringify(data.save.files)]),()=>{notify('status',{message:'Đã nhập tiến trình. Đang khởi động lại game…'});notify('imported');});
    }
  });
  function editable(target){return target instanceof Element&&(target.matches('input,textarea,select,button')||target.isContentEditable);}
  window.addEventListener('keydown',event=>{if(editable(event.target))return;const code=keys[event.key.toLowerCase?.()]??keys[event.key];if(code===undefined)return;event.preventDefault();if(!event.repeat)key(code,true);});
  window.addEventListener('keyup',event=>{const code=keys[event.key.toLowerCase?.()]??keys[event.key];if(code!==undefined){if(!editable(event.target))event.preventDefault();key(code,false);}});
  window.addEventListener('blur',release);
  window.addEventListener('pagehide',flush);
  // Return to the web cover when the original "Thoát" command is chosen.
  showExitScreen=function(){flush();notify('exit');};
  const watcher=setInterval(()=>{
    const canvas=document.getElementById('canvas');
    if(!canvas.width||!canvas.height)return;
    const bytes=canvas.getContext('2d').getImageData(0,0,canvas.width,canvas.height).data;
    let nonwhite=0;for(let i=0;i<bytes.length;i+=64)if(bytes[i+3]&&bytes[i]+bytes[i+1]+bytes[i+2]<700)nonwhite++;
    if(nonwhite>30){clearInterval(watcher);notify('ready');}
  },200);
  setTimeout(()=>{if(!document.getElementById('canvas').width)notify('status',{message:'Khởi động chậm. Hãy tải lại trang nếu game chưa xuất hiện.'});},25000);
})();
