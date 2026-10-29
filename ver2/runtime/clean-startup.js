'use strict';
(() => {
  const H=LoanJavaHooks;
  window.loanStartup={neutralFrames:0,transitions:0};
  H.replace('cn.d.()V',address=>{
    // Keep the original readiness loop and viewport initialization. Suppress
    // advertising painting and only its timer; do not change cx.F.
    H.set(address,'cn','o','Z',0);
    H.set(address,'cn','q','I',1);
    window.loanStartup.neutralFrames++;
  });
  H.replace('cn.e.()V',()=>{});
  H.replace('cn.a.()V',()=>{});
  H.observe('cn.b.()V',{before(){window.loanStartup.transitions++;}});
})();
