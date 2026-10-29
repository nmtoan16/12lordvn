import * as E from '../src/engine.js';import {MISSIONS} from '../src/content.js';import {serialize} from '../src/storage.js';import {writeFileSync} from 'node:fs';
const rows=[];
for(let run=1;run<=20;run++){
 const p=E.newProfile();p.modules=[3,3,3,0,0,0];p.equipped=[0,1,2];
 for(const m of MISSIONS){
  // Testing every mission even if an earlier attempt lost; no stats modified mid-fight.
  p.unlocked=m.id;p.battle=E.newBattle(p,m.id,run*100+m.id+1);const s=p.battle;let actions=0;
  for(;actions<600&&s.status==='playing';actions++){
   if(s.turn)E.enemyStep(s);
   else if(s.player.hp<s.player.maxHp*.4&&s.player.mana>=10){if(s.active!==2)E.switchHero(s,2);E.cast(s,0);}
   else {if(s.active!==0)E.switchHero(s,0);if(s.player.mana>=28&&!s.cooldowns[0])E.cast(s,2);else if(s.player.mana>=10)E.cast(s,0);else{const move=E.bestMove(s);E.swap(s,move.a,move.b);}}
   serialize(p);
  }
  if(s.status==='won')E.reward(p);rows.push({run,mission:m.id+1,result:s.status,actions,hp:s.player.hp,phase:s.phase,level:E.level(p.xp)});
 }
}
const report={policy:'20 seeded attempts per mission; level grows from earned rewards, modules damage/shield/mana III (all legally buyable with free shortfall), Lyra/Yen tactical switch; no mid-fight stat edits.',battles:rows.length,wins:rows.filter(x=>x.result==='won').length,missions:MISSIONS.map(m=>({mission:m.id+1,name:m.name,wins:rows.filter(x=>x.mission===m.id+1&&x.result==='won').length,outOf:20})),rows};
writeFileSync('evidence/balance.json',JSON.stringify(report,null,2));console.log(JSON.stringify({...report,rows:undefined},null,2));if(rows.some(r=>r.result==='playing'))process.exitCode=1;
