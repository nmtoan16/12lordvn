/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package com.mic.smsgame.suquan;

import com.mic.smsgame.suquan.ac;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class l
extends aj {
    private Image c = az.b("/bksplashscreen");
    private int d;

    public l() {
        cz.d().a(30);
    }

    public void a(Graphics graphics) {
        graphics.drawImage(this.c, cx.l, cx.m, 20);
    }

    public void a() {
        ++this.d;
        if (this.d == 30) {
            cz.a().f = cz.a().d(ay.a(cz.a().j()));
            cz.d().a(new ac(), true);
        }
    }
}

