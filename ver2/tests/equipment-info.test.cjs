const {test}=require('node:test');
const assert=require('node:assert/strict');
const fs=require('node:fs');
const vm=require('node:vm');
const rules=require('../runtime/equipment/rules.js');
const catalog=require('../runtime/equipment/catalog.js');
const skills=require('../runtime/equipment/skills.js');
const context={window:{LoanEquipment:{...catalog,...rules,...skills}}};
vm.runInNewContext(fs.readFileSync('runtime/equipment/info.js','utf8'),context);
const info=context.window.LoanEquipmentInfo;
test('Skill descriptions keep 12/24/36 eligibility, apply sword damage and seal refund once',()=>{
  const state={profile:{ownedTiers:[3,0,0,3,3,0],equippedIds:[0,3,4]},attack:100};
  const one=info.skill(0,state),two=info.skill(1,state),three=info.skill(2,state);
  assert.match(one.description,/Gây 202 sát thương trước phòng thủ/);
  assert.match(two.description,/Gây 300 sát thương trước phòng thủ/);
  assert.match(three.description,/Gây 495 sát thương trước phòng thủ/);
  [one,two,three].forEach((s,i)=>assert.match(s.description,new RegExp(`Cần ${(i+1)*12} Mana`)));
  assert.match(one.description,/Hoàn 10 Mana/);assert.doesNotMatch(one.description,/Thêm 1 lượt đi/);assert.match(three.description,/Thêm 1 lượt đi/);
});
test('Skill description uses frozen battle tiers and actual heal rules, not upgraded owned tier',()=>{
  const state={profile:{ownedTiers:[3,3,3,3,3,3],equippedIds:[0,3,4]},battle:{activeTiers:[0,1,0,0,2,0],attack:100,maxHP:105}};
  assert.match(info.skill(0,state).description,/Gây 135 sát thương/);
  assert.match(info.skill(7,state).description,/Hồi 38 HP sau cộng trang bị/);
  assert.doesNotMatch(info.skill(0,state).description,/Hoàn 10 Mana/);
  assert.equal(info.skill(2,state).definition.upgraded,false);
});
test('Outside battle, unknown HP/attack descriptions still disclose equipped modifiers',()=>{
  const state={profile:{ownedTiers:[3,3,0,0,3,0],equippedIds:[0,1,4]}};
  assert.match(info.skill(0,state).description,/Sát thương cơ bản ×1.35 ATK/);
  assert.match(info.skill(0,state).description,/tăng thêm 50% sát thương/);
  assert.match(info.skill(7,state).description,/tăng thêm 80% lượng hồi/);
});
