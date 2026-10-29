'use strict';
(() => {
  const names=['Thần Binh','Linh Châu','Bảo Giáp','Pháp Ấn','Bí Kíp','Thần Thạch'];
  const roman=['0','I','II','III'];
  let api=null,state=null,mode=null,selected=0,busy=false,message='',confirmation=null,lastSignature='',tab=1;
  let tabs,launcher,panel,failure;
  const el=(tag,props={},text)=>{const n=document.createElement(tag);Object.assign(n,props);if(text!==undefined)n.textContent=text;return n;};
  const button=(text,action,props={})=>{const b=el('button',{type:'button',...props},text);b.addEventListener('click',action);return b;};
  const shop=()=>state?.screen==='com/mic/smsgame/suquan/q';
  const battle=()=>state?.screen==='com/mic/smsgame/suquan/ab';
  const profile=()=>state?.profile||{ownedTiers:[0,0,0,0,0,0],equippedIds:[]};
  const item=id=>api?.catalog?.[id]||window.LoanEquipment?.catalog?.[id]||{id,name:names[id]};
  function tierData(id,tier){const c=item(id);return c.tiers?.find(t=>t.tier===tier)||c.tiers?.[tier-1]||c.levels?.[tier-1]||{};}
  function title(id,tier){return tier? tierData(id,tier).name||`${names[id]} ${roman[tier]}`:names[id];}
  function price(id,tier){const c=item(id),t=tierData(id,tier);return t.price??c.prices?.[tier-1]??0;}
  function textDescription(id,tier){return window.LoanEquipmentInfo?.equipment(id,tier)||tierData(id,tier).description||'Chọn bậc trang bị để xem công dụng.';}
  function sync(){state=api?.state?.()||state;}
  function tabButtons(container){
    const b1=button('[1] Vật Phẩm',()=>close());b1.setAttribute('role','tab');b1.setAttribute('aria-selected',String(!mode));
    const b3=button('[3] Trang Bị',()=>open('shop'));b3.setAttribute('role','tab');b3.setAttribute('aria-selected',String(mode==='shop'));
    container.append(b1,b3);
  }
  function close(){if(busy)return;mode=null;confirmation=null;message='';tab=1;lastSignature='';refresh();document.getElementById('canvas')?.focus();}
  function open(next='shop'){
    if(!api)return;sync();
    if(next==='shop'&&!shop())next='loadout';
    if(battle()&&next!=='skills'&&next!=='info')next='info';
    mode=next;tab=next==='shop'?3:1;confirmation=null;message='';lastSignature='';render();
    panel.querySelector('button')?.focus({preventScroll:true});
  }
  async function perform(operation,success){
    if(busy)return;busy=true;confirmation=null;message='Đang lưu…';render();
    try{await operation();sync();message=success;}catch(error){message=error?.message||'Không thể lưu thay đổi. Hãy thử lại.';}
    finally{busy=false;lastSignature='';render();}
  }
  function purchase(){
    const p=profile(),level=p.ownedTiers[selected]||0;if(level>=3||busy||state.ready===false)return;
    const cost=price(selected,level+1),gold=Math.max(0,Number(state.gold)||0);
    confirmation={type:'purchase',id:selected,level:level+1,cost,charge:Math.min(gold,cost)};render();
  }
  function equip(){
    if(busy||battle()||state.ready===false)return;const p=profile(),id=selected;
    if(!p.ownedTiers[id])return;
    if(p.equippedIds.includes(id))return perform(()=>api.unequip(id),'Đã tháo trang bị.');
    if(p.equippedIds.length>=3){confirmation={type:'replace',id};render();return;}
    return perform(()=>api.equip(id),'Đã lắp trang bị.');
  }
  function renderConfirmation(){
    if(!confirmation)return;const c=confirmation,box=el('section',{className:'v2-confirm'});box.setAttribute('role','alertdialog');box.setAttribute('aria-modal','true');
    if(c.type==='purchase'){
      box.append(el('h3',{},`Nhận bậc ${roman[c.level]}`),el('p',{},title(c.id,c.level)),el('p',{},`Giá: ${c.cost.toLocaleString('vi-VN')} vàng. Thực trừ: ${c.charge.toLocaleString('vi-VN')} vàng.`),el('p',{},c.charge<c.cost?'Phần vàng thiếu được miễn. Mua xong hãy chọn Lắp để mang vào trận.':'Mua xong hãy chọn Lắp để mang vào trận.'));
      const row=el('div',{className:'v2-actions'});row.append(button('Mua',()=>perform(()=>api.purchase(c.id),'Đã nâng cấp. Chọn Lắp nếu muốn mang vào trận.')) ,button('Hủy',()=>{confirmation=null;render();}));box.append(row);
    }else{
      box.append(el('h3',{},'Đã mang đủ 3 món'),el('p',{},'Chọn món muốn thay. Hủy để giữ nguyên trang bị.'));
      profile().equippedIds.forEach(id=>box.append(button(`Thay ${names[id]}`,()=>perform(()=>api.equip(c.id,id),'Đã thay trang bị.'))));
      box.append(button('Hủy',()=>{confirmation=null;render();}));
    }
    panel.append(box);
    const choices=box.querySelectorAll('button');
    choices[confirmation.selectedIndex||0]?.focus({preventScroll:true});
  }
  function render(){
    if(!panel)return;sync();panel.hidden=!mode;tabs.hidden=!shop()||Boolean(mode);launcher.hidden=Boolean(mode)||!canLaunch();
    failure.hidden=!state.error;failure.textContent=state.error?`Không thể sử dụng dữ liệu ver2: ${state.error}. Hãy tải lại để phục hồi; dữ liệu lỗi được giữ để kiểm tra.`:'';
    tabs.replaceChildren();tabButtons(tabs);
    launcher.replaceChildren(button(state?.dialog==='ah'?'Chiêu thức':state?.mapPhase===3?'Chọn trang bị':'Trang bị',()=>open(state?.dialog==='ah'?'skills':state?.dialog==='ad'?'info':'loadout')));
    if(!mode)return;panel.replaceChildren();
    panel.append(el('h2',{},mode==='shop'?'CỬA HÀNG':mode==='skills'?'TUYỆT CHIÊU':mode==='info'?'TRANG BỊ NHÂN VẬT':'CHUẨN BỊ TRANG BỊ'));
    if(mode==='shop'){const nav=el('nav',{className:'v2-tabs'});nav.setAttribute('role','tablist');tabButtons(nav);panel.append(nav);}
    const meta=el('div',{className:'v2-meta'});meta.append(el('span',{className:'v2-gold'},`${Math.max(0,Number(state.gold)||0).toLocaleString('vi-VN')} vàng`),el('span',{},`${profile().equippedIds.length}/3 đang lắp`));panel.append(meta);
    if(mode==='skills'){renderSkills();return;}
    if(mode==='loadout'&&state.opponent){
      panel.append(el('div',{className:'v2-label',title:state.opponent.name||String(state.opponent)},`Đối thủ: ${state.opponent.name||String(state.opponent)}`));
      if(Number.isFinite(state.opponent.hp)&&Number.isFinite(state.opponent.attack))panel.append(el('div',{className:'v2-label'},`HP ${state.opponent.hp} · Công ${state.opponent.attack}`));
    }
    const p=profile(),grid=el('div',{className:'v2-grid'});grid.setAttribute('aria-label','Sáu loại trang bị');
    for(let id=0;id<6;id++){
      const level=p.ownedTiers[id]||0,b=button('',()=>{selected=id;message='';render();},{className:p.equippedIds.includes(id)?'v2-equipped':''});b.dataset.equipmentId=id;b.setAttribute('aria-pressed',String(id===selected));b.setAttribute('aria-label',`${names[id]}, bậc ${roman[level]}${p.equippedIds.includes(id)?', đang lắp':''}`);
      b.append(el('span',{className:`v2-icon v2-icon-${id}`}),el('span',{},`${names[id]} ${roman[level]}`));grid.append(b);
    }
    panel.append(grid);
    const level=p.ownedTiers[selected]||0,next=Math.min(3,level+1),detail=el('section',{className:'v2-detail',tabIndex:0});
    detail.append(el('h3',{title:title(selected,level||1)},title(selected,level||1)));
    if(level)detail.append(el('p',{},`Bậc ${roman[level]} · ${p.equippedIds.includes(selected)?'Đang lắp':'Trong kho'}`),el('p',{},textDescription(selected,level)));
    else detail.append(el('p',{},'Chưa sở hữu. Mua bậc I rồi lắp vào một trong ba ô.'));
    if(mode==='shop'&&level<3)detail.append(el('h3',{title:title(selected,next)},`Bậc ${roman[next]}: ${title(selected,next)}`),el('p',{},textDescription(selected,next)),el('p',{className:'v2-gold'},`${price(selected,next).toLocaleString('vi-VN')} vàng · mua được cả khi thiếu vàng`));
    if(battle()){
      const active=state.battle?.activeTiers?.[selected]||0;
      detail.append(el('p',{},active?`Trong trận: bậc ${roman[active]}. ${textDescription(selected,active)}`:'Không có hiệu lực trong trận này.'),el('p',{},'Trang bị đã khóa theo bậc lúc bắt đầu trận.'));
    }
    if(message||state.ready===false){const status=el('p',{className:'v2-status'},message||state.error||'Đang đọc tiến trình…');status.setAttribute('role','status');detail.append(status);}
    panel.append(detail);
    const slots=el('div',{className:'v2-slots'});slots.setAttribute('aria-label','Ba ô trang bị');
    for(let i=0;i<3;i++){const id=p.equippedIds[i];slots.append(button(id===undefined?`Ô ${i+1}: trống`:names[id],()=>{if(id!==undefined){selected=id;render();}},{title:id===undefined?'Ô trống':title(id,p.ownedTiers[id])}));}panel.append(slots);
    const actions=el('div',{className:'v2-actions'});
    if(mode==='shop')actions.append(button(level===3?'MAX':level?'Nâng cấp':'Mua',purchase,{disabled:busy||level===3||state.ready===false}));
    else actions.append(button('Chiêu thức',()=>open('skills')));
    actions.append(button(p.equippedIds.includes(selected)?'Tháo':'Lắp',equip,{disabled:busy||!level||battle()||state.ready===false}));panel.append(actions);
    footer();renderConfirmation();
  }
  function renderSkills(){
    const detail=el('div',{className:'v2-detail',tabIndex:0}),list=el('div',{className:'v2-skill-list'}),ids=state.skillIds||[0,1,2,3,4,5,6,7,8];
    ids.forEach(id=>{const info=window.LoanEquipmentInfo?.skill(id,state)||{name:`Chiêu ${id+1}`,description:'Đang nạp mô tả chiêu thức.'};const section=el('section',{className:'v2-skill-desc'});section.append(el('h3',{title:info.name},info.name),el('p',{},info.description));list.append(section);});
    detail.append(list);panel.append(detail);footer();
  }
  function footer(){const row=el('div',{className:'v2-footer'});row.append(el('span',{className:'v2-hint'},busy?'Đang lưu…':'2/8 Cuộn · Q Xác nhận'),button('Đóng [E]',close,{disabled:busy}));panel.append(row);}
  function canLaunch(){return state?.dialog==='ad'||state?.dialog==='ah'||state?.screen==='com/mic/smsgame/suquan/aa'&&state?.mapPhase===3;}
  function refresh(){
    if(!api)return;sync();
    if(mode==='shop'&&!shop()||mode&&battle()&&!['info','skills'].includes(mode)){mode=null;confirmation=null;}
    const signature=JSON.stringify([state.screen,state.dialog,state.mapPhase,state.gold,state.profile,state.opponent,state.battle?.activeTiers,state.skillIds,state.ready,state.error,mode,selected,busy,message]);
    if(signature===lastSignature)return;lastSignature=signature;render();
  }
  function handleKey(code,down=true){
    if(!api)return false;
    if(!mode){
      if(shop()&&[49,51,42,35].includes(code)){if(down&&code!==49)open('shop');return true;}
      return false;
    }
    if(!down||busy)return true;
    if(code===-7){if(confirmation){confirmation=null;render();}else close();return true;}
    if(confirmation){
      const choices=panel.querySelectorAll('.v2-confirm button');
      if([-1,-2,-3,-4].includes(code)){confirmation.selectedIndex=((confirmation.selectedIndex||0)+(code===-1||code===-3?-1:1)+choices.length)%choices.length;choices[confirmation.selectedIndex]?.focus({preventScroll:true});}
      if(code===-5||code===-6){const focused=panel.querySelector('.v2-confirm button:focus');(focused||choices[confirmation.selectedIndex||0])?.click();}
      return true;
    }
    if(mode==='shop'&&[49,42,35].includes(code)){close();return true;}
    if(mode==='shop'&&code===51)return true;
    if(code===50||code===56){panel.querySelector('.v2-detail').scrollBy(0,code===50?-35:35);return true;}
    if(mode==='skills'){if(code===-1||code===-2)panel.querySelector('.v2-detail').scrollBy(0,code===-1?-50:50);return true;}
    if(document.activeElement===panel.querySelector('.v2-detail')&&(code===-1||code===-2)){document.activeElement.scrollBy(0,code===-1?-35:35);return true;}
    if([-1,-2,-3,-4].includes(code)){selected=(selected+(code===-1?-3:code===-2?3:code===-3?-1:1)+6)%6;message='';render();return true;}
    if(code===-5||code===-6){if(mode==='shop')purchase();else equip();return true;}
    return true;
  }
  function init(){
    api=window.LoanV2;if(!api)return false;
    tabs=el('nav',{className:'v2-ui v2-tabs'});tabs.setAttribute('role','tablist');
    launcher=el('div',{className:'v2-ui v2-open'});
    panel=el('section',{className:'v2-ui v2-panel',hidden:true});panel.setAttribute('role','dialog');panel.setAttribute('aria-label','Trang bị');panel.setAttribute('aria-modal','true');
    failure=el('div',{className:'v2-ui v2-failure',hidden:true});failure.setAttribute('role','alert');
    document.body.append(tabs,launcher,panel,failure);
    for(const node of [tabs,launcher,panel])for(const event of ['pointerdown','pointerup','click'])node.addEventListener(event,e=>e.stopPropagation());
    document.addEventListener('keydown',event=>{const codes={ArrowUp:-1,ArrowDown:-2,ArrowLeft:-3,ArrowRight:-4,Enter:-5,q:-6,e:-7,Escape:-7,'1':49,'2':50,'3':51,'8':56,'*':42,'#':35};const code=codes[event.key];if(code!==undefined&&handleKey(code,!event.repeat)){event.preventDefault();event.stopImmediatePropagation();}},true);
    document.addEventListener('keyup',event=>{if(mode){event.preventDefault();event.stopImmediatePropagation();}},true);
    api.subscribe?.(refresh);refresh();return true;
  }
  window.LoanEquipmentUI=Object.freeze({open,close,refresh,handleKey,get isOpen(){return Boolean(mode);},get mode(){return mode;}});
  if(!init()){const timer=setInterval(()=>{if(init())clearInterval(timer);},100);}
})();
