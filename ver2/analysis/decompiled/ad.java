/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import com.mic.smsgame.suquan.t;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class ad
extends ae {
    private Image e;
    private Image f;
    private av t;
    private c u;
    private int v;
    private int w;
    private Image x;
    private Image y;
    private Image z;
    private int A;
    private int B;
    private t C;
    private int D;
    private int E;

    public ad(c c2, int n2, int n3) {
        super(1);
        this.u = c2;
        this.v = n2;
        this.w = n3;
        this.t = new av();
        this.C = new t();
        this.e = az.b("/gold");
        this.f = az.b("/m/isw");
        this.y = az.b("/m/bl");
        this.z = az.b("/f/divide");
        this.x = az.b("/m/lv");
        this.A = this.y.getWidth();
        this.B = this.y.getHeight() / 2;
        this.b_ = 170;
        this.c_ = 80;
        this.g = cx.f - this.b_ >> 1;
        this.a_ = cx.g - this.c_ >> 1;
        this.D = dd.u(c2.e + 1);
        this.E = dd.u(c2.e);
    }

    private void a(Graphics graphics, int n2, int n3) {
        graphics.setColor(0xFFFFFF);
        graphics.fillRect(n2 + 2, n3 + 2, 34, 34);
        u.a(graphics, this.u.a, n2 + 4, n3 + 4, true);
        bz.c.a(graphics, u.a(this.u.a), n2 + 40, n3 + 0, 0);
        y.a(graphics, this.y, 0, this.B, this.A, this.B, n2 + 40, n3 + 15, 0);
        y.a(graphics, this.y, 0, 0, this.A * this.u.c / this.u.b, this.B, n2 + 40, n3 + 15, 0);
        this.C.a(graphics, this.u.c, n2 + 92, n3 + 16, 2);
        this.C.a(graphics, this.u.b, n2 + 98, n3 + 16, 0);
        graphics.drawImage(this.z, n2 + 93, n3 + 14, 0);
        y.a(graphics, this.x, 0, this.B, this.A, this.B, n2 + 40, n3 + 28, 0);
        y.a(graphics, this.x, 0, 0, this.A * (this.v - this.E) / (this.D - this.E), this.B, n2 + 40, n3 + 28, 0);
        this.C.a(graphics, this.v, n2 + 92, n3 + 29, 2);
        this.C.a(graphics, this.D, n2 + 98, n3 + 29, 0);
        graphics.drawImage(this.z, n2 + 93, n3 + 27, 0);
        graphics.drawImage(this.e, n2 + 132, n3 + 36, 0);
        this.t.a(graphics, this.w, n2 + 130 - this.t.a(this.w), n3 + 41);
        graphics.drawImage(this.f, n2 + 2, n3 + 39, 0);
        this.t.a(graphics, this.u.d, n2 + 17, n3 + 41);
        bz.d.a(graphics, " " + this.u.e, n2 + 151, n3 + 0, 2);
    }

    private void b(Graphics graphics, int n2, int n3) {
    }

    public void a(Graphics graphics) {
        cz.a(graphics, cx.P, cx.Q, this.g, this.a_, this.b_, this.c_);
        bz.c.a(graphics, "Th\u00f4ng tin", cx.f >> 1, this.a_ + 5, 1);
        this.a(graphics, this.g + 9, this.a_ + 20);
        this.b(graphics, this.g + 9, this.a_ + 20 + 60);
    }
}

