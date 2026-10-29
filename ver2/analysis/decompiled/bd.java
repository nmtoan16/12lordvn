/*
 * Decompiled with CFR 0.152.
 */
class bd {
    int a;
    int b;
    int[] c;
    byte[] d;

    bd() {
    }

    byte[] a() {
        int n2 = 12 + this.c.length * 4;
        byte[] byArray = new byte[n2];
        int n3 = 0;
        System.arraycopy(ai.a(this.a), 0, byArray, n3, 4);
        System.arraycopy(ai.a(this.b), 0, byArray, n3 += 4, 4);
        System.arraycopy(ai.a(this.c.length), 0, byArray, n3 += 4, 4);
        n3 += 4;
        int n4 = 0;
        while (n4 < this.c.length) {
            System.arraycopy(ai.a(this.c[n4]), 0, byArray, n3, 4);
            n3 += 4;
            ++n4;
        }
        return byArray;
    }

    int a(byte[] byArray, int n2) {
        int n3 = n2;
        this.a = ai.a(byArray, n2);
        this.b = ai.a(byArray, n2 += 4);
        this.c = new int[ai.a(byArray, n2 += 4)];
        n2 += 4;
        int n4 = 0;
        while (n4 < this.c.length) {
            this.c[n4] = ai.a(byArray, n2);
            n2 += 4;
            ++n4;
        }
        return n2 - n3;
    }
}

