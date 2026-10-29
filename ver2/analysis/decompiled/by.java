/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public abstract class by {
    protected int a;
    protected int b;
    protected int c;
    protected int d;
    protected int e;
    protected int f;
    protected int g;
    protected int h;
    protected boolean i;

    public abstract void a(int var1, int var2, int var3, int var4, int var5, int var6);

    public boolean b() {
        return this.i;
    }

    public void a() {
        if (this.i) {
            return;
        }
    }

    public void a(Graphics graphics) {
    }

    public int c() {
        return this.a - this.e / 2;
    }

    public int d() {
        return this.b - this.f / 2;
    }

    public int e() {
        return this.e;
    }

    public int f() {
        return this.f;
    }
}

