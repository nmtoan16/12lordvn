/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class g
extends ds {
    private Image a;
    private int b;
    private int c;
    private int d;

    public g() {
        this.a = bs.a().j[0];
    }

    public void a(int n2, int n3, int n4) {
        this.d(n2, n3);
        this.b = -1;
        this.c = n4;
    }

    public void a() {
        this.b = 0;
    }

    public void a(Graphics graphics, int n2, int n3) {
        if (this.b != 0) {
            int n4 = 8;
            if (this.d >= 3) {
                ++n4;
            }
            if ((this.c & 1) == 1) {
                graphics.drawRegion(this.a, 0, 0, this.a.getWidth(), this.a.getHeight(), 6, this.p - n4, this.q, 10);
            }
            if ((this.c & 2) == 2) {
                graphics.drawRegion(this.a, 0, 0, this.a.getWidth(), this.a.getHeight(), 5, this.p + n4, this.q, 6);
            }
            if ((this.c & 4) == 4) {
                graphics.drawRegion(this.a, 0, 0, this.a.getWidth(), this.a.getHeight(), 0, this.p, this.q - n4, 33);
            }
            if ((this.c & 8) == 8) {
                graphics.drawRegion(this.a, 0, 0, this.a.getWidth(), this.a.getHeight(), 3, this.p, this.q + n4, 17);
            }
        }
    }

    public void b() {
        if (this.b != 0) {
            if (this.b > 0) {
                --this.b;
            }
            ++this.d;
            if (this.d > 6) {
                this.d = 0;
            }
        }
    }
}

