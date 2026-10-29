'use strict';
(function (root) {
  const names = ['Quả Cầu Lửa', 'Mưa Thiên Thạch', 'Lửa Địa Ngục', 'Chuỗi Sét', 'Khiên Sét', 'Sấm Sét', 'Mũi Tên Băng', 'Cam Lộ Thủy', 'Băng Phong'];
  const baseRatios = [[1, 1], [3, 2], [5, 2], [4, 5], [0, 1], [2, 1], [4, 5], [0, 1], [3, 2]];
  const upgradedRatios = [[135, 100], [2, 1], [33, 10], [6, 5], [6, 5], [14, 5], [6, 5], [0, 1], [11, 5]];
  function skillDefinition(id, tomeTier = 0) {
    if (!Number.isInteger(id) || id < 0 || id > 8) throw new RangeError('Skill id must be 0..8');
    if (!Number.isInteger(tomeTier) || tomeTier < 0 || tomeTier > 3) throw new RangeError('Tome tier must be 0..3');
    const slot = id % 3;
    const upgraded = tomeTier > slot;
    const [damageNumerator, damageDenominator] = (upgraded ? upgradedRatios : baseRatios)[id];
    const definition = {
      id, name: names[id], slot, element: ['fire', 'lightning', 'ice'][Math.floor(id / 3)], upgraded,
      damageNumerator, damageDenominator, manaCost: 12 * (slot + 1),
      pattern: { kind: 'none', min: 0, max: 0, width: 0, height: 0 },
      manaDrain: 0, manaGain: 0, healPercent: 0, barrierPercent: 0,
      shieldTurns: 0, freezeTurns: 0, freezeInitialPadding: 0,
      bonusTurns: 0, fireCells: 0, lifeStealPercent: 0
    };
    switch (id) {
      case 0: definition.pattern = { kind: 'regions', min: 1, max: 1, width: 3, height: 3 }; definition.fireCells = upgraded ? 2 : 0; break;
      case 1: definition.pattern = { kind: 'regions', min: upgraded ? 5 : 3, max: upgraded ? 6 : 5, width: 2, height: 2 }; definition.barrierPercent = upgraded ? 15 : 0; break;
      case 2: definition.pattern = { kind: 'split-regions', min: 2, max: 2, width: 4, height: 4 }; definition.lifeStealPercent = upgraded ? 20 : 0; definition.freezeTurns = upgraded ? 1 : 0; break;
      case 3: definition.pattern = { kind: 'same-kind', min: upgraded ? 7 : 4, max: upgraded ? 10 : 8 }; definition.manaGain = upgraded ? 4 : 0; break;
      case 4: definition.shieldTurns = upgraded ? 8 : 6; definition.healPercent = upgraded ? 15 : 0; break;
      // The original constructs four candidate regions, then clamps its 3..5 request to those four.
      case 5: definition.pattern = { kind: 'regions', min: upgraded ? 5 : 3, max: upgraded ? 6 : 4, width: 3, height: 3 }; definition.manaDrain = upgraded ? 12 : 0; definition.shieldTurns = upgraded ? 3 : 0; break;
      case 6: definition.pattern = { kind: 'random', min: upgraded ? 4 : 2, max: upgraded ? 5 : 3 }; definition.manaDrain = upgraded ? 12 : 8; definition.manaGain = upgraded ? 6 : 0; break;
      case 7: definition.pattern = { kind: 'hearts', min: 0, max: 64 }; definition.healPercent = upgraded ? 30 : 20; definition.barrierPercent = upgraded ? 20 : 0; break;
      case 8: definition.pattern = { kind: 'random', min: upgraded ? 5 : 3, max: upgraded ? 7 : 5 }; definition.manaDrain = upgraded ? 24 : 18; definition.freezeTurns = upgraded ? 3 : 2; definition.healPercent = upgraded ? 15 : 0; break;
    }
    // J is decremented at f(7) before the first frozen turn: the original adds this padding on first freeze.
    if (definition.freezeTurns) {
      definition.freezeInitialPadding = 1;
      definition.bonusTurns = definition.freezeTurns;
    }
    return definition;
  }
  function sample(items, count, rng) {
    const shuffled = items.slice();
    const length = Math.min(count, shuffled.length);
    for (let i = 0; i < length; i++) {
      const random = rng();
      if (!Number.isFinite(random) || random < 0 || random >= 1) throw new RangeError('RNG must return [0, 1)');
      const next = i + Math.floor(random * (shuffled.length - i));
      [shuffled[i], shuffled[next]] = [shuffled[next], shuffled[i]];
    }
    return shuffled.slice(0, length);
  }
  function randomInt(min, max, rng) { return sample(Array.from({ length: max - min + 1 }, (_, index) => min + index), 1, rng)[0]; }
  function planSkill(options) {
    const { id, side = 0, tomeTier = 0, attack = 0, maxHp = 0, board, rng = Math.random } = options;
    const definition = skillDefinition(id, side === 0 ? tomeTier : 0);
    if (!Array.isArray(board) || ![8, 12].includes(board.length) || board.some(row => !row || row.length !== board.length)) throw new TypeError('Board must be 8x8 or padded 12x12');
    const start = board.length === 12 ? 2 : 0;
    const end = start + 8;
    const available = [];
    for (let row = start; row < end; row++) for (let col = start; col < end; col++) {
      const tile = board[row][col];
      if (Number.isInteger(tile) && tile >= 0 && tile <= 6) available.push({ row, col, tile });
    }
    const regions = [];
    let selected = [];
    const pattern = definition.pattern;
    if (pattern.kind === 'regions') {
      const candidates = [];
      for (let row = start; row <= end - pattern.height; row += pattern.width === 2 ? 2 : 1) {
        for (let col = start; col <= end - pattern.width; col += pattern.width === 2 ? 2 : 1) candidates.push({ row, col, width: pattern.width, height: pattern.height });
      }
      regions.push(...sample(candidates, randomInt(pattern.min, pattern.max, rng), rng));
    } else if (pattern.kind === 'split-regions') {
      for (let col = start; col < end; col += 4) regions.push({ row: randomInt(start, start + 4, rng), col, width: 4, height: 4 });
    } else if (pattern.kind === 'same-kind') {
      const kinds = [...new Set(available.filter(cell => cell.tile < 6).map(cell => cell.tile))];
      if (kinds.length) {
        const tile = sample(kinds, 1, rng)[0];
        selected = sample(available.filter(cell => cell.tile === tile), randomInt(pattern.min, pattern.max, rng), rng);
      }
    } else if (pattern.kind === 'hearts') selected = available.filter(cell => cell.tile === 2);
    else if (pattern.kind === 'random') selected = sample(available, randomInt(pattern.min, pattern.max, rng), rng);
    if (regions.length) selected = available.filter(cell => regions.some(region => cell.row >= region.row && cell.row < region.row + region.height && cell.col >= region.col && cell.col < region.col + region.width));
    const keys = new Set(selected.map(cell => `${cell.row}:${cell.col}`));
    const convertToFire = sample(available.filter(cell => !keys.has(`${cell.row}:${cell.col}`)), definition.fireCells, rng).map(({ row, col }) => ({ row, col }));
    const effects = {
      damage: Math.floor(attack * definition.damageNumerator / definition.damageDenominator),
      heal: id === 7 && !definition.upgraded ? Math.floor(maxHp / 10) * 2 : Math.floor(maxHp * definition.healPercent / 100),
      barrier: Math.floor(maxHp * definition.barrierPercent / 100),
      manaDrain: definition.manaDrain, manaGain: definition.manaGain,
      shieldTurns: definition.shieldTurns, freezeTurns: definition.freezeTurns,
      freezeInitialPadding: definition.freezeInitialPadding, bonusTurns: definition.bonusTurns,
      lifeStealPercent: definition.lifeStealPercent
    };
    return { definition, regions, cells: selected, convertToFire, effects };
  }
  function describeSkill(id, tomeTier = 0, stats = {}) {
    const d = skillDefinition(id, tomeTier);
    const text = [`${d.name}${d.upgraded ? ' · Cường hóa' : ''}`, `Tốn ${d.manaCost} mana`];
    if (d.damageNumerator) text.push(Number.isFinite(stats.attack) ? `Gây ${Math.floor(stats.attack * d.damageNumerator / d.damageDenominator)} sát thương` : `Sát thương ×${d.damageNumerator / d.damageDenominator} ATK`);
    const p = d.pattern;
    if (p.kind.includes('regions')) text.push(`Phá ${p.min === p.max ? p.min : `${p.min}–${p.max}`} vùng ${p.width}×${p.height}`);
    if (p.kind === 'same-kind' || p.kind === 'random') text.push(`Phá ${p.min}–${p.max} ô${p.kind === 'same-kind' ? ' cùng loại' : ''}`);
    if (p.kind === 'hearts') text.push('Ăn toàn bộ ô Tim');
    if (d.fireCells) text.push(`Tạo ${d.fireCells} Kiếm Lửa`);
    if (d.manaDrain) text.push(`Trừ ${d.manaDrain} mana địch`);
    if (d.manaGain) text.push(`Nhận ${d.manaGain} mana`);
    if (d.healPercent) {
      const amount = id === 7 && !d.upgraded ? Math.floor(stats.maxHp / 10) * 2 : Math.floor(stats.maxHp * d.healPercent / 100);
      text.push(Number.isFinite(stats.maxHp) ? `Hồi ${amount} HP (${d.healPercent}% tối đa)` : `Hồi ${d.healPercent}% HP tối đa`);
    }
    if (d.barrierPercent) text.push(`Lá chắn ${d.barrierPercent}% HP tối đa`);
    if (d.lifeStealPercent) text.push(`Hút ${d.lifeStealPercent}% HP thực mất`);
    if (d.shieldTurns) text.push(`Khiên Sét ${d.shieldTurns} nhịp`);
    if (d.freezeTurns) text.push(`${id === 2 ? 'Choáng' : 'Đóng băng'} ${d.freezeTurns} lượt`);
    return text.join('. ');
  }
  const api = { skillDefinition, planSkill, describeSkill };
  if (typeof module === 'object' && module.exports) module.exports = api;
  root.LoanEquipment = Object.assign(root.LoanEquipment || {}, api);
})(typeof globalThis === 'object' ? globalThis : this);
