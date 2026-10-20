/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class j
extends bw {
    public void a() {
        this.f = this.d / 14 + 1;
        this.h = new int[this.f];
    }

    public void a(Graphics graphics, Image image) {
        int n2 = this.d;
        int n3 = 0;
        while (n3 < this.f) {
            block6: {
                int n4;
                int n5;
                block5: {
                    if (this.h[n3] <= 0) break;
                    n5 = this.h[n3];
                    n4 = n2 - n5;
                    if (n3 != this.f - 1) break block5;
                    if (n4 + n5 <= 0) break block6;
                    if (n4 < 0) {
                        n5 = n4 + n5;
                        n4 = 0;
                    }
                }
                y.a(graphics, image, 0, n4, this.c, n5, this.a, this.b + n4, 20);
                n2 -= 14;
            }
            ++n3;
        }
    }
}

