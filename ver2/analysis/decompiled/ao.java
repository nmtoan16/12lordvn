/*
 * Decompiled with CFR 0.152.
 */
public class ao {
    public static boolean a(String string) {
        if (string == null) {
            return true;
        }
        String string2 = string.trim();
        return string2 == null || string2.length() == 0;
    }

    public static boolean a(String string, String string2) {
        String string3;
        if (string == null || string2 == null) {
            return false;
        }
        String string4 = string.toLowerCase();
        return string4.equals(string3 = string2.toLowerCase());
    }

    public static String[] b(String string, String string2) {
        return ao.a(string, string2, -1, false);
    }

    public static String[] a(String string, String string2, int n2, boolean bl2) {
        if (n2 == 0 || string == null) {
            return null;
        }
        if (string2 == null) {
            return new String[]{string};
        }
        di di2 = new di();
        String string3 = string;
        int n3 = string2.length();
        int n4 = 0;
        n4 = bl2 ? string3.toLowerCase().indexOf(string2.toLowerCase()) : string3.indexOf(string2);
        if (n4 >= 0) {
            int n5 = 0;
            while (n4 >= 0) {
                if (n2 == 1) {
                    if (!ao.a(string3)) {
                        di2.a(string3);
                    }
                    return ao.a(di2);
                }
                if (n4 > n5) {
                    String string4 = string3.substring(n5, n4);
                    di2.a(string4);
                    if (n2 > 0) {
                        --n2;
                    }
                }
                n5 = 0;
                string3 = string3.substring(n4 + n3);
                n4 = bl2 ? string3.toLowerCase().indexOf(string2.toLowerCase()) : string3.indexOf(string2);
            }
            if (!ao.a(string3)) {
                di2.a(string3);
            }
            return ao.a(di2);
        }
        return new String[]{string};
    }

    public static String[] a(di di2) {
        if (di2 == null || di2.b() == 0) {
            return null;
        }
        int n2 = di2.b();
        String[] stringArray = new String[n2];
        int n3 = 0;
        while (n3 < n2) {
            stringArray[n3] = (String)di2.b(n3);
            ++n3;
        }
        return stringArray;
    }

    public static byte[] b(String string) {
        return ao.c(string, null);
    }

    public static byte[] c(String string, String string2) {
        if (string2 == null) {
            string2 = "UTF-8";
        }
        return string.getBytes(string2);
    }

    public static String a(byte[] byArray) {
        if (byArray == null || byArray.length == 0) {
            return null;
        }
        try {
            return new String(byArray, 0, byArray.length, "UTF-8");
        }
        catch (Throwable throwable) {
            return new String(byArray, 0, byArray.length);
        }
    }

    public static String a(String string, String string2, String string3) {
        int n2 = 0;
        int n3 = 0;
        StringBuffer stringBuffer = new StringBuffer();
        while ((n3 = string.indexOf(string2, n2)) >= 0) {
            stringBuffer.append(string.substring(n2, n3));
            stringBuffer.append(string3);
            n2 = n3 + string2.length();
        }
        stringBuffer.append(string.substring(n2));
        return stringBuffer.toString();
    }
}

