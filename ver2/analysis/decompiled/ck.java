/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import com.mg.smsgame.main.b;
import com.mic.smsgame.suquan.h;
import com.mic.smsgame.suquan.t;
import com.mic.smsgame.suquan.u;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class ck
extends ae
implements b {
    private Image[] e;
    private Image f;
    private Image t;
    private Image u;
    private dy v;
    private int w;
    private String[] x;
    private String[] y;
    private int z;
    private di A;
    private int B;
    private t C;

    public ck() {
        super(1);
        this.u();
        this.A = new di();
        this.b_ = this.e[0].getWidth();
        this.c_ = 180;
        this.g = cx.f - 176 >> 1;
        this.a_ = cx.g - 180 >> 1;
        this.a_ -= 25;
        this.w = 0;
        this.x = h.d;
        this.C = new t(bs.a().B);
        this.a(this);
    }

    private void u() {
        this.e = bs.a().k;
        this.f = bs.a().z;
        this.t = bs.a().A;
        this.u = bs.a().E;
    }

    protected void e() {
        this.z = this.z < 1 ? 8 : --this.z;
    }

    public void a(dx[] dxArray, dy dy2) {
        this.v = dy2;
        this.A.a();
        this.m();
        this.w = 0;
        int n2 = 0;
        while (n2 < dxArray.length) {
            if (dxArray[n2].b > 0 && dxArray[n2].a != 2) {
                this.A.a(dxArray[n2]);
            }
            ++n2;
        }
        this.B = (this.b_ - 10 - this.A.b() * 36) / (this.A.b() + 1);
        if (this.A.b() > 0) {
            dx dx2 = (dx)this.A.b(this.w);
            this.y = bz.a(this.x[dx2.a], this.b_ - 15);
        } else {
            this.y = bz.a("Hi\u1ec7n t\u1ea1i kh\u00f4ng c\u00f3 d\u1ee5ng c\u1ee5 h\u1ed7 tr\u1ee3 n\u00e0o.", this.b_ - 15);
        }
        this.b(new da("\u0110\u00f3ng", -2));
        this.c = true;
        if (this.A.b() > 0) {
            dx dx3 = (dx)this.A.b(this.w);
            this.y = bz.a(this.x[dx3.a], this.b_ - 15);
        }
        if (this.A.b() > 0) {
            this.a(new da("D\u00f9ng", -1));
            this.c(new da(" ", -1));
        } else {
            this.a((de)null);
            this.c((de)null);
        }
        this.c = true;
        cz.d().a(this, false);
    }

    public void a(int n2) {
        if (this.A.b() < 1) {
            return;
        }
        int n3 = this.w;
        switch (n2) {
            case 14: {
                this.w = (this.w - 1 + this.A.b()) % this.A.b();
                break;
            }
            case 15: {
                this.w = (this.w + 1) % this.A.b();
                break;
            }
            case 12: {
                this.w -= 4;
                if (this.w >= 0) break;
                this.w = n3;
                break;
            }
            case 13: {
                this.w += 4;
                if (this.w < this.A.b()) break;
                this.w = n3;
            }
        }
        if (n3 != this.w) {
            this.v();
        }
    }

    private void v() {
        dx dx2 = (dx)this.A.b(this.w);
        this.y = bz.a(this.x[dx2.a], this.b_ - 15);
    }

    public void a(int n2, int n3) {
        if (this.A.b() < 1) {
            return;
        }
        int n4 = this.g + this.B + 6;
        int n5 = this.a_ + 13;
        if (n3 >= n5 && n3 <= n5 + 36) {
            int n6 = 0;
            while (n6 < this.A.b()) {
                if (n2 >= n4 && n2 <= n4 + 36 && n6 != this.w) {
                    this.w = n6;
                    this.v();
                }
                n4 += 36 + this.B;
                ++n6;
            }
        }
    }

    public void a(Graphics graphics) {
        int n2 = this.g;
        int n3 = this.a_;
        com.mic.smsgame.suquan.u.a(graphics, n2, n3, 180, 7814005, this.e);
        int n4 = this.g;
        int n5 = this.a_ + 13;
        int n6 = 0;
        int n7 = this.A.b() / 4;
        n7 += this.A.b() % 4 != 0 ? 1 : 0;
        int n8 = 0;
        while (n8 < n7) {
            n4 = this.g + 6 + this.B;
            int n9 = 0;
            while (n9 < 4) {
                if (n6 >= this.A.b()) break;
                dx dx2 = (dx)this.A.b(n6);
                if (this.w == n6) {
                    y.a(graphics, this.t, 0, 0, 36, 36, n4, n5, 0);
                } else {
                    y.a(graphics, this.t, 36, 0, 36, 36, n4, n5, 0);
                }
                y.a(graphics, this.u, dx2.a * 28, 0, 28, 28, n4 + 4, n5 + 4, 0);
                this.C.a(graphics, dx2.b, n4 + 34, n5 + 29, 2);
                n4 += 36 + this.B;
                ++n6;
                ++n9;
            }
            n5 += 38;
            ++n8;
        }
        if (this.A.b() > 0) {
            graphics.drawImage(this.f, this.g + this.e[0].getWidth() / 2, n5, 17);
            dx dx3 = (dx)this.A.b(this.w);
            bz.c.a(graphics, h.c[dx3.a], this.g + this.e[0].getWidth() / 2, n5 + 8, 1);
            n5 += 15;
        } else {
            n5 += 45;
        }
        bz.a(graphics, bz.d, this.y, this.g + 11, n5 + 15, this.b_, 120, 0);
    }

    public void b(int n2, int n3) {
        dx dx2 = (dx)this.A.b(this.w);
        switch (n3) {
            case -1: {
                dx2.a();
                bg.g(dx2.a);
                this.v.a("item", dx2.a);
            }
            case -2: {
                cz.d().a(false);
            }
        }
    }
}

