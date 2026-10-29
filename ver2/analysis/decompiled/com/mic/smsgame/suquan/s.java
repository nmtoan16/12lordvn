/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package com.mic.smsgame.suquan;

import com.mic.smsgame.suquan.x;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class s
extends ds {
    private x[] a;
    private byte[][] b;

    public s() {
        byte[][] byArrayArray = new byte[1][];
        byte[] byArray = new byte[10];
        byArray[4] = 1;
        byArray[5] = 1;
        byArray[6] = 1;
        byArray[7] = 2;
        byArray[8] = 2;
        byArray[9] = 2;
        byArrayArray[0] = byArray;
        this.b = byArrayArray;
        this.a = new x[5];
        int n2 = 0;
        while (n2 < this.a.length) {
            this.a[n2] = new x();
            this.a[n2].a(this.b);
            ++n2;
        }
    }

    public void a(int n2, int n3, int n4, int n5, Image image) {
        int n6 = 0;
        while (n6 < this.a.length) {
            this.a[n6].a(image, 3);
            this.a[n6].b(ay.a(this.b[0].length));
            this.a[n6].b(n2 + ay.a() % 13, n3 + ay.a() % 13, n4 + ay.a() % 20, n5 + ay.a() % 3);
            ++n6;
        }
        this.u = true;
    }

    public void a(Graphics graphics, int n2, int n3) {
        if (!this.u) {
            return;
        }
        int n4 = this.a.length - 1;
        while (n4 >= 0) {
            this.a[n4].a(graphics);
            --n4;
        }
    }

    public void a() {
        if (!this.u) {
            return;
        }
        int n2 = 0;
        int n3 = this.a.length - 1;
        while (n3 >= 0) {
            if (this.a[n3].m()) {
                this.a[n3].e();
            } else {
                ++n2;
            }
            --n3;
        }
        if (n2 >= this.a.length) {
            this.u = false;
        }
    }
}

