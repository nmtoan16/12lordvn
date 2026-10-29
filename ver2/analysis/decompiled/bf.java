/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class bf
extends ab {
    private int v;
    private boolean w;

    public bf(Image image, int n2) {
        this.a(image, n2);
        this.b(false);
        this.u = false;
        this.t = 3;
    }

    public void a(int n2, int n3, int n4, int n5, int n6) {
        this.d(n2, n3);
        this.c(n4, n5);
        this.v = n6;
        this.a_(0);
        this.u = true;
        this.h = false;
        this.w = false;
    }

    public void c() {
        this.w = true;
    }

    public void a() {
        if (this.v > 0) {
            --this.v;
            return;
        }
        if (this.c == 0) {
            if (this.w && this.g >= this.d[0].length - 1) {
                this.a_(1);
            }
        } else if (this.c == 1 && this.g >= this.d[1].length - 1) {
            this.u = false;
        }
    }

    public void a(Graphics graphics) {
        if (this.v > 0) {
            return;
        }
        super.a(graphics);
    }
}

