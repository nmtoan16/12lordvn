const {test,expect}=require('@playwright/test');
const fs=require('node:fs');
const path=require('node:path');
const output=path.resolve('analysis/verification');
fs.mkdirSync(output,{recursive:true});

async function readState(frame){
  return frame.evaluate(()=>{
    function field(address,className,name,signature,isStatic=false){
      const cls=CLASSES.getClass(className),field=cls.getFieldByName(J2ME.toUTF8(name),J2ME.toUTF8(signature),isStatic);
      return i32[(address+field.byteOffset)>>2];
    }
    if(typeof CLASSES==='undefined'||!J2ME.RuntimeTemplate.all)return {};
    const cls=CLASSES.getClass('cz');
    const runtime=Array.from(J2ME.RuntimeTemplate.all).find(runtime=>runtime.SA[cls.id]);
    if(!runtime)return {};
    const canvas=field(runtime.SA[cls.id],'cz','a','Lcz;',true);if(!canvas)return {};
    const router=field(canvas,'cz','e','Lbv;');if(!router)return {};
    const screen=field(router,'bv','b','Laj;');const dialog=field(router,'bv','d','Lae;');
    const current=screen?J2ME.fromUTF8(J2ME.getClassInfo(screen).utf8Name):null;
    const transition=field(router,'bv','h','Z');
    let menuReady=true;
    if(current==='com/mic/smsgame/suquan/ac'){
      const menu=field(screen,current,'u','Ldv;');
      menuReady=!field(menu,'dv','n','Z');
    }
    let battle=null;
    if(current==='com/mic/smsgame/suquan/ab'){
      const model=field(screen,current,'c','Lcq;'),controller=field(screen,current,'e','Lbu;'),renderer=field(screen,current,'d','Li;');
      const rows=J2ME.getArrayFromAddr(field(model,'cq','e','[[I'));
      const fighters=Array.from(J2ME.getArrayFromAddr(field(model,'cq','f','[Lbn;'))).map(address=>({hp:field(address,'bn','c','I'),mana:field(address,'bn','i','I'),food:field(address,'bn','g','I')}));
      const guide=field(renderer,'i','g','Lw;');
      battle={state:field(controller,'bu','v','I'),turn:field(model,'cq','k','I'),row:field(model,'cq','l','I'),col:field(model,'cq','m','I'),selected:Boolean(field(model,'cq','o','Z')),board:Array.from(rows).slice(2,10).map(row=>Array.from(J2ME.getArrayFromAddr(row)).slice(2,10)),fighters,tutorial:guide?{step:field(guide,'w','k','I'),move:field(guide,'w','c','I'),bubble:Boolean(field(guide,'ds','u','Z'))}:null};
    }
    return {screen:current,screenAddress:screen,transition,menuReady,mapPhase:current==='com/mic/smsgame/suquan/aa'?field(screen,current,'f','I'):null,battle,dialog:dialog?J2ME.fromUTF8(J2ME.getClassInfo(dialog).utf8Name):null,isolates:Array.from(J2ME.RuntimeTemplate.all).length};
  }).catch(()=>({}));
}
async function press(page,key){await page.locator('#game').focus();await page.keyboard.press(key,{delay:120});await page.waitForTimeout(250);}
async function waitScreen(frame,name){await expect.poll(async()=>{const s=await readState(frame);return s.screen===name&&!s.transition&&s.menuReady;},{timeout:25000}).toBe(true);await frame.page().waitForTimeout(150);}
async function start(page){
  await page.goto('./');await page.click('#start');
  const frame=page.frames().find(f=>f.url().includes('runtime/main.html'))||await new Promise(resolve=>page.on('framenavigated',f=>{if(f.url().includes('runtime/main.html'))resolve(f);}));
  await waitScreen(frame,'v');await press(page,'Enter');await waitScreen(frame,'com/mic/smsgame/suquan/ac');return frame;
}

for(const width of [375,768,1440])test(`Layout ${width}px has no horizontal overflow`,async({page})=>{
  await page.setViewportSize({width,height:1000});await page.goto('./');
  expect(await page.evaluate(()=>document.documentElement.scrollWidth)).toBe(width);
  const wrapping=await page.locator('button,kbd,summary,nav a,.brand').evaluateAll(elements=>elements.filter(el=>el.offsetWidth>0&&(getComputedStyle(el).whiteSpace!=='nowrap'||el.scrollWidth>el.clientWidth+1)).map(el=>el.textContent));
  expect(wrapping).toEqual([]);
});

