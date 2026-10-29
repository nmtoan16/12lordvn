/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class ap
extends br {
    protected Image a;
    protected int[] b;
    protected int[] c;
    protected int[] d;
    protected int[] e;
    protected int[] f;
    protected String g = " 0123456789.,:!?()+-*/#$%abcdefghijklmnopqrstuvwxyz\u00e1\u00e0\u1ea3\u00e3\u1ea1\u0103\u1eaf\u1eb1\u1eb3\u1eb5\u1eb7\u00e2\u1ea5\u1ea7\u1ea9\u1eab\u1ead\u00e9\u00e8\u1ebb\u1ebd\u1eb9\u00ea\u1ebf\u1ec1\u1ec3\u1ec5\u1ec7\u00ed\u00ec\u1ec9\u0129\u1ecb\u00f3\u00f2\u1ecf\u00f5\u1ecd\u00f4\u1ed1\u1ed3\u1ed5\u1ed7\u1ed9\u01a1\u1edb\u1edd\u1edf\u1ee1\u1ee3\u00fa\u00f9\u1ee7\u0169\u1ee5\u01b0\u1ee9\u1eeb\u1eed\u1eef\u1ef1\u00fd\u1ef3\u1ef7\u1ef9\u1ef5\u0111ABCD\u0110EFGHIJKLMNOPQRSTUVWXYZ\u00c1\u00c0\u1ea2\u00c3\u1ea0\u0102\u1eae\u1eb0\u1eb2\u1eb4\u1eb6\u00c2\u1ea4\u1ea6\u1ea8\u1eaa\u1eac\u00c9\u00c8\u1eba\u1ebc\u1eb8\u00ca\u1ebe\u1ec0\u1ec2\u1ec4\u1ec6\u00cd\u00cc\u1ec8\u0128\u1eca\u00d3\u00d2\u1ece\u00d5\u1ecc\u00d4\u1ed0\u1ed2\u1ed4\u1ed6\u1ed8\u01a0\u1eda\u1edc\u1ede\u1ee0\u1ee2\u00da\u00d9\u1ee6\u0168\u1ee4\u01af\u1ee8\u1eea\u1eec\u1eee\u1ef0\u00dd\u1ef2\u1ef6\u1ef8\u1ef4\\\"@<=>;_&'`^~{}";
    protected String h = "";
    protected int i;

    public void a(Graphics graphics, String string, int n2, int n3, int n4) {
        int n5 = string.length();
        int n6 = this.a(string);
        int n7 = n4 == 0 ? n2 : (n4 == 2 ? n2 - n6 : n2 - (n6 >> 1));
        int n8 = 0;
        while (n8 < n5) {
            int n9 = this.g.indexOf(string.charAt(n8));
            if (n9 == -1) {
                n9 = this.h.indexOf(string.charAt(n8));
            }
            if (n9 == -1) {
                n9 = 0;
            }
            if (n9 > -1) {
                y.a(graphics, this.a, this.b[n9], this.c[n9], this.d[n9], this.e[n9], n7, n3 - 1 + this.f[n9], 20);
            }
            n7 += this.d[n9] + 1;
            ++n8;
        }
    }

    public int a() {
        return this.i;
    }

    public int a(String string) {
        int n2 = 0;
        int n3 = 0;
        while (n3 < string.length()) {
            n2 = n2 + 1 + this.a(string.charAt(n3));
            ++n3;
        }
        return n2;
    }

    public int a(char c2) {
        int n2 = this.g.indexOf(c2);
        if (n2 == -1) {
            n2 = this.h.indexOf(c2);
        }
        if (n2 > -1) {
            return this.d[n2];
        }
        return this.d[0];
    }
}

