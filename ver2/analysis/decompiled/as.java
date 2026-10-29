/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Font
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

public class as
extends br {
    Font a = Font.getDefaultFont();

    public int a() {
        return this.a.getHeight();
    }

    public int a(String string) {
        return this.a.stringWidth(string);
    }

    public void a(Graphics graphics, String string, int n2, int n3, int n4) {
        if (n4 == 0) {
            graphics.drawString(string, n2, n3, 0);
        } else if (n4 == 2) {
            graphics.drawString(string, n2, n3, 24);
        } else if (n4 == 1) {
            graphics.drawString(string, n2, n3, 17);
        }
    }

    public int a(char c2) {
        return this.a.charWidth(c2);
    }
}

