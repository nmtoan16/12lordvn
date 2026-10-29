/*
 * Decompiled with CFR 0.152.
 */
public class c {
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    public int[] f;
    public int g;
    public int h;

    public void a() {
        this.b();
        ++this.e;
        this.d = this.h + dd.v(this.e);
        int n2 = dd.w(this.e) + this.g - this.b;
        this.b = this.g + dd.w(this.e);
        this.c += n2;
    }

    public void b() {
        this.d = this.h + dd.v(this.e);
        this.b = this.g + dd.w(this.e);
    }

    public c c() {
        c c2 = new c();
        c2.a = this.a;
        c2.b = this.b;
        c2.c = this.c;
        c2.d = this.d;
        c2.e = this.e;
        c2.h = this.h;
        c2.g = this.g;
        return c2;
    }

    public int a(byte[] byArray, int n2) {
        int n3 = n2;
        System.arraycopy(ai.a(this.a), 0, byArray, n2, 4);
        System.arraycopy(ai.a(this.b), 0, byArray, n2 += 4, 4);
        System.arraycopy(ai.a(this.c), 0, byArray, n2 += 4, 4);
        System.arraycopy(ai.a(this.d), 0, byArray, n2 += 4, 4);
        System.arraycopy(ai.a(this.e), 0, byArray, n2 += 4, 4);
        System.arraycopy(ai.a(this.g), 0, byArray, n2 += 4, 4);
        System.arraycopy(ai.a(this.h), 0, byArray, n2 += 4, 4);
        System.arraycopy(ai.a(this.f.length), 0, byArray, n2 += 4, 4);
        n2 += 4;
        int n4 = 0;
        while (n4 < this.f.length) {
            System.arraycopy(ai.a(this.f[n4]), 0, byArray, n2, 4);
            n2 += 4;
            ++n4;
        }
        return n2 - n3;
    }

    public int b(byte[] byArray, int n2) {
        int n3 = n2;
        this.a = ai.a(byArray, n2);
        this.b = ai.a(byArray, n2 += 4);
        this.c = ai.a(byArray, n2 += 4);
        this.d = ai.a(byArray, n2 += 4);
        this.e = ai.a(byArray, n2 += 4);
        this.g = ai.a(byArray, n2 += 4);
        this.h = ai.a(byArray, n2 += 4);
        this.f = new int[ai.a(byArray, n2 += 4)];
        n2 += 4;
        int n4 = 0;
        while (n4 < this.f.length) {
            this.f[n4] = ai.a(byArray, n2);
            n2 += 4;
            ++n4;
        }
        return n2 - n3;
    }
}

