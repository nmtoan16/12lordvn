/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class y {
    static int a = 255;
    public static byte[][] b = new byte[][]{{1, 2, 1}, {2, 4, 2}, {1, 2, 1}};
    public static byte[][] c;
    public static dl d;

    static {
        byte[][] byArrayArray = new byte[5][];
        byte[] byArray = new byte[5];
        byArray[1] = 1;
        byArray[2] = 2;
        byArray[3] = 1;
        byArrayArray[0] = byArray;
        byArrayArray[1] = new byte[]{1, 3, 5, 3, 1};
        byArrayArray[2] = new byte[]{2, 5, 9, 5, 2};
        byArrayArray[3] = new byte[]{1, 3, 5, 3, 1};
        byte[] byArray2 = new byte[5];
        byArray2[1] = 1;
        byArray2[2] = 2;
        byArray2[3] = 1;
        byArrayArray[4] = byArray2;
        c = byArrayArray;
        d = new dl(0, 0, cx.f, cx.g);
    }

    public static void a(Graphics graphics, dl dl2) {
        dl2.a = graphics.getClipX();
        dl2.b = graphics.getClipY();
        dl2.c = graphics.getClipWidth();
        dl2.d = graphics.getClipHeight();
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        int n10 = n2;
        int n11 = n3;
        int n12 = n6;
        int n13 = n7;
        long l2 = n10;
        l2 += (long)n4;
        long l3 = n11;
        l3 += (long)n5;
        long l4 = n12;
        l4 += (long)n8;
        long l5 = n13;
        l5 += (long)n9;
        if (n10 < n12) {
            n10 = n12;
        }
        if (n11 < n13) {
            n11 = n13;
        }
        if (l2 > l4) {
            l2 = l4;
        }
        if (l3 > l5) {
            l3 = l5;
        }
        l3 -= (long)n11;
        if ((l2 -= (long)n10) < Integer.MIN_VALUE) {
            l2 = Integer.MIN_VALUE;
        }
        if (l3 < Integer.MIN_VALUE) {
            l3 = Integer.MIN_VALUE;
        }
        graphics.setClip(n10, n11, (int)l2, (int)l3);
    }

    public static void b(Graphics graphics, dl dl2) {
        graphics.setClip(dl2.a, dl2.b, dl2.c, dl2.d);
    }

    public static void a(Graphics graphics, dl dl2, int n2, int n3, int n4, int n5) {
        y.a(graphics, dl2.a, dl2.b, dl2.c, dl2.d, n2, n3, n4, n5);
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5) {
        y.a(graphics, graphics.getClipX(), graphics.getClipY(), graphics.getClipWidth(), graphics.getClipHeight(), n2, n3, n4, n5);
    }

    public static final void a(Graphics graphics, Image image, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        if ((n8 & 1) > 0) {
            n6 -= n4 >> 1;
        } else if ((n8 & 8) > 0) {
            n6 -= n4;
        }
        if ((n8 & 2) > 0) {
            n7 -= n5 >> 1;
        } else if ((n8 & 0x20) > 0) {
            n7 -= n5;
        }
        y.a(graphics, d);
        y.a(graphics, d, n6, n7, n4, n5);
        graphics.drawImage(image, n6 - n2, n7 - n3, 0);
        y.b(graphics, d);
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6) {
        graphics.setColor(n2);
        int n7 = n3;
        int n8 = n4;
        int n9 = n3;
        int n10 = n4;
        while (true) {
            graphics.drawLine(n7, n8, n9, n10);
            if (n8 < n4 + n6) {
                n8 += 2;
            } else if ((n7 += 2) > n3 + n5) break;
            if (n9 < n3 + n5) {
                n9 += 2;
                continue;
            }
            if ((n10 += 2) > n4 + n6) break;
        }
    }

    public static void b(Graphics graphics, int n2, int n3, int n4, int n5, int n6) {
        graphics.setColor(n2);
        graphics.fillRect(n3 + 2, n4, n5 - 4, 1);
        graphics.fillRect(n3 + 2, n4 + n6 - 1, n5 - 4, 1);
        graphics.fillRect(n3, n4 + 2, 1, n6 - 4);
        graphics.fillRect(n3 + n5 - 1, n4 + 2, 1, n6 - 4);
        graphics.fillRect(n3 + 1, n4 + 1, 1, 1);
        graphics.fillRect(n3 + 1, n4 + n6 - 2, 1, 1);
        graphics.fillRect(n3 + n5 - 2, n4 + 1, 1, 1);
        graphics.fillRect(n3 + n5 - 2, n4 + n6 - 2, 1, 1);
    }
}

