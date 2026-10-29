import {newProfile,clone} from './engine.js';import {HEROES} from './content.js';
export const KEY='afterglow-ver3:save',BACKUP=KEY+':backup';
const int=(x,a,b)=>Number.isSafeInteger(x)&&x>=a&&x<=b;
const list=(a,min,max,pred)=>Array.isArray(a)&&a.length>=min&&a.length<=max&&a.every(pred);
const unique=a=>new Set(a).size===a.length;
const hero=id=>HEROES.some(h=>h.id===id);
function check(ok){if(!ok)throw Error('Bản lưu không hợp lệ hoặc thuộc phiên bản khác.');}
export function validate(p){
 check(p&&p.version===3&&int(p.credits,0,1e9)&&int(p.xp,0,1e9)&&int(p.unlocked,0,11));
 check(list(p.team,1,3,hero)&&unique(p.team)&&list(p.modules,6,6,x=>int(x,0,3)));
 check(list(p.equipped,0,3,x=>int(x,0,5)&&p.modules[x]>0)&&unique(p.equipped));
 check(list(p.cleared,0,12,x=>int(x,0,11)&&x<=p.unlocked)&&unique(p.cleared));
 check(p.settings&&typeof p.settings.sound==='boolean'&&typeof p.settings.reduced==='boolean'&&typeof p.endingSeen==='boolean');
 if(p.battle!==null){const s=p.battle;
  check(s&&s.version===3&&int(s.mission,0,p.unlocked)&&int(s.seed,1,4294967295)&&list(s.board,64,64,x=>int(x,0,6)));
  check(list(s.team,1,3,hero)&&unique(s.team)&&int(s.active,0,s.team.length-1)&&[0,1].includes(s.turn)&&int(s.phase,1,3)&&int(s.round,1,1000000));
  check(['playing','won','lost'].includes(s.status)&&typeof s.switched==='boolean'&&int(s.chain,0,24)&&list(s.cooldowns,6,6,x=>int(x,0,4)));
  check(list(s.tiers,6,6,x=>int(x,0,3))&&list(s.loadout,0,3,x=>int(x,0,5)&&s.tiers[x]>0)&&unique(s.loadout));
  check(int(s.loot,0,1e8)&&int(s.data,0,1e8)&&list(s.log,0,35,x=>typeof x==='string'&&x.length<400));
  for(const f of [s.player,s.enemy]){check(f&&int(f.maxHp,1,1000000)&&int(f.hp,0,f.maxHp)&&int(f.attack,1,10000)&&int(f.mana,0,40)&&int(f.shield,0,100)&&int(f.battery,0,200));for(const k of ['burn','stun','reflect'])check(int(f[k],0,10));}
  check(s.status!=='won'||s.enemy.hp===0);check(s.status!=='lost'||s.player.hp===0);
  check(s.rewarded===undefined||typeof s.rewarded==='boolean');if(s.receipt)check(int(s.receipt.credits,0,1e8)&&int(s.receipt.xp,0,1e8));s.events=[];
 }
 return clone(p);
}
export function serialize(profile){return JSON.stringify({format:'afterglow-save',version:3,profile:validate(clone(profile))});}
export function deserialize(text){check(typeof text==='string'&&text.length<200000);const data=JSON.parse(text);check(data.format==='afterglow-save'&&data.version===3);return validate(data.profile);}
export function load(storage){
 try{const raw=storage.getItem(KEY);if(!raw)return {profile:newProfile()};try{return {profile:deserialize(raw)};}catch{
  const backup=storage.getItem(BACKUP);if(backup)return {profile:deserialize(backup),warning:'Đã phục hồi bản lưu dự phòng. Bản lỗi được giữ ở khóa backup-corrupt.'};
  return {profile:newProfile(),blocked:true,warning:'Bản lưu hiện có bị lỗi. Hãy xuất bản gốc trước khi tạo hành trình mới.'};
 }}catch{return {profile:newProfile(),blocked:true,warning:'Trình duyệt không cho truy cập bản lưu. Bật quyền lưu dữ liệu website để tiếp tục.'};}
}
export function save(storage,profile){const text=serialize(profile),old=storage.getItem(KEY);
 if(old){try{deserialize(old);storage.setItem(BACKUP,old);}catch{storage.setItem(KEY+':backup-corrupt',old);}}
 storage.setItem(KEY,text);
}
