/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public abstract class aa {
    protected int a;
    protected int[] b;
    protected int[] c;
    protected Image d;

    protected aa(String string) {
        this.d = az.b(string);
    }

    public int a() {
        return this.d.getHeight();
    }

    public int a(String string) {
        int n2 = 0;
        int n3 = string.length() - 1;
        while (n3 >= 0) {
            int n4 = " 0123456789.,:!?()+-*/#$%abcdefghijklmnopqrstuvwxyz\u00e1\u00e0\u1ea3\u00e3\u1ea1\u0103\u1eaf\u1eb1\u1eb3\u1eb5\u1eb7\u00e2\u1ea5\u1ea7\u1ea9\u1eab\u1ead\u00e9\u00e8\u1ebb\u1ebd\u1eb9\u00ea\u1ebf\u1ec1\u1ec3\u1ec5\u1ec7\u00ed\u00ec\u1ec9\u0129\u1ecb\u00f3\u00f2\u1ecf\u00f5\u1ecd\u00f4\u1ed1\u1ed3\u1ed5\u1ed7\u1ed9\u01a1\u1edb\u1edd\u1edf\u1ee1\u1ee3\u00fa\u00f9\u1ee7\u0169\u1ee5\u01b0\u1ee9\u1eeb\u1eed\u1eef\u1ef1\u00fd\u1ef3\u1ef7\u1ef9\u1ef5\u0111ABCDEFGHIJKLMNOPQRSTUVWXYZ\u00c1\u00c0\u1ea2\u00c3\u1eb6\u0102\u1eae\u1eb0\u1eb2\u1eb4\u1eb6\u00c2\u1ea4\u1ea6\u1ea8\u1eaa\u1eac\u00c9\u00c8\u1eba\u1ebc\u1eb8\u00ca\u1ebe\u1ec0\u1ec2\u1ec4\u1ec6\u00cd\u00cc\u1ec8\u0128\u1eca\u00d3\u00d2\u1ece\u00d5\u1ecc\u00d4\u1ed0\u1ed2\u1ed4\u1ed6\u1ed8\u01a0\u1eda\u1edc\u1ede\u1ee0\u1ee2\u00da\u00d9\u1ee6\u0168\u1ee4\u01af\u1ee8\u1eea\u1eec\u1eee\u1ef0\u00dd\u1ef2\u1ef6\u1ef8\u1ef4\u0110".indexOf(string.charAt(n3));
            if (n4 == -1) {
                n4 = 0;
            }
            n2 += this.b[n4] + this.a;
            --n3;
        }
        return n2 - this.a;
    }

    public void a(Graphics graphics, String string, int n2, int n3, int n4) {
        int n5 = 0;
        int n6 = string.length();
        switch (n4) {
            case 1: {
                n5 = n2 - this.a(string);
                break;
            }
            case 2: {
                n5 = n2 - (this.a(string) >> 1);
                break;
            }
            default: {
                n5 = n2;
            }
        }
        int n7 = 0;
        while (n7 < n6) {
            int n8 = " 0123456789.,:!?()+-*/#$%abcdefghijklmnopqrstuvwxyz\u00e1\u00e0\u1ea3\u00e3\u1ea1\u0103\u1eaf\u1eb1\u1eb3\u1eb5\u1eb7\u00e2\u1ea5\u1ea7\u1ea9\u1eab\u1ead\u00e9\u00e8\u1ebb\u1ebd\u1eb9\u00ea\u1ebf\u1ec1\u1ec3\u1ec5\u1ec7\u00ed\u00ec\u1ec9\u0129\u1ecb\u00f3\u00f2\u1ecf\u00f5\u1ecd\u00f4\u1ed1\u1ed3\u1ed5\u1ed7\u1ed9\u01a1\u1edb\u1edd\u1edf\u1ee1\u1ee3\u00fa\u00f9\u1ee7\u0169\u1ee5\u01b0\u1ee9\u1eeb\u1eed\u1eef\u1ef1\u00fd\u1ef3\u1ef7\u1ef9\u1ef5\u0111ABCDEFGHIJKLMNOPQRSTUVWXYZ\u00c1\u00c0\u1ea2\u00c3\u1eb6\u0102\u1eae\u1eb0\u1eb2\u1eb4\u1eb6\u00c2\u1ea4\u1ea6\u1ea8\u1eaa\u1eac\u00c9\u00c8\u1eba\u1ebc\u1eb8\u00ca\u1ebe\u1ec0\u1ec2\u1ec4\u1ec6\u00cd\u00cc\u1ec8\u0128\u1eca\u00d3\u00d2\u1ece\u00d5\u1ecc\u00d4\u1ed0\u1ed2\u1ed4\u1ed6\u1ed8\u01a0\u1eda\u1edc\u1ede\u1ee0\u1ee2\u00da\u00d9\u1ee6\u0168\u1ee4\u01af\u1ee8\u1eea\u1eec\u1eee\u1ef0\u00dd\u1ef2\u1ef6\u1ef8\u1ef4\u0110".indexOf(string.charAt(n7));
            if (n8 == -1) {
                n8 = 0;
            } else {
                graphics.drawRegion(this.d, this.c[n8], 0, this.b[n8], this.a(), 0, n5, n3, 20);
            }
            n5 += this.b[n8] + this.a;
            ++n7;
        }
    }

    public int a(char c2) {
        int n2 = " 0123456789.,:!?()+-*/#$%abcdefghijklmnopqrstuvwxyz\u00e1\u00e0\u1ea3\u00e3\u1ea1\u0103\u1eaf\u1eb1\u1eb3\u1eb5\u1eb7\u00e2\u1ea5\u1ea7\u1ea9\u1eab\u1ead\u00e9\u00e8\u1ebb\u1ebd\u1eb9\u00ea\u1ebf\u1ec1\u1ec3\u1ec5\u1ec7\u00ed\u00ec\u1ec9\u0129\u1ecb\u00f3\u00f2\u1ecf\u00f5\u1ecd\u00f4\u1ed1\u1ed3\u1ed5\u1ed7\u1ed9\u01a1\u1edb\u1edd\u1edf\u1ee1\u1ee3\u00fa\u00f9\u1ee7\u0169\u1ee5\u01b0\u1ee9\u1eeb\u1eed\u1eef\u1ef1\u00fd\u1ef3\u1ef7\u1ef9\u1ef5\u0111ABCDEFGHIJKLMNOPQRSTUVWXYZ\u00c1\u00c0\u1ea2\u00c3\u1eb6\u0102\u1eae\u1eb0\u1eb2\u1eb4\u1eb6\u00c2\u1ea4\u1ea6\u1ea8\u1eaa\u1eac\u00c9\u00c8\u1eba\u1ebc\u1eb8\u00ca\u1ebe\u1ec0\u1ec2\u1ec4\u1ec6\u00cd\u00cc\u1ec8\u0128\u1eca\u00d3\u00d2\u1ece\u00d5\u1ecc\u00d4\u1ed0\u1ed2\u1ed4\u1ed6\u1ed8\u01a0\u1eda\u1edc\u1ede\u1ee0\u1ee2\u00da\u00d9\u1ee6\u0168\u1ee4\u01af\u1ee8\u1eea\u1eec\u1eee\u1ef0\u00dd\u1ef2\u1ef6\u1ef8\u1ef4\u0110".indexOf(c2);
        if (n2 > -1) {
            return this.b[n2];
        }
        return this.b[0];
    }

    public String[] a(String string, int n2) {
        int n3;
        int n4;
        int n5 = this.a(' ');
        di di2 = new di();
        String string2 = "";
        boolean bl2 = false;
        int n6 = 0;
        block0: while (n6 < string.length()) {
            while (true) {
                if (n6 >= string.length() || string.charAt(n6) != ' ') {
                    if (n6 <= 0 || di2.b() != 0 || n6 * n5 >= n2) break;
                    String string3 = "";
                    n4 = 0;
                    while (n4 < n6) {
                        string3 = String.valueOf(string3) + ' ';
                        ++n4;
                    }
                    di2.a(string3);
                    break;
                }
                ++n6;
            }
            while (true) {
                if (n6 >= string.length() || string.charAt(n6) == ' ') {
                    if (string2 != null && string2.length() > 0) {
                        int n7 = this.a(string2 = String.valueOf(string2) + " ");
                        if (n7 < n2) {
                            di2.a(string2);
                        } else {
                            n4 = 0;
                            String string4 = "";
                            n3 = 0;
                            while (n4 < string2.length()) {
                                if (n3 + this.a(string2.charAt(n4)) + n5 < n2) {
                                    n3 += this.a(string2.charAt(n4));
                                    string4 = String.valueOf(string4) + string2.charAt(n4);
                                    if (++n4 < string2.length()) continue;
                                    if (string4 == null || string4.length() <= 0) break;
                                    di2.a(string4);
                                    break;
                                }
                                di2.a(string4);
                                n3 = 0;
                                string4 = "";
                            }
                        }
                    }
                    string2 = "";
                    continue block0;
                }
                string2 = String.valueOf(string2) + string.charAt(n6);
                ++n6;
            }
        }
        String[] stringArray = new String[di2.b()];
        n4 = 0;
        while (n4 < stringArray.length) {
            stringArray[n4] = (String)di2.b(n4);
            ++n4;
        }
        di2 = new di();
        String string5 = "";
        int n8 = 0;
        n6 = 0;
        while (true) {
            if (n6 >= stringArray.length) {
                if (string5 == null || string5.length() <= 0) break;
                di2.a(string5);
                break;
            }
            n3 = this.a(stringArray[n6]);
            if (n8 + n3 - n5 < n2) {
                string5 = String.valueOf(string5) + stringArray[n6];
                n8 = this.a(string5);
                if (bl2) {
                    di2.a(string5);
                    string5 = "";
                    n8 = 0;
                }
                ++n6;
                continue;
            }
            if (n3 > n2) {
                di2.a(stringArray[n6]);
                ++n6;
            } else {
                di2.a(string5);
            }
            string5 = "";
            n8 = 0;
        }
        String[] stringArray2 = new String[di2.b()];
        int n9 = 0;
        while (n9 < stringArray2.length) {
            stringArray2[n9] = (String)di2.b(n9);
            ++n9;
        }
        return stringArray2;
    }
}

