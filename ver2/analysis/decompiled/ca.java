/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class ca
extends ds {
    private int[] a;
    private String[] b;
    private int c;
    private Image d;
    private String[] e;
    private int f;
    private int g;
    private String h;
    private cm i = new cm("/f/ari_b");
    private int j;
    private int[] k;
    private int[] l;
    private int m;
    private int n;
    private int o = dd.d();
    private String[] v;

    public ca(String[][] stringArray) {
        this.h(cx.f - 10);
        this.i(7 * this.i.a() + 15);
        this.j = (cx.g >> 1) + 20;
        this.f(cx.f - this.k() >> 1);
        this.g(this.j - this.s - 15);
        this.a = new int[stringArray.length];
        this.b = new String[stringArray.length];
        int n2 = 0;
        while (n2 < stringArray.length) {
            if (stringArray[n2][0] == null) {
                this.a[n2] = -1;
            } else {
                this.a[n2] = Integer.parseInt(stringArray[n2][0]);
                if (this.a[n2] == 0) {
                    this.a[n2] = this.o;
                }
            }
            this.b[n2] = stringArray[n2][1];
            ++n2;
        }
        this.c = 0;
        this.h = dd.c();
        this.k = new int[4];
        n2 = cx.f / 4;
        this.k[0] = n2 / 2 - 15;
        this.k[2] = this.k[0] + n2;
        this.k[3] = this.k[2] + n2;
        this.k[1] = this.k[3] + n2;
        this.l = new int[4];
        this.n = 0;
        this.m = -1;
        this.a(this.a[0], this.b[0], true);
    }

    public boolean a() {
        this.f += 6;
        if (this.f >= this.e.length) {
            ++this.c;
            if (this.c >= this.a.length) {
                return false;
            }
            this.a(this.a[this.c], this.b[this.c], true);
        }
        this.g = this.f + 6 - 1;
        if (this.g >= this.e.length) {
            this.g = this.e.length - 1;
        }
        int n2 = this.g - this.f + 1;
        this.h(cx.f - 10);
        this.i((n2 + 1) * this.i.a() + 15);
        this.j = (cx.g >> 1) + 20;
        this.f(cx.f - this.k() >> 1);
        this.g(this.j - this.s - 15);
        this.c();
        return true;
    }

    private final void a(int n2) {
        if (n2 >= 0) {
            this.m = -1;
            int n3 = 0;
            while (n3 < this.n) {
                if (this.l[n3] == n2) {
                    this.m = n3;
                }
                ++n3;
            }
            if (this.m < 0) {
                this.m = this.n;
                this.l[this.n++] = n2;
            }
        } else {
            this.m = -1;
        }
    }

    private void a(int n2, String string, boolean bl2) {
        if (n2 >= 0) {
            this.a(n2);
            this.v = ao.b(u.a(n2), " ");
        }
        string = ao.a(string, "{p}", this.h);
        this.e = this.i.a(string, this.r - 12);
        this.f = 0;
        this.g = 5;
        if (this.g >= this.e.length) {
            this.g = this.e.length - 1;
        }
        int n3 = this.g - this.f + 1;
        this.h(cx.f - 10);
        this.i((n3 + 1) * this.i.a() + 15);
        this.j = (cx.g >> 1) + 20;
        this.f(cx.f - this.k() >> 1);
        this.g(this.j - this.s - 15);
        this.c();
    }

    private void c() {
        this.d = Image.createImage((int)(this.r - 10), (int)(this.s - 10));
        Graphics graphics = this.d.getGraphics();
        graphics.setColor(0xFFFFFF);
        graphics.fillRect(0, 0, this.r, this.s);
        int n2 = 3;
        int n3 = 5;
        int n4 = this.f;
        while (n4 <= this.g) {
            this.i.a(graphics, this.e[n4], n2, n3, 0);
            n3 += this.i.a();
            ++n4;
        }
    }

    private final void a(Graphics graphics, int n2, int n3, int n4, boolean bl2, boolean bl3) {
        if (n2 >= 0) {
            graphics.setColor(0xFFFFFF);
            graphics.fillRect(n3, n4, 34, 34);
            u.a(graphics, n2, n3 + 2, n4 + 2, bl2);
            if (bl3) {
                n3 += 17;
                n4 += 35;
                int n5 = 0;
                while (n5 < this.v.length) {
                    bz.c.a(graphics, this.v[n5], n3, n4, 1);
                    n4 += bz.c.a();
                    ++n5;
                }
            }
        }
    }

    private final void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        graphics.setColor(n8);
        graphics.fillRect(n2 + 4, n3, n4 - 8, n5);
        graphics.fillRect(n2, n3 + 4, n4, n5 - 8);
        graphics.fillRect(n2 + 1, n3 + 2, n4 - 2, n5 - 4);
        graphics.fillRect(n2 + 2, n3 + 1, n4 - 4, n5 - 2);
        graphics.setColor(n7);
        graphics.fillRect(n2 + 2, n3 + 2, n4 - 4, n5 - 4);
        graphics.fillRect(n2 + 4, n3 + 1, n4 - 8, n5 - 2);
        graphics.fillRect(n2 + 1, n3 + 4, n4 - 2, n5 - 8);
        if (n6 >= 0) {
            graphics.fillTriangle(n6 - 5, n3 + n5 - 1, n6 + 5, n3 + n5 - 1, n6, n3 + n5 + 7);
            graphics.setColor(n8);
            graphics.drawLine(n6, n3 + n5 + 7, n6 - 5, n3 + n5 - 1);
            graphics.drawLine(n6, n3 + n5 + 7, n6 + 5, n3 + n5 - 1);
        }
    }

    public void b() {
    }

    public void a(Graphics graphics, int n2, int n3) {
        if (this.d == null) {
            return;
        }
        if (this.a[this.c] >= 0) {
            this.a(graphics, this.p, this.q, this.r, this.s, this.k[this.m] + 15, 0xFFFFFF, 0);
        } else {
            this.a(graphics, this.p, this.q, this.r, this.s, -1, 0xFFFFFF, 0);
        }
        graphics.drawImage(this.d, this.p + 5, this.q + 5, 0);
        int n4 = 0;
        while (n4 < this.n) {
            this.a(graphics, this.l[n4], this.k[n4], this.j + 2, n4 % 2 == 0, n4 == this.m);
            ++n4;
        }
    }
}

