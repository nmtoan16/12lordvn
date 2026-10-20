/*
 * Decompiled with CFR 0.152.
 */
public class dl {
    public int a;
    public int b;
    public int c;
    public int d;

    public dl() {
    }

    public dl(int n2, int n3, int n4, int n5) {
        this.a = n2;
        this.b = n3;
        this.c = n4;
        this.d = n5;
    }

    public void a(int n2, int n3, int n4, int n5) {
        this.a = n2;
        this.b = n3;
        this.c = n4;
        this.d = n5;
    }

    public boolean a(int n2, int n3) {
        return n2 > this.a && n2 < this.a + this.c && n3 > this.b && n3 < this.b + this.d;
    }

    public boolean a(dl dl2) {
        return this.b(dl2.a, dl2.b, dl2.c, dl2.d);
    }

    public boolean b(int n2, int n3, int n4, int n5) {
        int n6 = this.c;
        int n7 = this.d;
        int n8 = n4;
        int n9 = n5;
        if (n8 <= 0 || n9 <= 0 || n6 <= 0 || n7 <= 0) {
            return false;
        }
        int n10 = this.a;
        int n11 = this.b;
        int n12 = n2;
        int n13 = n3;
        return !((n8 += n12) >= n12 && n8 <= n10 || (n9 += n13) >= n13 && n9 <= n11 || (n6 += n10) >= n10 && n6 <= n12 || (n7 += n11) >= n11 && n7 <= n13);
    }

    public boolean b(int n2, int n3) {
        int n4 = this.c;
        int n5 = this.d;
        if ((n4 | n5) < 0) {
            return false;
        }
        int n6 = this.a;
        int n7 = this.b;
        if (n2 < n6 || n3 < n7) {
            return false;
        }
        return !((n4 += n6) >= n6 && n4 <= n2 || (n5 += n7) >= n7 && n5 <= n3);
    }

    public String toString() {
        String string = "[X]: " + this.a + " [Y]: " + this.b + " [W]: " + this.c + " [H]: " + this.d;
        return string;
    }
}

