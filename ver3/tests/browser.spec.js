import {test,expect} from '@playwright/test';
import * as E from '../src/engine.js';import * as S from '../src/storage.js';import {HEROES} from '../src/content.js';
let runtimeErrors=[];test.beforeEach(()=>{runtimeErrors=[];});test.afterEach(()=>{expect(runtimeErrors).toEqual([]);});
const watch=page=>{page.on('pageerror',e=>runtimeErrors.push(e.message));page.on('console',m=>{if(m.type()==='error')runtimeErrors.push(m.text())});};
const base=process.env.GAME_URL||'http://localhost/12suquan/ver3/';
async function seeded(browser,profile,options={}){const context=await browser.newContext({viewport:{width:1440,height:1000},...options,storageState:{cookies:[],origins:[{origin:new URL(base).origin,localStorage:[{name:S.KEY,value:S.serialize(profile)},{name:'ver2:test-preservation',value:'untouched'}]}]}});const page=await context.newPage();watch(page);await page.goto(base);await page.waitForFunction(()=>window.Afterglow);return {page,context};}
async function ready(page){await page.waitForFunction(()=>!Afterglow.busy()&&(Afterglow.state().battle?.turn===0||Afterglow.state().battle?.status!=='playing'));}
async function move(page){const s=(await page.evaluate(()=>Afterglow.state())).battle;const m=E.bestMove(s);await page.locator(`[data-cell="${m.a}"]`).click();await page.locator(`[data-cell="${m.b}"]`).click();}
async function noOverflow(page){expect(await page.evaluate(()=>document.documentElement.scrollWidth<=innerWidth)).toBe(true);expect(await page.locator('button').evaluateAll(els=>els.filter(e=>getComputedStyle(e).whiteSpace!=='nowrap').length)).toBe(0);}
for(const width of [375,768,1440])test(`responsive ${width}: complete art, campaign, battle, no overflow`,async({page})=>{
 const errors=[];page.on('pageerror',e=>errors.push(e.message));page.on('console',m=>{if(m.type()==='error')errors.push(m.text())});page.on('response',r=>{if(r.status()>=400)errors.push(r.status()+' '+r.url())});
 await page.setViewportSize({width,height:1000});await page.goto(base);await page.waitForFunction(()=>window.Afterglow);await page.waitForLoadState('networkidle');await expect(page.locator('.hero-card')).toHaveCount(6);await noOverflow(page);
 await page.screenshot({path:`evidence/home-${width}.png`,fullPage:width===375});
 await page.locator('[data-action=launch]').click();await expect(page.locator('.dialog-line')).toHaveCount(3);await page.locator('[data-dialog=claim]').click();await page.locator('.board').waitFor();await noOverflow(page);await expect(page.locator('.tile')).toHaveCount(64);
 await page.screenshot({path:`evidence/battle-${width}.png`,fullPage:true});expect(errors).toEqual([]);
});
test('team rules, zero-credit upgrades, exactly three modules, frozen loadout',async({browser})=>{
 const p=E.newProfile();p.credits=0;const {page,context}=await seeded(browser,p);
 await page.locator('[data-action=team][data-id=nham]').click();expect((await page.evaluate(()=>Afterglow.state())).team).toHaveLength(3);
 await page.locator('[data-action=team][data-id=sora]').click();await page.locator('[data-action=team][data-id=nham]').click();expect((await page.evaluate(()=>Afterglow.state())).team).toEqual(['lyra','yen','nham']);
 for(let i=0;i<4;i++){await page.locator(`[data-action=buy][data-id="${i}"]`).click();await expect(page.locator('#dialog')).toContainText('Thực trừ 0');await page.locator('[data-dialog=claim]').click();await page.locator(`[data-action=equip][data-id="${i}"]`).click();}
 let current=await page.evaluate(()=>Afterglow.state());expect(current.credits).toBe(0);expect(current.equipped).toEqual([0,1,2]);expect(current.modules.slice(0,4)).toEqual([1,1,1,1]);
 await page.locator('[data-action=launch]').click();await page.locator('[data-dialog=claim]').click();current=await page.evaluate(()=>Afterglow.state());expect(current.battle.loadout).toEqual([0,1,2]);await page.locator('#home').click();
 await page.locator('[data-action=buy][data-id="0"]').click();await page.locator('[data-dialog=claim]').click();current=await page.evaluate(()=>Afterglow.state());expect(current.modules[0]).toBe(2);expect(current.battle.tiers[0]).toBe(1);await context.close();
});
test('real victory through UI, AI, skills, reward once and next mission',async({browser})=>{
 const p=E.newProfile();p.settings.reduced=true;p.battle=E.newBattle(p,0,42);const {page,context}=await seeded(browser,p);await page.locator('[data-action=resume]').click();
 for(let n=0;n<80;n++){await ready(page);const s=(await page.evaluate(()=>Afterglow.state())).battle;if(s.status!=='playing')break;if(s.player.mana>=10)await page.locator('[data-action=cast][data-id="0"]').click();else await move(page);}
 await ready(page);let current=await page.evaluate(()=>Afterglow.state());expect(current.battle.status).toBe('won');expect(current.unlocked).toBe(1);expect(current.cleared).toEqual([0]);const credits=current.credits;
 await page.screenshot({path:'evidence/victory.png'});await page.reload();await page.locator('[data-action=resume]').click();expect((await page.evaluate(()=>Afterglow.state())).credits).toBe(credits);await page.locator('[data-dialog=claim]').click();await expect(page.locator('[data-action=mission][data-id="1"]')).toBeEnabled();expect((await page.evaluate(()=>Afterglow.state())).battle).toBe(null);await context.close();
});
test('keyboard, swipe, pause and reload preserve exact combat state',async({browser})=>{
 const p=E.newProfile();p.unlocked=1;p.battle=E.newBattle(p,1,42); // mission unlock is set before validation below
 p.unlocked=1;const {page,context}=await seeded(browser,p,{viewport:{width:375,height:1000},hasTouch:true});await page.locator('[data-action=resume]').click();
 await page.locator('[data-action=hint]').click();await expect(page.locator('.hinted')).toHaveCount(2);await page.locator('[data-cell="0"]').focus();await page.keyboard.press('ArrowRight');await expect(page.locator('[data-cell="1"]')).toBeFocused();
 await page.keyboard.press('p');const before=await page.evaluate(()=>Afterglow.state());await expect(page.locator('.turn')).toHaveText('ĐÃ TẠM DỪNG');await page.waitForTimeout(850);expect(await page.evaluate(()=>Afterglow.state())).toEqual(before);await page.keyboard.press('p');
 const m=E.bestMove(before.battle),a=await page.locator(`[data-cell="${m.a}"]`).boundingBox(),b=await page.locator(`[data-cell="${m.b}"]`).boundingBox();await page.mouse.move(a.x+a.width/2,a.y+a.height/2);await page.mouse.down();await page.mouse.move(b.x+b.width/2,b.y+b.height/2,{steps:5});await page.mouse.up();await ready(page);
 await page.locator('[data-action=pause]').click();const snapshot=await page.evaluate(()=>Afterglow.state());expect(snapshot.battle.board).not.toEqual(before.battle.board);await page.reload();await page.locator('[data-action=resume]').click();const reloaded=await page.evaluate(()=>Afterglow.state());expect(reloaded.battle.board).toEqual(snapshot.battle.board);expect(reloaded.battle.player).toEqual(snapshot.battle.player);expect(reloaded.battle.seed).toBe(snapshot.battle.seed);await context.close();
});
test('cannot swipe for the AI during its thinking interval',async({browser})=>{
 const p=E.newProfile();p.battle=E.newBattle(p,0,5);p.battle.turn=1;const {page,context}=await seeded(browser,p);await page.locator('[data-action=resume]').click();const before=await page.evaluate(()=>Afterglow.state());
 const a=await page.locator('[data-cell="0"]').boundingBox(),b=await page.locator('[data-cell="1"]').boundingBox();await page.mouse.move(a.x+a.width/2,a.y+a.height/2);await page.mouse.down();await page.mouse.move(b.x+b.width/2,b.y+b.height/2);await page.mouse.up();expect((await page.evaluate(()=>Afterglow.state())).battle.seed).toBe(before.battle.seed);await ready(page);await context.close();
});
test('export/import, corrupt rejection and other version keys remain intact',async({browser})=>{
 const p=E.newProfile();p.credits=937;const {page,context}=await seeded(browser,p);await page.locator('#settings').click();const download=page.waitForEvent('download');await page.locator('[data-dialog=export]').click();const file=await download;const path=await file.path();
 await page.locator('#import-file').setInputFiles({name:'bad.json',mimeType:'application/json',buffer:Buffer.from('{bad')});await expect(page.locator('#toast')).toHaveClass('show');expect((await page.evaluate(()=>Afterglow.state())).credits).toBe(937);
 page.on('dialog',d=>d.accept());await page.locator('[data-dialog=reset]').click();expect((await page.evaluate(()=>Afterglow.state())).credits).toBe(360);await page.locator('#settings').click();await page.locator('#import-file').setInputFiles(path);await expect(page.locator('#dialog')).not.toBeVisible();expect((await page.evaluate(()=>Afterglow.state())).credits).toBe(937);
 expect((await context.storageState()).origins[0].localStorage.find(x=>x.name==='ver2:test-preservation').value).toBe('untouched');await context.close();
});
test('final boss phase 3 and complete ending UI',async({browser})=>{
 const p=E.newProfile();p.unlocked=11;p.xp=2000;p.battle=E.newBattle(p,11,91);p.battle.enemy.hp=190;p.battle.player.mana=40;E.cast(p.battle,0);p.battle.turn=0;p.battle.player.mana=40;p.battle.enemy.hp=1;const {page,context}=await seeded(browser,p);await page.locator('[data-action=resume]').click();expect((await page.evaluate(()=>Afterglow.state())).battle.phase).toBe(3);await page.screenshot({path:'evidence/boss-varkhos.png',fullPage:true});
 await page.locator('[data-action=cast][data-id="0"]').click();await expect(page.locator('.victory')).toHaveText('BÌNH MINH TRỞ LẠI.');await page.screenshot({path:'evidence/ending.png'});await page.locator('[data-dialog=claim]').click();expect((await page.evaluate(()=>Afterglow.state())).endingSeen).toBe(true);await context.close();
});
test('file URL explains how to open HTTP instead of an endless loading screen',async({page})=>{await page.goto(new URL('../index.html',import.meta.url).href);await expect(page.locator('.file-message')).toContainText('Bật Apache');await expect(page.getByRole('link',{name:'Mở Dư Quang trên localhost'})).toHaveAttribute('href','http://localhost/12suquan/ver3/');});
test('six distinct hero effects, sound and reduced motion settings persist',async({browser})=>{
 for(const hero of HEROES){const p=E.newProfile();p.team=[hero.id];p.unlocked=11;p.battle=E.newBattle(p,11,42);p.battle.player.mana=40;p.battle.player.hp=180;const {page,context}=await seeded(browser,p);await page.locator('[data-action=resume]').click();await page.waitForLoadState('networkidle');await page.locator('[data-action=cast][data-id="1"]').click();await expect(page.locator('.cast-banner').first()).toBeVisible();if(hero.id==='kinh')await page.screenshot({path:'evidence/skill-prism.png',fullPage:true});await context.close();}
 const {page,context}=await seeded(browser,E.newProfile());await page.locator('#settings').click();await page.locator('[data-dialog=sound]').click();await expect(page.locator('[data-dialog=sound]')).toHaveText('Đang bật');await page.locator('[data-dialog=reduced]').click();await expect(page.locator('body')).toHaveClass('reduced');await page.reload();expect((await page.evaluate(()=>Afterglow.state())).settings).toEqual({sound:true,reduced:true});await context.close();
});
test('defeat returns to command without losing earned credits or XP',async({browser})=>{
 const p=E.newProfile();p.credits=1234;p.xp=500;p.battle=E.newBattle(p,0,21);p.battle.player.hp=0;p.battle.status='lost';const {page,context}=await seeded(browser,p);await page.locator('[data-action=resume]').click();await expect(page.locator('#dialog')).toContainText('Chúng ta vẫn còn cơ hội.');await page.locator('[data-dialog=claim]').click();const saved=await page.evaluate(()=>Afterglow.state());expect(saved.credits).toBe(1234);expect(saved.xp).toBe(500);expect(saved.battle).toBe(null);await context.close();
});
