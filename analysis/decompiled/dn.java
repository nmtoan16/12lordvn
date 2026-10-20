/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class dn
extends ds {
    Image a;
    int b;
    private int c;
    private int d;

    public dn(Image image) {
        this.u = false;
        this.a = image;
        this.r = image.getWidth() / 6;
        this.s = image.getHeight();
    }

    public void a(int n2, int n3, int n4) {
        this.p = n3;
        this.q = n4;
        this.u = true;
        this.b = 0;
        this.c = n2;
        this.d = 5;
    }

    public void a(Graphics graphics, int n2, int n3) {
        if (!this.u) {
            return;
        }
        if (this.d > 0) {
            return;
        }
        if (this.b > 15) {
            if (this.b % 2 == 0) {
                y.a(graphics, this.a, this.c * this.r, 0, this.r, this.s, this.p, this.q - 7, 3);
            }
        } else {
            y.a(graphics, this.a, this.c * this.r, 0, this.r, this.s, this.p, this.q - (this.b >> 1), 3);
        }
    }

    public void a() {
        if (!this.u) {
            return;
        }
        if (this.d > 0) {
            --this.d;
            return;
        }
        ++this.b;
        if (this.b > 24) {
            this.u = false;
        }
    }
}

