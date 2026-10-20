/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import com.mic.smsgame.suquan.n;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class bx
extends ds {
    bn[] a;
    Image b;
    Image c;
    Image d;
    Image e;
    private int g;
    private int[] h = new int[]{81208, 85576, 23637};
    private int[] i = new int[]{5046585, 6095173, 7209297};
    ax[] f;
    private int j;
    private int k;
    private int l;
    private Image m;
    private int n = 0;
    private boolean o;
    private int v;
    private n[] w;
    private n[] x;
    private int y;
    private int z;
    private int[] A;
    private dq[] B;

    public bx(bn[] bnArray) {
        this.a = bnArray;
        this.d();
        this.f = new ax[2];
        this.f[0] = new ax();
        this.f[0].d(cx.l + 35, cx.m + 237);
        this.f[1] = new ax();
        this.f[1].d(cx.l + 175, cx.m + 237);
        byte[][] byArrayArray = new byte[1][];
        byte[] byArray = new byte[10];
        byArray[4] = 1;
        byArray[5] = 1;
        byArray[6] = 1;
        byArray[7] = 2;
        byArray[8] = 2;
        byArray[9] = 1;
        byArrayArray[0] = byArray;
        byte[][] byArrayArray2 = byArrayArray;
        Image image = bs.a().c[2];
        this.w = new n[25];
        int n2 = 0;
        while (n2 < this.w.length) {
            this.w[n2] = new n();
            this.w[n2].a(byArrayArray2);
            this.w[n2].a(image, 3);
            this.w[n2].a(false);
            ++n2;
        }
        image = bs.a().c[3];
        this.x = new n[10];
        n2 = 0;
        while (n2 < this.x.length) {
            this.x[n2] = new n();
            this.x[n2].a(byArrayArray2);
            this.x[n2].a(image, 3);
            this.x[n2].a(false);
            ++n2;
        }
        this.A = new int[2];
        this.B = new dq[2];
        this.B[0] = new dq(bs.a().C, 3);
        this.B[0].d(this.f[0].i() + this.f[0].k() / 2, this.f[0].j() + this.f[0].l() / 2);
        this.B[0].k(3);
        this.B[1] = new dq(bs.a().D, 3);
        this.B[1].d(this.f[0].i() + this.f[0].k() / 2, this.f[0].j() + this.f[0].l());
        this.B[1].k(33);
        byte[][] byArrayArray3 = new byte[1][];
        byte[] byArray2 = new byte[6];
        byArray2[2] = 1;
        byArray2[3] = 1;
        byArray2[4] = 2;
        byArray2[5] = 2;
        byArrayArray3[0] = byArray2;
        byArrayArray2 = byArrayArray3;
        n2 = 0;
        while (n2 < this.B.length) {
            this.B[n2].a(false);
            this.B[n2].a(byArrayArray2);
            ++n2;
        }
    }

    private void d() {
        this.b = bs.a().l;
        this.d = bs.a().n;
        this.e = bs.a().o;
        this.c = bs.a().m;
        this.m = bs.a().p;
    }

    public ax[] a() {
        return this.f;
    }

    public void b(boolean bl2) {
        this.B[1].a(bl2);
    }

    public void c(boolean bl2) {
        this.B[0].a(bl2);
    }

    public void a(Graphics graphics, int n2, int n3) {
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        bn bn2 = this.a[0];
        int n9 = this.f[0].i() + this.f[0].k() + 6;
        int n10 = this.f[0].j();
        int n11 = bn2.l();
        if (n11 > bn2.j() || this.A[0] % 2 == 0) {
            if (n11 > bn2.e()) {
                y.a(graphics, this.b, 0, 0, this.b.getWidth(), this.b.getHeight(), n9, n10, 0);
                n8 = n11 - bn2.e();
                n7 = this.b.getWidth() * n8 / bn2.e();
                y.a(graphics, this.c, 0, 0, n7, this.c.getHeight(), n9, n10, 0);
            } else {
                n8 = this.b.getWidth() * n11 / bn2.e();
                if (bn2.l() > 0 && n8 < 1) {
                    n8 = 1;
                }
                y.a(graphics, this.b, 0, 0, n8, this.b.getHeight(), n9, n10, 0);
            }
        }
        y.a(graphics, this.e, 0, 0, this.e.getWidth() * bn2.p() / bn2.q(), this.e.getHeight(), n9, n10 += 11, 0);
        n10 += 11;
        if (this.z % 2 == 0) {
            y.a(graphics, this.d, 0, 0, this.d.getWidth() * bn2.o() / bn2.n(), this.d.getHeight(), n9, n10, 0);
        }
        bn2 = this.a[1];
        n9 = this.f[1].i() - 5;
        n10 = this.f[0].j();
        n8 = 0;
        n11 = bn2.l();
        if (n11 > bn2.j() || this.A[1] % 2 == 0) {
            if (n11 > bn2.e()) {
                graphics.drawRegion(this.b, 0, 0, this.b.getWidth(), this.b.getHeight(), 2, n9 - this.b.getWidth(), n10, 0);
                n7 = n11 - bn2.e();
                n8 = this.b.getWidth() * n7 / bn2.e();
                graphics.drawRegion(this.c, 0, 0, n8, this.c.getHeight(), 2, n9 - n8, n10, 0);
            } else {
                n8 = this.b.getWidth() * n11 / bn2.e();
                if (bn2.l() > 0 && n8 < 1) {
                    n8 = 1;
                }
                graphics.drawRegion(this.b, 0, 0, n8, this.b.getHeight(), 2, n9 - n8, n10, 0);
            }
        }
        n8 = this.e.getWidth() * bn2.p() / bn2.q();
        graphics.drawRegion(this.e, 0, 0, n8, this.e.getHeight(), 2, n9 - n8, n10 += 11, 0);
        n8 = this.d.getWidth() * bn2.o() / bn2.n();
        graphics.drawRegion(this.d, 0, 0, n8, this.d.getHeight(), 2, n9 - n8, n10 += 11, 0);
        if (this.k > 0 && this.k <= 10) {
            ax ax2 = this.f[this.j];
            if (this.k > 6) {
                if (this.k % 2 == 0) {
                    graphics.setColor(0xFFFFFF);
                    graphics.fillRect(ax2.i(), ax2.j(), ax2.k(), ax2.l());
                }
            } else {
                n6 = this.m.getWidth() / 5;
                n5 = this.m.getHeight();
                n4 = (6 - this.k) % 5;
                int n12 = 2;
                if (this.j == 0) {
                    n12 = 0;
                }
                graphics.drawRegion(this.m, n4 * n6, 0, n6, n5, n12, ax2.i() + ax2.k() / 2, ax2.j() + ax2.l() / 2 - 7, 3);
            }
        }
        n9 = this.f[0].i() + this.f[0].k() + 5;
        n10 = this.f[0].j();
        n8 = 0;
        if (!this.o) {
            n9 = this.f[this.n].i() - 2;
            n10 = this.f[this.n].j() - 2;
            int[] nArray = this.h;
            n8 = 83;
            if (this.n == 1) {
                n9 = n9 + this.f[this.n].k() - n8 + 5;
                nArray = this.i;
            }
            n8 = 82;
            n6 = 34;
            n5 = 0;
            while (n5 < nArray.length) {
                y.b(graphics, nArray[n5], n9, n10, n8, n6);
                --n9;
                --n10;
                n8 += 2;
                n6 += 2;
                ++n5;
            }
            n5 = 0;
            if (this.g < 10) {
                if (this.g % 2 == 0) {
                    n5 = 3;
                }
            } else {
                n5 = this.g < 18 ? (this.g - 3) % 4 : 3;
            }
            n9 = this.f[0].i() - 2;
            n10 = this.f[0].j() - 186;
            n8 = 172;
            n6 = 172;
            n4 = 0;
            while (n4 < n5) {
                graphics.setColor(nArray[n4]);
                graphics.drawRect(n9, n10, n8, n6);
                --n9;
                --n10;
                n8 += 2;
                n6 += 2;
                ++n4;
            }
        }
        int n13 = 0;
        while (n13 < this.B.length) {
            this.B[n13].a(graphics);
            ++n13;
        }
        n13 = this.x.length - 1;
        while (n13 > -1) {
            this.x[n13].a(graphics);
            --n13;
        }
        n13 = this.w.length - 1;
        while (n13 > -1) {
            this.w[n13].a(graphics);
            --n13;
        }
    }

    public void b() {
        if (!this.o) {
            ++this.v;
            if (this.v == 10) {
                this.v = 0;
            }
            if (this.g < 20) {
                ++this.g;
            }
        }
        if (this.k > 0) {
            --this.k;
            if (this.k == 7) {
                bs.a().f();
                if (this.j == 0) {
                    cz.d(250);
                }
            } else if (this.k == 0) {
                this.k = this.l;
                this.l = 0;
            }
        }
        int n2 = 0;
        while (n2 < this.a.length) {
            if (this.j == n2 && this.y > 0) {
                --this.y;
            } else {
                int n3 = this.a[n2].l();
                int n4 = this.a[n2].r() - n3;
                if (n4 != 0) {
                    if (Math.abs(n4) >= 6) {
                        n4 /= 6;
                    }
                    this.a[n2].d(n3 + n4);
                }
            }
            ++n2;
        }
        n2 = 0;
        while (n2 < this.A.length) {
            if (this.a[n2].l() <= this.a[n2].j()) {
                int n5 = n2;
                this.A[n5] = this.A[n5] + 1;
                if (this.A[n2] > 1) {
                    this.A[n2] = 0;
                }
            }
            ++n2;
        }
        n2 = this.w.length - 1;
        while (n2 > -1) {
            this.w[n2].e();
            --n2;
        }
        n2 = this.x.length - 1;
        while (n2 > -1) {
            this.x[n2].e();
            --n2;
        }
        if (this.z > 0) {
            --this.z;
        }
        n2 = 0;
        while (n2 < this.B.length) {
            this.B[n2].e();
            ++n2;
        }
    }

    public void a(int n2) {
        if (this.y == 0) {
            this.y = n2;
        } else if (this.y < n2) {
            this.y = this.y + n2 >> 1;
        }
    }

    public void a(int n2, int n3) {
        bn bn2 = this.a[this.j];
        int n4 = this.f[n2].j() + 10;
        if (n2 == 0) {
            int n5 = this.f[n2].i() - 3 + this.f[n2].k() + this.d.getWidth() * bn2.o() / bn2.n();
            int n6 = 0;
            while (n6 < this.x.length) {
                if (!this.x[n6].m()) {
                    this.x[n6].a(n5, n4, n5 - 5 + ay.a(80), n4 - 80 + ay.a(100), n3);
                    this.x[n6].b(ay.a(10));
                }
                ++n6;
            }
        } else {
            int n7 = this.f[n2].i() - 5 - this.d.getWidth() * bn2.o() / bn2.n();
            int n8 = 0;
            while (n8 < this.x.length) {
                if (!this.x[n8].m()) {
                    this.x[n8].a(n7, n4, n7 + 5 - ay.a(80), n4 - 80 + ay.a(100), n3);
                    this.x[n8].b(ay.a(10));
                }
                ++n8;
            }
        }
    }

    public void b(int n2, int n3) {
        this.a(this.y);
        bn bn2 = this.a[n2];
        if (bn2.k()) {
            return;
        }
        if (this.k < 1) {
            this.k = n3;
        }
        if (this.k < n3) {
            this.l = n3 - this.k;
            if (this.l < 6) {
                this.l = 0;
            }
        } else {
            this.l = this.k - n3;
            this.k = n3;
        }
        if ((n3 -= 5) < 0) {
            n3 = 0;
        }
        this.j = n2;
        int n4 = this.f[n2].j();
        if (n2 == 0) {
            int n5 = this.f[n2].i() - 3 + this.f[n2].k() + this.b.getWidth() * bn2.r() / bn2.s();
            int n6 = 15;
            int n7 = 0;
            while (n7 < this.w.length) {
                if (!this.w[n7].m()) {
                    this.w[n7].a(n5, n4, n5 - 5 + ay.a(80), n4 - 80 + ay.a(100), n3);
                    this.w[n7].b(ay.a(10));
                    if (--n6 <= 0) break;
                }
                ++n7;
            }
        } else {
            int n8 = this.f[n2].i() - 5 - this.b.getWidth() * bn2.r() / bn2.s();
            int n9 = 15;
            int n10 = 0;
            while (n10 < this.w.length) {
                if (!this.w[n10].m()) {
                    this.w[n10].a(n8, n4, n8 + 5 - ay.a(80), n4 - 80 + ay.a(100), n3);
                    this.w[n10].b(ay.a(10));
                    if (--n9 <= 0) break;
                }
                ++n10;
            }
        }
    }

    public void b(int n2) {
        if (this.n != n2) {
            this.g = 0;
        }
        this.n = n2;
    }

    public void d(boolean bl2) {
        this.o = bl2;
    }

    public void c() {
        this.z = 40;
    }
}

