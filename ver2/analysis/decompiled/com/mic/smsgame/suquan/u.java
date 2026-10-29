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

public class u {
    public static void a(Graphics graphics, int n2, int n3, int n4, int n5, Image[] imageArray) {
        int n6 = imageArray[0].getWidth();
        graphics.setColor(n5);
        graphics.fillRect(n2 + 9, n3 + 9, n6 - 18, n4 - 18);
        graphics.setColor(0x3C0000);
        graphics.drawRect(n2 + 8, n3 + 8, n6 - 17, n4 - 17);
        graphics.setColor(14323025);
        graphics.drawRect(n2 + 7, n3 + 7, n6 - 15, n4 - 15);
        graphics.setColor(1838350);
        graphics.drawRect(n2 + 6, n3 + 6, n6 - 13, n4 - 13);
        graphics.setColor(9132617);
        graphics.drawRect(n2 + 5, n3 + 5, n6 - 11, n4 - 11);
        graphics.setColor(0);
        graphics.drawRect(n2 + 4, n3 + 4, n6 - 9, n4 - 9);
        graphics.drawImage(imageArray[0], n2, n3, 20);
        graphics.drawRegion(imageArray[0], 0, 0, imageArray[0].getWidth(), imageArray[0].getHeight(), 1, n2, n3 + n4, 36);
        if (imageArray.length > 1) {
            graphics.drawImage(imageArray[1], n2 + 1, n3 + 23, 17);
            graphics.drawImage(imageArray[1], n2 + n6 - 2, n3 + 23, 17);
        }
    }
}

