/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import com.mg.smsgame.main.b;
import com.mic.smsgame.suquan.h;
import com.mic.smsgame.suquan.u;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class ah
extends ae
implements b {
    private Image[] t;
    private Image u;
    private Image v;
    private Image[] w;
    private int x;
    private dy y;
    private int z;
    private int A;
    private String[] B;
    private String[] C;
    private String[] D;
    private int E;
    private int F;
    private int[] G;
    int[] e;
    int[] f;

    public ah(int[] nArray) {
        super(1);
        int[] nArray2 = new int[9];
        nArray2[0] = 1;
        nArray2[1] = 3;
        nArray2[2] = 5;
        nArray2[3] = 4;
        nArray2[5] = 2;
        nArray2[6] = 4;
        nArray2[8] = 3;
        this.e = nArray2;
        this.f = new int[]{1, 2, 2, 5, 1, 1, 5, 5, 2};
        this.t = bs.a().k;
        this.u = bs.a().z;
        this.v = bs.a().A;
        this.b_ = this.t[0].getWidth();
        this.c_ = 180;
        this.g = cx.f - 176 >> 1;
        this.a_ = cx.g - 180 >> 1;
        this.a_ -= 25;
        this.x = 0;
        this.z = 0;
        this.w = bs.a().i;
        this.C = new String[3];
        this.B = new String[3];
        this.G = nArray;
        int n2 = 0;
        while (n2 < nArray.length) {
            this.B[n2] = h.a[nArray[n2]];
            this.C[n2] = h.b[nArray[n2]];
            ++n2;
        }
        this.D = bz.a(this.C[this.z], this.b_ - 15);
        this.A = this.w.length;
        this.a(this);
    }

    protected void e() {
        this.E = this.E < 1 ? 8 : --this.E;
    }

    public void a(int n2, dy dy2, bn bn2) {
        if (this.F != bn2.m()) {
            this.F = bn2.m();
            int n3 = this.C.length - 1;
            while (n3 > -1) {
                String string = h.b[this.G[n3]];
                int n4 = string.indexOf("=");
                if (n4 >= 0) {
                    this.C[n3] = String.valueOf(string.substring(0, n4)) + this.F * this.e[this.G[n3]] / this.f[this.G[n3]] + string.substring(n4 + 1);
                }
                --n3;
            }
        }
        this.x = n2;
        this.y = dy2;
        this.z = 0;
        this.m();
        cz.d().a(this, false);
        this.c = true;
        this.b(new da("\u0110\u00f3ng", -2));
        if (this.z < n2) {
            this.a(new da("D\u00f9ng", -1));
            this.c(new da(" ", -1));
        }
        this.D = bz.a(this.C[this.z], this.b_ - 15);
    }

    public void a(int n2) {
        int n3 = this.z;
        switch (n2) {
            case 14: {
                this.z = (this.z - 1 + this.A) % this.A;
                break;
            }
            case 15: {
                this.z = (this.z + 1) % this.A;
            }
        }
        if (n3 != this.z) {
            this.u();
        }
    }

    private void u() {
        if (this.z < this.x) {
            this.a(new da("D\u00f9ng", -1));
            this.c(new da(" ", -1));
        } else {
            if (this.o()) {
                this.c = true;
            }
            this.a((de)null);
            this.c((de)null);
        }
        this.D = bz.a(this.C[this.z], this.b_ - 15);
    }

    public void a(int n2, int n3) {
        int n4 = this.g + 25;
        int n5 = this.a_ + 12;
        if (n3 >= n5 && n3 <= n5 + 36) {
            int n6 = 0;
            while (n6 < this.A) {
                if (n2 >= n4 && n2 <= n4 + 36 && n6 != this.z) {
                    this.z = n6;
                    this.u();
                }
                n4 += 45;
                ++n6;
            }
        }
    }

    public void a(Graphics graphics) {
        int n2 = this.g;
        int n3 = this.a_;
        com.mic.smsgame.suquan.u.a(graphics, n2, n3, 180, 7814005, this.t);
        graphics.drawImage(this.u, n2 + this.b_ / 2, n3 + 50, 17);
        int n4 = n2 + 25;
        int n5 = n3 + 12;
        int n6 = 0;
        while (n6 < this.A) {
            if (this.z == n6 && this.E % 2 == 0) {
                y.a(graphics, this.v, 0, 0, 36, 36, n4, n5, 0);
            } else {
                y.a(graphics, this.v, 36, 0, 36, 36, n4, n5, 0);
            }
            graphics.drawImage(this.w[n6], n4 + 18, n5 + 18, 3);
            n4 += 45;
            ++n6;
        }
        n4 = n2 + 25 + 45 * this.x;
        n5 = n3 + 12;
        n6 = this.x;
        while (n6 < this.A) {
            y.a(graphics, 0xFFACAC, n4 + 2, n5 + 2, 30, 30);
            n4 += 45;
            ++n6;
        }
        n4 = n2 + this.b_ / 2;
        n5 = n3 + 58;
        bz.c.a(graphics, this.B[this.z], n4, n5, 1);
        bz.a(graphics, bz.d, this.D, this.g + 11, n5 + 15, this.b_, 120, 0);
    }

    public void b(int n2, int n3) {
        switch (n3) {
            case -1: {
                this.y.a("skill", this.z);
            }
            case -2: {
                cz.d().a(false);
            }
        }
    }
}

