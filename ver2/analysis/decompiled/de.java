/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public class de {
    protected int a;
    protected int b;
    protected int c;
    protected int d;
    protected int e;

    public int a() {
        return this.e;
    }

    public de(int n2) {
        this.e = n2;
    }

    public void a(int n2, int n3, int n4) {
        this.b = n2;
        switch (n4) {
            case 24: {
                this.b = n2 - this.d;
                break;
            }
            case 17: {
                this.b = n2 - this.d / 2;
            }
        }
        this.c = n3;
    }

    public final boolean a(int n2, int n3) {
        return n3 > this.c && n2 >= this.b && n2 < this.b + this.d;
    }

    public void b() {
        this.a = 3;
    }

    public void a(Graphics graphics) {
    }

    public void c() {
        if (this.a > 0) {
            --this.a;
        }
    }
}

