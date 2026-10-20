/*
 * Decompiled with CFR 0.152.
 */
import java.io.InputStream;

public class bg {
    public static int a = 10;
    public static dr b = dr.a("16b", 16);
    public static dr c = dr.a("161b", 161);

    public static int a(byte[] byArray, InputStream inputStream, int n2) {
        int n3 = n2;
        while (n3 < byArray.length) {
            int n4 = inputStream.read(byArray, n3, byArray.length - n3);
            if (n4 < 0) {
                return -1;
            }
            n3 += n4;
        }
        return n3;
    }

    public static final boolean g() {
        return dr.a(b, 14);
    }

    public static final byte[] h() {
        return dr.b(b, 14);
    }

    public static void b(byte[] byArray) {
        dr.a(b, 14, byArray, 0, byArray.length);
        dr.a(b);
    }

    public static void i() {
        if (!cx.C) {
            return;
        }
        cx.G = 0;
        byte[] byArray = dr.b(b, 8);
        if (byArray != null && byArray.length >= 4) {
            cx.G = ai.a(byArray);
        }
        byArray = ai.a(++cx.G);
        dr.a(b, 8, byArray, 0, byArray.length);
        dr.a(b);
    }

    private static final int b() {
        int n2 = 1;
        byte[] byArray = dr.b(b, 7);
        n2 = byArray != null ? ai.a(byArray) : cx.J;
        if (n2 < 1) {
            n2 = 1;
        }
        if (n2 > cx.J) {
            n2 = cx.J;
        }
        dr.a(b, 7, ai.a(n2 > 1 ? n2 - 1 : 1), 0, 4);
        dr.a(b);
        return n2;
    }

    public static boolean j() {
        return !dr.a(b, 4);
    }

    public static void k() {
        if (bg.j()) {
            dr.a(b, 4, new byte[]{1}, 0, 1);
        }
        dr.a(b, 5, ai.a(bg.b()), 0, 4);
        dr.a(b);
    }

    public static boolean l() {
        byte[] byArray = dr.b(b, 5);
        if (byArray == null) {
            return false;
        }
        int n2 = ai.a(byArray);
        return n2 >= 1;
    }

    public static int m() {
        byte[] byArray = dr.b(b, 5);
        if (byArray == null) {
            return 0;
        }
        int n2 = ai.a(byArray);
        if (n2 < 1) {
            return 0;
        }
        return n2;
    }

    public static void n() {
        byte[] byArray = dr.b(b, 5);
        int n2 = 0;
        if (byArray != null) {
            n2 = ai.a(byArray);
        }
        if (--n2 < 0) {
            n2 = 0;
        }
        dr.a(b, 5, ai.a(n2), 0, 4);
        dr.a(b);
    }

    public static void c(int n2) {
        byte[] byArray = dr.b(b, 5);
        int n3 = 0;
        if (byArray != null) {
            n3 = ai.a(byArray);
        }
        dr.a(b, 5, ai.a(n3 += n2), 0, 4);
        dr.a(b);
    }

    public static byte[] o() {
        return dr.b(b, 11);
    }

    public static void d(int n2) {
        dr.a(b, 11, ai.a(n2), 0, 4);
        dr.a(b);
    }

    public static void p() {
        if (!cx.C) {
            return;
        }
        cx.A = true;
        byte[] byArray = dr.b(b, 13);
        if (byArray != null) {
            if (byArray[0] <= 0) {
                cx.A = false;
            }
        } else {
            bg.a(false);
        }
    }

    public static void a(boolean bl2) {
        if (!cx.C) {
            return;
        }
        byte by2 = 3;
        byte[] byArray = dr.b(b, 13);
        if (byArray != null) {
            by2 = byArray[0];
        }
        by2 = bl2 ? (byte)((byte)(by2 - 1)) : (byte)3;
        cx.A = by2 > 0;
        dr.a(b, 13, new byte[]{by2}, 0, 1);
        dr.a(b);
    }

