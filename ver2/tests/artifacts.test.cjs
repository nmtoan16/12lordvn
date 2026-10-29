'use strict';
const {test}=require('node:test');
const assert=require('node:assert/strict');
const fs=require('node:fs');
const crypto=require('node:crypto');
const sha=bytes=>crypto.createHash('sha256').update(bytes).digest('hex');
const manifest=JSON.parse(fs.readFileSync('assets/manifest.json'));
test('Preserves the supplied JAR and every original resource byte',()=>{
  assert.equal(sha(fs.readFileSync('Loan-12-Su-Quan.jar')),manifest.sourceArchiveSha256);
  assert.equal(manifest.assets.length,151);
  for(const asset of manifest.assets){
    assert.equal(sha(fs.readFileSync(asset.original)),asset.kind==='image'?asset.sourceSha256:asset.sha256,asset.original);
  }
});
test('Restored MG graphics change only the four PNG signature bytes',()=>{
  const images=manifest.assets.filter(asset=>asset.kind==='image');assert.equal(images.length,131);
  for(const image of images){
    const png=fs.readFileSync(image.file),original=fs.readFileSync(image.original);
    assert.equal(sha(png),image.sha256,image.file);
    assert.equal(png.subarray(0,8).toString('hex'),'89504e470d0a1a0a');
    assert.equal(png.readUInt32BE(16),image.width);assert.equal(png.readUInt32BE(20),image.height);
    if(image.source.endsWith('.mg'))assert.deepEqual(png.subarray(4),original.subarray(4),image.file);
    else assert.deepEqual(png,original,image.file);
  }
});
test('Recovered campaign data is complete and tied to the same artifact',()=>{
  const game=JSON.parse(fs.readFileSync('analysis/game-data.json'));
  assert.equal(game.source.sha256,manifest.sourceArchiveSha256);
  assert.equal(game.characters.length,77);assert.equal(game.campaign.length,46);
  assert.equal(game.sideQuests.length,8);assert.equal(game.locations.length,41);assert.equal(game.roads.length,43);
});
