'use strict';
(() => {
  const $ = id => document.getElementById(id);
  const game = $('game');
  let running = false, paused = false, muted = false, restarting = false;
  const gameUrl = 'runtime/main.html?jars=../Loan-12-Su-Quan.jar&midletClassName=com.mic.smsgame.suquan.SQMidlet&canvasSize=size-240x320&autosize=0';
  const keys = {ArrowUp:-1,ArrowDown:-2,ArrowLeft:-3,ArrowRight:-4,w:-1,s:-2,a:-3,d:-4,Enter:-5,' ':-5,q:-6,e:-7,Escape:-7,'*':42,'#':35};
  for (let n=0;n<10;n++) keys[n] = 48+n;
  function send(type, data = {}) {
    if (running) game.contentWindow.postMessage({app:'loan12',type,...data}, location.origin);
  }
  function setStatus(message) { $('status').textContent = message; }
  function resize() {
    const width = $('stage').getBoundingClientRect().width || $('stage').clientWidth;
    $('stage').style.setProperty('--game-scale', width / 240);
  }
  new ResizeObserver(resize).observe($('stage'));
  window.addEventListener('resize', resize);
  resize();
  $('start').addEventListener('click', () => {
    running = true;
    $('start').disabled = true;
    $('start').textContent = 'ĐANG KHỞI ĐỘNG…';
    $('boot-status').textContent = 'Đang nạp trò chơi và tài nguyên gốc';
    game.hidden = false;
    game.src = gameUrl;
    if (window.matchMedia('(max-width: 900px) and (pointer: coarse)').matches && !document.fullscreenElement) {
      $('machine').requestFullscreen?.().catch(() => {});
    }
    resize();
  });
  window.addEventListener('message', event => {
    if (event.origin !== location.origin || event.source !== game.contentWindow || event.data?.app !== 'loan12') return;
    const {type,message,save} = event.data;
    if (type === 'ready') {
      restarting = false;
      $('cover').hidden = true;
      paused = false;
      $('pause-screen').hidden = true;
      $('pause').innerHTML = '<span aria-hidden="true">Ⅱ</span> Tạm dừng';
      ['pause','sound','export','import'].forEach(id => $(id).disabled = false);
      send('mute',{muted});
      game.focus();
    } else if (type === 'status') setStatus(message);
    else if (type === 'imported') {
      // The parent owns the new VM lifecycle. Ignore an old VM's exit event
      // until the replacement frame has painted its first screen.
      game.src = gameUrl + '&restore=' + Date.now();
    }
    else if (type === 'save') {
      const url = URL.createObjectURL(new Blob([JSON.stringify(save,null,2)],{type:'application/json'}));
      const link = document.createElement('a');
      link.href = url; link.download = 'loan-12-su-quan-save.json'; link.click();
      setTimeout(() => URL.revokeObjectURL(url),1000);
      setStatus('Đã tải bản lưu. Cất giữ tệp để chuyển tiến trình.');
    } else if (type === 'exit') {
      if (restarting) return;
      running = false; paused = false; $('pause-screen').hidden = true;
      $('cover').hidden = false; game.hidden = true; game.src = 'about:blank';
      $('start').disabled = false; $('start').innerHTML = 'VÀO GAME <span aria-hidden="true">▶</span>';
      $('boot-status').textContent = 'Tiến trình đã lưu. Bấm để trở lại.';
      ['pause','sound','export','import'].forEach(id => $(id).disabled = true);
    }
  });
  function setPaused(value) {
    if (!running) return;
    paused = value; send('pause',{paused});
    $('pause-screen').hidden = !paused;
    $('pause').innerHTML = paused ? '<span aria-hidden="true">▶</span> Tiếp tục' : '<span aria-hidden="true">Ⅱ</span> Tạm dừng';
    if (!paused) game.focus();
  }
  $('pause').addEventListener('click',()=>setPaused(!paused));
  $('resume').addEventListener('click',()=>setPaused(false));
  $('sound').addEventListener('click',()=>{
    muted = !muted; send('mute',{muted});
    $('sound').setAttribute('aria-pressed',String(muted));
    $('sound').innerHTML = '<span aria-hidden="true">♪</span> '+(muted?'Bật âm thanh':'Âm thanh');
    game.focus();
  });
  $('fullscreen').addEventListener('click',async()=>{
    try { if (document.fullscreenElement) await document.exitFullscreen(); else await $('machine').requestFullscreen(); }
    catch { setStatus('Trình duyệt không hỗ trợ toàn màn hình; có thể xoay ngang thiết bị.'); }
    resize();
  });
  const held = new Set();
  function press(code) { if (!running || paused || held.has(code)) return; held.add(code); send('key',{code,down:true}); }
  function release(code) { if (held.delete(code)) send('key',{code,down:false}); }
  document.querySelectorAll('[data-key]').forEach(button=>{
    const code = Number(button.dataset.key);
    button.addEventListener('pointerdown',e=>{ e.preventDefault(); button.setPointerCapture(e.pointerId); button.classList.add('held'); press(code); });
    const up = ()=>{button.classList.remove('held');release(code);if(running&&!paused)game.focus();};
    button.addEventListener('pointerup',up);button.addEventListener('pointercancel',up);button.addEventListener('lostpointercapture',up);
    // Enter/Space activation by keyboard and assistive technology.
    button.addEventListener('click',e=>{if(e.detail===0){press(code);setTimeout(()=>release(code),70);}});
  });
  function editable(target) { return target instanceof Element && (target.matches('input,textarea,select,summary,a') || target.isContentEditable); }
  window.addEventListener('keydown',e=>{
    if (!running || editable(e.target)) return;
    if(e.target instanceof HTMLButtonElement && ['Enter',' '].includes(e.key)) return;
    const code=keys[e.key.toLowerCase?.()] ?? keys[e.key];
    if(code===undefined)return;e.preventDefault();if(!e.repeat)press(code);
  });
  window.addEventListener('keyup',e=>{const code=keys[e.key.toLowerCase?.()]??keys[e.key];if(code!==undefined){release(code);if(running&&!editable(e.target))e.preventDefault();}});
  window.addEventListener('blur',()=>{for(const code of [...held])release(code);send('release');});
  document.addEventListener('visibilitychange',()=>{if(document.hidden&&running){setPaused(true);send('flush');}});
  $('export').addEventListener('click',()=>{send('export');setStatus('Đang tạo bản sao tiến trình…');});
  $('import').addEventListener('click',()=>$('save-file').click());
  $('save-file').addEventListener('change',async event=>{
    const file=event.target.files[0];event.target.value='';if(!file)return;
    try {
      if(file.size>4*1024*1024)throw Error('Tệp lưu quá lớn.');
      const save=JSON.parse(await file.text());
      if(save.format!=='loan12-save-v1'||save.gameSha256!=='90bf8dc99acba9f0a89da99c58b45c8fdb20240841e5a708e0ccfee58a650ff9'||!save.files||typeof save.files!=='object'||Array.isArray(save.files))throw Error('Tệp không phải bản lưu của game này.');
      const entries=Object.entries(save.files);
      if(!entries.length||entries.length>1000)throw Error('Cấu trúc tệp lưu không hợp lệ.');
      for(const [path,record] of entries){
        if(!path.startsWith('/')||path.includes('..')||!record||typeof record.isDir!=='boolean')throw Error('Dữ liệu tệp lưu không hợp lệ.');
        if(!record.isDir&&(!Array.isArray(record.data)||record.data.some(n=>!Number.isInteger(n)||n< -128||n>127)||record.size!==record.data.length))throw Error('Dữ liệu tệp lưu không hợp lệ.');
      }
      if(!confirm('Thay tiến trình hiện tại bằng bản lưu này? Bạn nên tải bản lưu hiện tại trước.'))return;
      restarting = true;setPaused(true);send('import',{save});
    } catch(error){setStatus(error.message);}
  });
})();
