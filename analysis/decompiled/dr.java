/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.rms.RecordStore
 */
import javax.microedition.rms.RecordStore;

public class dr {
    private String a;
    private int b;
    private int c;
    private di d;
    private di e;
    private di f;
    private int g;

    private dr(String string, int n2) {
        this(string, n2, 2048);
    }

    private dr(String string, int n2, int n3) {
        this.c = n3;
        this.a = string;
        this.b = n2;
        this.d = new di();
        this.e = new di();
        this.f = new di();
        this.a();
    }

    private byte[] a(RecordStore recordStore) {
        byte[] byArray = new byte[this.c];
        l.a(recordStore, byArray);
        l.a(recordStore, byArray);
        l.a(recordStore, new byte[]{1});
        return byArray;
    }

    private void a() {
        RecordStore recordStore = l.a(this.a, false);
        try {
            byte[] byArray = null;
            if (recordStore == null || l.b(recordStore) < 3) {
                l.a(this.a);
                recordStore = l.a(this.a, true);
                byArray = this.a(recordStore);
                this.g = 1;
            } else {
                this.g = 0;
                byte[] byArray2 = l.a(recordStore, 3);
                if (byArray2 != null) {
                    this.g = byArray2[0];
                }
                if (this.g < 1 || this.g > 2) {
                    this.g = 1;
                }
                byArray = l.a(recordStore, this.g);
            }
            int n2 = l.b(recordStore);
            int n3 = 3;
            l.a(recordStore);
            int n4 = 0;
            int n5 = ai.a(byArray, n4);
            n4 += 4;
            int n6 = 0;
            while (n6 < n5) {
                bd bd2 = new bd();
                n4 += bd2.a(byArray, n4);
                this.d.a(bd2);
                n3 += bd2.c.length;
                ++n6;
            }
            n6 = ai.a(byArray, n4);
            n4 += 4;
            int n7 = 0;
            while (n7 < n6) {
                this.e.a(new Integer(ai.a(byArray, n4)));
                n4 += 4;
                ++n7;
            }
            if (n2 - 3 > (n3 += this.e.b())) {
                this.a(n2);
            }
        }
        catch (Throwable throwable) {
            l.a(recordStore);
            throwable.printStackTrace();
        }
    }

