'use strict';
const {test,expect}=require('@playwright/test');
const {enterTraining,configureBattle,snapshot}=require('./helpers/combat-fixture.cjs');

test('Hỏa Lôi Băng: all nine real Java casts keep baseline/AI parity and awaken player spells',async({page})=>{
  const errors=[];page.on('pageerror',error=>errors.push(error.message));
  const frame=await enterTraining(page);
  const expectedBase=[100,150,250,80,0,200,80,0,150],expectedUp=[135,200,330,120,120,280,120,0,220];
  for(const mode of ['tier0','tier1','tier2','tier3','AI'])for(let id=0;id<9;id++){
    const side=mode==='AI'?1:0,tier=mode==='AI'?3:Number(mode.slice(-1)),slot=id%3,upgraded=side===0&&tier>slot;
    const context=await configureBattle(frame,{tiers:[0,0,0,0,tier,0],side,
      fighters:[{hp:side===0?500:1000,mana:36,maxHp:1000,attack:100},{hp:side===1?500:10000,mana:36,maxHp:side===1?1000:10000,attack:100}]});
    const result=await frame.evaluate(async ({context,id,slot,side})=>{
      const H=LoanJavaHooks,address=context.fighters[side],config=H.get(address,'bn','m','Lc;');
      // Allocate the fixture int[] while an actual Java context owns the heap.
      await LoanVM.invoke('bn.o.()I',[address],()=>{const list=J2ME.newIntArray(3);J2ME.getArrayFromAddr(list).set([Math.floor(id/3)*3,Math.floor(id/3)*3+1,Math.floor(id/3)*3+2]);H.set(config,'c','f','[I',list);});
      await LoanVM.invoke('bu.a.(I)V',[context.controller,slot],()=>{H.set(context.controller,'bu','v','I',100);});
      return {counts:LoanJavaHooks.counts['bu.a.(I)V']};
    },{context,id,slot,side});
    expect(result.counts).toBeGreaterThan(0);
    const state=await snapshot(frame),caster=state.fighters[side],target=state.fighters[1-side];
    expect(target.hp,`${mode} skill${id} damage`).toBe((side===0?10000:1000)-(upgraded?expectedUp[id]:expectedBase[id]));
    const heal=upgraded?(id===2?66:id===4||id===8?150:id===7?300:0):id===7?200:0;
    expect(caster.hp,`${mode} skill${id} heal`).toBe(500+heal);
    expect(caster.mana,`${mode} skill${id} mana`).toBe(36-12*(slot+1)+(upgraded?(id===3?4:id===6?6:0):0));
    const drain=id===6?(upgraded?12:8):id===8?(upgraded?24:18):upgraded&&id===5?12:0;
    expect(target.mana,`${mode} skill${id} drain`).toBe(36-drain);
    expect(state.shield[side],`${mode} skill${id} shield`).toBe(id===4?(upgraded?8:6):upgraded&&id===5?3:0);
    expect(state.freeze[1-side],`${mode} skill${id} freeze`).toBe(id===8?(upgraded?4:3):upgraded&&id===2?2:0);
    expect(state.barrier,`${mode} skill${id} barrier`).toBe(upgraded?(id===1?150:id===7?200:0):0);
    if(upgraded){
      const coords=state.groups.map(group=>`${group.a}:${group.b}`);expect(new Set(coords).size).toBe(coords.length);
      expect(state.groups.every(g=>g.a>=2&&g.a<=9&&g.b>=2&&g.b<=9)).toBe(true);
      if(id===0){expect(state.groups).toHaveLength(9);expect(state.board.flat().filter(tile=>tile===6)).toHaveLength(2);}
      if(id===1)expect([20,24]).toContain(state.groups.length);
      if(id===2)expect(state.groups).toHaveLength(32);
      if(id===3){expect(state.groups.length).toBeGreaterThanOrEqual(7);expect(state.groups.length).toBeLessThanOrEqual(10);expect(new Set(state.groups.map(g=>g.e)).size).toBe(1);}
      if(id===6){expect(state.groups.length).toBeGreaterThanOrEqual(4);expect(state.groups.length).toBeLessThanOrEqual(5);}
      if(id===8){expect(state.groups.length).toBeGreaterThanOrEqual(5);expect(state.groups.length).toBeLessThanOrEqual(7);}
    }
  }
  expect(errors).toEqual([]);
});

test('real Java casts with insufficient mana do not change HP, board, buffs or refund',async({page})=>{
  const frame=await enterTraining(page);
  for(const slot of [0,1,2]){
    const context=await configureBattle(frame,{tiers:[0,0,0,3,3,0],fighters:[{mana:0,hp:500},{mana:36}]});
    await frame.evaluate(({fighters,slot})=>LoanJavaHooks.set(fighters[0],'bn','i','I',12*(slot+1)-1),{...context,slot});
    const before=await snapshot(frame);
    await frame.evaluate(({controller,slot})=>LoanVM.invoke('bu.a.(I)V',[controller,slot]),{...context,slot});
    const after=await snapshot(frame);
    expect(after.fighters).toEqual(before.fighters);expect(after.barrier).toBe(before.barrier);
    expect(after.shield).toEqual(before.shield);expect(after.freeze).toEqual(before.freeze);
    expect(after.bonusTurns).toBe(before.bonusTurns);expect(after.board).toEqual(before.board);
  }
});

test('healing and shield casts burn once at Tome 0/III; insufficient mana never burns',async({page})=>{
  const frame=await enterTraining(page);
  await frame.evaluate(()=>{window.loanBurnProbe=[];LoanV2.onCombat(event=>window.loanBurnProbe.push(event));});
  for(const tomeTier of [0,3])for(const id of [4,7])for(const enoughMana of [true,false]){
    const context=await configureBattle(frame,{tiers:[3,0,0,0,tomeTier,0],fighters:[{hp:500,mana:enoughMana?36:23},{hp:10000,maxHp:10000,mana:36}]});
    const events=await frame.evaluate(async ({context,id})=>{
      const H=LoanJavaHooks,address=context.fighters[0],config=H.get(address,'bn','m','Lc;');window.loanBurnProbe=[];
      await LoanVM.invoke('bn.o.()I',[address],()=>{const list=J2ME.newIntArray(3);J2ME.getArrayFromAddr(list).set([Math.floor(id/3)*3,Math.floor(id/3)*3+1,Math.floor(id/3)*3+2]);H.set(config,'c','f','[I',list);});
      await LoanVM.invoke('bu.a.(I)V',[context.controller,1],()=>H.set(context.controller,'bu','v','I',100));
      return window.loanBurnProbe;
    },{context,id});
    const state=await snapshot(frame);
    const damage=enoughMana&&id===4&&tomeTier===3?180:0;
    const burn=enoughMana?Math.floor((10000-damage)*.05):0;
    expect(state.fighters[1].hp).toBe(10000-damage-burn);
    expect(events.filter(e=>e.type==='cast')).toHaveLength(enoughMana?1:0);
    expect(events.filter(e=>e.type==='damage'&&e.source==='burn')).toHaveLength(enoughMana?1:0);
    if(enoughMana)expect(events.find(e=>e.source==='burn').amount).toBe(burn);
  }
});
