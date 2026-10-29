/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Font
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 *  javax.microedition.lcdui.game.GameCanvas
 */
import com.mg.smsgame.main.MGMIDlet;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;

public class cn
extends GameCanvas
implements Runnable {
    private int a = 0;
    private Graphics b;
    private Image c;
    private int d;
    private int e;
    private int f;
    private int g = 0;
    private int h = 0;
    private int i = 0;
    private int j = 10;
    private int k = 1;
    private int l;
    private int m = 1;
    private int n = 160 / this.j;
    private boolean o = true;
    private boolean p = false;
    private int q = 0;
    private Font r = null;

    public cn() {
        super(false);
        this.setFullScreenMode(true);
        this.r = Font.getFont((int)0, (int)0, (int)8);
        this.q = 0;
        this.b = this.getGraphics();
        this.e = this.getWidth();
        this.d = this.getHeight();
        bz.a();
        this.c = az.a("/_mglogo");
        this.f = this.c.getHeight() / 5;
        this.n = this.c.getHeight() / 2 / this.j + 1;
        new Thread(this).start();
    }

    public void run() {
        if (!cx.F) {
            while (this.o || !MGMIDlet.f().d) {
                this.d();
                if (this.q == 0) {
                    if (!this.p && this.g < this.n) {
                        this.e();
                    }
                    this.flushGraphics();
                    ++this.a;
                    if (this.a > 30) {
                        this.p = false;
                    }
                    if (this.a > 60) {
                        this.a();
                        this.q = 1;
                        this.a = 0;
                    }
                } else {
                    this.flushGraphics();
                    ++this.a;
                    if (this.a > 40) {
                        this.o = false;
                    }
                }
                try {
                    Thread.sleep(50L);
                }
                catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
            this.d();
        }
        this.b();
    }

    private void a() {
        this.c = az.a("/_partnerLogo");
    }

    private void b() {
        cx.g = this.getHeight();
        cx.h = cx.f = this.getWidth();
        cx.i = cx.g;
        if (cx.f > 240) {
            cx.e = true;
            cx.f = 240;
            cx.j = (cx.h - cx.f) / 2;
        }
        if (cx.g > 320) {
            cx.e = true;
            cx.g = 320;
            cx.k = (cx.i - cx.g) / 2;
        }
        if (cx.g == 320 && cx.f == 240) {
            cx.E = true;
        }
        cx.l = (cx.f - 240) / 2;
        cx.m = (cx.g - 320) / 2;
        MGMIDlet.f().c();
        this.c();
    }

    private void c() {
        this.c = null;
        this.r = null;
        System.gc();
    }

    private void d() {
        this.e = this.getWidth();
        this.d = this.getHeight();
        this.b.setClip(0, 0, this.e, this.d);
        this.b.setColor(0xFFFFFF);
        this.b.fillRect(0, 0, this.e, this.d);
        if (this.q == 0) {
            this.b.setColor(0x666666);
            this.b.setFont(this.r);
            this.b.drawString("www.giaitri321.pro", this.e / 2, this.d - 5, 65);
            this.b.drawImage(this.c, this.e / 2, this.d / 2 - this.f, 3);
        } else {
            this.b.drawImage(this.c, (this.e - this.c.getWidth()) / 2, (this.d - this.c.getHeight()) / 2, 0);
        }
    }

    protected void keyPressed(int n2) {
        if (this.q == 1) {
            this.a = 40;
        }
    }

    private void e() {
        this.l = this.d / 2 - this.f;
        this.b.setColor(0xFFFFFF);
        this.b.fillRect(0, 0, this.e, this.l - this.j / 2 - this.g * this.j);
        this.b.fillRect(0, this.l + this.j / 2 + this.g * this.j, this.e, this.d - this.l + this.j / 2 + this.g * this.j);
        int n2 = this.l - this.j / 2 - this.g * this.j;
        this.b.fillRect(0, n2, this.e, this.j / 2 - this.k);
        n2 = n2 + 10 - (this.j / 2 - this.k);
        this.b.fillRect(0, n2, this.e, this.j / 2 - this.k);
        n2 = this.l - this.j / 2 + this.g * this.j;
        this.b.fillRect(0, n2, this.e, this.j / 2 - this.k);
        n2 = n2 + 10 - (this.j / 2 - this.k);
        this.b.fillRect(0, n2, this.e, this.j / 2 - this.k);
        this.k += this.m;
        if (this.k >= this.j / 2 || this.k <= 0) {
            if (this.i >= this.h) {
                this.k = 0;
                ++this.g;
                this.m = 1;
                this.i = 0;
            } else {
                ++this.i;
                this.m = -this.m;
            }
        }
    }

    protected void pointerPressed(int n2, int n3) {
        if (this.q == 1) {
            this.a = 40;
        }
    }
}

