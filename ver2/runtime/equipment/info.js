'use strict';
(() => {
  function activeTiers(state){
    if(state.battle?.activeTiers)return state.battle.activeTiers;
    const profile=state.profile||{},active=[0,0,0,0,0,0];
    for(const id of profile.equippedIds||[])active[id]=profile.ownedTiers?.[id]||0;
    return active;
  }
  function equipment(id,tier){
    const data=window.LoanEquipment?.tierInfo?.(id,tier)||window.LoanEquipment?.catalog?.[id]?.tiers?.find(t=>t.tier===tier);
    const text=data?.description;
    return Array.isArray(text)?text.join(' · '):text||'Chưa sở hữu.';
  }
  function skill(id,state){
    const api=window.LoanEquipment,tier=activeTiers(state)[4],definition=api?.skillDefinition?.(id,tier);
    if(!definition)return {name:`Chiêu ${id+1}`,description:'Đang nạp mô tả chiêu thức.'};
    const fighter=state.battle?.fighters?.[0]||state.character||{},attack=state.battle?.attack??fighter.attack??state.attack;
    const stats={attack,maxHp:state.battle?.maxHP??fighter.maxHP??fighter.maxHp??state.maxHP};
    let description=api.describeSkill?.(id,tier,stats)||'';
    const tiers=activeTiers(state),rules=api.rules;
    if(Number.isFinite(stats.attack)&&definition.damageNumerator&&rules){
      const raw=Math.floor(stats.attack*definition.damageNumerator/definition.damageDenominator),actual=rules.offense(raw,tiers[0],{source:'skill'});
      description=description.replace(/Gây \d+ sát thương/,`Gây ${actual} sát thương trước phòng thủ`);
    }
    else if(definition.damageNumerator&&tiers[0]&&rules){
      description=description.replace('Sát thương ×','Sát thương cơ bản ×');
      description+=` Thần Binh tăng thêm ${rules.tables.attack[tiers[0]]}% sát thương sau khi tính chiêu.`;
    }
    if(Number.isFinite(stats.maxHp)&&definition.healPercent&&rules){
      const raw=id===7&&!definition.upgraded?Math.floor(stats.maxHp/10)*2:Math.floor(stats.maxHp*definition.healPercent/100),actual=rules.healing(raw,tiers[1],'skill');
      description=description.replace(/Hồi \d+ HP \([^)]*\)/,`Hồi ${actual} HP sau cộng trang bị`);
    }
    else if(definition.healPercent&&tiers[1]&&rules)description+=` Linh Châu tăng thêm ${rules.tables.heal[tiers[1]]}% lượng hồi.`;
    const seal=activeTiers(state)[3],refund=[0,0,6,10][seal]||0;
    description+=` Cần ${definition.manaCost} Mana trước khi dùng.`;
    if(refund)description+=` Hoàn ${refund} Mana sau khi tung chiêu.`;
    if(seal===3&&definition.slot===2)description+=' Thêm 1 lượt đi.';
    if(tiers[2]>=2)description+=' Sau cast thêm lá chắn bằng 10% HP tối đa.';
    const life=(definition.lifeStealPercent||0)+(rules?.tables.lifesteal[tiers[1]]||0);
    if(life)description+=` Tổng hút máu kỹ năng: ${life}% HP địch thực mất.`;
    if(tiers[0]===3)description+=' Đốt thêm 5% HP hiện tại của địch một lần mỗi chiêu.';
    return {name:definition.name+(definition.upgraded?' · Thức tỉnh':''),description,definition};
  }
  window.LoanEquipmentInfo=Object.freeze({activeTiers,equipment,skill});
})();
