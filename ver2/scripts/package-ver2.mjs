import {cp,mkdir,readdir,readFile,writeFile} from 'node:fs/promises';
import path from 'node:path';
import {fileURLToPath} from 'node:url';
import {createHash} from 'node:crypto';
const root=path.resolve(path.dirname(fileURLToPath(import.meta.url)),'..');
const stamp=new Date().toISOString().replace(/[:.]/g,'-');
const destination=path.join(root,'dist',stamp,'ver2');
await mkdir(destination,{recursive:true});
const entries=['index.html','.htaccess','Loan-12-Su-Quan.jar','web','assets','runtime','THIRD_PARTY.md','README-VER2.md','analysis/LOGIC.md','analysis/assets/index.html','analysis/assets/contact-sheet-major.png'];
for(const entry of entries){await mkdir(path.dirname(path.join(destination,entry)),{recursive:true});await cp(path.join(root,entry),path.join(destination,entry),{recursive:true});}
for(const entry of ['VER2-VERIFICATION.md','VER2-RELEASE.md','VER2-HOOKS.md','VER2-DATA.md','VER2-BALANCE.md','VER2-PERFORMANCE.md'])await cp(path.join(root,'analysis',entry),path.join(destination,'analysis',entry));
const evidence=path.join(root,'analysis','verification','ver2'),publishedEvidence=path.join(destination,'analysis','verification','ver2');
await mkdir(publishedEvidence,{recursive:true});
for(const name of await readdir(evidence))if(/\.png$/.test(name)||['vfx-reference-performance.json','vfx-matched-performance.json','vfx-performance.json','hud-events.json','original-consumables.json'].includes(name))await cp(path.join(evidence,name),path.join(publishedEvidence,name));
const files=[];
async function walk(directory){for(const item of await readdir(directory,{withFileTypes:true})){const file=path.join(directory,item.name);if(item.isDirectory())await walk(file);else{const bytes=await readFile(file);files.push({path:path.relative(destination,file).split(path.sep).join('/'),bytes:bytes.length,sha256:createHash('sha256').update(bytes).digest('hex')});}}}
await walk(destination);await writeFile(path.join(destination,'release-manifest.json'),JSON.stringify({variant:'ver2',files},null,2));
await writeFile(path.join(root,'dist','latest.json'),JSON.stringify({directory:destination,files:files.length,bytes:files.reduce((s,f)=>s+f.bytes,0)},null,2));
console.log(destination);
