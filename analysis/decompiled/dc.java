/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import com.mg.smsgame.main.MGMIDlet;
import com.mg.smsgame.main.b;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public abstract class dc
extends aj
implements b {
    private int E = 1;
    private int F = 2;
    private int G = 3;
    private int H = 4;
    private int I = 5;
    private int J = 6;
    private int K = 7;
    protected int[] c;
    protected int d;
    protected int e = 0;
    protected int f;
    protected int[][] t;
    private String L = null;
    private String[] M;
    protected boolean u = false;
    private boolean N;
    private String[] O;
    protected da v;
    private boolean P;
    private int Q;
    protected cb[] w;
    protected int[] x;
    protected int[] y;
    private boolean R;
    private int S;
    private int T;
    protected Image z;
    protected Image A;
    protected Image[] B;
    protected dj C;
    protected int D = 33;

    private boolean w() {
        byte[] byArray = bg.q();
        return byArray == null;
    }

    public dc(int n2, String[] stringArray, int n3) {
        this.q = 3;
        this.D = n3;
        this.Q = n2;
        if (stringArray == null) {
            stringArray = new String[]{"1", "2", "3", "4"};
        }
        this.O = stringArray;
        this.b();
        this.u();
        this.C = new dj("Ph\u1ea7n th\u01b0\u1edfng");
        this.C.a(new da("\u0110\u00f3ng", this.K));
        this.v();
        this.a((h)null);
        this.a(this);
    }

    public final void v() {
        this.L = bg.w();
        this.c = bg.s();
        this.e = 0;
        int n2 = this.c.length - 1;
        while (n2 > -1) {
            if (this.c[n2] < 0) {
                this.e = n2;
                break;
            }
            --n2;
        }
        this.z();
        this.P = bg.a(this.c);
        if (this.P) {
            byte[] byArray = bg.u();
            if (byArray == null) {
                bg.x();
                MGMIDlet.f().d();
                return;
            }
            this.R = false;
            this.u = true;
            if (this.L != null) {
                this.M = bz.a("Ch\u00fac m\u1eebng b\u1ea1n \u0111\u00e3 s\u01b0u t\u1eadp \u0111\u1ee7 b\u1ed9 V\u1eadt ph\u1ea9m. H\u00e3y b\u1ea5m n\u00fat g\u1eedi \u0111\u1ec3 nh\u1eadn ph\u1ea7n th\u01b0\u1edfng: " + this.L, this.b_ - 10);
            }
            this.b((de)null);
            this.c((de)null);
            this.a((de)null);
            this.c(new da("G\u1eedi", this.F));
        }
    }

    protected abstract void b();

    private void x() {
        this.a(new da("C\u1ea7n bi\u1ebft", this.J));
    }

    private void y() {
        this.b(new da("Tr\u1edf v\u1ec1", this.I));
    }

    protected void u() {
        this.t = new int[10][4];
        this.t[0] = new int[]{-1, -1, 1, -1};
        int[] nArray = new int[4];
        nArray[1] = 3;
        nArray[2] = 8;
        nArray[3] = 2;
        this.t[1] = nArray;
        this.t[2] = new int[]{1, 1, 4, -1};
        this.t[3] = new int[]{1, -1, 5, 1};
        this.t[4] = new int[]{2, 5, 6, -1};
        this.t[5] = new int[]{3, -1, 7, 4};
        this.t[6] = new int[]{4, 8, 8, -1};
        this.t[7] = new int[]{5, -1, 8, 8};
        this.t[8] = new int[]{1, 7, 9, 6};
        this.t[9] = new int[]{8, -1, -1, -1};
        int n2 = this.b_ / 2 - 10;
        int n3 = this.c_ / 2 - 10;
        this.x = new int[]{n2, n2, n2 - 40, n2 + 40, n2 - 50, n2 + 50, n2 - 40, n2 + 40, n2, n2};
        this.y = new int[]{n3 - 80, n3 - 50, n3 - 40, n3 - 40, n3, n3, n3 + 40, n3 + 40, n3 + 50, n3 + 80};
    }

    private void z() {
        this.N = this.w();
        this.w = new cb[this.x.length];
        int n2 = 0;
        while (n2 < this.w.length) {
            Image image = null;
            image = this.c[n2] == -1 ? this.A : (this.c[n2] == 0 ? this.z : this.B[this.c[n2] - 1]);
            this.w[n2] = new cb(image, this.x[n2], this.y[n2]);
            this.w[n2].k(this.D);
            ++n2;
        }
        if (!this.N) {
            this.y();
        }
        this.x();
        this.v = new da("G\u1eedi", this.E);
        this.c((de)null);
        if (this.c[this.e] < 0) {
            this.c(this.v);
        }
    }

    public void a(int n2) {
        if (this.P || this.R) {
            return;
        }
        if (n2 == 14) {
            if (this.t[this.e][3] > -1) {
                this.e = this.t[this.e][3];
            }
        } else if (n2 == 15) {
            if (this.t[this.e][1] > -1) {
                this.e = this.t[this.e][1];
            }
        } else if (n2 == 12) {
            if (this.t[this.e][0] > -1) {
                this.e = this.t[this.e][0];
            }
        } else if (n2 == 13 && this.t[this.e][2] > -1) {
            this.e = this.t[this.e][2];
        }
        if (this.c[this.e] < 0) {
            this.c(this.v);
        } else {
            this.c((de)null);
        }
    }

    public void a(int n2, int n3) {
        if (this.P || this.R) {
            return;
        }
        int n4 = 0;
        while (n4 < this.w.length) {
            if (n2 > this.w[n4].i() && n2 < this.w[n4].i() + this.w[n4].l() && n3 > this.w[n4].j() && n3 < this.w[n4].j() + this.w[n4].l()) {
                if (this.e == n4) {
                    cx.u[16] = 2;
                } else {
                    this.e = n4;
                }
            }
            ++n4;
        }
    }

    public void a(Graphics graphics) {
        this.e(graphics);
        this.b(graphics);
    }

    protected void e(Graphics graphics) {
        if (this.r == null) {
            graphics.setColor(cx.S);
            graphics.fillRect(0, 0, this.b_, this.c_);
        }
    }

    protected void b(Graphics graphics) {
        int n2 = 0;
        while (n2 < this.w.length) {
            if (this.R && n2 == this.d) {
                if (this.T > 0) {
                    if (this.T % 2 == 0) {
                        this.w[n2].a(graphics);
                    }
                } else {
                    int n3 = this.w[n2].k();
                    int n4 = this.w[n2].l();
                    graphics.setClip(this.x[n2] + n3 / 2 - n3 * this.S / 20, this.y[n2] + n4 / 2 - n4 * this.S / 20, n3 * this.S / 10, n4 * this.S / 10);
                    this.w[n2].a(graphics);
                    graphics.setClip(0, 0, this.b_, this.c_);
                }
            } else {
                this.w[n2].a(graphics);
            }
            ++n2;
        }
        if (this.R) {
            return;
        }
        this.a(graphics, this.w[this.e].i() + this.w[this.e].l() / 2, this.w[this.e].j() + this.w[this.e].l() / 2);
        if (this.u) {
            bz.a(bz.c);
            if (this.M != null) {
                bz.a(graphics, bz.d, this.M, 5, 5, this.b_ - 10, this.c_, 1);
            }
        }
    }

    protected void a(Graphics graphics, int n2, int n3) {
        graphics.setColor(0xFFFF00);
        graphics.fillTriangle(n2, n3 - 10 - this.f, n2 - 4, n3 - 14 - this.f, n2 + 4, n3 - 14 - this.f);
        graphics.fillTriangle(n2, n3 + 10 + this.f, n2 - 5, n3 + 15 + this.f, n2 + 4, n3 + 15 + this.f);
        graphics.fillTriangle(n2 - 10 - this.f, n3, n2 - 14 - this.f, n3 - 4, n2 - 14 - this.f, n3 + 4);
        graphics.fillTriangle(n2 + 10 + this.f, n3, n2 + 15 + this.f, n3 - 5, n2 + 15 + this.f, n3 + 4);
    }

    public void a() {
        int n2 = 0;
        while (n2 < this.w.length) {
            this.w[n2].a();
            ++n2;
        }
        if (this.R) {
            if (this.T > 0) {
                --this.T;
            } else if (this.S < 10) {
                ++this.S;
            } else {
                this.R = false;
            }
        }
        this.f = this.f < 4 ? ++this.f : 0;
    }

    public void e(int n2) {
        this.R = true;
        this.T = 10;
        this.S = 0;
        this.d = n2;
    }

    public void b(int n2, int n3) {
        if (n3 == this.E) {
            if (this.c[this.e] < 0) {
                ae ae2 = cz.a("Th\u00f4ng tin", "C\u00f3 ph\u1ea3i b\u1ea1n mu\u1ed1n \u0111\u1ed5i v\u1eadt ph\u1ea9m? Tham kh\u1ea3o gi\u00e1 t\u1ea1i http://free.ola.vn.", "C\u00f3", this.G, "Kh\u00f4ng", this.H, 1);
                ae2.a(this);
                cz.d().a(ae2);
            }
        } else if (n3 == this.F) {
            byte[] byArray = bg.u();
            if (byArray == null) {
                bg.x();
                MGMIDlet.f().d();
                return;
            }
            String string = new String(byArray);
            an.a(string, new f(this));
        } else if (n3 == this.G) {
            bg.e(this.e);
        } else if (n3 == this.H) {
            cz.d().a(true);
        } else if (n3 == this.I) {
            if (this.Q == -1) {
                this.Q = 2;
            }
            this.f(this.Q);
        } else if (n3 == this.J) {
            String string = "B\u1ea1n c\u1ed1 g\u1eafng s\u01b0u t\u1eadp \u0111\u1ee7 b\u1ed9 v\u1eadt ph\u1ea9m nh\u01b0 sau \u0111\u1ec3 nh\u1eadn ";
            string = this.L == null ? String.valueOf(string) + "c\u00e1c ph\u1ea7n qu\u00e0 gi\u00e1 tr\u1ecb" : String.valueOf(string) + this.L;
            dt dt2 = new dt(string, 0);
            dt dt3 = new dt(this.O[0], 0);
            x x2 = new x(this.B[0]);
            dt dt4 = new dt(this.O[1], 0);
            x x3 = new x(this.B[1]);
            dt dt5 = new dt(this.O[2], 0);
            x x4 = new x(this.B[2]);
            dt dt6 = new dt(this.O[3], 0);
            x x5 = new x(this.B[3]);
            dt dt7 = new dt("Ch\u00fa \u00dd: Ph\u1ea7n qu\u00e0 s\u1ebd thay \u0111\u1ed5i khi b\u1ea1n nh\u1eadn \u0111\u01b0\u1ee3c v\u1eadt ph\u1ea9m m\u1edbi. \u0110\u1ec3 tr\u00e1nh b\u1ecb m\u1ea5t qu\u00e0 th\u01b0\u1edfng, b\u1ea1n kh\u00f4ng n\u00ean thay \u0111\u1ed5i s\u1ed1 \u0111i\u1ec7n tho\u1ea1i trong qu\u00e1 tr\u00ecnh ch\u01a1i.", 0);
            dt7.a(bz.c);
            this.C.m();
            this.C.b(new da("\u0110\u00f3ng", this.K));
            this.C.a(new e[]{dt2, dt3, x2, dt4, x3, dt5, x4, dt6, x5, dt7});
            this.C.a(this);
            cz.d().a(this.C);
            if (this.N) {
                bg.r();
                this.N = false;
                this.y();
            }
        } else if (n3 == this.K) {
            cz.d().a(false);
        }
    }

    public void f(int n2) {
        cz.d().e.a(3, this.Q, true);
    }

    static void a(dc dc2, String string) {
        dc2.L = string;
    }

    static void a(dc dc2, boolean bl2) {
        dc2.P = bl2;
    }

    static void a(dc dc2) {
        dc2.z();
    }
}

