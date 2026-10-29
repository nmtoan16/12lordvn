/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
import java.io.InputStream;
import javax.microedition.lcdui.Image;

public class az {
    public static boolean a = false;
    private static byte[] b = new byte[]{-119, 80, 78, 71};

    private static Image c(String string) {
        string = String.valueOf(string) + cx.U;
        try {
            InputStream inputStream = "".getClass().getResourceAsStream(string);
            byte[] byArray = new byte[b.length];
            inputStream.read(byArray, 0, byArray.length);
            int n2 = ai.a(byArray);
            byte[] byArray2 = new byte[n2];
            bg.a(byArray2, inputStream, b.length);
            inputStream.close();
            inputStream = null;
            int n3 = 0;
            while (n3 < b.length) {
                byArray2[n3] = b[n3];
                ++n3;
            }
            return Image.createImage((byte[])byArray2, (int)0, (int)n2);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private static Image d(String string) {
        string = String.valueOf(string) + ".png";
        try {
            return Image.createImage((String)string);
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

    public static Image a(String string) {
        return az.c(string);
    }

    public static Image b(String string) {
        if (a) {
            return az.d(string);
        }
        return az.c(string);
    }
}

