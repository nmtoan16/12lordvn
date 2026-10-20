/*
 * Decompiled with CFR 0.152.
 */
public abstract class aq {
    protected int a;
    protected int b;
    protected boolean c;
    protected String d;
    protected int e;
    protected int f = 8;

    public void a(int n2) {
        this.f = n2;
    }

    public void a(String string, int n2, int n3) {
        this.e = 0;
        this.d = string;
        this.a = n2;
        this.b = n3;
        this.c = true;
    }

    public void a() {
        if (this.e < this.f) {
            ++this.e;
        } else {
            this.c = false;
        }
    }
}

