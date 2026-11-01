import {HEROES,MISSIONS,MODULES,CHAPTERS,TILES} from './content.js';import {level,enemyIntent} from './engine.js';
export const esc=value=>String(value).replace(/[&<>"']/g,c=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));
export const icon=(name,alt='')=>`<img src="assets/${name}.svg" alt="${alt}">`;
export function portrait(id){const i=HEROES.findIndex(h=>h.id===id);return `<span class="portrait" role="img" aria-label="${HEROES[i].name}" style="--px:${i%3*50}%;--py:${Math.floor(i/3)*100}%"></span>`;}
export function enemyPortrait(id){const row=Math.floor(id/3),ys=[0,358,720,1078],hs=[358,362,358,458];return `<span class="enemy-art" role="img" aria-label="${MISSIONS[id].enemy}" style="--ex:${id%3*50}%;--ey:${ys[row]/(1536-hs[row])*100}%;--eh:${1536/hs[row]*100}%"></span>`;}
export const traitText=t=>({burn:'Đốt 8 HP ×3 lượt.',heal:'Hồi 40 HP.',shield:'Tạo lá chắn.',drain:'Rút 8 năng lượng.',stun:'Choáng một lượt.',reflect:'Phản lại 35% sát thương HP.',boss:'Sát thương mạnh, tạo khiên và hút năng lượng.'}[t]);
export function command(p,selected,blocked){
 const m=MISSIONS[selected],lv=level(p.xp),lead=HEROES.find(h=>h.id===p.team[0])||HEROES[0];
 const teamHp=lead.hp+(lv-1)*24,teamAtk=lead.attack+(lv-1)*2;
 return `<section class="hero"><div class="hero-copy"><span class="eyebrow">CHIẾN DỊCH 01 / ĐÊM CUỐI CÙNG</span><h1>GIỮ LẤY<br><em>BÌNH MINH.</em></h1><p>Thành phố đã quên màu nắng. Sáu người ở lại, một lời hứa chưa tắt. Mỗi nước đi của bạn là một cơ hội đưa tất cả trở về.</p><div class="actions"><button class="primary" data-action="campaign">Vào chiến dịch <span>↗</span></button>${p.battle?'<button class="ghost" data-action="resume">Tiếp tục trận →</button>':''}</div><div class="hero-stats"><span><strong>06</strong>ANH HÙNG</span><span><strong>18</strong>KỸ NĂNG</span><span><strong>${String(p.cleared.length).padStart(2,'0')}/12</strong>KHU VỰC GIẢI PHÓNG</span></div></div><span class="hero-coordinate">SAO TRẦM // 04:58 // ĐÊM THỨ 109</span></section>
<div class="command">
 <div class="loadout-strip" aria-label="Tóm tắt biên chế">
  <div class="loadout-group"><span class="eyebrow">BIÊN CHẾ HIỆN TẠI · CẤP ${lv}</span><strong>${teamHp} HP · ${teamAtk} CÔNG · ${p.credits.toLocaleString('vi-VN')} ◈</strong></div>
  <div class="loadout-pills">${p.team.map((id,idx)=>{const h=HEROES.find(x=>x.id===id);return `<span class="squad-pill ${idx===0?'leader':''}"><b>0${idx+1}</b> ${h.name} <small>${idx===0?'Chỉ huy':'Chi viện'}</small></span>`;}).join('')}</div>
  <div class="loadout-nav"><button data-action="jump" data-id="crew-sec">Đội hình</button><button data-action="campaign">Chiến dịch</button><button data-action="jump" data-id="workshop-sec">Xưởng Module</button></div>
 </div>
 <div class="section-head" id="crew-sec"><div><span class="eyebrow">CHỌN NHỮNG NGƯỜI ĐỒNG HÀNH</span><h2>Biệt đội của bạn</h2></div><p>${p.team.length}/3 thành viên · Cấp ${lv}<br>${p.credits.toLocaleString('vi-VN')} tín dụng</p></div>
 <div class="crew">${HEROES.map(h=>{const idx=p.team.indexOf(h.id),sel=idx>=0;return `<button class="hero-card ${sel?'selected':''}" data-action="team" data-id="${h.id}" aria-pressed="${sel}" title="${h.passive} (${h.subPassive})">${portrait(h.id)}${sel?`<span class="mark">0${idx+1} · ${idx===0?'CHỈ HUY':'CHI VIỆN'}</span>`:''}<span class="card-label"><b>${h.name}</b><small>${h.title} / ${h.role}</small><small class="card-sub">${h.passive}</small><small class="card-assist">⚡ Chi viện: ${h.assist.short}</small></span></button>`;}).join('')}</div>
 <p class="hint-box">Chọn tối đa 3 người. Người đứng đầu (01) quyết định HP/Công và Nội tại chính; 2 thành viên dự bị (02, 03) đóng góp 50% Nội tại phụ và tung đòn Chi viện khi đổi Chỉ huy.</p>
 <div class="campaign" id="campaign"><div><div class="section-head"><div><span class="eyebrow">BẢN ĐỒ TÍN HIỆU</span><h2>Đường tới bình minh</h2></div><p>Đã mở ${p.unlocked+1}/12 khu vực</p></div><div class="mission-list">${MISSIONS.map((x,i)=>`${i%4===0?`<p class="chapter-label">${CHAPTERS[x.chapter]}</p>`:''}<button class="mission ${i===selected?'selected':''} ${p.cleared.includes(i)?'cleared':''}" data-action="mission" data-id="${i}" ${i>p.unlocked?'disabled':''}><span class="number">${String(i+1).padStart(2,'0')}</span><span><b>${x.name}</b><small>${x.enemy} · ${x.hp} HP</small></span><span>${p.cleared.includes(i)?'✓':i>p.unlocked?'·':'↗'}</span></button>`).join('')}</div></div>
 <aside class="briefing"><span class="eyebrow">NHIỆM VỤ ${String(selected+1).padStart(2,'0')} / ${selected===11?'TRẬN CUỐI':p.cleared.includes(selected)?'ĐÃ GIẢI PHÓNG (CHƠI LẠI 50%)':'SẴN SÀNG TRIỂN KHAI'}</span>${enemyPortrait(selected)}<h2>${m.name}</h2><p>${m.brief}</p><div class="brief-stats"><span>ĐỐI THỦ<b>${m.hp} HP · ${m.attack}⚔</b></span><span>THƯỞNG<b>${m.reward} ◈</b></span><span>DỮ LIỆU<b>${m.xp} XP</b></span></div><p class="passive">${m.id===11?'Ba khóa: dưới ⅔ và ⅓ HP, Varkhos tăng Công, nhận khiên và rút năng lượng của bạn.':'Giao thức: '+traitText(m.trait)} Đối thủ dùng kỹ năng khi có 18 năng lượng.</p><button class="primary" data-action="launch" ${blocked?'disabled':''}>Triển khai biệt đội →</button>${p.battle?.status==='playing'?'<p>Trận đang lưu được giữ cho đến khi xác nhận triển khai trận khác.</p>':''}</aside></div>
 <div class="section-head" id="workshop-sec"><div><span class="eyebrow">XƯỞNG CỘNG HƯỞNG</span><h2>Module chiến thuật</h2></div><p>${p.equipped.length}/3 đang lắp · Bậc tối đa III</p></div>
 <div class="module-list">${MODULES.map((x,i)=>{const tier=p.modules[i],eq=p.equipped.includes(i);return `<article class="module ${eq?'equipped':''} ${tier===3?'awakened':''}">${icon(x.icon)}<small>${eq?'ĐANG LẮP':'TRONG KHO'} · BẬC ${['0','I','II','III'][tier]}${tier===3?' ✦ THỨC TỈNH':''}</small><h3>${x.name}</h3><p>${x.desc}</p><p class="module-awaken">✦ ${x.awakened}</p><div class="actions"><button data-action="buy" data-id="${i}" ${tier>=3||blocked?'disabled':''}>${tier>=3?'Tối đa':`Nâng · ${x.price[tier]} ◈`}</button><button data-action="equip" data-id="${i}" ${!tier||blocked?'disabled':''}>${eq?'Tháo':'Lắp'}</button></div></article>`;}).join('')}</div>
 <p class="hint-box">Có thể mua khi thiếu tín dụng: trừ tối đa số đang có, phần thiếu được miễn. Nâng lên Bậc III mở khóa Đặc tính Thức Tỉnh trong chiến đấu.</p>
</div>`;
}
const bar=(value,max,cls='',label='')=>`<div class="meter ${cls}" role="progressbar" aria-valuemin="0" aria-valuemax="${max}" aria-valuenow="${value}" aria-label="${label||(cls==='mana'?'Năng lượng':cls==='shield'?'Lá chắn':cls==='battery'?'Pin hỗ trợ':'Sinh lực')}"><i style="--value:${Math.min(100,Math.max(0,value/max*100))}%"></i></div>`;
const effects=(f,isPlayer=false,s=null)=>{
 const badges=[];
 if(f.burn)badges.push(`<span class="status-pill burn">🔥 Đốt ×${f.burn}</span>`);
 if(f.stun)badges.push(`<span class="status-pill stun">⚡ Choáng ×${f.stun}</span>`);
 if(f.reflect)badges.push(`<span class="status-pill reflect">🛡️ Phản đòn ×${f.reflect}</span>`);
 if(isPlayer&&s&&s.loadout.includes(3)&&s.tiers[3]===3&&!s.seedSaved)badges.push(`<span class="status-pill seed">✦ Mầm Cấp Cứu</span>`);
 return badges.join('')||`<span class="status-idle">Trạng thái ổn định</span>`;
};
export function boardHTML(board,disabled=false,selected=-1,drop=false,specials=null){
 return board.map((t,i)=>{
  const spec=specials?.[i]||0,specCls=spec?`special special-${spec}`:'',specBadge=spec?`<em class="spec-badge" aria-hidden="true">${['','↔','↕','✦'][spec]}</em>`:'';
  const specLabel=spec===1?' (Ô Xung Ngang)':spec===2?' (Ô Xung Dọc)':spec===3?' (Lõi Siêu Tân Tinh)':'';
  return `<button class="tile ${i===selected?'selected':''} ${drop?'drop':''} ${specCls}" style="--tile:${TILES[t].color};--row:${Math.floor(i/8)}" data-cell="${i}" role="gridcell" aria-label="Hàng ${Math.floor(i/8)+1}, cột ${i%8+1}: ${TILES[t].name}${specLabel}" aria-selected="${i===selected}" tabindex="${i===0?'0':'-1'}" ${disabled?'disabled':''}>${icon(TILES[t].icon)}${specBadge}</button>`;
 }).join('');
}
export function battleView(s,busy,paused){
 const h=HEROES.find(x=>x.id===s.team[s.active]),hi=HEROES.indexOf(h),m=MISSIONS[s.mission],p=s.player,e=s.enemy;
 const intent=enemyIntent(s),bench=s.team.filter((_,i)=>i!==s.active).map(id=>HEROES.find(x=>x.id===id));
 const specialCount=(s.specials||[]).filter(x=>x>0).length;
 return `<section class="battle"><div class="battle-title"><div><span class="eyebrow">${CHAPTERS[m.chapter]} · KHU VỰC ${String(m.id+1).padStart(2,'0')}</span><h2>${m.name}</h2></div><div class="tools"><button data-action="hint">Gợi ý [H]</button><button data-action="pause">${paused?'Tiếp tục':'Tạm dừng'}</button></div></div><div class="arena">
<aside class="fighter player ${p.shield>0?'shielded':''} ${p.burn>0?'burning':''}" id="player">
 ${portrait(h.id)}<span class="tag">${h.role} / CHỈ HUY · ⚔ ${p.attack}</span><h3>${h.name}</h3>
 <div class="health-head"><span>SINH LỰC</span><b>${p.hp} / ${p.maxHp}</b></div>${bar(p.hp,p.maxHp,'','Sinh lực')}
 <div class="mana"><div class="health-head"><span>NĂNG LƯỢNG</span><b>${p.mana}/40</b></div>${bar(p.mana,40,'mana','Năng lượng')}</div>
 <div class="sub-meters">
  <div><div class="health-head"><span>◇ LÁ CHẮN</span><b>${p.shield}/100</b></div>${bar(p.shield,100,'shield','Lá chắn')}</div>
  <div class="${p.battery<=24?'low-battery':''}"><div class="health-head"><span>▰ PIN HỖ TRỢ</span><b>${p.battery}</b></div>${bar(p.battery,160,'battery','Pin hỗ trợ')}</div>
 </div>
 <div class="resources"><span>◇ <b>${p.shield}</b> khiên</span><span>▰ <b>${p.battery}</b> pin</span><span>◈ <b>+${s.loot}</b></span></div>
 <div class="effects">${effects(p,true,s)}</div>
 <div class="crew-switch">${s.team.map((id,i)=>{const ch=HEROES.find(x=>x.id===id);return `<button class="${i===s.active?'active':''}" data-action="switch" data-id="${i}" title="${i===s.active?ch.name+' (Đang chỉ huy)':`Đổi sang ${ch.name} · Chi viện: ${ch.assist.short}`}" aria-label="Đổi chỉ huy sang ${id}" ${busy||paused||s.turn||s.switched||s.status!=='playing'?'disabled':''}>${portrait(id)}<small class="switch-tag">${i===s.active?'★':s.switched?'·':'⚡'}</small></button>`;}).join('')}</div>
 <p class="passive"><b>Nội tại:</b> ${h.passive}${bench.length?`<br><b>Chi viện:</b> ${bench.map(b=>`${b.name} (${b.subPassive.replace('Dự bị: ','')})`).join(' · ')}`:''}<br><small>Đổi chỉ huy 1 lần/lượt để kích hoạt kỹ năng Chi Viện.</small></p>
 <div class="journal"><h4>MODULE ĐANG HIỆU LỰC</h4><ul>${s.loadout.map(i=>`<li>${MODULES[i].name} ${['','I','II','III'][s.tiers[i]]}${s.tiers[i]===3?' ✦':''}</li>`).join('')||'<li>Không có module. Thu tài nguyên để tự tạo lợi thế.</li>'}</ul></div>
</aside>
<div class="board-column">
 <div class="board-head"><span class="turn ${s.turn?'enemy-turn':''}" role="status">${paused?'ĐÃ TẠM DỪNG':s.status==='won'?'CHIẾN THẮNG':s.status==='lost'?'TÍN HIỆU THẤT BẠI':s.turn?'LƯỢT ĐỐI THỦ':'ĐẾN LƯỢT BẠN'}</span><span>LƯỢT ${String(s.round).padStart(2,'0')}${s.chain>1?' · CHUỖI ×'+s.chain:''}${specialCount?` · ✦${specialCount} QUÁ TẢI`:''}</span></div>
 <div class="board-shell"><div class="board" role="grid" aria-label="Sàn đấu 8 hàng 8 cột">${boardHTML(s.board,busy||paused||s.turn===1||s.status!=='playing',-1,false,s.specials)}</div></div>
 <p class="board-caption">Ghép 3 để thu · Ghép 4 tạo <b>Ô Xung Tuyến (↔/↕)</b> · Ghép 5/L-T tạo <b>Lõi Siêu Tân Tinh (✦)</b></p>
 <div class="skill-list">${h.skills.map((k,i)=>{const cd=i===2?s.cooldowns[hi]:0,ready=!cd&&p.mana>=k.cost;return `<button class="skill ${ready?'ready':''}" style="--ready:${Math.min(100,Math.floor(p.mana/k.cost*100))}%" data-action="cast" data-id="${i}" title="${k.text}" ${busy||paused||s.turn||s.status!=='playing'||!ready?'disabled':''}><small class="skill-number">${i+1}</small><span>⚡${k.cost} NĂNG LƯỢNG</span><strong>${k.name}</strong><small class="skill-desc">${cd?'Hồi chiêu '+cd+' lượt':(k.short||'KÍCH HOẠT')}</small></button>`;}).join('')}</div>
 <div class="mobile-feed" aria-live="polite"><span>📡 ${esc(s.log[0]||'Sẵn sàng chiến đấu.')}</span></div>
 <div class="legend">${TILES.map(t=>`<span title="${t.help}">${icon(t.icon)}${t.short}</span>`).join('')}</div>
</div>
<aside class="fighter enemy ${e.shield>0?'shielded':''} ${e.burn>0?'burning':''}" id="enemy">
 ${enemyPortrait(m.id)}<span class="tag">${m.id===11?'KẺ KHÓA BÌNH MINH':'THẾ LỰC HẮC ĐIỆN'}</span><h3 title="${m.enemy}">${m.enemy}</h3>
 <div class="health-head"><span>SINH LỰC</span><b>${e.hp} / ${e.maxHp}</b></div>${bar(e.hp,e.maxHp,'','Sinh lực đối thủ')}
 <div class="mana"><div class="health-head"><span>NĂNG LƯỢNG</span><b>${e.mana}/40</b></div>${bar(e.mana,40,'mana','Năng lượng đối thủ')}</div>
 <div class="sub-meters">
  <div><div class="health-head"><span>◇ LÁ CHẮN</span><b>${e.shield}/100</b></div>${bar(e.shield,100,'shield','Lá chắn đối thủ')}</div>
  <div><div class="health-head"><span>⚔ SỨC CÔNG</span><b>${e.attack}</b></div>${bar(e.attack,25,'attack','Sức công đối thủ')}</div>
 </div>
 <div class="resources"><span>◇ <b>${e.shield}</b> khiên</span><span>⚡ <b>${e.attack}</b> Công</span></div>
 <div class="effects">${effects(e)}</div>
 ${m.id===11?`<div class="boss-phases" aria-label="Khóa ${s.phase} trên 3">${[1,2,3].map(i=>`<span class="${i<=s.phase?'on':''}"></span>`).join('')}</div>`:''}
 <div class="enemy-intent ${intent.ready?'intent-ready':''}">${intent.ready?'⚠ SẮP DÙNG KỸ NĂNG':'CẦN 18 NĂNG LƯỢNG ('+e.mana+'/18)'}<br>${traitText(m.trait)}</div>
 <div class="journal"><h4>NHẬT KÝ GIAO TRANH</h4><ul>${s.log.slice(0,6).map(l=>`<li>${esc(l)}</li>`).join('')}</ul></div>
</aside></div></section>`;
}
