/*
 * Decompiled with CFR 0.152.
 */
public class dx {
    public int a;
    public int b;

    public boolean a() {
        if (this.b > 0) {
            --this.b;
            return true;
        }
        return false;
    }

    public int a(byte[] byArray, int n2) {
        int n3 = n2;
        System.arraycopy(ai.a(this.a), 0, byArray, n2, 4);
        System.arraycopy(ai.a(this.b), 0, byArray, n2 += 4, 4);
        return (n2 += 4) - n3;
    }

    public int b(byte[] byArray, int n2) {
        int n3 = n2;
        this.a = ai.a(byArray, n2);
        this.b = ai.a(byArray, n2 += 4);
        return (n2 += 4) - n3;
    }
}

