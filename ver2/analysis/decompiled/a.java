/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public class a
extends ds {
    private String a;
    private int b;
    private int c;
    private br d;
    private boolean e;
    private boolean f;

    public a(String string, int n2, br br2) {
        this.d = br2;
        this.a(string, n2);
        this.a(0, 0, br2.a(string), br2.a());
    }

    private void b() {
        if (this.t == 0) {
            this.c = 0;
            this.f = true;
        } else {
            this.c = this.r - this.b;
            this.f = false;
        }
        this.e = this.r < this.b + 2;
    }

    public void a(String string, int n2) {
        this.a = string;
        this.j(n2);
        this.b = this.d.a(string);
        this.b();
    }

    public void a(int n2, int n3, int n4, int n5) {
        n5 = this.d.a();
        super.a(n2, n3, n4, n5);
        this.b();
    }

    public void a() {
        if (!this.e) {
            return;
        }
        if (this.f) {
            --this.c;
            if (this.c + this.b <= this.r) {
                this.f = false;
            }
        } else {
            ++this.c;
            if (this.c >= 0) {
                this.f = true;
            }
        }
    }

    public void a(Graphics graphics, int n2, int n3) {
        dl dl2 = new dl();
        y.a(graphics, dl2);
        y.a(graphics, n2 + this.p, n3 + this.q, this.r, this.s);
        this.d.a(graphics, this.a, n2 + this.c + this.p, n3 + this.q, 0);
        y.b(graphics, dl2);
    }
}

