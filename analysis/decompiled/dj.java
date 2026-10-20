/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class dj
extends ae {
    private e[] e;
    private String f;
    private int t;
    private int u;
    private dl v;
    private int w;
    private Image x;

    public void a(e[] eArray) {
        this.e = eArray;
        this.u();
    }

    private static e[] a(String string, di di2, di di3) {
        int n2;
        int n3;
        int n4;
        String[] stringArray = new String[50];
        int n5 = 1;
        int n6 = 0;
        if (di2 != null) {
            n4 = 0;
            while (n4 < di2.b()) {
                n3 = string.indexOf("{s}");
                string = String.valueOf(string.substring(0, n3)) + (String)di2.b(n4) + string.substring(n3 + 2, string.length());
                ++n4;
            }
        }
        n4 = 0;
        stringArray[0] = string;
        while (n4 < n5) {
            n3 = stringArray[n4].indexOf("@");
            if (n3 >= 0) {
                n2 = n5 - 1;
                while (n2 > n4) {
                    stringArray[n2 + 1] = stringArray[n2];
                    --n2;
                }
                stringArray[n4 + 1] = stringArray[n4].substring(n3 + 1);
                stringArray[n4] = stringArray[n4].substring(0, n3);
                ++n5;
            }
            ++n4;
        }
        e[] eArray = new e[n5];
        n4 = 0;
        while (n4 < n5) {
            if ("{i}".equals(stringArray[n4])) {
                eArray[n4] = (e)di3.b(n6++);
            } else {
                n2 = 0;
                if (stringArray[n4].length() > 0 && stringArray[n4].charAt(0) == '*') {
                    n2 = 1;
                }
                if (n2 != 0) {
                    stringArray[n4] = stringArray[n4].substring(1);
                }
                eArray[n4] = new dt(stringArray[n4], 0);
                if (stringArray[n4].length() > 0 && n2 != 0) {
                    ((dt)eArray[n4]).a(bz.c);
                }
            }
            ++n4;
        }
        return eArray;
    }

    public static e[] a(String string, String[] stringArray, e[] eArray, br br2, br br3) {
        int n2;
        int n3;
        int n4 = Integer.parseInt(string.substring(0, 3));
        e[] eArray2 = new e[n4];
        string = string.substring(4);
        if (stringArray != null) {
            n3 = 0;
            while (n3 < stringArray.length) {
                n2 = string.indexOf("&r");
                string = String.valueOf(string.substring(0, n2)) + stringArray[n3] + string.substring(n2 + 2, string.length());
                ++n3;
            }
        }
        n3 = 0;
        n2 = 0;
        while (string != null) {
            String string2;
            int n5 = string.indexOf("#");
            if (n5 > -1) {
                string2 = string.substring(0, n5);
                string = string.substring(n5 + 1);
            } else {
                string2 = string;
            }
            String string3 = String.valueOf(string2.charAt(0));
            if (string3.equals("s")) {
                String string4 = String.valueOf(string2.charAt(1));
                String string5 = String.valueOf(string2.charAt(2));
                int n6 = 0;
                if (string5.equals("r")) {
                    n6 = 2;
                } else if (string5.equals("c")) {
                    n6 = 1;
                }
                if (String.valueOf(string2.charAt(3)).equals("$")) {
                    int n7 = Integer.parseInt(string2.substring(4, 6));
                    boolean bl2 = String.valueOf(string2.charAt(4)).equals("t");
                    string2 = string2.substring(7);
                    String[] stringArray2 = new String[n7];
                    int n8 = 0;
                    while (n8 < stringArray2.length) {
                        int n9 = string2.indexOf("|");
                        if (n9 > -1) {
                            stringArray2[n8] = string2.substring(0, n9);
                            string2 = string2.substring(n9 + 1);
                        } else {
                            stringArray2[n8] = string2;
                        }
                        ++n8;
                    }
                    eArray2[n3] = new dt(stringArray2, bl2, n6);
                } else {
                    eArray2[n3] = new dt(string2.substring(3), n6);
                }
                if (string4.equals("t")) {
                    ((dt)eArray2[n3]).a(bz.c);
                }
            } else if (string3.equals("i")) {
                eArray2[n3] = eArray[n2++];
            }
            if (++n3 < n4) continue;
        }
        return eArray2;
    }

    public static final dj a(String string, String string2, di di2, di di3, de de2, de de3) {
        dj dj2 = new dj(string, dj.a(string2, di2, di3));
        if (de3 == null) {
            dj2.c(de2);
        } else if (de2 == null) {
            dj2.c(de3);
        } else {
            dj2.a(de2);
            dj2.b(de3);
        }
        return dj2;
    }

    public dj(String string) {
        this(string, null);
    }

    public dj(String string, e[] eArray) {
        super(1);
        this.b = false;
        this.v();
        this.v = new dl(10, 41, this.b_ - 15, this.c_ - 69);
        this.f = string;
        this.e = eArray;
        this.u();
    }

    private void u() {
        if (this.e == null) {
            return;
        }
        this.w = 0;
        this.u = 0;
        this.t = 0;
        int n2 = 0;
        while (n2 < this.e.length) {
            this.e[n2].a(this.v);
            this.w += this.e[n2].a() + 8;
            ++n2;
        }
    }

    private void v() {
        try {
            this.x = cz.d;
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void a(int n2, int n3) {
        int n4;
        int n5;
        super.a(n2, n3);
        if (this.t > 0) {
            n5 = this.b_ / 2 + 2 - 5;
            n4 = this.v.b - 2 - 15;
            if (n2 > n5 && n2 < n5 + 10 && n3 > n4 && n3 < n4 + 15) {
                cx.u[12] = 2;
                return;
            }
        }
        if (this.w > this.v.d && this.w - this.t - this.v.d > 0) {
            n5 = this.b_ / 2 + 2 - 5;
            n4 = this.v.b + this.v.d + 2;
            if (n2 > n5 && n2 < n5 + 10 && n3 > n4 && n3 < n4 + 15) {
                cx.u[13] = 2;
                return;
            }
        }
    }

    public void a(int n2) {
        super.a(n2);
        if (n2 == 12 || n2 == 2) {
            this.t -= this.v.d / 2;
            if (this.t < 0) {
                this.t = 0;
            }
        } else if (n2 == 13 || n2 == 8) {
            this.t += this.v.d / 2;
            if (this.w - this.t < this.v.d) {
                this.t = this.w - this.v.d;
            }
            if (this.t < 0) {
                this.t = 0;
            }
        }
    }

    protected void e() {
        int n2;
        if (this.u != this.t) {
            n2 = this.t - this.u;
            this.u = Math.abs(n2) < 3 ? (this.u += n2) : (this.u += n2 / 2);
        }
        if (this.e != null) {
            n2 = 0;
            while (n2 < this.e.length) {
                this.e[n2].b();
                ++n2;
            }
        }
    }

    public void a(Graphics graphics) {
        if (this.r == null) {
            graphics.setColor(cx.S);
            graphics.fillRect(0, 0, this.b_, this.c_);
        }
        if (this.f != null) {
            bz.c.a(graphics, this.f, 2, 2, 0);
            graphics.setColor(0xFF0000);
            graphics.fillRect(2, 4 + bz.c.a(), bz.c.a(this.f) + 2, 2);
        }
        if (this.e != null) {
            graphics.setClip(0, this.v.b, this.b_, this.v.d);
            int n2 = this.v.b - this.u;
            int n3 = this.v.b + this.v.d;
            dl dl2 = new dl();
            int n4 = 0;
            while (n4 < this.e.length) {
                if (n2 > n3) break;
                dl2.a(this.v.a, n2, this.v.c, this.e[n4].a());
                if (this.v.a(dl2)) {
                    this.e[n4].a(graphics, this.v.a, n2);
                }
                n2 += this.e[n4].a() + 8;
                ++n4;
            }
            graphics.setClip(0, 0, this.b_, this.c_);
        }
        if (this.t > 0) {
            graphics.drawRegion(this.x, 0, 0, this.x.getWidth(), this.x.getHeight(), 6, this.b_ / 2 + 2, this.v.b - 2, 33);
        }
        if (this.w > this.v.d && this.w - this.t - this.v.d > 0) {
            graphics.drawRegion(this.x, 0, 0, this.x.getWidth(), this.x.getHeight(), 5, this.b_ / 2 + 2, this.v.b + this.v.d + 2, 17);
        }
    }
}

