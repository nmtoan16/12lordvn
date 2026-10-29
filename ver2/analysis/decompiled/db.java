/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public class db
extends aq {
    private boolean g;

    public void a() {
        super.a();
        if (this.e % 3 == 0) {
            this.g = !this.g;
        }
    }

    public void a(Graphics graphics, int n2, int n3) {
        if (!this.c) {
            return;
        }
        if (this.g) {
            bz.c.a(graphics, this.d, n2 + this.a, n3 + this.b, 1);
        }
    }
}