test('Original game creates a character, saves progress and plays a tutorial match',async({page})=>{
  const errors=[];page.on('pageerror',error=>errors.push(error.message));
  const externalRequests=[];page.on('request',request=>{if(/^https?:/.test(request.url())&&new URL(request.url()).origin!==new URL(test.info().project.use.baseURL).origin)externalRequests.push(request.url());});
  const frame=await start(page);
  expect((await readState(frame)).isolates).toBe(1);
  await frame.locator('#canvas').screenshot({path:path.join(output,'game-menu.png')});
  await press(page,'Enter');await waitScreen(frame,'df');
  for(let n=0;n<15&&(await readState(frame)).screen==='df';n++){await page.waitForTimeout(400);await press(page,'e');}
  await waitScreen(frame,'com/mic/smsgame/suquan/z');
  await frame.locator('#canvas').screenshot({path:path.join(output,'game-character.png')});
  await press(page,'q');await expect(frame.locator('#web-player-name')).toBeVisible({timeout:8000});
  await frame.locator('#web-player-name').fill('TOAN');await frame.getByRole('button',{name:'Xong',exact:true}).click();
  await waitScreen(frame,'com/mic/smsgame/suquan/aa');
  await frame.locator('#canvas').screenshot({path:path.join(output,'game-map.png')});
  // Browser pause must freeze the actual game canvas, including pending frames.
  await page.click('#pause');await expect(page.locator('#pause-screen')).toBeVisible();
  await page.waitForTimeout(100);
  const frozen=await frame.locator('#canvas').evaluate(canvas=>canvas.toDataURL());
  await page.waitForTimeout(600);
  expect(await frame.locator('#canvas').evaluate(canvas=>canvas.toDataURL())).toBe(frozen);
  await page.click('#resume');
  // Export through the visible UI and inspect the original RMS filesystem.
  await page.locator('summary').filter({hasText:'Lưu và chuyển tiến trình'}).click();
  const downloadEvent=page.waitForEvent('download');await page.click('#export');const download=await downloadEvent;
  const savePath=path.join(output,'browser-save.json');await download.saveAs(savePath);
  const save=JSON.parse(fs.readFileSync(savePath));expect(save.format).toBe('loan12-save-v1');
  expect(Object.keys(save.files).some(key=>key.includes('RecordStore'))).toBe(true);
  expect(Object.values(save.files).some(record=>Array.isArray(record.data)&&Buffer.from(record.data).includes(Buffer.from('TOAN')))).toBe(true);
  const history=[];
  // Skip narrative through the original right softkey until the training board.
  for(let n=0;n<35&&(await readState(frame)).screen!=='com/mic/smsgame/suquan/ab';n++){
    const state=await readState(frame);history.push(state);
    if(state.mapPhase===2)await press(page,'e');
    else if(state.mapPhase===3)await press(page,'q');
    await page.waitForTimeout(350);
  }
  fs.writeFileSync(path.join(output,'gameplay.json'),JSON.stringify({errors,history},null,2));
  await waitScreen(frame,'com/mic/smsgame/suquan/ab');
  await frame.locator('#canvas').screenshot({path:path.join(output,'game-battle.png')});
  const initial=await readState(frame);history.push(initial);
  expect(initial.battle.board).toHaveLength(8);expect(initial.battle.board.every(row=>row.length===8)).toBe(true);
  // The first scripted action selects a sword at (2,4), then swaps downward.
  for(let n=0;n<20;n++){
    const state=await readState(frame);history.push(state);
    if(state.battle?.state===8&&!state.battle.tutorial?.bubble)break;
    await press(page,'Enter');await page.waitForTimeout(200);
  }
  await press(page,'Enter');
  for(let n=0;n<15;n++){
    const state=await readState(frame);history.push(state);
    if(state.battle?.state===8&&state.battle.selected&&!state.battle.tutorial?.bubble)break;
    await press(page,'Enter');await page.waitForTimeout(200);
  }
  const before=await readState(frame);history.push(before);await press(page,'ArrowDown');
  await expect.poll(async()=>{const state=await readState(frame);history.push(state);return state.battle?.fighters[1].hp;},{timeout:12000}).toBeLessThan(before.battle.fighters[1].hp);
  await frame.locator('#canvas').screenshot({path:path.join(output,'game-match-resolved.png')});
  // Let the original tutorial AI make its scripted response.
  for(let n=0;n<50;n++){
    const state=await readState(frame);history.push(state);
    if(state.battle?.fighters[0].hp<before.battle.fighters[0].hp)break;
    if(state.battle?.state===14&&state.battle.tutorial?.bubble)await press(page,'Enter');
    await page.waitForTimeout(200);
  }
  expect((await readState(frame)).battle.fighters[0].hp).toBeLessThan(before.battle.fighters[0].hp);
  await frame.locator('#canvas').screenshot({path:path.join(output,'game-ai-response.png')});
  fs.writeFileSync(path.join(output,'gameplay.json'),JSON.stringify({errors,history},null,2));
  // The original tutorial has no checkpoint before it finishes. Use its own
  // right-softkey "skip training" confirmation, then export the saved map.
  for(let n=0;n<50;n++){
    const state=await readState(frame);history.push(state);
    if(state.dialog)break;
    if(state.battle?.state===14&&state.battle.tutorial?.bubble)await press(page,'Enter');
    else if(state.battle?.state===8&&state.battle.turn===0)await press(page,'e');
    await page.waitForTimeout(200);
  }
  expect((await readState(frame)).dialog).toBeTruthy();
  await press(page,'q');
  for(let n=0;n<40;n++){
    const state=await readState(frame);history.push(state);
    if(state.screen==='com/mic/smsgame/suquan/aa'&&!state.transition)break;
    if(state.dialog||state.battle?.state===14)await press(page,'Enter');
    await page.waitForTimeout(300);
  }
  await waitScreen(frame,'com/mic/smsgame/suquan/aa');
  const checkpointDownload=page.waitForEvent('download');await page.click('#export');
  await(await checkpointDownload).saveAs(savePath);
  fs.writeFileSync(path.join(output,'gameplay.json'),JSON.stringify({errors,history},null,2));
  // Re-import the original named save and verify a fresh VM reads its RMS data.
  page.once('dialog',dialog=>dialog.accept());
  await page.locator('#save-file').setInputFiles(savePath);
  await expect(page.locator('#status')).toContainText('Đã nhập tiến trình',{timeout:10000});
  await expect.poll(()=>frame.url(),{timeout:10000}).toContain('restore=');
  await waitScreen(frame,'v');await press(page,'Enter');await waitScreen(frame,'com/mic/smsgame/suquan/ac');
  const restoredDownload=page.waitForEvent('download');await page.click('#export');
  const restoredPath=path.join(output,'restored-save.json');await(await restoredDownload).saveAs(restoredPath);
  const restored=JSON.parse(fs.readFileSync(restoredPath));
  expect(Object.values(restored.files).some(record=>Array.isArray(record.data)&&Buffer.from(record.data).includes(Buffer.from('TOAN')))).toBe(true);
  await press(page,'Enter');await waitScreen(frame,'com/mic/smsgame/suquan/aa');
  const restoredState=await readState(frame),layouts=[];
  for(const width of [375,768,1440]){
    await page.setViewportSize({width,height:1000});
    await expect.poll(()=>page.evaluate(()=>document.documentElement.scrollWidth)).toBe(width);
    const stage=await page.locator('#stage').boundingBox(),screen=await page.locator('#game').boundingBox();
    expect(Math.abs(stage.width-screen.width)).toBeLessThan(1);
    expect(Math.abs(stage.height-screen.height)).toBeLessThan(1);
    layouts.push({width,stage,screen});
    await page.screenshot({path:path.join(output,`playing-${width}.png`),fullPage:true});
  }
  expect(errors).toEqual([]);
  expect(externalRequests).toEqual([]);
  fs.writeFileSync(path.join(output,'web-flow.json'),JSON.stringify({result:'pass',restoredState,layouts,errors,externalRequests},null,2));
});

module.exports={readState,press,waitScreen,start};
