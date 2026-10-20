/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package com.mic.smsgame.suquan;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class g
extends cv {
    private Image v;
    private int w;
    private int x;
    private int[] y;
    private int[] z;
    private dl A;

    public g() {
        this.t = 0;
        this.v = az.b("/crystalblue");
        int n2 = 50;
        this.A = new dl((this.b_ - (n2 + 20)) / 2, (this.c_ - (n2 + 20)) / 2, n2 + 20, n2 + 20);
        int n3 = this.A.a + this.A.c / 2;
        int n4 = this.A.b + this.A.d / 2;
        int n5 = n2 / 2;
        int n6 = 8;
        int n7 = 360 / n6;
        int n8 = -90;
        this.y = new int[n6];
        this.z = new int[n6];
        int n9 = 0;
        while (n9 < n6) {
            this.y[n9] = n3 + (n5 * be.b(n8) >> 14);
            this.z[n9] = n4 + (n5 * be.a(n8) >> 14);
            n8 += n7;
            ++n9;
        }
        this.q = -1000;
    }

    public void a() {
        this.x = this.w;
        this.w = (this.w + 1) % this.y.length;
        super.a();
    }

    protected void b(Graphics graphics) {
        graphics.setColor(0);
        graphics.fillRect(this.A.a, this.A.b, this.A.c, this.A.d);
        bz.c.a(graphics, String.valueOf(this.c), this.A.a + (this.A.c >> 1), this.A.b - 4 + (this.A.d >> 1), 1);
        int n2 = this.v.getWidth() / 3;
        int n3 = this.v.getHeight();
        int n4 = this.y.length - 1;
        while (n4 > -1) {
            y.a(graphics, this.v, 0, 0, n2, n3, this.y[n4], this.z[n4], 3);
            --n4;
        }
        y.a(graphics, this.v, n2, 0, n2, n3, this.y[this.x], this.z[this.x], 3);
        y.a(graphics, this.v, n2 + n2, 0, n2, n3, this.y[this.w], this.z[this.w], 3);
    }
}

