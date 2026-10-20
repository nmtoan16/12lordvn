import http from 'node:http';
import {readFile,stat} from 'node:fs/promises';
import path from 'node:path';
import {fileURLToPath} from 'node:url';
const root=path.resolve(path.dirname(fileURLToPath(import.meta.url)),'..');
const port=Number(process.env.PORT||8080);
const mime={'.html':'text/html; charset=utf-8','.css':'text/css; charset=utf-8','.js':'text/javascript; charset=utf-8','.json':'application/json; charset=utf-8','.png':'image/png','.jpg':'image/jpeg','.svg':'image/svg+xml','.jar':'application/java-archive','.mid':'audio/midi','.wav':'audio/wav','.amr':'audio/amr','.md':'text/plain; charset=utf-8'};
const server=http.createServer(async(req,res)=>{
  try{
    const pathname=decodeURIComponent(new URL(req.url,'http://localhost').pathname);
    if(pathname.split('/').some(s=>s.startsWith('.')||['node_modules','tools','tests','scripts'].includes(s))){res.writeHead(403).end('Forbidden');return;}
    let filename=path.resolve(root,'.'+pathname);
    if(!filename.startsWith(root+path.sep)&&filename!==root){res.writeHead(403).end('Forbidden');return;}
    if((await stat(filename)).isDirectory())filename=path.join(filename,'index.html');
    const body=await readFile(filename);
    res.writeHead(200,{'Content-Type':mime[path.extname(filename)]||'application/octet-stream','Cache-Control':'no-cache','X-Content-Type-Options':'nosniff'});
    res.end(req.method==='HEAD'?undefined:body);
  }catch(error){res.writeHead(error instanceof URIError?400:404).end('Not found');}
});
server.listen(port,'127.0.0.1',()=>console.log(`Local: http://localhost:${port}/`));
