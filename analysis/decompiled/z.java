/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public class z
extends by {
    private boolean j;

    public void a(int n2, int n3, int n4, int n5, int n6, int n7) {
        if (n7 < 18) {
            n7 = 18;
        }
        if (n6 < 18) {
            n6 = 18;
        }
        this.a = n2 + n6 / 2;
        this.b = n3 + n7 / 2;
        this.c = n4 + n6 / 2;
        this.d = n5;
        this.g = 18;
        this.h = 18;
        this.e = n6;
        this.f = n7;
        this.c = this.a;
        this.d = this.b;
        this.j = true;
        this.i = false;
    }

    public void a() {
        if (this.i) {
            return;
        }
        if (this.j) {
            int n2;
            boolean bl2 = true;
            if (this.e != this.g) {
                n2 = this.g - this.e;
                if (Math.abs(n2) < 5) {
                    this.e += n2;
                } else {
                    this.e += n2 / 2;
                    bl2 = false;
                }
            }
            if (bl2) {
                n2 = this.h - this.f;
                if (Math.abs(n2) < 5) {
                    this.f += n2;
                    this.i = true;
                } else {
                    this.f += n2 / 2;
                }
            }
        } else {
            int n3 = this.d;
            int n4 = n3 - this.b;
            if (Math.abs(n4) < 2) {
                this.b += n4;
                this.i = true;
            } else {
                this.b += n4 / 2;
            }
        }
    }

    public void a(Graphics graphics) {
        cz.a(graphics, cx.P, cx.Q, this.c(), this.d(), this.e(), this.f());
    }
}

