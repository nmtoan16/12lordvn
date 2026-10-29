/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public class bz {
    private static bz e = null;
    public static br a;
    public static as b;
    public static dh c;
    public static dh d;

    private bz() {
        b = new as();
        c = new dh("/_font1");
        d = new dh("/_font2");
        bz.a(c);
    }

    public static void a() {
        e = new bz();
    }

    public static void a(br br2) {
        a = br2;
    }

    public static String[] a(String string, int n2) {
        int n3;
        int n4;
        di di2 = new di();
        int n5 = 0;
        String string2 = "";
        boolean bl2 = false;
        block0: while (n5 < string.length()) {
            while (true) {
                if (n5 >= string.length() || string.charAt(n5) != ' ') {
                    if (n5 <= 0 || di2.b() != 0 || n5 * a.a(' ') >= n2) break;
                    String string3 = "";
                    n4 = 0;
                    while (n4 < n5) {
                        string3 = String.valueOf(string3) + ' ';
                        ++n4;
                    }
                    di2.a(string3);
                    break;
                }
                ++n5;
            }
            while (true) {
                if (n5 >= string.length() || string.charAt(n5) == ' ') {
                    if (string2 != null && string2.length() > 0) {
                        int n6 = a.a(string2 = String.valueOf(string2) + " ");
                        if (n6 < n2) {
                            di2.a(string2);
                        } else {
                            n4 = 0;
                            String string4 = "";
                            n3 = 0;
                            while (n4 < string2.length()) {
                                if (n3 + a.a(string2.charAt(n4)) + a.a(' ') < n2) {
                                    n3 += a.a(string2.charAt(n4));
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
                string2 = String.valueOf(string2) + string.charAt(n5);
                ++n5;
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
        int n7 = 0;
        n5 = 0;
        while (true) {
            if (n5 >= stringArray.length) {
                if (string5 == null || string5.length() <= 0) break;
                di2.a(string5);
                break;
            }
            n3 = a.a(stringArray[n5]);
            if (n7 + n3 - a.a(' ') < n2) {
                n7 += n3;
                string5 = String.valueOf(string5) + stringArray[n5];
                if (bl2) {
                    di2.a(string5);
                    string5 = "";
                    n7 = 0;
                }
                ++n5;
                continue;
            }
            if (n3 > n2) {
                di2.a(stringArray[n5]);
                ++n5;
            } else {
                di2.a(string5);
            }
            string5 = "";
            n7 = 0;
        }
        String[] stringArray2 = new String[di2.b()];
        int n8 = 0;
        while (n8 < stringArray2.length) {
            stringArray2[n8] = (String)di2.b(n8);
            ++n8;
        }
        return stringArray2;
    }

    public static void a(Graphics graphics, br br2, String[] stringArray, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        int n9 = n4;
        if (n8 == 1) {
            n9 += n6 / 2;
        } else if (n8 == 2) {
            n9 += n6;
        }
        int n10 = n2 + n3;
        int n11 = 0;
        int n12 = n2;
        while (n12 < n10) {
            if (n11 + br2.a() > n7) break;
            br2.a(graphics, stringArray[n12], n9, n5 + n11, n8);
            n11 += br2.a();
            ++n12;
        }
    }

    public static void a(Graphics graphics, br br2, String[] stringArray, int n2, int n3, int n4, int n5, int n6) {
        bz.a(graphics, br2, stringArray, 0, stringArray.length, n2, n3, n4, n5, n6);
    }
}

