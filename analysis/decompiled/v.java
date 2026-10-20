/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import com.mg.smsgame.main.b;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class v
extends aj
implements b {
    private int c = 1;
    private int d = 2;
    private int e = 3;
    private int f;
    private de t;
    private de u;
    private Image v;
    private int w;

    public v() {
        this(2);
    }

    public v(int n2) {
        this.a((h)null);
        byte[] byArray = bg.o();
        cx.y = byArray == null ? 80 : ai.a(byArray);
        this.f = cx.y;
        this.v = az.a("/_sound");
        this.c(new da("Ch\u1ecdn", this.c));
        this.t = new da("M\u1edf", this.e);
        this.u = new da("T\u1eaft", this.d);
        this.w = n2;
        if (this.f <= 0) {
            this.b(this.t);
        } else {
            this.b(this.u);
        }
        this.a(this);
    }

    public void a(int n2) {
        super.a(n2);
        if (n2 == 14 || n2 == 4 || n2 == 13 || n2 == 8) {
            this.f -= 5;
            if (this.f <= 0) {
                this.f = 0;
                this.b(this.t);
            }
        } else if (n2 == 15 || n2 == 6 || n2 == 12 || n2 == 2) {
            if (this.f <= 0) {
                this.b(this.u);
            }
            this.f += 5;
            if (this.f > 100) {
                this.f = 100;
            }
        }
    }

    public void a(int n2, int n3) {
        super.a(n2, n3);
        int n4 = this.c_ / 2 + this.v.getHeight() / 2 + 5;
        int n5 = n4 + 18;
        int n6 = (this.b_ - 100) / 2 - 10;
        int n7 = n6 + 10;
        if (n3 > n4 && n3 < n5) {
            if (n2 > n6 && n2 < n7) {
                cx.u[14] = 2;
            } else if (n2 > n6 + 113 && n2 < n7 + 113) {
                cx.u[15] = 2;
            } else if (n2 >= n7 && n2 <= n6 + 113) {
                this.f = n2 - n7;
                if (this.f > 100) {
                    this.f = 100;
                }
                if (this.f < 0) {
                    this.f = 0;
                }
                if (this.f <= 0) {
                    this.b(this.t);
                } else {
                    this.b(this.u);
                }
            }
        }
    }

    public void a() {
    }

    public void a(Graphics graphics) {
        graphics.setColor(0);
        graphics.fillRect(0, 0, this.b_, this.c_);
        int n2 = this.c_ / 2 + this.v.getHeight() / 2 + 5;
        int n3 = (this.b_ - 100) / 2;
        bz.c.a(graphics, "-", n3 - 2, n2, 2);
        bz.c.a(graphics, "+", n3 + 103, n2, 0);
        graphics.setColor(255);
        graphics.drawRect(n3 - 1, n2, 101, 10);
        if (this.f > 0) {
            y.a(graphics, this.v, 0, 0, 100, 100, this.b_ / 2, this.c_ / 2 - 5, 3);
            graphics.setColor(6998052);
            graphics.fillRect(n3, n2 + 1, this.f, 9);
        } else {
            y.a(graphics, this.v, 100, 0, 100, 100, this.b_ / 2, this.c_ / 2 - 5, 3);
        }
    }

    public void b(int n2, int n3) {
        if (n3 == this.c) {
            if (cx.y != this.f) {
                bg.d(this.f);
            }
            cx.x = (cx.y = this.f) > 0;
            this.b();
        } else if (n3 == this.d) {
            this.f = 0;
            this.b(this.t);
        } else if (n3 == this.e) {
            this.f = 80;
            this.b(this.u);
        }
    }

    public void b() {
        cz.d().e.a(1, this.w, false);
    }
}

