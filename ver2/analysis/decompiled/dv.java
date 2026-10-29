/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public class dv
extends cd {
    protected int[] e;
    protected boolean f;
    protected boolean g = true;
    protected dl h;
    protected dl i = new dl();
    protected int j;
    protected int k;
    private boolean n;
    private boolean o;
    protected int[] l;
    protected int m;

    public boolean c() {
        return this.n;
    }

    public dv() {
        this(false);
    }

    public dv(boolean bl2) {
        this(bl2, null);
    }

    public dv(boolean bl2, dl dl2) {
        int[] nArray = new int[8];
        nArray[1] = -1;
        nArray[2] = 1;
        nArray[3] = -2;
        nArray[4] = 2;
        nArray[5] = -3;
        nArray[6] = 3;
        this.l = nArray;
        this.f = bl2;
        if (bl2) {
            if (dl2 == null) {
                dl2 = new dl(0, 0, cx.f, cx.g);
            }
            this.h = dl2;
        }
    }

    public void a(ds[] dsArray, int n2) {
        super.a(dsArray, n2);
        if (this.f) {
            this.d();
        }
    }

    public void d() {
        this.n = true;
        this.o = true;
        this.e = new int[this.a.length];
        int n2 = 0;
        while (n2 < this.e.length) {
            this.e[n2] = this.a[n2].i();
            System.out.println("xStop=" + this.e[n2]);
            ++n2;
        }
        n2 = 0;
        int n3 = Integer.MAX_VALUE;
        int n4 = 0;
        while (n4 < this.a.length) {
            if (this.a[n4].k() > n2) {
                n2 = this.a[n4].k();
            }
            if (this.a[n4].i() < n3) {
                n3 = this.a[n4].i();
            }
            ++n4;
        }
        n4 = this.h.a + this.h.c - n3;
        if (n3 + n2 - n4 > this.h.a) {
            n4 += n3 + n2 - n4 - this.h.a;
        }
        this.j = n3 - n4;
        this.k = n3 + n4;
        boolean bl2 = true;
        int n5 = 0;
        while (n5 < this.a.length) {
            if (bl2) {
                this.a[n5].f(this.j);
            } else {
                this.a[n5].f(this.k);
            }
            bl2 = !bl2;
            ++n5;
        }
    }

    protected void b(int n2, int n3) {
        if (this.g) {
            this.m = 0;
        }
        super.b(n2, n3);
    }

    public void a(int n2) {
        if (this.n) {
            return;
        }
        super.a(n2);
    }

    public void a(int n2, int n3) {
        if (this.n) {
            return;
        }
        super.a(n2, n3);
    }

    public void b() {
        if (this.n) {
            int n2 = 0;
            int n3 = 0;
            while (n3 < this.a.length) {
                int n4 = this.a[n3].i();
                int n5 = this.e[n3] - n4;
                if (Math.abs(n5) < 2) {
                    n4 += n5;
                    ++n2;
                } else {
                    n4 += n5 / 2;
                }
                this.a[n3].f(n4);
                ++n3;
            }
            if (n2 >= this.a.length) {
                this.n = false;
                if (!this.o) {
                    super.b(this.b);
                }
            }
        }
        if (this.m < this.l.length - 1) {
            ++this.m;
        }
    }

    protected void b(Graphics graphics) {
        y.a(graphics, this.i);
        y.a(graphics, this.i, this.h.a, this.h.b, this.h.c, this.h.d);
        if (this.n) {
            super.b(graphics);
        } else {
            int n2 = 0;
            while (n2 < this.a.length) {
                if (this.b != n2) {
                    this.a[n2].a(graphics);
                } else {
                    this.a[n2].a(graphics, this.l[this.m], 0);
                }
                ++n2;
            }
        }
        y.b(graphics, this.i);
    }
}

