/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class bj
extends ds {
    protected Image[] a;
    protected int b;

    public bj(Image[] imageArray) {
        this.a = imageArray;
        if (imageArray != null) {
            this.r = imageArray[0].getWidth();
            this.s = imageArray[0].getHeight();
        }
    }

    public void a(Graphics graphics, int n2, int n3) {
        graphics.drawImage(this.a[this.b], n2 + this.p, n3 + this.q, this.t);
    }

    public void a(boolean bl2) {
        super.a(bl2);
        this.b = (this.b + 1) % this.a.length;
    }
}

