import {HEROES,MISSIONS,MODULES} from './content.js';
export const SIZE=8;
export const clone=x=>JSON.parse(JSON.stringify(x));
export const level=xp=>1+Math.floor(Math.sqrt(xp/100));
export function random(s,n){s.seed^=s.seed<<13;s.seed^=s.seed>>>17;s.seed^=s.seed<<5;s.seed>>>=0;return s.seed%n;}
export function matches(board){
 const groups=[];
 for(let axis=0;axis<2;axis++)for(let line=0;line<SIZE;line++){
  let start=0;while(start<SIZE){const at=i=>axis?i*SIZE+line:line*SIZE+i,t=board[at(start)];let end=start+1;
   while(end<SIZE&&board[at(end)]===t)end++;
   if(t>=0&&end-start>=3)groups.push({type:t,axis,cells:Array.from({length:end-start},(_,i)=>at(start+i))});start=end;
  }
 }return groups;
}
export function adjacent(a,b){return Number.isInteger(a)&&Number.isInteger(b)&&a>=0&&b>=0&&a<64&&b<64&&(Math.abs(a-b)===8||(Math.floor(a/8)===Math.floor(b/8)&&Math.abs(a-b)===1));}
export function moves(board){const out=[];for(let a=0;a<64;a++)for(const b of [a+1,a+8])if(adjacent(a,b)){
 const copy=board.slice();[copy[a],copy[b]]=[copy[b],copy[a]];const groups=matches(copy);if(groups.length)out.push({a,b,groups});
 }return out;}
