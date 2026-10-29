/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class w
extends ds {
    private Image e;
    private int f;
    String[][] a = new String[][]{{"T\u01b0\u1edbng qu\u00e2n h\u00e3y l\u00e0m theo ch\u1ec9 d\u1eabn c\u1ee7a t\u00f4i.", "H\u00e3y b\u1ea5m ph\u00edm (5) ho\u1eb7c ph\u00edm OK \u0111\u1ec3 ch\u1ecdn thanh ki\u1ebfm0*#"}, {"B\u1ea5m ph\u00edm (8) ho\u1eb7c ph\u00edm xu\u1ed1ng \u0111\u1ec3 di chuy\u1ec3n xu\u1ed1ng"}, {"Kh\u00e1 l\u1eafm!", "\u0110\u1ec3 t\u1ea5n c\u00f4ng k\u1ebb th\u00f9 h\u00e3y x\u1ebfp 3 thanh ki\u1ebfm c\u00e1c lo\u1ea1i s\u00e1t nhau theo h\u00e0ng ngang ho\u1eb7c d\u1ecdc.", "B\u00e2y gi\u1edd \u0111\u1ebfn l\u01b0\u1ee3t ta..."}, {"T\u01b0\u1edbng qu\u00e2n \u0111\u00e3 b\u1ecb th\u01b0\u01a1ng", "\u0110\u1ec3 h\u1ed3i ph\u1ee5c sinh l\u1ef1c h\u00e3y x\u1ebfp 3 tr\u00e1i tim2*#"}, {"T\u1ed1t l\u1eafm.", "Ph\u00eda d\u01b0\u1edbi c\u00f3 1 thanh m\u00e0u xanh l\u00e1 t\u01b0\u1ee3ng tr\u01b0ng cho l\u01b0\u01a1ng th\u1ef1c, h\u1ebft l\u01b0\u01a1ng th\u1ef1c th\u00ec s\u1ebd thua cu\u1ed9c.", "\u0110\u1ec3 l\u1ea5y l\u01b0\u01a1ng th\u1ef1c h\u00e3y x\u1ebfp 3 b\u00e1nh ch\u01b0ng1*#"}, {"R\u1ea5t t\u1ed1t!", "Thanh m\u00e0u xanh ng\u1ecdc b\u00edch l\u00e0 n\u0103ng l\u01b0\u1ee3ng", "\u0110\u1ec3 l\u1ea5y n\u0103ng l\u01b0\u1ee3ng h\u00e3y x\u1ebfp 3 bi\u1ec3u t\u01b0\u1ee3ng \u00e2m d\u01b0\u01a1ng3*#"}, {"C\u00e2y n\u0103ng l\u01b0\u1ee3ng c\u00f3 3 ph\u1ea7n \u1ee9ng v\u1edbi 3 tuy\u1ec7t chi\u00eau", "T\u01b0\u1edbng qu\u00e2n h\u00e3y m\u1edf b\u1ea3ng ph\u00e9p thu\u1eadt b\u1eb1ng c\u00e1ch nh\u1ea5n ph\u00edm 'T.Chi\u00eau' ,ch\u1ecdn m\u1ed9t lo\u1ea1i tuy\u1ec7t chi\u00eau v\u00e0 b\u1ea5m 'D\u00f9ng'."}, {"Tuy\u1ec7t v\u1eddi!", "T\u01b0\u1edbng qu\u00e2n s\u1ebd \u0111\u01b0\u1ee3c th\u0103ng c\u1ea5p khi thu \u0111\u01b0\u1ee3c nhi\u1ec1u kinh nghi\u1ec7m chi\u1ebfn \u0111\u1ea5u, c\u1ea5p c\u00e0ng cao th\u00ec ch\u1ec9 s\u1ed1 t\u1ea5n c\u00f4ng v\u00e0 sinh l\u1ef1c c\u00e0ng cao.", "\u0110\u1ec3 l\u1ea5y kinh nghi\u1ec7m chi\u1ebfn \u0111\u1ea5u h\u00e3y x\u1ebfp c\u00e1c bi\u1ec3u t\u01b0\u1ee3ng cu\u1ed1n s\u00e1ch.5*#", "Ngo\u00e0i ra, \u0111\u1ec3 mua c\u00e1c v\u1eadt ph\u1ea9m h\u1ed7 tr\u1ee3 t\u1ea1i c\u1eeda h\u00e0ng t\u01b0\u1edbng qu\u00e2n ph\u1ea3i thu th\u1eadp c\u00e1c th\u1ecfi v\u00e0ng.4*#", "N\u1ebfu x\u1ebfp 4 bi\u1ec3u t\u01b0\u1ee3ng tr\u1edf l\u00ean s\u1ebd \u0111\u01b0\u1ee3c \u0111i th\u00eam 1 l\u01b0\u1ee3t.", "V\u00e0 x\u1ebfp 5 bi\u1ec3u t\u01b0\u1ee3ng tr\u1edf l\u00ean s\u1ebd nh\u1eadn \u0111\u01b0\u1ee3c th\u00eam c\u00e1c v\u1eadt ph\u1ea9m h\u1ed7 tr\u1ee3", "B\u00e2y gi\u1edd h\u00e3y th\u1eed x\u1ebfp 5 bi\u1ec3u t\u01b0\u1ee3ng1*#"}, {"T\u01b0\u1edbng qu\u00e2n \u0111\u00e3 \u0111\u01b0\u1ee3c th\u01b0\u1edfng 1 v\u1eadt ph\u1ea9m, h\u00e3y b\u1ea5m ph\u00edm # \u0111\u1ec3 ch\u1ecdn v\u00e0 s\u1eed d\u1ee5ng v\u1eadt ph\u1ea9m."}, {"Ch\u00fa \u00fd, m\u1ed9t l\u01b0\u1ee3t \u0111i ph\u1ea3i gh\u00e9p \u00edt nh\u1ea5t 3 bi\u1ec3u t\u01b0\u1ee3ng gi\u1ed1ng nhau, n\u1ebfu th\u1ef1c hi\u1ec7n kh\u00f4ng \u0111\u00fang s\u1ebd b\u1ecb m\u1ea5t l\u01b0\u1ee3t \u0111i v\u00e0 ti\u00eau hao sinh l\u1ef1c. H\u00e3y c\u1ea9n tr\u1ecdng."}};
    private int g;
    private int h;
    private int i;
    private String[] j;
    private int k;
    private int l;
    private int[] m;
    int[][] b;
    int c;
    cm d;

    public void a() {
        ++this.c;
    }

    public int[] b() {
        return this.b[this.c];
    }

    public w() {
        int[] nArray = new int[20];
        nArray[1] = 6;
        nArray[3] = 7;
        nArray[5] = 2;
        nArray[7] = 1;
        nArray[9] = 1;
        nArray[11] = 1;
        nArray[13] = 3;
        nArray[15] = 1;
        nArray[17] = 5;
        nArray[19] = 4;
        this.m = nArray;
        this.b = new int[][]{{2, 4, 3, 4}, {6, 9, 6, 8}, {7, 7, 6, 7}, {8, 4, 9, 4}, {6, 3, 7, 3}, {2, 2, 2, 2}, {4, 3, 4, 2}, {2, 2, 2, 2}};
        this.d = new cm("/f/ari_b");
        this.p = cx.l + 32;
        this.q = cx.m + 225;
        this.r = 176;
        this.u = false;
        this.i = this.d.a() + 2;
        this.l = -1;
    }

    public void c() {
        ++this.k;
    }

    public void d() {
        ++this.l;
        if (this.l >= this.a.length) {
            return;
        }
        this.h = this.a[this.l].length;
        this.g = 0;
        this.u = true;
        this.a(this.a[this.l][this.g]);
    }

    public void e() {
        this.h = 1;
        this.g = this.a[this.l].length - 1;
        this.u = true;
        this.a(this.a[this.l][this.g]);
    }

    public void f() {
        ++this.g;
        if (this.g >= this.h) {
            this.u = false;
        } else {
            this.a(this.a[this.l][this.g]);
        }
    }

    private void a(String string) {
        int n2;
        this.e = null;
        if (string.substring(string.length() - 2).equals("*#")) {
            n2 = Integer.parseInt(string.substring(string.length() - 3, string.length() - 2));
            this.e = bs.a().d[n2];
            string = string.substring(0, string.length() - 3);
        }
        n2 = this.r - 4;
        this.j = this.d.a(string, n2);
        this.s = this.i * this.j.length;
        this.s = this.e != null ? (this.s += 15) : (this.s += 10);
        this.f = this.d.a(this.j[this.j.length - 1]);
    }

    public int g() {
        return this.m[this.k];
    }

    private final void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        graphics.setColor(n8);
        graphics.fillRect(n2 + 4, n3, n4 - 8, n5);
        graphics.fillRect(n2, n3 + 4, n4, n5 - 8);
        graphics.fillRect(n2 + 1, n3 + 2, n4 - 2, n5 - 4);
        graphics.fillRect(n2 + 2, n3 + 1, n4 - 4, n5 - 2);
        graphics.setColor(n7);
        graphics.fillRect(n2 + 2, n3 + 2, n4 - 4, n5 - 4);
        graphics.fillRect(n2 + 4, n3 + 1, n4 - 8, n5 - 2);
        graphics.fillRect(n2 + 1, n3 + 4, n4 - 2, n5 - 8);
        if (n6 >= 0) {
            graphics.fillTriangle(n6 - 5, n3 + n5 - 1, n6 + 5, n3 + n5 - 1, n6, n3 + n5 + 7);
            graphics.setColor(n8);
            graphics.drawLine(n6, n3 + n5 + 7, n6 - 5, n3 + n5 - 1);
            graphics.drawLine(n6, n3 + n5 + 7, n6 + 5, n3 + n5 - 1);
        }
    }

    public void h() {
        if (!this.u) {
            return;
        }
    }

    public void a(Graphics graphics, int n2, int n3) {
        if (!this.u) {
            return;
        }
        int n4 = this.q - this.s;
        int n5 = this.p + 4;
        this.a(graphics, this.p, n4, this.r, this.s, this.p + this.r - 25, 0xFFFFFF, 0);
        n4 += 5;
        int n6 = 0;
        while (n6 < this.j.length) {
            this.d.a(graphics, this.j[n6], n5, n4, 0);
            n4 += this.i;
            ++n6;
        }
        if (this.e != null) {
            graphics.drawImage(this.e, n5 + this.f - 1, n4 - this.i - 2, 0);
        }
    }
}

