/*
 * Decompiled with CFR 0.152.
 */
import com.mg.smsgame.main.MGMIDlet;
import com.mg.smsgame.main.a;
import java.io.UnsupportedEncodingException;

class bc {
    String a;
    String b;
    a c;

    bc(String string, String string2, a a2) {
        this.b = string;
        this.c = a2;
        this.a(string2);
    }

    private void a(String string) {
        if ((string = string.trim()).length() == 1) {
            int n2 = Integer.parseInt(string.trim());
            String string2 = "8731";
            try {
                string2 = String.valueOf(8) + string + 31;
                byte[] byArray = b.a(string2);
                byte[] byArray2 = new byte[byArray.length];
                System.arraycopy(cx.a[n2], 0, byArray2, 0, 4);
                System.arraycopy(cx.b[n2], 0, byArray2, 8, 4);
                System.arraycopy(cx.c[n2], 0, byArray2, 4, 4);
                System.arraycopy(cx.d[n2], 0, byArray2, 12, 4);
                if (!b.a(byArray, byArray2)) {
                    al.b();
                    MGMIDlet.f().d();
                    return;
                }
                this.a = string2;
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                this.a = string2;
            }
        }
    }
}