    public static byte[] q() {
        return dr.b(b, 12);
    }

    public static void r() {
        dr.a(b, 12, ai.a(1), 0, 4);
        dr.a(b);
    }

    private static byte[] c() {
        byte[] byArray = dr.b(c, 1);
        if (byArray == null) {
            byArray = new byte[(a + 1) * 4];
            byte[] byArray2 = ai.a(ay.a(4) + 1);
            System.arraycopy(byArray2, 0, byArray, byArray.length - 4, 4);
            dr.a(c, 1, byArray, 0, byArray.length);
            dr.a(c);
        }
        return byArray;
    }

    public static int[] s() {
        int[] nArray = new int[a];
        byte[] byArray = bg.c();
        int n2 = 0;
        while (n2 < nArray.length) {
            nArray[n2] = ai.a(byArray, n2 * 4);
            ++n2;
        }
        return nArray;
    }

    /*
     * WARNING - void declaration
     */
    public static boolean a(int[] nArray) {
        int n2;
        void var2_3;
        if (nArray == null) {
            return false;
        }
        boolean[] blArray = new boolean[4];
        int bl2 = nArray.length - 1;
        while (var2_3 > -1) {
            if (nArray[var2_3] > 0) {
                blArray[nArray[var2_3] - 1] = true;
            }
            --var2_3;
        }
        boolean bl3 = true;
        int n3 = 0;
        while (n3 < blArray.length) {
            n2 &= blArray[n3];
            ++n3;
        }
        return n2 != 0;
    }

    private static void b(int[] nArray) {
        if (nArray == null) {
            return;
        }
        byte[] byArray = bg.c();
        int n2 = 0;
        while (n2 < nArray.length) {
            System.arraycopy(ai.a(nArray[n2]), 0, byArray, n2 * 4, 4);
            ++n2;
        }
        dr.a(c, 1, byArray, 0, byArray.length);
        dr.a(c);
    }

    public static void a(int n2, int n3, String string, String string2) {
        if (Math.abs(n2) >= a) {
            return;
        }
        byte[] byArray = dr.b(c, 1);
        if (byArray == null) {
            return;
        }
        int[] nArray = new int[a + 1];
        int[] nArray2 = new int[4];
        int n4 = 0;
        while (n4 < nArray.length) {
            nArray[n4] = ai.a(byArray, n4 * 4);
            if (n4 < nArray.length - 1 && nArray[n4] > 0) {
                int n5 = nArray[n4] - 1;
                nArray2[n5] = nArray2[n5] + 1;
            }
            ++n4;
        }
        n4 = -1;
        if (n3 != 1) {
            int n6 = Math.abs(nArray[nArray.length - 1]) - 1;
            int n7 = nArray2[0] + nArray2[1] + nArray2[2] + nArray2[3];
            if (n7 >= 10) {
                return;
            }
            if (n7 >= 7) {
                int n8 = 0;
                while (n8 < nArray2.length) {
                    if (n8 != n6 && nArray2[n8] <= 0) {
                        n4 = n8;
                        break;
                    }
                    ++n8;
                }
                if (n4 == -1) {
                    while ((n4 = ay.a(4)) == n6) {
                    }
                }
            } else {
                while ((n4 = ay.a(4)) == n6) {
                }
            }
            ++n4;
        } else {
            if (string == null) {
                return;
            }
            n4 = Math.abs(nArray[nArray.length - 1]);
            if (string != null) {
                byte[] byArray2 = string.getBytes();
                dr.a(c, 2, byArray2, 0, byArray2.length);
            }
        }
        d.a("index=" + n2 + ",luckyNum=" + n3 + ",codeWin=" + string + "recordChoose=" + n4);
        byte[] byArray3 = ai.a(n4);
        System.arraycopy(byArray3, 0, byArray, n2 * 4, 4);
        dr.a(c, 1, byArray, 0, byArray.length);
        if (string2 != null) {
            if (n3 == 1 && string != null) {
                byArray = string2.trim().getBytes();
                dr.a(c, 3, byArray, 0, byArray.length);
                byArray = string.trim().getBytes();
                dr.a(c, 2, byArray, 0, byArray.length);
            } else {
                byArray = dr.b(c, 2);
                if (byArray == null) {
                    byArray = string2.trim().getBytes();
                    dr.a(c, 3, byArray, 0, byArray.length);
                }
            }
        }
        dr.a(c);
    }

