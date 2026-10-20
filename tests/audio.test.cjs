'use strict';
const {test}=require('node:test');const assert=require('node:assert/strict');const vm=require('node:vm');const fs=require('node:fs');
test('Mute also suspends audio contexts created later by original game sounds',()=>{
  class BrowserAudioContext{constructor(){this.state='running';}suspend(){this.state='suspended';return Promise.resolve();}}
  const sandbox={window:{AudioContext:BrowserAudioContext}};vm.runInNewContext(fs.readFileSync('runtime/host.js','utf8'),sandbox);
  const first=new sandbox.window.AudioContext();assert.equal(first.state,'running');
  sandbox.window.loanAudioMuted=true;
  const second=new sandbox.window.AudioContext();assert.equal(second.state,'suspended');
  assert.equal(sandbox.window.loanAudioContexts.length,2);
});
