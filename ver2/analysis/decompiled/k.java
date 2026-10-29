/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Image;

public class k
extends dq {
    private final int a;
    private final int v;
    private int w;

    public k(int n2, int n3) {
        this.a = 0;
        this.v = 1;
        this.d(n2, n3);
        byte[][] byArrayArray = new byte[2][];
        byte[] byArray = new byte[6];
        byArray[3] = 2;
        byArray[4] = 1;
        byArray[5] = 2;
        byArrayArray[0] = byArray;
        byArrayArray[1] = new byte[]{2};
        this.d = byArrayArray;
        this.u = false;
        this.t = 3;
    }

    public int a(Image image) {
        this.a(image, 3);
        this.u = true;
        this.a_(0);
        this.g = 0;
        this.w = 45;
        return this.d[0].length + this.w;
    }

    public void a() {
        switch (this.c) {
            case 0: {
                if (this.g < this.d[0].length - 1) break;
                this.a_(1);
                break;
            }
            case 1: {
                if (this.w <= 0) break;
                --this.w;
                if (this.w != 0) break;
                this.n = null;
                this.u = false;
            }
        }
    }
}

