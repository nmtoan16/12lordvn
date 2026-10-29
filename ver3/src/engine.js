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
   if(t>=0&&end-start>=3)groups.push({type:t,cells:Array.from({length:end-start},(_,i)=>at(start+i))});start=end;
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
export function newBattle(profile,index,seed=Date.now()){
 if(!MISSIONS[index]||index>profile.unlocked)throw Error('Nhiệm vụ chưa mở.');
 const hero=HEROES.find(h=>h.id===profile.team[0]),l=level(profile.xp),m=MISSIONS[index];
 const s={version:3,mission:index,seed:(seed>>>0)||1,board:[],team:profile.team.slice(),active:0,loadout:profile.equipped.slice(),tiers:profile.modules.slice(),turn:0,round:1,phase:1,status:'playing',switched:false,chain:0,cooldowns:[0,0,0,0,0,0],loot:0,data:0,log:[],events:[],
 player:{hp:hero.hp+(l-1)*24,maxHp:hero.hp+(l-1)*24,attack:hero.attack+(l-1)*2,mana:8,shield:0,battery:100,burn:0,stun:0,reflect:0},
 enemy:{hp:m.hp,maxHp:m.hp,attack:m.attack,mana:0,shield:0,battery:100,burn:0,stun:0,reflect:0}};
 s.player.mana+=mod(s,2)*5;s.player.shield+=mod(s,1)*15+(hero.id==='sora'?12:0);s.player.battery+=mod(s,4)*20;s.board=boardFor(s);note(s,'Nhiệm vụ bắt đầu. Ghép xung kích để tấn công.');return s;
}
function note(s,text){s.log.unshift(text);s.log.length=Math.min(s.log.length,35);}
function event(s,type,data={}){s.events.push({type,...data});}
function heroOf(s){return HEROES.find(h=>h.id===s.team[s.active]);}
function hit(s,side,amount,pierce=false,reflect=true){
 const target=side===0?s.enemy:s.player;let d=Math.max(0,Math.round(amount));
 const absorbed=pierce?0:Math.min(target.shield,d);target.shield-=absorbed;
 const effective=side===1&&heroOf(s).id==='nham'?Math.floor((d-absorbed)*.88):d-absorbed;
 const lost=Math.min(target.hp,effective);target.hp-=lost;
 event(s,'hit',{side:1-side,value:lost,absorbed});
 if(reflect&&target.reflect>0&&lost>0)hit(s,1-side,Math.ceil(lost*.35),false,false);
}
function gain(s,side,key,value){const f=side?s.enemy:s.player,cap=key==='hp'?f.maxHp:key==='mana'?40:key==='shield'?100:200;if(key==='hp'&&f.hp<=0)return;const old=f[key];f[key]=Math.min(cap,f[key]+value);if(f[key]>old)event(s,key,{side,value:f[key]-old});}
function endCheck(s){
 if(s.player.hp<=0){s.status='lost';note(s,'La bàn tín hiệu đã tắt. Hãy đổi đội hình và thử lại.');return;}
 if(s.enemy.hp<=0){s.status='won';note(s,'Khu vực được giải phóng. Dư Quang đã chiến thắng!');return;}
 if(s.mission===11){const next=s.enemy.hp<=s.enemy.maxHp/3?3:s.enemy.hp<=s.enemy.maxHp*2/3?2:1;
  while(s.phase<next){s.phase++;s.enemy.shield=Math.min(100,s.enemy.shield+25);s.enemy.attack+=3;s.player.mana=Math.max(0,s.player.mana-5);note(s,`Varkhos mở KHÓA ${s.phase}: +25 khiên, +3 Công, hút 5 năng lượng!`);event(s,'phase',{value:s.phase});}
 }
}
function collect(s,side,cells,multiplier=1){
 const counts=Array(7).fill(0);for(const i of new Set(cells))if(s.board[i]>=0)counts[s.board[i]]++;
 const f=side?s.enemy:s.player,h=heroOf(s),boost=side?1:1+mod(s,0)*.1;
 if(counts[0])hit(s,side,counts[0]*f.attack*multiplier*boost*(!side&&h.id==='lyra'?1.15:1));
 gain(s,side,'mana',counts[1]*(!side&&h.id==='kinh'?4:3));gain(s,side,'hp',counts[2]*(7+(!side?mod(s,3)*2+(h.id==='yen'?3:0):0)));
 gain(s,side,'shield',counts[3]*6);gain(s,side,'battery',counts[4]*12);
 if(!side){s.loot+=counts[5]*8;s.data+=counts[6]*4;}
 else{gain(s,side,'mana',counts[5]+counts[6]);}
 event(s,'match',{cells:[...new Set(cells)],counts,side,board:s.board.slice()});
 for(const i of new Set(cells))s.board[i]=-1;
 for(let col=0;col<8;col++){const kept=[];for(let row=7;row>=0;row--)if(s.board[row*8+col]>=0)kept.push(s.board[row*8+col]);for(let row=7;row>=0;row--)s.board[row*8+col]=kept[7-row]??random(s,7);}
 endCheck(s);
}
function resolve(s,side,initial){
 let groups=initial||matches(s.board),chain=0,extra=false;
 while(groups.length&&s.status==='playing'&&chain<24){
  if(groups.some(g=>g.cells.length>=4))extra=true;
  collect(s,side,groups.flatMap(g=>g.cells),1+Math.min(chain,5)*.15);chain++;groups=matches(s.board);
 }
 s.chain=chain;
 if(groups.length||!moves(s.board).length){s.board=boardFor(s);note(s,'Bàn cờ tự tái cấu trúc để luôn có nước đi.');event(s,'shuffle');}
 if(!side&&extra&&heroOf(s).id==='da')hit(s,0,20);
 if(chain>1)note(s,`Chuỗi ${chain}: sát thương tăng theo mỗi đợt cộng hưởng.`);
 endCheck(s);return extra;
}
function beginTurn(s,side){
 s.turn=side;const f=side?s.enemy:s.player;
 if(f.reflect>0)f.reflect--;
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
 s.events=[];s.chain=0;[s.board[a],s.board[b]]=[s.board[b],s.board[a]];const groups=matches(s.board);
 if(!groups.length){[s.board[a],s.board[b]]=[s.board[b],s.board[a]];note(s,'Chưa tạo được bộ 3. Hãy thử nước khác.');event(s,'invalid',{cells:[a,b]});return false;}
 const side=s.turn,extra=resolve(s,side,groups);finish(s,side,extra);return true;
}
function skillCells(s,clear){const center=random(s,64),row=Math.floor(center/8),col=center%8;
 if(typeof clear==='number')return s.board.flatMap((t,i)=>t===clear?[i]:[]);
 return s.board.flatMap((_,i)=>{const r=Math.floor(i/8),c=i%8;return (clear==='row'&&r===row||clear==='double'&&(r===row||r===(row+1)%8)||clear==='column'&&c===col||clear==='area'&&Math.abs(r-row)<=1&&Math.abs(c-col)<=1)?[i]:[];});
}
export function cast(s,index){
 if(s.status!=='playing'||s.turn!==0||!Number.isInteger(index)||index<0||index>2)return false;
 const h=heroOf(s),skill=h.skills[index],hi=HEROES.indexOf(h);if(s.player.mana<skill.cost||index===2&&s.cooldowns[hi])return false;
 s.events=[];s.player.mana-=skill.cost;if(index===2)s.cooldowns[hi]=4;
 note(s,`${h.name} · ${skill.name}`);event(s,'cast',{hero:h.id,name:skill.name,color:h.color});
 if(skill.damage)hit(s,0,skill.damage*(1+mod(s,0)*.1),skill.pierce);
 if(skill.heal)gain(s,0,'hp',skill.heal);if(skill.shield)gain(s,0,'shield',skill.shield);
 for(const key of ['burn','stun'])if(skill[key])s.enemy[key]=Math.max(s.enemy[key],skill[key]);
 if(skill.reflect)s.player.reflect=skill.reflect+1;if(skill.drain)s.enemy.mana=Math.max(0,s.enemy.mana-skill.drain);
 endCheck(s);if(skill.clear!==undefined&&s.status==='playing'){collect(s,0,skillCells(s,skill.clear));resolve(s,0);}
 finish(s,0);return true;
}
export function switchHero(s,index){if(s.status!=='playing'||s.turn||s.switched||!s.team[index]||index===s.active)return false;s.active=index;s.switched=true;note(s,`${heroOf(s).name} nhận quyền chỉ huy. Nội tại đã thay đổi.`);return true;}
export function bestMove(s,side=s.turn){
 const f=side?s.enemy:s.player;const weights=[5,f.mana<18?3:1,f.hp<f.maxHp*.5?7:1,f.shield<20?3:1,f.battery<35?8:1,.5,.5];
 return moves(s.board).map(m=>({...m,score:m.groups.reduce((v,g)=>v+g.cells.length*weights[g.type]+(g.cells.length>=4?18:0),0)})).sort((a,b)=>b.score-a.score)[0];
}
export function enemyStep(s){
 if(s.turn!==1||s.status!=='playing')return false;s.events=[];const m=MISSIONS[s.mission],e=s.enemy;
 if(e.mana>=18){e.mana-=18;const trait=m.trait;note(s,`${m.enemy} kích hoạt ${trait==='boss'?'Khóa Bình Minh':'giao thức '+trait.toUpperCase()}.`);event(s,'cast',{name:'Giao thức đối thủ',color:'#ff796e',hero:'enemy'});
  hit(s,1,e.attack*(trait==='boss'?3:2));
  if(trait==='shield'||trait==='boss')gain(s,1,'shield',20+s.phase*5);
  if(trait==='heal')gain(s,1,'hp',40);if(trait==='burn')s.player.burn=3;
  if(trait==='stun')s.player.stun=1;if(trait==='drain'||trait==='boss')s.player.mana=Math.max(0,s.player.mana-8);
  if(trait==='reflect')e.reflect=3;finish(s,1);return true;
 }
 const move=bestMove(s,1);if(!move){s.board=boardFor(s);return true;}return swap(s,move.a,move.b);
}
export function reward(profile){
 const s=profile.battle;if(!s||s.status!=='won'||s.rewarded)return null;const m=MISSIONS[s.mission],mult=1+mod(s,5)*.1,first=!profile.cleared.includes(m.id);
 const receipt={credits:Math.floor((m.reward+s.loot)*mult*(first?1:.5)),xp:Math.floor((m.xp+s.data)*mult*(first?1:.5))};
 profile.credits+=receipt.credits;profile.xp+=receipt.xp;if(first)profile.cleared.push(m.id);profile.unlocked=Math.min(11,Math.max(profile.unlocked,m.id+1));s.rewarded=true;s.receipt=receipt;return receipt;
}
export function purchase(profile,id){if(!MODULES[id]||profile.modules[id]>=3)throw Error('Module đã đạt tối đa.');const price=MODULES[id].price[profile.modules[id]],charged=Math.min(profile.credits,price);profile.credits-=charged;profile.modules[id]++;return {price,charged};}
export function equip(profile,id){if(!profile.modules[id])throw Error('Cần sở hữu module trước.');const at=profile.equipped.indexOf(id);if(at>=0)profile.equipped.splice(at,1);else{if(profile.equipped.length>=3)throw Error('Chỉ được mang 3 module. Tháo một module trước.');profile.equipped.push(id);}}
