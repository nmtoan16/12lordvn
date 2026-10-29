'use strict';
const { test, expect } = require('@playwright/test');
const { enterTraining, configureBattle, snapshot } = require('./helpers/combat-fixture.cjs');

test('actual Java scoring preserves zero-equipment values and player-only sword modifiers', async ({ page }) => {
  const errors=[]; page.on('pageerror', error=>errors.push(error.message));
  const frame=await enterTraining(page);
  let context=await configureBattle(frame,{fighters:[{hp:500,mana:0,food:500,attack:10},{attack:10}]});
  await frame.evaluate(async ({controller})=>{
    const H=LoanJavaHooks,groups=J2ME.getArrayFromAddr(H.get(controller,'bu','y','[Lcc;'));
    [0,2,3,1,4,5].forEach((tile,index)=>{for(const [name,value] of Object.entries({a:2,b:2,c:1,d:3,e:tile,f:3}))H.set(groups[index],'cc',name,'I',value);});
    H.set(controller,'bu','z','I',6);await LoanVM.invoke('bu.x.()V',[controller]);
  },context);
  let state=await snapshot(frame);
  expect(state.fighters.map(f=>({hp:f.hp,mana:f.mana,food:f.food,gold:f.gold,xp:f.xp}))).toEqual([
    {hp:508,mana:3,food:743,gold:3,xp:3},{hp:987,mana:0,food:1000,gold:0,xp:0}
  ]);
  for(const side of [0,1]){
    context=await configureBattle(frame,{tiers:[1,0,0,0,0,0],side,fighters:[{attack:10},{attack:10}]});
    await frame.evaluate(async ({controller})=>{
      const H=LoanJavaHooks,group=J2ME.getArrayFromAddr(H.get(controller,'bu','y','[Lcc;'))[0];
      for(const [name,value] of Object.entries({a:2,b:2,c:1,d:4,e:0,f:4}))H.set(group,'cc',name,'I',value);
      H.set(controller,'bu','z','I',1);await LoanVM.invoke('bu.x.()V',[controller]);
    },context);
    state=await snapshot(frame);expect(state.fighters[1-side].hp).toBe(side===0?977:982);
  }
  expect(errors).toEqual([]);
});

test('actual Java damage respects armor, barrier, reflection and frozen battle loadout',async({page})=>{
  const frame=await enterTraining(page);
  const context=await configureBattle(frame,{tiers:[0,0,3,0,0,0],fighters:[{itemArmor:true},{}]});
  expect((await snapshot(frame)).barrier).toBe(500);
  await frame.evaluate(async ({controller,fighters})=>{
    LoanV2.profile().ownedTiers[2]=1; // purchasing/changing the profile cannot alter the frozen battle snapshot
    await LoanVM.invoke('bu.a.(Lbn;I)V',[controller,fighters[0],4000]);
  },context);
  const state=await snapshot(frame);
  expect(state.activeTiers).toEqual([0,0,3,0,0,0]);
  expect(state.barrier).toBe(0);expect(state.fighters[0].hp).toBe(800);expect(state.fighters[1].hp).toBe(825);
});

test('actual retry resets once-only battle state without multiplying food capacity twice',async({page})=>{
  const frame=await enterTraining(page);
  const context=await configureBattle(frame,{tiers:[0,3,3,0,0,1],fighters:[{hp:100},{}]});
  await frame.evaluate(async ({controller,fighters})=>{
    await LoanVM.invoke('bu.a.(Lbn;I)V',[controller,fighters[0],10]);
    await LoanVM.invoke('bu.c.(I)V',[controller,1]);
  },context);
  const used=await snapshot(frame);expect(used.emergencyUsed).toBe(true);expect(used.foodCarry).toBe(3);
  await frame.evaluate(({controller})=>LoanVM.invoke('bu.a.()V',[controller],()=>LoanJavaHooks.set(controller,'bu','v','I',100)),context);
  const retried=await snapshot(frame);
  expect(retried.id).not.toBe(used.id);expect(retried.emergencyUsed).toBe(false);expect(retried.foodCarry).toBe(0);
  expect(retried.barrier).toBe(500);expect(retried.fighters[0].maxFood).toBe(1300);expect(retried.fighters[0].food).toBe(1300);
});

test('campaign, side-quest and arena result fixtures use original victory/defeat commits',async({page})=>{
  const errors=[];page.on('pageerror',error=>errors.push(error.message));
  const frame=await enterTraining(page);
  for(const mode of ['campaign','side-quest','arena'])for(const victory of [true,false]){
    const context=await configureBattle(frame,{tiers:[0,0,0,0,0,3],fighters:[{hp:victory?500:0}, {hp:victory?0:500}]});
    const result=await frame.evaluate(async ({context,mode,victory})=>{
      const H=LoanJavaHooks,cls=CLASSES.getClass('dd'),stat=LoanVM.runtime.SA[cls.id];
      for(const [key,value]of Object.entries({a:mode==='arena'?0:-1,b:777,c:2,d:mode==='side-quest'?0:-1,i:7,j:9}))H.set(stat,'dd',key,'I',value,true);
      H.set(context.model,'cq','g','I',0);
      // Isolate each outcome from earlier fixtures' XP; otherwise a legitimate
      // original level-up adds HP and changes the arena's restored HP value.
      await LoanVM.invoke('dd.b.(I)V',[0]);await LoanVM.setGold(1000);await LoanVM.flush();
      const terminal=await LoanVM.invoke('bu.y.()Z',[context.controller],value=>{H.set(context.controller,'bu','v','I',100);return value;});
      const won=H.get(context.controller,'bu','E','Z');
      await LoanVM.invoke('bu.t.()V',[context.controller]);
      return {terminal,won,status:await LoanVM.invoke('dd.a.()I',[]),gold:await LoanVM.readGold(),hp:H.get(H.get(stat,'dd','e','Lc;',true),'c','c'),snapshot:await LoanVM.invoke('com/mic/smsgame/suquan/f.e.()Z',[])};
    },{context,mode,victory});
    expect(Boolean(result.terminal),`${mode} terminal`).toBe(true);expect(Boolean(result.won)).toBe(victory);
    expect(result.status).toBe(victory?2:3);expect(result.snapshot).toBe(0);
    expect(result.gold).toBe(mode!=='arena'&&victory?1007:1000); // no stone multiplier on committed campaign rewards
    expect(result.hp).toBe(mode==='arena'?777:victory?500:1000);
  }
  expect(errors).toEqual([]);
});

test('original ginseng retains its base heal while potion uses the equipped healing bonus',async({page})=>{
  const frame=await enterTraining(page);
  const context=await configureBattle(frame,{tiers:[0,3,0,0,0,0],fighters:[{hp:500},{}]});
  await frame.evaluate(({controller})=>LoanVM.invoke('bu.d.(I)V',[controller,1]),context);
  let state=await snapshot(frame);expect(state.fighters[0].maxHp).toBe(2000);expect(state.fighters[0].hp).toBe(1500);
  await frame.evaluate(({controller})=>LoanVM.invoke('bu.d.(I)V',[controller,4]),context);
  state=await snapshot(frame);expect(state.fighters[0].hp).toBe(1860);
});
