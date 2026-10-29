/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public class ag
extends by {
    private boolean j;
    private boolean k;

    public void a(int n2, int n3, int n4, int n5, int n6, int n7) {
        if (n7 < 18) {
            n7 = 18;
        }
        if (n6 < 18) {
            n6 = 18;
        }
        this.a = n2 + n6 / 2;
        this.b = n3;
        this.c = n4 + n6 / 2;
        this.d = n5 + n7 / 2;
        this.g = n6;
        this.h = n7;
        this.f = 18;
        this.e = 18;
        this.a = this.c;
        this.b = this.d;
        this.k = this.b > n5;
        this.j = false;
        this.i = false;
    }

    public void a() {
        if (this.i) {
            return;
        }
        if (this.j) {
            int n2;
            this.b = this.d;
            boolean bl2 = true;
            if (this.f != this.h) {
                n2 = this.h - this.f;
                if (Math.abs(n2) < 5) {
                    this.f += n2;
                } else {
                    this.f += n2 / 2;
                    bl2 = false;
                }
            }
            if (bl2) {
                n2 = this.g - this.e;
                if (Math.abs(n2) < 5) {
                    this.e += n2;
                    this.i = true;
                } else {
                    this.e += n2 / 2;
                }
            }
        } else {
            int n3 = this.k ? this.d - 2 : this.d + 2;
            int n4 = n3 - this.b;
            if (Math.abs(n4) < 2) {
                this.b += n4;
                this.j = true;
            } else {
                this.b += n4 / 2;
            }
        }
    }

    public void a(Graphics graphics) {
        cz.a(graphics, cx.P, cx.Q, this.c(), this.d(), this.e(), this.f());
    }
}

