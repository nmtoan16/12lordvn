/* Pure ownership/loadout rules; no JVM, DOM or storage side effects. */
'use strict';
(function (root, factory) {
  const api=factory();
  if(typeof module==='object'&&module.exports)module.exports=api;
  (root.LoanEquipment||(root.LoanEquipment={})).loadout=api;
})(typeof globalThis!=='undefined'?globalThis:this,function(){
  function fail(code,message){const error=new Error(message);error.code=code;throw error;}
  function validateOwnedTiers(tiers){
    if(!Array.isArray(tiers)||tiers.length!==6||tiers.some(tier=>!Number.isInteger(tier)||tier<0||tier>3))fail('OWNED_TIERS','Exactly six integer tiers from 0 to 3 are required.');
    return tiers.slice();
  }
  function validateLoadout(ids,ownedTiers){
    const tiers=validateOwnedTiers(ownedTiers);
    if(!Array.isArray(ids)||ids.length>3)fail('LOADOUT_FULL','At most three items can be equipped.');
    const seen=new Set();
    for(const id of ids){
      if(!Number.isInteger(id)||id<0||id>5)fail('ITEM_ID','Invalid equipment ID.');
      if(seen.has(id))fail('LOADOUT_DUPLICATE','An item cannot occupy two slots.');
      if(tiers[id]===0)fail('ITEM_NOT_OWNED','An unowned item cannot be equipped.');
      seen.add(id);
    }
    return ids.slice();
  }
  function activeTiers(tiers,ids){
    const valid=validateLoadout(ids,tiers);
    return tiers.map((tier,id)=>valid.includes(id)?tier:0);
  }
  function snapshot(tiers,ids){
    const levels=validateOwnedTiers(tiers),equipped=validateLoadout(ids,levels);
    return Object.freeze({equippedIds:Object.freeze(equipped),levelsAtStart:Object.freeze(levels),activeTiers:Object.freeze(activeTiers(levels,equipped))});
  }
  function equip(tiers,ids,id,replaceIndex){
    const result=validateLoadout(ids,tiers);
    validateLoadout([id],tiers);
    if(result.includes(id))return result;
    if(result.length<3)result.push(id);
    else{
      if(replaceIndex===undefined)fail('LOADOUT_FULL','Choose an explicit slot to replace, or cancel.');
      if(!Number.isInteger(replaceIndex)||replaceIndex<0||replaceIndex>=result.length)fail('LOADOUT_SLOT','Invalid replacement slot.');
      result[replaceIndex]=id;
    }
    return validateLoadout(result,tiers);
  }
  function unequip(tiers,ids,id){return validateLoadout(ids,tiers).filter(item=>item!==id);}
  return Object.freeze({validateOwnedTiers,validateLoadout,activeTiers,snapshot,equip,unequip});
});
