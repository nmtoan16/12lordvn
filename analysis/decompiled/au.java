/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class au
extends ab {
    private int v;
    private static int[] w;
    private static int[] x;
    private static int[] y;
    private static int[] z;
    private static int[] A;

    static {
        int[] nArray = new int[5];
        nArray[1] = 17;
        nArray[2] = 51;
        nArray[3] = 68;
        nArray[4] = 131;
        w = nArray;
        x = new int[]{17, 34, 17, 63, 62};
        int[] nArray2 = new int[5];
        nArray2[0] = 19;
        nArray2[1] = 20;
        nArray2[2] = 17;
        nArray2[3] = 12;
        y = nArray2;
        z = new int[]{12, 11, 4, 27, 56};
        int[] nArray3 = new int[5];
        nArray3[0] = 91;
        nArray3[1] = 74;
        nArray3[2] = 56;
        nArray3[3] = 1;
        A = nArray3;
    }

    public au(Image image, int n2) {
        this.n = image;
    }

    public void a(int n2, int n3, int n4, int n5, int n6) {
        this.d(n2, n3);
        this.c(n4, n5);
        this.k = this.b(n2, n4, 35);
        this.l = this.b(n3, n5, 35);
        this.v = n6;
        this.u = true;
        this.t = 33;
        this.g = 0;
    }

    public void a() {
        if (this.v > 0) {
            --this.v;
            return;
        }
        if (this.g >= this.d[0].length - 1) {
            this.u = false;
        }
    }

    public void a(Graphics graphics, int n2, int n3) {
        if (this.v < 1) {
            if (!this.u) {
                return;
            }
            byte by2 = this.d[this.c][this.g];
            if (this.n != null && this.g > -1 && by2 > -1) {
                y.a(graphics, this.n, y[by2], w[by2], z[by2], x[by2], this.p + n2, this.q - A[by2] + n3, this.t);
            }
        }
    }
}

