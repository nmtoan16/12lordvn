/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public abstract class ds {
    protected int p;
    protected int q;
    protected int r;
    protected int s;
    protected int t;
    protected boolean u = true;

    public abstract void a(Graphics var1, int var2, int var3);

    public void a(Graphics graphics) {
        this.a(graphics, 0, 0);
    }

    public void d(int n2, int n3) {
        this.f(n2);
        this.g(n3);
    }

    public void a(int n2, int n3, int n4, int n5) {
        this.d(n2, n3);
        this.h(n4);
        this.i(n5);
    }

    public int i() {
        return this.p;
    }

    public void f(int n2) {
        this.p = n2;
    }

    public int j() {
        return this.q;
    }

    public void g(int n2) {
        this.q = n2;
    }

    public int k() {
        return this.r;
    }

    public void h(int n2) {
        this.r = n2;
    }

    public int l() {
        return this.s;
    }

    public void i(int n2) {
        this.s = n2;
    }

    public void j(int n2) {
        this.t = n2;
    }

    public void k(int n2) {
        if ((n2 & 0x20) == 32) {
            this.q -= this.s;
        } else if ((n2 & 2) == 2) {
            this.q -= this.s / 2;
        }
        if ((n2 & 8) == 8) {
            this.p -= this.r;
        } else if ((n2 & 1) == 1) {
            this.p -= this.r / 2;
        }
    }

    public boolean c(int n2, int n3, int n4, int n5) {
        boolean bl2 = this.e(n2, n4);
        return bl2 &= this.f(n3, n5);
    }

    public final int b(int n2, int n3, int n4) {
        int n5 = Math.abs(n3 - n2);
        if (n5 < n4) {
            return 1;
        }
        return n5 / n4;
    }

    public final boolean e(int n2, int n3) {
        boolean bl2;
        boolean bl3;
        boolean bl4 = bl3 = this.p > n2;
        this.p = bl3 ? (this.p -= n3) : (this.p += n3);
        boolean bl5 = bl2 = this.p > n2;
        if (bl3 != bl2) {
            this.p = n2;
        }
        return this.p == n2;
    }

    public final boolean f(int n2, int n3) {
        boolean bl2;
        boolean bl3;
        boolean bl4 = bl3 = this.q > n2;
        this.q = bl3 ? (this.q -= n3) : (this.q += n3);
        boolean bl5 = bl2 = this.q > n2;
        if (bl3 != bl2) {
            this.q = n2;
        }
        return this.q == n2;
    }

    public boolean m() {
        return this.u;
    }

    public void a(boolean bl2) {
        this.u = bl2;
    }
}

