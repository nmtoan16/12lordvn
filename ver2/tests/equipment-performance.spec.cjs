const {test,expect}=require('@playwright/test');
const fs=require('node:fs');
const {equipmentBattle}=require('./helpers/equipment-battle.cjs');
const {configureBattle}=require('./helpers/combat-fixture.cjs');
test('Matched live VM renderer fixture measures ordinary versus golden effects',async({page})=>{
  const frame=await equipmentBattle(page,[2,4,5]);
  await frame.evaluate(()=>{
    const context=MIDP.deviceContext,original=context.drawImage;
    window.v2PerformanceProbe={refresh:[],original,context};
    // This is the visible device context, not the internal offscreen graphics.
    context.drawImage=function(...args){v2PerformanceProbe.refresh.push(performance.now());return original.apply(this,args);};
  });
  const phases=[];
  for(const stone of [0,3]){
    await configureBattle(frame,{tiers:[0,0,3,0,3,stone]});
    await page.waitForTimeout(500);
    phases.push(await frame.evaluate(async stone=>{
      LoanVFX.clear();LoanVFX.resetMetrics();v2PerformanceProbe.refresh.length=0;
      const raf=[],heapBefore=performance.memory?.usedJSHeapSize??null;
      const started=performance.now();let previous=started,lastEvent=started,eventCount=0;
      await new Promise(resolve=>{
        function sample(now){
          raf.push(now-previous);previous=now;
          if(now-lastEvent>=100){LoanV2.emitCombat({type:'damage',amount:30,side:1});LoanV2.emitCombat({type:'cast',skillId:eventCount++%9,side:0});lastEvent=now;}
          if(now-started>=3000)resolve();else requestAnimationFrame(sample);
        }
        requestAnimationFrame(sample);
      });
      const summarize=values=>{const sorted=[...values].sort((a,b)=>a-b);return {count:values.length,p50:sorted[Math.floor(sorted.length*.5)]??null,p95:sorted[Math.floor(sorted.length*.95)]??null,max:sorted.at(-1)??null};};
      const refresh=v2PerformanceProbe.refresh,intervals=refresh.slice(1).map((time,i)=>time-refresh[i]);
      return {stoneTier:stone,eventCount,raf:summarize(raf.slice(1)),vmCanvasRefresh:summarize(intervals),vfx:LoanVFX.stats(),heapBefore,heapAfter:performance.memory?.usedJSHeapSize??null};
    },stone));
    expect(phases.at(-1).raf.count).toBeGreaterThan(30);
    expect(phases.at(-1).vmCanvasRefresh.count).toBeGreaterThan(20);
    expect(phases.at(-1).vfx.text).toBeLessThanOrEqual(12);
    expect(phases.at(-1).vfx.particles).toBeLessThanOrEqual(24);
  }
  await frame.evaluate(()=>{v2PerformanceProbe.context.drawImage=v2PerformanceProbe.original;});
  fs.mkdirSync('analysis/verification/ver2',{recursive:true});
  fs.writeFileSync('analysis/verification/ver2/vfx-matched-performance.json',JSON.stringify({
    fixture:'Same browser, same live tutorial VM, seeded board and identical stats; controller idle fixture; Armor III + Tome III, Stone 0 versus III. Identical renderer events every 100 ms. Times in milliseconds.',
    limitations:'Comparison isolates added golden decoration in current ver2; not a pre-upgrade v1 baseline. Canvas refresh interval includes game sleep and browser scheduling, not just render CPU. Chromium heap is a coarse process measurement.',
    phases
  },null,2));
});
