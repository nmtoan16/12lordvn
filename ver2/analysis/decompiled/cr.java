/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import com.mic.smsgame.suquan.b;
import com.mic.smsgame.suquan.t;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class cr
extends ae {
    private Image e;
    private Image f;
    private Image t;
    private av u;
    private c v;
    private int w;
    private int x;
    private Image y;
    private Image z;
    private Image A;
    private int B;
    private int C;
    private t D;
    private int E;
    private int F;
    private int G;
    private int H;
    private int I;
    private int J;
    private int K;
    private int L;
    private de M;
    private boolean N = false;
    private b O;
    private dk P;

    public cr(boolean bl2) {
        super(1);
        this.N = bl2;
        this.b_ = 170;
        this.c_ = 200;
        this.g = cx.f - this.b_ >> 1;
        this.a_ = (cx.g - this.c_ >> 1) - 10;
        if (dd.a >= 0) {
            dd.e.c = dd.b;
        }
        this.v = dd.e.c();
        this.v.c = this.v.c > this.v.b ? this.v.b : this.v.c;
        this.w = dd.b();
        this.x = bg.f(2);
        this.u = new av();
        this.D = new t();
        this.e = az.b("/gold");
        this.t = az.b("/book");
        this.f = az.b("/m/isw");
        this.z = az.b("/m/bl");
        this.A = az.b("/f/divide");
        this.y = az.b("/m/lv");
        this.B = this.z.getWidth();
        this.C = this.z.getHeight() / 2;
        if (dd.a >= 0) {
            dd.j = dd.f(dd.a, dd.e.e);
        }
        this.E = dd.u(this.v.e + 1);
        this.F = dd.u(this.v.e);
        if (dd.a < 0) {
            this.G = dd.i;
        }
        if (dd.a < 0) {
            this.H = dd.j(dd.c);
        }
        this.K = dd.j;
        if (dd.a < 0) {
            this.J = dd.i(dd.c);
        }
        if (dd.a < 0) {
            this.I = dd.a(dd.e, dd.f);
        }
        this.O = new b(az.b("/crystalviolet"));
        this.O.a_(false);
        this.L = 50;
        this.P = new dk("lvu");
    }

    public cr() {
        this(false);
    }

    public void c(de de2) {
        this.M = de2;
    }

    public void a(int n2) {
        if (this.L > 0) {
            this.L = 1;
        }
    }

    public void a(int n2, int n3) {
        if (this.L > 0) {
            this.L = 1;
        }
    }

    protected void e() {
        this.c = true;
        if (this.L >= 0) {
            --this.L;
            if (this.L == 0) {
                super.c(this.M);
            }
        } else {
            int n2 = 0;
            if (!this.N) {
                if (this.G > 0) {
                    n2 = this.G / 10;
                    n2 = n2 == 0 ? 1 : n2;
                    this.G -= n2;
                    this.x += n2;
                }
                if (this.K > 0) {
                    n2 = this.K / 10;
                    n2 = n2 == 0 ? 1 : n2;
                    this.K -= n2;
                    this.w += n2;
                }
                if (this.I > 0) {
                    n2 = this.I / 10;
                    n2 = n2 == 0 ? 1 : n2;
                    this.I -= n2;
                    this.w += n2;
                }
            } else {
                if (this.J > 0) {
                    n2 = this.J / 10;
                    n2 = n2 == 0 ? 1 : n2;
                    this.J -= n2;
                    this.w += n2;
                }
                if (this.H > 0) {
                    n2 = this.H / 10;
                    n2 = n2 == 0 ? 1 : n2;
                    this.H -= n2;
                    this.x += n2;
                }
            }
            while (dd.c(this.v.e + 1, this.w)) {
                this.v.a();
                this.E = dd.u(this.v.e + 1);
                this.F = dd.u(this.v.e);
                System.out.println(String.valueOf(this.E) + " " + this.F);
                this.O.a(this.g + 150, this.a_ + 20, 1, "");
                if (this.P.e()) continue;
                this.P.b();
            }
            this.O.b();
        }
    }

    private void a(Graphics graphics, int n2, int n3) {
        graphics.setColor(0xFFFFFF);
        graphics.fillRect(n2 + 2, n3 + 2, 34, 34);
        u.a(graphics, this.v.a, n2 + 4, n3 + 4, true);
        bz.c.a(graphics, u.a(this.v.a), n2 + 40, n3 + 0, 0);
        y.a(graphics, this.z, 0, this.C, this.B, this.C, n2 + 40, n3 + 15, 0);
        y.a(graphics, this.z, 0, 0, this.B * this.v.c / this.v.b, this.C, n2 + 40, n3 + 15, 0);
        this.D.a(graphics, this.v.c, n2 + 92, n3 + 16, 2);
        this.D.a(graphics, this.v.b, n2 + 98, n3 + 16, 0);
        graphics.drawImage(this.A, n2 + 93, n3 + 14, 0);
        y.a(graphics, this.y, 0, this.C, this.B, this.C, n2 + 40, n3 + 28, 0);
        y.a(graphics, this.y, 0, 0, this.B * (this.w - this.F) / (this.E - this.F), this.C, n2 + 40, n3 + 28, 0);
        this.D.a(graphics, this.w, n2 + 92, n3 + 29, 2);
        this.D.a(graphics, this.E, n2 + 98, n3 + 29, 0);
        graphics.drawImage(this.A, n2 + 93, n3 + 27, 0);
        graphics.drawImage(this.e, n2 + 132, n3 + 36, 0);
        this.u.a(graphics, this.x, n2 + 130 - this.u.a(this.x), n3 + 41);
        graphics.drawImage(this.f, n2 + 2, n3 + 39, 0);
        this.u.a(graphics, this.v.d, n2 + 17, n3 + 41);
        bz.d.a(graphics, " " + this.v.e, n2 + 151, n3 + 0, 2);
        this.O.a(graphics);
    }

    private void b(Graphics graphics, int n2, int n3) {
        if (dd.a < 0) {
            graphics.drawImage(this.e, n2 + 4, n3, 0);
            bz.c.a(graphics, "V\u00e0ng", n2 + 25, n3 + 6, 0);
            if (this.N) {
                bz.d.a(graphics, "-Th\u01b0\u1edfng N.V\u1ee5", n2 + 4, n3 + 21, 0);
                bz.c.a(graphics, "" + this.H, n2 + 160, n3 + 21, 2);
            } else {
                bz.d.a(graphics, "-Thu th\u1eadp", n2 + 4, n3 + 21, 0);
                bz.c.a(graphics, "" + this.G, n2 + 160, n3 + 21, 2);
            }
            graphics.drawImage(this.t, n2 + 2, n3 + 52, 0);
            bz.c.a(graphics, "Kinh nghi\u1ec7m", n2 + 25, n3 + 55, 0);
            if (this.N) {
                bz.d.a(graphics, "-Th\u01b0\u1edfng N.V\u1ee5", n2 + 4, n3 + 70, 0);
                bz.c.a(graphics, "" + this.J, n2 + 160, n3 + 70, 2);
            } else {
                bz.d.a(graphics, "-Thu th\u1eadp", n2 + 4, n3 + 70, 0);
                bz.c.a(graphics, "" + this.K, n2 + 160, n3 + 70, 2);
                bz.d.a(graphics, "-Th\u1eafng tr\u1eadn", n2 + 4, n3 + 85, 0);
                bz.c.a(graphics, "" + this.I, n2 + 160, n3 + 85, 2);
            }
        } else {
            graphics.drawImage(this.t, n2 + 2, n3 + 3, 0);
            bz.c.a(graphics, "Kinh nghi\u1ec7m", n2 + 25, n3 + 6, 0);
            bz.d.a(graphics, "-Th\u1eafng tr\u1eadn", n2 + 4, n3 + 21, 0);
            bz.c.a(graphics, "" + this.K, n2 + 160, n3 + 21, 2);
        }
    }

    public void a(Graphics graphics) {
        cz.a(graphics, cx.P, cx.Q, this.g, this.a_, this.b_, this.c_);
        bz.c.a(graphics, "K\u1ebft qu\u1ea3", cx.f >> 1, this.a_ + 5, 1);
        this.a(graphics, this.g + 9, this.a_ + 20);
        this.b(graphics, this.g + 5, this.a_ + 78);
    }
}