    private void a(int n2) {
        boolean[] blArray = new boolean[n2];
        int n3 = this.d.b() - 1;
        while (n3 > -1) {
            bd bd2 = (bd)this.d.b(n3);
            int n4 = bd2.c.length - 1;
            while (n4 > -1) {
                blArray[bd2.c[n4]] = true;
                --n4;
            }
            --n3;
        }
        n3 = this.e.b() - 1;
        while (n3 > -1) {
            int n5 = (Integer)this.e.b(n3);
            blArray[n5] = true;
            --n3;
        }
        n3 = blArray.length - 1;
        while (n3 > 2) {
            if (!blArray[n3]) {
                this.e.a(new Integer(n3));
            }
            --n3;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private synchronized byte[] b(int n2) {
        RecordStore recordStore = l.a(this.a, false);
        try {
            bd bd2 = this.d(n2);
            if (bd2 == null || bd2.c == null || recordStore == null) {
                return null;
            }
            if (bd2.d != null) {
                return bd2.d;
            }
            byte[] byArray = new byte[bd2.b];
            int n3 = 0;
            int[] nArray = bd2.c;
            int n4 = 0;
            while (true) {
                if (n4 >= nArray.length) {
                    l.a(recordStore);
                    return byArray;
                }
                byte[] byArray2 = l.a(recordStore, nArray[n4]);
                if (byArray2 == null) {
                    this.d.b(bd2);
                    this.b(bd2.c, 0, bd2.c.length);
                    return null;
                }
                int n5 = byArray2.length;
                if (n5 > byArray.length - n3) {
                    n5 = byArray.length - n3;
                }
                System.arraycopy(byArray2, 0, byArray, n3, n5);
                n3 += n5;
                ++n4;
            }
        }
        catch (Throwable throwable) {
            l.a(recordStore);
            throwable.printStackTrace();
            return null;
        }
    }

    private synchronized void a(int n2, byte[] byArray, int n3, int n4) {
        if (byArray == null) {
            return;
        }
        try {
            bd bd2 = this.d(n2);
            if (bd2 != null) {
                if (bd2.c != null) {
                    this.b(bd2.c, 0, bd2.c.length);
                }
            } else {
                bd2 = new bd();
                this.d.a(bd2);
            }
            bd2.a = n2;
            bd2.b = n4;
            int n5 = n4 / this.b;
            if (n4 % this.b != 0) {
                ++n5;
            }
            int[] nArray = new int[n5];
            int n6 = 0;
            while (n6 < nArray.length) {
                int n7 = this.e.b() - 1;
                if (n7 > -1) {
                    nArray[n6] = (Integer)this.e.b(n7);
                    this.e.a(n7);
                } else {
                    nArray[n6] = -1;
                }
                ++n6;
            }
            bd2.c = nArray;
            bd2.d = new byte[n4];
            System.arraycopy(byArray, n3, bd2.d, 0, n4);
        }
        catch (Throwable throwable) {
            this.c(n2);
            throwable.printStackTrace();
        }
    }

    private synchronized void b() {
        RecordStore recordStore = l.a(this.a, false);
        try {
            if (recordStore == null) {
                recordStore = l.a(this.a, true);
                this.a(recordStore);
            }
            int n2 = this.d.b() - 1;
            while (n2 > -1) {
                bd bd2 = (bd)this.d.b(n2);
                if (bd2.d != null) {
                    this.a(recordStore, bd2.d, bd2.c);
                    bd2.d = null;
                }
                --n2;
            }
            byte[] byArray = new byte[this.c];
            int n3 = 0;
            System.arraycopy(ai.a(this.d.b()), 0, byArray, n3, 4);
            n3 += 4;
            int n4 = this.d.b() - 1;
            while (n4 > -1) {
                bd bd3 = (bd)this.d.b(n4);
                byte[] byArray2 = bd3.a();
                System.arraycopy(byArray2, 0, byArray, n3, byArray2.length);
                n3 += byArray2.length;
                --n4;
            }
            n4 = this.f.b() - 1;
            while (n4 > -1) {
                this.e.a(this.f.b(n4));
                --n4;
            }
            this.f.a();
            System.arraycopy(ai.a(this.e.b()), 0, byArray, n3, 4);
            n3 += 4;
            n4 = this.e.b() - 1;
            while (n4 > -1) {
                int n5 = (Integer)this.e.b(n4);
                System.arraycopy(ai.a(n5), 0, byArray, n3, 4);
                n3 += 4;
                --n4;
            }
            n4 = this.g == 1 ? 2 : 1;
            l.a(recordStore, n4, byArray);
            l.a(recordStore, 3, new byte[]{(byte)n4});
            l.a(recordStore);
            this.g = n4;
        }
        catch (Throwable throwable) {
            l.a(recordStore);
            throwable.printStackTrace();
        }
    }

    private void c(int n2) {
        try {
            int n3 = this.d.b() - 1;
            while (n3 > -1) {
                bd bd2 = (bd)this.d.b(n3);
                if (bd2.a == n2) {
                    this.d.a(n3);
                    if (bd2.c != null) {
                        this.a(bd2.c, 0, bd2.c.length);
                    }
                }
                --n3;
            }
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private bd d(int n2) {
        try {
            int n3 = this.d.b() - 1;
            while (n3 > -1) {
                bd bd2 = (bd)this.d.b(n3);
                if (bd2.a == n2) {
                    return bd2;
                }
                --n3;
            }
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    private void a(int[] nArray, int n2, int n3) {
        try {
            if (nArray == null) {
                return;
            }
            n3 = n2 + n3;
            int n4 = n2;
            while (n4 < n3) {
                this.e.a(new Integer(nArray[n4]));
                ++n4;
            }
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private void b(int[] nArray, int n2, int n3) {
        try {
            if (nArray == null) {
                return;
            }
            n3 = n2 + n3;
            int n4 = n2;
            while (n4 < n3) {
                this.f.a(new Integer(nArray[n4]));
                ++n4;
            }
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private void a(RecordStore recordStore, byte[] byArray, int[] nArray) {
        try {
            byte[] byArray2 = new byte[this.b];
            int n2 = 0;
            int n3 = 0;
            while (n3 < nArray.length) {
                int n4 = byArray.length - n2;
                if (n4 > byArray2.length) {
                    n4 = byArray2.length;
                }
                System.arraycopy(byArray, n2, byArray2, 0, n4);
                n2 += n4;
                if (nArray[n3] > 3) {
                    l.a(recordStore, nArray[n3], byArray2);
                } else {
                    nArray[n3] = l.a(recordStore, byArray2);
                }
                ++n3;
            }
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean e(int n2) {
        try {
            int n3 = this.d.b() - 1;
            while (true) {
                if (n3 <= -1) {
                    return false;
                }
                bd bd2 = (bd)this.d.b(n3);
                if (bd2.a == n2) {
                    return true;
                }
                --n3;
            }
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return false;
    }

    public static dr a(String string, int n2) {
        return new dr(string, n2);
    }

    public static boolean a(dr dr2, int n2) {
        return dr2.e(n2);
    }

    public static void a(dr dr2, int n2, byte[] byArray) {
        dr2.a(n2, byArray, 0, byArray.length);
    }

    public static void a(dr dr2, int n2, byte[] byArray, int n3, int n4) {
        dr2.a(n2, byArray, n3, n4);
    }

    public static byte[] b(dr dr2, int n2) {
        return dr2.b(n2);
    }

    public static void c(dr dr2, int n2) {
        dr2.c(n2);
    }

    public static void a(dr dr2) {
        dr2.b();
    }
}

