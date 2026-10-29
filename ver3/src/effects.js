export function createEffects(settings,isPaused){
 const canvas=document.getElementById('fx'),ctx=canvas.getContext('2d');let particles=[],spells=[],last=0,audio;
 const reduced=()=>settings().reduced||matchMedia('(prefers-reduced-motion: reduce)').matches;
 function tone(f=440){if(!settings().sound)return;try{audio??=new AudioContext();audio.resume();const o=audio.createOscillator(),g=audio.createGain();o.type='sine';o.frequency.setValueAtTime(f,audio.currentTime);o.frequency.exponentialRampToValueAtTime(f*.5,audio.currentTime+.2);g.gain.setValueAtTime(.055,audio.currentTime);g.gain.exponentialRampToValueAtTime(.001,audio.currentTime+.25);o.connect(g).connect(audio.destination);o.start();o.stop(audio.currentTime+.26);}catch{}}
 function burst(x,y,color,count=20){if(reduced())return;for(let i=0;i<count&&particles.length<160;i++){const angle=Math.random()*Math.PI*2;particles.push({x,y,vx:Math.cos(angle)*(50+Math.random()*150),vy:Math.sin(angle)*(50+Math.random()*150),life:.5+Math.random()*.4,color});}}
 function float(side,value,color){if(!value)return;const box=document.getElementById(side?'enemy':'player')?.getBoundingClientRect();if(!box)return;const el=document.createElement('span');el.className='damage-number';el.style.cssText=`left:${box.left+box.width/2}px;top:${box.top+60}px;color:${color}`;el.textContent=value;document.body.append(el);setTimeout(()=>el.remove(),1200);}
 function spell(e){if(reduced())return;const source=document.getElementById(e.hero==='enemy'?'enemy':'player')?.getBoundingClientRect(),target=document.getElementById(['nham','yen'].includes(e.hero)?'player':e.hero==='enemy'?'player':'enemy')?.getBoundingClientRect();if(!source||!target)return;spells.push({hero:e.hero,x:source.left+source.width/2,y:source.top+source.width/2,tx:target.left+target.width/2,ty:target.top+Math.min(130,target.width/2),color:e.color||'#ffb36c',life:1});}
 function play(events){for(const e of events){if(e.type==='hit'){float(e.side,e.value?'-'+e.value:'BỊ CHẶN','#ffb36c');if(e.absorbed)float(e.side,'◇ '+e.absorbed,'#c5abff');}
  if(e.type==='hp')float(e.side,'+'+e.value,'#80f0ba');
  if(e.type==='cast'||e.type==='phase'){const el=document.createElement('div');el.className='cast-banner';el.textContent=e.name||'VARKHOS · KHÓA '+e.value;document.body.append(el);setTimeout(()=>el.remove(),1400);burst(innerWidth/2,innerHeight*.4,e.color||'#ffb36c',60);spell(e);tone(e.hero==='enemy'?130:620);}
 }if(events.some(e=>e.type==='match'))tone(340);}
 function frame(time){const dt=Math.min(.04,(time-last)/1000||0);last=time;if(canvas.width!==innerWidth||canvas.height!==innerHeight){canvas.width=innerWidth;canvas.height=innerHeight;}ctx.clearRect(0,0,canvas.width,canvas.height);
  if(!isPaused())for(const p of particles){p.x+=p.vx*dt;p.y+=p.vy*dt;p.vy+=80*dt;p.life-=dt;}
  particles=particles.filter(p=>p.life>0);for(const p of particles){ctx.globalAlpha=Math.min(1,p.life*2);ctx.fillStyle=p.color;ctx.fillRect(p.x,p.y,3,3);}
  for(const p of spells){if(!isPaused())p.life-=dt;ctx.save();ctx.globalAlpha=Math.max(0,p.life);ctx.strokeStyle=p.color;ctx.shadowColor=p.color;ctx.shadowBlur=18;ctx.lineWidth=3;const radius=25+(1-p.life)*85;
   if(['nham','yen','sora','kinh'].includes(p.hero)){const sides=p.hero==='nham'?6:p.hero==='kinh'?3:0;for(let ring=0;ring<3;ring++){ctx.beginPath();const r=radius+ring*13;if(sides){for(let k=0;k<=sides;k++){const a=k/sides*Math.PI*2+(1-p.life);ctx[k?'lineTo':'moveTo'](p.tx+Math.cos(a)*r,p.ty+Math.sin(a)*r);}}else ctx.arc(p.tx,p.ty,r,ring,Math.PI*2+ring-.5);ctx.stroke();}}
   else for(let ray=-1;ray<=1;ray++){ctx.beginPath();ctx.moveTo(p.x,p.y+ray*12);ctx.lineTo(p.tx,p.ty+ray*12);ctx.stroke();}
   ctx.restore();}spells=spells.filter(p=>p.life>0);ctx.globalAlpha=1;requestAnimationFrame(frame);
 }requestAnimationFrame(frame);
 return {play,burst,tone,reduced,stop(){particles=[];spells=[];audio?.suspend();},count:()=>particles.length};
}
