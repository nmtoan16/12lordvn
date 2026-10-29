'use strict';
const test = require('node:test');
const assert = require('node:assert/strict');
const { catalog, activeTiers } = require('../runtime/equipment/catalog.js');
const { rules } = require('../runtime/equipment/rules.js');

test('catalog contains the eighteen quoted prices without cumulative bonuses', () => {
  assert.deepEqual(catalog.map(item => item.tiers.slice(1).map(t => t.price)), [
    [800, 2200, 5000], [700, 2000, 4500], [900, 2500, 5500],
    [800, 2200, 4800], [1200, 3000, 6000], [600, 1800, 4000]
  ]);
  assert.equal(rules.offense(100, 3, { source: 'sword', groupSize: 4 }), 172);
  assert.equal(rules.offense(100, 3, { source: 'skill', groupSize: 4 }), 150);
  assert.equal(rules.offense(100, 3, { source: 'skill-board', groupSize: 4 }), 172);
  assert.equal(rules.offense(100, 3, { source: 'reflect' }), 100);
});

test('only equipped owned tiers are active, with at most three unique slots', () => {
  assert.deepEqual(activeTiers({ tiers: [3, 2, 1, 3, 2, 1], loadout: [0, 2, 4] }), [3, 0, 1, 0, 2, 0]);
  assert.deepEqual(activeTiers({ ownedTiers: [3, 2, 1, 3, 2, 1], equippedIds: [0, 2, 4] }), [3, 0, 1, 0, 2, 0]);
  assert.throws(() => activeTiers({ tiers: [3, 2, 1, 3, 2, 1], loadout: [0, 0] }), /duplicate/i);
  assert.throws(() => activeTiers({ tiers: [3, 2, 1, 3, 2, 1], loadout: [0, 1, 2, 3] }), /three/i);
});

test('refill fire chance is conditional on a sword, with exact probability boundaries', () => {
  assert.equal(rules.refillTile(0, 2, 0, () => 0.19999), 6);
  assert.equal(rules.refillTile(0, 2, 0, () => 0.2), 0);
  assert.equal(rules.refillTile(0, 3, 0, () => 0.24999), 6);
  assert.equal(rules.refillTile(2, 3, 0, () => 0), 2);
  assert.equal(rules.refillTile(0, 3, 1, () => 0.15), 0);
});

test('exact four new drop is separate from original five-plus drop', () => {
  assert.equal(rules.dropForGroup(4, 2, 0, () => 0.2499), true);
  assert.equal(rules.dropForGroup(4, 2, 0, () => 0.25), false);
  assert.equal(rules.dropForGroup(5, 2, 0, () => { throw Error('must use original drop'); }), null);
  assert.equal(rules.dropForGroup(6, 3, 1, () => { throw Error('must use original drop'); }), null);
});
