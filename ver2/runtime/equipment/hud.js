'use strict';
(() => {
  let api=null,root,bar,fill,label,loadout,last='';
  const shortNames=['Kiếm','Châu','Giáp','Ấn','Bí Kíp','Thạch'];
  function update(){
    const state=api.state(),battle=state.battle,visible=state.screen==='com/mic/smsgame/suquan/ab'&&Boolean(battle);
    root.hidden=!visible;if(!visible)return;
    const barrier=Math.max(0,battle.barrier||0),max=Math.max(1,battle.maxHP||battle.maxHp||battle.fighters?.[0]?.maxHP||battle.fighters?.[0]?.maxHp||1),tiers=battle.activeTiers||[0,0,0,0,0,0];
    const signature=JSON.stringify([barrier,max,tiers]);if(signature===last)return;last=signature;
    root.classList.toggle('v2-golden',tiers[5]===3);bar.hidden=label.hidden=barrier<=0;
    fill.style.width=`${Math.min(100,barrier/max*100)}%`;bar.setAttribute('aria-valuenow',String(barrier));bar.setAttribute('aria-valuemax',String(max));label.textContent=`Chắn ${barrier}`;
    loadout.textContent=tiers.map((tier,id)=>tier?`${shortNames[id]} ${['','I','II','III'][tier]}`:'').filter(Boolean).join(' · ');
  }
  function init(){
    api=window.LoanV2;if(!api)return false;
    root=document.createElement('div');root.className='v2-hud';root.hidden=true;root.setAttribute('aria-label','Trang bị chiến đấu');
    bar=document.createElement('div');bar.className='v2-barrier';bar.setAttribute('role','meter');bar.setAttribute('aria-label','Lá chắn');bar.setAttribute('aria-valuemin','0');
    fill=document.createElement('div');fill.className='v2-barrier-fill';bar.append(fill);
    label=document.createElement('div');label.className='v2-barrier-value';loadout=document.createElement('div');loadout.className='v2-battle-loadout';
    root.append(bar,label,loadout);document.body.append(root);api.subscribe?.(update);update();return true;
  }
  if(!init()){const timer=setInterval(()=>{if(init())clearInterval(timer);},100);}
})();
