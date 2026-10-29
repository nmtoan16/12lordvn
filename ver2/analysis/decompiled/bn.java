/*
 * Decompiled with CFR 0.152.
 */
public class bn {
    private int b;
    private int c;
    private int d;
    private boolean e = false;
    private int f = 7500;
    private int g = 0;
    private int h = 36;
    private int i = 0;
    public dx[] a;
    private int j;
    private int k;
    private int l;
    private c m;
    private int n;

    public int a(byte[] byArray, int n2) {
        int n3 = n2;
        System.arraycopy(ai.a(this.c), 0, byArray, n2, 4);
        System.arraycopy(ai.a(this.b), 0, byArray, n2 += 4, 4);
        System.arraycopy(ai.a(this.i), 0, byArray, n2 += 4, 4);
        System.arraycopy(ai.a(this.g), 0, byArray, n2 += 4, 4);
        System.arraycopy(ai.a(this.j), 0, byArray, n2 += 4, 4);
        System.arraycopy(ai.a(this.k), 0, byArray, n2 += 4, 4);
        n2 += 4;
        int n4 = 0;
        while (n4 < this.a.length) {
            n2 += this.a[n4].a(byArray, n2);
            System.out.println("offset=" + n2);
            ++n4;
        }
        return n2 - n3;
    }

    public int b(byte[] byArray, int n2) {
        int n3 = n2;
        this.c = this.d = ai.a(byArray, n2);
        this.b = ai.a(byArray, n2 += 4);
        this.i = ai.a(byArray, n2 += 4);
        this.g = ai.a(byArray, n2 += 4);
        this.j = ai.a(byArray, n2 += 4);
        this.k = ai.a(byArray, n2 += 4);
        n2 += 4;
        int n4 = 0;
        while (n4 < this.a.length) {
            n2 += this.a[n4].b(byArray, n2);
            ++n4;
        }
        return n2 - n3;
    }

    public bn(c c2) {
        this.m = c2;
        int n2 = c2.e;
        this.n = n2 < 30 ? 70 : (n2 < 60 ? 50 : 30);
        this.a();
    }

    public void a() {
        this.b = this.m.b;
        this.c = this.d = this.m.c;
        this.i = 0;
        this.g = this.f;
        this.e = false;
        this.a = new dx[6];
        int n2 = 0;
        while (n2 < this.a.length) {
            this.a[n2] = new dx();
            this.a[n2].a = n2;
            ++n2;
        }
        this.k = 0;
        this.j = 0;
    }

    public int b() {
        return this.m.a;
    }

    public int c() {
        return this.n;
    }

    public int[] d() {
        return this.m.f;
    }

    public int e() {
        return this.m.b;
    }

    public int f() {
        return this.j;
    }

    public void a(int n2) {
        this.j += n2;
    }

    public int g() {
        return this.k;
    }

    public void b(int n2) {
        this.k += n2;
    }

    public void h() {
        this.m.c = this.m.b;
    }

    public void i() {
        if (this.c > this.m.b) {
            this.c = this.m.b;
        }
        this.m.c = this.c;
    }

    public int j() {
        return this.l;
    }

    public void c(int n2) {
        this.l = n2;
    }

    public boolean k() {
        return this.e;
    }

    public void a(boolean bl2) {
        this.e = bl2;
    }

    public int l() {
        return this.d;
    }

    public void d(int n2) {
        this.d = n2;
    }

    public int m() {
        if (this.a[0].b > 0) {
            return this.m.d + this.m.d;
        }
        return this.m.d;
    }

    public int n() {
        return this.h;
    }

    public int o() {
        return this.i;
    }

    public void e(int n2) {
        if (n2 < 0) {
            n2 = 0;
        } else if (n2 > this.h) {
            n2 = this.h;
        }
        this.i = n2;
    }

    public void f(int n2) {
        this.e(this.i + n2);
    }

    public void a(int n2, boolean bl2) {
        if (this.e && !bl2) {
            return;
        }
        this.e(this.i - n2);
    }

    public int p() {
        return this.g;
    }

    public void g(int n2) {
        if (n2 < 0) {
            n2 = 0;
        } else if (n2 > this.f) {
            n2 = this.f;
        }
        this.g = n2;
    }

    public int q() {
        return this.f;
    }

    public void h(int n2) {
        this.g = this.f = n2;
    }

    public void i(int n2) {
        this.g(this.g + n2);
    }

    public void j(int n2) {
        this.g(this.g - n2);
    }

    public int r() {
        return this.c;
    }

    public void k(int n2) {
        if (this.e) {
            return;
        }
        if (this.a[3].b > 0) {
            n2 = n2 * 1 / 4;
        }
        this.m(this.c - n2);
    }

    public void l(int n2) {
        this.m(this.c + n2);
    }

    public void m(int n2) {
        if (n2 < 0) {
            n2 = 0;
        } else if (n2 > this.b) {
            n2 = this.b;
        }
        this.c = n2;
    }

    public int s() {
        return this.b;
    }

    public void n(int n2) {
        this.b = n2;
    }
}

