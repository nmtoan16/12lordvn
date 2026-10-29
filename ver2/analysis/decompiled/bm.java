/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import com.mic.smsgame.suquan.e;
import com.mic.smsgame.suquan.i;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class bm
extends ds
implements e {
    private Image[] a;
    private Image[] b;
    private Image[] c;
    private i[][] d;
    private byte[][] e;
    private byte[] f;
    private int[] g;

    public bm(int n2, int n3) {
        int n4;
        int n5;
        this.b();
        this.d = new i[8][8];
        byte[][] byArrayArray = new byte[2][];
        byte[] byArray = new byte[12];
        byArray[3] = 1;
        byArray[4] = 1;
        byArray[5] = 1;
        byArray[6] = 2;
        byArray[7] = 2;
        byArray[8] = 2;
        byArray[9] = 3;
        byArray[10] = 3;
        byArray[11] = 3;
        byArrayArray[0] = byArray;
        byArrayArray[1] = new byte[1];
        this.e = byArrayArray;
        this.g = new int[]{4, 4, 4, 4, 4, 4, 4};
        byte[] byArray2 = new byte[8];
        byArray2[2] = 1;
        byArray2[3] = 1;
        byArray2[4] = 2;
        byArray2[5] = 2;
        byArray2[6] = 3;
        byArray2[7] = 3;
        this.f = byArray2;
        byte[][] byArrayArray2 = new byte[7][];
        byArrayArray2[0] = new byte[]{-1};
        byArrayArray2[1] = new byte[1];
        byte[] byArray3 = new byte[8];
        byArray3[2] = 1;
        byArray3[3] = 1;
        byArray3[4] = 2;
        byArray3[5] = 2;
        byArray3[6] = 3;
        byArray3[7] = 3;
        byArrayArray2[2] = byArray3;
        byArrayArray2[3] = new byte[1];
        byArrayArray2[4] = new byte[1];
        byArrayArray2[5] = new byte[1];
        byArrayArray2[6] = new byte[1];
        byte[][] byArrayArray3 = byArrayArray2;
        this.p = n2;
        this.q = n3;
        this.r = 168;
        this.s = 168;
        n3 += 10;
        int n6 = 0;
        while (n6 < 8) {
            n5 = n2 + 10;
            n4 = 0;
            while (n4 < 8) {
                this.d[n6][n4] = new i(n5, n3);
                this.d[n6][n4].j(3);
                this.d[n6][n4].a(byArrayArray3);
                n5 += 21;
                ++n4;
            }
            n3 += 21;
            ++n6;
        }
        n6 = 4;
        n5 = 4;
        n4 = 0;
        int n7 = 1000;
        int n8 = n7 + 32 + 3;
        int n9 = n6 - 1;
        while (n9 > -1) {
            int n10 = 0;
            int n11 = n7;
            int n12 = n5 - 1;
            while (n12 > -1) {
                this.d[n9][n12].a(n11, n8);
                this.d[n6 + n4][n12].a(n11, n8);
                this.d[n9][n5 + n10].a(n11, n8);
                this.d[n6 + n4][n5 + n10].a(n11, n8);
                n11 += 2;
                ++n10;
                --n12;
            }
            ++n4;
            n7 += 2;
            --n9;
        }
    }

    public void a(int n2, int n3, int n4) {
        this.d[n2 -= 2][n3 -= 2].a(this.p + n3 * 21, this.q + n2 * 21, this.a[n4]);
    }

    public void a(int n2, int n3, int n4, int n5, int n6, int n7) {
        int n8 = this.p + (n3 -= 2) * 21;
        int n9 = this.q + (n2 -= 2) * 21;
        int n10 = this.p + (n5 -= 2) * 21;
        int n11 = this.q + (n4 -= 2) * 21;
        this.d[n2][n3].a(n10, n11, n8, n9, this.a[n7]);
        this.d[n4][n5].a(n8, n9, n10, n11, this.a[n6]);
    }

    public void b(int n2, int n3, int n4, int n5, int n6, int n7) {
        int n8 = this.p + (n3 -= 2) * 21;
        int n9 = this.q + (n2 -= 2) * 21;
        int n10 = this.p + (n5 -= 2) * 21;
        int n11 = this.q + (n4 -= 2) * 21;
        this.d[n4][n5].a(n8, n9, n10, n11, this.a[n6], n7);
    }

    public int a(cc cc2, int n2, int n3) {
        int n4 = cc2.a - 2;
        int n5 = cc2.b - 2;
        int n6 = 0;
        int n7 = this.q + n4 * 21;
        int n8 = cc2.d > cc2.c ? cc2.d : cc2.c;
        byte[] byArray = null;
        int n9 = 4;
        Image image = null;
        if (n8 > 3 || cc2.f >= 7) {
            byArray = this.e[0];
            n9 = this.g[cc2.e];
            image = this.c[cc2.e];
        } else {
            byArray = this.f;
            image = this.b[cc2.e];
            n9 = 4;
        }
        int n10 = n4 + cc2.c;
        int n11 = n5 + cc2.d;
        int n12 = n4;
        while (n12 < n10) {
            n6 = this.p + n5 * 21;
            int n13 = n5;
            while (n13 < n11) {
                byte[][] byArray2 = this.d[n12][n13].d();
                byArray2[2] = byArray;
                this.d[n12][n13].a(n6, n7, image, n9);
                n6 += 21;
                ++n13;
            }
            n7 += 21;
            ++n12;
        }
        return byArray.length;
    }

    public i a(int n2, int n3) {
        return this.d[n2][n3];
    }

    private void b() {
        this.a = bs.a().d;
        this.b = bs.a().e;
        this.c = bs.a().f;
    }

    public void a(Graphics graphics, int n2, int n3) {
        int n4 = 0;
        while (n4 < 8) {
            int n5 = 0;
            while (n5 < 8) {
                this.d[n4][n5].a(graphics);
                ++n5;
            }
            ++n4;
        }
    }

    public void a() {
        int n2 = 0;
        while (n2 < 8) {
            int n3 = 0;
            while (n3 < 8) {
                this.d[n2][n3].e();
                ++n3;
            }
            ++n2;
        }
    }
}

