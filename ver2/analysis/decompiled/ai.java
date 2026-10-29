/*
 * Decompiled with CFR 0.152.
 */
public class ai {
    public static int a(byte[] byArray) {
        return ai.a(byArray[0], byArray[1], byArray[2], byArray[3]);
    }

    public static int a(byte by2, byte by3, byte by4, byte by5) {
        return ((by2 & 0xFF) << 24) + ((by3 & 0xFF) << 16) + ((by4 & 0xFF) << 8) + (by5 & 0xFF);
    }

    public static int a(byte[] byArray, int n2) {
        return ai.a(byArray[n2], byArray[n2 + 1], byArray[n2 + 2], byArray[n2 + 3]);
    }

    public static byte[] a(int n2) {
        return new byte[]{(byte)(n2 >>> 24 & 0xFF), (byte)(n2 >>> 16 & 0xFF), (byte)(n2 >>> 8 & 0xFF), (byte)(n2 >>> 0 & 0xFF)};
    }
}

