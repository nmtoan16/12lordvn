const {test,expect}=require('@playwright/test');
const path=require('node:path');
const commandSignature='javax/microedition/lcdui/NativeMenu.updateCommands.([Ljavax/microedition/lcdui/Command;I[Ljavax/microedition/lcdui/Command;I)V';

async function mount(page,kind){
  await page.goto('about:blank');
  await page.setContent('<iframe width="240" height="320" sandbox="allow-scripts allow-same-origin" srcdoc="<!doctype html><html><body></body></html>"></iframe>');
  const surface=page.frames().find(frame=>frame.parentFrame());
  await surface.addStyleTag({path:path.resolve('runtime/style/main.css')});
  await surface.addStyleTag({path:path.resolve('runtime/web.css')});
  await surface.evaluate(({signature,kind})=>{
    window.sentCommands=[];window.originalCommandCalls=0;window.mycontent='';
    window.Native={[signature]:()=>window.originalCommandCalls++};
    const objects=kind==='phone'?{1:{id:101,commandType:4,shortLabel:'Gửi'},2:{id:102,commandType:7,shortLabel:'Đóng'}}:
      {1:{id:101,commandType:4,shortLabel:kind==='name'?'Xong':'Đồng ý'},2:{id:102,commandType:2,shortLabel:'Trở về'}};
    window.J2ME={getArrayFromAddr:()=>[1,2],fromStringAddr:value=>value};
    window.getHandle=address=>objects[address];window.MIDP={sendCommandEvent:id=>sentCommands.push(id)};
    // Do not define curDisplayableId: upstream owns it in a private closure.
  },{signature:commandSignature,kind});
  await surface.addScriptTag({path:path.resolve('runtime/compat.js')});
  await surface.evaluate(signature=>Native[signature](0,0,0,3,2),commandSignature);
  return surface;
}

test('native character form keeps the MIDP command ids and trims the submitted name',async({page})=>{
  const surface=await mount(page,'name');
  const input=surface.locator('#web-player-name');await expect(input).toBeVisible();
  await expect(input).toHaveAttribute('maxlength','10');
  await input.fill(' TOAN ');await surface.getByRole('button',{name:'Xong',exact:true}).click();
  expect(await surface.evaluate(()=>({commands:sentCommands,value:mycontent,fallbacks:originalCommandCalls}))).toEqual({commands:[101],value:'TOAN',fallbacks:0});
  await expect(input).toHaveCount(0);
});

test('native character form cancels with the original BACK command',async({page})=>{
  const surface=await mount(page,'name');await surface.locator('#web-player-name').press('Escape');
  expect(await surface.evaluate(()=>sentCommands)).toEqual([102]);
});

test('gift phone form uses tel and EXIT command without sending an SMS command',async({page})=>{
  const surface=await mount(page,'phone');const input=surface.locator('#web-phone-number');
  await expect(input).toHaveAttribute('type','tel');await expect(input).toHaveAttribute('maxlength','20');
  await input.fill('0901234567');await surface.getByRole('button',{name:'Gửi',exact:true}).click();
  expect(await surface.evaluate(()=>sentCommands)).toEqual([102]);
});

test('unrelated native alert commands stay with the original renderer',async({page})=>{
  const surface=await mount(page,'alert');await expect(surface.locator('#web-name-dialog')).toHaveCount(0);
  expect(await surface.evaluate(()=>originalCommandCalls)).toBe(1);
});

for(const width of [375,768,1440])test(`native forms keep compact controls unwrapped at ${width}px`,async({page})=>{
  await page.setViewportSize({width,height:1000});
  for(const kind of ['name','phone']){
    const surface=await mount(page,kind);
    const invalid=await surface.locator('#web-name-dialog button,#web-name-dialog label').evaluateAll(elements=>elements.filter(element=>
      getComputedStyle(element).whiteSpace!=='nowrap'||element.scrollWidth>element.clientWidth+1).map(element=>element.textContent));
    expect(invalid).toEqual([]);
    expect(await surface.evaluate(()=>document.documentElement.scrollWidth)).toBe(240);
  }
});
