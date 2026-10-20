/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class aw
extends dz {
    private Image a;
    private int b;
    private int c;
    private int d;

    public aw(Image image, int n2, int n3, int n4) {
        this.a = image;
        this.b = n2;
        this.c = n3;
        this.d = n4;
    }

    public void a(Graphics graphics) {
        if (this.a != null) {
            graphics.drawImage(this.a, this.b, this.c, this.d);
        }
    }

    public void a() {
    }
}

