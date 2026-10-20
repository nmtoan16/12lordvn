/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class cb
extends ds {
    private Image a;
    private int b;
    private int c;

    public cb(Image image, int n2, int n3) {
        this.p = n2;
        this.q = n3;
        this.r = image.getWidth();
        this.s = image.getHeight();
        this.a = image;
    }

    public void a(Graphics graphics, int n2, int n3) {
        graphics.drawImage(this.a, this.p, this.q + this.b, this.c);
    }

    public void a() {
    }
}

