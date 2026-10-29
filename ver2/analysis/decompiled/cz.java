/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.io.ConnectionNotFoundException
 *  javax.microedition.lcdui.Canvas
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import com.mg.smsgame.main.MGMIDlet;
import com.mg.smsgame.main.b;
import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class cz
extends Canvas
implements b,
Runnable {
    public static cz a;
    public static int b;
    private long g;
    private long h;
    private boolean i;
    private boolean j = false;
    private boolean k;
    private boolean l;
    private Object m = new Object();
    public static Image c;
    public static Image d;
    private static int n;
    private Graphics o;
    private Image p;
    public bv e;
    public static int f;

    static {
        b = 0;
        n = -1;
        f = -1000;
    }

    public static bv a() {
        return cz.d().e;
    }

    protected cz() {
        bg.p();
        c = az.b("/_corner");
        d = az.b("/_arrow");
        this.p = Image.createImage((int)cx.f, (int)cx.g);
        this.o = this.p.getGraphics();
        this.setFullScreenMode(true);
        cx.n = this.hasPointerEvents();
        String string = System.getProperty("microedition.platform");
        cx.v = this.getKeyCode(8) == -20 ? 3 : (string == null || string.length() == 0 ? 4 : (string.indexOf("NokiaN") >= 0 ? 1 : (string.indexOf("Nokia") >= 0 ? 0 : (string.indexOf("Sony") >= 0 ? 2 : (string.indexOf("RIM") >= 0 ? 5 : (string.indexOf("SIE-EL71") >= 0 ? 6 : 4))))));
        d.a("Vendor: " + string + " detected as " + String.valueOf(cx.v));
    }

    public void b() {
        Thread thread = new Thread(this);
        thread.start();
    }

    public static final void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7) {
        int n8 = cx.R;
        graphics.setColor(n2);
        graphics.fillRect(n4 + 3, n5 + 3, n6 - 6, n7 - 6);
        graphics.setColor(n8);
        graphics.drawRect(n4 + 2, n5 + 2, n6 - 5, n7 - 5);
        graphics.setColor(n3);
        graphics.drawRect(n4 + 1, n5 + 1, n6 - 3, n7 - 3);
        graphics.drawRegion(c, 0, 0, c.getWidth(), c.getHeight(), 0, n4, n5, 20);
        graphics.drawRegion(c, 0, 0, c.getWidth(), c.getHeight(), 2, n4 + n6, n5, 24);
        graphics.drawRegion(c, 0, 0, c.getWidth(), c.getHeight(), 1, n4, n5 + n7, 36);
        graphics.drawRegion(c, 0, 0, c.getWidth(), c.getHeight(), 3, n4 + n6, n5 + n7, 40);
    }

    public static final void b(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7) {
        graphics.setColor(n3);
        graphics.drawRect(n4, n5 + 1, n6 - 1, n7 - 1);
        graphics.setColor(n2);
        graphics.fillRect(n4 + 1, n5 + 2, n6 - 2, n7 - 2);
    }

    protected void paint(Graphics graphics) {
        if (!this.j) {
            graphics.setClip(0, 0, cx.h, cx.i);
            graphics.setColor(0);
            graphics.fillRect(0, 0, cx.h, cx.i);
            this.j = true;
        }
        if (this.e == null) {
            return;
        }
        graphics.setClip(cx.j, cx.k, cx.f, cx.g);
        this.e.a(this.o, this.p, graphics);
    }

    protected void c() {
        if (b > 0) {
            --b;
        }
        if (n > 0 && --n == 0) {
            MGMIDlet.f().a.vibrate(0);
        }
        if (f > -1 && --f <= 0) {
            MGMIDlet.f().notifyDestroyed();
        }
        this.e.a();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void run() {
        this.i = false;
        this.l = false;
        while (!this.i) {
            if (!this.l) {
                try {
                    this.g = System.currentTimeMillis();
                    if (this.e != null) {
                        this.c();
                        this.repaint();
                    }
                    this.h = System.currentTimeMillis();
                    if (this.h - this.g > 40L) continue;
                    Thread.sleep(40L - (this.h - this.g));
                }
                catch (Exception exception) {
                    d.a(exception);
                    exception.printStackTrace();
                }
                continue;
            }
            Object object = this.m;
            synchronized (object) {
                try {
                    this.m.wait();
                }
                catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        }
        System.out.println("End GUIManager");
    }

    public void a(int n2) {
        if (this.e != null) {
            this.e.c(n2);
        }
    }

    public static cz d() {
        if (a == null) {
            a = new cz();
        }
        return a;
    }

    public static ae a(String string, String string2, String[] stringArray, int[] nArray, int n2) {
        return cz.d().b(string, string2, stringArray, nArray, n2);
    }

    public static ae a(String string, String string2, String string3, int n2, int n3) {
        String[] stringArray = new String[]{string3};
        int[] nArray = new int[]{n2};
        return cz.d().b(string, string2, stringArray, nArray, n3);
    }

    public static ae a(String string, String string2, String string3, int n2, String string4, int n3, int n4) {
        String[] stringArray = new String[]{string3, string4};
        int[] nArray = new int[]{n2, n3};
        return cz.d().b(string, string2, stringArray, nArray, n4);
    }

    public static ae a(String string, String string2, boolean bl2) {
        int n2 = 1;
        if (bl2) {
            n2 = 0;
        }
        return cz.d().b(string, string2, null, null, n2);
    }

    public static ae b(String string, String string2, boolean bl2) {
        int n2 = 1;
        if (bl2) {
            n2 = 0;
        }
        return new du(string, string2, null, null, n2);
    }

    private ae b(String string, String string2, String[] stringArray, int[] nArray, int n2) {
        return new du(string, string2, stringArray, nArray, n2);
    }

    public void a(aj aj2, boolean bl2) {
        this.a(aj2, true, bl2);
    }

    public void a(aj aj2, boolean bl2, boolean bl3) {
        cz.f();
        if (this.e != null) {
            this.e.a(aj2, bl2, bl3);
        }
    }

    public void a(ae ae2) {
        this.a(ae2, false);
    }

    public void a(ae ae2, boolean bl2) {
        cz.f();
        if (ae2 != null) {
            ae2.a(bl2);
            if (this.e != null) {
                this.e.a(ae2);
            }
        }
    }

    public void a(boolean bl2) {
        if (this.e != null) {
            this.e.a(bl2);
        }
    }

    public void e() {
        if (this.e != null) {
            this.e.e();
        }
    }

    private int e(int n2) {
        int n3 = n2;
        switch (n3) {
            case -21: {
                n3 = -6;
                break;
            }
            case -22: {
                n3 = -7;
                break;
            }
            case -23: {
                n3 = -8;
                break;
            }
            case -2: {
                n3 = -3;
                break;
            }
            case -5: {
                n3 = -4;
                break;
            }
            case -6: {
                n3 = -2;
                break;
            }
            case -20: {
                n3 = -5;
            }
        }
        return n3;
    }

    private int f(int n2) {
        int n3 = n2;
        switch (n3) {
            case -59: {
                n3 = -1;
                break;
            }
            case -60: {
                n3 = -2;
                break;
            }
            case -61: {
                n3 = -3;
                break;
            }
            case -62: {
                n3 = -4;
                break;
            }
            case -26: {
                n3 = -5;
                break;
            }
            case -1: {
                n3 = -6;
                break;
            }
            case -4: {
                n3 = -7;
            }
        }
        return n3;
    }

    private int g(int n2) {
        int n3 = n2;
        switch (n3) {
            case 50: {
                n3 = -1;
                break;
            }
            case 56: {
                n3 = -2;
                break;
            }
            case 52: {
                n3 = -3;
                break;
            }
            case 54: {
                n3 = -4;
                break;
            }
            case 53: {
                n3 = -5;
            }
        }
        return n3;
    }

    public static void f() {
        int n2 = 0;
        while (n2 < 21) {
            cx.u[n2] = 0;
            ++n2;
        }
        cx.t = -1;
        cx.s = -1;
        cx.r = -1;
        cx.q = -1;
        cx.p = -1;
        cx.o = -1;
    }

    public int b(int n2) {
        if (n2 == 113 || n2 == 35 || n2 == 81) {
            n2 = -6;
        } else if (n2 == 112 || n2 == 64 || n2 == 80) {
            n2 = -7;
        }
        return n2;
    }

    public int c(int n2) {
        int n3 = -1;
        switch (n2) {
            case 48: 
            case 49: 
            case 50: 
            case 51: 
            case 52: 
            case 53: 
            case 54: 
            case 55: 
            case 56: 
            case 57: {
                n3 = n2 - 48;
                if (!cx.F || n2 != 57) break;
                MGMIDlet.f().e();
                break;
            }
            case 42: {
                n3 = 10;
                break;
            }
            case 35: {
                n3 = 11;
                break;
            }
            case -1: 
            case 1: {
                n3 = 12;
                break;
            }
            case -2: 
            case 6: {
                n3 = 13;
                break;
            }
            case -3: 
            case 2: {
                n3 = 14;
                break;
            }
            case -4: 
            case 5: {
                n3 = 15;
                break;
            }
            case -5: 
            case 10: 
            case 13: {
                n3 = 16;
                break;
            }
            case -6: {
                if (cx.v == 1) {
                    if (b != 0) break;
                    b = 5;
                    n3 = 17;
                    break;
                }
                n3 = 17;
                break;
            }
            case -7: {
                if (cx.v == 1) {
                    if (b != 0) break;
                    b = 5;
                    n3 = 18;
                    break;
                }
                n3 = 18;
                break;
            }
            case -8: 
            case 8: {
                n3 = 19;
                break;
            }
            case -11: {
                n3 = 20;
            }
        }
        return n3;
    }

    protected void keyPressed(int n2) {
        int n3;
        if (this.e == null) {
            return;
        }
        if (cx.v == 5) {
            n2 = this.b(n2);
        } else if (cx.v == 3) {
            n2 = this.e(n2);
        } else if (cx.v == 6) {
            n2 = this.f(n2);
        }
        if (this.e.b()) {
            n2 = this.g(n2);
        }
        if ((n3 = this.c(n2)) > -1) {
            if (n3 > 16) {
                cx.u[n3] = 2;
            } else {
                if (n3 == 16 && this.e.d()) {
                    cx.u[n3] = 2;
                    return;
                }
                cx.u[n3] = 3;
                this.e.a(n3);
            }
        }
    }

    protected void keyReleased(int n2) {
        int n3;
        if (this.e == null) {
            return;
        }
        if (cx.v == 5) {
            n2 = this.b(n2);
        } else if (cx.v == 3) {
            n2 = this.e(n2);
        } else if (cx.v == 6) {
            n2 = this.f(n2);
        }
        if (this.e.b()) {
            n2 = this.g(n2);
        }
        if ((n3 = this.c(n2)) > -1) {
            cx.u[n3] = 1;
            if (n2 < 17 && this.e != null) {
                this.e.b(n3);
            }
        }
    }

    protected void pointerPressed(int n2, int n3) {
        if (this.e != null) {
            this.e.a(n2 - cx.j, n3 - cx.k);
        }
    }

    protected void pointerReleased(int n2, int n3) {
        if (this.e != null) {
            this.e.c(n2 - cx.j, n3 - cx.k);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void showNotify() {
        this.j = false;
        this.setFullScreenMode(true);
        cz.f();
        this.l = false;
        Object object = this.m;
        synchronized (object) {
            this.m.notify();
        }
        if (!this.k && this.e != null) {
            this.e.h();
        }
    }

    protected void hideNotify() {
        cz.f();
        if (this.e != null) {
            this.e.g();
        }
        this.l = true;
        if (!this.k && cx.T && !cx.H) {
            this.k = true;
            du du2 = new du("Th\u00f4ng tin", "B\u1ea5m ti\u1ebfp t\u1ee5c \u0111\u1ec3 ch\u01a1i ti\u1ebfp", new String[]{"Ti\u1ebfp t\u1ee5c"}, new int[]{1}, 1);
            du2.a(this);
            this.a(du2, false);
        }
    }

    public static void d(int n2) {
        n = (int)((long)n2 / 40L);
        if ((long)n2 % 40L > 0L) {
            ++n;
        }
        MGMIDlet.f().a.vibrate(n2);
    }

    public void g() {
        if (this.e != null) {
            this.e.i();
        }
    }

    public void b(int n2, int n3) {
        if (n3 == 1) {
            this.k = false;
            if (this.e != null) {
                this.e.h();
            }
            cz.d().a(false);
        } else if (n3 == 3) {
            cz.d().a(true);
        } else if (n3 == 2) {
            cz.d().a(false);
        } else if (n3 == 4) {
            MGMIDlet.f().d();
        } else if (n3 == 6) {
            cz.d().a(true);
        } else if (n3 == 5) {
            try {
                MGMIDlet.f().a("http://game.ola.vn");
            }
            catch (ConnectionNotFoundException connectionNotFoundException) {
                // empty catch block
            }
            cz.d().a(false);
        }
    }
}

