/*
 * Decompiled with CFR 0.152.
 */
import com.mic.smsgame.suquan.a;
import com.mic.smsgame.suquan.d;
import com.mic.smsgame.suquan.e;
import com.mic.smsgame.suquan.j;
import com.mic.smsgame.suquan.w;

public class cq
implements e {
    private int[][] e;
    private bn[] f;
    private int g;
    private int k;
    private int l = 2;
    private int m = 2;
    public dx[] a;
    public int b;
    public int c;
    private j n;
    public boolean d = false;
    private boolean o;

    public cq(int n2, c c2, c c3) {
        this.g = n2;
        this.d = n2 == 1;
        this.e = new int[12][12];
        this.i();
        this.f = new bn[2];
        this.f[0] = this.a(c2);
        this.f[1] = this.a(c3);
        int n3 = this.f[0].e() > this.f[1].e() ? this.f[0].e() : this.f[1].e();
        int n4 = this.f[0].m() < this.f[1].m() ? this.f[0].m() : this.f[1].m();
        this.b = n4 / 2;
        this.c = 240;
        int n5 = n3 / n4 * 3 * 120;
        int n6 = 0;
        while (n6 < this.f.length) {
            this.f[n6].h(n5);
            ++n6;
        }
        this.b();
        if (this.d) {
            this.f[0].e(9);
        }
    }

    public int a(byte[] byArray, int n2) {
        int n3 = n2;
        int n4 = 2;
        while (n4 < 10) {
            int n5 = 2;
            while (n5 < 10) {
                byArray[n2++] = (byte)this.e[n4][n5];
                ++n5;
            }
            ++n4;
        }
        System.arraycopy(ai.a(this.l), 0, byArray, n2, 4);
        System.arraycopy(ai.a(this.m), 0, byArray, n2 += 4, 4);
        n2 += 4;
        n4 = 0;
        while (n4 < this.f.length) {
            n2 += this.f[n4].a(byArray, n2);
            ++n4;
        }
        return n2 - n3;
    }

    public int b(byte[] byArray, int n2) {
        int n3 = n2;
        int n4 = 2;
        while (n4 < 10) {
            int n5 = 2;
            while (n5 < 10) {
                this.e[n4][n5] = byArray[n2++];
                ++n5;
            }
            ++n4;
        }
        this.l = ai.a(byArray, n2);
        this.m = ai.a(byArray, n2 += 4);
        n2 += 4;
        n4 = 0;
        while (n4 < this.f.length) {
            n2 += this.f[n4].b(byArray, n2);
            ++n4;
        }
        return n2 - n3;
    }

    public int a() {
        return this.k;
    }

    public void a(int n2) {
        this.k = n2;
    }

    public void b() {
        int[] nArray = bg.v();
        if (nArray == null) {
            nArray = new int[6];
        }
        nArray[5] = 0;
        this.a = new dx[nArray.length];
        int n2 = 0;
        while (n2 < this.a.length) {
            this.a[n2] = new dx();
            this.a[n2].a = n2;
            this.a[n2].b = nArray[n2];
            ++n2;
        }
        this.d(5);
        this.c(5);
        n2 = 0;
        while (n2 < this.f.length) {
            this.f[n2].a();
            ++n2;
        }
    }

    private bn a(c c2) {
        return new bn(c2);
    }

    public int c() {
        return this.g;
    }

    public bn[] d() {
        return this.f;
    }

    public bn b(int n2) {
        return this.f[n2];
    }

    public int a(int n2, int n3) {
        return this.e[n2][n3];
    }

    public int[][] e() {
        return this.e;
    }

    public int f() {
        return this.l;
    }

    public void c(int n2) {
        this.l = n2;
    }

    public int g() {
        return this.m;
    }

    public void d(int n2) {
        this.m = n2;
    }

    public boolean h() {
        return this.o;
    }

    public void a(boolean bl2) {
        this.o = bl2;
    }

    public boolean b(int n2, int n3) {
        if (n2 < 2) {
            n2 = 9;
        } else if (n2 >= 10) {
            n2 = 2;
        }
        if (n3 < 2) {
            n3 = 9;
        } else if (n3 >= 10) {
            n3 = 2;
        }
        if (this.g(n2, n3)) {
            this.l = n2;
            this.m = n3;
            return true;
        }
        return false;
    }

    public boolean c(int n2, int n3) {
        if (!this.o) {
            return true;
        }
        return n2 >= 2 && n2 < 10 && n3 >= 2 && n3 < 10;
    }

    private boolean g(int n2, int n3) {
        return this.e[n2][n3] >= 0;
    }

    public void i() {
        this.n = this.d ? new d() : new w();
        int n2 = 0;
        int n3 = 11;
        while (n3 >= 0) {
            this.e[n2][n3] = 8;
            --n3;
        }
        n2 = 11;
        n3 = 11;
        while (n3 >= 0) {
            this.e[n2][n3] = 8;
            --n3;
        }
        n3 = 0;
        n2 = 11;
        while (n2 >= 0) {
            this.e[n2][n3] = 9;
            --n2;
        }
        n3 = 11;
        n2 = 11;
        while (n2 >= 0) {
            this.e[n2][n3] = 9;
            --n2;
        }
        n2 = 1;
        n3 = 10;
        while (n3 >= 1) {
            this.e[n2][n3] = 10;
            --n3;
        }
        n2 = 10;
        n3 = 10;
        while (n3 >= 1) {
            this.e[n2][n3] = 10;
            --n3;
        }
        n3 = 1;
        n2 = 10;
        while (n2 >= 1) {
            this.e[n2][n3] = 11;
            --n2;
        }
        n3 = 10;
        n2 = 10;
        while (n2 >= 1) {
            this.e[n2][n3] = 11;
            --n2;
        }
    }

    public void j() {
        this.n.a(this.e);
        cq.a(this.e);
    }

    public void d(int n2, int n3) {
        this.n.a(this.e, n2, n3);
    }

    public int e(int n2, int n3) {
        if (this.e[n2][n3] >= 7) {
            return 0;
        }
        int n4 = -16777216;
        int n5 = 1;
        int n6 = 1;
        byte by2 = com.mic.smsgame.suquan.e.i[this.e[n2][n3]];
        while (by2 == com.mic.smsgame.suquan.e.i[this.e[n2][n3 - n6]]) {
            ++n6;
            ++n5;
        }
        n4 |= (n6 - 1 & 0xFF) << 16;
        n6 = 1;
        while (by2 == com.mic.smsgame.suquan.e.i[this.e[n2][n3 + n6]]) {
            ++n6;
            ++n5;
        }
        n4 |= (n6 - 1 & 0xFF) << 8;
        return n4 |= n5 & 0xFF;
    }

    public int f(int n2, int n3) {
        if (this.e[n2][n3] >= 7) {
            return 0;
        }
        int n4 = 0;
        int n5 = 1;
        int n6 = 1;
        byte by2 = com.mic.smsgame.suquan.e.i[this.e[n2][n3]];
        while (by2 == com.mic.smsgame.suquan.e.i[this.e[n2 - n6][n3]]) {
            ++n6;
            ++n5;
        }
        n4 |= (n6 - 1 & 0xFF) << 16;
        n6 = 1;
        while (by2 == com.mic.smsgame.suquan.e.i[this.e[n2 + n6][n3]]) {
            ++n6;
            ++n5;
        }
        n4 |= (n6 - 1 & 0xFF) << 8;
        return n4 |= n5 & 0xFF;
    }

    public a a(int n2, int n3, int n4, int n5) {
        a a2 = this.c(n2, n3, n4, n5);
        return a2;
    }

    public void b(int n2, int n3, int n4, int n5) {
        this.a(this.e, n2, n3, n4, n5);
    }

    private void a(int[][] nArray, int n2, int n3, int n4, int n5) {
        int n6 = nArray[n2][n3];
        nArray[n2][n3] = nArray[n4][n5];
        nArray[n4][n5] = n6;
    }

    private a c(int n2, int n3, int n4, int n5) {
        block7: {
            block6: {
                if (this.e[n2][n3] != this.e[n4][n5]) break block6;
                return null;
            }
            if (this.e[n2][n3] < 7 && this.e[n4][n5] < 7) break block7;
            return null;
        }
        try {
            this.a(this.e, n2, n3, n4, n5);
            boolean bl2 = false;
            int n6 = this.e(n2, n3);
            int n7 = this.f(n2, n3);
            int n8 = this.e(n4, n5);
            int n9 = this.f(n4, n5);
            if ((n6 & 0xFF) >= 3 || (n7 & 0xFF) >= 3 || (n8 & 0xFF) >= 3 || (n9 & 0xFF) >= 3) {
                bl2 = true;
            }
            this.a(this.e, n2, n3, n4, n5);
            if (bl2) {
                int[] nArray = new int[]{n6, n7, n8, n9};
                a a2 = new a();
                a2.a = n2;
                a2.b = n3;
                a2.c = n4;
                a2.d = n5;
                a2.e = nArray;
                return a2;
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public di k() {
        di di2 = new di();
        int n2 = 2;
        while (n2 < 10) {
            int n3 = 2;
            while (n3 < 10) {
                a a2 = this.c(n2, n3, n2 + 1, n3);
                if (a2 != null) {
                    di2.a(a2);
                }
                if ((a2 = this.c(n2, n3, n2, n3 + 1)) != null) {
                    di2.a(a2);
                }
                ++n3;
            }
            ++n2;
        }
        return di2;
    }

    public static void a(int[][] nArray) {
        int n2 = 0;
        while (n2 < 12) {
            int n3 = 0;
            while (n3 < 12) {
                System.out.print(String.valueOf(nArray[n2][n3]) + "\t");
                ++n3;
            }
            System.out.println();
            ++n2;
        }
    }
}

