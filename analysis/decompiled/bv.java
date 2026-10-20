/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import com.mg.smsgame.main.b;
import java.util.Stack;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public abstract class bv
implements at,
b {
    public boolean a = false;
    private Stack m;
    public aj b;
    public aj c;
    public ae d;
    protected cv e;
    public bb f;
    protected bb[] g;
    protected boolean h;
    public by i;
    public by j;
    public by k;
    protected boolean l;
    private int n;
    private int o;
    private boolean p;
    private int q;
    private int r;
    private int s;

    public void a(cv cv2) {
        this.e = cv2;
    }

    public bv(boolean bl2) {
        ci.a().a(5);
        this.l();
        this.m();
    }

    public final void a(Graphics graphics, Image image, Graphics graphics2) {
        if (this.l && this.i != null) {
            if (this.b != null) {
                this.b.a(graphics, false);
            }
            this.i.a(graphics);
        } else if (this.d != null) {
            if (this.d.c()) {
                if (this.b != null) {
                    this.b.a(graphics, false);
                }
                if (this.d.d()) {
                    this.d.b(false);
                }
            }
            this.d.d(graphics);
        } else if (this.b != null) {
            this.b.d(graphics);
        }
        if (this.h && this.f != null) {
            this.f.a(graphics2, image);
        } else {
            graphics2.drawImage(image, cx.j + this.r, cx.k + this.s, 20);
        }
    }

    public final void a() {
        if (this.q > 0) {
            --this.q;
            this.r = ay.a() % 2;
            this.s = ay.a() % 2;
            if (this.q == 0) {
                this.s = 0;
                this.r = 0;
            }
        }
        if (this.l && this.i != null) {
            if (this.h) {
                this.h = false;
                this.f = null;
            }
            this.i.a();
            if (this.i.b()) {
                this.l = false;
                this.i = null;
                if (this.h) {
                    this.h = false;
                    this.f = null;
                }
                this.a = false;
            }
            return;
        }
        if (this.d != null) {
            this.d.n();
            if (this.d != null && !this.d.d() && this.b != null) {
                this.b.n();
            }
        } else if (this.b != null) {
            if (this.h && this.f != null) {
                this.f.b();
                if (this.f.d()) {
                    this.h = false;
                    this.f = null;
                    this.a = false;
                }
                return;
            }
            this.b.n();
        }
    }

    public final void a(int n2) {
        if (this.a) {
            return;
        }
        if (this.d == null && this.b != null && !this.b.c()) {
            int n3 = n2;
            cx.u[n3] = cx.u[n3] + 1;
            this.b.a(n2);
        }
    }

    public final boolean b() {
        if (this.d != null) {
            return this.d.f();
        }
        if (this.b != null) {
            return this.b.f();
        }
        return false;
    }

    public final boolean d() {
        if (this.d != null) {
            return this.d.q();
        }
        if (this.b != null) {
            return this.b.q();
        }
        return false;
    }

    public final void b(int n2) {
        if (this.a) {
            return;
        }
        if (this.d == null && this.b != null && !this.b.c()) {
            cx.u[n2] = 0;
            this.b.b(n2);
        }
    }

    public final void a(int n2, int n3) {
        if (this.a) {
            return;
        }
        int n4 = -1;
        if (this.b != null) {
            n4 = this.b.c(n2, n3);
        }
        if (n4 > -1) {
            cx.u[n4] = 3;
            int n5 = n4;
            cx.u[n5] = cx.u[n5] + 1;
            this.b.a(n4);
        } else {
            cx.o = n2;
            cx.p = n3;
        }
    }

    public final void c(int n2, int n3) {
        if (this.a) {
            return;
        }
        int n4 = -1;
        if (this.b != null) {
            n4 = this.b.d();
        }
        if (n4 > -1) {
            cx.u[n4] = 0;
            this.b.b(n4);
        } else {
            cx.q = n2;
            cx.r = n3;
        }
    }

    public void a(ae ae2) {
        if (ae2 == null) {
            return;
        }
        if (this.d != null) {
            ae ae3 = this.d;
            ae3.b(true);
            if (this.m == null) {
                this.m = new Stack();
            }
            this.m.push(ae3);
        }
        this.d = ae2;
        if (this.d.b() && this.j != null) {
            if (this.h) {
                this.h = false;
                this.f = null;
            }
            this.l = true;
            this.i = this.j;
            this.i.a(this.d.g, cx.g, this.d.g, this.d.a_, this.d.b_, this.d.c_);
            this.a = true;
        }
    }

    public void a(boolean bl2) {
        cx.a();
        if (this.d != null) {
            if (bl2 && this.k != null) {
                if (this.h) {
                    this.h = false;
                    this.f = null;
                }
                this.l = true;
                this.i = this.k;
                this.i.a(this.d.g, this.d.a_, this.d.g, cx.g, this.d.b_, this.d.c_);
                this.a = true;
            }
            this.d.r();
        }
        this.d = null;
        if (this.m == null) {
            return;
        }
        if (this.m.size() > 0) {
            this.d = (ae)this.m.pop();
            if (this.d != null) {
                this.d.b(true);
            }
        }
        System.gc();
    }

    public void e() {
        cx.a();
        if (this.d != null) {
            this.d.r();
        }
        this.d = null;
        if (this.m == null) {
            return;
        }
        int n2 = 0;
        while (n2 < this.m.size()) {
            ((ae)this.m.elementAt(n2)).r();
            ++n2;
        }
        this.m.removeAllElements();
        this.i = null;
        this.l = false;
        System.gc();
    }

    public void a(aj aj2, boolean bl2, boolean bl3) {
        if (aj2 == null) {
            return;
        }
        if (bl2 && this.b != null) {
            this.b.r();
        }
        this.b = aj2;
        if (this.f != null && bl3 && !this.l) {
            this.h = true;
            this.f.c();
            this.a = true;
        }
    }

    public void a(int n2, int n3, boolean bl2) {
        this.a(n2, n3, bl2, false);
    }

    public void a(int n2, int n3, boolean bl2, boolean bl3) {
        this.o = n2;
        this.n = n3;
        this.p = bl2;
        if (bl3) {
            this.c = this.b;
        }
        this.f();
        ci.a().a(this);
    }

    public void c() {
        try {
            Thread.sleep(100L);
        }
        catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        if (this.c != null) {
            this.c.e();
            this.c = null;
        }
        aj aj2 = this.d(this.o, this.n);
        this.e.a(aj2, this.p);
    }

    public void f() {
        if (this.e == null) {
            this.e = new cv();
        }
        this.e.b();
        cx.T = false;
        this.a(this.e, true, false);
    }

    public void g() {
        this.h = false;
        this.l = false;
        this.f = null;
        this.i = null;
        if (this.b != null) {
            this.b.t();
        }
    }

    public void h() {
        if (this.b != null) {
            this.b.s();
        }
    }

    public void c(int n2) {
        this.q = n2;
    }

    public void i() {
        cz.d().a(true);
        ae ae2 = cz.a("Ch\u00fa \u00fd", "M\u1ea1ng di \u0111\u1ed9ng \u0111ang c\u00f3 v\u1ea5n \u0111\u1ec1 ho\u1eb7c qu\u00e1 t\u1ea3i, vui l\u00f2ng th\u1eed l\u1ea1i sau", "\u0110\u00f3ng", 3, 1);
        ae2.a(cz.d());
        this.a(ae2);
    }

    public void a(int n2, String string, String string2) {
    }

    public void a(int n2, String string, String string2, String string3) {
        this.b(n2, string, string2, string3);
    }

    private void b(int n2, String string, String string2, String string3) {
        cx.w = false;
        cx.K = n2;
        cx.L = string;
        cx.M = string2;
        if (string3 != null && string3.length() >= 4 && "reco".toLowerCase().equals(string3.toLowerCase().substring(0, 4)) && string3.length() >= 5) {
            String string4 = string3.trim().substring(4);
            int n3 = Integer.parseInt(string4);
            bg.a(n3, n2, string2, string);
            dc dc2 = null;
            aj aj2 = cz.d().e.b;
            if (aj2 != null && aj2.k() == 3) {
                dc2 = (dc)aj2;
                dc2.v();
            } else {
                dc2 = this.k();
            }
            if (dc2 == null) {
                return;
            }
            dc2.e(n3);
            cz.d().a(dc2, false);
            cz.d().e();
        }
    }

    public bb d(int n2) {
        return this.g[n2];
    }

    public int j() {
        return this.g.length;
    }

    protected abstract aj d(int var1, int var2);

    public abstract dc k();

    public abstract void l();

    public abstract void m();
}

