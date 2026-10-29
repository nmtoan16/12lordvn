/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.wireless.messaging.MessageConnection
 */
import com.mg.smsgame.main.a;
import com.mg.smsgame.main.b;
import java.util.Vector;
import javax.wireless.messaging.MessageConnection;

public class an
implements ba,
b {
    public static String a = "TESTGAME";
    public static String b = "3";
    public static String c = "1";
    public static String d = "19003";
    public static int e = 0;
    public static int f = 6;
    public static int g = 5;
    public static int h = 5;
    public static int i = 5;
    public static int j = 7;
    public static String k = String.valueOf(a) + b;
    public static String l = null;
    public static String m = "3ef9f437ab2ed01315d84fc1d7c6718a";
    private static an n;
    private static a o;

    public static String a() {
        return "" + (cx.F ? 1 : 0) + e + f + g + h + i + j;
    }

    protected an() {
    }

    public static void a(String string, int n2, a a2) {
        String string2 = null;
        String string3 = System.getProperty("microedition.platform");
        if (string3 == null) {
            string3 = "Unknown";
        } else if (string3.length() >= 80) {
            string3 = string3.substring(0, 80);
        }
        String string4 = " \"m" + string3 + "\"" + " e" + String.valueOf(System.currentTimeMillis());
        string2 = String.valueOf(k) + " v" + String.valueOf(2) + " a2" + " o" + String.valueOf(n2) + " d" + b + " p" + l;
        if (string != null) {
            string2 = String.valueOf(string2) + " r" + string;
        }
        string2 = String.valueOf(string2) + string4;
        dm.a(string2, String.valueOf(g), new o(a2));
    }

    public static void b(String string, int n2, a a2) {
        try {
            String string2 = null;
            String string3 = System.getProperty("microedition.platform");
            if (string3 == null) {
                string3 = "Unknown";
            } else if (string3.length() >= 80) {
                string3 = string3.substring(0, 80);
            }
            String string4 = " \"m" + string3 + "\"" + " e" + String.valueOf(System.currentTimeMillis());
            string2 = String.valueOf(k) + " v" + String.valueOf(2) + " a2" + " o" + String.valueOf(n2) + " d" + b + " p" + l;
            if (string != null) {
                string2 = String.valueOf(string2) + " r" + string;
            }
            string2 = String.valueOf(string2) + string4;
            dm.a(string2, String.valueOf(h), new n(a2));
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void c(String string, int n2, a a2) {
        String string2 = null;
        String string3 = System.getProperty("microedition.platform");
        if (string3 == null) {
            string3 = "Unknown";
        } else if (string3.length() >= 80) {
            string3 = string3.substring(0, 80);
        }
        String string4 = " \"m" + string3 + "\"" + " e" + String.valueOf(System.currentTimeMillis());
        string2 = String.valueOf(k) + " v" + String.valueOf(2) + " a4" + " o" + String.valueOf(n2) + " d" + b + " p" + l;
        if (string != null) {
            string2 = String.valueOf(string2) + " r" + string;
        }
        string2 = String.valueOf(string2) + string4;
        dm.a(string2, String.valueOf(i), new p(a2));
    }

    public static void a(String string, a a2) {
        String string2 = null;
        String string3 = System.getProperty("microedition.platform");
        if (string3 == null) {
            string3 = "Unknown";
        } else if (string3.length() >= 80) {
            string3 = string3.substring(0, 80);
        }
        String string4 = " \"m" + string3 + "\"" + " e" + String.valueOf(System.currentTimeMillis());
        string2 = String.valueOf(k) + " v" + String.valueOf(2) + " a5" + " c" + string + " p" + l + string4;
        dm.a(string2, String.valueOf(e), new r(a2));
    }

    public static void a(String string) {
        dm.a("CSE " + c + " " + string, String.valueOf(j), new q());
    }

    public static an b() {
        if (n == null) {
            n = new an();
        }
        return n;
    }

    private static String[] b(String string) {
        return an.a(string, " ", "\"", "\"", -1);
    }

    public static String[] a(String string, String string2, String string3, String string4, int n2) {
        boolean bl2 = false;
        if (n2 == 0) {
            return null;
        }
        String[] stringArray = null;
        int n3 = 0;
        Vector<String> vector = new Vector<String>();
        int n4 = 0;
        while (n4 < string.length() - string2.length() + 1) {
            if (string2.equals(string.substring(n4, n4 + string2.length())) && !bl2) {
                vector.addElement(string.substring(n3, n4));
                n3 = n4 + string2.length();
                if (n2 > 0 && vector.size() >= n2 - 1) {
                    vector.addElement(string.substring(n3));
                    break;
                }
                n4 = n3 - 1;
            } else if (string3 != null && string4 != null) {
                if (string3.equals(string.substring(n4, n4 + string3.length())) && !bl2) {
                    vector.addElement(string.substring(n3, n4));
                    n3 = n4 + string3.length();
                    if (n2 > 0 && vector.size() >= n2 - 1) {
                        vector.addElement(string.substring(n3));
                        break;
                    }
                    n4 = n3 - 1;
                    bl2 = true;
                } else if (string4.equals(string.substring(n4, n4 + string4.length())) && bl2) {
                    vector.addElement(string.substring(n3, n4));
                    n3 = n4 + string4.length();
                    if (n2 > 0 && vector.size() >= n2 - 1) {
                        vector.addElement(string.substring(n3));
                        break;
                    }
                    n4 = n3 - 1;
                    bl2 = false;
                }
            }
            ++n4;
        }
        if (n3 < string.length()) {
            if (n2 < 0) {
                vector.addElement(string.substring(n3));
            } else if (vector.size() < n2) {
                vector.addElement(string.substring(n3));
            }
        }
        if (vector.size() > 0) {
            stringArray = new String[vector.size()];
            n4 = 0;
            while (n4 < vector.size()) {
                stringArray[n4] = (String)vector.elementAt(n4);
                ++n4;
            }
            return stringArray;
        }
        return null;
    }

    public void a(String string, String string2) {
        String[] stringArray = an.b(string);
        if (!stringArray[0].toUpperCase().equals(k.toUpperCase())) {
            return;
        }
        try {
            String string3;
            int n2 = 0;
            String string4 = null;
            String string5 = null;
            String string6 = null;
            String string7 = null;
            int n3 = 0;
            int n4 = 1;
            while (n4 < stringArray.length) {
                if (stringArray[n4].length() != 0) {
                    char c2 = stringArray[n4].charAt(0);
                    string3 = null;
                    if (stringArray[n4].length() > 1) {
                        string3 = stringArray[n4].substring(1);
                    }
                    switch (c2) {
                        case 'a': {
                            n2 = Integer.parseInt(string3);
                            break;
                        }
                        case 'l': {
                            string4 = string3;
                            break;
                        }
                        case 'c': {
                            string5 = string3;
                            break;
                        }
                        case 'q': {
                            string6 = string3;
                            break;
                        }
                        case 'r': {
                            string7 = string3;
                            break;
                        }
                        case 'i': {
                            try {
                                n3 = Integer.parseInt(string3);
                            }
                            catch (Exception exception) {
                                n3 = 0;
                            }
                            break;
                        }
                    }
                }
                ++n4;
            }
            al.d();
            if (n2 == 2) {
                cz.d().e.a(n3, string7, string6);
                return;
            }
            n4 = n2;
            String string8 = string4;
            string3 = string5;
            String string9 = string7;
            if (!cx.B) {
                cz.d().e.a(n4, string8, string3, string9);
                cx.B = false;
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
            cz.d().a(false);
            ae ae2 = cz.a("C\u00f3 l\u1ed7i", "Tin sai c\u00fa ph\u00e1p", "C\u00f3", 3, 1);
            ae2.a(an.b());
            cz.d().a(ae2);
        }
    }

    public void b(int n2, int n3) {
        if (n3 == 1) {
            if (o != null) {
                o.a();
            }
            cz.d().a(true);
        } else if (n3 == 2) {
            cz.d().a(true);
        } else if (n3 == 3) {
            cz.d().a(true);
        }
    }

    public static void a(MessageConnection messageConnection) {
        dm.a(messageConnection);
    }

    static void a(a a2) {
        o = a2;
    }
}

