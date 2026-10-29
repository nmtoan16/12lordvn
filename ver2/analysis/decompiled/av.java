/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class av {
    private int[] a;
    private int[] b;
    private Image c;
    private int d;

    public av() {
        int[] nArray = new int[10];
        nArray[1] = 7;
        nArray[2] = 11;
        nArray[3] = 18;
        nArray[4] = 25;
        nArray[5] = 33;
        nArray[6] = 40;
        nArray[7] = 47;
        nArray[8] = 54;
        nArray[9] = 61;
        this.a = nArray;
        this.b = new int[]{7, 4, 7, 7, 8, 7, 7, 7, 7, 7};
        this.c = az.b("/f/nb");
        this.d = this.c.getHeight();
    }

    public int a(int n2) {
        int n3 = 0;
        int n4 = n2;
        do {
            int n5 = n4 % 10;
            n3 += this.b[n5] + 1;
        } while ((n4 /= 10) > 0);
        return n3;
    }

    public void a(Graphics graphics, int n2, int n3, int n4) {
        int n5 = this.a(n2);
        n3 += n5;
        do {
            int n6 = n2 % 10;
            y.a(graphics, this.c, this.a[n6], 0, this.b[n6], this.d, n3 -= this.b[n6] + 1, n4, 0);
        } while ((n2 /= 10) > 0);
    }
}

