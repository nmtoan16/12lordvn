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
import com.mic.smsgame.suquan.c;
import com.mic.smsgame.suquan.f;
import com.mic.smsgame.suquan.r;
import com.mic.smsgame.suquan.v;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.TextBox;

public class ac
extends aj
implements b,
dy {
    protected da c;
    protected da d;
    final int e;
    final int f;
    final int t;
    dv u;
    int[] v;
    private boolean w;
    private ds[] x;
    private c y;
    private Image z;
    private int A;
    private boolean B;
    private dz C;

    public ac() {
        this.e = 0;
        this.f = 1;
        this.t = 2;
        cx.B = false;
        cx.N = true;
        this.b();
        this.u = new dv(true);
        this.u.a(this);
        this.E();
        this.C = new aw(az.b("/bkmenu"), cx.l, cx.m, 20);
        this.y = new c(this.b_ / 2, cx.m + 110);
        this.z = az.b("/swordfocus");
        boolean bl2 = this.w = !com.mic.smsgame.suquan.f.c();
        if (this.w) {
            this.e(0);
        } else {
            this.e(1);
        }
        this.a(this.C);
        this.a(this);
        this.a((h)null);
        cf.a().a("menu", -1);
        d.a("+++++++++++Tempomenu=" + cf.a().f());
        cf.a().a(120000);
        cf.a().e();
        this.q = 7;
    }

    private void E() {
        String[] stringArray = new String[]{"strcontinuegame", "strnewgame", "strrecord", "strshop", "strinformation", "strgivegame", "strchatola", "strothergame", "strguide", "strauthor"};
        this.x = new v[stringArray.length];
        Image[] imageArray = new Image[2];
        int n2 = 0;
        while (n2 < this.x.length) {
            imageArray = new Image[]{az.b("/" + stringArray[n2]), az.b("/" + stringArray[n2] + "focus")};
            this.x[n2] = new v(imageArray);
            ++n2;
        }
    }

    protected void e(int n2) {
        switch (n2) {
            case 0: {
                this.v = new int[]{1, 2, 3, 4};
                break;
            }
            case 1: {
                int[] nArray = new int[5];
                nArray[1] = 1;
                nArray[2] = 2;
                nArray[3] = 3;
                nArray[4] = 4;
                this.v = nArray;
                break;
            }
            case 2: {
                this.v = new int[]{5, 6, 7, 8, 9};
            }
        }
        if (n2 == 2) {
            this.b(this.d);
        } else {
            this.b(this.c);
        }
        this.u();
    }

    protected void b() {
        this.c = new da("Tho\u00e1t", 1);
        this.d = new da("Tr\u1edf v\u1ec1", 2);
    }

    protected void u() {
        ds[] dsArray = new ds[this.v.length];
        int n2 = 130 + cx.m;
        int n3 = this.b_ / 2;
        int n4 = 0;
        while (n4 < dsArray.length) {
            dsArray[n4] = this.x[this.v[n4]];
            dsArray[n4].d(n3, n2);
            dsArray[n4].k(17);
            dsArray[n4].a(false);
            n2 += 24;
            ++n4;
        }
        this.u.a(dsArray, 0);
    }

    public final void a(Graphics graphics) {
        this.b(graphics);
        this.e(graphics);
    }

    protected void b(Graphics graphics) {
        this.y.a(graphics);
    }

    protected void e(Graphics graphics) {
        this.u.a(graphics);
        if (!this.u.c()) {
            ds ds2 = this.u.a();
            int n2 = ds2.i();
            int n3 = ds2.j() + 3;
            y.a(graphics, this.z, 0, 0, this.z.getWidth(), this.z.getHeight(), n2 - 2 - this.A, n3, 24);
            graphics.drawRegion(this.z, 0, 0, this.z.getWidth(), this.z.getHeight(), 2, n2 + ds2.k() + 2 + this.A, n3, 20);
        }
    }

    public void a() {
        if (this.y.m()) {
            this.y.a();
        }
        this.u.b();
        if (this.B) {
            --this.A;
            if (this.A == 0) {
                this.B = false;
            }
        } else {
            ++this.A;
            if (this.A == 3) {
                this.B = true;
            }
        }
    }

    protected void v() {
        ae ae2 = cz.a("Ch\u00fa \u00fd", "C\u1eadp nh\u1eadt game mi\u1ec5n ph\u00ed m\u1edbi nh\u1ea5t t\u1ea1i www.giaitri321.pro", "C\u00f3", 3, "Kh\u00f4ng", 4, 1);
        ae2.a(this);
        cz.d().a(ae2, true);
    }

    protected void w() {
        MGMIDlet.f().d();
    }

    protected void x() {
        cz.d().e.a(2, 3, true);
    }

    protected void y() {
        cz.d().e.a(2, 4, true);
    }

    protected void z() {
        TextBox textBox = new TextBox("Nhap so dien thoai nguoi nhan", "", 20, 3);
        textBox.addCommand(new Command("G\u1eedi", 4, 1));
        textBox.addCommand(new Command("\u0110\u00f3ng", 7, 1));
        textBox.setCommandListener((CommandListener)new r(this, textBox));
        MGMIDlet.f().a.setCurrent((Displayable)textBox);
    }

    protected void A() {
        cz.d().a(false);
        this.z();
    }

    protected void a(Image image) {
        dj dj2 = new dj("Chat Ola", dj.a("007#sfl- Ph\u1ea7n m\u1ec1m chat tr\u00ean \u0111i\u1ec7n tho\u1ea1i \u0111i \u0111\u1ed9ng v\u1edbi chi ph\u00ed c\u1ef1c th\u1ea5p.#sfl- K\u1ebft n\u1ed1i Yahoo.#sfl- Chia s\u1ebb h\u00ecnh \u1ea3nh, video clip tr\u1ef1c ti\u1ebfp.#sfl- \u0110\u1ecdc b\u00e1o Online.#sfl- Tra t\u1eeb \u0111i\u1ec3n ti\u1ebfng anh tr\u1ef1c tuy\u1ebfn.#sfl- Tra b\u00e0i h\u00e1t Karaoke.#sfl- Ch\u01a1i game tr\u1ef1c tuy\u1ebfn...", null, null, bz.d, bz.c));
        dj2.b(new da("\u0110\u00f3ng", 6));
        dj2.a(new da("T\u1ea3i v\u1ec1", 8));
        dj2.a((h)null);
        dj2.a(this.C);
        dj2.a(this);
        cz.d().a(dj2);
    }

    protected void B() {
        try {
            try {
                if (Class.forName("javax.microedition.io.file.FileConnection") != null) {
                    MGMIDlet.f().a("http://chat.ola.vn/ola/olamobileext39.jad");
                }
            }
            catch (Throwable throwable) {
                MGMIDlet.f().a("http://chat.ola.vn/ola/olamobile.jad");
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    protected void a(String[][] stringArray, Image image) {
        if (stringArray == null) {
            return;
        }
        e[] eArray = new dt[stringArray.length];
        int n2 = 0;
        while (n2 < eArray.length) {
            eArray[n2] = new dt(stringArray[n2], true, 1);
            ((dt)eArray[n2]).d = -1;
            ++n2;
        }
        dj dj2 = new dj("Th\u00f4ng tin", eArray);
        dj2.c(new da("\u0110\u00f3ng", 6));
        dj2.a((h)null);
        dj2.a(this.C);
        dj2.a(this);
        cz.d().a(dj2);
    }

    protected void h(int n2, int n3) {
        ae ae2 = cz.a("Ch\u00fa \u00fd", "b\u1ea1n c\u00f3 mu\u1ed1n ch\u01a1i l\u1ea1i t\u1eeb \u0111\u1ea7u kh\u00f4ng?", "C\u00f3", n2, "Kh\u00f4ng", n3, 1);
        ae2.a(this);
        cz.d().a(ae2, true);
    }

    protected void C() {
        ae ae2 = cz.a("Ch\u00fa \u00fd", "C\u1eadp nh\u1eadt game mi\u1ec5n ph\u00ed m\u1edbi nh\u1ea5t t\u1ea1i www.giaitri321.pro", "C\u00f3", 7, "Kh\u00f4ng", 4, 1);
        ae2.a(this);
        cz.d().a(ae2, true);
    }

    protected void D() {
        cz.d().a(true);
        try {
            MGMIDlet.f().a("http://giaitri321.pro");
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void a(int n2) {
        this.u.a(n2);
    }

    public void a(int n2, int n3) {
        this.u.a(n2, n3);
    }

    public void b(int n2, int n3) {
        switch (n3) {
            case 1: {
                this.v();
                break;
            }
            case 4: {
                cz.d().a(true);
                break;
            }
            case 6: {
                cz.d().a(false);
                break;
            }
            case 3: {
                this.w();
                break;
            }
            case 2: {
                if (!this.w) {
                    this.e(1);
                    break;
                }
                this.e(0);
                break;
            }
            case 7: {
                this.D();
                break;
            }
            case 8: {
                this.B();
                break;
            }
            case 5: {
                this.A();
                break;
            }
            case 9: {
                com.mic.smsgame.suquan.f.a();
                bg.k();
                bg.x();
                cz.d().a(false);
                cf.a().d();
                cz.d().a(new df(true), false);
            }
        }
    }

    private void F() {
        di di2 = new di();
        di2.a(new x(az.b("/sword")));
        di2.a(new x(az.b("/yinyang")));
        di2.a(new x(az.b("/rice")));
        di2.a(new x(az.b("/gold")));
        di2.a(new x(az.b("/book")));
        di2.a(new x(az.b("/heart")));
        di2.a(new x(az.b("/swordred")));
        dj dj2 = dj.a("H\u01b0\u1edbng d\u1eabn", "**T\u00ecnh ti\u1ebft, c\u1ed1t truy\u1ec7n v\u00e0 t\u00ean nh\u00e2n v\u1eadt ph\u1ee5 c\u1ee7a tr\u00f2 ch\u01a1i \u0111\u1ec1u d\u1ef1a v\u00e0o s\u1ef1 ki\u1ec7n Lo\u1ea1n 12 S\u1ee9 Qu\u00e2n trong l\u1ecbch s\u1eed Vi\u1ec7t Nam \u1edf th\u1ebf k\u1ef7 X.@*Lu\u1eadt ch\u01a1i:@- M\u1ed9t l\u01b0\u1ee3t \u0111i ph\u1ea3i x\u1ebfp \u00edt nh\u1ea5t 3 bi\u1ec3u t\u01b0\u1ee3ng c\u00f9ng lo\u1ea1i n\u1eb1m s\u00e1t nhau theo h\u00e0ng ngang ho\u1eb7c d\u1ecdc.@- X\u1ebfp 4 bi\u1ec3u t\u01b0\u1ee3ng tr\u1edf l\u00ean \u0111\u01b0\u1ee3c th\u01b0\u1edfng th\u00eam 1 l\u01b0\u1ee3t \u0111i.@- X\u1ebfp 5 bi\u1ec3u t\u01b0\u1ee3ng tr\u1edf l\u00ean \u0111\u01b0\u1ee3c th\u01b0\u1edfng 1 v\u1eadt ph\u1ea9m h\u1ed7 tr\u1ee3.@{i}@- X\u1ebfp bi\u1ec3u t\u01b0\u1ee3ng thanh ki\u1ebfm \u0111\u1ec3 t\u1ea5n c\u00f4ng \u0111\u1ed1i ph\u01b0\u01a1ng.@{i}@- X\u1ebfp bi\u1ec3u t\u01b0\u1ee3ng \u00e2m d\u01b0\u01a1ng \u0111\u1ec3 l\u1ea5y n\u0103ng l\u01b0\u1ee3ng s\u1eed d\u1ee5ng tuy\u1ec7t chi\u00eau.@{i}@- X\u1ebfp bi\u1ec3u t\u01b0\u1ee3ng b\u00e1nh ch\u01b0ng \u0111\u1ec3 l\u1ea5y l\u01b0\u01a1ng th\u1ef1c.@{i}@- X\u1ebfp bi\u1ec3u t\u01b0\u1ee3ng th\u1ecfi v\u00e0ng \u0111\u1ec3 l\u1ea5y ti\u1ec1n v\u00e0ng.@{i}@- X\u1ebfp bi\u1ec3u t\u01b0\u1ee3ng cu\u1ed1n s\u00e1ch \u0111\u1ec3 l\u1ea5y kinh nghi\u1ec7m.@{i}@- X\u1ebfp bi\u1ec3u t\u01b0\u1ee3ng tr\u00e1i tim \u0111\u1ec3 ph\u1ee5c h\u1ed3i sinh l\u1ef1c (m\u00e1u).@{i}@- Ki\u1ebfm l\u1eeda c\u00f3 th\u1ec3 x\u1ebfp c\u00f9ng v\u1edbi ki\u1ebfm th\u01b0\u1eddng.@- Ki\u1ebfm l\u1eeda c\u00f3 l\u1ef1c t\u1ea5n c\u00f4ng m\u1ea1nh h\u01a1n v\u00e0 l\u00e0m n\u1ed5 1 \u00f4 bao quanh n\u00f3.@- H\u1ebft sinh l\u1ef1c s\u1ebd b\u1ecb thua tr\u1eadn.@- L\u01b0\u01a1ng th\u1ef1c ti\u00eau hao trong l\u01b0\u1ee3t \u0111i c\u1ee7a m\u1ed7i \u0111\u1ed1i ph\u01b0\u01a1ng.@- \u0110i\u1ec3m kinh nghi\u1ec7m d\u00f9ng \u0111\u1ec3 t\u0103ng c\u1ea5p \u0111\u1ed9.@- C\u1ea5p \u0111\u1ed9 c\u00e0ng cao th\u00ec l\u1ef1c t\u1ea5n c\u00f4ng v\u00e0 sinh l\u1ef1c c\u00e0ng l\u1edbn.@- Ti\u1ec1n v\u00e0ng d\u00f9ng \u0111\u1ec3 mua v\u1eadt d\u1ee5ng h\u1ed7 tr\u1ee3 trong c\u1eeda h\u00e0ng.@- N\u1ebfu x\u1ebfp sai bi\u1ec3u t\u01b0\u1ee3ng s\u1ebd b\u1ecb m\u1ea5t l\u01b0\u1ee3t \u0111i v\u00e0 m\u1ea5t sinh l\u1ef1c.@*Ch\u00fa \u00fd: V\u1eadt d\u1ee5ng trong c\u1eeda h\u00e0ng r\u1ea5t h\u1eefu d\u1ee5ng trong c\u00e1c tr\u1eadn chi\u1ebfn. B\u1ea1n n\u00ean mua th\u01b0\u1eddng xuy\u00ean v\u00e0 s\u1eed d\u1ee5ng khi c\u1ea7n thi\u1ebft.", null, di2, null, null);
        dj2.c(new da("\u0110\u00f3ng", 4));
        dj2.a(this);
        cz.d().a(dj2, true);
    }

    public void f(int n2, int n3) {
    }

    public void a(String string, int n2) {
        int n3 = this.v[n2];
        switch (n3) {
            case 0: {
                if (com.mic.smsgame.suquan.f.e()) {
                    cf.a().d();
                    cz.d().e.a(2, 5, false);
                    break;
                }
                cf.a().d();
                cz.d().e.a(2, 7, false);
                break;
            }
            case 1: {
                if (!this.w) {
                    this.h(9, 4);
                    return;
                }
                cf.a().d();
                bg.k();
                bg.x();
                cz.d().a(new df(true), false);
                break;
            }
            case 2: {
                this.x();
                break;
            }
            case 3: {
                this.y();
                break;
            }
            case 4: {
                this.e(2);
                break;
            }
            case 5: {
                this.z();
                break;
            }
            case 6: {
                this.a((Image)null);
                break;
            }
            case 7: {
                this.C();
                break;
            }
            case 8: {
                this.F();
                break;
            }
            default: {
                this.a(com.mic.smsgame.suquan.h.f, null);
            }
        }
    }
}

