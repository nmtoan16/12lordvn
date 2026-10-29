const {defineConfig}=require('@playwright/test');
module.exports=defineConfig({
  testDir:'./tests',testMatch:'**/*.spec.cjs',timeout:180000,workers:1,
  use:{baseURL:process.env.GAME_URL||'http://localhost/12suquan/ver2/',viewport:{width:1440,height:1000},launchOptions:{channel:'chrome'},screenshot:'only-on-failure'},
  reporter:'list'
});
