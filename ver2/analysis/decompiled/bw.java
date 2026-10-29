/*
 * Decompiled with CFR 0.152.
 */
public abstract class bw
extends bb {
    protected int f;
    protected int g;
    protected int[] h;

    public bw() {
        this.a();
    }

    public void c() {
        this.g = 0;
        int n2 = this.h.length - 1;
        while (n2 > -1) {
            this.h[n2] = 0;
            --n2;
        }
        this.e = false;
    }

    public void b() {
        int n2;
        if (this.e) {
            return;
        }
        int n3 = n2 = this.g <= this.f ? this.g - 1 : this.f - 1;
        while (n3 > -1) {
            if (this.h[n3] < 14) {
                int n4 = n3;
                this.h[n4] = this.h[n4] + 1;
            }
            --n3;
        }
        if (this.g < this.f) {
            this.h[this.g] = 1;
            ++this.g;
        } else if (this.h[this.f - 1] >= 14) {
            this.e = true;
        }
    }
}

