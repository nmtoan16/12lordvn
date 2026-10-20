/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Command
 *  javax.microedition.lcdui.CommandListener
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 *  javax.microedition.lcdui.TextBox
 */
package com.mic.smsgame.suquan;

import com.mg.smsgame.main.MGMIDlet;
import com.mg.smsgame.main.b;
import com.mic.smsgame.suquan.u;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.TextBox;

public class z
extends aj
implements b,
CommandListener {
    private Image[] c;
    private Image d;
    private Image e;
    private int f;
    private Image[] t;
    private Image u;
    private String[] v;
    private String[] w;
    private TextBox x;
    private int y;

    public z() {
        this.b();
        this.g = this.b_ - this.t[0].getWidth() >> 1;
        this.a_ = (this.c_ - 180 >> 1) - 18;
        this.w = com.mic.smsgame.suquan.h.e;
        this.v = bz.a(this.w[this.f], this.t[0].getWidth() - 15);
        this.a(new da("Ch\u1ecdn", -1));
        this.c(new da(" ", -1));
        this.b(new da("Tr\u1edf v\u1ec1", -2));
        this.a((h)null);
        this.a(this);
        this.x = new TextBox("Nhap ten nhan vat", "", 10, 0x100000);
        this.x.addCommand(new Command("Xong", 4, 1));
        this.x.addCommand(new Command("Tr\u1edf v\u1ec1", 2, 1));
        this.x.setCommandListener((CommandListener)this);
    }

    private void b() {
        Image image = az.b("/avatar");
        this.c = new Image[6];
        int n2 = 0;
        while (n2 < 6) {
            this.c[n2] = Image.createImage((Image)image, (int)(n2 * 30), (int)0, (int)30, (int)30, (int)0);
            ++n2;
        }
        image = null;
        this.d = az.b("/focusitem");
        this.t = new Image[2];
        this.t[0] = az.b("/frame1");
        this.t[1] = az.b("/frame2");
        this.u = az.b("/frame3");
        this.e = Image.createImage((int)this.b_, (int)this.c_);
        Image image2 = az.b("/bkmenu");
        Graphics graphics = this.e.getGraphics();
        graphics.drawImage(image2, cx.l, cx.m, 0);
        image2 = null;
        y.a(graphics, 0x828282, 0, 0, this.b_, this.c_);
    }

    public void a(int n2) {
        int n3 = this.f;
        switch (n2) {
            case 14: {
                this.f = (this.f - 1 + this.w.length) % this.w.length;
                break;
            }
            case 15: {
                this.f = (this.f + 1) % this.w.length;
                break;
            }
            case 12: {
                this.f = (this.f - 3 + this.w.length) % this.w.length;
                break;
            }
            case 13: {
                this.f = (this.f + 3) % this.w.length;
                break;
            }
        }
        if (this.f != n3) {
            this.v = bz.a(this.w[this.f], this.t[0].getWidth() - 15);
        }
    }

    public void a(int n2, int n3) {
        int n4 = this.g;
        int n5 = this.a_ + 12;
        int n6 = 0;
        int n7 = 0;
        while (n7 < 2) {
            n4 = this.g + 25;
            int n8 = 0;
            while (n8 < 3) {
                if (n3 >= n5 && n3 <= n5 + 36 && n2 >= n4 && n2 <= n4 + 38 && n6 != this.f) {
                    this.f = n6;
                    this.v = bz.a(this.w[this.f], this.t[0].getWidth() - 15);
                    return;
                }
                n4 += 45;
                ++n6;
                ++n8;
            }
            n5 += 38;
            ++n7;
        }
    }

    public void a(Graphics graphics) {
        graphics.drawImage(this.e, 0, 0, 0);
        com.mic.smsgame.suquan.u.a(graphics, this.g, this.a_, 200, 7814005, this.t);
        int n2 = this.g;
        int n3 = this.a_ + 12;
        int n4 = 0;
        int n5 = 0;
        while (n5 < 2) {
            n2 = this.g + 25;
            int n6 = 0;
            while (n6 < 3) {
                if (this.f == n4 && this.y % 2 == 0) {
                    y.a(graphics, this.d, 0, 0, 36, 36, n2, n3, 0);
                } else {
                    y.a(graphics, this.d, 36, 0, 36, 36, n2, n3, 0);
                }
                graphics.drawImage(this.c[n4], n2 + 3, n3 + 3, 0);
                n2 += 45;
                ++n4;
                ++n6;
            }
            n3 += 38;
            ++n5;
        }
        graphics.drawImage(this.u, this.g + this.t[0].getWidth() / 2, n3, 17);
        bz.a(graphics, bz.d, this.v, this.g + 11, n3 + 15, this.b_, 120, 0);
    }

    public void a() {
        this.y = this.y < 1 ? 8 : --this.y;
    }

    private void u() {
        this.x.setString("");
        MGMIDlet.f().a.setCurrent((Displayable)this.x);
    }

    public void b(int n2, int n3) {
        switch (n3) {
            case -1: {
                this.u();
                break;
            }
            case -2: {
                cz.d().e.a(8, 2, true);
                break;
            }
            case -3: {
                cz.d().a(false);
                this.u();
                break;
            }
            case -4: {
                cz.d().a(false);
                MGMIDlet.f().a.setCurrent((Displayable)cz.d());
            }
        }
    }

    public void commandAction(Command command, Displayable displayable) {
        if (ao.a(command.getLabel(), "Xong")) {
            MGMIDlet.f().a.setCurrent((Displayable)cz.d());
            String string = this.x.getString().trim();
            if (string == null || string.length() < 2) {
                ae ae2 = cz.a("Th\u00f4ng tin", "Nh\u1eadp t\u00ean nh\u00e2n v\u1eadt c\u00f3 \u00edt nh\u1ea5t 2 k\u00ed t\u1ef1 v\u00e0 nhi\u1ec1u nh\u1ea5t 10 k\u00ed t\u1ef1. B\u1ea1n c\u00f3 mu\u1ed1n ti\u1ebfp t\u1ee5c kh\u00f4ng?", "C\u00f3", -3, "Kh\u00f4ng", -4, 1);
                ae2.a(this);
                cz.d().a(ae2, false);
                return;
            }
            dd.a(string.toUpperCase());
            dd.c(this.f);
            cz.d().e.a(8, 7, true);
        } else if (ao.a(command.getLabel(), "Tr\u1edf v\u1ec1")) {
            MGMIDlet.f().a.setCurrent((Displayable)cz.d());
        }
    }
}

