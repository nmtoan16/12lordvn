/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public class cv
extends aj {
    private int v;
    protected int c;
    protected int d;
    protected aj e;
    protected boolean f;
    protected int t = cx.S;
    protected boolean u;

    public void a(aj aj2, boolean bl2) {
        if (aj2 != null) {
            this.e = aj2;
            this.u = bl2;
            this.f = true;
        }
    }

    public cv() {
        this.b();
    }

    public final void b() {
        this.v = 0;
        this.c = 0;
        this.d = 5;
        this.e = null;
        this.f = false;
    }

    protected void b(Graphics graphics) {
        graphics.setColor(0xFFFFFF);
        graphics.drawRect(0, this.c_ - 20, this.b_, 19);
        int n2 = this.c_ - 20;
        graphics.setColor(15484506);
        graphics.fillRect(0, n2 + 1, this.c * this.b_ / 100, 18);
        bz.c.a(graphics, "Vui l\u00f2ng ch\u1edd...", this.b_ / 2, n2 + 3, 1);
    }

    public void a() {
        if (this.c == 100) {
            if (this.e != null) {
                cz.a().a(this.e, false, this.u);
                this.e = null;
            }
            return;
        }
        if (this.f) {
            this.d += 3;
        }
        this.c += this.d;
        if (this.c == 95) {
            this.c = 90;
        }
        if (this.c > 100) {
            this.c = 100;
        }
    }

    protected void e(Graphics graphics) {
        graphics.setColor(this.t);
        graphics.fillRect(0, 0, this.b_, this.c_);
    }

    public final void a(Graphics graphics) {
        if (this.v <= 2) {
            this.e(graphics);
            ++this.v;
        }
        this.b(graphics);
    }
}