export function boardFor(s){
 for(let attempt=0;attempt<200;attempt++){const b=[];for(let i=0;i<64;i++){
  let tile;do{tile=random(s,7);}while((i%8>=2&&b[i-1]===tile&&b[i-2]===tile)||(i>=16&&b[i-8]===tile&&b[i-16]===tile));b.push(tile);
 }if(moves(b).length)return b;}throw Error('Không tạo được bàn cờ.');
}
export function newProfile(){return {version:3,credits:360,xp:0,unlocked:0,cleared:[],team:['lyra','sora','yen'],modules:[0,0,0,0,0,0],equipped:[],battle:null,settings:{sound:false,reduced:false},endingSeen:false};}
const mod=(s,id)=>s.loadout.includes(id)?s.tiers[id]:0;
export function heroOf(s){return HEROES.find(h=>h.id===s.team[s.active]);}
export function hasHero(s,id){return heroOf(s)?.id===id?1:s.team.includes(id)?.5:0;}
export function newBattle(profile,index,seed=Date.now()){
 if(!MISSIONS[index]||index>profile.unlocked)throw Error('Nhiệm vụ chưa mở.');
 const hero=HEROES.find(h=>h.id===profile.team[0]),l=level(profile.xp),m=MISSIONS[index];
 const s={version:3,mission:index,seed:(seed>>>0)||1,board:[],specials:Array(64).fill(0),team:profile.team.slice(),active:0,loadout:profile.equipped.slice(),tiers:profile.modules.slice(),turn:0,round:1,phase:1,status:'playing',switched:false,seedSaved:false,chain:0,cooldowns:[0,0,0,0,0,0],loot:0,data:0,log:[],events:[],
 player:{hp:hero.hp+(l-1)*24,maxHp:hero.hp+(l-1)*24,attack:hero.attack+(l-1)*2,mana:8,shield:0,battery:100,burn:0,stun:0,reflect:0},
 enemy:{hp:m.hp,maxHp:m.hp,attack:m.attack,mana:0,shield:0,battery:100,burn:0,stun:0,reflect:0}};
 s.player.mana+=mod(s,2)*5;s.player.shield+=mod(s,1)*15+Math.round(12*hasHero(s,'sora'));s.player.battery+=mod(s,4)*20;s.board=boardFor(s);
 if(mod(s,5)===3)s.specials[27]=1;
 note(s,'Nhiệm vụ bắt đầu. Ghép xung kích để tấn công.');return s;
}
function note(s,text){s.log.unshift(text);s.log.length=Math.min(s.log.length,35);}
function event(s,type,data={}){s.events.push({type,...data});}
function hit(s,side,amount,pierce=false,reflect=true){
 const target=side===0?s.enemy:s.player;let d=Math.max(0,Math.round(amount));
 const absorbed=pierce?0:Math.min(target.shield,d);target.shield-=absorbed;
 const reduction=side===1?0.12*hasHero(s,'nham'):0;
 const effective=side===1&&reduction>0?Math.floor((d-absorbed)*(1-reduction)):d-absorbed;
 const lost=Math.min(target.hp,effective);target.hp-=lost;
 event(s,'hit',{side:1-side,value:lost,absorbed,pierce});
 if(side===1&&absorbed>0&&target.shield===0&&mod(s,1)===3&&s.enemy.hp>0){
  hit(s,0,16,false,false);note(s,'Màng Bảo Hộ III phóng xung phản 16 sát thương!');
 }
 if(side===1&&target.hp>0&&target.hp<=target.maxHp*.35&&!s.seedSaved&&mod(s,3)===3){
  s.seedSaved=true;gain(s,0,'hp',45);gain(s,0,'shield',25);note(s,'Mầm Sinh Học III kích hoạt Cấp Cứu (+45 HP, +25 Khiên)!');event(s,'awakened',{id:'seed'});
 }
 if(reflect&&target.reflect>0&&lost>0)hit(s,1-side,Math.ceil(lost*.35),false,false);
}
function gain(s,side,key,value){const f=side?s.enemy:s.player,cap=key==='hp'?f.maxHp:key==='mana'?40:key==='shield'?100:200;if(key==='hp'&&f.hp<=0)return;const old=f[key];f[key]=Math.min(cap,f[key]+Math.round(value));if(f[key]>old)event(s,key,{side,value:f[key]-old});}
function endCheck(s){
 if(s.player.hp<=0){s.status='lost';note(s,'La bàn tín hiệu đã tắt. Hãy đổi đội hình và thử lại.');return;}
 if(s.enemy.hp<=0){s.status='won';note(s,'Khu vực được giải phóng. Dư Quang đã chiến thắng!');return;}
 if(s.mission===11){const next=s.enemy.hp<=s.enemy.maxHp/3?3:s.enemy.hp<=s.enemy.maxHp*2/3?2:1;
  while(s.phase<next){s.phase++;s.enemy.shield=Math.min(100,s.enemy.shield+25);s.enemy.attack+=3;s.player.mana=Math.max(0,s.player.mana-5);note(s,`Varkhos mở KHÓA ${s.phase}: +25 khiên, +3 Công, hút 5 năng lượng!`);event(s,'phase',{value:s.phase});}
 }
}
function expandSpecials(s,side,initialCells){
 if(!Array.isArray(s.specials)||s.specials.length!==64)s.specials=Array(64).fill(0);
 const set=new Set(initialCells),queue=[...set],visited=new Set();
 while(queue.length){
  const i=queue.shift();if(visited.has(i))continue;visited.add(i);
  const kind=s.specials[i];if(!kind)continue;
  s.specials[i]=0;const r=Math.floor(i/8),c=i%8;const extraCells=[];
  if(kind===1)for(let col=0;col<8;col++)extraCells.push(r*8+col);
  else if(kind===2)for(let row=0;row<8;row++)extraCells.push(row*8+c);
  else if(kind===3)for(let dr=-1;dr<=1;dr++)for(let dc=-1;dc<=1;dc++)if(r+dr>=0&&r+dr<8&&c+dc>=0&&c+dc<8)extraCells.push((r+dr)*8+(c+dc));
  for(const n of extraCells)if(!set.has(n)){set.add(n);queue.push(n);}
  if(kind===3){hit(s,side,18);gain(s,side,'mana',6);gain(s,side,'battery',6);note(s,'Lõi Siêu Tân Tinh phát nổ vùng 3×3 (+18 ST, +6 NL, +6 Pin)!');}
  else{hit(s,side,12);note(s,`Ô Xung Tuyến quét sạch ${kind===1?'hàng ngang':'cột dọc'} (+12 ST)!`);}
  event(s,'special',{cell:i,kind,side,cells:extraCells});
 }
 return [...set];
}
function collect(s,side,cells,multiplier=1){
 const expanded=expandSpecials(s,side,cells);
 const counts=Array(7).fill(0);for(const i of expanded)if(s.board[i]>=0)counts[s.board[i]]++;
 const f=side?s.enemy:s.player,boost=side?1:1+mod(s,0)*.1;
 if(counts[0])hit(s,side,counts[0]*f.attack*multiplier*boost*(1+(!side?.15*hasHero(s,'lyra'):0)));
 gain(s,side,'mana',Math.round(counts[1]*(3+(!side?hasHero(s,'kinh'):0))));
 gain(s,side,'hp',Math.round(counts[2]*(7+(!side?mod(s,3)*2+3*hasHero(s,'yen'):0))*multiplier));
 gain(s,side,'shield',Math.round(counts[3]*6*multiplier));gain(s,side,'battery',counts[4]*12);
 if(!side){s.loot+=counts[5]*8;s.data+=counts[6]*4;}
 else{gain(s,side,'mana',counts[5]+counts[6]);}
 event(s,'match',{cells:expanded,counts,side,chain:s.chain+1,board:s.board.slice(),specials:s.specials.slice()});
 for(const i of expanded){s.board[i]=-1;s.specials[i]=0;}
 for(let col=0;col<8;col++){
  const kept=[],keptSpec=[];
  for(let row=7;row>=0;row--)if(s.board[row*8+col]>=0){kept.push(s.board[row*8+col]);keptSpec.push(s.specials[row*8+col]||0);}
  for(let row=7;row>=0;row--){const idx=7-row;s.board[row*8+col]=kept[idx]??random(s,7);s.specials[row*8+col]=keptSpec[idx]??0;}
 }
 endCheck(s);
}
function resolve(s,side,initial){
 if(!Array.isArray(s.specials)||s.specials.length!==64)s.specials=Array(64).fill(0);
 let groups=initial||matches(s.board),chain=0,extra=false;
 const pendingSpawns=[];
 while(groups.length&&s.status==='playing'&&chain<24){
  const cellCounts=new Map();for(const g of groups)for(const c of g.cells)cellCounts.set(c,(cellCounts.get(c)||0)+1);
  for(const g of groups){
   if(g.cells.length>=4){
    extra=true;
    const isCross=g.cells.some(c=>cellCounts.get(c)>1);
    const kind=g.cells.length>=5||isCross?3:g.axis===1?2:1;
    pendingSpawns.push({cell:g.cells[0],kind});
   }else if(g.cells.some(c=>cellCounts.get(c)>1)){
    pendingSpawns.push({cell:g.cells[0],kind:3});
   }
  }
  collect(s,side,groups.flatMap(g=>g.cells),1+Math.min(chain,5)*.15);
  chain++;groups=matches(s.board);
 }
 s.chain=chain;
 if(groups.length||!moves(s.board).length){s.board=boardFor(s);s.specials=Array(64).fill(0);note(s,'Bàn cờ tự tái cấu trúc để luôn có nước đi.');event(s,'shuffle');}
 if(s.status==='playing')for(const sp of pendingSpawns){s.specials[sp.cell]=sp.kind;event(s,'crystallize',sp);}
 if(!side&&extra){
  const daFactor=hasHero(s,'da');if(daFactor>0)hit(s,0,Math.round(20*daFactor));
  if(mod(s,5)===3){s.loot+=4;s.data+=4;}
 }
 if(chain>1)note(s,`Chuỗi ${chain}: sát thương & hồi phục tăng +${Math.min(chain-1,5)*15}%!`);
 endCheck(s);return extra;
}
function beginTurn(s,side){
 s.turn=side;const f=side?s.enemy:s.player;
 if(f.reflect>0)f.reflect--;
 if(!side&&mod(s,1)===3&&f.hp>0)gain(s,0,'shield',8);
 if(f.burn>0){f.burn--;hit(s,1-side,8,true,false);note(s,`${side?'Đối thủ':'Biệt đội'} chịu 8 sát thương đốt.`);}
 endCheck(s);if(s.status!=='playing')return;
 if(f.stun>0){f.stun--;note(s,`${side?'Đối thủ':'Biệt đội'} bị choáng, mất lượt.`);event(s,'stun',{side});beginTurn(s,1-side);return;}
 if(!side){s.round++;s.switched=false;s.cooldowns=s.cooldowns.map(n=>Math.max(0,n-1));}
}
function finish(s,side,extra=false){
 if(s.status!=='playing')return;
 const f=side?s.enemy:s.player;f.battery=Math.max(0,f.battery-6);if(f.battery===0){hit(s,1-side,10,true,false);note(s,'Cạn pin: mất 10 HP. Ghép ô pin để duy trì.');}
 endCheck(s);if(s.status!=='playing')return;
 if(extra){note(s,`${side?'Đối thủ':'Bạn'} ghép 4+ và được thêm lượt!`);event(s,'extra',{side});}
 else beginTurn(s,1-side);
}
export function swap(s,a,b){
 if(s.status!=='playing'||!adjacent(a,b))return false;
 if(!Array.isArray(s.specials)||s.specials.length!==64)s.specials=Array(64).fill(0);
 s.events=[];s.chain=0;[s.board[a],s.board[b]]=[s.board[b],s.board[a]];[s.specials[a],s.specials[b]]=[s.specials[b],s.specials[a]];
 const groups=matches(s.board);
 if(!groups.length){[s.board[a],s.board[b]]=[s.board[b],s.board[a]];[s.specials[a],s.specials[b]]=[s.specials[b],s.specials[a]];note(s,'Chưa tạo được bộ 3. Hãy thử nước khác.');event(s,'invalid',{cells:[a,b]});return false;}
 const side=s.turn;event(s,'swap',{side,cells:[a,b]});
 const extra=resolve(s,side,groups);finish(s,side,extra);return true;
}
function skillCells(s,clear){const center=random(s,64),row=Math.floor(center/8),col=center%8;
 if(typeof clear==='number')return s.board.flatMap((t,i)=>t===clear?[i]:[]);
 return s.board.flatMap((_,i)=>{const r=Math.floor(i/8),c=i%8;return (clear==='row'&&r===row||clear==='double'&&(r===row||r===(row+1)%8)||clear==='column'&&c===col||clear==='area'&&Math.abs(r-row)<=1&&Math.abs(c-col)<=1)?[i]:[];});
}
export function cast(s,index){
 if(s.status!=='playing'||s.turn!==0||!Number.isInteger(index)||index<0||index>2)return false;
 const h=heroOf(s),skill=h.skills[index],hi=HEROES.indexOf(h);if(s.player.mana<skill.cost||index===2&&s.cooldowns[hi])return false;
 if(!Array.isArray(s.specials)||s.specials.length!==64)s.specials=Array(64).fill(0);
 s.events=[];s.player.mana-=skill.cost;if(index===2)s.cooldowns[hi]=4;
 const bonusAtk=Math.max(0,s.player.attack-h.attack),edgeTier=mod(s,0),edgeMult=1+edgeTier*.1+(edgeTier===3?.12:0);
 note(s,`${h.name} · ${skill.name}`);event(s,'cast',{hero:h.id,name:skill.name,color:h.color,slot:index});
 if(skill.cleanse)s.player.burn=0;
 if(skill.damage){
  let dmg=skill.damage+bonusAtk*(skill.scale||1.8);
  if(skill.shieldSlam)dmg+=Math.floor(s.player.shield*skill.shieldSlam);
  if(skill.shieldBreak&&s.enemy.shield>0)dmg*=skill.shieldBreak;
  const finalDmg=dmg*edgeMult;
  hit(s,0,finalDmg,skill.pierce);
  if(edgeTier===3||(skill.vamp&&s.enemy.burn>0))gain(s,0,'hp',Math.ceil(finalDmg*.15));
 }
 if(skill.heal)gain(s,0,'hp',skill.heal+bonusAtk*1.5+mod(s,3)*4);
 if(skill.shield)gain(s,0,'shield',skill.shield+bonusAtk*1.2);
 if(skill.manaGain)gain(s,0,'mana',skill.manaGain);
 if(skill.batteryGain)gain(s,0,'battery',skill.batteryGain);
 if(mod(s,2)===3)gain(s,0,'mana',4);
 for(const key of ['burn','stun'])if(skill[key])s.enemy[key]=Math.max(s.enemy[key],skill[key]);
 if(skill.reflect)s.player.reflect=skill.reflect+1;if(skill.drain)s.enemy.mana=Math.max(0,s.enemy.mana-skill.drain);
 endCheck(s);
 if(skill.clear!==undefined&&s.status==='playing'){const cells=skillCells(s,skill.clear);event(s,'skillClear',{cells,color:h.color});collect(s,0,cells);resolve(s,0);}
 if(skill.special&&s.status==='playing')s.specials[27]=skill.special;
 finish(s,0);return true;
}
export function switchHero(s,index){
 if(s.status!=='playing'||s.turn||s.switched||!s.team[index]||index===s.active)return false;
 if(!Array.isArray(s.specials)||s.specials.length!==64)s.specials=Array(64).fill(0);
 s.active=index;s.switched=true;s.events=[];const h=heroOf(s);
 if(h.id==='lyra'&&s.enemy.hp>1)hit(s,0,Math.min(s.enemy.hp-1,10),false,false);
 else if(h.id==='sora'){s.enemy.mana=Math.max(0,s.enemy.mana-5);gain(s,0,'mana',4);}
 else if(h.id==='nham')gain(s,0,'shield',14);
 else if(h.id==='da'&&s.enemy.hp>1)hit(s,0,Math.min(s.enemy.hp-1,12),true,false);
 else if(h.id==='kinh'){gain(s,0,'mana',5);s.specials[27]=s.specials[27]||1;}
 else if(h.id==='yen'){gain(s,0,'hp',18);gain(s,0,'battery',8);}
 if(mod(s,4)===3){gain(s,0,'mana',5);gain(s,0,'battery',12);}
 note(s,`${h.name} vào trận · Chi viện: ${h.assist.name}!`);
 event(s,'assist',{hero:h.id,name:h.assist.name,color:h.color});
 return true;
}
export function enemyIntent(s){
 const m=MISSIONS[s.mission],e=s.enemy,ready=e.mana>=18;
 const labels={burn:'Đốt 8 HP ×3 lượt',heal:'Hồi 40 HP',shield:'Tạo lá chắn bảo vệ',drain:'Rút 8 năng lượng đội',stun:'Làm choáng 1 lượt',reflect:'Phản 35% sát thương',boss:`Khóa Bình Minh Pha ${s.phase}`};
 return {ready,trait:m.trait,title:ready?`⚠ SẮP TUNG: ${(labels[m.trait]||m.trait).toUpperCase()}`:`TÍCH NĂNG LƯỢNG (${e.mana}/18)`,detail:labels[m.trait]||''};
}
export function bestMove(s,side=s.turn){
 const f=side?s.enemy:s.player;const weights=[5,f.mana<18?3:1,f.hp<f.maxHp*.5?7:1,f.shield<20?3:1,f.battery<35?8:1,.5,.5];
 return moves(s.board).map(m=>({...m,score:m.groups.reduce((v,g)=>v+g.cells.length*weights[g.type]+(g.cells.length>=4?18:0)+g.cells.reduce((acc,c)=>acc+(s.specials?.[c]?14:0),0),0)})).sort((a,b)=>b.score-a.score)[0];
}
export function enemyStep(s){
 if(s.turn!==1||s.status!=='playing')return false;s.events=[];const m=MISSIONS[s.mission],e=s.enemy;
 if(e.mana>=18){e.mana-=18;const trait=m.trait;note(s,`${m.enemy} kích hoạt ${trait==='boss'?'Khóa Bình Minh':'giao thức '+trait.toUpperCase()}.`);event(s,'cast',{name:trait==='boss'?'KHÓA BÌNH MINH':'GIAO THỨC '+trait.toUpperCase(),color:'#ff796e',hero:'enemy'});
  hit(s,1,e.attack*(trait==='boss'?3:2));
  if(trait==='shield'||trait==='boss')gain(s,1,'shield',20+s.phase*5);
  if(trait==='heal')gain(s,1,'hp',40);if(trait==='burn')s.player.burn=3;
  if(trait==='stun')s.player.stun=1;if(trait==='drain'||trait==='boss')s.player.mana=Math.max(0,s.player.mana-8);
  if(trait==='reflect')e.reflect=3;finish(s,1);return true;
 }
 const move=bestMove(s,1);if(!move){s.board=boardFor(s);s.specials=Array(64).fill(0);return true;}return swap(s,move.a,move.b);
}
export function reward(profile){
 const s=profile.battle;if(!s||s.status!=='won'||s.rewarded)return null;const m=MISSIONS[s.mission],mult=1+mod(s,5)*.1,first=!profile.cleared.includes(m.id);
 const receipt={credits:Math.floor((m.reward+s.loot)*mult*(first?1:.5)),xp:Math.floor((m.xp+s.data)*mult*(first?1:.5))};
 profile.credits+=receipt.credits;profile.xp+=receipt.xp;if(first)profile.cleared.push(m.id);profile.unlocked=Math.min(11,Math.max(profile.unlocked,m.id+1));s.rewarded=true;s.receipt=receipt;return receipt;
}
export function purchase(profile,id){if(!MODULES[id]||profile.modules[id]>=3)throw Error('Module đã đạt tối đa.');const price=MODULES[id].price[profile.modules[id]],charged=Math.min(profile.credits,price);profile.credits-=charged;profile.modules[id]++;return {price,charged};}
export function equip(profile,id){if(!profile.modules[id])throw Error('Cần sở hữu module trước.');const at=profile.equipped.indexOf(id);if(at>=0)profile.equipped.splice(at,1);else{if(profile.equipped.length>=3)throw Error('Chỉ được mang 3 module. Tháo một module trước.');profile.equipped.push(id);}}
