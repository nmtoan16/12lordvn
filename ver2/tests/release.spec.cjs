const {test,expect}=require('@playwright/test');
const fs=require('node:fs'),http=require('node:http'),path=require('node:path'),{execFileSync}=require('node:child_process');
const {start}=require('./helpers/vm.cjs');
let server,origin,directory;
test.beforeAll(async()=>{
 execFileSync(process.execPath,['scripts/package-ver2.mjs']);directory=JSON.parse(fs.readFileSync('dist/latest.json')).directory;
 const types={'.html':'text/html','.js':'text/javascript','.css':'text/css','.json':'application/json','.png':'image/png','.jar':'application/java-archive','.mid':'audio/midi','.wav':'audio/wav','.md':'text/plain'};
 server=http.createServer((req,res)=>{try{const url=new URL(req.url,'http://local'),parts=decodeURIComponent(url.pathname).split('/').filter(Boolean);if(!['ver2','alternative'].includes(parts.shift()))throw Error();if(parts.some(p=>p==='..'||p.startsWith('.'))){res.writeHead(403).end();return;}let file=path.join(directory,...parts);if(fs.statSync(file).isDirectory())file=path.join(file,'index.html');res.writeHead(200,{'Content-Type':types[path.extname(file)]||'application/octet-stream'});res.end(fs.readFileSync(file));}catch{res.writeHead(404).end();}});
 await new Promise(resolve=>server.listen(0,'127.0.0.1',resolve));origin=`http://127.0.0.1:${server.address().port}`;
});
test.afterAll(async()=>{await new Promise(resolve=>server.close(resolve));});
test('standalone release boots under ver2 and another folder name with private files absent',async({page,request})=>{
 const external=[],errors=[];page.on('pageerror',e=>errors.push(e.message));page.on('request',r=>{if(/^http/.test(r.url())&&!r.url().startsWith(origin+'/'))external.push(r.url());});
 for(const prefix of ['ver2','alternative']){
   const frame=await start(page,`${origin}/${prefix}/`);await expect.poll(()=>frame.evaluate(()=>LoanV2.state().ready)).toBe(true);
   expect(frame.url()).toContain(`/${prefix}/runtime/main.html`);
   await frame.evaluate(async()=>{await LoanV2.purchase(0);await LoanV2.flush();});
   for(const relative of ['analysis/assets/index.html','analysis/assets/contact-sheet-major.png','assets/manifest.json','THIRD_PARTY.md','runtime/LICENSE','README-VER2.md','analysis/VER2-VERIFICATION.md','analysis/VER2-BALANCE.md','analysis/verification/ver2/equipment-shop-375.png'])expect((await request.get(`${origin}/${prefix}/${relative}`)).status()).toBe(200);
   for(const relative of ['.git/config','tools/probe.js','node_modules/playwright/package.json','tasks/baseline.json','tests/game.spec.cjs','SPEC.md'])expect([403,404]).toContain((await request.get(`${origin}/${prefix}/${relative}`)).status());
 }
 expect(errors).toEqual([]);expect(external).toEqual([]);
 const manifest=JSON.parse(fs.readFileSync(path.join(directory,'release-manifest.json')));expect(manifest.files.length).toBeGreaterThan(300);
});
test('workspace server blocks development files and serves public ver2 files',async({request})=>{
 for(const relative of ['.git/config','tasks/baseline.json','tests/game.spec.cjs','SPEC.md','package.json','node_modules/playwright/package.json','dist/latest.json'])expect([403,404],relative).toContain((await request.get(relative)).status());
 for(const relative of ['index.html','runtime/main.html','Loan-12-Su-Quan.jar','README-VER2.md'])expect((await request.get(relative)).status(),relative).toBe(200);
});
