/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Image;

public class do
extends ab {
    private int v;
    private int w;

    public do(Image image, int n2) {
        this.a(image, n2);
        this.t = 3;
    }

    public void a(int n2, int n3, int n4, int n5, int n6) {
        this.d(n2, n3);
        this.u = true;
        this.w = 0;
    }

    public void a_(int n2) {
        super.a_(n2);
        if (n2 == 0) {
            this.h = true;
        } else {
            this.h = false;
            this.g = 0;
        }
    }

    public void d(int n2) {
        this.v = n2;
    }

    public void a() {
        if (this.v > 0) {
            --this.v;
            if (this.v == 0) {
                this.u = false;
            }
        }
        if (this.w > 0) {
            --this.w;
            if (this.w == 0) {
                this.a_(1);
            }
        }
        if (1 == this.c && this.g >= this.d[1].length - 1) {
            this.a_(0);
        }
    }

    public void e(int n2) {
        this.w = n2;
    }
}

