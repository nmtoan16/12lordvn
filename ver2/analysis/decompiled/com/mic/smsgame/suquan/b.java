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

public class b
extends ds {
    dq[] a;
    int b = 3;
    int c = 100;
    private boolean e = true;
    private int f = 35;
    ak d;

    public boolean a() {
        return this.e;
    }

    public void a_(boolean bl2) {
        this.e = bl2;
    }

    public b(Image image) {
        byte[][] byArrayArray = new byte[1][];
        byte[] byArray = new byte[3];
        byArray[1] = 1;
        byArray[2] = 2;
        byArrayArray[0] = byArray;
        byte[][] byArrayArray2 = byArrayArray;
        this.a = new dq[20];
        int n2 = 0;
        while (n2 < this.a.length) {
            this.a[n2] = new dq(image, 3);
            this.a[n2].a(byArrayArray2);
            ++n2;
        }
        this.d = new ak();
        this.d.a(12);
    }

    public void a(int n2, int n3, int n4, String string) {
        this.b = 3;
        this.d(n2, n3);
        this.e = true;
        this.d.a(string, n2, n3 + 3);
        this.c = this.f + (n4 - 1) * 5;
    }

    public void a(Graphics graphics, int n2, int n3) {
        if (!this.e) {
            return;
        }
        int n4 = 360 / this.a.length;
        int n5 = 0;
        int n6 = 0;
        while (n6 < this.a.length) {
            int n7 = this.p + (this.b * be.b(n5) >> 14);
            int n8 = this.q + (this.b * be.a(n5) >> 14);
            this.a[n6].a(graphics, n7, n8);
            n5 += n4;
            ++n6;
        }
        if (this.b >= 7) {
            this.d.a(graphics, 0, 0);
        }
    }

    public void b() {
        if (!this.e) {
            return;
        }
        int n2 = 0;
        while (n2 < this.a.length) {
            this.a[n2].e();
            ++n2;
        }
        this.b += 3;
        if (this.b >= this.c) {
            this.e = false;
        }
        if (this.b >= 7) {
            this.d.a();
        }
    }
}

