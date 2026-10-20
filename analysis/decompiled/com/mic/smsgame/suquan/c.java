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

public class c
extends ds {
    private final int b;
    private final int c;
    private int[] d;
    private int e;
    private Image f;
    private int g;
    private int h;
    private int i;
    int a;

    public c(int n2, int n3) {
        this.b = 0;
        this.c = 1;
        int[] nArray = new int[9];
        nArray[0] = -2;
        nArray[1] = -6;
        nArray[3] = -3;
        nArray[5] = -2;
        nArray[7] = -1;
        this.d = nArray;
        this.a = 0;
        this.f = az.b("/title");
        this.i = n3;
        this.d(n2, n3 - 150);
        this.h = this.b(this.q, n3, 4);
        this.g = 0;
    }

    public void a(Graphics graphics, int n2, int n3) {
        graphics.drawImage(this.f, this.p, this.q + this.a, 33);
    }

    public void a() {
        switch (this.g) {
            case 0: {
                if (this.f(this.i, this.h)) {
                    this.g = 1;
                    cz.d().a(10);
                    break;
                }
                ++this.h;
                break;
            }
            case 1: {
                this.a = this.d[this.e];
                ++this.e;
                if (this.e < this.d.length) break;
                this.u = false;
                return;
            }
        }
    }
}

