/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class dq
extends cw {
    protected int m = 0;
    protected Image n;
    protected int o;

    public dq() {
    }

    public dq(int n2, int n3, Image image, int n4) {
        this(n2, n3);
        this.a(image, n4);
    }

    public dq(Image image, int n2) {
        this.a(image, n2);
    }

    public dq(int n2, int n3) {
        this.d(n2, n3);
    }

    public void a(Image image, int n2) {
        this.o = n2;
        this.g = -1;
        if (image != null) {
            this.r = image.getWidth() / this.o;
            this.s = image.getHeight();
            this.n = image;
        }
    }

    public void a(Graphics graphics, int n2, int n3) {
        if (!this.u) {
            return;
        }
        if (this.n != null && this.g > -1 && this.d[this.c][this.g] > -1) {
            graphics.drawRegion(this.n, this.d[this.c][this.g] * this.r, 0, this.r, this.s, this.m, this.p + n2, this.q + n3, this.t);
        }
    }

    public void a() {
    }
}

