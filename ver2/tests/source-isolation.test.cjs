const {test}=require('node:test'),assert=require('node:assert/strict'),fs=require('node:fs'),path=require('node:path'),crypto=require('node:crypto');
test('all 784 parent source files and original SPEC remain byte-identical to the clone baseline',()=>{
 const baseline=JSON.parse(fs.readFileSync('tasks/baseline.json'));
 const sha=bytes=>crypto.createHash('sha256').update(bytes).digest('hex');
 for(const file of baseline.files)assert.equal(sha(fs.readFileSync(path.join(baseline.sourceDirectory,file.path))),file.sha256,file.path);
 assert.equal(sha(fs.readFileSync(path.join(baseline.sourceDirectory,'SPEC.md'))),baseline.sourceSpecSha256);
});
test('ver2 config uses distinct names for all database and localStorage namespaces',()=>{
 const vm=require('node:vm'),sandbox={window:{}};vm.runInNewContext(fs.readFileSync('runtime/ver2-config.js','utf8'),sandbox);
 for(const key of ['fs','jars','methods','versionKey']){assert.match(sandbox.window.LOAN_VERSION[key],/ver2/);assert.doesNotMatch(sandbox.window.LOAN_VERSION[key],/v1/);}
});
