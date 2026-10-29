'use strict';
(function (root) {
  const names = ['Thần Binh', 'Linh Châu', 'Bảo Giáp', 'Pháp Ấn', 'Bí Kíp Võ Học', 'Thần Thạch'];
  const rows = [
    [
      ['Thanh Phong Kiếm', 800, '+15% sát thương kiếm/phép; nhóm kiếm từ 4 ô chí mạng +15%.'],
      ['Hỏa Lôi Đao', 2200, '+30% sát thương; chí mạng kế thừa; kiếm refill có 20% thành Kiếm Lửa.'],
      ['Thuận Thiên Bảo Kiếm', 5000, '+50% sát thương; Kiếm Lửa 25%; phép/nhóm Kiếm Lửa đốt 5% HP hiện tại.']
    ],
    [
      ['Mộc Linh Châu', 700, '+25% hồi Tim/Thuốc/Phép; hồi 3% HP tối đa cuối hành động.'],
      ['Huyết Ngọc Bội', 2000, '+50% hồi phục; hồi 4% cuối hành động; hút 15% HP thực mất từ phép.'],
      ['Cửu Chuyển Liên Hoa', 4500, '+80% hồi phục; hồi 4% cuối hành động; hút 25%; cứu nguy 30% HP một lần khi còn dưới 25%.']
    ],
    [
      ['Huyền Thiết Giáp', 900, 'Lá chắn đầu trận 20% HP tối đa; giảm 10% sát thương.'],
      ['Kim Cang Hộ Tâm Kính', 2500, 'Lá chắn đầu trận 35%; giảm 20% sát thương; tung phép thêm lá chắn 10%.'],
      ['Long Lân Chiến Giáp', 5500, 'Lá chắn đầu trận 50%; giảm 30%; phản 25% khi có khiên; chặn hút mana khi có khiên.']
    ],
    [
      ['Thái Cực Phù', 800, '+8 mana đầu trận; mỗi nhóm Âm Dương thêm 2 mana.'],
      ['Lưỡng Nghi Ngọc Ấn', 2200, '+14 mana đầu trận; mỗi nhóm thêm 3; dùng phép hoàn 6 mana.'],
      ['Tiên Thiên Bát Quái Ấn', 4800, '+20 mana đầu trận; mỗi nhóm thêm 3; hoàn 10 mana; chiêu 3 thêm một lượt.']
    ],
    [
      ['Bí Kíp Quyển Thượng', 1200, 'Cường hóa chiêu 1 của hệ nhân vật.'],
      ['Bí Kíp Quyển Trung', 3000, 'Cường hóa chiêu 1 và chiêu 2 của hệ nhân vật.'],
      ['Bí Kíp Quyển Hạ', 6000, 'Thức tỉnh cả ba chiêu của hệ nhân vật.']
    ],
    [
      ['Linh Thạch Giác Ngộ', 600, '+30% lương tối đa; giảm 25% hao lương và 50% phạt xếp sai.'],
      ['Tinh Thạch Phong Vân', 1800, 'Kế thừa bậc I; vàng/EXP trên bàn ×1,5; nhóm đúng 4 có 25% rơi đồ.'],
      ['Thiên Nhãn Thần Thạch', 4000, 'Kế thừa; vàng/EXP trên bàn ×2; hiệu ứng Hoàng Kim.']
    ]
  ];
  const catalog = Object.freeze(rows.map((row, id) => Object.freeze({
    id, name: names[id], tiers: Object.freeze([
      Object.freeze({ tier: 0, name: 'Chưa sở hữu', price: 0, description: '' }),
      ...row.map(([name, price, description], index) => Object.freeze({ tier: index + 1, name, price, description }))
    ])
  })));
  function getEquipment(id) {
    if (!Number.isInteger(id) || id < 0 || id >= catalog.length) throw new RangeError('Equipment id must be 0..5');
    return catalog[id];
  }
  function tierInfo(id, tier) {
    if (!Number.isInteger(tier) || tier < 0 || tier > 3) throw new RangeError('Equipment tier must be 0..3');
    return getEquipment(id).tiers[tier];
  }
  function activeTiers(profile) {
    const owned = profile.ownedTiers || profile.tiers || profile.owned;
    if (!Array.isArray(owned) || owned.length !== 6 || owned.some(tier => !Number.isInteger(tier) || tier < 0 || tier > 3)) {
      throw new TypeError('Six owned tiers 0..3 required');
    }
    const loadout = profile.equippedIds || profile.loadout || [];
    if (!Array.isArray(loadout) || loadout.length > 3) throw new RangeError('At most three equipment slots');
    const active = [0, 0, 0, 0, 0, 0];
    const seen = new Set();
    for (const id of loadout) {
      if (id === null || id === -1) continue;
      getEquipment(id);
      if (seen.has(id)) throw new RangeError('Duplicate equipment');
      if (!owned[id]) throw new RangeError('Cannot equip unowned equipment');
      seen.add(id);
      active[id] = owned[id];
    }
    return active;
  }
  const api = { catalog, getEquipment, tierInfo, activeTiers };
  if (typeof module === 'object' && module.exports) module.exports = api;
  root.LoanEquipment = Object.assign(root.LoanEquipment || {}, api);
})(typeof globalThis === 'object' ? globalThis : this);
