/*
 * Decompiled with CFR 0.152.
 */
import com.mg.smsgame.main.b;
import com.mic.smsgame.suquan.a;
import com.mic.smsgame.suquan.e;
import com.mic.smsgame.suquan.f;

public class bu
implements b,
e,
dy {
    final int a;
    final int b;
    final int c;
    final int d;
    final int e;
    final int f;
    final int g;
    final int k;
    final int l;
    final int m;
    final int n;
    final int o;
    final int p;
    final int q;
    final int r;
    private cq s;
    private i t;
    private ch[] u;
    private int v;
    private int[] w;
    private int x;
    private cc[] y;
    private int z;
    private a A;
    private di B;
    private int C;
    private int D;
    private boolean E = false;
    private int F;
    private int G;
    private int H;
    private int[] I;
    private int[] J;
    private boolean K;
    private boolean L = false;
    private int[] M;
    private int[] N;

    public void a(byte[] byArray, int n2) {
        if (byArray == null) {
            return;
        }
        this.s.a(0);
        this.f(7);
        n2 += this.s.b(byArray, n2);
        this.t.l();
        this.B = this.s.k();
        this.t.e(ai.a(byArray, n2));
        this.G = ai.a(byArray, n2 += 4);
        this.H = ai.a(byArray, n2 += 4);
        n2 += 4;
        if (this.H > 0) {
            this.t.c(this.H, 6, 6);
        }
        int n3 = 0;
        while (n3 < this.I.length) {
            this.I[n3] = ai.a(byArray, n2);
            n2 += 4;
            if (this.I[n3] > 0) {
                this.t.a(4, n3, this.y, 0);
                this.s.b(n3).a(true);
            }
            ++n3;
        }
        n3 = 0;
        while (n3 < this.J.length) {
            this.J[n3] = ai.a(byArray, n2);
            n2 += 4;
            if (this.J[n3] > 0) {
                this.t.a(8, (n3 + 1) % 2, null, 0);
            }
            ++n3;
        }
        this.K = ai.a(byArray, n2) == 1;
        this.L = ai.a(byArray, n2 += 4) == 1;
        dx[] dxArray = this.s.b((int)0).a;
        if (dxArray[3].b >= 1) {
            this.t.e(true);
        }
        if (dxArray[0].b >= 1) {
            this.t.d(true);
        }
    }

    private void u() {
        if (this.v != 8) {
            return;
        }
        int n2 = 0;
        byte[] byArray = new byte[400];
        System.arraycopy(ai.a(dd.c), 0, byArray, n2, 4);
        n2 += 4;
        n2 += dd.e.a(byArray, n2);
        n2 += dd.f.a(byArray, n2);
        n2 += this.s.a(byArray, n2);
        System.arraycopy(ai.a(this.t.o()), 0, byArray, n2, 4);
        System.arraycopy(ai.a(this.G), 0, byArray, n2 += 4, 4);
        System.arraycopy(ai.a(this.H), 0, byArray, n2 += 4, 4);
        n2 += 4;
        int n3 = 0;
        while (n3 < this.I.length) {
            System.arraycopy(ai.a(this.I[n3]), 0, byArray, n2, 4);
            n2 += 4;
            ++n3;
        }
        n3 = 0;
        while (n3 < this.J.length) {
            System.arraycopy(ai.a(this.J[n3]), 0, byArray, n2, 4);
            n2 += 4;
            ++n3;
        }
        System.arraycopy(ai.a(this.K ? 1 : 0), 0, byArray, n2, 4);
        System.arraycopy(ai.a(this.L ? 1 : 0), 0, byArray, n2 += 4, 4);
        com.mic.smsgame.suquan.f.a(byArray, 0, n2 += 4);
        bg.c(1);
    }

    public bu(cq cq2, i i2) {
        this.a = 0;
        this.b = 6;
        this.c = 10;
        this.d = 7;
        this.e = 8;
        this.f = 1;
        this.g = 2;
        this.k = 3;
        this.l = 4;
        this.m = 5;
        this.n = 11;
        this.o = 9;
        this.p = 12;
        this.q = 13;
        this.r = 14;
        int[] nArray = new int[8];
        nArray[0] = -1;
        nArray[1] = -1;
        nArray[3] = 1;
        nArray[4] = 1;
        nArray[5] = 1;
        nArray[7] = -1;
        this.M = nArray;
        int[] nArray2 = new int[8];
        nArray2[1] = 1;
        nArray2[2] = 1;
        nArray2[3] = 1;
        nArray2[5] = -1;
        nArray2[6] = -1;
        nArray2[7] = -1;
        this.N = nArray2;
        this.s = cq2;
        this.t = i2;
        this.w = new int[64];
        this.y = new cc[64];
        int n2 = 0;
        while (n2 < this.y.length) {
            this.y[n2] = new cc();
            ++n2;
        }
        this.I = new int[2];
        this.J = new int[2];
        this.b();
        if (!this.s.d) {
            this.t.a("T.D\u1eebng", 2);
            this.t.a("T.Chi\u00eau", 0);
            if (cx.n) {
                this.t.a("T\u00fai", 1);
            }
        }
        this.v();
        this.f(0);
    }

    public void a() {
        this.I = new int[2];
        this.J = new int[2];
        this.L = false;
        bn[] bnArray = this.s.d();
        int n2 = 0;
        while (n2 < bnArray.length) {
            bnArray[n2].h();
            ++n2;
        }
        this.s.b();
        this.t.n();
        this.t.e(dd.g);
        this.H = 0;
        this.G = 0;
        this.D = 0;
        dd.j = 0;
        dd.i = 0;
        this.v();
        this.f(10);
        dd.h();
    }

    private void v() {
        this.s.a(dd.h ? 0 : 1);
        this.H = 1;
        this.G = 1;
    }

    public void b() {
        bn[] bnArray = this.s.d();
        this.u = new ch[2];
        this.u[0] = this.g(bnArray[0].b(), 0);
        this.u[1] = this.g(bnArray[1].b(), 1);
        int n2 = 3;
        bnArray[0].c(bnArray[1].m() + n2);
        bnArray[1].c(bnArray[0].m() + n2);
    }

    private ch g(int n2, int n3) {
        if (n2 < 6) {
            return new ac(this, n3);
        }
        return new t(this, n3);
    }

    private void w() {
        this.z = 0;
        int n2 = 0;
        int n3 = 0;
        boolean bl2 = false;
        boolean bl3 = false;
        int n4 = 0;
        int n5 = this.A.a;
        int n6 = this.A.b;
        n2 = this.A.e[0];
        boolean bl4 = bl2 = (n2 & 0xFF) >= 3;
        if (bl2) {
            n4 += n2 & 0xFF;
        }
        boolean bl5 = bl3 = ((n3 = this.A.e[1]) & 0xFF) >= 3;
        if (bl3) {
            n4 += n3 & 0xFF;
        }
        if (bl2) {
            this.a(this.s.a(n5, n6), n5, n6 - ((n2 & 0xFF0000) >> 16), 1, n2 & 0xFF, n4);
        }
        if (bl3) {
            this.a(this.s.a(n5, n6), n5 - ((n3 & 0xFF0000) >> 16), n6, n3 & 0xFF, 1, n4);
        }
        n2 = 0;
        n3 = 0;
        bl2 = false;
        bl3 = false;
        n4 = 0;
        n5 = this.A.c;
        n6 = this.A.d;
        n2 = this.A.e[2];
        boolean bl6 = bl2 = (n2 & 0xFF) >= 3;
        if (bl2) {
            n4 += n2 & 0xFF;
        }
        boolean bl7 = bl3 = ((n3 = this.A.e[3]) & 0xFF) >= 3;
        if (bl3) {
            n4 += n3 & 0xFF;
        }
        if (bl2) {
            this.a(this.s.a(n5, n6), n5, n6 - ((n2 & 0xFF0000) >> 16), 1, n2 & 0xFF, n4);
        }
        if (bl3) {
            this.a(this.s.a(n5, n6), n5 - ((n3 & 0xFF0000) >> 16), n6, n3 & 0xFF, 1, n4);
        }
    }

    public void c() {
        if (this.C > 0) {
            --this.C;
            return;
        }
        switch (this.v) {
            case 9: {
                this.f(12);
                this.C = this.t.b(this.E);
                if (this.E) {
                    bs.a().d();
                } else {
                    bs.a().e();
                }
                return;
            }
            case 12: {
                this.s.b(0).i();
                dd.i = this.s.b(0).f();
                dd.j = this.s.b(0).g();
                if (this.E) {
                    ae ae2 = dd.a(false);
                    ae2.c(new da("\u0110\u00f3ng", -5));
                    ae2.a(this);
                    cz.d().a(ae2, true);
                    break;
                }
                ae ae3 = dd.g();
                if (dd.a >= 0 || !bg.l()) {
                    ae3.c(new da("Tr\u1edf v\u1ec1", -4));
                } else {
                    ae3.a(new da("C\u00f3", -3));
                    ae3.b(new da("Kh\u00f4ng", -4));
                }
                ae3.a(this);
                cz.d().a(ae3, true);
                break;
            }
            case 0: {
                if (this.s.d) {
                    this.f(13);
                    break;
                }
                this.f(6);
                break;
            }
            case 14: {
                if (this.t.g.m()) break;
                this.f(13);
                break;
            }
            case 11: {
                this.f(3);
                break;
            }
            case 10: {
                this.f(0);
                break;
            }
            case 6: {
                break;
            }
            case 7: {
                this.t.c(this.s.a());
                this.t.c(false);
                this.f(8);
                if (this.s.a() != 0) break;
                this.t.f();
                break;
            }
            case 1: {
                this.w();
                if (this.z > 0) {
                    this.f(3);
                    break;
                }
                this.f(2);
                break;
            }
            case 2: {
                this.f(6);
                break;
            }
            case 8: {
                if (this.t.o() == 1 && this.y()) {
                    return;
                }
                int n2 = this.s.a();
                bn bn2 = this.s.b(n2);
                if (!this.s.d && bn2.p() <= 0) {
                    this.E = n2 != 0;
                    this.f(9);
                }
                this.u[n2].b();
                if (n2 != 0 || this.B == null) break;
                ++this.F;
                if (this.F != 250) break;
                a a2 = (a)this.B.b(ay.a(this.B.b()));
                this.t.b(a2);
                break;
            }
            case 3: {
                this.x();
                this.f(4);
                break;
            }
            case 4: {
                this.f(5);
                break;
            }
            case 5: {
                this.f(3);
            }
        }
    }

    private void a(bn bn2, int n2) {
        bn2.k(n2);
    }

    private void b(bn bn2, int n2) {
        bn2.l(n2);
    }

    private void x() {
        int n2 = this.s.a();
        bn[] bnArray = this.s.d();
        int n3 = 0;
        while (n3 < this.z) {
            cc cc2 = this.y[n3];
            int n4 = cc2.d > cc2.c ? cc2.d : cc2.c;
            int n5 = n4 * n4 - 2 * n4;
            if (n5 < 0) {
                n5 = 1;
            }
            int n6 = 0;
            switch (com.mic.smsgame.suquan.e.i[cc2.e]) {
                case 0: {
                    int n7 = n2 == 1 ? 0 : 1;
                    this.a(bnArray[n7], (n5 + n6) * this.D + bnArray[n2].m());
                    if (!this.s.d || n2 != 0 || bnArray[n2].r() > 0) break;
                    bnArray[n2].m(1);
                    break;
                }
                case 2: {
                    this.b(bnArray[n2], n5 * this.D + this.s.b);
                    break;
                }
                case 1: {
                    bnArray[n2].i(n5 * this.D + this.s.c);
                    break;
                }
                case 3: {
                    int n8 = bnArray[n2].o() / 12;
                    bnArray[n2].f(n5 * this.D);
                    if (n2 != 0 || bnArray[n2].o() / 12 == n8) break;
                    this.t.m();
                    break;
                }
                case 4: {
                    bnArray[n2].a(n5 * this.D);
                    break;
                }
                case 5: {
                    bnArray[n2].b(n5 * this.D);
                }
            }
            ++n3;
        }
    }

    private void f(int n2) {
        this.t.a();
        this.t.h();
        this.t.i();
        switch (n2) {
            case 9: {
                this.C = 20;
                this.t.c(true);
                break;
            }
            case 0: {
                this.t.c(true);
                this.s.j();
                this.C = this.t.k();
                break;
            }
            case 10: {
                this.t.c(true);
                this.C = this.t.j();
                break;
            }
            case 6: {
                if (this.y()) {
                    return;
                }
                this.B = this.s.k();
                if (this.B.b() <= 0) {
                    this.f(10);
                } else {
                    this.f(7);
                }
                return;
            }
            case 13: {
                this.D = 0;
                this.t.c();
                this.t.a("B\u1ecf qua", 2);
                int[] nArray = this.t.g.b();
                this.s.c(nArray[0]);
                this.s.d(nArray[1]);
                w w2 = this.t.g;
                if (w2 == null) break;
                int n3 = w2.g();
                switch (n3) {
                    case 0: {
                        w2.d();
                        this.f(14);
                        w2.c();
                        break;
                    }
                    case 6: {
                        this.B = null;
                        this.G = 1;
                        this.s.a(0);
                        this.f(7);
                        break;
                    }
                    case 7: {
                        this.s.a(true);
                        this.G = 1;
                        this.s.a(0);
                        a a2 = new a();
                        a2.a = nArray[0];
                        a2.b = nArray[1];
                        a2.c = nArray[2];
                        a2.d = nArray[3];
                        this.B = new di(1);
                        this.B.a(a2);
                        this.f(8);
                        this.t.g();
                        break;
                    }
                    case 1: {
                        a a3 = new a();
                        a3.a = nArray[0];
                        a3.b = nArray[1];
                        a3.c = nArray[2];
                        a3.d = nArray[3];
                        this.B = new di(1);
                        this.B.a(a3);
                        this.G = 1;
                        this.s.a(0);
                        this.f(7);
                        this.F = 249;
                        break;
                    }
                    case 2: {
                        a a4 = new a();
                        a4.a = nArray[0];
                        a4.b = nArray[1];
                        a4.c = nArray[2];
                        a4.d = nArray[3];
                        this.B = new di(1);
                        this.B.a(a4);
                        this.G = 1;
                        this.s.a(1);
                        this.f(7);
                        break;
                    }
                    case 3: 
                    case 5: {
                        this.B = null;
                        this.G = 1;
                        this.s.a(0);
                        this.f(8);
                        this.t.i();
                        if (w2.g() == 3) {
                            this.t.a("T.Chi\u00eau", 0);
                            break;
                        }
                        if (!cx.n) break;
                        this.t.a("T\u00fai", 1);
                        break;
                    }
                    case 4: {
                        this.E = true;
                        this.f(12);
                    }
                }
                return;
            }
            case 7: {
                Object object;
                this.F = 0;
                int n4 = 0;
                while (n4 < 2) {
                    if (this.J[n4] > 0) {
                        int n5 = n4;
                        this.J[n5] = this.J[n5] - 1;
                        if (this.J[n4] == 0) {
                            this.C = 8;
                            this.t.a(n4);
                        }
                    }
                    if (this.I[n4] > 0) {
                        int n6 = n4;
                        this.I[n6] = this.I[n6] - 1;
                        if (this.I[n4] == 0) {
                            object = this.s.b(n4);
                            ((bn)object).a(false);
                            this.t.b(n4);
                        }
                    }
                    ++n4;
                }
                this.K = false;
                dx[] dxArray = this.s.b((int)0).a;
                object = this.s.b((int)1).a;
                int n7 = 0;
                while (n7 < dxArray.length) {
                    dxArray[n7].a();
                    object[n7].a();
                    ++n7;
                }
                if (dxArray[3].b < 1) {
                    this.t.e(false);
                }
                if (dxArray[0].b < 1) {
                    this.t.d(false);
                }
                this.s.a(false);
                if (this.G <= 0) {
                    this.G = 0;
                    n7 = (this.s.a() + 1) % this.u.length;
                    this.s.a(n7);
                    this.C = 8;
                } else {
                    if (this.G > 1 || this.H > 1) {
                        this.t.c(this.G, 6, 6);
                    }
                    this.H = this.G--;
                }
                this.D = 0;
                break;
            }
            case 8: {
                if (this.s.a() == 0) {
                    this.t.b();
                }
                this.u[this.s.a()].a();
                break;
            }
            case 1: {
                this.t.a(this.A.a, this.A.b, this.A.c, this.A.d, this.s.a(this.A.a, this.A.b), this.s.a(this.A.c, this.A.d));
                this.C = 10;
                this.s.b(this.A.a, this.A.b, this.A.c, this.A.d);
                this.s.a(false);
                break;
            }
            case 2: {
                this.t.a(this.A.a, this.A.b, this.A.c, this.A.d, this.s.a(this.A.a, this.A.b), this.s.a(this.A.c, this.A.d));
                this.s.b(this.A.a, this.A.b, this.A.c, this.A.d);
                this.C = 10;
                bn bn2 = this.s.b(this.s.a());
                this.a(bn2, 3 + bn2.m());
                this.t.b(this.s.a(), 0, 3);
                break;
            }
            case 3: {
                int n8;
                int n9;
                int n10;
                cc cc2;
                System.out.println("********************************getScore**************************************");
                if (this.z <= 0) {
                    if (this.s.d) {
                        this.t.g.c();
                        this.t.g.a();
                        this.f(10);
                        return;
                    }
                    this.f(6);
                    return;
                }
                this.C = 0;
                if (this.z > 0) {
                    ++this.D;
                }
                int[][] nArray = this.s.e();
                int n11 = this.z;
                int n12 = 0;
                while (n12 < n11) {
                    cc2 = this.y[n12];
                    n10 = cc2.a + cc2.c - 1;
                    while (n10 >= cc2.a) {
                        n9 = cc2.b + cc2.d - 1;
                        while (n9 >= cc2.b) {
                            if (nArray[n10][n9] == 6) {
                                this.b(6, n10, n9, 1, 1, 1);
                            }
                            nArray[n10][n9] = 7;
                            --n9;
                        }
                        --n10;
                    }
                    ++n12;
                }
                n12 = n11;
                while (n12 < this.z) {
                    cc2 = this.y[n12];
                    if (cc2.e == 6) {
                        n10 = 0;
                        while (n10 < this.M.length) {
                            n9 = cc2.a + this.M[n10];
                            n8 = cc2.b + this.N[n10];
                            if (nArray[n9][n8] < 7) {
                                this.b(nArray[n9][n8], n9, n8, 1, 1, 1);
                                nArray[n9][n8] = 7;
                            }
                            ++n10;
                        }
                        cc2.e = 0;
                    }
                    nArray[cc2.a][cc2.b] = 7;
                    ++n12;
                }
                n12 = this.s.a();
                int n13 = 0;
                while (n13 < this.z) {
                    cc cc3 = this.y[n13];
                    n9 = 0;
                    n9 = this.t.a(cc3, this.s.a());
                    if (n9 > this.C) {
                        this.C = n9;
                    }
                    if (this.D > 1) {
                        this.t.b(this.D, cc3.a, cc3.b, cc3.c, cc3.d);
                    }
                    if (n12 == 0) {
                        int n14;
                        int n15 = n8 = cc3.d > cc3.c ? cc3.d : cc3.c;
                        if (n8 >= 5 && (n14 = this.g(n8)) >= 0) {
                            this.t.d(n14, cc3.a + cc3.c / 2, cc3.b + cc3.d / 2);
                        }
                    }
                    ++n13;
                }
                bs.a().g();
                break;
            }
            case 4: {
                this.e();
                break;
            }
            case 5: {
                this.f();
                break;
            }
        }
        this.v = n2;
    }

    private int g(int n2) {
        int n3 = -1;
        if (this.s.d) {
            n3 = 1;
            ++this.s.a[n3].b;
        } else {
            int n4 = (n2 - 5) * (n2 - 5) * 15;
            int n5 = ay.a(100);
            if (n5 < 40 + n4) {
                n5 = ay.a(100);
                if (n5 < 10 + n4) {
                    n3 = ay.a(100) < 50 ? 1 : 0;
                } else if (n5 < 25 + n4) {
                    n3 = 3;
                } else if (n5 < 45 + n4) {
                    n3 = 5;
                } else if (n5 < 65 + n4) {
                    n3 = 4;
                }
            }
            if (n3 >= 0) {
                ++this.s.a[n3].b;
                bg.a(n3, 1, 6);
            }
        }
        return n3;
    }

    public int d() {
        int n2 = this.s.a();
        bn bn2 = this.s.b(n2);
        if (bn2.d().length < 1) {
            return 0;
        }
        int n3 = bn2.o() * 3 / bn2.n();
        return n3;
    }

    public void a(int n2) {
        int n3 = this.s.a();
        bn bn2 = this.s.b(n3);
        int n4 = (this.s.a() + 1) % 2;
        bn bn3 = this.s.b(n4);
        this.z = 0;
        int[] nArray = bn2.d();
        if (n2 > nArray.length - 1) {
            n2 = nArray.length - 1;
        }
        if (n2 < 0) {
            return;
        }
        int n5 = nArray[n2];
        bn2.a(bn2.n() * (n2 + 1) / 3, true);
        switch (n5) {
            case 0: {
                this.a(bn3, bn2.m());
                int n6 = ay.a(6) + 2;
                int n7 = ay.a(6) + 2;
                int n8 = n6 + 2;
                while (n8 >= n6) {
                    int n9 = n7 + 2;
                    while (n9 >= n7) {
                        this.a(this.s.a(n8, n9), n8, n9, 1, 1, 1);
                        --n9;
                    }
                    --n8;
                }
                break;
            }
            case 1: {
                this.a(bn3, bn2.m() * 3 / 2);
                int n10 = 0;
                int n11 = 2;
                int n12 = 2;
                int n13 = 3;
                while (n13 >= 0) {
                    int n14;
                    n10 = ay.a(4);
                    int n15 = n10 * 2 + 2;
                    int n16 = n15 + 1;
                    while (n16 >= n15) {
                        n14 = n12 + 1;
                        while (n14 >= n12) {
                            this.a(this.s.a(n16, n14), n16, n14, 1, 1, 1);
                            --n14;
                        }
                        --n16;
                    }
                    if (ay.a(2) == 0 && n11 > 0 || n11 > n13) {
                        --n11;
                        n10 = (n10 + 1 + ay.a(3)) % 4;
                        n15 = n10 * 2 + 2;
                        n16 = n15 + 1;
                        while (n16 >= n15) {
                            n14 = n12 + 1;
                            while (n14 >= n12) {
                                this.a(this.s.a(n16, n14), n16, n14, 1, 1, 1);
                                --n14;
                            }
                            --n16;
                        }
                    }
                    n12 += 2;
                    --n13;
                }
                n13 = 3 + ay.a(3);
                this.h(n13, 4);
                break;
            }
            case 2: {
                this.a(bn3, bn2.m() * 5 / 2);
                int n17 = 0;
                int n18 = 2;
                int n19 = 0;
                while (n19 < 2) {
                    n17 = ay.a(5) + 2;
                    int n20 = n17 + 3;
                    while (n20 >= n17) {
                        int n21 = n18 + 3;
                        while (n21 >= n18) {
                            this.a(this.s.a(n20, n21), n20, n21, 1, 1, 1);
                            --n21;
                        }
                        --n20;
                    }
                    n18 += 4;
                    ++n19;
                }
                int n22 = 2;
                this.h(n22, 16);
                break;
            }
            case 3: {
                this.a(bn3, bn2.m() * 8 / 10);
                int n23 = ay.a(com.mic.smsgame.suquan.e.h.length);
                while (true) {
                    int n24 = 0;
                    int[][] nArray2 = this.s.e();
                    int n25 = 2;
                    while (n25 < 10) {
                        int n26 = 2;
                        while (n26 < 10) {
                            if (nArray2[n25][n26] == n23) {
                                this.a(n23, n25, n26, 1, 1, 1);
                                ++n24;
                            }
                            ++n26;
                        }
                        ++n25;
                    }
                    if (n24 > 0) break;
                    n23 = (n23 + 1 + ay.a(com.mic.smsgame.suquan.e.h.length - 2)) % com.mic.smsgame.suquan.e.h.length;
                }
                int n27 = 4 + ay.a(5);
                this.h(n27, 1);
                break;
            }
            case 4: {
                int n28 = n3;
                this.I[n28] = this.I[n28] + 6;
                bn2.a(true);
                break;
            }
            case 5: {
                this.a(bn3, bn2.m() * 2);
                int n29 = 0;
                while (n29 < 8) {
                    int n30 = 0;
                    while (n30 < 8) {
                        int n31 = 2 + n29 + ay.a(2);
                        int n32 = 2 + n30 + ay.a(2);
                        int n33 = n31 + 2;
                        while (n33 >= n31) {
                            int n34 = n32 + 2;
                            while (n34 >= n32) {
                                this.a(this.s.a(n33, n34), n33, n34, 1, 1, 1);
                                --n34;
                            }
                            --n33;
                        }
                        n30 += 4;
                    }
                    n29 += 4;
                }
                int n35 = 3 + ay.a(3);
                this.h(n35, 9);
                break;
            }
            case 6: {
                this.a(bn3, bn2.m() * 8 / 10);
                bn3.a(8, false);
                int n36 = 0;
                int n37 = ay.a(2) + 2;
                while (n36 < n37) {
                    int n38 = ay.a(8) + 2;
                    int n39 = ay.a(8) + 2;
                    boolean bl2 = true;
                    int n40 = this.z - 1;
                    while (n40 >= 0) {
                        if (this.y[n40].a == n38 && this.y[n40].b == n39) {
                            bl2 = false;
                            break;
                        }
                        --n40;
                    }
                    if (!bl2) continue;
                    this.a(this.s.a(n38, n39), n38, n39, 1, 1, 1);
                    ++n36;
                }
                break;
            }
            case 7: {
                this.b(bn2, bn2.s() / 10 * 2);
                int n41 = 2;
                while (n41 < 10) {
                    int n42 = 2;
                    while (n42 < 10) {
                        if (this.s.a(n41, n42) == 2) {
                            this.a(2, n41, n42, 1, 1, 1);
                        }
                        ++n42;
                    }
                    ++n41;
                }
                break;
            }
            case 8: {
                this.a(bn3, bn2.m() * 3 / 2);
                bn3.a(18, false);
                int n43 = 0;
                int n44 = ay.a(3) + 3;
                while (n43 < n44) {
                    int n45 = ay.a(8) + 2;
                    int n46 = ay.a(8) + 2;
                    boolean bl3 = true;
                    int n47 = this.z - 1;
                    while (n47 >= 0) {
                        if (this.y[n47].a == n45 && this.y[n47].b == n46) {
                            bl3 = false;
                            break;
                        }
                        --n47;
                    }
                    if (!bl3) continue;
                    this.a(this.s.a(n45, n46), n45, n46, 1, 1, 1);
                    ++n43;
                }
                if (bn3.k()) break;
                this.G += 2;
                if (this.J[n4] > 0) {
                    int n48 = n4;
                    this.J[n48] = this.J[n48] + 2;
                    break;
                }
                int n49 = n4;
                this.J[n49] = this.J[n49] + 3;
            }
        }
        this.C = this.t.a(n5, n3, this.y, this.z);
        this.f(11);
        bs.a().a(n5);
    }

    private void h(int n2, int n3) {
        int n4 = this.z / n3;
        int n5 = 0;
        while (n5 < this.z) {
            int n6 = ay.a(n4) * n3;
            int n7 = 0;
            while (n7 < n3) {
                int n8 = n5 + n7;
                int n9 = n6 + n7;
                cc cc2 = this.y[n8];
                this.y[n8] = this.y[n9];
                this.y[n9] = cc2;
                ++n7;
            }
            n5 += n3;
        }
        if (n2 < n4) {
            this.z = n2 * n3;
        }
    }

    private boolean y() {
        boolean bl2;
        bn[] bnArray = this.s.d();
        boolean bl3 = bl2 = bnArray[0].r() <= 0 || bnArray[1].r() <= 0;
        if (bl2 || this.t.o() == 1) {
            if (this.s.d) {
                this.E = true;
            } else if (bl2) {
                this.E = bnArray[0].r() > 0;
            } else {
                switch (this.s.c()) {
                    case 2: {
                        this.E = false;
                        break;
                    }
                    case 3: {
                        this.E = true;
                    }
                }
            }
            this.f(9);
            return true;
        }
        return false;
    }

    public void e() {
        int n2;
        int n3;
        this.x = 0;
        int[][] nArray = this.s.e();
        int[] nArray2 = new int[12];
        int[] nArray3 = new int[12];
        int n4 = 2;
        while (n4 < 10) {
            nArray2[n4] = 9;
            ++n4;
        }
        n4 = 0;
        int n5 = 2;
        while (n5 < 10) {
            n3 = 0;
            n2 = 9;
            while (n2 >= 2) {
                if (nArray[n2][n5] < 7) {
                    if (n2 != nArray2[n5]) {
                        this.w[this.x++] = nArray2[n5] << 8 | n5;
                        nArray[nArray2[n5]][n5] = nArray[n2][n5];
                        nArray[n2][n5] = 7;
                        this.t.b(n2, n5, nArray2[n5], n5, nArray[nArray2[n5]][n5], nArray3[n5] + n4);
                        int n6 = n5;
                        nArray3[n6] = nArray3[n6] + 1;
                        n3 = 1;
                    }
                    int n7 = n5;
                    nArray2[n7] = nArray2[n7] - 1;
                }
                --n2;
            }
            if (n3 != 0) {
                ++n4;
            }
            ++n5;
        }
        n4 = 0;
        n5 = 2;
        while (n5 < 10) {
            n3 = 0;
            if (nArray2[n5] >= 2) {
                n2 = nArray2[n5];
                while (n2 >= 2) {
                    this.w[this.x++] = n2 << 8 | n5;
                    this.s.d(n2, n5);
                    this.t.b(n3, n5, n2, n5, nArray[n2][n5], nArray3[n5] + n4);
                    int n8 = n5;
                    nArray3[n8] = nArray3[n8] + 1;
                    --n3;
                    this.C = 10;
                    --n2;
                }
                ++n4;
            }
            ++n5;
        }
    }

    public void f() {
        this.z = 0;
        int[][] nArray = new int[12][12];
        int n2 = 0;
        while (n2 < this.x) {
            int n3 = (this.w[n2] & 0xFF00) >> 8;
            int n4 = this.w[n2] & 0xFF;
            int n5 = 0;
            int n6 = 0;
            boolean bl2 = false;
            boolean bl3 = false;
            int n7 = 0;
            if ((nArray[n3][n4] & 0xFF) != 255) {
                n5 = this.s.e(n3, n4);
                boolean bl4 = bl2 = (n5 & 0xFF) >= 3;
                if (bl2) {
                    n7 += n5 & 0xFF;
                }
                this.a(nArray, n3, n4 - ((n5 & 0xFF0000) >> 16), n5 & 0xFF);
            }
            if ((nArray[n3][n4] & 0xFF00) != 65280) {
                n6 = this.s.f(n3, n4);
                boolean bl5 = bl3 = (n6 & 0xFF) >= 3;
                if (bl3) {
                    n7 += n6 & 0xFF;
                }
                this.b(nArray, n3 - ((n6 & 0xFF0000) >> 16), n4, n6 & 0xFF);
            }
            if (bl2) {
                this.a(this.s.a(n3, n4), n3, n4 - ((n5 & 0xFF0000) >> 16), 1, n5 & 0xFF, n7);
            }
            if (bl3) {
                this.a(this.s.a(n3, n4), n3 - ((n6 & 0xFF0000) >> 16), n4, n6 & 0xFF, 1, n7);
            }
            nArray[n3][n4] = 65535;
            ++n2;
        }
    }

    private void a(int[][] nArray, int n2, int n3, int n4) {
        int n5 = n3 + n4;
        int n6 = n3;
        while (n6 < n5) {
            int[] nArray2 = nArray[n2];
            int n7 = n6++;
            nArray2[n7] = nArray2[n7] | 0xFF;
        }
    }

    private void b(int[][] nArray, int n2, int n3, int n4) {
        int n5 = n2 + n4;
        int n6 = n2;
        while (n6 < n5) {
            int[] nArray2 = nArray[n6];
            int n7 = n3;
            nArray2[n7] = nArray2[n7] | 0xFF00;
            ++n6;
        }
    }

    private void a(int n2, int n3, int n4, int n5, int n6, int n7) {
        this.b(com.mic.smsgame.suquan.e.i[n2], n3, n4, n5, n6, n7);
        if (n5 >= 4 || n6 >= 4) {
            ++this.G;
        }
    }

    private void b(int n2, int n3, int n4, int n5, int n6, int n7) {
        cc cc2 = this.y[this.z];
        cc2.e = n2;
        cc2.a = n3;
        cc2.c = n5;
        cc2.b = n4;
        cc2.d = n6;
        cc2.f = n7;
        ++this.z;
    }

    public void b(int n2) {
        if (this.v == 14) {
            this.t.g.f();
        } else if (this.v == 8 && this.s.a() == 0) {
            this.u[this.s.a()].a(n2);
        }
    }

    public void a(int n2, int n3) {
        if (this.v == 14) {
            this.t.g.f();
        } else if (this.v == 8 && this.s.a() == 0) {
            this.u[this.s.a()].a(n2, n3);
        }
    }

    public void g() {
        if (this.s.d) {
            ae ae2 = cz.a("Th\u00f4ng tin", "B\u1ea1n c\u00f3 mu\u1ed1n b\u1ecf qua ph\u1ea7n luy\u1ec7n t\u1eadp kh\u00f4ng?", "C\u00f3", -6, "Kh\u00f4ng", -2, 1);
            ae2.a(this);
            this.t.a(false);
            cz.d().a(ae2, true);
            return;
        }
        ae ae3 = cz.a("Th\u00f4ng tin", "B\u1ea1n mu\u1ed1n d\u1eebng game l\u1ea1i?", "C\u00f3", -1, "Kh\u00f4ng", -2, 1);
        ae3.a(this);
        this.t.a(false);
        cz.d().a(ae3, true);
    }

    public dl h() {
        return this.t.f;
    }

    public boolean a(int n2, int n3, int n4, int n5) {
        a a2;
        if (this.s.d) {
            a2 = (a)this.B.b(0);
            if (!(a2.a == n2 && a2.b == n3 && a2.c == n4 && a2.d == n5 || a2.a == n4 && a2.b == n5 && a2.c == n2 && a2.d == n3)) {
                this.t.g.e();
                this.f(14);
                return false;
            }
        }
        this.A = a2 = this.s.a(n2, n3, n4, n5);
        if (this.A == null) {
            this.A = new a();
            this.A.b = n3;
            this.A.a = n2;
            this.A.d = n5;
            this.A.c = n4;
        }
        this.f(1);
        return a2 != null;
    }

    public void i() {
        if (this.s.d) {
            if (this.t.g.g() == 6) {
                this.t.g.c();
                this.f(13);
                return;
            }
            if (this.t.g.g() != 1) {
                this.t.g.e();
                this.f(14);
                return;
            }
        }
        if (this.s.h()) {
            this.s.a(false);
            this.t.e();
        } else {
            this.s.a(true);
            this.t.g();
            this.t.h();
        }
    }

    public void c(int n2, int n3) {
        if (this.s.d && !this.s.h()) {
            this.t.g.e();
            this.f(14);
            return;
        }
        this.s.c(n2);
        this.s.d(n3);
        this.s.a(false);
        this.t.e();
    }

    public boolean j() {
        return this.s.h();
    }

    public int k() {
        return this.s.f();
    }

    public int l() {
        return this.s.g();
    }

    public boolean m() {
        int n2 = this.s.f();
        int n3 = this.s.g();
        return this.b(n2, n3, n2 - 1, n3);
    }

    public boolean n() {
        int n2 = this.s.f();
        int n3 = this.s.g();
        return this.b(n2, n3, n2 + 1, n3);
    }

    public boolean o() {
        int n2 = this.s.f();
        int n3 = this.s.g();
        return this.b(n2, n3, n2, n3 - 1);
    }

    public boolean p() {
        int n2 = this.s.f();
        int n3 = this.s.g();
        return this.b(n2, n3, n2, n3 + 1);
    }

    public boolean b(int n2, int n3, int n4, int n5) {
        if (this.s.d && !this.s.h()) {
            this.t.g.e();
            this.f(14);
            return false;
        }
        if (!this.s.c(n4, n5)) {
            return false;
        }
        if (this.s.h()) {
            return this.a(n2, n3, n4, n5);
        }
        int n6 = this.s.f();
        int n7 = this.s.g();
        boolean bl2 = this.s.b(n4, n5);
        if (bl2) {
            this.t.a(n6, n7);
        }
        return bl2;
    }

    public void a(a a2) {
        this.t.a(a2);
    }

    public di q() {
        return this.B;
    }

    public int d(int n2, int n3) {
        return this.s.a(n2, n3);
    }

    public int e(int n2, int n3) {
        int n4 = this.s.a();
        int n5 = n4 == 0 ? 1 : 0;
        bn[] bnArray = this.s.d();
        int n6 = 0;
        switch (n2) {
            case 0: 
            case 6: {
                n6 = n2 == 0 ? 9 : 10;
                if (bnArray[n5].r() > n3 * n3 - 2 * n3 + bnArray[n4].m()) break;
                return 120;
            }
            case 2: {
                n6 = 4;
                int n7 = bnArray[n4].r() * 100 / bnArray[n4].s();
                if (n7 <= 10) {
                    n6 += 4;
                    break;
                }
                if (n7 > 30) break;
                n6 += 2;
                break;
            }
            case 1: {
                n6 = 3;
                int n8 = bnArray[n4].p() * 100 / bnArray[n4].q();
                if (n8 <= 10) {
                    n6 += 4;
                    break;
                }
                if (n8 > 30) break;
                n6 += 2;
                break;
            }
            case 3: {
                n6 = 2;
                break;
            }
            default: {
                n6 = 1;
            }
        }
        return n6 += com.mic.smsgame.suquan.e.j[n3];
    }

    public void c(int n2) {
        bn bn2 = this.s.b(this.s.a());
        bn2.j(n2);
    }

    public void f(int n2, int n3) {
    }

    public void a(String string, int n2) {
        if (string.equals("skill")) {
            this.a(n2);
        } else {
            this.d(n2);
            this.K = true;
            if (this.s.d) {
                this.t.g.c();
                this.f(13);
                System.out.println("Usinggggggggggsssssssssssssssssssssssssssssssssss");
            }
        }
    }

    public void d(int n2) {
        bn bn2 = this.s.b(this.s.a());
        dx[] dxArray = bn2.a;
        int n3 = 0;
        while (n3 < dxArray.length) {
            if (n2 == dxArray[n3].a) {
                dxArray[n3].a();
                if (dxArray[n3].a == 1) {
                    bn2.n(bn2.e() * 2);
                    bn2.l(bn2.e());
                    this.L = true;
                    break;
                }
                if (dxArray[n3].a == 4) {
                    this.b(bn2, bn2.s() * 1 / 10);
                    this.t.d(this.s.a());
                    break;
                }
                if (n2 == 3) {
                    dxArray[n3].b = 2;
                    this.t.e(true);
                    break;
                }
                if (n2 != 0) break;
                dxArray[n3].b = 1;
                this.t.d(true);
                break;
            }
            ++n3;
        }
        bs.a().h();
    }

    public void r() {
        if (this.s.d && this.t.g.g() != 3) {
            return;
        }
        int n2 = this.d();
        this.t.a(n2, this, this.s.b(this.s.a()));
    }

    public void s() {
        int n2 = this.s.c();
        if (n2 == 1 && this.t.g.g() != 5) {
            return;
        }
        if (this.K) {
            return;
        }
        if (this.L) {
            this.s.a[1].b = 0;
        }
        this.t.e.a(this.s.a, this);
    }

    public void b(int n2, int n3) {
        switch (n3) {
            case -1: {
                cz.d().a(false);
                this.u();
                cz.a().a(5, 2, true, false);
                break;
            }
            case -2: {
                this.t.a(true);
                cz.d().a(true);
                break;
            }
            case -3: {
                dd.j();
                cz.d().a(false);
                this.a();
                break;
            }
            case -5: 
            case -4: {
                cz.d().a(false);
                cz.a().a(5, 7, true, true);
                break;
            }
            case -8: {
                break;
            }
            case -6: {
                cz.d().a(false);
                this.E = true;
                this.f(12);
            }
        }
    }

    public void t() {
        bn[] bnArray = this.s.d();
        int n2 = 0;
        while (n2 < bnArray.length) {
            bnArray[n2].i();
            ++n2;
        }
        com.mic.smsgame.suquan.f.f();
        if (this.E) {
            dd.i();
        } else {
            dd.j();
        }
    }

    public int a(int n2, int n3, int n4, int n5, int n6) {
        if (n2 != 0) {
            return n2;
        }
        int[][] nArray = this.s.e();
        int n7 = n4 - n5;
        while (n7 < n4) {
            if (nArray[n3][n7] == 6) {
                return 6;
            }
            ++n7;
        }
        n7 = n4 + n6;
        while (n7 > n4) {
            if (nArray[n3][n7] == 6) {
                return 6;
            }
            --n7;
        }
        return 0;
    }

    public int b(int n2, int n3, int n4, int n5, int n6) {
        if (n2 != 0) {
            return n2;
        }
        int[][] nArray = this.s.e();
        int n7 = n3 - n5;
        while (n7 < n3) {
            if (nArray[n7][n4] == 6) {
                return 6;
            }
            ++n7;
        }
        n7 = n3 + n6;
        while (n7 > n3) {
            if (nArray[n7][n4] == 6) {
                return 6;
            }
            --n7;
        }
        return 0;
    }

    public boolean e(int n2) {
        return ay.a(100) <= this.s.b(n2).c();
    }
}

