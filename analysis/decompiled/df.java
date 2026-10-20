/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import com.mg.smsgame.main.MGMIDlet;
import com.mg.smsgame.main.b;
import com.mic.smsgame.suquan.f;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class df
extends aj
implements b {
    private int c = -1;
    private static final int[][] d;
    private Image e;
    private Image f;
    private Image t;
    private int u;
    private int v;
    private int w;
    private int x;
    private cl y;
    private dl z;
    private dl A;
    private dl B;
    private dl C;
    private int D;
    private com.mic.smsgame.suquan.b[] E;
    private int F;
    private boolean G;
    private boolean H = false;
    private int I;
    private int J;
    private dq K;

    static {
        int[][] nArrayArray = new int[117][];
        nArrayArray[42] = new int[]{60, 338};
        nArrayArray[45] = new int[]{186, 229};
        nArrayArray[48] = new int[]{303, 287};
        nArrayArray[51] = new int[]{103, 76};
        nArrayArray[54] = new int[]{148, 116};
        nArrayArray[57] = new int[]{108, 183};
        nArrayArray[60] = new int[]{265, 160};
        nArrayArray[63] = new int[]{318, 107};
        nArrayArray[66] = new int[]{243, 205};
        nArrayArray[69] = new int[]{178, 170};
        nArrayArray[72] = new int[]{29, 88};
        nArrayArray[75] = new int[]{313, 228};
        nArrayArray[81] = new int[]{170, 295};
        d = nArrayArray;
    }

    public df(boolean bl2) {
        this.a(this);
        this.a((h)null);
        this.b_ = 240;
        this.c_ = 320;
        this.g = cx.f - this.b_ >> 1;
        this.a_ = cx.g - this.c_ >> 1;
        this.t = az.b("/m/m");
        this.w = this.t.getWidth();
        this.x = this.t.getHeight();
        this.A = new dl(this.g + 20, this.a_ + 30, 200, 130);
        this.C = new dl(this.A.a + 100, this.A.b + 65, 0, 0);
        this.B = new dl(this.g, this.a_ + 165, this.b_, cx.g - 20 - (this.a_ + 165));
        this.z = new dl();
        this.K = new dq(az.b("/m/ar"), 6);
        this.K.c(1);
        this.K.b(true);
        byte[][] byArrayArray = new byte[1][];
        byte[] byArray = new byte[12];
        byArray[1] = 1;
        byArray[2] = 2;
        byArray[3] = 3;
        byArray[4] = 4;
        byArray[5] = 5;
        byArray[6] = 5;
        byArray[7] = 4;
        byArray[8] = 3;
        byArray[9] = 2;
        byArray[10] = 1;
        byArrayArray[0] = byArray;
        this.K.a(byArrayArray);
        this.K.a_(0);
        if (bl2) {
            this.e(3);
        } else {
            this.e(0);
        }
        if (bl2) {
            cf.a().a("intro", -1);
            cf.a().a(120000);
        } else {
            cf.a().a("victory", -1);
            cf.a().a(120000);
        }
        cf.a().e();
    }

    private boolean a(int n2, int n3, dl dl2) {
        int n4;
        int n5;
        int n6 = (dl2.c >> 1) + dl2.a - this.u - this.g;
        int n7 = (dl2.d >> 1) + dl2.b - this.v - this.a_ + 15;
        int n8 = dl2.c >> 1;
        int n9 = dl2.d >> 1;
        if (n2 > this.w - n8) {
            n2 = this.w - n8;
        }
        if (n2 < n8) {
            n2 = n8;
        }
        if (n3 > this.x - n9) {
            n3 = this.x - n9;
        }
        if (n3 < n9) {
            n3 = n9;
        }
        if (n2 - n6 > 4 || n2 - n6 < 4) {
            n5 = (n2 - n6) / 4;
        } else if (n2 == n6) {
            n5 = 0;
        } else {
            int n10 = n5 = n2 - n6 > 0 ? 1 : -1;
        }
        if (n3 - n7 > 4 || n3 - n7 < 4) {
            n4 = (n3 - n7) / 4;
        } else if (n3 == n7) {
            n4 = 0;
        } else {
            int n11 = n4 = n3 - n7 > 0 ? 1 : -1;
        }
        if (n5 == 0 && n4 == 0) {
            return false;
        }
        System.out.println(">>" + n5 + " " + n4);
        this.u -= n5;
        this.v -= n4;
        System.out.println(">>>>" + this.u + " " + this.v);
        return true;
    }

    private void e(int n2) {
        this.c = n2;
        switch (this.c) {
            case 3: {
                this.u = this.A.a;
                this.v = this.A.b;
                break;
            }
            case 4: {
                this.y = new cl(com.mic.smsgame.suquan.h.h, this.B);
                this.D = 1;
                this.b(new da("B\u1ecf qua", 102));
                break;
            }
            case 0: {
                this.u = -43;
                this.v = -154;
                break;
            }
            case 1: {
                String[] stringArray = com.mic.smsgame.suquan.h.i;
                String string = dd.c();
                int n3 = 0;
                while (n3 < stringArray.length) {
                    stringArray[n3] = ao.a(stringArray[n3], "{s}", string);
                    ++n3;
                }
                this.y = new cl(stringArray, this.B);
                this.E = new com.mic.smsgame.suquan.b[5];
                this.E[0] = new com.mic.smsgame.suquan.b(az.b("/crystalviolet"));
                this.E[1] = new com.mic.smsgame.suquan.b(az.b("/crystalblue"));
                this.E[2] = new com.mic.smsgame.suquan.b(az.b("/crystalgreen"));
                this.E[3] = new com.mic.smsgame.suquan.b(az.b("/crystalred"));
                this.E[4] = new com.mic.smsgame.suquan.b(az.b("/crystalyellow"));
                n3 = 0;
                while (n3 < this.E.length) {
                    this.E[n3].a_(false);
                    ++n3;
                }
                this.F = 30;
                this.b(new da("B\u1ecf qua", 103));
                break;
            }
            case 2: {
                this.e = az.b("/m/e1");
                this.f = az.b("/m/e2");
                this.F = 0;
                this.b((de)null);
                this.c(new da("K\u1ebft th\u00fac", 101));
            }
        }
    }

    public void a() {
        switch (this.c) {
            case 3: {
                boolean bl2 = false;
                if (this.C.a > this.A.a) {
                    bl2 = true;
                    this.C.a -= 2;
                    this.C.c += 4;
                }
                if (this.C.b > this.A.b) {
                    bl2 = true;
                    this.C.b -= 2;
                    this.C.d += 4;
                }
                if (bl2) break;
                this.e(4);
                break;
            }
            case 4: {
                int n2;
                this.y.b();
                if (this.y.a()) {
                    this.e(5);
                }
                if (d[n2 = this.y.c()] != null) {
                    this.H = true;
                    this.I = d[n2][0];
                    this.J = d[n2][1];
                }
                if (!this.H) {
                    switch (this.D) {
                        case 1: {
                            --this.u;
                            if (this.g + this.u + this.t.getWidth() > this.A.a + this.A.c) break;
                            this.D = 2;
                            break;
                        }
                        case 2: {
                            --this.v;
                            if (this.a_ + this.v + this.t.getHeight() > this.A.b + this.A.d) break;
                            this.D = 3;
                            break;
                        }
                        case 3: {
                            ++this.u;
                            if (this.g + this.u < this.A.a) break;
                            this.D = 4;
                            break;
                        }
                        case 4: {
                            ++this.v;
                            if (this.a_ + this.v < this.A.b) break;
                            this.D = 1;
                        }
                    }
                    break;
                }
                this.K.e();
                this.a(this.I, this.J, this.A);
                break;
            }
            case 5: {
                cz.a().a(-1, 8, false);
                break;
            }
            case 0: {
                boolean bl3 = false;
                if (this.C.a > this.A.a) {
                    bl3 = true;
                    this.C.a -= 2;
                    this.C.c += 4;
                }
                if (this.C.b > this.A.b) {
                    bl3 = true;
                    this.C.b -= 2;
                    this.C.d += 4;
                }
                if (bl3) break;
                this.e(1);
                break;
            }
            case 1: {
                this.y.b();
                if (this.y.a()) {
                    this.e(2);
                }
                --this.F;
                if (this.F == 0) {
                    this.F = 20;
                    this.E[ay.a(5)].a(ay.a(cx.f), ay.a(cx.g), 6, "");
                }
                int n3 = 0;
                while (n3 < this.E.length) {
                    this.E[n3].b();
                    ++n3;
                }
                break;
            }
            case 2: {
                ++this.F;
                if (this.F % 3 != 0) break;
                this.G = !this.G;
            }
        }
    }

    private void b(Graphics graphics) {
        y.a(graphics, this.z);
        y.a(graphics, this.B.a, this.B.b, this.B.c, this.B.d);
        this.y.a(graphics, this.g, this.a_);
        y.b(graphics, this.z);
    }

    private void a(Graphics graphics, dl dl2) {
        y.a(graphics, this.z);
        y.a(graphics, dl2.a, dl2.b, dl2.c, dl2.d);
        graphics.drawImage(this.t, this.g + this.u, this.a_ + this.v, 0);
        if (this.H) {
            this.K.a(graphics, this.g + this.u + this.I - 10, this.a_ + this.v + this.J - 30);
        }
        y.b(graphics, this.z);
    }

    public void a(Graphics graphics) {
        graphics.setColor(0);
        graphics.fillRect(this.g, this.a_, this.b_, this.c_);
        switch (this.c) {
            case 3: {
                this.a(graphics, this.C);
                break;
            }
            case 4: {
                this.a(graphics, this.A);
                this.b(graphics);
                break;
            }
            case 0: {
                this.a(graphics, this.C);
                break;
            }
            case 1: {
                this.a(graphics, this.A);
                this.b(graphics);
                int n2 = 0;
                while (n2 < this.E.length) {
                    this.E[n2].a(graphics);
                    ++n2;
                }
                break;
            }
            case 2: {
                if (this.F < 10) {
                    if (this.F % 2 == 0) {
                        graphics.drawImage(this.e, (cx.f >> 1) + 2, (cx.g >> 1) - 2, 3);
                    } else {
                        graphics.drawImage(this.e, (cx.f >> 1) - 2, (cx.g >> 1) + 2, 3);
                    }
                } else {
                    graphics.drawImage(this.e, cx.f >> 1, cx.g >> 1, 3);
                }
                if (!this.G) break;
                graphics.drawImage(this.f, cx.f >> 1, (cx.g >> 1) + 40, 3);
            }
        }
    }

    public void b(int n2, int n3) {
        switch (n3) {
            case 101: {
                com.mic.smsgame.suquan.f.a();
                bg.k();
                ae ae2 = cz.a("Ch\u00fac m\u1eebng", "B\u1ea1n \u0111\u00e3 ho\u00e0n th\u00e0nh tr\u00f2 ch\u01a1i. B\u1ea1n c\u00f3 mu\u1ed1n ch\u01a1i c\u00e1c tr\u00f2 ch\u01a1i kh\u00e1c kh\u00f4ng? (Tr\u00f2 ch\u01a1i c\u00f3 kh\u1ea3 n\u0103ng b\u1ecb \u0111\u00f3ng l\u1ea1i n\u1ebfu ch\u1ecdn C\u00f3).", "C\u00f3", 105, "Kh\u00f4ng", 104, 1);
                ae2.a(this);
                cz.d().a(ae2, true);
                break;
            }
            case 104: {
                cz.d().a(false);
                cz.d().e.a(-1, 2, true);
                break;
            }
            case 105: {
                cz.d().a(false);
                try {
                    MGMIDlet.f().a("http://game.ola.vn");
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                cz.d().e.a(-1, 2, true);
                break;
            }
            case 102: {
                this.e(5);
                break;
            }
            case 103: {
                this.e(2);
            }
        }
    }

    public void e() {
    }

    public void r() {
        cf.a().d();
    }
}

