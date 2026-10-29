const fs=require('node:fs');
const p='tests/game.spec.cjs',s=fs.readFileSync(p,'utf8');
const start=s.indexOf('async function readState'),end=s.indexOf('for(const width');
fs.mkdirSync('tests/helpers',{recursive:true});
fs.writeFileSync('tests/helpers/vm.cjs',"const {expect}=require('@playwright/test');\n"+s.slice(start,end)+'\nmodule.exports={readState,press,waitScreen,start};\n');
fs.writeFileSync(p,s.slice(0,start)+"const {readState,press,waitScreen,start}=require('./helpers/vm.cjs');\n\n"+s.slice(end));
