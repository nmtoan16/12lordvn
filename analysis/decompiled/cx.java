/*
 * Decompiled with CFR 0.152.
 */
public class cx {
    public static final byte[][] a = new byte[][]{{103, 3, -118, -86}, {-67, -99, -59, -116}, {32, 4, -32, -14}, {7, 108, -51, -109}, {-10, -107, 67, -96}, {116, -102, -114, 108}, {71, 56, -88, -10}, {-128, -87, -17, -45}, {-65, 67, 52, -94}, {50, -72, -110, 61}};
    public static final byte[][] b = new byte[][]{{-125, 12, -1, -80}, {-70, -61, 100, -82}, {-110, -45, -90, -81}, {-14, 55, 7, -104}, {-37, -45, -18, -18}, {119, 86, -37, 35}, {-103, -82, -106, 49}, {-30, 53, 17, -110}, {-86, 23, 98, -98}, {-102, 103, 86, 74}};
    public static final byte[][] c = new byte[][]{{5, -1, -84, 81}, {122, -7, 6, -51}, {-73, 70, 85, -18}, {-83, 104, -66, 81}, {-15, -79, -56, 68}, {35, 24, 49, -17}, {-6, -71, 55, -40}, {124, 98, -53, -34}, {66, 28, 84, 78}, {-62, 32, 35, -48}};
    public static final byte[][] d = new byte[][]{{68, 65, -8, -61}, {-35, 76, 8, 72}, {107, -37, 102, 38}, {-114, -109, 73, 6}, {48, -22, 4, 4}, {11, 67, 89, -56}, {-66, -85, 17, 111}, {-104, 58, 67, -42}, {82, 2, -100, -95}, {29, 69, -46, 16}};
    public static boolean e = false;
    public static int f;
    public static int g;
    public static int h;
    public static int i;
    public static int j;
    public static int k;
    public static int l;
    public static int m;
    public static boolean n;
    public static int o;
    public static int p;
    public static int q;
    public static int r;
    public static int s;
    public static int t;
    public static int[] u;
    public static int v;
    public static boolean w;
    public static boolean x;
    public static int y;
    public static int z;
    public static boolean A;
    public static boolean B;
    public static boolean C;
    public static boolean D;
    public static boolean E;
    public static boolean F;
    public static int G;
    public static boolean H;
    public static int I;
    public static int J;
    public static int K;
    public static String L;
    public static String M;
    public static boolean N;
    public static boolean O;
    public static int P;
    public static int Q;
    public static int R;
    public static int S;
    public static boolean T;
    public static String U;
    public static boolean V;

    static {
        j = 0;
        k = 0;
        n = false;
        o = -1;
        p = -1;
        q = -1;
        r = -1;
        s = -1;
        t = -1;
        u = new int[21];
        v = 0;
        w = true;
        x = true;
        y = 80;
        z = 100;
        A = true;
        B = false;
        C = true;
        D = false;
        E = false;
        F = false;
        G = 0;
        H = false;
        I = 10;
        J = 4;
        M = null;
        N = false;
        O = false;
        R = 0;
        S = 200038;
        cx.a(S);
        T = false;
        U = ".mg";
        V = false;
    }

    public static void a() {
        int n2 = 0;
        while (n2 < 21) {
            cx.u[n2] = 0;
            ++n2;
        }
    }

    public static void a(int n2) {
        P = n2;
        int n3 = (n2 & 0xFF0000) >> 16;
        int n4 = (n2 & 0xFF00) >> 8;
        int n5 = n2 & 0xFF;
        int n6 = 6;
        Q = (255 - n3) / n6 + n3 << 16 | (255 - n4) / n6 + n4 << 8 | (255 - n5) / n6 + n5;
    }
}

