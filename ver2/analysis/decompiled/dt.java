/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public class dt
extends e {
    private String e = null;
    private String[] f;
    private boolean g;
    private int h;
    private br i;
    public int d = 41377;
    private br j;

    public void a(br br2) {
        this.i = br2;
    }

    private void c() {
        this.i = bz.d;
        this.j = bz.c;
    }

    public dt(String string, int n2) {
        this.e = string;
        this.h = n2;
        this.c();
    }

    public dt(String[] stringArray, boolean bl2, int n2) {
        this.f = stringArray;
        this.g = bl2;
        this.h = n2;
        this.c();
    }

    public void a(dl dl2) {
        super.a(dl2);
        if (this.e != null) {
            this.f = bz.a(this.e, dl2.c);
        }
        if (this.f != null) {
            this.b = 0;
            if (this.f != null) {
                this.b = this.f.length * 15;
            }
            this.e = null;
        }
    }

    public void a(Graphics graphics, int n2, int n3) {
        if (this.f != null) {
            int n4;
            int n5 = 0;
            int n6 = this.c.b + this.c.d;
            if (this.g) {
                n5 = 1;
                n4 = this.j.a(this.f[0]);
                if (this.d >= 0) {
                    graphics.setColor(this.d);
                }
                switch (this.h) {
                    case 2: {
                        n2 += this.c.c;
                        if (this.d < 0) break;
                        graphics.fillRect(n2 - n4, n3 - 2, n4, 16);
                        break;
                    }
                    case 1: {
                        n2 += this.c.c / 2;
                        if (this.d < 0) break;
                        graphics.fillRect(n2 - n4 / 2, n3 - 2, n4, 16);
                        break;
                    }
                    default: {
                        if (this.d < 0) break;
                        graphics.fillRect(n2, n3 - 2, n4, 16);
                    }
                }
                this.j.a(graphics, this.f[0], n2, n3, this.h);
                n3 += 15;
            }
            n4 = n5;
            while (n4 < this.f.length) {
                if (n3 > n6) break;
                this.i.a(graphics, this.f[n4], n2, n3, this.h);
                n3 += 15;
                ++n4;
            }
        }
    }

    public void b() {
    }
}

