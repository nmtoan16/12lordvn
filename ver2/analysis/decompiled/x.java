/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class x
extends e {
    protected Image d;

    public x(Image image) {
        this.d = image;
        if (this.d != null) {
            this.a = this.d.getWidth();
            this.b = this.d.getHeight();
        }
    }

    public void a(dl dl2) {
        super.a(dl2);
        if (this.b > dl2.d) {
            this.b = dl2.d;
        }
        if (this.a > dl2.c) {
            this.a = dl2.c;
        }
    }

    public void a(Graphics graphics, int n2, int n3) {
        y.a(graphics, this.d, 0, 0, this.a, this.b, n2 + this.c.c / 2, n3, 17);
    }

    public void b() {
    }
}

