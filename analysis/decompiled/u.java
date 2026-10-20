/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class u {
    private Image a = az.b("/avatar");
    private static u b = null;
    private String c = null;

    private u() {
    }

    public static final u a() {
        if (b == null) {
            b = new u();
        }
        return b;
    }

    public static final void a(u u2) {
        b = u2;
        if (u2 == null) {
            System.gc();
        }
    }

    private String b(int n2) {
        String string = ea.a[n2];
        if (string.equals("PLAYER")) {
            if (this.c == null) {
                this.c = dd.c();
            }
            string = this.c;
        }
        return string;
    }

    public static final String a(int n2) {
        return u.a().b(n2);
    }

    public static final void a(Graphics graphics, int n2, int n3, int n4, boolean bl2) {
        u.a().b(graphics, n2, n3, n4, bl2);
    }

    private void b(Graphics graphics, int n2, int n3, int n4, boolean bl2) {
        if (!bl2) {
            y.a(graphics, this.a, n2 % 10 * 30, n2 / 10 * 30, 30, 30, n3, n4, 0);
        } else {
            graphics.drawRegion(this.a, n2 % 10 * 30, n2 / 10 * 30, 30, 30, 2, n3, n4, 0);
        }
    }
}

