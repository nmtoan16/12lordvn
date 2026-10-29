import {readdirSync,existsSync,readFileSync} from 'node:fs';import {execFileSync} from 'node:child_process';import assert from 'node:assert/strict';import {HEROES,MISSIONS,TILES} from '../src/content.js';
let checked=0;for(const dir of ['src','scripts','tests'])for(const name of readdirSync(dir))if(name.endsWith('.js')){execFileSync(process.execPath,['--check',`${dir}/${name}`]);checked++;}
for(const file of ['index.html','src/entry.js','src/style.css','assets/afterglow.png','assets/heroes.png','assets/enemies.png','README.md','CREDITS.md',...TILES.map(t=>`assets/${t.icon}.svg`)])assert.ok(existsSync(file),file);
assert.equal(HEROES.length,6);assert.equal(HEROES.flatMap(h=>h.skills).length,18);assert.equal(MISSIONS.length,12);
assert.ok(!readFileSync('index.html','utf8').includes('https://'));assert.ok(!readFileSync('src/style.css','utf8').includes('@import'));
console.log(`${checked} JavaScript files parse; all runtime assets and 6/18/12 content inventory present; no CDN dependency.`);
