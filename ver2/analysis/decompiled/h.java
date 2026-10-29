/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public class h
extends ds {
    private int a;
    private int b;

    public h() {
        this(cx.S);
    }

    public h(int n2) {
        this.a(n2);
    }

    public void a(int n2) {
        this.a = n2 & 0xFFFFFF;
        int n3 = (n2 & 0xFF0000) >> 16;
        int n4 = (n2 & 0xFF00) >> 8;
        int n5 = n2 & 0xFF;
        int n6 = 6;
        this.b = (255 - n3) / n6 + n3 << 16 | (255 - n4) / n6 + n4 << 8 | (255 - n5) / n6 + n5;
    }

    public void a(Graphics graphics, int n2, int n3) {
        cz.b(graphics, this.a, this.b, 0, cx.g - 17, cx.f, 17);
    }
}

