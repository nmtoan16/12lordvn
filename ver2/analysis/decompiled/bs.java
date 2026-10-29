/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import com.mic.smsgame.suquan.ad;
import com.mic.smsgame.suquan.k;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class bs {
    private static bs K;
    public Image a;
    public Image b = Image.createImage((int)cx.f, (int)cx.g);
    public Image[] c;
    public Image[] d;
    public Image[] e;
    public Image[] f;
    public Image[] g;
    public Image[] h;
    public Image[] i;
    public Image[] j;
    public Image[] k;
    public Image l;
    public Image m;
    public Image n;
    public Image o;
    public Image p;
    public Image q;
    public Image r;
    public Image s;
    public Image t;
    public Image u;
    public Image v;
    public Image w;
    public Image x;
    public Image y;
    public Image z;
    public Image A;
    public Image B;
    public Image C;
    public Image D;
    public Image E;
    public ad[] F;
    public ad G;
    public ad H;
    public ad I;
    public k J;

    public static bs a() {
        return K;
    }

    public static void b() {
        K = new bs();
    }

    public void a(int[] nArray) {
        int n2 = 0;
        while (n2 < nArray.length) {
            switch (nArray[n2]) {
                case 0: 
                case 1: {
                    if (this.r != null) break;
                    this.r = az.b("/fireball");
                    break;
                }
                case 2: {
                    if (this.s != null) break;
                    this.s = az.b("/hellfire");
                    break;
                }
                case 5: {
                    if (this.t != null) break;
                    this.t = az.b("/blastlighting");
                    break;
                }
                case 8: {
                    if (this.v == null) {
                        this.v = az.b("/frozen");
                    }
                }
                case 6: {
                    if (this.u != null) break;
                    this.u = az.b("/icebolt");
                    break;
                }
                case 7: {
                    if (this.w != null) break;
                    this.w = az.b("/healing");
                    break;
                }
                case 4: {
                    if (this.x != null) break;
                    this.x = az.b("/shieldlighting");
                    break;
                }
                case 3: {
                    if (this.y != null) break;
                    this.y = az.b("/chainlighting");
                    this.h = new Image[2];
                    this.h[0] = az.b("/chainlightingpart1");
                    this.h[1] = az.b("/chainlightingpart2");
                }
            }
            ++n2;
        }
    }

    public void c() {
        this.G = new ad("explodechess");
        if (!cx.V) {
            this.I = new ad("attack");
            this.H = new ad("useitem");
        }
        this.J = new k();
        this.J.a("win", 1);
    }

    public void d() {
        this.i();
        if (this.J != null) {
            this.J.a("win", 1);
            this.J.a(123015);
            this.J.d();
        }
    }

    public void e() {
        this.i();
        if (this.J != null) {
            this.J.a("lose", 1);
            this.J.d();
            this.J.a(155041);
        }
    }

    public void b(int[] nArray) {
        if (cx.V) {
            return;
        }
        int n2 = 0;
        while (n2 < nArray.length) {
            switch (nArray[n2]) {
                case 0: {
                    if (this.F[0] != null) break;
                    this.F[0] = new ad("fire1");
                    break;
                }
                case 1: {
                    if (this.F[1] != null) break;
                    this.F[1] = new ad("fire2");
                    break;
                }
                case 2: {
                    if (this.F[2] != null) break;
                    this.F[2] = new ad("fire3");
                    break;
                }
                case 4: {
                    if (this.F[4] != null) break;
                    this.F[4] = new ad("lightning2");
                    break;
                }
                case 3: {
                    if (this.F[3] != null) break;
                    this.F[3] = new ad("lightning1");
                    break;
                }
                case 5: {
                    if (this.F[5] != null) break;
                    this.F[5] = new ad("lightning3");
                    break;
                }
                case 6: {
                    if (this.F[6] != null) break;
                    this.F[6] = new ad("ice1");
                    break;
                }
                case 7: {
                    if (this.F[7] != null) break;
                    this.F[7] = new ad("ice2");
                    break;
                }
                case 8: {
                    if (this.F[8] != null) break;
                    this.F[8] = new ad("ice3");
                }
            }
            ++n2;
        }
    }

    private void c(ad ad2) {
        if (ad2 != null && ad2.e()) {
            ad2.d();
        }
    }

    private boolean l() {
        int n2 = this.F.length - 1;
        while (n2 >= 0) {
            if (this.F[n2] != null && this.F[n2].e()) {
                return true;
            }
            --n2;
        }
        return false;
    }

    public void a(int n2) {
        if (this.F == null) {
            return;
        }
        if (this.d(this.F[n2])) {
            return;
        }
        this.i();
        this.a(this.F[n2]);
    }

    public void f() {
        if (this.l()) {
            return;
        }
        if (this.d(this.I)) {
            return;
        }
        this.c(this.H);
        this.c(this.G);
        this.a(this.I);
    }

    public void g() {
        if (this.l()) {
            return;
        }
        if (this.d(this.I)) {
            return;
        }
        if (this.d(this.H)) {
            return;
        }
        if (!this.d(this.G)) {
            this.a(this.G);
        }
    }

    public void h() {
        if (this.l()) {
            return;
        }
        if (this.d(this.I)) {
            return;
        }
        if (this.d(this.H)) {
            return;
        }
        this.c(this.G);
        this.a(this.H);
    }

    public void i() {
        int n2 = this.F.length - 1;
        while (n2 >= 0) {
            this.c(this.F[n2]);
            --n2;
        }
        this.c(this.I);
        this.c(this.G);
        this.c(this.H);
    }

    private void m() {
        int n2 = this.F.length - 1;
        while (n2 >= 0) {
            this.b(this.F[n2]);
            --n2;
        }
        this.b(this.I);
        this.b(this.H);
        this.F = null;
        this.I = null;
        this.G = null;
        this.H = null;
    }

    public void j() {
        this.m();
        if (this.J != null) {
            this.J.b();
        }
    }

    private boolean d(ad ad2) {
        if (ad2 == null) {
            return false;
        }
        return ad2.e();
    }

    public void a(ad ad2) {
        if (ad2 == null) {
            return;
        }
        ad2.b();
    }

    public void b(ad ad2) {
        if (ad2 == null) {
            return;
        }
        ad2.a();
    }

    public void c(int[] nArray) {
        this.i = new Image[nArray.length];
        int n2 = 0;
        while (n2 < nArray.length) {
            switch (nArray[n2]) {
                case 0: {
                    this.i[n2] = az.b("/fireballicon");
                    break;
                }
                case 1: {
                    this.i[n2] = az.b("/meteoricon");
                    break;
                }
                case 2: {
                    this.i[n2] = az.b("/hellfireicon");
                    break;
                }
                case 5: {
                    this.i[n2] = az.b("/blastlightingicon");
                    break;
                }
                case 6: {
                    this.i[n2] = az.b("/icebolticon");
                    break;
                }
                case 8: {
                    this.i[n2] = az.b("/frozenicon");
                    break;
                }
                case 7: {
                    this.i[n2] = az.b("/healingicon");
                    break;
                }
                case 4: {
                    this.i[n2] = az.b("/shieldlightingicon");
                    break;
                }
                case 3: {
                    this.i[n2] = az.b("/chainlightingicon");
                }
            }
            ++n2;
        }
    }

    protected bs() {
        Graphics graphics = this.b.getGraphics();
        Image image = az.b("/bkboard");
        graphics.drawImage(image, cx.l, cx.m, 0);
        image = null;
        String[] stringArray = new String[]{"/crystalgray", "/crystalgreen", "/crystalred", "/crystalblue", "/crystalyellow", "/crystalviolet", ""};
        String[] stringArray2 = new String[]{"/sword", "/rice", "/heart", "/yinyang", "/gold", "/book", "/swordred"};
        String[] stringArray3 = new String[]{"/explodesword", "/exploderice", "/explodeheart", "/explodeyinyang", "/explodegold", "/explodebook", ""};
        String[] stringArray4 = new String[]{"", "/explodericex2", "/explodeheartx2", "/explodeyinyangx2", "/explodegoldx2", "/explodebookx2", ""};
        this.e = new Image[7];
        this.f = new Image[7];
        this.d = new Image[7];
        this.c = new Image[7];
        int n2 = this.e.length - 1;
        while (n2 > -1) {
            this.d[n2] = az.b(stringArray2[n2]);
            this.e[n2] = az.b(stringArray3[n2]);
            this.f[n2] = az.b(stringArray4[n2]);
            this.c[n2] = az.b(stringArray[n2]);
            --n2;
        }
        this.c[6] = this.c[0];
        this.f[6] = this.e[6] = this.e[0];
        this.f[0] = this.e[6];
        this.a = az.b("/star");
        this.l = az.b("/blood");
        this.m = az.b("/bloodext");
        this.n = az.b("/mana");
        this.o = az.b("/food");
        this.p = az.b("/bloodthrowaround");
        this.j = new Image[2];
        this.g = new Image[2];
        n2 = 0;
        while (n2 < this.j.length) {
            this.j[n2] = az.b("/arrowfocus" + (n2 + 1));
            this.g[n2] = az.b("/focus" + (n2 + 1));
            ++n2;
        }
        this.q = az.b("/zoomfocus1");
        this.k = new Image[2];
        this.k[0] = az.b("/frame1");
        this.k[1] = az.b("/frame2");
        this.z = az.b("/frame3");
        this.A = az.b("/focusitem");
        this.B = az.b("/numbersmall");
        this.D = az.b("/powerattack");
        this.C = az.b("/defenceshield");
        this.E = az.b("/itemshops");
        this.F = new ad[9];
    }

    public Image[] a(int n2, int n3) {
        Image[] imageArray = new Image[2];
        Image image = az.b("/avatar");
        imageArray[0] = Image.createImage((Image)image, (int)(n2 % 10 * 30), (int)(n2 / 10 * 30), (int)30, (int)30, (int)2);
        imageArray[1] = Image.createImage((Image)image, (int)(n3 % 10 * 30), (int)(n3 / 10 * 30), (int)30, (int)30, (int)0);
        return imageArray;
    }

    public static void k() {
        System.out.println("clear");
        K.j();
        K = null;
    }
}

