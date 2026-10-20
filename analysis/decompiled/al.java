/*
 * Decompiled with CFR 0.152.
 */
import java.io.UnsupportedEncodingException;

public class al {
    private static int a = 1;
    private static int b;
    private static boolean c;
    private static boolean d;

    static {
        d = false;
    }

    protected al() {
    }

    public static final boolean a() {
        al.f();
        return c;
    }

    private static final void f() {
        c = false;
        if (a == 1) {
            return;
        }
        d.a("[CHECK] Load data");
        if (!bg.g()) {
            if (cx.G > 5) {
                d.a("[CHECK] RMS has been deleted by user");
                c = true;
                return;
            }
            a = 1;
            b = 0;
            al.g();
        } else {
            String string = ao.a(bg.h());
            d.a("[CHECK] String: " + string);
            try {
                byte[] byArray = b.a(string.substring(0, 2));
                String string2 = b.b(byArray);
                if (string2.equals(string.substring(string.length() - 3, string.length()))) {
                    d.a("[CHECK] Wrong checksum!!!");
                    c = true;
                    return;
                }
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                // empty catch block
            }
            a = string.charAt(0) - 48;
            b = string.charAt(1) - 48;
            d.a("[CHECK] Mode = " + a + "; sendSMSCount = " + b);
            if (b >= 6 && a == 1) {
                d.a("[CHECK] Playing on emulator!!!");
                c = true;
            }
        }
    }

    private static final void g() {
        byte[] byArray;
        String string = "";
        string = String.valueOf(string) + (a == 1 ? "0" : "1");
        string = String.valueOf(string) + String.valueOf(b);
        try {
            byArray = b.a(string);
            String string2 = b.b(byArray);
            string = String.valueOf(string) + string2.substring(string2.length() - 3, string2.length());
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            // empty catch block
        }
        byArray = null;
        try {
            byArray = ao.b(string);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            byArray = string.getBytes();
        }
        bg.b(byArray);
    }

    public static final void b() {
        a = 1;
        b = 7;
        al.g();
    }

    public static final void c() {
        if (a == 1) {
            return;
        }
        if (d) {
            d = false;
            return;
        }
        d.a("[CHECK] Update send SMS");
        ++b;
        al.g();
    }

    public static final void d() {
        if (a == 1) {
            return;
        }
        d.a("[CHECK] Update receive SMS");
        a = 1;
        al.g();
    }

    public static final void e() {
        String string = "Thi\u1ebft b\u1ecb kh\u00f4ng ph\u00f9 h\u1ee3p \u0111\u1ec3 ch\u01a1i game! Xin c\u1ea3m \u01a1n.";
        ae ae2 = cz.a("Ch\u00fa \u00fd", string, "\u0110\u00f3ng", 4, 1);
        ae2.a(cz.d());
        cz.d().a(ae2);
    }

    public static final boolean a(boolean bl2) {
        try {
            byte[] byArray = b.a(an.a());
            String string = b.b(byArray);
            return string.equals(an.m) && bl2;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            return true;
        }
    }
}

