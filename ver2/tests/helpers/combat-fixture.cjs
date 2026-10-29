'use strict';
const { expect } = require('@playwright/test');
const { start, press, waitScreen, readState } = require('./vm.cjs');

// Explicit fixtures below alter only this disposable browser context. Every
// tested rule is entered through a real interpreted Java method via LoanVM.
async function enterTraining(page, beforeBattle) {
  const frame = await start(page);
  await expect.poll(() => frame.evaluate(() => LoanV2.state().ready)).toBe(true);
  await press(page, 'Enter');
  await waitScreen(frame, 'df');
  for (let i = 0; i < 18 && (await readState(frame)).screen === 'df'; i++) { await page.waitForTimeout(250); await press(page, 'e'); }
  await waitScreen(frame, 'com/mic/smsgame/suquan/z');
  await press(page, 'q');
  await expect(frame.locator('#web-player-name')).toBeVisible();
  await frame.locator('#web-player-name').fill('KIEMTHU');
  await frame.getByRole('button', { name: 'Xong', exact: true }).click();
  await waitScreen(frame, 'com/mic/smsgame/suquan/aa');
  if (beforeBattle) await beforeBattle(frame);
  for (let i = 0; i < 40 && (await readState(frame)).screen !== 'com/mic/smsgame/suquan/ab'; i++) {
    const state = await readState(frame);
    if (state.mapPhase === 2) await press(page, 'e');
    else if (state.mapPhase === 3) await press(page, 'q');
    await page.waitForTimeout(250);
  }
  await waitScreen(frame, 'com/mic/smsgame/suquan/ab');
  await frame.evaluate(() => {
    const H = LoanJavaHooks, address = LoanV2.current().address;
    H.set(H.get(address, 'com/mic/smsgame/suquan/ab', 'e', 'Lbu;'), 'bu', 'v', 'I', 100);
  });
  return frame;
}

async function configureBattle(frame, options = {}) {
  return frame.evaluate(async options => {
    const H = LoanJavaHooks;
    const screen = LoanV2.current().address;
    const controller = H.get(screen, 'com/mic/smsgame/suquan/ab', 'e', 'Lbu;');
    const model = H.get(controller, 'bu', 's', 'Lcq;');
    const fighters = Array.from(J2ME.getArrayFromAddr(H.get(model, 'cq', 'f', '[Lbn;')));
    const tiers = options.tiers || [0, 0, 0, 0, 0, 0];
    const profile = LoanV2.profile();
    profile.ownedTiers = tiers.slice(); profile.equippedIds = tiers.map((tier, id) => tier ? id : -1).filter(id => id >= 0);
    if (profile.equippedIds.length > 3) throw Error('Fixture exceeds three equipped items');
    for (let side = 0; side < 2; side++) {
      const address = fighters[side], fighter = options.fighters?.[side] || {};
      const config = H.get(address, 'bn', 'm', 'Lc;');
      const hp = fighter.hp === undefined ? 1000 : fighter.hp;
      const maxHp = fighter.maxHp || 1000;
      for (const [field, value] of Object.entries({ b: maxHp, c: hp, d: hp, h: 36, i: fighter.mana || 0, f: 1000, g: fighter.food === undefined ? 1000 : fighter.food, j: 0, k: 0 })) H.set(address, 'bn', field, 'I', value);
      H.set(address, 'bn', 'e', 'Z', fighter.immune ? 1 : 0);
      for (const field of ['b', 'c']) H.set(config, 'c', field, 'I', maxHp);
      H.set(config, 'c', 'd', 'I', fighter.attack || 100);
      const buffs = J2ME.getArrayFromAddr(H.get(address, 'bn', 'a', '[Ldx;'));
      Array.from(buffs).forEach((buff,index) => { H.set(buff, 'dx', 'a', 'I', index); H.set(buff, 'dx', 'b', 'I', 0); });
      if (fighter.itemArmor) H.set(buffs[3], 'dx', 'b', 'I', 5);
    }
    H.set(model, 'cq', 'd', 'Z', 0);
    H.set(model, 'cq', 'k', 'I', options.side || 0);
    H.set(model, 'cq', 'b', 'I', 5); H.set(model, 'cq', 'c', 'I', 240);
    const rows = J2ME.getArrayFromAddr(H.get(model, 'cq', 'e', '[[I'));
    for (let row = 0; row < 12; row++) for (let col = 0; col < 12; col++) J2ME.getArrayFromAddr(rows[row])[col] = row >= 2 && row < 10 && col >= 2 && col < 10 ? (row * 2 + col) % 6 : 7;
    J2ME.getArrayFromAddr(H.get(controller, 'bu', 'I', '[I')).fill(0);
    J2ME.getArrayFromAddr(H.get(controller, 'bu', 'J', '[I')).fill(0);
    for (const [field, value] of Object.entries({ v: 100, D: 1, G: 1, H: 1, z: 0, C: 0 })) H.set(controller, 'bu', field, 'I', value);
    await LoanVM.invoke('bu.a.([BI)V', [controller, 0, 0]);
    return { controller, model, fighters };
  }, options);
}

async function snapshot(frame) {
  return frame.evaluate(() => {
    const H = LoanJavaHooks, state = LoanCombatBridge.state();
    const screen = LoanV2.current().address, controller = H.get(screen, 'com/mic/smsgame/suquan/ab', 'e', 'Lbu;');
    const entries = J2ME.getArrayFromAddr(H.get(controller, 'bu', 'y', '[Lcc;'));
    const groups = Array.from(entries).slice(0, H.get(controller, 'bu', 'z')).map(address => Object.fromEntries(['a','b','c','d','e','f'].map(name => [name,H.get(address,'cc',name)])));
    const rows = J2ME.getArrayFromAddr(H.get(state.modelAddress, 'cq', 'e', '[[I'));
    return { ...state, shield: Array.from(J2ME.getArrayFromAddr(H.get(controller, 'bu', 'I', '[I'))), freeze: Array.from(J2ME.getArrayFromAddr(H.get(controller, 'bu', 'J', '[I'))), bonusTurns: H.get(controller, 'bu', 'G'), groups, board: Array.from(rows).slice(2,10).map(row => Array.from(J2ME.getArrayFromAddr(row)).slice(2,10)) };
  });
}
module.exports = { enterTraining, configureBattle, snapshot };
