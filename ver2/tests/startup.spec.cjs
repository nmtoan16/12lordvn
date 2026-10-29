const {test,expect}=require('@playwright/test');
const {start}=require('./helpers/vm.cjs');
test('cold and warm startup omit ad painting and transition once',async({page})=>{
 await page.addInitScript(()=>{
   window.adPaint=[];
   const text=CanvasRenderingContext2D.prototype.fillText;
   CanvasRenderingContext2D.prototype.fillText=function(value,...args){if(/giaitri321|crack/i.test(value))window.adPaint.push(value);return text.call(this,value,...args);};
 });
 for(let pass=0;pass<2;pass++){
   const frame=await start(page);
   const s=await frame.evaluate(()=>({startup:loanStartup,ads:adPaint,hook:LoanJavaHooks.counts['cn.b.()V']}));
   expect(s.ads).toEqual([]);expect(s.startup.transitions).toBe(1);expect(s.hook).toBe(1);
   await frame.locator('#canvas').screenshot({path:`analysis/verification/ver2-startup-${pass}.png`});
 }
});
