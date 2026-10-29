/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import com.mic.smsgame.suquan.a;
import com.mic.smsgame.suquan.b;
import com.mic.smsgame.suquan.e;
import com.mic.smsgame.suquan.p;
import com.mic.smsgame.suquan.s;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class i
implements e {
    cq a;
    bm b;
    bh c;
    private Image k;
    private di l;
    private Image m;
    private Image[] n;
    private di o;
    private di p;
    private g q;
    public bx d;
    private dl r;
    private di s;
    private dn t;
    private k u;
    private String[] v = new String[3];
    private int w;
    private int x;
    private db y;
    private ah z;
    public ck e;
    private cj A;
    private boolean B = true;
    public dl f;
    public w g;

    public void a(String string, int n2) {
        this.v[n2] = string;
    }

    public void a() {
        this.w = 0;
    }

    public void b() {
        this.x = 15;
        this.w = 30 + this.x;
        if (cx.n) {
            this.w = -1;
        }
    }

    public void c() {
        this.v = new String[3];
    }

    public i(cq cq2) {
        try {
            this.a = cq2;
            this.p();
            this.r = new dl();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void p() {
        this.l = new di();
        this.o = new di();
        this.p = new di();
        this.s = new di();
        this.u = new k(cx.f / 2, cx.g / 2 - 15);
        this.y = new db();
        this.y.a(30);
        this.m = bs.a().b;
        this.n = bs.a().c;
        this.b = new bm(cx.l + 36 + 10, cx.m + 54 + 10);
        this.c = new bh();
        com.mic.smsgame.suquan.i i2 = this.b.a(0, 0);
        this.c.a(i2.i(), i2.j());
        bn[] bnArray = this.a.d();
        this.d = new bx(bnArray);
        this.d.d(this.b.i() - 18, this.b.j() + 167);
        ax[] axArray = this.d.a();
        Image[] imageArray = bs.a().a(bnArray[0].b(), bnArray[1].b());
        Graphics graphics = this.m.getGraphics();
        int n2 = 0;
        while (n2 < imageArray.length) {
            graphics.drawImage(imageArray[n2], axArray[n2].i(), axArray[n2].j(), 0);
            ++n2;
        }
        imageArray = null;
        this.k = bs.a().a;
        this.q = new g();
        bs.a().a(bnArray[0].d());
        bs.a().a(bnArray[1].d());
        bs.a().c(bnArray[0].d());
        this.z = new ah(this.a.b(0).d());
        this.e = new ck();
        this.A = new cj(cx.f >> 1, this.b.j() + 163);
        this.A.b(dd.g);
        this.t = new dn(bs.a().E);
        this.f = new dl(this.b.i() - 10, this.b.j() - 10, this.b.k(), this.b.l());
        System.out.println("+++++++idMath+++++++++=" + this.a.c());
        if (this.a.d) {
            this.g = new w();
        }
    }

    public void d() {
        if (!this.B) {
            return;
        }
        this.b.a();
        this.c.a();
        int n2 = 0;
        while (n2 < this.l.b()) {
            ((b)this.l.b(n2)).b();
            ++n2;
        }
        n2 = 0;
        while (n2 < this.o.b()) {
            ((p)this.o.b(n2)).a();
            ++n2;
        }
        n2 = 0;
        while (n2 < this.p.b()) {
            ((ab)this.p.b(n2)).e();
            ++n2;
        }
        n2 = this.s.b() - 1;
        while (n2 >= 0) {
            ((s)this.s.b(n2)).a();
            --n2;
        }
        this.q.b();
        this.d.b();
        this.u.e();
        if (this.w > 0) {
            --this.w;
            if (this.w < 15) {
                --this.x;
            }
        }
        this.y.a();
        this.A.b();
        this.t.a();
        if (this.g != null) {
            this.g.h();
        }
    }

    public void a(Graphics graphics) {
        graphics.drawImage(this.m, 0, 0, 0);
        if (this.B) {
            y.a(graphics, this.r);
            y.a(graphics, this.r, cx.l + 30, cx.m + 51, 180, cx.g);
            this.b.a(graphics);
            y.b(graphics, this.r);
            this.c.a(graphics);
            this.q.a(graphics);
        }
        this.d.a(graphics);
        if (!this.B) {
            return;
        }
        this.y.a(graphics, 0, 0);
        int n2 = 0;
        while (n2 < this.l.b()) {
            ((b)this.l.b(n2)).a(graphics, 0, 0);
            ++n2;
        }
        n2 = this.s.b() - 1;
        while (n2 >= 0) {
            ((s)this.s.b(n2)).a(graphics);
            --n2;
        }
        n2 = 0;
        while (n2 < this.o.b()) {
            ((p)this.o.b(n2)).a(graphics, 0, 0);
            ++n2;
        }
        n2 = 0;
        while (n2 < this.p.b()) {
            ((ab)this.p.b(n2)).a(graphics);
            ++n2;
        }
        this.u.a(graphics);
        this.A.a(graphics);
        this.t.a(graphics);
        if (this.g != null) {
            this.g.a(graphics);
        }
        if (this.w != 0) {
            if (this.v[0] != null) {
                bz.d.a(graphics, this.v[0], 0, cx.g - this.x, 0);
            }
            if (this.v[1] != null) {
                bz.d.a(graphics, this.v[1], cx.f / 2, cx.g - this.x, 1);
            }
            if (this.v[2] != null) {
                bz.d.a(graphics, this.v[2], cx.f, cx.g - this.x, 2);
            }
        }
    }

    public void e() {
        this.c.b(0);
        com.mic.smsgame.suquan.i i2 = this.b.a(this.a.f() - 2, this.a.g() - 2);
        this.c.a(i2.i(), i2.j());
    }

    public void f() {
        this.c.b(0);
        com.mic.smsgame.suquan.i i2 = this.b.a(this.a.f() - 2, this.a.g() - 2);
        this.c.b(i2.i(), i2.j());
    }

    public void g() {
        this.c.b(0);
        int n2 = this.a.f();
        int n3 = this.a.g();
        com.mic.smsgame.suquan.i i2 = this.b.a(n2 - 2, n3 - 2);
        int n4 = 0;
        if (n3 > 2) {
            n4 |= 1;
        }
        if (n3 < 9) {
            n4 |= 2;
        }
        if (n2 > 2) {
            n4 |= 4;
        }
        if (n2 < 9) {
            n4 |= 8;
        }
        this.c.a(n4, i2.i(), i2.j());
    }

    private int a(int n2, int n3, int n4, int n5) {
        int n6 = 0;
        if (n3 < n5) {
            n6 = 2;
        } else if (n3 > n5) {
            n6 = 1;
        } else if (n2 < n4) {
            n6 = 8;
        } else if (n2 > n4) {
            n6 = 4;
        }
        return n6;
    }

    public void a(int n2, int n3) {
        this.c.b(0);
        int n4 = this.a.f();
        int n5 = this.a.g();
        com.mic.smsgame.suquan.i i2 = this.b.a(n4 - 2, n5 - 2);
        com.mic.smsgame.suquan.i i3 = this.b.a(n2 - 2, n3 - 2);
        this.c.b(i3.i(), i3.j(), i2.i(), i2.j());
    }

    public void a(a a2) {
        int n2;
        int n3 = a2.a;
        int n4 = a2.b;
        if ((a2.e[0] & 0xFF) >= 3 || (a2.e[1] & 0xFF) >= 3) {
            n2 = this.a(a2.c, a2.d, a2.a, a2.b);
            n3 = a2.c;
            n4 = a2.d;
        } else {
            n2 = this.a(a2.a, a2.b, a2.c, a2.d);
        }
        this.c.b(1);
        com.mic.smsgame.suquan.i i2 = this.b.a(n3 - 2, n4 - 2);
        this.c.a(n2, i2.i(), i2.j());
    }

    public void b(a a2) {
        int n2;
        int n3 = a2.a;
        int n4 = a2.b;
        if ((a2.e[0] & 0xFF) >= 3 || (a2.e[1] & 0xFF) >= 3) {
            n2 = this.a(a2.c, a2.d, a2.a, a2.b);
            n3 = a2.c;
            n4 = a2.d;
        } else {
            n2 = this.a(a2.a, a2.b, a2.c, a2.d);
        }
        int n5 = (n4 - 2) * 21 + this.b.i();
        int n6 = (n3 - 2) * 21 + this.b.j();
        this.q.a(n5, n6, n2);
    }

    public void h() {
        this.q.a();
    }

    public void i() {
        this.c.b();
    }

    public int j() {
        int n2 = 0;
        int n3 = 0;
        while (n3 < 8) {
            int n4 = 0 + n2;
            int n5 = 0;
            while (n5 < 8) {
                com.mic.smsgame.suquan.i i2 = this.b.a(n3, n5);
                i2.b(21, n4);
                ++n4;
                ++n5;
            }
            ++n2;
            ++n3;
        }
        return 22;
    }

    public int k() {
        int n2 = 0;
        int[][] nArray = this.a.e();
        int[] nArray2 = new int[12];
        int n3 = 2;
        while (n3 < 10) {
            int n4 = 0;
            int n5 = 9;
            while (n5 >= 2) {
                this.b(n4, n3, n5, n3, nArray[n5][n3], nArray2[n3] + n2);
                int n6 = n3;
                nArray2[n6] = nArray2[n6] + 1;
                --n4;
                --n5;
            }
            ++n2;
            ++n3;
        }
        return 21;
    }

    public void l() {
        int[][] nArray = this.a.e();
        int n2 = 2;
        while (n2 < 10) {
            int n3 = 9;
            while (n3 >= 2) {
                this.a(n3, n2, nArray[n3][n2]);
                --n3;
            }
            ++n2;
        }
    }

    public void a(boolean bl2) {
        this.B = bl2;
    }

    public void a(int n2, int n3, int n4) {
        this.b.a(n2, n3, n4);
    }

    public void a(int n2, int n3, int n4, int n5, int n6, int n7) {
        this.b.a(n2, n3, n4, n5, n6, n7);
    }

    public void b(int n2, int n3, int n4, int n5, int n6, int n7) {
        this.b.b(n2, n3, n4, n5, n6, n7);
    }

    public int a(cc cc2, int n2) {
        if (com.mic.smsgame.suquan.e.i[cc2.e] == 0) {
            n2 = (n2 + 1) % 2;
            this.b(cc2, n2);
        }
        ax ax2 = this.d.a()[n2];
        int n3 = ax2.i() + ax2.k() / 2;
        int n4 = 35;
        if (cc2.e != 4 && cc2.e != 5) {
            n3 = n2 == 0 ? (n3 += n4) : (n3 -= n4 + 20);
        }
        int n5 = ax2.j();
        switch (cc2.e) {
            case 4: 
            case 5: {
                n5 += ax2.l() / 2;
                break;
            }
            case 2: {
                this.d.a(12);
                n5 += 0;
                break;
            }
            case 1: {
                n5 += 8;
                break;
            }
            case 3: {
                n5 += 16;
            }
        }
        int n6 = this.b.i();
        int n7 = this.b.j();
        int n8 = cc2.a - 2;
        int n9 = cc2.b - 2;
        int n10 = 0;
        int n11 = n7 + n8 * 21;
        int n12 = n8 + cc2.c;
        int n13 = n9 + cc2.d;
        int n14 = n8;
        while (n14 < n12) {
            n10 = n6 + n9 * 21;
            int n15 = n9;
            while (n15 < n13) {
                this.a(n10, n11, n3, n5, cc2.e);
                n10 += 21;
                ++n15;
            }
            n11 += 21;
            ++n14;
        }
        return this.b.a(cc2, n3, n5);
    }

    public int b(boolean bl2) {
        Image image = bl2 ? az.b("/strwin") : az.b("/strlose");
        return this.u.a(image);
    }

    public void a(int n2, int n3, int n4, int n5, int n6) {
        if (n6 == 0 || n6 > 5) {
            return;
        }
        s s2 = null;
        Image image = this.n[n6];
        if (image == null) {
            return;
        }
        int n7 = this.s.b() - 1;
        while (n7 > -1) {
            s s3 = (s)this.s.b(n7);
            if (!s3.m()) {
                s2 = s3;
                break;
            }
            --n7;
        }
        if (s2 == null) {
            s2 = new s();
            this.s.a(s2);
        }
        s2.a(n2, n3, n4, n5, image);
    }

    public void b(int n2, int n3, int n4, int n5, int n6) {
        b b2 = null;
        int n7 = this.l.b() - 1;
        while (n7 > -1) {
            b b3 = (b)this.l.b(n7);
            if (!b3.a()) {
                b2 = b3;
                break;
            }
            --n7;
        }
        if (b2 == null) {
            b2 = new b(this.k);
            this.l.a(b2);
        }
        n7 = (n4 + n6 / 2 - 2) * 21 - (n6 % 2 == 0 ? 10 : 4) + this.b.i();
        int n8 = (n3 + n5 / 2 - 2) * 21 - (n5 % 2 == 0 ? 10 : 5) + this.b.j();
        int n9 = n2 - 1;
        b2.a(n7, n8, n9, "x" + n2);
    }

    public void b(cc cc2, int n2) {
        int n3;
        Image image = this.n[0];
        ax[] axArray = this.d.a();
        int n4 = axArray[n2].i() + 3 + ay.a(axArray[n2].k() - 6);
        int n5 = axArray[n2].j() + 3 + ay.a(axArray[n2].l() - 6);
        p p2 = null;
        int n6 = cc2.a + cc2.c;
        int n7 = cc2.b + cc2.d;
        int n8 = cc2.a;
        while (n8 < n6) {
            n3 = cc2.b;
            while (n3 < n7) {
                p2 = null;
                com.mic.smsgame.suquan.i i2 = this.b.a(n8 - 2, n3 - 2);
                i2.a_(0);
                int n9 = this.o.b() - 1;
                while (n9 > -1) {
                    p p3 = (p)this.o.b(n9);
                    if (!p3.m()) {
                        p2 = p3;
                        break;
                    }
                    --n9;
                }
                if (p2 == null) {
                    p2 = new p(image);
                    this.o.a(p2);
                }
                p2.b(i2.i(), i2.j(), n4, n5);
                ++n3;
            }
            ++n8;
        }
        this.d.b(n2, 36);
        this.d.a(26);
        ax ax2 = axArray[n2];
        n3 = ax2.i() + ax2.k() / 2;
        int n10 = 0;
        while (n10 < this.p.b()) {
            ab ab2 = (ab)this.p.b(n10);
            if (ab2.b() == 4 && ab2.i() == n3) {
                ((do)ab2).e(26);
            }
            ++n10;
        }
    }

    public void a(int n2) {
        ax ax2 = this.d.a()[n2];
        int n3 = ax2.i() + ax2.k() / 2;
        int n4 = 0;
        while (n4 < this.p.b()) {
            ab ab2 = (ab)this.p.b(n4);
            if (ab2.m() && ab2.b() == 8 && ab2.i() == n3) {
                ab2.a_(1);
            }
            ++n4;
        }
    }

    public void b(int n2) {
        ax ax2 = this.d.a()[n2];
        int n3 = ax2.i() + ax2.k() / 2;
        int n4 = 0;
        while (n4 < this.p.b()) {
            ab ab2 = (ab)this.p.b(n4);
            if (ab2.b() == 4 && ab2.i() == n3) {
                ((do)ab2).d(15);
            }
            ++n4;
        }
    }

    public void b(int n2, int n3, int n4) {
        this.d.b(n2, n3);
        this.d.a(n4);
    }

    public int a(int n2, int n3, cc[] ccArray, int n4) {
        int n5 = 0;
        int n6 = 0;
        int n7 = (n3 + 1) % 2;
        switch (n2) {
            case 0: {
                n6 = 0;
                n5 = 0;
                int n8 = -300;
                int n9 = 300;
                if (n3 == 0) {
                    n8 = -n8;
                }
                ax ax2 = this.d.a()[n7];
                int n10 = ax2.i() + ax2.k() / 2;
                int n11 = ax2.j() + ax2.l() / 2;
                n5 = this.a(n5, n2, n10 - n8, n11 - n9, n10, n11, n6);
                this.b(n7, n6 + 28, n6 + 18);
                n6 = ay.a(5) + 7;
                com.mic.smsgame.suquan.i i2 = this.b.a(ccArray[4].a - 2, ccArray[4].b - 2);
                n10 = i2.i();
                n11 = i2.j();
                n5 = this.a(n5, n2, n10 - n8, n11 - n9, n10, n11, n6);
                n6 += 18;
                break;
            }
            case 2: {
                n6 = 0;
                n5 = 0;
                ax ax3 = this.d.a()[n7];
                int n12 = ax3.i() + ax3.k() / 2;
                int n13 = ax3.j() + ax3.l() / 2;
                n5 = this.a(n5, n2, n12, n13, n12, n13, n6);
                this.b(n7, n6 + 10, n6 + 4);
                int n14 = n4 / 16;
                int n15 = 5;
                int n16 = 0;
                while (n16 < n14) {
                    com.mic.smsgame.suquan.i i3 = this.b.a(ccArray[n15].a - 2, ccArray[n15].b - 2);
                    n12 = i3.i();
                    n13 = i3.j();
                    int n17 = ay.a(15) + 4;
                    n5 = this.a(n5, n2, n12, n13, n12, n13, n17);
                    if (n17 > n6) {
                        n6 = n17;
                    }
                    n15 += 16;
                    ++n16;
                }
                n6 += 6;
                break;
            }
            case 1: {
                n6 = 0;
                n5 = 0;
                int n18 = -300;
                int n19 = 300;
                int n20 = 0;
                int n21 = 0;
                if (n3 == 0) {
                    n18 = -n18;
                }
                ax ax4 = this.d.a()[n7];
                n20 = ax4.i() + ax4.k() / 2;
                n21 = ax4.j() + ax4.l() / 2;
                n5 = this.a(n5, n2, n20 - n18, n21 - n19, n20, n21, n6);
                this.b(n7, n6 + 28, n6 + 18);
                int n22 = 0;
                int n23 = n4 / 4;
                int n24 = 0;
                while (n24 < n23) {
                    com.mic.smsgame.suquan.i i4 = this.b.a(ccArray[n22].a - 2, ccArray[n22].b - 2);
                    n20 = i4.i() - 10;
                    n21 = i4.j() - 10;
                    int n25 = ay.a(15) + 7;
                    n5 = this.a(n5, n2, n20 - n18, n21 - n19, n20, n21, n25);
                    if (n25 > n6) {
                        n6 = n25;
                    }
                    n22 += 4;
                    ++n24;
                }
                n6 += 16;
                break;
            }
            case 5: {
                n6 = 0;
                n5 = 0;
                int n26 = 0;
                int n27 = 0;
                ax ax5 = this.d.a()[n7];
                n26 = ax5.i() + ax5.k() / 2;
                n27 = ax5.j() + ax5.l() / 2;
                n5 = this.a(n5, n2, n26, n27, n26, n27, n6);
                this.b(n7, n6 + 12, n6 + 10);
                int n28 = 4;
                int n29 = n4 / 9;
                int n30 = 0;
                while (n30 < n29) {
                    com.mic.smsgame.suquan.i i5 = this.b.a(ccArray[n28].a - 2, ccArray[n28].b - 2);
                    n26 = i5.i();
                    n27 = i5.j();
                    int n31 = ay.a(20) + 6;
                    n5 = this.a(n5, n2, n26, n27, n26, n27, n31);
                    if (n31 > n6) {
                        n6 = n31;
                    }
                    n28 += 9;
                    ++n30;
                }
                n6 += 5;
                break;
            }
            case 6: {
                n6 = 0;
                n5 = 0;
                int n32 = -55;
                int n33 = 300;
                int n34 = 0;
                int n35 = 0;
                if (n3 == 0) {
                    n32 = -n32;
                }
                ax ax6 = this.d.a()[n7];
                n34 = ax6.i() + ax6.k() / 2;
                n35 = ax6.j() + ax6.l() / 2;
                n5 = this.a(n5, 6, n34 - n32, n35 - n33, n34, n35, n6);
                this.b(n7, n6 + 28, n6 + 18);
                n6 = ay.a(5) + 7;
                n35 = ax6.j() + 28;
                bn bn2 = this.a.b(n7);
                n34 = n7 == 0 ? ax6.i() - 1 + ax6.k() + 44 * bn2.o() / bn2.n() : ax6.i() - 7 - 44 * bn2.o() / bn2.n();
                n5 = this.a(n5, 6, n34 - n32, n35 - n33, n34, n35, n6);
                this.d.a(n7, (n6 += 18) - 4);
                int n36 = 0;
                while (n36 < n4) {
                    com.mic.smsgame.suquan.i i6 = this.b.a(ccArray[n36].a - 2, ccArray[n36].b - 2);
                    n34 = i6.i() - 10;
                    n35 = i6.j() - 10;
                    int n37 = ay.a(15) + 7;
                    n5 = this.a(n5, 6, n34 - n32, n35 - n33, n34, n35, n37);
                    if (n37 > n6) {
                        n6 = n37;
                    }
                    ++n36;
                }
                n6 += 3;
                break;
            }
            case 8: {
                n6 = 0;
                n5 = 0;
                int n38 = -55;
                int n39 = 300;
                int n40 = 0;
                int n41 = 0;
                if (n3 == 0) {
                    n38 = -n38;
                }
                ax ax7 = this.d.a()[n7];
                n40 = ax7.i() + ax7.k() / 2;
                n41 = ax7.j() + ax7.l() / 2;
                if (ccArray != null) {
                    n5 = this.a(n5, 6, n40 - n38, n41 - n39, n40, n41, n6);
                    this.b(n7, n6 + 23, n6 + 13);
                }
                n5 = this.a(n5, 8, n40, n41, n40, n41, n6 + 28);
                bn bn3 = this.a.b(n7);
                if (bn3.k()) {
                    ((bf)this.p.b(n5)).c();
                }
                if (ccArray != null) {
                    n6 = ay.a(5) + 7;
                    n41 = ax7.j() + 27;
                    n40 = n7 == 0 ? ax7.i() - 3 + ax7.k() + 44 * bn3.o() / bn3.n() : ax7.i() - 5 - 44 * bn3.o() / bn3.n();
                    n5 = this.a(n5, 6, n40 - n38, n41 - n39, n40, n41, n6);
                    this.d.a(n7, (n6 += 18) - 4);
                }
                int n42 = 0;
                while (n42 < n4) {
                    com.mic.smsgame.suquan.i i7 = this.b.a(ccArray[n42].a - 2, ccArray[n42].b - 2);
                    n40 = i7.i() - 10;
                    n41 = i7.j() - 10;
                    int n43 = ay.a(15) + 7;
                    n5 = this.a(n5, 6, n40 - n38, n41 - n39, n40, n41, n43);
                    if (n43 > n6) {
                        n6 = n43;
                    }
                    ++n42;
                }
                n6 = 36;
                break;
            }
            case 7: {
                n5 = 0;
                n6 = 14;
                this.d.a(n6 - 2);
                n6 = 0;
                ax ax8 = this.d.a()[n3];
                int n44 = ax8.i() + ax8.k() / 2;
                int n45 = ax8.j() + ax8.l() / 2;
                this.a(n5, n2, n44, n45 - 15, n44, n45, 0);
                break;
            }
            case 4: {
                n6 = 0;
                n5 = 0;
                ax ax9 = this.d.a()[n3];
                int n46 = ax9.i() + ax9.k() / 2;
                int n47 = ax9.j() + ax9.l() / 2;
                this.a(n5, n2, n46, n47, n46, n47, 0);
                break;
            }
            case 3: {
                n6 = 0;
                n5 = 0;
                int[] nArray = new int[n4 + 1];
                int[] nArray2 = new int[n4 + 1];
                int n48 = nArray2.length - 1;
                int n49 = 0;
                while (n49 < n48) {
                    com.mic.smsgame.suquan.i i8 = this.b.a(ccArray[n49].a - 2, ccArray[n49].b - 2);
                    nArray[n49] = i8.i();
                    nArray2[n49] = i8.j();
                    ++n49;
                }
                n6 = nArray2.length * 5 + 10;
                this.b(n7, n6, n6 - 6);
                ax ax10 = this.d.a()[n7];
                int n50 = ax10.i() + ax10.k() / 2;
                int n51 = ax10.j() + ax10.l() / 2;
                nArray[nArray.length - 1] = n50;
                nArray2[nArray.length - 1] = n51;
                n5 = this.a(n5, n2, n50, n51, n50, n51, n6);
                cu cu2 = (cu)this.p.b(n5);
                cu2.a(nArray, nArray2);
            }
        }
        return n6;
    }

    private int b(int n2, int n3) {
        if (n2 < 0) {
            return -1;
        }
        int n4 = n2;
        while (n4 < this.p.b()) {
            ab ab2 = (ab)this.p.b(n4);
            if (!ab2.m() && ab2.b() == n3) {
                return n4;
            }
            ++n4;
        }
        return -1;
    }

    private int a(int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        n2 = this.b(n2, n3);
        ab ab2 = null;
        if (n2 > 0) {
            ab2 = (ab)this.p.b(n2);
        } else {
            switch (n3) {
                case 0: 
                case 1: {
                    ab2 = new bt(bs.a().r, 9);
                    byte[][] byArrayArray = new byte[2][];
                    byte[] byArray = new byte[4];
                    byArray[1] = 1;
                    byArray[2] = 2;
                    byArray[3] = 3;
                    byArrayArray[0] = byArray;
                    byArrayArray[1] = new byte[]{4, 4, 5, 5, 6, 6, 7, 7, 8, 8};
                    ab2.a(byArrayArray);
                    break;
                }
                case 2: {
                    ab2 = new bt(bs.a().s, 4);
                    byte[][] byArrayArray = new byte[2][];
                    byArrayArray[0] = new byte[1];
                    byte[] byArray = new byte[9];
                    byArray[2] = 1;
                    byArray[3] = 1;
                    byArray[4] = 2;
                    byArray[5] = 2;
                    byArray[6] = 3;
                    byArray[7] = 3;
                    byArray[8] = 3;
                    byArrayArray[1] = byArray;
                    ab2.a(byArrayArray);
                    break;
                }
                case 5: {
                    ab2 = new au(bs.a().t, 5);
                    byte[][] byArrayArray = new byte[1][];
                    byte[] byArray = new byte[13];
                    byArray[2] = 1;
                    byArray[3] = 1;
                    byArray[4] = 2;
                    byArray[5] = 2;
                    byArray[6] = 2;
                    byArray[7] = 3;
                    byArray[8] = 3;
                    byArray[9] = 3;
                    byArray[10] = 4;
                    byArray[11] = 4;
                    byArray[12] = 4;
                    byArrayArray[0] = byArray;
                    ab2.a(byArrayArray);
                    break;
                }
                case 6: {
                    ab2 = new ce(bs.a().u, 5);
                    ab2.a(new byte[][]{new byte[1], {1, 1, 2, 2, 3, 3, 4, 4}});
                    break;
                }
                case 8: {
                    ab2 = new bf(bs.a().v, 9);
                    byte[][] byArrayArray = new byte[2][];
                    byte[] byArray = new byte[10];
                    byArray[2] = 1;
                    byArray[3] = 1;
                    byArray[4] = 2;
                    byArray[5] = 2;
                    byArray[6] = 3;
                    byArray[7] = 3;
                    byArray[8] = 4;
                    byArray[9] = 4;
                    byArrayArray[0] = byArray;
                    byArrayArray[1] = new byte[]{5, 5, 6, 6, 7, 7, 8, 8};
                    ab2.a(byArrayArray);
                    break;
                }
                case 7: {
                    ab2 = new am(bs.a().w, 3);
                    byte[][] byArrayArray = new byte[1][];
                    byte[] byArray = new byte[14];
                    byArray[3] = 1;
                    byArray[4] = 1;
                    byArray[5] = 1;
                    byArray[6] = 2;
                    byArray[7] = 2;
                    byArray[10] = 1;
                    byArray[11] = 1;
                    byArray[12] = 2;
                    byArray[13] = 2;
                    byArrayArray[0] = byArray;
                    ab2.a(byArrayArray);
                    break;
                }
                case 4: {
                    ab2 = new do(bs.a().x, 7);
                    byte[][] byArrayArray = new byte[2][];
                    byte[] byArray = new byte[11];
                    byArray[2] = 1;
                    byArray[3] = 1;
                    byArray[4] = 2;
                    byArray[5] = 2;
                    byArray[6] = 3;
                    byArray[7] = 3;
                    byArray[8] = 4;
                    byArray[9] = 4;
                    byArray[10] = 4;
                    byArrayArray[0] = byArray;
                    byArrayArray[1] = new byte[]{5, 5, 6, 6, 5, 5, 6, 6};
                    ab2.a(byArrayArray);
                    break;
                }
                case 3: {
                    ab2 = new cu(bs.a().y, 3, bs.a().h);
                    byte[][] byArrayArray = new byte[1][];
                    byte[] byArray = new byte[4];
                    byArray[1] = 1;
                    byArray[2] = 2;
                    byArray[3] = -1;
                    byArrayArray[0] = byArray;
                    ab2.a(byArrayArray);
                }
            }
            ab2.a(n3);
            this.p.a(ab2);
            n2 = this.p.b() - 1;
        }
        ab2.a(n4, n5, n6, n7, n8);
        return n2;
    }

    public void c(boolean bl2) {
        this.d.d(bl2);
    }

    public void c(int n2) {
        this.d.b(n2);
    }

    public void a(int n2, dy dy2, bn bn2) {
        this.z.a(n2, dy2, bn2);
    }

    public void m() {
        this.d.c();
    }

    public void n() {
        int n2 = this.p.b() - 1;
        while (n2 > -1) {
            ab ab2 = (ab)this.p.b(n2);
            ab2.a(false);
            --n2;
        }
        this.A.b(dd.g);
        this.d.b(false);
        this.d.c(false);
    }

    public void c(int n2, int n3, int n4) {
        int n5 = (n4 - 2) * 21 + this.b.i();
        int n6 = (n3 - 2) * 21 + this.b.j();
        this.y.a("C\u00f2n " + n2 + " l\u01b0\u1ee3t", n5, n6);
    }

    public void d(boolean bl2) {
        this.d.b(bl2);
    }

    public void e(boolean bl2) {
        this.d.c(bl2);
    }

    public void d(int n2) {
        ax ax2 = this.d.a()[n2];
        int n3 = ax2.i() + ax2.k() / 2;
        int n4 = 35;
        n3 = n2 == 0 ? (n3 += n4) : (n3 -= n4 + 20);
        int n5 = ax2.j();
        int[] nArray = new int[]{-20, -10, 10, 20, -10, 10};
        int[] nArray2 = new int[6];
        nArray2[1] = -20;
        nArray2[2] = -20;
        nArray2[4] = 20;
        nArray2[5] = 20;
        int[] nArray3 = nArray2;
        int n6 = 0;
        while (n6 < nArray3.length) {
            this.a(n3 + nArray[n6], n5 + nArray3[n6], n3, n5, 2);
            ++n6;
        }
    }

    public int o() {
        return this.A.a();
    }

    public void e(int n2) {
        this.A.a(n2);
    }

    public void d(int n2, int n3, int n4) {
        int n5 = (n4 - 2) * 21 + this.b.i();
        int n6 = (n3 - 2) * 21 + this.b.j();
        this.t.a(n2, n5, n6);
    }
}

