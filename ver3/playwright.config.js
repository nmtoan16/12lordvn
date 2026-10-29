import {defineConfig} from '@playwright/test';
export default defineConfig({testDir:'./tests',testMatch:'**/*.spec.js',timeout:90000,workers:1,use:{baseURL:process.env.GAME_URL||'http://localhost/12suquan/ver3/',viewport:{width:1440,height:1000},channel:'chrome',screenshot:'only-on-failure',trace:'retain-on-failure'},reporter:[['list'],['json',{outputFile:'evidence/browser-results.json'}]]});
