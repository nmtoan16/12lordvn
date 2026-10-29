/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import com.mic.smsgame.suquan.t;
import com.mic.smsgame.suquan.u;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class co {
    private Image[] a;
    private Image b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int[] g;
    private boolean[] h;
    private int[] i;
    private int j;
    private t k;
    private av l;
    private Image m;
    private int n;
    private int o;
    private dq p;
    private Image q;
    private int r;
    private c s;
    private int t;
    private Image u;
    private Image v;
    private Image w;
    private Image x;
    private String y;
    private boolean[] z;
    private Image A;

    public co(boolean[] blArray) {
        this.z = blArray;
        this.a = new Image[2];
        this.a[0] = az.b("/frame1");
        this.a[1] = az.b("/frame2");
        this.b = az.b("/frame3");
        this.A = az.b("/m/lock");
        this.m = az.b("/m/g");
        this.n = this.m.getWidth() / 5;
        this.o = this.m.getHeight();
        this.q = az.b("/m/b");
        this.k = new t();
        this.l = new av();
        this.e = this.a[0].getWidth();
        this.f = 200;
        this.c = cx.f - this.e >> 1;
        this.d = cx.g - this.f >> 1;
        this.r = bg.f(2);
        this.j = -1;
        this.g = dd.k();
        this.h = new boolean[this.g.length];
        this.i = new int[this.g.length];
        int n2 = 0;
        while (n2 < this.h.length) {
            this.i[n2] = dd.A(n2);
            if (this.r >= this.i[n2] && blArray[n2]) {
                this.j = 0;
                this.h[n2] = true;
            }
            ++n2;
        }
        this.u = az.b("/m/af");
        this.v = az.b("/m/ih");
        this.w = az.b("/m/isw");
        this.x = az.b("/gold");
        this.b(this.j);
        this.p = new dq(az.b("/focusitem"), 2);
        this.p.c(2);
        this.p.b(true);
        byte[][] byArrayArray = new byte[1][];
        byte[] byArray = new byte[2];
        byArray[1] = 1;
        byArrayArray[0] = byArray;
        this.p.a(byArrayArray);
        this.p.a_(0);
    }

    public boolean a() {
        return this.j >= 0;
    }

    private void b(int n2) {
        if (n2 < 0) {
            return;
        }
        this.s = dd.f = dd.e(n2, dd.e.e);
        this.t = dd.f(n2, dd.e.e);
        this.y = u.a(this.g[n2]);
    }

    public void b() {
        if (this.j < 0) {
            return;
        }
        if (bg.f(5) > 0) {
            dd.h = true;
            bg.g(5);
        } else {
            dd.h = dd.e.e > dd.f.e ? true : (dd.e.e < dd.f.e ? false : ay.a(2) == 0);
        }
        dd.a = this.j;
        bg.b(2, dd.A(this.j));
    }

    public void c() {
        if (this.j >= 0) {
            this.p.e();
        }
    }

    public void a(int n2) {
        if (this.j < 0) {
            return;
        }
        switch (n2) {
            case 14: {
                do {
                    this.j = (this.j - 1 + this.g.length) % this.g.length;
                } while (!this.h[this.j]);
                break;
            }
            case 15: {
                do {
                    this.j = (this.j + 1) % this.g.length;
                } while (!this.h[this.j]);
            }
        }
        this.b(this.j);
    }

    private void a(Graphics graphics, int n2, int n3) {
        u.a(graphics, this.s.a, n2 + 10, n3 + 3, true);
        bz.c.a(graphics, this.y, n2 + 45, n3 + 2, 0);
        graphics.drawImage(this.u, n2 + 8, n3 + 1, 0);
        graphics.drawImage(this.v, n2 + 54, n3 + 20, 0);
        graphics.drawImage(this.w, n2 + 111, n3 + 20, 0);
        this.l.a(graphics, this.s.c, n2 + 68, n3 + 22);
        this.l.a(graphics, this.s.d, n2 + 126, n3 + 22);
        bz.c.a(graphics, "C\u1ea5p " + this.s.e, n2 + 8, n3 + 34, 0);
    }

    private void b(Graphics graphics, int n2, int n3) {
        if (this.j < 0) {
            bz.d.a(graphics, "B\u1ea1n kh\u00f4ng \u0111\u1ee7 v\u00e0ng", cx.f >> 1, n3 + 10, 1);
        } else {
            this.a(graphics, n2, n3);
            bz.d.a(graphics, "K.Nghi\u1ec7m thu \u0111\u01b0\u1ee3c:", n2 + 5, n3 + 55, 0);
            bz.c.a(graphics, String.valueOf(this.t) + " (" + dd.B(this.j) + "%)", cx.f >> 1, n3 + 70, 1);
        }
        graphics.drawImage(this.x, n2 + 132, n3 + 100, 0);
        this.l.a(graphics, this.r, n2 + 130 - this.l.a(this.r), n3 + 105);
    }

    public void a(Graphics graphics) {
        com.mic.smsgame.suquan.u.a(graphics, this.c, this.d, this.f, 7814005, this.a);
        bz.c.a(graphics, "\u0110\u1ea5u tr\u01b0\u1eddng", cx.f >> 1, this.d + 12, 1);
        graphics.drawImage(this.b, this.c + this.a[0].getWidth() / 2, this.d + 62, 17);
        graphics.drawImage(this.b, this.c + this.a[0].getWidth() / 2, this.d + 117, 17);
        int n2 = this.c + 9;
        int n3 = 0;
        while (n3 < this.g.length) {
            if (this.z[n3]) {
                y.a(graphics, this.m, n3 * this.n, 0, this.n, this.o, n2, this.d + 30, 0);
                this.k.a(graphics, this.i[n3], n2 + (this.n >> 1), this.d + 53, 1);
                if (!this.h[n3]) {
                    graphics.drawImage(this.q, n2, this.d + 30, 0);
                }
            } else {
                graphics.drawImage(this.A, n2 + 15, this.d + 45, 3);
            }
            n2 += 32;
            ++n3;
        }
        if (this.j >= 0) {
            n3 = this.c + 9 + this.j * 32;
            this.p.a(graphics, n3 - 3, this.d + 30 - 3);
            y.a(graphics, this.m, this.j * this.n, 0, this.n, this.o, n3, this.d + 30, 0);
            this.k.a(graphics, this.i[this.j], n3 + (this.n >> 1), this.d + 53, 1);
        }
        this.b(graphics, this.c + 9, this.d + 70);
    }
}

