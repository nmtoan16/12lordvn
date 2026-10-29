/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package com.mic.smsgame.suquan;

import com.mic.smsgame.suquan.e;
import com.mic.smsgame.suquan.f;
import javax.microedition.lcdui.Graphics;

public class ab
extends aj
implements e {
    cq c;
    i d;
    bu e;

    public ab() {
        long l2 = System.currentTimeMillis();
        try {
            bs.b();
            try {
                byte[] byArray = null;
                int n2 = 0;
                if (f.e()) {
                    System.out.println("khasc nullllllllllllll");
                    byArray = f.d();
                    f.f();
                    dd.c = ai.a(byArray, n2);
                    n2 += 4;
                    dd.e = new c();
                    n2 += dd.e.b(byArray, n2);
                    dd.f = new c();
                    n2 += dd.f.b(byArray, n2);
                }
                this.c = new cq(dd.d(dd.c), dd.e, dd.f);
                this.d = new i(this.c);
                this.e = new bu(this.c, this.d);
                this.e.a(byArray, n2);
            }
            catch (Exception exception) {
                exception.printStackTrace();
                d.a(exception);
                f.f();
                cz.a().a(5, 7, false);
                return;
            }
            this.a(cz.a());
            this.a((h)null);
            dd.h();
            try {
                bs.a().c();
                bs.a().b(dd.e.f);
                bs.a().b(dd.f.f);
            }
            catch (Exception exception) {
                bs.a().j();
                exception.printStackTrace();
                d.a(exception);
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
            d.a(exception);
            cz.a().a(5, 7, false);
        }
        d.a("DONE LOADPLAY" + (System.currentTimeMillis() - l2));
        this.q = 5;
    }

    public void r() {
        bs.k();
    }

    public void a(int n2) {
        this.e.b(n2);
    }

    public void a(int n2, int n3) {
        this.e.a(n2, n3);
    }

    public void a() {
        this.e.c();
        this.d.d();
    }

    public void a(Graphics graphics) {
        this.d.a(graphics);
    }

    public void e() {
        this.e.t();
    }

    public void t() {
        this.d.a(false);
    }

    public void s() {
        this.d.a(true);
    }
}

