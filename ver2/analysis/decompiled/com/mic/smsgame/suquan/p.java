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

public class p
extends ds {
    private int a;
    private int b;
    private int c;
    private int d;
    private boolean e = false;
    private Image f;
    private int g;
    private int h;
    private int i;
    private int j;

    public p(Image image) {
        this.f = image;
        this.r = image.getWidth() / 3;
        this.s = image.getHeight();
    }

    public void b(int n2, int n3, int n4, int n5) {
        this.d(n2, n3);
        this.a = n4;
        this.b = n5;
        this.c = n2;
        this.d = n3;
        int n6 = 20 + ay.a(40);
        int n7 = Math.abs(n2 - n4);
        int n8 = Math.abs(n3 - n5);
        if (n7 > n8) {
            this.d -= n7 - n8;
            this.d -= n6;
            this.c = n4 < cx.f / 2 ? (this.c += n6) : (this.c -= n6);
        } else {
            this.d -= n6;
            this.c = n4 < cx.f / 2 ? (this.c += n6) : (this.c -= (n6 += n8 - n7));
        }
        this.j = n4 < cx.f / 2 ? 2 : 0;
        this.i = 0;
        this.e = false;
        this.u = true;
        this.g = this.b(this.p, this.c, 14) + ay.a(4);
        this.h = this.b(this.q, this.d, 14) + ay.a(4);
    }

    public void a(Graphics graphics, int n2, int n3) {
        if (!this.u) {
            return;
        }
        graphics.drawRegion(this.f, this.i * this.r, 0, this.r, this.s, this.j, this.p, this.q, 3);
    }

    public void a() {
        if (!this.u) {
            return;
        }
        if (this.e) {
            if (this.i < 2) {
                ++this.i;
            }
            ++this.g;
            ++this.h;
            if (this.c(this.a, this.b, this.g, this.h)) {
                this.u = false;
                this.i = 1;
            }
        } else if (this.c(this.c, this.d, this.g, this.h)) {
            this.e = true;
            this.g = this.b(this.p, this.a, 28);
            this.h = this.b(this.q, this.b, 28);
        }
    }
}

