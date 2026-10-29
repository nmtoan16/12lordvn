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

public class m
extends dc {
    private Image E = az.b("/r/br");
    private int F = 240 - this.E.getWidth() >> 1;
    private int G = 320 - this.E.getHeight() >> 1;

    public m(int n2) {
        super(n2, com.mic.smsgame.suquan.h.g, 3);
        this.C.a((dz)null);
        this.C.a((h)null);
        this.q = 3;
    }

    protected void b() {
        this.B = new Image[4];
        try {
            this.z = az.b("/r/q");
            this.A = az.b("/r/g");
            int n2 = 0;
            while (n2 < this.B.length) {
                this.B[n2] = az.b("/r/" + (n2 + 1));
                ++n2;
            }
            Image image = Image.createImage((int)this.b_, (int)this.c_);
            Image image2 = az.b("/bkmenu");
            Graphics graphics = image.getGraphics();
            graphics.drawImage(image2, cx.l, cx.m, 0);
            image2 = null;
            y.a(graphics, 0x828282, 0, 0, this.b_, this.c_);
            this.a(new aw(image, this.b_ >> 1, this.c_ >> 1, 3));
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    protected void a(Graphics graphics, int n2, int n3) {
        n2 = this.w[this.e].i() + this.w[this.e].k() / 2;
        n3 = this.w[this.e].j() + this.w[this.e].l() / 2;
        graphics.setColor(0xFFFF00);
        graphics.fillTriangle(n2, n3 - 10 - this.f, n2 - 4, n3 - 14 - this.f, n2 + 4, n3 - 14 - this.f);
        graphics.fillTriangle(n2, n3 + 10 + this.f, n2 - 5, n3 + 15 + this.f, n2 + 4, n3 + 15 + this.f);
        graphics.fillTriangle(n2 - 10 - this.f, n3, n2 - 14 - this.f, n3 - 4, n2 - 14 - this.f, n3 + 4);
        graphics.fillTriangle(n2 + 10 + this.f, n3, n2 + 15 + this.f, n3 - 5, n2 + 15 + this.f, n3 + 4);
    }

    protected void b(Graphics graphics) {
        graphics.drawImage(this.E, this.g + this.F, this.a_ + this.G, 0);
        super.b(graphics);
    }

    protected void u() {
        this.t = new int[10][4];
        this.t[0] = new int[]{-1, 1, 4, 9};
        int[] nArray = new int[4];
        nArray[0] = -1;
        nArray[1] = 2;
        nArray[2] = 5;
        this.t[1] = nArray;
        this.t[2] = new int[]{-1, 3, 6, 1};
        this.t[3] = new int[]{-1, 4, 7, 2};
        int[] nArray2 = new int[4];
        nArray2[1] = 5;
        nArray2[2] = -1;
        nArray2[3] = 3;
        this.t[4] = nArray2;
        this.t[5] = new int[]{1, 6, 8, 4};
        this.t[6] = new int[]{2, 7, 9, 5};
        this.t[7] = new int[]{3, 8, -1, 6};
        this.t[8] = new int[]{5, 9, -1, 7};
        int[] nArray3 = new int[4];
        nArray3[0] = 6;
        nArray3[2] = -1;
        nArray3[3] = 8;
        this.t[9] = nArray3;
        int n2 = 34;
        int n3 = 34;
        this.g = cx.l;
        this.a_ = cx.m;
        int n4 = this.g + 68;
        int n5 = this.a_ + 124;
        this.x = new int[this.t.length];
        this.y = new int[this.t.length];
        int n6 = 0;
        while (n6 < this.x.length) {
            this.x[n6] = n4 + n6 % 4 * n2;
            this.y[n6] = n5 + n6 / 4 * n3;
            ++n6;
        }
        n6 = this.x.length - 2;
        while (n6 < this.x.length) {
            int n7 = n6++;
            this.x[n7] = this.x[n7] + n2;
        }
    }
}

