'use strict';
const test = require('node:test');
const assert = require('node:assert/strict');
const { skillDefinition, planSkill, describeSkill } = require('../runtime/equipment/skills.js');
const board = () => Array.from({ length: 12 }, (_, row) => Array.from({ length: 12 }, (_, col) => (row + col) % 6));
function seeded(seed) { return () => ((seed = (Math.imul(seed, 1664525) + 1013904223) >>> 0) / 4294967296); }

test('tome gating follows all nine slots and never upgrades AI', () => {
  for (let id = 0; id < 9; id++) {
    for (let tier = 0; tier <= 3; tier++) assert.equal(skillDefinition(id, tier).upgraded, tier >= id % 3 + 1);
    assert.equal(planSkill({ id, tomeTier: 3, side: 1, attack: 100, maxHp: 1000, board: board(), rng: seeded(7) }).definition.upgraded, false);
  }
});

test('nine awakened skills have the specified independently quoted outputs', () => {
  const expected = [
    [135, 0, 0, 0, 0, 0], [200, 0, 150, 0, 0, 0], [330, 0, 0, 0, 0, 1],
    [120, 0, 0, 0, 4, 0], [120, 150, 0, 0, 0, 0], [280, 0, 0, 12, 0, 0],
    [120, 0, 0, 12, 6, 0], [0, 300, 200, 0, 0, 0], [220, 150, 0, 24, 0, 3]
  ];
  for (let id = 0; id < 9; id++) {
    const { effects } = planSkill({ id, tomeTier: 3, side: 0, attack: 100, maxHp: 1000, board: board(), rng: seeded(7) });
    assert.deepEqual([effects.damage, effects.heal, effects.barrier, effects.manaDrain, effects.manaGain, effects.freezeTurns], expected[id], `skill ${id}`);
  }
});

test('fire patterns select legal regions and do not duplicate destroyed or converted cells', () => {
  for (let id = 0; id < 9; id++) {
    const plan = planSkill({ id, tomeTier: 3, side: 0, attack: 100, maxHp: 1000, board: board(), rng: seeded(98) });
    const keys = plan.cells.map(cell => `${cell.row}:${cell.col}`);
    assert.equal(new Set(keys).size, keys.length);
    assert.ok(plan.cells.every(cell => cell.row >= 2 && cell.row <= 9 && cell.col >= 2 && cell.col <= 9));
    if (id === 0) {
      assert.equal(plan.cells.length, 9);
      assert.equal(plan.convertToFire.length, 2);
      assert.ok(plan.convertToFire.every(cell => !keys.includes(`${cell.row}:${cell.col}`)));
    }
    if (id === 1 || id === 5) assert.ok(plan.regions.length >= 5 && plan.regions.length <= 6);
    if (id === 2) assert.equal(plan.cells.length, 32);
  }
});

test('same-kind selection clamps to availability and ice consumes all hearts', () => {
  const sparse = Array.from({ length: 8 }, () => Array(8).fill(7));
  sparse[1][1] = 2; sparse[3][3] = 2; sparse[7][7] = 2;
  const lightning = planSkill({ id: 3, tomeTier: 1, attack: 25, maxHp: 100, board: sparse, rng: () => 0 });
  assert.equal(lightning.cells.length, 3);
  assert.ok(lightning.cells.every(cell => cell.tile === 2));
  const heal = planSkill({ id: 7, tomeTier: 2, attack: 25, maxHp: 109, board: sparse, rng: () => 0 });
  assert.equal(heal.cells.length, 3);
  assert.equal(heal.effects.heal, 32);
  // Original Java divides maxHP by 10 before multiplying by 2.
  assert.equal(planSkill({ id: 7, tomeTier: 0, attack: 25, maxHp: 109, board: sparse }).effects.heal, 20);
  assert.match(describeSkill(7, 2, { maxHp: 109 }), /32 HP/);
});

test('original lightning storm has at most four generated regions despite requested five', () => {
  const definition = skillDefinition(5, 0);
  assert.equal(definition.pattern.max, 4);
  assert.equal(skillDefinition(4, 2).shieldTurns, 8);
  assert.equal(skillDefinition(5, 3).shieldTurns, 3);
  assert.equal(skillDefinition(2, 3).lifeStealPercent, 20);
});
