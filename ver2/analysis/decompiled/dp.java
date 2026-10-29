/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public class dp
extends aj {
    private String[] t;
    private int u;
    private int v;
    private String w;
    private int x = 0;
    private int y = 2;
    private boolean z = false;
    private int A = 20;
    private int B = 0;
    protected int c = 6;
    protected int d = cx.P;
    protected int e;
    protected int f;

    public dp(String string, String string2, int n2) {
        this.w = string;
        this.a(string2);
        this.B = n2;
        this.b();
    }

    private void b() {
        if (this.B == 0) {
            this.u = 0;
            this.v = cx.g - 17;
        } else {
            int n2 = this.t.length;
            int n3 = (n2 + 2) * bz.d.a();
            this.u = (this.c_ - 17 - n3) / 2;
            this.v = (this.c_ - 17 + n3) / 2 + bz.d.a();
            if (this.u < 0) {
                this.u = 0;
            }
            if (this.v > cx.g - 17) {
                this.v = cx.g - 17;
            }
        }
        this.g = 0;
        this.a_ = this.u;
        this.e = cx.f;
        this.f = this.v - this.u;
        if (this.B != 0) {
            this.b_ = this.e;
            this.c_ = this.f;
        }
    }

    protected void b(Graphics graphics) {
        cz.a(graphics, cx.P, cx.Q, this.g, this.a_, this.e, this.f);
    }

    protected void e(Graphics graphics) {
        this.c = 6;
        if (this.w != null && this.w.length() > 0) {
            bz.c.a(graphics, this.w, this.b_ / 2, this.u + bz.c.a() - 5, 1);
            bz.a(bz.d);
            graphics.setClip(this.c, this.u + bz.d.a() * 2 - 5, this.e - this.c * 2, this.f - 2 * bz.d.a());
            bz.a(graphics, bz.d, this.t, this.c, this.u + bz.d.a() * 2 - 5 + this.x, this.e - this.c * 2, this.f - 2 * bz.d.a() + 5 - this.x, 1);
        } else {
            graphics.setClip(this.c, this.u + bz.d.a() - 5, this.e - this.c * 2, this.f - bz.d.a());
            bz.a(graphics, bz.d, this.t, this.c, this.u + bz.d.a() - 5 + this.x, this.e - this.c * 2, this.f - bz.d.a() + 5 - this.x, 1);
        }
        graphics.setClip(0, 0, cx.f, cx.g);
    }

    public void a() {
        if (this.A == 0 && this.z) {
            this.x -= this.y / 2;
        }
        if (this.A > 0) {
            --this.A;
        }
    }

    public void a(String string) {
        this.t = bz.a(string, this.b_ - this.c * 2);
    }

    public void a(int n2) {
        if (n2 == 12) {
            this.A = 20;
            if (this.x < 0) {
                this.x += this.y;
            }
        } else if (n2 == 13) {
            this.A = 20;
            if (this.z) {
                this.x -= this.y;
            }
        }
        super.a(n2);
    }

    public void a(int n2, int n3) {
        this.A = 20;
        super.a(n2, n3);
    }

    public void c(int n2) {
        this.A = 20;
        if (n2 > 0) {
            if (this.x < 0) {
                this.x += this.y * n2;
                if (this.x > 0) {
                    this.x = 0;
                }
            }
        } else if (this.z) {
            this.x -= -n2 * this.y;
        }
    }

    public void r() {
        this.w = null;
        this.t = null;
        super.r();
    }

    public void a(Graphics graphics) {
        this.b(graphics);
        this.e(graphics);
    }
}

