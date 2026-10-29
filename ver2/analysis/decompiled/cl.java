/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public class cl
extends ds {
    private String[] a;
    private int b;
    private int c;
    private int d;
    private int e;

    public cl(String[] stringArray, dl dl2) {
        this.p = dl2.a;
        this.q = dl2.b;
        this.r = dl2.c;
        this.s = dl2.d;
        this.a = stringArray;
        this.b = 0;
        this.c = this.q + this.s << 10;
        this.e = this.c >> 10;
        this.d = this.p + (this.r >> 1);
    }

    public boolean a() {
        return this.b >= this.a.length;
    }

    public void b() {
        if (this.b >= this.a.length) {
            return;
        }
        this.c -= 512;
        this.e = this.c >> 10;
        if (this.e + 15 <= this.q) {
            ++this.b;
            this.c += 15360;
            this.e = this.c >> 10;
        }
    }

    public int c() {
        return this.b;
    }

    public void a(Graphics graphics, int n2, int n3) {
        if (this.b >= this.a.length) {
            return;
        }
        int n4 = this.e;
        int n5 = this.b;
        while (n5 < this.a.length && n4 < this.q + this.s) {
            bz.d.a(graphics, this.a[n5], this.d, n4, 1);
            ++n5;
            n4 += 15;
        }
    }
}

