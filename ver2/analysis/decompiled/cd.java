/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public class cd
extends ds {
    protected ds[] a;
    protected int b;
    protected boolean c = true;
    protected dy d;

    public void a(ds[] dsArray, int n2) {
        this.a = dsArray;
        this.b = n2;
        this.b(0, n2);
    }

    public void a(int n2) {
        if (this.a == null) {
            return;
        }
        int n3 = this.b--;
        switch (n2) {
            case 12: 
            case 14: {
                if (this.b >= 0) break;
                if (this.c) {
                    this.b = this.a.length - 1;
                    break;
                }
                this.b = 0;
                break;
            }
            case 13: 
            case 15: {
                ++this.b;
                if (this.b < this.a.length) break;
                if (this.c) {
                    this.b = 0;
                    break;
                }
                this.b = this.a.length - 1;
                break;
            }
            case 16: {
                this.b(this.b);
                return;
            }
        }
        if (n3 != this.b) {
            this.b(n3, this.b);
        }
    }

    public void a(int n2, int n3) {
        if (this.a == null) {
            return;
        }
        int n4 = this.b;
        int n5 = 0;
        while (n5 < this.a.length) {
            ds ds2 = this.a[n5];
            if (n2 >= ds2.i() && n2 <= ds2.i() + ds2.k() & n3 >= ds2.j() && n3 <= ds2.j() + ds2.l()) {
                this.b = n5;
                if (n4 == this.b) {
                    this.b(this.b);
                    break;
                }
                this.b(n4, this.b);
                break;
            }
            ++n5;
        }
    }

    public ds a() {
        return this.a[this.b];
    }

    public void b() {
    }

    public final void a(Graphics graphics, int n2, int n3) {
        this.b(graphics);
    }

    protected void b(Graphics graphics) {
        int n2 = 0;
        while (n2 < this.a.length) {
            this.a[n2].a(graphics);
            ++n2;
        }
    }

    protected void b(int n2) {
        if (this.d != null) {
            this.d.a(this.a[n2].toString(), this.b);
        }
    }

    protected void b(int n2, int n3) {
        this.a[n2].a(false);
        this.a[this.b].a(true);
        if (this.d != null) {
            this.d.f(n2, this.b);
        }
    }

    public void a(dy dy2) {
        this.d = dy2;
    }
}