    private static boolean c(int[] nArray) {
        boolean[] blArray = new boolean[5];
        boolean bl2 = false;
        int n2 = nArray.length;
        int n3 = 0;
        while (n3 < n2) {
            if (nArray[n3] > 0) {
                if (blArray[nArray[n3]]) {
                    bl2 = true;
                    nArray[n3] = 0;
                } else {
                    blArray[nArray[n3]] = true;
                }
            }
            ++n3;
        }
        return bl2;
    }

    public static int t() {
        int[] nArray = bg.s();
        int n2 = nArray.length;
        int n3 = 0;
        while (n3 < n2) {
            if (nArray[n3] == 0) {
                nArray[n3] = -1;
                bg.b(nArray);
                return n3;
            }
            ++n3;
        }
        n3 = nArray.length - 1;
        while (n3 >= 0) {
            if (nArray[n3] == -1) {
                return n3;
            }
            --n3;
        }
        if (bg.c(nArray)) {
            bg.b(nArray);
            return bg.t();
        }
        return -1;
    }

    public static void e(int n2) {
        int n3 = cx.G;
        an.c("reco" + n2, n3, null);
    }

    public static byte[] u() {
        return dr.b(c, 2);
    }

    public static void a(int n2, int n3, int n4) {
        byte[] byArray = null;
        if (n2 < n4) {
            byArray = dr.b(c, 9);
            if (byArray != null) {
                n3 += ai.a(byArray, n2 * 4);
            } else {
                byArray = new byte[n4 * 4];
            }
        }
        n3 = n3 < 0 ? 0 : n3;
        byte[] byArray2 = ai.a(n3);
        System.arraycopy(byArray2, 0, byArray, n2 * 4, 4);
        dr.a(c, 9, byArray, 0, byArray.length);
        dr.a(c);
    }

    public static int[] v() {
        byte[] byArray = null;
        byArray = dr.b(c, 9);
        if (byArray == null) {
            return null;
        }
        int[] nArray = new int[byArray.length / 4];
        int n2 = 0;
        while (n2 < nArray.length) {
            nArray[n2] = ai.a(byArray, n2 * 4);
            ++n2;
        }
        return nArray;
    }

    public static int f(int n2) {
        byte[] byArray = null;
        byArray = dr.b(c, 9);
        if (byArray == null) {
            return 0;
        }
        return ai.a(byArray, n2 * 4);
    }

    public static void b(int n2, int n3) {
        byte[] byArray = null;
        byArray = dr.b(c, 9);
        if (byArray == null) {
            return;
        }
        int n4 = ai.a(byArray, n2 * 4);
        if ((n4 -= n3) < 0) {
            n4 = 0;
        }
        byte[] byArray2 = ai.a(n4);
        System.arraycopy(byArray2, 0, byArray, n2 * 4, 4);
        dr.a(c, 9, byArray, 0, byArray.length);
        dr.a(c);
    }

    public static void g(int n2) {
        bg.b(n2, 1);
    }

    public static String w() {
        byte[] byArray = dr.b(c, 3);
        if (byArray == null) {
            return null;
        }
        return new String(byArray);
    }

    public static void x() {
        try {
            dr.c(c, 1);
            dr.c(c, 2);
            dr.c(c, 3);
            dr.a(c);
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public static void a() {
        try {
            dr.c(b, 10);
            dr.c(b, 6);
            dr.c(b, 5);
            dr.c(b, 16);
            dr.c(c, 9);
            dr.a(b);
            dr.a(c);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

