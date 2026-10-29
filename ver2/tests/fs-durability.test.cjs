'use strict';
const {test}=require('node:test');
const assert=require('node:assert/strict');
const fs=require('node:fs');
const vm=require('node:vm');
function fixture(){
  const source=fs.readFileSync(require.resolve('../runtime/bld/main-all.js'),'utf8');
  const implementation=source.slice(source.indexOf('  Store.prototype.sync = function(cb) {'),source.indexOf('  Store.prototype.import = function(file, cb) {'));
  const transactions=[],events=[];
  class Store {constructor(){this.map=new Map();this.changesToSync=new Map();this.db={transaction(){const transaction={writes:[],objectStore(){return {put(value){transaction.writes.push(['put',{...value}]);return {};},delete(key){transaction.writes.push(['delete',key]);return {};},openCursor(){return transaction.cursorRequest={};}};}};transactions.push(transaction);return transaction;}};}}
  Store.DBSTORENAME='fixture';
  vm.runInNewContext(implementation,{Store,Map,Blob,Promise,Error,DEBUG_FS:false,console:{error(){},log(){}}});
  const store=new Store();
  const put=(key,value)=>{store.map.set(key,value);store.changesToSync.set(key,{type:'put',value});};
  const complete=index=>{events.push('complete:'+index);transactions[index].oncomplete();};
  const abort=(index,error)=>{transactions[index].error=error;events.push('abort:'+index);transactions[index].onabort();};
  return {store,transactions,events,put,complete,abort};
}
test('empty filesystem sync waits for its IndexedDB transaction behind earlier writes',()=>{
  const f=fixture();f.put('/RecordStore/161b.0',{data:'journal'});
  f.store.sync(error=>f.events.push(error?'failed:0':'ack:0'));
  f.store.sync(error=>f.events.push(error?'failed:1':'ack:1'));
  assert.equal(f.transactions.length,2);assert.deepEqual(f.transactions[1].writes,[]);assert.deepEqual(f.events,[]);
  f.complete(0);assert.deepEqual(f.events,['complete:0','ack:0']);
  f.complete(1);assert.deepEqual(f.events,['complete:0','ack:0','complete:1','ack:1']);
});
test('aborted write rejects both its callback and overlapping barrier, and retains retry bytes',()=>{
  const f=fixture(),error=new Error('Quota exceeded'),results=[];f.put('/save',{data:'prepared journal'});
  f.store.sync(result=>results.push(result));f.store.sync(result=>results.push(result));
  f.abort(0,error);f.complete(1);
  assert.deepEqual(results,[error,error]);assert.equal(f.store.changesToSync.get('/save').value.data,'prepared journal');
  f.store.sync(result=>results.push(result));assert.equal(f.transactions[2].writes[0][1].data,'prepared journal');f.complete(2);
  assert.equal(results[2],undefined);assert.equal(f.store.changesToSync.size,0);
});
test('failed older transaction cannot restore a stale value over a newer pending transaction',()=>{
  const f=fixture();f.put('/save',{data:'old'});f.store.sync(()=>{});
  f.put('/save',{data:'new'});f.store.sync(()=>{});
  f.abort(0,new Error('aborted old transaction'));f.complete(1);
  assert.equal(f.store.changesToSync.has('/save'),false);assert.equal(f.store.map.get('/save').data,'new');
});
test('synchronous IndexedDB setup failures propagate once and preserve unsynced changes',()=>{
  const f=fixture(),error=new Error('Database closed'),results=[];f.put('/save',{data:'retained'});
  f.store.db.transaction=()=>{throw error;};f.store.sync(value=>results.push(value));
  assert.deepEqual(results,[error]);assert.equal(f.store.changesToSync.get('/save').value.data,'retained');
});
test('filesystem export propagates a failed durable barrier and a readonly cursor abort once',()=>{
  for(const where of ['barrier','cursor']){
    const f=fixture(),error=new Error('Export transaction failed'),results=[];
    f.store.export((blob,failure)=>results.push([blob,failure]));
    if(where==='barrier')f.abort(0,error);
    else {f.complete(0);f.abort(1,error);f.transactions[1].onerror({target:{error}});}
    assert.deepEqual(results,[[null,error]]);
  }
});
test('a request error cannot be hidden by a later transaction complete event',()=>{
  const f=fixture(),error=new Error('Failed put'),results=[];f.put('/save',{data:'retry me'});f.store.sync(value=>results.push(value));
  f.transactions[0].onerror({target:{error}});f.complete(0);
  assert.deepEqual(results,[error]);assert.equal(f.store.changesToSync.get('/save').value.data,'retry me');
});
