import {readdir,readFile} from 'node:fs/promises';
import {spawnSync} from 'node:child_process';
for(const directory of ['web','runtime','runtime/equipment','runtime/bld'])for(const entry of await readdir(directory)){
 if(!/\.js$/.test(entry))continue;
 const file=`${directory}/${entry}`,result=spawnSync(process.execPath,['--check',file],{encoding:'utf8'});
 if(result.status){process.stderr.write(result.stderr);process.exit(result.status);}
}
const html=(await readFile('runtime/main.html','utf8')).replace(/<!--[\s\S]*?-->/g,'');
for(const [,src] of html.matchAll(/<script\b[^>]*src="([^"?]+)"/g)){if(src.startsWith('http'))throw Error('External runtime script '+src);await readFile('runtime/'+src);}
console.log('All application scripts parse; local runtime scripts exist.');
