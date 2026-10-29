/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class bh
extends ds {
    private int a;
    private Image[] b;
    private Image[] c;
    private Image[] d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    private int j;

    public void a(int n2) {
        this.a = n2;
    }

    public bh() {
        this.c();
    }

    private void c() {
        this.c = bs.a().g;
        this.d = bs.a().j;
        this.b = new Image[1];
        this.b[0] = bs.a().q;
    }

    public void a(Graphics graphics, int n2, int n3) {
        switch (this.a) {
            case 2: 
            case 3: {
                graphics.drawImage(this.c[this.g], this.p, this.q, 3);
                break;
            }
            case 1: {
                graphics.drawImage(this.c[this.g], this.p, this.q, 3);
                int n4 = 14;
                if (this.e >= 3) {
                    ++n4;
                }
                if ((this.f & 1) == 1) {
                    graphics.drawRegion(this.d[this.g], 0, 0, this.d[this.g].getWidth(), this.d[this.g].getHeight(), 6, this.p - n4, this.q, 10);
                }
                if ((this.f & 2) == 2) {
                    graphics.drawRegion(this.d[this.g], 0, 0, this.d[this.g].getWidth(), this.d[this.g].getHeight(), 5, this.p + n4, this.q, 6);
                }
                if ((this.f & 4) == 4) {
                    graphics.drawRegion(this.d[this.g], 0, 0, this.d[this.g].getWidth(), this.d[this.g].getHeight(), 0, this.p, this.q - n4, 33);
                }
                if ((this.f & 8) != 8) break;
                graphics.drawRegion(this.d[this.g], 0, 0, this.d[this.g].getWidth(), this.d[this.g].getHeight(), 3, this.p, this.q + n4, 17);
                break;
            }
            case 4: {
                int n5 = this.b[this.g].getWidth() >> 1;
                int n6 = 0;
                if (this.j >= 2) {
                    n6 = 1;
                }
                y.a(graphics, this.b[this.g], n6 * n5, 0, n5, this.b[this.g].getHeight(), this.p, this.q, 3);
            }
        }
    }

    public void a() {
        switch (this.a) {
            case 4: {
                ++this.j;
                if (this.j < 4) break;
                this.a(2);
                break;
            }
            case 3: {
                int n2 = Math.abs(this.i - this.q);
                n2 = n2 < 2 ? 2 : (n2 /= 2);
                boolean bl2 = this.f(this.i, n2);
                n2 = Math.abs(this.h - this.p);
                n2 = n2 < 2 ? 2 : (n2 /= 2);
                if (bl2 &= this.e(this.h, n2)) {
                    this.a(2);
                }
            }
            case 1: {
                ++this.e;
                if (this.e <= 6) break;
                this.e = 0;
                break;
            }
        }
    }

    public void b() {
        this.a = 0;
    }

    public void b(int n2) {
        this.g = n2;
    }

    public void b(int n2, int n3, int n4, int n5) {
        this.a = 3;
        this.h = n4;
        this.i = n5;
        this.d(n2, n3);
    }

    public void a(int n2, int n3) {
        this.a = 2;
        this.d(n2, n3);
    }

    public void a(int n2, int n3, int n4) {
        this.a = 1;
        this.f = n2;
        this.d(n3, n4);
    }

    public void b(int n2, int n3) {
        this.a = 4;
        this.d(n2, n3);
        this.j = 0;
        this.e = 0;
    }
}

