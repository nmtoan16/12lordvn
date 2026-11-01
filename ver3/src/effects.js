export function createEffects(settings,isPaused){
 const canvas=document.getElementById('fx'),ctx=canvas.getContext('2d');
 let particles=[],spells=[],rings=[],last=0,audio,floatOffset=0;
 const reduced=()=>settings().reduced||matchMedia('(prefers-reduced-motion: reduce)').matches;
 function tone(f=440,type='sine',dur=.24,vol=.05,delay=0){
  if(!settings().sound)return;
  try{
   audio??=new AudioContext();audio.resume();
   const t0=audio.currentTime+delay,o=audio.createOscillator(),g=audio.createGain();
   o.type=type;o.frequency.setValueAtTime(f,t0);o.frequency.exponentialRampToValueAtTime(Math.max(60,f*.65),t0+dur);
   g.gain.setValueAtTime(vol,t0);g.gain.exponentialRampToValueAtTime(.001,t0+dur+.02);
   o.connect(g).connect(audio.destination);o.start(t0);o.stop(t0+dur+.03);
  }catch{}
 }
 function chord(notes,type='triangle',step=.055){notes.forEach((n,i)=>tone(n,type,.22,.045,i*step));}
 function burst(x,y,color,count=20){
  if(reduced())return;
  rings.push({x,y,r:8,maxR:48+Math.min(count,40),color,life:.38,maxLife:.38});
  for(let i=0;i<count&&particles.length<220;i++){
   const angle=Math.random()*Math.PI*2,speed=55+Math.random()*175,life=.45+Math.random()*.45;
   particles.push({x,y,vx:Math.cos(angle)*speed,vy:Math.sin(angle)*speed,size:2+Math.random()*3.2,life,maxLife:life,color,spark:i%3===0});
  }
 }
 function float(side,value,color){
  if(!value)return;
  const box=document.getElementById(side?'enemy':'player')?.getBoundingClientRect();if(!box)return;
  const slot=(floatOffset++%4),el=document.createElement('span');
  el.className='damage-number';
  el.style.cssText=`left:${box.left+box.width*.38+(slot%2)*28}px;top:${box.top+44+slot*22}px;color:${color}`;
  el.textContent=value;document.body.append(el);setTimeout(()=>el.remove(),1200);
 }
 function pulseUnit(id,cls){
  if(reduced())return;
  const el=document.getElementById(id);if(!el)return;
  el.classList.remove(cls);void el.offsetWidth;el.classList.add(cls);
  setTimeout(()=>el.classList.remove(cls),380);
 }
 function banner(text,color='#67ebd5'){
  const el=document.createElement('div');el.className='cast-banner';el.style.borderColor=color;
  el.textContent=text;document.body.append(el);setTimeout(()=>el.remove(),1400);
 }
 function spell(e){
  if(reduced())return;
  const source=document.getElementById(e.hero==='enemy'?'enemy':'player')?.getBoundingClientRect();
  const target=document.getElementById(['nham','yen'].includes(e.hero)?'player':e.hero==='enemy'?'player':'enemy')?.getBoundingClientRect();
  if(!source||!target)return;
  spells.push({hero:e.hero,x:source.left+source.width/2,y:source.top+Math.min(130,source.height/2),tx:target.left+target.width/2,ty:target.top+Math.min(130,target.height/2),color:e.color||'#ffb36c',life:1});
 }
 function play(events){
  floatOffset=0;
  for(const e of events){
   if(e.type==='hit'){
    float(e.side,e.value?(e.pierce?'⚡-':'-')+e.value:'BỊ CHẶN',e.pierce?'#f0abfc':'#ffb36c');
    if(e.absorbed)float(e.side,'◇ -'+e.absorbed,'#c5abff');
    pulseUnit(e.side?'enemy':'player','hit-react');pulseUnit(e.side?'player':'enemy','lunge');
   }
   if(e.type==='hp')float(e.side,'+'+e.value+' HP','#80f0ba');
   if(e.type==='shield')float(e.side,'+◇'+e.value,'#c4b5fd');
   if(e.type==='special'){
    const label=e.kind===3?'✦ SIÊU TÂN TINH!':'⚡ XUNG TUYẾN!';
    float(e.side?0:1,label,'#fde047');chord([520,680,880],'triangle',.04);
   }
   if(e.type==='assist'){
    banner(`⚡ CHI VIỆN · ${e.name}`,e.color||'#67ebd5');
    burst(innerWidth*.28,innerHeight*.45,e.color||'#67ebd5',28);
    spell(e);chord([440,554,659],'sine',.045);
   }
   if(e.type==='awakened'){
    banner('✦ MẦM SINH HỌC III · CẤP CỨU!','#77e7bd');chord([392,523,659,784],'triangle',.05);
   }
   if(e.type==='cast'||e.type==='phase'){
    banner(e.name||'VARKHOS · KHÓA '+e.value,e.color||'#67ebd5');
    burst(innerWidth/2,innerHeight*.4,e.color||'#ffb36c',60);
    spell(e);
    if(e.hero==='enemy')chord([196,164,130],'sawtooth',.06);
    else chord([523,659,784,1046],'triangle',.045);
   }
  }
  const matchWaves=events.filter(e=>e.type==='match');
  if(matchWaves.length){
   const maxChain=Math.max(...matchWaves.map(m=>m.chain||1));
   if(maxChain>=2)float(0,`CHUỖI ×${maxChain}!`,'#67ebd5');
   tone(340*Math.min(1.8,1+(maxChain-1)*.12),'sine',.22,.05);
  }
  if(events.some(e=>e.type==='invalid'))tone(185,'sawtooth',.16,.04);
 }
 function drawSpell(p){
  const t=1-p.life,mx=(p.x+p.tx)/2,my=(p.y+p.ty)/2;
  ctx.save();ctx.globalAlpha=Math.max(0,p.life);ctx.strokeStyle=p.color;ctx.fillStyle=p.color;ctx.shadowColor=p.color;ctx.shadowBlur=20;ctx.lineWidth=3;
  if(p.hero==='lyra'){
   const cx=p.x+(p.tx-p.x)*Math.min(1,t*1.4),cy=p.y+(p.ty-p.y)*Math.min(1,t*1.4);
   for(const s of [-1,1]){ctx.beginPath();ctx.moveTo(p.x,p.y+s*16);ctx.quadraticCurveTo(mx,my-s*45,cx,cy-s*16);ctx.stroke();}
   for(const s of [-1,1]){ctx.beginPath();ctx.moveTo(p.tx-38,p.ty-38*s);ctx.lineTo(p.tx+38,p.ty+38*s);ctx.stroke();}
  }else if(p.hero==='sora'){
   for(let b=-1;b<=1;b++){ctx.beginPath();ctx.moveTo(p.x,p.y);for(let k=1;k<=6;k++){const u=k/6;ctx.lineTo(p.x+(p.tx-p.x)*u,p.y+(p.ty-p.y)*u+(k<6?(k%2?1:-1)*(16+b*8):0));}ctx.stroke();}
   ctx.beginPath();ctx.arc(p.tx,p.ty,24+t*55,0,Math.PI*2);ctx.stroke();
  }else if(p.hero==='nham'){
   for(let ring=0;ring<3;ring++){const r=28+t*65+ring*15;ctx.beginPath();for(let k=0;k<=6;k++){const a=k/6*Math.PI*2+t*(ring%2?1:-1);ctx[k?'lineTo':'moveTo'](p.tx+Math.cos(a)*r,p.ty+Math.sin(a)*r);}ctx.stroke();}
  }else if(p.hero==='da'){
   for(let i=-2;i<=2;i++){const u=Math.min(1,Math.max(0,t*1.5-Math.abs(i)*.08)),ax=p.x+(p.tx-p.x)*u,ay=p.y+i*18+(p.ty-p.y)*u;ctx.beginPath();ctx.moveTo(ax-28,ay-8);ctx.lineTo(ax,ay);ctx.stroke();}
  }else if(p.hero==='kinh'){
   const cols=['#67ebd5','#eacb80','#c1a1ec'];
   cols.forEach((c,i)=>{ctx.strokeStyle=c;ctx.beginPath();ctx.moveTo(p.x,p.y);ctx.lineTo(mx,my+(i-1)*24);ctx.lineTo(p.tx,p.ty+(i-1)*18);ctx.stroke();});
   ctx.strokeStyle=p.color;ctx.beginPath();for(let k=0;k<=3;k++){const a=k/3*Math.PI*2+t*2;ctx[k?'lineTo':'moveTo'](mx+Math.cos(a)*(30+t*30),my+Math.sin(a)*(30+t*30));}ctx.stroke();
  }else if(p.hero==='yen'){
   for(let ring=0;ring<3;ring++){ctx.beginPath();ctx.arc(p.tx,p.ty,22+t*70+ring*14,0,Math.PI*2);ctx.stroke();}
   for(let k=0;k<8;k++){const a=k/8*Math.PI*2+t*3,r=20+t*55;ctx.beginPath();ctx.arc(p.tx+Math.cos(a)*r,p.ty+Math.sin(a)*r,4,0,Math.PI*2);ctx.fill();}
  }else{
   for(let ray=-1;ray<=1;ray++){ctx.beginPath();ctx.moveTo(p.x,p.y+ray*14);ctx.lineTo(p.tx,p.ty+ray*14);ctx.stroke();}
   ctx.beginPath();ctx.arc(p.tx,p.ty,26+t*60,0,Math.PI*2);ctx.stroke();
  }
  ctx.restore();
 }
 function frame(time){
  const dt=Math.min(.04,(time-last)/1000||0);last=time;
  if(canvas.width!==innerWidth||canvas.height!==innerHeight){canvas.width=innerWidth;canvas.height=innerHeight;}
  ctx.clearRect(0,0,canvas.width,canvas.height);
  if(!isPaused()){
   for(const r of rings){r.life-=dt;r.r=r.maxR*(1-r.life/r.maxLife);}
   for(const p of particles){p.x+=p.vx*dt;p.y+=p.vy*dt;p.vy+=90*dt;p.life-=dt;}
   for(const s of spells)s.life-=dt;
  }
  rings=rings.filter(r=>r.life>0);
  for(const r of rings){ctx.save();ctx.globalAlpha=Math.max(0,r.life/r.maxLife*.7);ctx.strokeStyle=r.color;ctx.lineWidth=2.5;ctx.beginPath();ctx.arc(r.x,r.y,r.r,0,Math.PI*2);ctx.stroke();ctx.restore();}
  particles=particles.filter(p=>p.life>0);
  for(const p of particles){
   ctx.save();ctx.globalAlpha=Math.min(1,p.life/p.maxLife*1.6);ctx.fillStyle=p.color;ctx.strokeStyle=p.color;
   if(p.spark){ctx.lineWidth=2;ctx.beginPath();ctx.moveTo(p.x,p.y);ctx.lineTo(p.x-p.vx*.035,p.y-p.vy*.035);ctx.stroke();}
   else{ctx.beginPath();ctx.arc(p.x,p.y,p.size,0,Math.PI*2);ctx.fill();}
   ctx.restore();
  }
  for(const s of spells)drawSpell(s);
  spells=spells.filter(s=>s.life>0);ctx.globalAlpha=1;requestAnimationFrame(frame);
 }
 requestAnimationFrame(frame);
 return {play,burst,tone,reduced,stop(){particles=[];spells=[];rings=[];audio?.suspend();},count:()=>particles.length};
}
