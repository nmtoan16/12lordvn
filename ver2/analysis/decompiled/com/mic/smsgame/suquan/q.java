/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package com.mic.smsgame.suquan;

import com.mg.smsgame.main.b;
import com.mic.smsgame.suquan.o;
import com.mic.smsgame.suquan.t;
import com.mic.smsgame.suquan.u;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class q
extends aj
implements b {
    public static final int[] c = new int[]{3, 3, 1000, 3, 3, 3};
    private Image d;
    private Image e;
    private Image[] f;
    private Image t;
    private Image u;
    private Image v;
    private final int[] w = new int[]{-1, -1, -1, 1000, 250, 500};
    private boolean x;
    private String[] y;
    private int z;
    private String[] A;
    private int B;
    private int C;
    private t D;
    private int[] E;
    private String F;

    public q(int n2) {
        this.b();
        this.g = this.b_ - this.f[0].getWidth() >> 1;
        this.a_ = (this.c_ - 180 >> 1) - 14;
        this.A = com.mic.smsgame.suquan.h.d;
        this.v();
        this.u();
        this.C = n2;
        this.E = bg.v();
        if (this.E == null) {
            this.E = new int[6];
        }
        this.D = new t();
        this.q = 4;
    }

    private void b() {
        this.d = Image.createImage((int)this.b_, (int)this.c_);
        Image image = az.b("/bkmenu");
        Graphics graphics = this.d.getGraphics();
        graphics.drawImage(image, cx.l, cx.m, 0);
        image = null;
        y.a(graphics, 0x828282, 0, 0, this.b_, this.c_);
        this.e = az.b("/itemshops");
        this.f = new Image[2];
        this.f[0] = az.b("/frame1");
        this.f[1] = az.b("/frame2");
        this.t = az.b("/frame3");
        this.u = az.b("/focusitem");
        this.v = az.b("/gold");
        this.a((h)null);
        this.a(this);
    }

    public void a(Graphics graphics) {
        graphics.drawImage(this.d, 0, 0, 0);
        com.mic.smsgame.suquan.u.a(graphics, this.g, this.a_, 200, 7814005, this.f);
        if (this.x) {
            return;
        }
        int n2 = this.g;
        int n3 = this.a_ + 12;
        int n4 = 0;
        int n5 = 0;
        while (n5 < 2) {
            n2 = this.g + 25;
            int n6 = 0;
            while (n6 < 3) {
                if (this.z == n4) {
                    y.a(graphics, this.u, 0, 0, 36, 36, n2, n3, 0);
                } else {
                    y.a(graphics, this.u, 36, 0, 36, 36, n2, n3, 0);
                }
                y.a(graphics, this.e, n4 * 28, 0, 28, 28, n2 + 4, n3 + 4, 0);
                if (n4 != 2) {
                    this.D.a(graphics, this.E[n4], n2 + 34, n3 + 29, 2);
                }
                n2 += 45;
                ++n4;
                ++n6;
            }
            n3 += 38;
            ++n5;
        }
        graphics.drawImage(this.t, this.g + this.f[0].getWidth() / 2, n3, 17);
        bz.c.a(graphics, com.mic.smsgame.suquan.h.c[this.z], this.g + this.f[0].getWidth() / 2, n3 + 8, 1);
        bz.a(graphics, bz.d, this.y, this.g + 11, n3 + 22, this.b_, 120, 0);
        bz.c.a(graphics, this.F, this.g + this.f[0].getWidth() / 2, this.a_ + 180, 1);
        graphics.drawImage(this.v, 0, -4, 0);
        bz.d.a(graphics, "= " + this.E[2], this.v.getWidth() + 1, 0, 0);
    }

    private void u() {
        this.m();
        if (!this.x) {
            this.a(new da("Mua", -1));
            this.b(new da("Tr\u1edf v\u1ec1", -3));
            this.c(new da(" ", -1));
        }
    }

    public void a() {
    }

    public void a(int n2) {
        if (this.x) {
            return;
        }
        int n3 = this.z;
        switch (n2) {
            case 14: {
                this.z = (this.z - 1 + this.w.length) % this.w.length;
                break;
            }
            case 15: {
                this.z = (this.z + 1) % this.w.length;
                break;
            }
            case 12: {
                this.z = (this.z - 3 + this.w.length) % this.w.length;
                break;
            }
            case 13: {
                this.z = (this.z + 3) % this.w.length;
            }
        }
        if (this.z != n3) {
            this.v();
        }
    }

    private void v() {
        this.y = bz.a(this.A[this.z], this.f[0].getWidth() - 15);
        this.F = this.w[this.z] > 0 ? " Gi\u00e1 " + this.w[this.z] + " v\u00e0ng" : " ";
    }

    public void a(int n2, int n3) {
        int n4 = this.a_ + 12;
        int n5 = this.g + 25;
        if (n3 >= n4 && n3 <= n4 + 76) {
            int n6 = 0;
            while (n6 < 3) {
                int n7;
                if (n2 >= n5 && n2 <= n5 + 36 && (n7 = (n3 - n4) / 38 * 3 + n6) != this.z) {
                    this.z = n7;
                    this.v();
                }
                n5 += 45;
                ++n6;
            }
        }
    }

    public void b(int n2, int n3) {
        switch (n3) {
            case -1: {
                String string = String.valueOf(this.A[this.z]) + " " + "B\u1ea1n c\u00f3 mu\u1ed1n mua kh\u00f4ng?";
                if (this.z <= 2) {
                    string = String.valueOf(string) + " Tham kh\u1ea3o gi\u00e1 t\u1ea1i http://free.ola.vn.";
                }
                ae ae2 = cz.a("Th\u00f4ng tin", string, "C\u00f3", -4, "Kh\u00f4ng", -5, 1);
                ae2.a(this);
                cz.d().a(ae2, true);
                break;
            }
            case -2: {
                break;
            }
            case -3: {
                cz.a().a(4, this.C, false);
                break;
            }
            case -4: {
                cz.d().a(false);
                if (this.w[this.z] > 0) {
                    if (this.E[2] < this.w[this.z]) {
                        ae ae3 = cz.a("Th\u00f4ng tin", "Kh\u00f4ng \u0111\u1ee7 v\u00e0ng \u0111\u1ec3 mua v\u1eadt ph\u1ea9m n\u00e0y? B\u1ea1n c\u00f3 mu\u1ed1n mua th\u00eam v\u00e0ng kh\u00f4ng? Tham kh\u1ea3o gi\u00e1 t\u1ea1i http://free.ola.vn.", "C\u00f3", -6, "Kh\u00f4ng", -5, 1);
                        ae3.a(this);
                        cz.d().a(ae3, true);
                        break;
                    }
                    this.E[2] = this.E[2] - this.w[this.z];
                    bg.b(2, this.w[this.z]);
                    this.B = this.z;
                    this.w();
                    break;
                }
                this.B = this.z;
                this.e(this.B);
                break;
            }
            case -5: {
                cz.d().a(false);
                break;
            }
            case -6: {
                cz.d().a(false);
                this.B = 2;
                this.e(this.B);
            }
        }
    }

    private void e(int n2) {
        an.a("shop", cx.G, new o(this));
    }

    private void w() {
        int n2 = this.B;
        this.E[n2] = this.E[n2] + c[this.B];
        bg.a(this.B, c[this.B], 6);
        ae ae2 = cz.a("Th\u00f4ng tin", "\u0110\u00e3 mua v\u1eadt ph\u1ea9m th\u00e0nh c\u00f4ng!", "\u0110\u00f3ng", -5, 1);
        ae2.a(this);
        cz.d().a(ae2, true);
    }

    static void a(q q2) {
        q2.w();
    }
}

