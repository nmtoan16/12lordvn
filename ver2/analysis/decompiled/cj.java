/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mic.smsgame.suquan.t;
import javax.microedition.lcdui.Graphics;

public class cj
extends ds {
    private boolean a;
    private int b;
    private t c;

    public int a() {
        return this.b;
    }

    public void a(int n2) {
        this.b = n2;
    }

    public cj(int n2, int n3) {
        this.p = n2;
        this.q = n3;
        this.c = new t(bs.a().B);
    }

    public void b(int n2) {
        this.b = n2;
        this.a = true;
        if (n2 < 0) {
            this.a = false;
        }
    }

    public void a(Graphics graphics, int n2, int n3) {
        if (this.a) {
            int n4 = this.b / 25;
            int n5 = n4 / 60;
            int n6 = n4 % 60;
            bz.c.a(graphics, ":", this.p, this.q - 4, 1);
            this.c.a(graphics, n5, this.p - 2, this.q, 2);
            this.c.a(graphics, n6, this.p + 2, this.q, 0);
        }
    }

    public void b() {
        if (this.b > 0) {
            --this.b;
            if (this.b == 0) {
                this.a = true;
            } else if (this.b < 250) {
                if (this.b % 3 == 0) {
                    this.a = !this.a;
                }
            } else if (this.b < 750 && this.b % 8 == 0) {
                this.a = !this.a;
            }
        }
    }
}

