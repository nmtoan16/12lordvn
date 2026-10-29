/*
 * Decompiled with CFR 0.152.
 */
public class ac
extends ch {
    public ac(bu bu2, int n2) {
        super(bu2, n2);
    }

    public void a(int n2) {
        switch (n2) {
            case 12: {
                this.b.m();
                break;
            }
            case 13: {
                this.b.n();
                break;
            }
            case 14: {
                this.b.o();
                break;
            }
            case 15: {
                this.b.p();
                break;
            }
            case 16: {
                this.b.i();
                break;
            }
            case 17: {
                this.b.r();
                break;
            }
            case 18: {
                this.b.g();
                break;
            }
            case 11: {
                this.b.s();
            }
        }
    }

    public void a(int n2, int n3) {
        if (n3 >= cx.g - 15) {
            int n4 = cx.f / 3;
            int n5 = n4 + n4;
            if (n2 > n5) {
                this.b.g();
            } else if (n2 > n4) {
                this.b.s();
            } else {
                this.b.r();
            }
            return;
        }
        dl dl2 = this.b.h();
        if (dl2.b(n2, n3)) {
            int n6 = (n2 - dl2.a) / 21 + 2;
            int n7 = (n3 - dl2.b) / 21 + 2;
            int n8 = this.b.k();
            int n9 = this.b.l();
            if (n7 == n8 && n6 == n9) {
                this.b.i();
            } else if (this.b.j()) {
                int n10 = n7 - n8;
                int n11 = n6 - n9;
                if (n10 == 1) {
                    this.b.n();
                } else if (n10 == -1) {
                    this.b.m();
                } else if (n11 == 1) {
                    this.b.p();
                } else if (n11 == -1) {
                    this.b.o();
                }
            } else {
                this.b.c(n7, n6);
            }
        }
    }

    public void a() {
    }

    public void b() {
        this.b.c(1);
    }
}

