/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class s
extends ds {
    private Image a = az.b("/m/fsw");
    private Image b = az.b("/crystalblue");
    private int c;
    private int d = this.b.getWidth() / 3;
    private int e = this.b.getHeight();
    private int f;
    private int g;
    private int h;
    private boolean i;
    private boolean j;

    public void a() {
        this.g = 15;
        this.h = 0;
        this.j = false;
        this.i = false;
        this.c = 0;
    }

    public void b() {
        if (this.i) {
            return;
        }
        ++this.c;
        if (this.c % 2 == 0) {
            return;
        }
        if (this.g > 0) {
            this.g -= 3;
        }
        if (!this.j) {
            ++this.f;
            if (this.f >= 3) {
                this.f = 3;
                this.j = true;
            }
        } else {
            this.h += 5;
            --this.f;
            if (this.f < 0) {
                this.f = 0;
                this.i = true;
            }
        }
    }

    public boolean c() {
        return this.i;
    }

    public void a(Graphics graphics, int n2, int n3) {
        if (this.i) {
            return;
        }
        graphics.drawImage(this.a, n2 + this.p + this.g, n3 + this.q + this.g, 3);
        graphics.drawRegion(this.a, 0, 0, 24, 24, 2, n2 + this.p - this.g, n3 + this.q + this.g, 3);
        if (!this.j) {
            y.a(graphics, this.b, this.d * this.f, 0, this.d, this.e, n2 + this.p, n3 + this.q, 3);
        } else {
            y.a(graphics, this.b, this.d * this.f, 0, this.d, this.e, n2 + this.p + this.h, n3 + this.q, 3);
            y.a(graphics, this.b, this.d * this.f, 0, this.d, this.e, n2 + this.p, n3 + this.q + this.h, 3);
            y.a(graphics, this.b, this.d * this.f, 0, this.d, this.e, n2 + this.p - this.h, n3 + this.q, 3);
            y.a(graphics, this.b, this.d * this.f, 0, this.d, this.e, n2 + this.p, n3 + this.q - this.h, 3);
            y.a(graphics, this.b, this.d * this.f, 0, this.d, this.e, n2 + this.p + this.h, n3 + this.q + this.h, 3);
            y.a(graphics, this.b, this.d * this.f, 0, this.d, this.e, n2 + this.p + this.h, n3 + this.q - this.h, 3);
            y.a(graphics, this.b, this.d * this.f, 0, this.d, this.e, n2 + this.p - this.h, n3 + this.q + this.h, 3);
            y.a(graphics, this.b, this.d * this.f, 0, this.d, this.e, n2 + this.p - this.h, n3 + this.q - this.h, 3);
        }
    }
}

