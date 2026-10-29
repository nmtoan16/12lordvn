/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class bi
extends bw {
    public void a() {
        this.f = this.c / 14 + 1;
        this.h = new int[this.f];
    }

    public void a(Graphics graphics, Image image) {
        int n2 = 0;
        int n3 = 0;
        while (n3 < this.f) {
            block6: {
                int n4;
                int n5;
                block5: {
                    if (this.h[n3] <= 0) break;
                    n5 = this.h[n3];
                    n4 = n2;
                    if (n3 != this.f - 1) break block5;
                    if (n4 >= this.c) break block6;
                    if (n4 + n5 > this.c) {
                        n5 = this.c - n4;
                    }
                }
                y.a(graphics, image, n4, 0, n5, this.d, this.a + n4, this.b, 20);
                n2 += 14;
            }
            ++n3;
        }
    }
}

