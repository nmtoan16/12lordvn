/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.smsgame.main.b;
import javax.microedition.lcdui.Graphics;

public abstract class ar {
    protected int g = 0;
    protected int a_ = 0;
    protected int b_ = cx.f;
    protected int c_ = cx.g;
    protected boolean k = true;
    protected h l = new h();
    private de[] a = new de[3];
    private int[] b = new int[3];
    protected int m;
    protected int n;
    protected int o = 13;
    protected boolean p = true;
    protected int q;
    protected dz r = null;
    protected b s;

    public boolean f() {
        return this.k;
    }

    public void a(dz dz2) {
        this.r = dz2;
    }

    public int g() {
        return this.g;
    }

    public int h() {
        return this.a_;
    }

    public int i() {
        return this.b_;
    }

    public int j() {
        return this.c_;
    }

    public int k() {
        return this.q;
    }

    public void a(b b2) {
        this.s = b2;
    }

    public void c(boolean bl2) {
        this.p = bl2;
    }

    public h l() {
        return this.l;
    }

    public void a(h h2) {
        this.l = h2;
    }

    public void m() {
        int n2 = 0;
        while (n2 < this.a.length) {
            this.a[n2] = null;
            ++n2;
        }
    }

    private boolean b() {
        if (this.l != null) {
            int n2 = 0;
            while (n2 < this.a.length) {
                if (this.a[n2] != null) {
                    return true;
                }
                ++n2;
            }
        }
        return false;
    }

    protected final void c(Graphics graphics) {
        if (this.b()) {
            this.l.a(graphics);
        }
        int n2 = 0;
        while (n2 < this.a.length) {
            if (this.a[n2] != null) {
                this.a[n2].a(graphics);
            }
            ++n2;
        }
    }

    public final void d(Graphics graphics) {
        if (this.r != null) {
            this.r.a(graphics);
        }
        this.a(graphics);
        this.c(graphics);
    }

    public final void a(Graphics graphics, boolean bl2) {
        if (this.r != null) {
            this.r.a(graphics);
        }
        this.a(graphics);
        if (bl2) {
            this.c(graphics);
        }
    }

    public final void n() {
        if (this.r != null) {
            this.r.a();
        }
        int n2 = 0;
        while (n2 < this.a.length) {
            if (this.a[n2] != null) {
                this.a[n2].c();
                if (this.b[n2] > 0) {
                    int n3 = n2;
                    this.b[n3] = this.b[n3] - 1;
                    if (this.b[n2] == 0 && this.s != null) {
                        this.s.b(this.q, this.a[n2].a());
                    }
                }
            }
            ++n2;
        }
        this.c();
        this.d();
        this.a();
    }

    public boolean o() {
        return this.a[0] != null;
    }

    public boolean p() {
        return this.a[2] != null;
    }

    public boolean q() {
        return this.a[1] != null;
    }

    public void a(de de2) {
        this.a[0] = de2;
        this.b[0] = 0;
        if (de2 == null) {
            return;
        }
        this.a[0].a(2, cx.g - 17 + 4, 20);
    }

    public void b(de de2) {
        this.a[2] = de2;
        this.b[2] = 0;
        if (de2 == null) {
            return;
        }
        this.a[2].a(cx.f - 2, cx.g - 17 + 4, 24);
    }

    public void c(de de2) {
        this.a[1] = de2;
        this.b[1] = 0;
        if (de2 == null) {
            return;
        }
        this.a[1].a(cx.f / 2 - 2, cx.g - 17 + 4, 17);
    }

    private void c() {
        if (cx.n) {
            int n2 = cx.o;
            int n3 = cx.p;
            if (n2 > -1 && n3 > -1) {
                this.d(n2, n3);
                cx.p = -1;
                cx.o = -1;
            }
            n2 = cx.q;
            n3 = cx.q;
            if (n2 > -1 && n3 > -1) {
                this.e(n2, n3);
                cx.r = -1;
                cx.q = -1;
            }
            n2 = cx.s;
            n3 = cx.t;
            if (n2 > -1 && n3 > -1) {
                this.a_(n2, n3);
                cx.t = -1;
                cx.s = -1;
            }
        }
    }

    private void d() {
        int n2 = 0;
        while (n2 < 21) {
            if (cx.u[n2] == 1) {
                this.b(n2);
            } else if (cx.u[n2] == 2) {
                if (!this.e(n2)) {
                    this.a(n2);
                }
            } else if (cx.u[n2] >= 3) {
                if (cx.u[n2] > this.o || cx.u[n2] == 3) {
                    if (!this.e(n2)) {
                        this.a(n2);
                    }
                    if (!this.p) {
                        cx.u[n2] = 0;
                    }
                }
                if (cx.u[n2] < Integer.MAX_VALUE) {
                    int n3 = n2;
                    cx.u[n3] = cx.u[n3] + 1;
                }
                return;
            }
            cx.u[n2] = 0;
            ++n2;
        }
    }

    private boolean e(int n2) {
        if (this.s == null) {
            return false;
        }
        boolean bl2 = false;
        if (this.o() && n2 == 17) {
            if (this.b[0] == 0) {
                this.a[0].b();
                this.b[0] = 3;
                bl2 = true;
            }
        } else if (this.p() && n2 == 18) {
            if (this.b[2] == 0) {
                this.a[2].b();
                this.b[2] = 3;
                bl2 = true;
            }
        } else if (this.q() && n2 == 16 && this.b[1] == 0) {
            this.a[1].b();
            this.b[1] = 3;
            bl2 = true;
        }
        return bl2;
    }

    public final void d(int n2, int n3) {
        boolean bl2 = false;
        this.m = n3;
        this.n = n2;
        if (this.o() && this.a[0].a(n2, n3)) {
            cx.u[17] = 2;
            bl2 = true;
        }
        if (this.p() && this.a[2].a(n2, n3)) {
            cx.u[18] = 2;
            bl2 = true;
        }
        if (this.q() && this.a[1].a(n2, n3)) {
            cx.u[16] = 2;
            bl2 = true;
        }
        if (!bl2) {
            this.a(n2, n3);
        }
    }

    public final void e(int n2, int n3) {
        this.g(n2, n3);
    }

    public final void a_(int n2, int n3) {
        this.c(n3 - this.m);
        this.d(n2 - this.n);
        this.m = n3;
        this.n = n2;
    }

    public void a(int n2) {
    }

    public void b(int n2) {
    }

    public void a(int n2, int n3) {
    }

    public void g(int n2, int n3) {
    }

    public void c(int n2) {
    }

    public void d(int n2) {
    }

    public void r() {
    }

    public void s() {
    }

    public void t() {
    }

    public abstract void a();

    public abstract void a(Graphics var1);
}

