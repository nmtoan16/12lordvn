'use strict';
if(location.protocol==='file:'){
 const root=document.getElementById('app');root.className='file-message';root.textContent='Bật Apache trong XAMPP và mở game qua máy chủ web. ';
 const a=document.createElement('a');a.textContent='Mở Dư Quang trên localhost';a.href='http://localhost/12suquan/ver3/';a.className='primary';root.append(a);
}else import('./app.js').catch(error=>{document.getElementById('app').textContent='Không tải được game. Hãy tải lại trang. '+error.message;});
