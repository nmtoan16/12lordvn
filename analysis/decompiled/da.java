/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public class da
extends de {
    private String f;
    private int g = 0xFFFFFF;

    public da(String string, int n2) {
        super(n2);
        this.f = string;
        this.d = bz.c.a(string);
    }

    public void a(Graphics graphics) {
        if (this.a > 0) {
            bz.c.a(graphics, this.f, this.b, this.c + 1, 0);
        } else {
            bz.d.a(graphics, this.f, this.b, this.c, 0);
        }
    }
}

