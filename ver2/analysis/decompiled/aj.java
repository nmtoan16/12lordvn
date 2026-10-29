/*
 * Decompiled with CFR 0.152.
 */
public abstract class aj
extends ar {
    protected boolean a = true;
    protected bk b;

    public boolean c() {
        return this.a;
    }

    public void a(bk bk2) {
        this.b = bk2;
    }

    public int c(int n2, int n3) {
        if (this.b != null) {
            return this.b.b(n2, n3);
        }
        return -1;
    }

    public int d() {
        if (this.b != null) {
            return this.b.c();
        }
        return -1;
    }

    public void e() {
    }
}

