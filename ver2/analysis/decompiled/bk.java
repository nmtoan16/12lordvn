/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class bk {
    protected int a;
    protected int b;
    protected int c;
    protected int d;
    protected Image e;
    private boolean h;
    protected int[] f;
    protected dl[] g;
    private int i;

    public int a() {
        return this.c;
    }

    public int b() {
        return this.d;
    }

    public bk(String string, int n2, int n3) {
        this.a(n2, n3);
        this.e = az.b(string);
        this.c = this.e.getWidth() / 2;
        this.d = this.e.getHeight();
        this.f = new int[]{14, 15, 12, 13, 16};
        this.g = new dl[5];
        this.g[0] = new dl(0, 13, 18, 28);
        this.g[1] = new dl(36, 13, 18, 28);
        this.g[2] = new dl(13, 0, 28, 18);
        this.g[3] = new dl(13, 36, 28, 18);
        this.g[4] = new dl(20, 20, 14, 14);
        this.i = -1;
        this.h = false;
    }

    public void a(int n2, int n3) {
        this.a = n2;
        this.b = n3;
    }

    public int b(int n2, int n3) {
        if (this.h) {
            return -1;
        }
        if (n2 >= this.a && n2 <= this.a + this.c && n3 >= this.b && n3 <= this.b + this.d) {
            this.h = true;
            n2 -= this.a;
            n3 -= this.b;
            int n4 = 0;
            while (n4 < this.g.length) {
                if (this.g[n4].a(n2, n3)) {
                    this.i = n4;
                    return this.f[n4];
                }
                ++n4;
            }
        }
        this.i = -1;
        return -1;
    }

    public int c() {
        this.h = false;
        if (this.i > -1) {
            int n2 = this.i;
            this.i = -1;
            return this.f[n2];
        }
        return -1;
    }

    public void a(Graphics graphics) {
        y.a(graphics, this.e, 0, 0, this.c, this.d, this.a, this.b, 20);
        if (this.i > -1) {
            y.a(graphics, this.e, this.c + this.g[this.i].a, this.g[this.i].b, this.g[this.i].c, this.g[this.i].d, this.a + this.g[this.i].a, this.b + this.g[this.i].b, 20);
        }
    }
}

