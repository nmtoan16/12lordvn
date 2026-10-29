/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class bp {
    private c a;
    private c b;
    private Image c;
    private Image d;
    private Image e;
    private Image f;
    private a g;
    private a h;
    private int i;
    private int j;
    private int k;
    private int l;
    private dq m;

    public bp(c c2, c c3) {
        if (dd.h) {
            this.a = c2;
            this.b = c3;
        } else {
            this.a = c3;
            this.b = c2;
        }
        this.b();
        this.l = cx.f >> 1;
        int n2 = this.e.getHeight() * 2 + this.f.getHeight();
        this.i = cx.g - n2 >> 1;
        this.j = this.i + this.e.getHeight() + 2;
        this.k = this.j + this.f.getHeight();
        this.m = new dq(az.b("/crystalblue"), 3);
        byte[][] byArrayArray = new byte[1][];
        byte[] byArray = new byte[4];
        byArray[1] = 1;
        byArray[2] = 2;
        byArray[3] = 1;
        byArrayArray[0] = byArray;
        this.m.a(byArrayArray);
        this.m.c(4);
        this.m.b(true);
        this.m.d(this.l - (this.m.k() >> 1), this.j + (this.m.l() >> 1));
    }

    private void b() {
        Graphics graphics = null;
        this.e = az.b("/m/ff");
        this.f = az.b("/m/sw");
        Image image = az.b("/m/af");
        Image image2 = az.b("/m/ih");
        Image image3 = az.b("/m/isw");
        av av2 = new av();
        int n2 = 160;
        int n3 = 46;
        this.g = new a(u.a(this.a.a), 0, bz.c);
        this.g.a(45, 2, 112, 14);
        this.h = new a(u.a(this.b.a), 1, bz.c);
        this.h.a(3, 2, 112, 14);
        this.c = Image.createImage((int)n2, (int)n3);
        graphics = this.c.getGraphics();
        graphics.setColor(0x800000);
        graphics.fillRect(0, 0, n2, n3);
        u.a(graphics, this.a.a, 10, 3, true);
        graphics.drawImage(image, 8, 1, 0);
        graphics.drawImage(image2, 54, 20, 0);
        graphics.drawImage(image3, 111, 20, 0);
        av2.a(graphics, this.a.c, 68, 22);
        av2.a(graphics, this.a.d, 126, 22);
        bz.c.a(graphics, "C\u1ea5p " + this.a.e, 8, 34, 0);
        this.d = Image.createImage((int)n2, (int)n3);
        graphics = this.d.getGraphics();
        graphics.setColor(0x800000);
        graphics.fillRect(0, 0, n2, n3);
        u.a(graphics, this.b.a, 120, 3, false);
        graphics.drawImage(image, 118, 1, 0);
        graphics.drawImage(image3, 14, 20, 0);
        graphics.drawImage(image2, 71, 20, 0);
        av2.a(graphics, this.b.d, 28, 22);
        av2.a(graphics, this.b.c, 86, 22);
        bz.c.a(graphics, "C\u1ea5p " + this.b.e, 150, 34, 2);
    }

    public void a() {
        this.m.e();
        this.g.a();
        this.h.a();
    }

    public void a(Graphics graphics) {
        graphics.drawImage(this.e, this.l, this.i, 17);
        graphics.drawImage(this.c, this.l, this.i + 11, 17);
        this.g.a(graphics, this.l - 80, this.i + 11);
        graphics.drawImage(this.f, this.l, this.j, 17);
        this.m.a(graphics);
        graphics.drawImage(this.e, this.l, this.k, 17);
        graphics.drawImage(this.d, this.l, this.k + 11, 17);
        this.h.a(graphics, this.l - 80, this.k + 11);
    }
}

