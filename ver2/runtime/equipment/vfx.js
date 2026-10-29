'use strict';
(function(root){
  class EffectPool {
    constructor(){this.text=[];this.particles=[];this.reduced=false;this.serial=0;}
    clear(){this.text.length=0;this.particles.length=0;}
    add(event,{gold=false,awakened=false}={}){
      const side=event.side??event.targetSide??1,x=side===0?55:185,y=223;
      const colors={damage:'#fff0d0',heal:'#83f596',block:'#8be4ff','barrier-break':'#b4eaff',cast:gold?'#ffe58a':['#ff9767','#9bcfff','#b5edff'][Math.floor((event.skillId||0)/3)]};
      const type=event.type,amount=Math.max(0,Math.floor(event.amount??event.actual??event.blocked??0));
      if(['damage','heal','block','barrier-break'].includes(type)){
        const label=type==='barrier-break'?'Vỡ khiên':type==='heal'?`+${amount}`:type==='block'?`Chắn ${amount}`:`−${amount}`;
        if(type==='barrier-break'||amount>0){
          const lane=this.text.filter(item=>item.side===side).length%6;
          if(this.text.length>=12)this.text.shift();
          this.text.push({label,side,x,y:y-lane*12,age:0,life:950,color:colors[type]});
        }
      }
      if((type==='cast'&&awakened)||type==='barrier-break'||type==='block'){
        const count=this.reduced?3:gold?12:6;
        for(let i=0;i<count;i++){
          if(this.particles.length>=24)this.particles.shift();
          const angle=i/count*Math.PI*2;
          this.particles.push({x:type==='cast'?120:x,y:type==='cast'?144:246,vx:Math.cos(angle)*(gold?30:22),vy:Math.sin(angle)*25,age:0,life:gold?600:450,color:gold?'#ffe187':colors[type]||'#a5dcff',kind:type==='cast'?Math.floor((event.skillId||0)/3):3});
        }
      }
    }
    step(delta,paused=false){if(paused)return;delta=Math.max(0,Math.min(delta,50));for(const collection of [this.text,this.particles]){for(const item of collection)item.age+=delta;for(let i=collection.length-1;i>=0;i--)if(collection[i].age>=collection[i].life)collection.splice(i,1);}}
  }
  if(typeof module==='object'&&module.exports){module.exports={EffectPool};return;}
  const pool=new EffectPool(),samples=[];let api,canvas,context,previous=0,identity=null,frame=0;
  function state(){return api?.state?.()||{};}
  function event(e){const s=state(),tiers=s.battle?.activeTiers||[];pool.add(e,{gold:tiers[5]===3,awakened:(e.skillId%3)+1<=(tiers[4]||0)});}
  function tick(now){
    frame=requestAnimationFrame(tick);const s=state(),battle=s.battle,visible=s.screen==='com/mic/smsgame/suquan/ab'&&Boolean(battle);
    canvas.hidden=!visible;
    const id=visible?(battle.battleId??battle.id??battle.modelAddress??'battle'):null;
    if(id!==identity){identity=id;pool.clear();context.clearRect(0,0,240,320);}
    const delta=previous?now-previous:0;previous=now;
    if(!visible||s.paused)return;
    const begin=performance.now();pool.step(delta);context.clearRect(0,0,240,320);
    const gold=battle.activeTiers?.[5]===3;
    if(gold&&battle.barrier>0){context.globalAlpha=.45;context.strokeStyle='#ffdc72';context.strokeRect(34,235,33,34);context.globalAlpha=1;}
    for(const p of pool.particles){context.globalAlpha=1-p.age/p.life;context.fillStyle=p.color;const x=Math.round(p.x+p.vx*p.age/1000),y=Math.round(p.y+p.vy*p.age/1000);if(p.kind===1){context.fillRect(x,y,1,5);context.fillRect(x-1,y+2,3,1);}else if(p.kind===2){context.fillRect(x-2,y,5,1);context.fillRect(x,y-2,1,5);}else context.fillRect(x,y,2,2);}
    context.font='bold 10px Arial';context.textAlign='center';context.lineWidth=2;
    for(const t of pool.text){context.globalAlpha=Math.min(1,(1-t.age/t.life)*2);const y=Math.round(t.y-t.age/45);context.strokeStyle='#15100a';context.strokeText(t.label,t.x,y);context.fillStyle=t.color;context.fillText(t.label,t.x,y);}
    context.globalAlpha=1;samples.push(performance.now()-begin);if(samples.length>512)samples.shift();
    if(samples.length>=120){const sorted=[...samples].sort((a,b)=>a-b);pool.reduced=sorted[Math.floor(sorted.length*.95)]>4;}
  }
  function init(){api=root.LoanV2;if(!api)return false;canvas=document.createElement('canvas');canvas.width=240;canvas.height=320;canvas.className='v2-vfx';canvas.setAttribute('aria-hidden','true');canvas.hidden=true;document.body.append(canvas);context=canvas.getContext('2d');context.imageSmoothingEnabled=false;api.onCombat?.(event);frame=requestAnimationFrame(tick);return true;}
  root.LoanVFX=Object.freeze({emit:event,clear:()=>pool.clear(),resetMetrics:()=>{samples.length=0;},stats:()=>({text:pool.text.length,particles:pool.particles.length,reduced:pool.reduced,samples:[...samples],p95:samples.length?[...samples].sort((a,b)=>a-b)[Math.floor(samples.length*.95)]:0}),destroy:()=>{cancelAnimationFrame(frame);pool.clear();canvas?.remove();}});
  if(!init()){const timer=setInterval(()=>{if(init())clearInterval(timer);},100);}
})(typeof window==='object'?window:globalThis);
