/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public class du
extends ae {
    private dp e;

    public du(String string, String string2, String[] stringArray, int[] nArray, int n2) {
        super(n2);
        this.e = new dp(string, string2, 1);
        if (stringArray != null) {
            if (stringArray.length == 1) {
                this.c(new da(stringArray[0], nArray[0]));
            } else if (stringArray.length == 2) {
                this.a(new da(stringArray[0], nArray[0]));
                this.b(new da(stringArray[1], nArray[1]));
            } else {
                this.a(new da(stringArray[0], nArray[0]));
                this.c(new da(stringArray[1], nArray[1]));
                this.b(new da(stringArray[1], nArray[2]));
            }
        }
        this.g = this.e.g();
        this.a_ = this.e.h();
        this.b_ = this.e.i();
        this.c_ = this.e.j();
        this.a(this.l());
    }

    public void a(h h2) {
        if (h2 != null) {
            h2.a(cx.P);
        }
        super.a(h2);
    }

    public void a(int n2) {
        this.e.a(n2);
    }

    public void a(int n2, int n3) {
        this.e.a(n2, n3);
    }

    protected void e() {
        this.e.a();
    }

    public void a(Graphics graphics) {
        this.e.a(graphics);
    }
}

