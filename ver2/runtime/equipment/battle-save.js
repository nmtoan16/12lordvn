/* Battle extension lives beside the original snapshot; it never edits key 106. */
'use strict';
(function(root,factory){
  const common=typeof module==='object'&&module.exports;
  const api=factory(common?require('./store.js'):root.LoanEquipment?.store,common?require('./loadout.js'):root.LoanEquipment?.loadout);
  if(common)module.exports=api;
  (root.LoanEquipment||(root.LoanEquipment={})).battleSave=api;
})(typeof globalThis!=='undefined'?globalThis:this,function(Store,Loadout){
  if(!Store||!Loadout)throw new Error('Load equipment/loadout.js and store.js before battle-save.js.');
  function fail(code,message){const error=new Error(message);error.code=code;throw error;}
  function id(value){if(typeof value!=='string'||value.length<1||value.length>128||/[\u0000-\u001f]/.test(value))fail('BATTLE_ID','Invalid battle/profile identity.');return value;}
  function bytesOf(input){
    if(!ArrayBuffer.isView(input)||input.BYTES_PER_ELEMENT!==1||input.byteLength>1048576)fail('SNAPSHOT_BYTES','Expected a bounded original snapshot byte array.');
    return new Uint8Array(input.buffer,input.byteOffset,input.byteLength);
  }
  // SHA-256 is self-contained so fingerprints also work outside secure-context
  // WebCrypto (e.g. an HTTP LAN installation of XAMPP).
  function fingerprintSnapshot(input){
    const bytes=bytesOf(input),data=new Uint8Array(Math.ceil((bytes.length+9)/64)*64);data.set(bytes);data[bytes.length]=0x80;
    const view=new DataView(data.buffer);view.setUint32(data.length-4,bytes.length*8,false);
    const hash=[0x6a09e667,0xbb67ae85,0x3c6ef372,0xa54ff53a,0x510e527f,0x9b05688c,0x1f83d9ab,0x5be0cd19];
    const constants=[0x428a2f98,0x71374491,0xb5c0fbcf,0xe9b5dba5,0x3956c25b,0x59f111f1,0x923f82a4,0xab1c5ed5,0xd807aa98,0x12835b01,0x243185be,0x550c7dc3,0x72be5d74,0x80deb1fe,0x9bdc06a7,0xc19bf174,0xe49b69c1,0xefbe4786,0x0fc19dc6,0x240ca1cc,0x2de92c6f,0x4a7484aa,0x5cb0a9dc,0x76f988da,0x983e5152,0xa831c66d,0xb00327c8,0xbf597fc7,0xc6e00bf3,0xd5a79147,0x06ca6351,0x14292967,0x27b70a85,0x2e1b2138,0x4d2c6dfc,0x53380d13,0x650a7354,0x766a0abb,0x81c2c92e,0x92722c85,0xa2bfe8a1,0xa81a664b,0xc24b8b70,0xc76c51a3,0xd192e819,0xd6990624,0xf40e3585,0x106aa070,0x19a4c116,0x1e376c08,0x2748774c,0x34b0bcb5,0x391c0cb3,0x4ed8aa4a,0x5b9cca4f,0x682e6ff3,0x748f82ee,0x78a5636f,0x84c87814,0x8cc70208,0x90befffa,0xa4506ceb,0xbef9a3f7,0xc67178f2];
    const rotate=(value,bits)=>(value>>>bits)|(value<<(32-bits)),words=new Uint32Array(64);
    for(let offset=0;offset<data.length;offset+=64){
      for(let index=0;index<16;index++)words[index]=view.getUint32(offset+index*4,false);
      for(let index=16;index<64;index++){
        const x=words[index-15],y=words[index-2];
        words[index]=(words[index-16]+(rotate(x,7)^rotate(x,18)^(x>>>3))+words[index-7]+(rotate(y,17)^rotate(y,19)^(y>>>10)))>>>0;
      }
      let [a,b,c,d,e,f,g,h]=hash;
      for(let index=0;index<64;index++){
        const first=(h+(rotate(e,6)^rotate(e,11)^rotate(e,25))+((e&f)^(~e&g))+constants[index]+words[index])>>>0;
        const second=((rotate(a,2)^rotate(a,13)^rotate(a,22))+((a&b)^(a&c)^(b&c)))>>>0;
        h=g;g=f;f=e;e=(d+first)>>>0;d=c;c=b;b=a;a=(first+second)>>>0;
      }
      [a,b,c,d,e,f,g,h].forEach((value,index)=>{hash[index]=(hash[index]+value)>>>0;});
    }
    return hash.map(value=>value.toString(16).padStart(8,'0')).join('');
  }
  function copyState(state){
    let nodes=0;const seen=new Set();
    function copy(value,depth){
      if(depth>8||++nodes>4096)fail('BATTLE_STATE','Battle state exceeds structural limits.');
      if(value===null||typeof value==='boolean')return value;
      if(typeof value==='number'){if(!Number.isFinite(value)||Math.abs(value)>2147483647)fail('BATTLE_STATE','Invalid battle state number.');return value;}
      if(typeof value==='string'){if(value.length>4096)fail('BATTLE_STATE','Battle state string is too long.');return value;}
      if(!value||typeof value!=='object'||seen.has(value))fail('BATTLE_STATE','Battle state must be acyclic JSON data.');
      seen.add(value);let result;
      if(Array.isArray(value)){
        if(value.length>1024)fail('BATTLE_STATE','Battle state array is too long.');
        result=value.map(item=>copy(item,depth+1));
      }else{
        const prototype=Object.getPrototypeOf(value);
        if(prototype!==Object.prototype&&prototype!==null)fail('BATTLE_STATE','Battle state must contain plain objects.');
        const entries=Object.entries(value);if(entries.length>256)fail('BATTLE_STATE','Battle state object has too many fields.');
        result={};
        for(const [key,item] of entries){
          if(['__proto__','prototype','constructor'].includes(key)||key.length>128)fail('BATTLE_STATE','Invalid battle state field.');
          result[key]=copy(item,depth+1);
        }
      }
      seen.delete(value);return result;
    }
    if(!state||typeof state!=='object'||Array.isArray(state))fail('BATTLE_STATE','Battle state must be an object.');
    const result=copy(state,0);
    if('barrierHp' in result&&(!Number.isInteger(result.barrierHp)||result.barrierHp<0))fail('BATTLE_STATE','Barrier HP must be a nonnegative integer.');
    if('emergencyUsed' in result&&typeof result.emergencyUsed!=='boolean')fail('BATTLE_STATE','Emergency-heal flag must be boolean.');
    return result;
  }
  function validateCombatState(state,activeTiers){
    if(!Object.prototype.hasOwnProperty.call(state,'combat'))return;
    const combat=state.combat;
    function object(value,fields){
      if(!value||typeof value!=='object'||Array.isArray(value)||Object.keys(value).length!==fields.length||fields.some(field=>!Object.prototype.hasOwnProperty.call(value,field)))fail('BATTLE_COMBAT','Unexpected combat snapshot fields.');
    }
    function integer(value,min=0,max=2147483647){if(!Number.isInteger(value)||value<min||value>max)fail('BATTLE_COMBAT','Combat number is outside its allowed integer range.');}
    function boolean(value){if(typeof value!=='boolean')fail('BATTLE_COMBAT','Combat flags must be boolean.');}
    function token(value){if(typeof value!=='string'||!value.length||value.length>256||/[\u0000-\u001f]/.test(value))fail('BATTLE_COMBAT','Invalid combat event identity.');}
    function pairs(values,limit,validate){
      if(!Array.isArray(values)||values.length>limit)fail('BATTLE_COMBAT','Invalid combat event collection.');
      const seen=new Set();for(const pair of values){if(!Array.isArray(pair)||pair.length!==2)fail('BATTLE_COMBAT','Invalid combat event entry.');token(pair[0]);if(seen.has(pair[0]))fail('BATTLE_COMBAT','Duplicate combat event identity.');seen.add(pair[0]);validate(pair[1],pair[0]);}
    }
    if(!combat||combat.version!==1)fail('BATTLE_COMBAT_VERSION','Unsupported combat snapshot version.');
    object(combat,['version','tiers','fighters','barrier','started','emergencyUsed','foodCarry','results','procs','casts']);
    if(JSON.stringify(Loadout.validateOwnedTiers(combat.tiers))!==JSON.stringify(activeTiers))fail('BATTLE_COMBAT','Combat tiers differ from the frozen battle loadout.');
    if(!Array.isArray(combat.fighters)||combat.fighters.length!==2)fail('BATTLE_COMBAT','Two saved combat fighters are required.');
    for(const fighter of combat.fighters){
      object(fighter,['hp','maxHp','mana','maxMana','food','maxFood','immune','itemArmor','gold','xp']);
      integer(fighter.maxHp,1);integer(fighter.maxMana);integer(fighter.maxFood);
      integer(fighter.hp,0,fighter.maxHp);integer(fighter.mana,0,fighter.maxMana);integer(fighter.food,0,fighter.maxFood);
      boolean(fighter.immune);boolean(fighter.itemArmor);integer(fighter.gold);integer(fighter.xp);
    }
    integer(combat.barrier,0,combat.fighters[0].maxHp);integer(combat.foodCarry,0,3);boolean(combat.started);boolean(combat.emergencyUsed);
    const resultFields={barrier:['amount','barrier'],heal:['amount','modified','hpBefore','hpAfter'],damage:['raw','modified','reduced','absorbed','hpLoss','hpBefore','hpAfter','reflected','burn','lifesteal','emergency'],cast:['successful','cost','refund','barrier','bonusTurns','burn'],'mana-gain':['amount','mana'],'mana-drain':['amount','mana','blocked'],action:['regen'],reward:['amount','total']};
    pairs(combat.results,32,(result,key)=>{
      const fields=resultFields[key.slice(0,key.indexOf(':'))];if(!fields)fail('BATTLE_COMBAT','Unknown saved combat result kind.');object(result,fields);
      for(const field of fields)if(['successful','blocked'].includes(field))boolean(result[field]);else integer(result[field]);
    });
    if(!Array.isArray(combat.procs)||combat.procs.length>64||new Set(combat.procs).size!==combat.procs.length)fail('BATTLE_COMBAT','Invalid combat proc identities.');
    for(const proc of combat.procs){token(proc);if(!/^(burn-cast:|burn-group:|yin:).+/.test(proc))fail('BATTLE_COMBAT','Unknown saved combat proc kind.');}
    pairs(combat.casts,32,cast=>{object(cast,['side','slot','skillId','lifeStealPercent']);integer(cast.side,0,1);integer(cast.slot,0,2);integer(cast.skillId,0,8);integer(cast.lifeStealPercent,0,100);if(cast.skillId%3!==cast.slot)fail('BATTLE_COMBAT','Saved skill does not match its slot.');});
    integer(state.sequence);
    if(state.action!==null){object(state.action,['id','side']);token(state.action.id);integer(state.action.side,0,1);}
    if(state.cast!==null){
      if(!state.cast||typeof state.cast!=='object')fail('BATTLE_COMBAT','Invalid current cast.');
      token(state.cast.id);integer(state.cast.side,0,1);integer(state.cast.slot,0,2);integer(state.cast.skillId,0,8);
      if(state.cast.skillId%3!==state.cast.slot)fail('BATTLE_COMBAT','Current skill does not match its slot.');
    }
  }
  function validateSidecar(sidecar){
    if(!sidecar||sidecar.schemaVersion!==1)fail('SCHEMA_VERSION','Unsupported battle sidecar schema.');
    if(typeof sidecar.snapshotFingerprint!=='string'||!/^[0-9a-f]{64}$/.test(sidecar.snapshotFingerprint))fail('SNAPSHOT_FINGERPRINT','Invalid original snapshot fingerprint.');
    const loadout=sidecar.loadout;
    if(!loadout)fail('BATTLE_LOADOUT','A frozen battle loadout is required.');
    const snapshot=Loadout.snapshot(loadout.levelsAtStart,loadout.equippedIds);
    if(!Array.isArray(loadout.activeTiers)||JSON.stringify(loadout.activeTiers)!==JSON.stringify(snapshot.activeTiers))fail('BATTLE_LOADOUT','Active tiers do not match the captured loadout.');
    const state=copyState(sidecar.state);validateCombatState(state,snapshot.activeTiers);
    return {schemaVersion:1,battleId:id(sidecar.battleId),profileId:id(sidecar.profileId),snapshotFingerprint:sidecar.snapshotFingerprint,loadout:{equippedIds:Array.from(snapshot.equippedIds),levelsAtStart:Array.from(snapshot.levelsAtStart),activeTiers:Array.from(snapshot.activeTiers)},state};
  }
  function createSidecar(snapshotBytes,context){return validateSidecar({schemaVersion:1,battleId:context.battleId,profileId:context.profileId,snapshotFingerprint:fingerprintSnapshot(snapshotBytes),loadout:context.loadout,state:context.state});}
  function encodeSidecar(sidecar){return Store.encodeRecord('battle',validateSidecar(sidecar));}
  function decodeSidecar(bytes){return validateSidecar(Store.decodeRecord('battle',bytes));}
  function createBattleSave(port){
    for(const name of ['read','write','flush'])if(typeof port?.[name]!=='function')throw new TypeError('Battle save port requires '+name+'().');
    let tail=Promise.resolve();
    function serial(work){const operation=tail.then(work);tail=operation.catch(()=>{});return operation;}
    return Object.freeze({
      save(snapshotBytes,context){
        const sidecar=createSidecar(snapshotBytes,context),bytes=encodeSidecar(sidecar);
        return serial(async()=>{const previous=await port.read(Store.KEYS.battle);if(previous!==null)decodeSidecar(previous);await port.write(Store.KEYS.battle,bytes);await port.flush();return sidecar;});
      },
      restore(snapshotBytes,options={}){
        const fingerprint=fingerprintSnapshot(snapshotBytes);
        return serial(async()=>{
          const bytes=await port.read(Store.KEYS.battle);if(bytes===null)return {mode:'legacy',sidecar:null};
          const sidecar=decodeSidecar(bytes);
          if(sidecar.snapshotFingerprint!==fingerprint)return {mode:'mismatch',reason:'SNAPSHOT_MISMATCH',sidecar:null};
          if(options.profileId!==undefined&&sidecar.profileId!==options.profileId)return {mode:'mismatch',reason:'PROFILE_MISMATCH',sidecar:null};
          return {mode:'restored',sidecar};
        });
      },
      clear:()=>serial(async()=>{await port.write(Store.KEYS.battle,null);await port.flush();})
    });
  }
  return Object.freeze({fingerprintSnapshot,copyState,validateCombatState,validateSidecar,createSidecar,encodeSidecar,decodeSidecar,createBattleSave});
});
