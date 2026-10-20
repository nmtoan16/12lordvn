/*
 * Decompiled with CFR 0.152.
 */
class dw {
    int[] a;
    long b = 0L;
    byte[] c = new byte[64];

    public dw() {
        this.a = new int[4];
        this.a[0] = 1732584193;
        this.a[1] = -271733879;
        this.a[2] = -1732584194;
        this.a[3] = 271733878;
    }

    public dw(dw dw2) {
        this();
        int n2 = 0;
        while (n2 < this.c.length) {
            this.c[n2] = dw2.c[n2];
            ++n2;
        }
        n2 = 0;
        while (n2 < this.a.length) {
            this.a[n2] = dw2.a[n2];
            ++n2;
        }
        this.b = dw2.b;
    }
}

