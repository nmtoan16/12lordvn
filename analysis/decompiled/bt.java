/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class bt
extends ab {
    private int v;

    public bt(Image image, int n2) {
        this.a(image, n2);
    }

    public void a(int n2, int n3, int n4, int n5, int n6) {
        this.d(n2, n3);
        this.c(n4, n5);
        this.k = this.b(n2, n4, 35);
        this.l = this.b(n3, n5, 35);
        this.v = n6;
        this.u = true;
        this.a_(0);
        this.t = 33;
        this.m = n2 > n4 ? 2 : 0;
    }

    public void a() {
        if (this.v > 0) {
            --this.v;
            return;
        }
        switch (this.c) {
            case 0: {
                if (this.c(this.i, this.j, this.k, this.l)) {
                    cz.a().c(10);
                    this.a_(1);
                    this.t = 3;
                    break;
                }
                ++this.l;
                ++this.k;
                break;
            }
            case 1: {
                if (this.g < this.d[1].length - 1) break;
                this.u = false;
            }
        }
    }

    public void a(Graphics graphics, int n2, int n3) {
        if (this.v < 1) {
            super.a(graphics, n2, n3);
        }
    }
}

