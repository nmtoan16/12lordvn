'use strict';
(function (root) {
  const { rules } = typeof module === 'object' && module.exports ? require('./rules.js') : root.LoanEquipment;
  const { clamp, percent } = rules;
  function fighterState(value = {}) {
    const maxHp = Math.max(1, Math.trunc(value.maxHp || 1));
    const maxMana = Math.max(0, Math.trunc(value.maxMana === undefined ? 36 : value.maxMana));
    const maxFood = Math.max(0, Math.trunc(value.maxFood || 0));
    return {
      hp: clamp(value.hp === undefined ? maxHp : value.hp, 0, maxHp), maxHp,
      mana: clamp(value.mana || 0, 0, maxMana), maxMana,
      food: clamp(value.food === undefined ? maxFood : value.food, 0, maxFood), maxFood,
      immune: Boolean(value.immune), itemArmor: Boolean(value.itemArmor),
      gold: Math.trunc(value.gold || 0), xp: Math.trunc(value.xp || 0)
    };
  }
  function sideIndex(side) {
    if (side !== 0 && side !== 1) throw new RangeError('Combat side must be 0 or 1');
    return side;
  }
  function eventId(event) {
    if (!event || !['string', 'number'].includes(typeof event.id) || event.id === '' || (typeof event.id === 'number' && !Number.isFinite(event.id))) throw new TypeError('Stable event id required');
    return String(event.id);
  }
  /** Pure state engine. JVM bindings copy field values in/out; methods never call Java or render. */
  class CombatSession {
    constructor(options = {}) {
      this.tiers = Object.freeze(rules.validateTiers(options.tiers || [0, 0, 0, 0, 0, 0]));
      if (!Array.isArray(options.fighters) || options.fighters.length !== 2) throw new TypeError('Two combat fighters required');
      this.fighters = options.fighters.map(fighterState);
      this.barrier = 0;
      this.started = false;
      this.emergencyUsed = false;
      this.foodCarry = 0; // quarters, retaining exact .75 consumption without floating point drift
      this.events = [];
      this._results = new Map();
      this._procs = new Set();
      this._casts = new Map();
    }
    _once(kind, event, callback) {
      const key = `${kind}:${eventId(event)}`;
      if (this._results.has(key)) return this._results.get(key);
      const result = callback();
      this._results.set(key, result);
      if (this._results.size > 512) this._results.delete(this._results.keys().next().value);
      return result;
    }
    _emit(type, event, side, amount, extra = {}) {
      if (amount > 0) this.events.push({ type, id: String(event.id), side, amount, source: event.source || type, actionId: event.actionId, groupId: event.groupId, castId: event.castId, ...extra });
      if (this.events.length > 512) this.events.splice(0, this.events.length - 512);
    }
    drainEvents() { return this.events.splice(0); }
    start() {
      if (this.started) return false;
      this.started = true;
      const player = this.fighters[0];
      const armor = this.tiers[2];
      this.addBarrier({ id: 'initial-barrier', side: 0, source: 'start', amount: percent(player.maxHp, rules.tables.initialBarrier[armor]) });
      this.manaGain({ id: 'initial-mana', side: 0, source: 'start', amount: rules.tables.initialMana[this.tiers[3]] });
      if (this.tiers[5]) {
        const oldMax = player.maxFood;
        player.maxFood = percent(oldMax, 130);
        player.food = clamp(player.food + player.maxFood - oldMax, 0, player.maxFood);
      }
      return true;
    }
    addBarrier(event) {
      return this._once('barrier', event, () => {
        const side = sideIndex(event.side === undefined ? 0 : event.side);
        if (side !== 0) return { amount: 0, barrier: 0 };
        const before = this.barrier;
        this.barrier = clamp(before + Math.max(0, Math.trunc(event.amount)), 0, this.fighters[0].maxHp);
        const amount = this.barrier - before;
        this._emit('barrier', event, 0, amount, { barrier: this.barrier });
        return { amount, barrier: this.barrier };
      });
    }
    heal(event) {
      return this._once('heal', event, () => {
        const side = sideIndex(event.side);
        const fighter = this.fighters[side];
        const before = fighter.hp;
        const modified = rules.healing(event.amount, side === 0 ? this.tiers[1] : 0, event.source);
        fighter.hp = clamp(before + modified, 0, fighter.maxHp);
        const amount = fighter.hp - before;
        this._emit('heal', event, side, amount, { hpBefore: before, hpAfter: fighter.hp });
        return { amount, modified, hpBefore: before, hpAfter: fighter.hp };
      });
    }
    _emergency(event) {
      const player = this.fighters[0];
      if (this.tiers[1] !== 3 || this.emergencyUsed || player.hp <= 0 || player.hp * 4 >= player.maxHp) return 0;
      this.emergencyUsed = true;
      return this.heal({ ...event, id: `${event.id}:emergency`, side: 0, source: 'emergency', amount: percent(player.maxHp, 30) }).amount;
    }
    damage(event) {
      return this._once('damage', event, () => {
        const side = sideIndex(event.side);
        const target = sideIndex(event.target === undefined ? 1 - side : event.target);
        const defender = this.fighters[target];
        const raw = Math.max(0, Math.trunc(event.amount));
        let modified = side === 0 && !event.trueDamage ? rules.offense(raw, this.tiers[0], event) : raw;
        if (event.source === 'invalid-swap' && target === 0 && this.tiers[5]) modified = Math.floor(modified / 2);
        let reduced = modified;
        if (!event.trueDamage) {
          if (defender.itemArmor) reduced = Math.floor(reduced / 4);
          if (target === 0) reduced = percent(reduced, 100 - rules.tables.reduction[this.tiers[2]]);
        }
        const hpBefore = defender.hp;
        const hadShield = target === 0 && (this.barrier > 0 || defender.immune);
        let absorbed = 0;
        if (!defender.immune) {
          if (target === 0) { absorbed = Math.min(this.barrier, reduced); this.barrier -= absorbed; }
          defender.hp = clamp(hpBefore - (reduced - absorbed), 0, defender.maxHp);
        }
        const hpLoss = hpBefore - defender.hp;
        this._emit('block', event, target, absorbed, { barrier: this.barrier });
        this._emit('damage', event, target, hpLoss, { attacker: side, raw, modified, reduced, hpBefore, hpAfter: defender.hp });
        const result = { raw, modified, reduced, absorbed, hpLoss, hpBefore, hpAfter: defender.hp, reflected: 0, burn: 0, lifesteal: 0, emergency: 0 };
        const secondary = ['reflect', 'burn', 'invalid-swap'].includes(event.source);
        if (target === 0 && side === 1 && this.tiers[2] === 3 && hadShield && !secondary && reduced > 0) {
          const reflected = this.damage({ ...event, id: `${event.id}:reflect`, side: 0, target: 1, source: 'reflect', amount: percent(reduced, 25) });
          result.reflected = reflected.hpLoss;
        }
        if (side === 0 && target === 1 && this.tiers[0] === 3 && !secondary) {
          const fire = event.source === 'fire-sword' || event.fireGroup;
          const proc = fire ? `burn-group:${event.groupId === undefined ? event.id : event.groupId}` : null;
          if (proc && !this._procs.has(proc)) {
            this._procs.add(proc);
            if (this._procs.size > 128) this._procs.delete(this._procs.values().next().value);
            const burned = this.damage({ ...event, id: `${event.id}:burn`, source: 'burn', amount: percent(defender.hp, 5), trueDamage: true });
            result.burn = burned.hpLoss;
          }
        }
        if (side === 0 && target === 1 && ['skill', 'skill-board'].includes(event.source) && this.fighters[0].hp > 0) {
          const cast = this._casts.get(String(event.castId));
          const extra = event.lifeStealPercent === undefined ? (cast ? cast.lifeStealPercent : 0) : event.lifeStealPercent;
          const rate = rules.tables.lifesteal[this.tiers[1]] + Math.max(0, Math.trunc(extra || 0));
          result.lifesteal = this.heal({ ...event, id: `${event.id}:lifesteal`, side: 0, source: 'lifesteal', amount: percent(hpLoss, rate) }).amount;
        }
        if (target === 0) result.emergency = this._emergency(event);
        result.hpAfter = defender.hp;
        return result;
      });
    }
    beginCast(event) {
      const id = eventId(event);
      const side = sideIndex(event.side);
      if (!this._casts.has(id)) this._casts.set(id, { side, slot: event.slot, skillId: event.skillId, lifeStealPercent: Math.max(0, Math.trunc(event.lifeStealPercent || 0)) });
      if (this._casts.size > 32) this._casts.delete(this._casts.keys().next().value);
      return this._casts.get(id);
    }
    triggerCastBurn(event) {
      const side = sideIndex(event.side);
      if (side !== 0 || this.tiers[0] !== 3) return 0;
      const key = `burn-cast:${event.castId === undefined ? event.id : event.castId}`;
      if (this._procs.has(key)) return 0;
      this._procs.add(key);
      if (this._procs.size > 128) this._procs.delete(this._procs.values().next().value);
      return this.damage({ ...event, id: `${event.id}:cast-burn`, side: 0, target: 1, source: 'burn', trueDamage: true, amount: percent(this.fighters[1].hp, 5) }).hpLoss;
    }
    /** payCost:false means Java has already paid the original cost; only successful casts call this mode. */
    cast(event) {
      return this._once('cast', event, () => {
        const side = sideIndex(event.side);
        if (!Number.isInteger(event.slot) || event.slot < 0 || event.slot > 2) throw new RangeError('Skill slot must be 0..2');
        const caster = this.fighters[side];
        const cost = event.manaCost === undefined ? caster.maxMana * (event.slot + 1) / 3 : event.manaCost;
        if (event.successful === false || (event.payCost !== false && caster.mana < cost)) return { successful: false, cost: 0, refund: 0, barrier: 0, bonusTurns: 0, burn: 0 };
        this.beginCast(event);
        const paid = event.payCost === false ? 0 : this.manaDrain({ ...event, id: `${event.id}:cost`, side, source: 'cast-cost', amount: cost, forced: true }).amount;
        const tier = side === 0 ? this.tiers[3] : 0;
        const refund = this.manaGain({ ...event, id: `${event.id}:refund`, side, source: 'refund', amount: rules.tables.refund[tier] }).amount;
        const barrier = side === 0 && this.tiers[2] >= 2 ? this.addBarrier({ ...event, id: `${event.id}:cast-barrier`, side, source: 'cast', amount: percent(caster.maxHp, 10) }).amount : 0;
        const bonusTurns = side === 0 && tier === 3 && event.slot === 2 ? 1 : 0;
        this._emit('bonus-turn', event, side, bonusTurns);
        const burn = this.triggerCastBurn({ ...event, castId: event.id });
        return { successful: true, cost: paid, refund, barrier, bonusTurns, burn };
      });
    }
    manaGain(event) {
      return this._once('mana-gain', event, () => {
        const side = sideIndex(event.side);
        const fighter = this.fighters[side];
        let requested = Math.max(0, Math.trunc(event.amount));
        if (side === 0 && event.source === 'yin') {
          const proc = `yin:${event.groupId === undefined ? event.id : event.groupId}`;
          if (!this._procs.has(proc)) { this._procs.add(proc); requested += rules.tables.groupMana[this.tiers[3]]; }
          if (this._procs.size > 128) this._procs.delete(this._procs.values().next().value);
        }
        const before = fighter.mana;
        fighter.mana = clamp(before + requested, 0, fighter.maxMana);
        const amount = fighter.mana - before;
        this._emit('mana', event, side, amount);
        return { amount, mana: fighter.mana };
      });
    }
    manaDrain(event) {
      return this._once('mana-drain', event, () => {
        const side = sideIndex(event.side);
        const fighter = this.fighters[side];
        const protectedMana = !event.forced && (fighter.immune || (side === 0 && this.tiers[2] === 3 && this.barrier > 0));
        const before = fighter.mana;
        if (!protectedMana) fighter.mana = clamp(before - Math.max(0, Math.trunc(event.amount)), 0, fighter.maxMana);
        return { amount: before - fighter.mana, mana: fighter.mana, blocked: protectedMana };
      });
    }
    endAction(event) {
      return this._once('action', event, () => {
        const side = sideIndex(event.side);
        let regen = 0;
        if (side === 0 && this.fighters[0].hp > 0) regen = this.heal({ ...event, id: `${event.id}:regen`, side, source: 'regen', amount: percent(this.fighters[0].maxHp, rules.tables.regen[this.tiers[1]]) }).amount;
        this._casts.clear();
        this._procs.clear();
        return { regen };
      });
    }
    consumeFood(event) {
      if (event.paused) return { amount: 0, food: this.fighters[sideIndex(event.side)].food };
      return this._once('food', event, () => {
        const side = sideIndex(event.side);
        const fighter = this.fighters[side];
        let cost = Math.max(0, Math.trunc(event.amount));
        if (side === 0 && this.tiers[5]) {
          const quarters = cost * 3 + this.foodCarry;
          cost = Math.floor(quarters / 4);
          this.foodCarry = quarters % 4;
        }
        const before = fighter.food;
        fighter.food = clamp(before - cost, 0, fighter.maxFood);
        return { amount: before - fighter.food, food: fighter.food };
      });
    }
    reward(event) {
      return this._once('reward', event, () => {
        const side = sideIndex(event.side);
        if (!['gold', 'xp'].includes(event.kind)) throw new RangeError('Reward kind must be gold or xp');
        const amount = rules.boardReward(Math.max(0, Math.trunc(event.amount)), side === 0 ? this.tiers[5] : 0, event.source);
        this.fighters[side][event.kind] += amount;
        return { amount, total: this.fighters[side][event.kind] };
      });
    }
    snapshot() {
      return {
        version: 1, tiers: this.tiers.slice(), fighters: this.fighters.map(fighter => ({ ...fighter })),
        barrier: this.barrier, started: this.started, emergencyUsed: this.emergencyUsed, foodCarry: this.foodCarry,
        // The binding persists a monotonic event sequence. Old food ticks cannot recur after resume;
        // retain only recent non-food results and the current action's proc/cast state in RMS.
        results: Array.from(this._results.entries()).filter(([key]) => !key.startsWith('food:')).slice(-32),
        procs: Array.from(this._procs).slice(-64), casts: Array.from(this._casts.entries())
      };
    }
    static restore(snapshot) {
      if (!snapshot || snapshot.version !== 1) throw new RangeError('Unsupported combat snapshot version');
      const battle = new CombatSession(snapshot);
      if (!Number.isInteger(snapshot.barrier) || snapshot.barrier < 0 || snapshot.barrier > battle.fighters[0].maxHp || !Number.isInteger(snapshot.foodCarry) || snapshot.foodCarry < 0 || snapshot.foodCarry > 3) throw new RangeError('Invalid combat snapshot state');
      battle.barrier = snapshot.barrier;
      battle.started = Boolean(snapshot.started);
      battle.emergencyUsed = Boolean(snapshot.emergencyUsed);
      battle.foodCarry = snapshot.foodCarry;
      battle._results = new Map((snapshot.results || []).slice(-32));
      battle._procs = new Set((snapshot.procs || []).slice(-64));
      battle._casts = new Map((snapshot.casts || []).slice(-32));
      return battle;
    }
  }
  const api = { CombatSession };
  if (typeof module === 'object' && module.exports) module.exports = api;
  root.LoanEquipment = Object.assign(root.LoanEquipment || {}, api);
})(typeof globalThis === 'object' ? globalThis : this);
