'use strict';
(() => {
 const H=LoanJavaHooks,E=LoanEquipment,V=LoanV2;
 let controller=0,model=0,addresses=[],session=null,loadout=null,battleId=null,sequence=0,baseFood=0,action=null,cast=null,casting=false,scoring=null,invalid=false,restoring=false,sidecar=null,pending=Promise.resolve();
 let itemContext=null;
 const get=(a,c,n,s='I')=>H.get(a,c,n,s),set=(a,c,n,v,s='I')=>H.set(a,c,n,s,v);
 const array=addr=>J2ME.getArrayFromAddr(addr),next=label=>`${label}:${++sequence}`;
 function turn(){return model?get(model,'cq','k'):0;}
 function fighter(addr){const buffs=array(get(addr,'bn','a','[Ldx;'));return {hp:get(addr,'bn','c'),maxHp:get(addr,'bn','b'),mana:get(addr,'bn','i'),maxMana:get(addr,'bn','h'),food:get(addr,'bn','g'),maxFood:get(addr,'bn','f'),immune:!!get(addr,'bn','e','Z'),itemArmor:get(buffs[3],'dx','b')>0,gold:get(addr,'bn','j'),xp:get(addr,'bn','k')};}
 function sync(){if(session)addresses.forEach((addr,i)=>Object.assign(session.fighters[i],fighter(addr)));}
 function flush(){if(!session)return;addresses.forEach((addr,i)=>{const f=session.fighters[i];for(const [field,key] of [['c','hp'],['i','mana'],['g','food'],['f','maxFood'],['j','gold'],['k','xp']])set(addr,'bn',field,f[key]);});for(const event of session.drainEvents())V.emitCombat(event);}
 function board(){return Array.from(array(get(model,'cq','e','[[I')),addr=>array(addr));}
 function skillIds(side=0){return Array.from(array(get(get(addresses[side],'bn','m','Lc;'),'c','f','[I')));}
 function attack(side=0){const a=addresses[side],buff=array(get(a,'bn','a','[Ldx;'))[0];return get(get(a,'bn','m','Lc;'),'c','d')*(get(buff,'dx','b')>0?2:1);}
 function group(){if(!scoring)return null;return scoring.groups[scoring.index++]||null;}
 function baseDamage(addr,amount){if(get(addr,'bn','e','Z'))return;const b=array(get(addr,'bn','a','[Ldx;'));if(get(b[3],'dx','b')>0)amount=Math.trunc(amount/4);set(addr,'bn','c',Math.max(0,Math.min(get(addr,'bn','b'),get(addr,'bn','c')-amount)));}
 function evt(source,side,extra={}){return {id:next(source),source,side,actionId:action?.id,castId:cast?.id,...extra};}
 function damage(addr,amount){
   const target=addresses.indexOf(addr);if(!session||target<0)return baseDamage(addr,amount);
   if(casting&&cast?.plan?.definition.upgraded)return;
   sync();const g=group(),side=invalid?target:1-target,source=invalid?'invalid-swap':cast?'skill-board':g?.fire?'fire-sword':'sword';
   const barrierBefore=session.barrier;session.damage(evt(casting?'skill':source,side,{target,amount,groupSize:g?.size||1,groupId:g?.id,fireGroup:!!g?.fire}));flush();
   if(barrierBefore>0&&session.barrier===0)V.emitCombat({type:'barrier-break',side:0,amount:barrierBefore});
 }
 H.replace('bn.k.(I)V',damage);
 H.replace('bn.l.(I)V',(addr,amount)=>{
   const side=addresses.indexOf(addr);if(!session||side<0){set(addr,'bn','c',Math.max(0,Math.min(get(addr,'bn','b'),get(addr,'bn','c')+amount)));return;}
   if(casting&&cast?.plan?.definition.upgraded)return;
   sync();const g=group();session.heal(evt(casting?'skill':g?'heart':itemContext===1?'ginseng':'potion',side,{amount,groupId:g?.id}));flush();
 });
 H.replace('bn.a.(IZ)V',(addr,amount,forced)=>{
   const side=addresses.indexOf(addr);if(casting&&cast?.plan?.definition.upgraded&&!forced)return;
   if(!session||side<0){if(!forced&&get(addr,'bn','e','Z'))return;set(addr,'bn','i',Math.max(0,Math.min(get(addr,'bn','h'),get(addr,'bn','i')-amount)));return;}
   sync();session.manaDrain(evt(forced?'cost':'drain',side,{amount,forced:!!forced}));flush();
 });
 H.replace('bn.f.(I)V',(addr,amount)=>{
   const side=addresses.indexOf(addr);if(!session||side<0){set(addr,'bn','i',Math.max(0,Math.min(get(addr,'bn','h'),get(addr,'bn','i')+amount)));return;}
   sync();const g=group();session.manaGain(evt(g?'yin':'mana',side,{amount,groupId:g?.id}));flush();
 });
 H.observe('bn.i.(I)V',{before(){if(scoring)group();}});
 H.observe('bu.d.(I)V',{before(c){c.data.previous=itemContext;itemContext=c.args[1];},after(c){itemContext=c.data.previous;}});
 for(const [method,kind] of [['a','gold'],['b','xp']])H.observe(`bn.${method}.(I)V`,{before(c){const side=addresses.indexOf(c.args[0]);if(!session||side<0)return;const g=group();if(g)c.setArg(1,E.rules.boardReward(c.args[1],side===0?session.tiers[5]:0,'board'));}});
 H.replace('bn.j.(I)V',(addr,amount)=>{
   const side=addresses.indexOf(addr);if(!session||side<0){set(addr,'bn','g',Math.max(0,Math.min(get(addr,'bn','f'),get(addr,'bn','g')-amount)));return;}
   sync();session.consumeFood(evt('food',side,{amount,paused:!!window.loanPaused}));flush();
 });
 H.observe('bu.a.([BI)V',{before(c){restoring=true;c.data.bytes=c.args[1]?new Uint8Array(array(c.args[1])):null;},after(c){
   controller=c.args[0];model=get(controller,'bu','s','Lcq;');addresses=Array.from(array(get(model,'cq','f','[Lbn;')));baseFood=get(addresses[0],'bn','f');
   const bytes=c.data.bytes,p=V.profile();let recovered=null;
   if(bytes&&sidecar&&sidecar.profileId===p.profileId&&sidecar.snapshotFingerprint===E.battleSave.fingerprintSnapshot(bytes))recovered=sidecar;
   if(recovered){session=E.CombatSession.restore(recovered.state.combat);sequence=recovered.state.sequence;baseFood=recovered.state.baseFood??baseFood;loadout=recovered.loadout;battleId=recovered.battleId;action=recovered.state.action;cast=recovered.state.cast;set(addresses[0],'bn','f',session.fighters[0].maxFood);sync();}
   else {loadout=E.loadout.snapshot(bytes?[0,0,0,0,0,0]:p.ownedTiers,bytes?[]:p.equippedIds);session=new E.CombatSession({tiers:loadout.activeTiers,fighters:addresses.map(fighter)});battleId=E.store.defaultProfile().profileId;sequence=0;action=null;cast=null;if(!bytes)session.start();else session.started=true;}
   restoring=false;casting=false;scoring=null;flush();V.notify();
 }});
 H.observe('bu.f.(I)V',{before(c){
   if(!session||c.args[0]!==controller||restoring)return;
   const state=c.args[1];c.data.invalid=invalid;if(state===2)invalid=true;
   if(state===1){action={id:next('action'),side:turn()};cast=null;}
   if((state===7||state===10&&get(model,'cq','d','Z'))&&action){sync();session.endAction({id:action.id,side:action.side});flush();action=null;cast=null;}
 },after(c){if(c.args[1]===2)invalid=!!c.data.invalid;}});
 H.observe('bu.x.()V',{before(c){if(!session||c.args[0]!==controller)return;const entries=array(get(controller,'bu','y','[Lcc;')),count=get(controller,'bu','z');scoring={index:0,groups:Array.from(entries).slice(0,count).map(addr=>({id:next('group'),size:Math.max(get(addr,'cc','c'),get(addr,'cc','d')),fire:fireGroups.has(addr)}))};},after(){scoring=null;}});
 let fireGroups=new Set();
 H.observe('bu.f.(I)V',{before(c){if(!session||c.args[0]!==controller||c.args[1]!==3)return;fireGroups=new Set();const b=board(),entries=array(get(controller,'bu','y','[Lcc;'));for(const addr of Array.from(entries).slice(0,get(controller,'bu','z'))){const r=get(addr,'cc','a'),col=get(addr,'cc','b');for(let y=r;y<r+get(addr,'cc','c');y++)for(let x=col;x<col+get(addr,'cc','d');x++)if(b[y]?.[x]===6)fireGroups.add(addr);}}});
 let refillDepth=0;
 H.observe('com/mic/smsgame/suquan/w.a.([[III)V',{before(){refillDepth++;},after(){refillDepth--;}});
 H.observe('ay.a.(I)I',{after(c){if(refillDepth&&c.args[0]===100&&session&&turn()===0&&session.tiers[0]>=2)c.result=c.result<E.rules.tables.fireChance[session.tiers[0]]?0:99;}});
 H.observe('i.a.(Lcc;I)I',{before(c){
   if(!session||c.args[2]!==0||session.tiers[5]<2)return;const g=c.args[1],size=Math.max(get(g,'cc','c'),get(g,'cc','d'));
   if(E.rules.dropForGroup(size,session.tiers[5],0)===true){const items=array(get(model,'cq','a','[Ldx;')),choices=[0,1,3,4,5],id=choices[Math.floor(Math.random()*choices.length)];set(items[id],'dx','b',get(items[id],'dx','b')+1);V.emitCombat({type:'drop',side:0,amount:1,itemId:id});pending=pending.then(()=>LoanVM.invoke('bg.a.(III)V',[id,1,6])).then(()=>LoanVM.flush()).catch(V.reportError);}
 }});
 // The original cast still owns cost payment, animation and the FSM. Only its
 // numeric effects and selected cells are replaced for an awakened player spell.
 H.observe('bu.a.(I)V',{before(c){
   if(!session||c.args[0]!==controller)return;const side=turn(),slot=c.args[1],ids=skillIds(side);if(slot<0||slot>=ids.length)return;
   sync();if(session.fighters[side].mana<12*(slot+1)){c.skipReturn=true;return;}
   action={id:next('action'),side};cast={id:next('cast'),side,slot,skillId:ids[slot]};casting=true;
   cast.plan=E.planSkill({id:cast.skillId,side,tomeTier:session.tiers[4],attack:attack(side),maxHp:session.fighters[side].maxHp,board:board().map(row=>Array.from(row))});
   session.beginCast({...cast,lifeStealPercent:cast.plan.effects.lifeStealPercent});
   c.data.buffI=Array.from(array(get(controller,'bu','I','[I')));c.data.buffJ=Array.from(array(get(controller,'bu','J','[I')));c.data.bonus=get(controller,'bu','G');c.data.immune=addresses.map(a=>get(a,'bn','e','Z'));c.data.cast=cast;
 },after(c){
   if(!c.data.cast)return;const active=c.data.cast;casting=false;sync();
   if(active.plan.definition.upgraded){const effects=active.plan.effects,side=active.side,target=1-side,I=array(get(controller,'bu','I','[I')),J=array(get(controller,'bu','J','[I'));
     I.set(c.data.buffI);J.set(c.data.buffJ);set(controller,'bu','G',c.data.bonus);addresses.forEach((a,i)=>set(a,'bn','e',c.data.immune[i],'Z'));sync();
     if(effects.damage>0)session.damage(evt('skill',side,{id:active.id+':hit',castId:active.id,target,amount:effects.damage}));
     if(effects.heal)session.heal(evt('skill',side,{amount:effects.heal}));
     if(effects.barrier)session.addBarrier(evt('skill',side,{amount:effects.barrier}));
     if(effects.manaDrain)session.manaDrain(evt('drain',target,{amount:effects.manaDrain}));
     if(effects.manaGain)session.manaGain(evt('skill',side,{amount:effects.manaGain}));
     if(effects.shieldTurns){I[side]+=effects.shieldTurns;set(addresses[side],'bn','e',1,'Z');}
     if(effects.freezeTurns&&!session.fighters[target].immune){set(controller,'bu','G',get(controller,'bu','G')+effects.freezeTurns);J[target]+=effects.freezeTurns+(J[target]===0?1:0);}
     const b=board();for(const cell of active.plan.convertToFire)b[cell.row][cell.col]=6;
   }
   const result=session.cast({...active,id:active.id,payCost:false,successful:true,manaCost:12*(active.slot+1)});
   if(result.bonusTurns)set(controller,'bu','G',get(controller,'bu','G')+result.bonusTurns);
   flush();V.emitCombat({type:'cast',side:active.side,skillId:active.skillId,amount:1});
 }});
 H.observe('i.a.(II[Lcc;I)I',{before(c){
   if(!casting||!cast?.plan.definition.upgraded)return;
   const cells=cast.plan.cells,entries=array(get(controller,'bu','y','[Lcc;'));
   if(cells.length>entries.length)throw Error('Spell selection exceeds original group capacity');
   cells.forEach((cell,index)=>{const addr=entries[index];for(const [key,value]of Object.entries({a:cell.row,b:cell.col,c:1,d:1,e:cell.tile===6?0:cell.tile,f:1}))set(addr,'cc',key,value);});
   set(controller,'bu','z',cells.length);c.setArg(4,cells.length);
 }});
 H.observe('com/mic/smsgame/suquan/f.a.([BII)V',{before(c){if(!session)return;sync();c.data.bytes=new Uint8Array(array(c.args[0]).slice(c.args[1],c.args[1]+c.args[2]));c.data.context={battleId,profileId:V.profile().profileId,loadout,state:{combat:session.snapshot(),sequence,baseFood,action,cast}};},after(c){
   if(!c.data.bytes)return;
   try{
     // Continue can run before the durability queue drains. Publish a validated,
     // detached copy now; queued saves must never replace a newer cached battle.
     const bytes=c.data.bytes,saved=E.battleSave.createSidecar(bytes,c.data.context);
     sidecar=saved;
     pending=pending.then(()=>E.battleSave.createBattleSave(LoanVM).save(bytes,saved)).catch(V.reportError);
   }catch(error){V.reportError(error);}
 }});
 H.observe('bu.a.()V',{before(c){if(session&&c.args[0]===controller){c.data.retry=true;restoring=true;sidecar=null;set(addresses[0],'bn','f',baseFood);}},after(c){if(!c.data.retry)return;session=new E.CombatSession({tiers:loadout.activeTiers,fighters:addresses.map(fighter)});battleId=E.store.defaultProfile().profileId;sequence=0;action=cast=null;restoring=false;session.start();flush();pending=pending.then(()=>E.battleSave.createBattleSave(LoanVM).clear()).catch(V.reportError);}});
 H.observe('bu.t.()V',{after(){sidecar=null;pending=pending.then(()=>E.battleSave.createBattleSave(LoanVM).clear()).catch(V.reportError);}});
 window.LoanCombatBridge={
   state(){if(!session||V.current().screen!=='com/mic/smsgame/suquan/ab')return null;return {id:battleId,modelAddress:model,activeTiers:[...session.tiers],barrier:session.barrier,maxHP:get(addresses[0],'bn','b'),fighters:addresses.map(fighter),skillIds:skillIds(),attack:attack(),emergencyUsed:session.emergencyUsed,foodCarry:session.foodCarry};},
   async loadSidecar(){const bytes=await LoanVM.read(121);sidecar=bytes?E.battleSave.decodeSidecar(bytes):null;},
   flush:()=>pending,
   reset(){session=null;sidecar=null;controller=model=0;addresses=[];},
   opponent(){if(!LoanVM.runtime)return null;const a=V.staticField('dd','f','Lc;');if(!a)return null;const id=get(a,'c','a'),names=V.staticField('ea','a','[Ljava/lang/String;'),nameAddress=names?array(names)[id]:0;return {name:nameAddress?J2ME.fromStringAddr(nameAddress):`Tướng ${id}`,hp:get(a,'c','b'),attack:get(a,'c','d')};}
 };
})();
