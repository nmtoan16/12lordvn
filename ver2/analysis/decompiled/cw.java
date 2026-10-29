/*
 * Decompiled with CFR 0.152.
 */
public abstract class cw
extends ds {
    protected int b = 0;
    protected int c;
    protected byte[][] d;
    protected int e;
    protected int f;
    protected int g;
    protected boolean h = true;
    protected int i;
    protected int j;
    protected int k;
    protected int l;

    public void c(int n2, int n3) {
        this.i = n2;
        this.j = n3;
    }

    public byte[][] d() {
        return this.d;
    }

    public void a(byte[][] byArray) {
        this.d = byArray;
    }

    public void b(int n2) {
        this.g = n2;
    }

    public void c(int n2) {
        this.e = n2;
        this.f = 0;
    }

    public void b(boolean bl2) {
        this.h = bl2;
    }

    public void a_(int n2) {
        this.c = n2;
        this.g %= this.d[n2].length;
    }

    public final void e() {
        if (!this.u) {
            return;
        }
        this.a();
        if (this.f >= this.e) {
            if (this.d != null && this.d[this.c] != null) {
                if (this.h) {
                    this.g = (this.g + 1) % this.d[this.c].length;
                } else {
                    ++this.g;
                    if (this.g >= this.d[this.c].length) {
                        this.g = this.d[this.c].length - 1;
                    }
                }
            } else {
                this.g = -1;
            }
            this.f = 0;
        } else {
            ++this.f;
        }
    }

    public abstract void a();
}

