'use strict';
(function (root) {
  const tables = Object.freeze({
    attack: Object.freeze([0, 15, 30, 50]),
    heal: Object.freeze([0, 25, 50, 80]),
    regen: Object.freeze([0, 3, 4, 4]),
    lifesteal: Object.freeze([0, 0, 15, 25]),
    initialBarrier: Object.freeze([0, 20, 35, 50]),
    reduction: Object.freeze([0, 10, 20, 30]),
    initialMana: Object.freeze([0, 8, 14, 20]),
    groupMana: Object.freeze([0, 2, 3, 3]),
    refund: Object.freeze([0, 0, 6, 10]),
    fireChance: Object.freeze([11, 11, 20, 25])
  });
  const clamp = (value, min, max) => Math.min(max, Math.max(min, Math.trunc(value)));
  const percent = (amount, rate) => Math.floor(amount * rate / 100);
  function validateTiers(tiers) {
    if (!Array.isArray(tiers) || tiers.length !== 6 || tiers.some(t => !Number.isInteger(t) || t < 0 || t > 3)) {
      throw new TypeError('Six active equipment tiers 0..3 required');
    }
    return tiers.slice();
  }
  function offense(amount, tier, event = {}) {
    let result = Math.max(0, Math.trunc(amount));
    if (!['sword', 'fire-sword', 'skill', 'skill-board'].includes(event.source)) return result;
    result = percent(result, 100 + tables.attack[tier]);
    if (tier > 0 && ['sword', 'fire-sword', 'skill-board'].includes(event.source) && event.groupSize >= 4) result = percent(result, 115);
    return result;
  }
  function healing(amount, tier, source) {
    return percent(Math.max(0, Math.trunc(amount)), 100 + (['heart', 'potion', 'skill'].includes(source) ? tables.heal[tier] : 0));
  }
  function refillTile(tile, tier, side, rng = Math.random) {
    if (tile !== 0) return tile;
    return rng() * 100 < tables.fireChance[side === 0 ? tier : 0] ? 6 : 0;
  }
  // null delegates the >=5 rule to the original drop routine, exactly once.
  function dropForGroup(size, tier, side, rng = Math.random) {
    if (size >= 5) return null;
    return side === 0 && tier >= 2 && size === 4 && rng() < 0.25;
  }
  function boardReward(amount, tier, source = 'board') {
    return source === 'board' ? Math.floor(amount * (tier === 3 ? 2 : tier === 2 ? 1.5 : 1)) : Math.trunc(amount);
  }
  const rules = Object.freeze({ tables, clamp, percent, validateTiers, offense, healing, refillTile, dropForGroup, boardReward });
  const api = { rules };
  if (typeof module === 'object' && module.exports) module.exports = api;
  root.LoanEquipment = Object.assign(root.LoanEquipment || {}, api);
})(typeof globalThis === 'object' ? globalThis : this);
