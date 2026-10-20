const test=require('node:test');
const assert=require('node:assert/strict');
const fs=require('node:fs');
const vm=require('node:vm');

function cacheHarness(previousVersion='old'){
  const request={},operations=[],keys=[];
  const database={transaction(name,mode){
    const transaction={objectStore(){return {
      clear(){const operation={kind:'clear'};operations.push(operation);return operation;},
      getAll(){const operation={kind:'restore',result:[]};operations.push(operation);return operation;},
      put(){}
    };}};
    return transaction;
  }};
  const context={indexedDB:{open(){return request;}},Map,Promise,performance,console,
    localStorage:{getItem(key){keys.push(['get',key]);return previousVersion;},setItem(key,value){keys.push(['set',key,value]);}},
    config:{version:'new'},setTimeout,clearTimeout};
  let source=fs.readFileSync('runtime/libs/compiled-method-cache.js','utf8');
  if(process.env.CACHE_MUTATION==='1')source=source.replaceAll('openDatabase.then(function() { return new Promise','openDatabase.then(new Promise').replaceAll('}); });','}));');
  vm.runInNewContext(source,context);
  return {cache:context.CompiledMethodCache,operations,keys,open(){request.result=database;request.onsuccess();}};
}
const settle=()=>new Promise(resolve=>setImmediate(resolve));

test('clear waits for IndexedDB open before starting a transaction',async()=>{
  const app=cacheHarness();
  const clearing=app.cache.clear();
  await settle();assert.equal(app.operations.length,0);
  app.open();await settle();
  assert.equal(app.operations.length,2); // Explicit clear plus version invalidation.
  for(const operation of app.operations)operation.onsuccess();
  await clearing;
  assert.ok(app.keys.every(([,key])=>key==='loan12suquan:lastAppVersion'));
});

test('matching version restores compiled entries after the database opens',async()=>{
  const app=cacheHarness('new');assert.equal(app.operations.length,0);
  app.open();await settle();
  assert.equal(app.operations.length,1);assert.equal(app.operations[0].kind,'restore');
  app.operations[0].result=[{key:'example-method',source:'return 42'}];
  app.operations[0].onsuccess();await settle();
  assert.equal(app.cache.get('example-method').source,'return 42');
  assert.ok(app.keys.every(([,key])=>key==='loan12suquan:lastAppVersion'));
});
