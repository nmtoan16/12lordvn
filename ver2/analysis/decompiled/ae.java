/*
 * Decompiled with CFR 0.152.
 */
public abstract class ae
extends ar {
    public int a = 0;
    protected boolean b = true;
    protected boolean c = true;
    protected boolean d = true;
    private int e;

    public void a(boolean bl2) {
        this.b = bl2;
    }

    public boolean b() {
        return this.b;
    }

    public boolean c() {
        return this.c;
    }

    public void b(boolean bl2) {
        this.c = bl2;
    }

    public boolean d() {
        return this.d;
    }

    public ae(int n2) {
        this.a = n2;
        if (this.a == 1) {
            this.e = -1;
        } else if (this.a == 0) {
            this.e = 2000;
        }
    }

    public final void a() {
        if (this.e > 0) {
            --this.e;
            if (this.e == 1) {
                cz.d().g();
                return;
            }
        }
        this.e();
    }

    protected void e() {
    }
}

