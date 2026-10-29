/* Offline Java ME adapters for Loạn 12 Sứ Quân. */
'use strict';
// The MIDlet's legacy SMS listener must sleep when no telephone exists.
// The upstream missing native returns immediately and creates a busy loop.
Native['com/sun/midp/io/j2me/sms/Protocol.waitUntilMessageAvailable0.(II)I'] = function () {
  asyncImpl('I', new Promise(function () {}));
};
Native['com/sun/midp/io/j2me/push/ConnectionRegistry.list0.([BZ[BI)I'] = function () { return -1; };

// The game's Sony capability probe parses "JP-8.0" as version 8 and leaves
// cx.D=false, the same result as its caught exception on a non-Sony handset.
MIDP.additionalProperties['com.sonyericsson.java.platform'] = 'JP-8.0';
window.addEventListener('DOMContentLoaded', function () {
  // LCDUI's factory calls getDisplay(null) solely to initialize Display and
  // catches its expected NullPointerException before DisplayContainer exists.
  // This VM fork disables that Java NPE, producing a JS null.vTable instead.
  // Class initialization has already completed before this interpreted entry.
  LoanJavaHooks.observe('javax/microedition/lcdui/Display.getDisplay.(Ljava/lang/Object;)Ljavax/microedition/lcdui/Display;', {
    before(call) {
      if (call.args[0] === 0 && LoanJavaHooks.stat('javax/microedition/lcdui/Display', 'displayContainer', 'Lcom/sun/midp/lcdui/DisplayContainer;') === 0) {
        call.skipReturn = true;
        call.skipValue = 0;
      }
    }
  });
});

// Upstream renders TextBox as window.prompt(). Use an in-frame form instead:
// sandboxed iframes suppress browser prompts and would silently cancel names.
const updateNativeCommands = Native['javax/microedition/lcdui/NativeMenu.updateCommands.([Ljavax/microedition/lcdui/Command;I[Ljavax/microedition/lcdui/Command;I)V'];
Native['javax/microedition/lcdui/NativeMenu.updateCommands.([Ljavax/microedition/lcdui/Command;I[Ljavax/microedition/lcdui/Command;I)V'] = function(address,itemCommands,numItemCommands,commandAddress,numCommands) {
  if (!commandAddress) {
    return updateNativeCommands(address,itemCommands,numItemCommands,commandAddress,numCommands);
  }
  const commands=Array.from(J2ME.getArrayFromAddr(commandAddress)).filter(Boolean).map(a=>getHandle(a));
  const done=commands.find(command=>command.commandType===4);
  const doneLabel=done?J2ME.fromStringAddr(done.shortLabel):'';
  const phone=doneLabel==='Gửi';
  const back=commands.find(command=>command.commandType===(phone?7:2));
  // curDisplayableId belongs to the upstream LCDUI closure, not window.
  // Match the game's character TextBox commands instead of reading that state.
  if (!done || !back || (doneLabel!=='Xong'&&!phone)) return updateNativeCommands(address,itemCommands,numItemCommands,commandAddress,numCommands);
  const previous=document.getElementById('web-name-dialog');
  if(previous)previous.remove();
  const dialog=document.createElement('form');dialog.id='web-name-dialog';
  const label=document.createElement('label');label.htmlFor=phone?'web-phone-number':'web-player-name';label.textContent=phone?'Số điện thoại':'Tên tướng quân';
  const input=document.createElement('input');input.id=label.htmlFor;input.name=phone?'phoneNumber':'playerName';input.type=phone?'tel':'text';input.maxLength=phone?20:10;input.autocomplete='off';input.placeholder=phone?'Số điện thoại người nhận':'2–10 ký tự';input.required=true;input.minLength=phone?1:2;
  const hint=document.createElement('p');hint.textContent=phone?'Tặng game qua SMS không hoạt động trên bản web. Không gửi tin nhắn hoặc thu phí.':'Chọn tên cho cuộc hành trình của bạn.';
  const actions=document.createElement('div');
  const cancel=document.createElement('button');cancel.type='button';cancel.textContent='Trở về';
  // Native form submission is disabled by the game iframe's sandbox. Use a
  // button action, retaining native constraint validation and Enter handling.
  const submit=document.createElement('button');submit.type='button';submit.textContent=doneLabel;
  const doneId=done.id,backId=back.id;
  cancel.addEventListener('click',()=>{dialog.remove();MIDP.sendCommandEvent(backId);});
  function finish(){
    if(!dialog.isConnected)return;
    const value=input.value.trim();
    if(value.length<(phone?1:2)){input.setCustomValidity(phone?'Nhập số điện thoại người nhận.':'Nhập tên có ít nhất 2 ký tự.');input.reportValidity();return;}
    if(!input.checkValidity()){input.reportValidity();return;}
    dialog.remove();
    if(phone){
      MIDP.sendCommandEvent(backId);
      if(parent!==window)parent.postMessage({app:'loan12',type:'status',message:'Tặng game qua SMS không khả dụng trong bản web. Không gửi SMS, không thu phí.'},location.origin);
    }else{mycontent=value;MIDP.sendCommandEvent(doneId);}
  }
  submit.addEventListener('click',finish);
  dialog.addEventListener('submit',event=>{event.preventDefault();finish();});
  input.addEventListener('input',()=>input.setCustomValidity(''));
  input.addEventListener('keydown',event=>{if(event.key==='Escape'){event.preventDefault();cancel.click();}else if(event.key==='Enter'){event.preventDefault();finish();}});
  actions.append(cancel,submit);dialog.append(label,hint,input,actions);document.body.append(dialog);input.focus();
};
