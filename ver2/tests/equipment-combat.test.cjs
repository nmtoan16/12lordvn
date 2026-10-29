'use strict';
const test = require('node:test');
const assert = require('node:assert/strict');
const { CombatSession } = require('../runtime/equipment/combat.js');
const fighter = (extra = {}) => ({ hp: 1000, maxHp: 1000, mana: 0, maxMana: 36, food: 1000, maxFood: 1000, ...extra });
const session = (tiers, player = {}, enemy = {}) => new CombatSession({ tiers, fighters: [fighter(player), fighter(enemy)] });

test('zero equipment preserves integer item armor, immunity, clamp and raw skill damage', () => {
  const battle = session([0, 0, 0, 0, 0, 0], {}, { hp: 10, itemArmor: true });
  assert.equal(battle.damage({ id: 1, side: 0, target: 1, source: 'skill', amount: 39 }).hpLoss, 9);
  assert.equal(battle.fighters[1].hp, 1);
  battle.fighters[1].immune = true;
  assert.equal(battle.damage({ id: 2, side: 0, target: 1, source: 'skill', amount: 900 }).hpLoss, 0);
  battle.fighters[1].immune = false;
  assert.equal(battle.damage({ id: 3, side: 0, target: 1, source: 'skill', amount: 100 }).hpLoss, 1);
});

test('sword offense and crit apply once to player, with stable event ID and no AI buff', () => {
  const battle = session([2, 0, 0, 0, 0, 0]);
  const hit = { id: 'sword1', side: 0, source: 'sword', amount: 100, groupSize: 4 };
  assert.equal(battle.damage(hit).hpLoss, 149);
  battle.damage(hit);
  assert.equal(battle.fighters[1].hp, 851);
  assert.equal(battle.damage({ id: 'ai', side: 1, source: 'sword', amount: 100, groupSize: 4 }).hpLoss, 100);
});

test('burn happens once per cast and once per fire group, with no recursion or lifesteal', () => {
  const battle = session([3, 0, 0, 0, 0, 0]);
  const first = battle.damage({ id: 'hit1', castId: 'cast1', side: 0, source: 'skill', amount: 100 });
  assert.equal(first.hpLoss, 150);
  assert.equal(first.burn, 0); // cast completion, not individual damage, owns this proc
  assert.equal(battle.cast({ id:'cast1',side:0,slot:0,skillId:0,payCost:false }).burn,42);
  const second = battle.damage({ id: 'hit2', castId: 'cast1', side: 0, source: 'skill-board', amount: 100 });
  assert.equal(second.burn, 0);
  assert.equal(battle.fighters[1].hp, 658);
  const fire = battle.damage({ id: 'fire1', groupId: 'group1', side: 0, source: 'fire-sword', amount: 10 });
  assert.equal(fire.burn, 32); // floor((658-15)*.05)
  const castFire = battle.damage({ id:'fire2',castId:'cast1',groupId:'group2',side:0,source:'skill-board',fireGroup:true,amount:10 });
  assert.equal(castFire.burn,29); // separate fire group in the same cast, floor((611-15)*.05)
});

test('successful defensive/healing casts burn once even without a Tome; failed casts never burn',()=>{
  for(const skillId of [4,7]){
    const battle=session([3,0,0,0,0,0],{mana:24});
    const cast={id:'cast1',side:0,slot:1,skillId};
    assert.equal(battle.cast(cast).burn,50);assert.equal(battle.fighters[1].hp,950);
    battle.cast(cast);assert.equal(battle.fighters[1].hp,950);
    assert.equal(battle.cast({...cast,id:'no-mana'}).successful,false);assert.equal(battle.fighters[1].hp,950);
  }
});

test('item armor then equipment reduction then barrier then HP', () => {
  const battle = session([0, 0, 3, 0, 0, 0], { itemArmor: true });
  battle.start();
  const result = battle.damage({ id: 1, side: 1, source: 'skill', amount: 4000 });
  assert.deepEqual([result.reduced, result.absorbed, result.hpLoss, result.reflected], [700, 500, 200, 175]);
  assert.equal(battle.fighters[0].hp, 800);
  assert.equal(battle.fighters[1].hp, 825);
  assert.equal(battle.barrier, 0);
});

