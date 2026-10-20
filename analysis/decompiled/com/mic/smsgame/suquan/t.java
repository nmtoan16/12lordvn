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

public class t {
    private int[] a;
    private Image b;

    public t() {
        int[] nArray = new int[10];
        nArray[1] = 5;
        nArray[2] = 10;
        nArray[3] = 15;
        nArray[4] = 20;
        nArray[5] = 25;
        nArray[6] = 30;
        nArray[7] = 35;
        nArray[8] = 40;
        nArray[9] = 45;
        this.a = nArray;
        this.b = az.b("/numbersmall");
    }

    public t(Image image) {
        int[] nArray = new int[10];
        nArray[1] = 5;
        nArray[2] = 10;
        nArray[3] = 15;
        nArray[4] = 20;
        nArray[5] = 25;
        nArray[6] = 30;
        nArray[7] = 35;
        nArray[8] = 40;
        nArray[9] = 45;
        this.a = nArray;
        this.b = image;
    }

    public void a(Graphics graphics, int n2, int n3, int n4, int n5) {
        int n6 = String.valueOf(n2).length();
        int n7 = n6 * 6 - 1;
        switch (n5) {
            case 0: {
                n3 += n7;
                break;
            }
            case 1: {
                n3 += n7 / 2;
            }
        }
        n3 -= 5;
        int n8 = 0;
        while (n8 < n6) {
            int n9 = n2 % 10;
            y.a(graphics, this.b, this.a[n9], 0, 5, 7, n3, n4, 0);
            n2 /= 10;
            n3 -= 6;
            ++n8;
        }
    }
}

