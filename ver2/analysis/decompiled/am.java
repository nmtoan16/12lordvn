/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Image;

public class am
extends ab {
    public am(Image image, int n2) {
        this.a(image, n2);
        this.b(false);
        this.u = true;
        this.t = 3;
    }

    public void a(int n2, int n3, int n4, int n5, int n6) {
        this.d(n2, n3);
        this.c(n4, n5);
        this.u = true;
        this.k = this.b(n2, n4, 20);
        this.l = this.b(n3, n5, 20);
        this.g = 0;
    }

    public void a() {
        this.c(this.i, this.j, this.k, this.l);
        if (this.g >= this.d[0].length - 1) {
            this.u = false;
        }
    }
}

