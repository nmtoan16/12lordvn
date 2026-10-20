/*
 * Decompiled with CFR 0.152.
 */
package com.mic.smsgame.suquan;

import com.mic.smsgame.suquan.aa;
import com.mic.smsgame.suquan.ab;
import com.mic.smsgame.suquan.ac;
import com.mic.smsgame.suquan.l;
import com.mic.smsgame.suquan.m;
import com.mic.smsgame.suquan.q;
import com.mic.smsgame.suquan.z;

public class y
extends bv {
    public y() {
        super(true);
    }

    public dc k() {
        if (cx.T) {
            return null;
        }
        int n2 = 2;
        if (this.b != null) {
            n2 = this.b.k();
        }
        if (aa.c) {
            n2 = 7;
        }
        if (n2 < 0) {
            return null;
        }
        if (n2 == 0) {
            n2 = 2;
        }
        return new m(n2);
    }

    public void l() {
        this.g = new bb[4];
        this.g[0] = new j();
        this.g[1] = new af();
        this.g[2] = new bi();
        this.g[3] = new cy();
    }

    public void m() {
        this.k = new z();
        this.j = new ag();
    }

    protected aj d(int n2, int n3) {
        cx.T = false;
        switch (n3) {
            case 1: {
                return new v(6);
            }
            case 6: {
                return new l();
            }
            case 2: {
                return new ac();
            }
            case 3: {
                return new m(n2);
            }
            case 5: {
                ab ab2 = new ab();
                cx.T = true;
                return ab2;
            }
            case 7: {
                if (n2 == 2) {
                    aa.d = true;
                }
                return new aa();
            }
            case 4: {
                return new q(n2);
            }
            case 8: {
                return new z();
            }
        }
        return null;
    }

    public void b(int n2, int n3) {
    }

    public void h() {
        super.h();
        if (!cx.T && !cx.H) {
            cf.a().e();
        }
    }

    public void g() {
        super.g();
        if (!cx.T && !cx.H) {
            cf.a().b();
        }
    }
}

