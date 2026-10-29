// Track browser audio contexts so user controls can resume/suspend the originals.
'use strict';
window.loanAudioContexts = [];
const BrowserAudioContext = window.AudioContext || window.webkitAudioContext;
if (BrowserAudioContext) {
  window.AudioContext = new Proxy(BrowserAudioContext, { construct(target,args) {
    const context = Reflect.construct(target,args);
    window.loanAudioContexts.push(context);
    if (window.loanAudioMuted) context.suspend().catch(()=>{});
    return context;
  }});
}
