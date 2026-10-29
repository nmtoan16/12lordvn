/*
 * Decompiled with CFR 0.152.
 */
public class ci
implements Runnable {
    private static ci a;
    private static Object b;
    private boolean c = true;
    private at[] d;
    private int e;

    private ci() {
        b = new Object();
        this.c = false;
    }

    public void a(int n2) {
        if (n2 <= 0) {
            throw new RuntimeException("Size of pool cannot be less than or equal 0");
        }
        this.d = new at[n2];
        this.c = false;
        new Thread(this).start();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(at at2) {
        if (this.d == null) {
            return;
        }
        int n2 = 0;
        while (n2 < this.d.length) {
            if (this.d[n2] == null) {
                this.d[n2] = at2;
                ++this.e;
                try {
                    Object object = b;
                    synchronized (object) {
                        b.notify();
                    }
                }
                catch (Exception exception) {
                    try {
                        Object object = b;
                        synchronized (object) {
                            b.notify();
                        }
                    }
                    catch (Exception exception2) {}
                }
                break;
            }
            ++n2;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void run() {
        while (!this.c) {
            int n2 = 0;
            while (n2 < this.d.length) {
                if (this.d[n2] != null) {
                    this.d[n2].c();
                    this.d[n2] = null;
                    --this.e;
                }
                ++n2;
            }
            if (this.e > 0) continue;
            try {
                Object object = b;
                synchronized (object) {
                    b.wait();
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public static ci a() {
        if (a == null) {
            a = new ci();
        }
        return a;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void b() {
        this.c = true;
        Object object = b;
        synchronized (object) {
            b.notify();
        }
    }
}