test('lightning immunity preserves barrier but still reflects reduced pre-absorption hit', () => {
  const battle = session([0, 0, 3, 0, 0, 0], { immune: true });
  battle.start();
  const result = battle.damage({ id: 1, side: 1, source: 'sword', amount: 100 });
  assert.deepEqual([result.hpLoss, result.absorbed, result.reflected], [0, 0, 17]);
  assert.equal(battle.barrier, 500);
  assert.equal(battle.fighters[0].hp, 1000);
});

test('barrier cap and no reflection when there was no shield before the hit', () => {
  const battle = session([0, 0, 3, 0, 0, 0]);
  battle.start();
  battle.addBarrier({ id: 'large', amount: 5000 });
  assert.equal(battle.barrier, 1000);
  battle.barrier = 0;
  assert.equal(battle.damage({ id: 1, side: 1, source: 'skill', amount: 100 }).reflected, 0);
});

test('healing bonus is limited to heart/potion/skill, regen once per actual action', () => {
  const battle = session([0, 3, 0, 0, 0, 0], { hp: 100 });
  assert.equal(battle.heal({ id: 1, side: 0, amount: 100, source: 'heart' }).amount, 180);
  assert.equal(battle.heal({ id: 2, side: 0, amount: 100, source: 'ginseng' }).amount, 100);
  assert.equal(battle.endAction({ id: 'action1', side: 0 }).regen, 40);
  battle.endAction({ id: 'action1', side: 0 });
  battle.endAction({ id: 'enemy-action', side: 1 });
  assert.equal(battle.fighters[0].hp, 420);
});

test('lifesteal uses actual enemy HP lost, combining spell and pearl rates once', () => {
  const battle = session([0, 3, 0, 0, 3, 0], { hp: 300 }, { hp: 10 });
  battle.beginCast({ id: 'cast1', side: 0, skillId: 2, slot: 2, lifeStealPercent: 20 });
  const result = battle.damage({ id: 1, castId: 'cast1', side: 0, source: 'skill', amount: 10000 });
  assert.equal(result.hpLoss, 10);
  assert.equal(result.lifesteal, 4); // floor(10 * (.25 + .20)), no +80% bonus
  assert.equal(battle.fighters[0].hp, 304);
});

test('emergency healing is below strictly 25%, once, never revives', () => {
  const battle = session([0, 3, 0, 0, 0, 0], { hp: 350 });
  battle.damage({ id: 1, side: 1, source: 'skill', amount: 100 });
  assert.equal(battle.fighters[0].hp, 250);
  battle.damage({ id: 2, side: 1, source: 'skill', amount: 1 });
  assert.equal(battle.fighters[0].hp, 549);
  battle.damage({ id: 3, side: 1, source: 'skill', amount: 400 });
  assert.equal(battle.fighters[0].hp, 149);
  battle.damage({ id: 4, side: 1, source: 'skill', amount: 149 });
  assert.equal(battle.fighters[0].hp, 0);
  assert.equal(battle.emergencyUsed, true);
});

test('start and restore never reissue mana, barrier, food or emergency flags', () => {
  const battle = session([0, 3, 3, 3, 0, 0]);
  battle.start();
  assert.deepEqual([battle.fighters[0].mana, battle.barrier], [20, 500]);
  battle.damage({ id: 1, side: 1, source: 'skill', amount: 100 });
  battle.fighters[0].mana = 3;
  const snapshot = JSON.parse(JSON.stringify(battle.snapshot()));
  const restored = CombatSession.restore(snapshot);
  restored.start();
  assert.deepEqual([restored.fighters[0].mana, restored.barrier], [3, 430]);
  restored.damage({ id: 1, side: 1, source: 'skill', amount: 100 });
  assert.equal(restored.barrier, 430);
});

test('successful casts refund exactly once and no-cost mode does not deduct original mana twice', () => {
  const battle = session([0, 0, 2, 3, 0, 0], { mana: 36 });
  const result = battle.cast({ id: 'cast1', side: 0, slot: 2, skillId: 2 });
  assert.deepEqual([battle.fighters[0].mana, result.bonusTurns, battle.barrier], [10, 1, 100]);
  battle.cast({ id: 'cast1', side: 0, slot: 2, skillId: 2 });
  assert.equal(battle.fighters[0].mana, 10);
  const failed = battle.cast({ id: 'cast2', side: 0, slot: 1, skillId: 1 });
  assert.equal(failed.successful, false);
  assert.equal(battle.fighters[0].mana, 10);
  battle.fighters[0].mana = 0; // original Java has just deducted 36
  battle.cast({ id: 'cast3', side: 0, slot: 2, skillId: 2, payCost: false });
  assert.equal(battle.fighters[0].mana, 10);
  assert.equal(battle.barrier, 200);
});

