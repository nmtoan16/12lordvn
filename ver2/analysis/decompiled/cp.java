/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class cp
extends e {
    private dq d;

    public cp(Image image, int n2) {
        this.d = new dq(0, 0, image, n2);
    }

    public void a(byte[][] byArray) {
        if (this.d != null) {
            this.d.a(byArray);
        }
    }

    public void a(int n2) {
        if (this.d != null) {
            this.d.c(n2);
        }
    }

    public void a(boolean bl2) {
        if (this.d != null) {
            this.d.b(bl2);
        }
    }

    public void b() {
        if (this.d != null) {
            this.d.e();
        }
    }

    public void b(int n2) {
        if (this.d != null) {
            this.d.a_(n2);
        }
    }

    public int a() {
        return this.d.l();
    }

    public void a(Graphics graphics, int n2, int n3) {
        if (this.d != null) {
            this.d.a(graphics, n2 + (this.c.c - this.d.k()) / 2, n3);
        }
    }
}