test('mana group bonus is once per group and shield prevents drain but not casting cost', () => {
  const battle = session([0, 0, 3, 2, 0, 0]);
  battle.start();
  battle.manaGain({ id: 'yin1', groupId: 'g1', side: 0, source: 'yin', amount: 3 });
  battle.manaGain({ id: 'yin1', groupId: 'g1', side: 0, source: 'yin', amount: 3 });
  assert.equal(battle.fighters[0].mana, 20);
  assert.equal(battle.manaDrain({ id: 'drain1', side: 0, amount: 12 }).amount, 0);
  assert.equal(battle.manaDrain({ id: 'cost1', side: 0, amount: 12, forced: true }).amount, 12);
  assert.equal(battle.fighters[0].mana, 8);
});

test('food consumes exactly 750 per 1000 ticks; pause and AI keep their own rules', () => {
  const battle = session([0, 0, 0, 0, 0, 1]);
  battle.start();
  assert.equal(battle.fighters[0].maxFood, 1300);
  for (let id = 0; id < 1000; id++) battle.consumeFood({ id, side: 0, amount: 1 });
  assert.equal(battle.fighters[0].food, 550);
  assert.equal(battle.consumeFood({ id: 'pause', side: 0, amount: 120, paused: true }).amount, 0);
  assert.equal(battle.consumeFood({ id: 'ai', side: 1, amount: 120 }).amount, 120);
  const restored = CombatSession.restore(JSON.parse(JSON.stringify(battle.snapshot())));
  assert.equal(restored.fighters[0].maxFood, 1300);
});

test('board rewards only, invalid swaps halved without reflection or lifesteal', () => {
  const battle = session([0, 0, 3, 0, 0, 3]);
  battle.start();
  assert.equal(battle.reward({ id: 1, side: 0, source: 'board', kind: 'gold', amount: 7 }).amount, 14);
  assert.equal(battle.reward({ id: 2, side: 0, source: 'quest', kind: 'xp', amount: 7 }).amount, 7);
  assert.equal(battle.reward({ id: 3, side: 1, source: 'board', kind: 'gold', amount: 7 }).amount, 7);
  const hit = battle.damage({ id: 'bad-swap', side: 0, target: 0, source: 'invalid-swap', amount: 103 });
  assert.deepEqual([hit.modified, hit.reduced, hit.reflected, hit.lifesteal], [51, 35, 0, 0]);
});

test('drained events contain actual HP/barrier changes and are isolated from state', () => {
  const battle = session([0, 0, 1, 0, 0, 0]);
  battle.start(); battle.drainEvents();
  battle.damage({ id: 1, side: 1, source: 'skill', amount: 100 });
  const events = battle.drainEvents();
  assert.ok(events.some(event => event.type === 'block' && event.amount === 90));
  assert.equal(battle.drainEvents().length, 0);
  assert.equal(battle.barrier, 110);
});

test('combat history remains bounded and saved state stays inside the extension record limit', () => {
  const battle = session([3, 3, 3, 0, 0, 0]);
  for (let id = 0; id < 2000; id++) {
    battle.fighters[1].hp = 1000;
    battle.damage({ id: `hit${id}`, castId: `cast${id}`, side: 0, source: 'skill', amount: 1 });
    battle.beginCast({ id: `cast${id}`, side: 0, slot: 0, skillId: 0 });
  }
  assert.ok(battle._results.size <= 512);
  assert.ok(battle.events.length <= 512);
  const snapshot = battle.snapshot();
  assert.ok(snapshot.procs.length <= 64);
  assert.ok(snapshot.casts.length <= 32);
  assert.ok(Buffer.byteLength(JSON.stringify(snapshot)) < 24000);
  battle.endAction({ id: 'finished-action', side: 0 });
  assert.deepEqual(battle.snapshot().procs, []);
  assert.deepEqual(battle.snapshot().casts, []);
});
