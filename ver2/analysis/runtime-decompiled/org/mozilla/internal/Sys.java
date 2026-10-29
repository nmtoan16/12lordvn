/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.cldc.isolate.Isolate
 *  com.sun.cldc.isolate.IsolateStartupException
 */
package org.mozilla.internal;

import com.sun.cldc.isolate.Isolate;
import com.sun.cldc.isolate.IsolateStartupException;

public final class Sys {
    private Sys() {
    }

    public static void copyArray(byte[] byArray, int n, byte[] byArray2, int n2, int n3) {
        if (byArray == byArray2) {
            System.arraycopy(byArray, n, byArray2, n2, n3);
            return;
        }
        if (n < 0 || n + n3 > byArray.length || n2 < 0 || n2 + n3 > byArray2.length || n3 < 0) {
            throw new ArrayIndexOutOfBoundsException("Invalid index.");
        }
        for (int i = 0; i < n3; ++i) {
            byArray2[n2++] = byArray[n++];
        }
    }

    public static void copyArray(char[] cArray, int n, char[] cArray2, int n2, int n3) {
        if (cArray == cArray2) {
            System.arraycopy(cArray, n, cArray2, n2, n3);
            return;
        }
        if (n < 0 || n + n3 > cArray.length || n2 < 0 || n2 + n3 > cArray2.length || n3 < 0) {
            throw new ArrayIndexOutOfBoundsException("Invalid index.");
        }
        for (int i = 0; i < n3; ++i) {
            cArray2[n2++] = cArray[n++];
        }
    }

    public static native void eval(String var0);

    public static native int getUnwindCount();

    public static void throwException(Exception exception) throws Exception {
        throw exception;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void runThread(Thread thread) {
        thread.run();
        Thread thread2 = thread;
        synchronized (thread2) {
            thread.notifyAll();
        }
    }

    public static void isolate0Entry(String string, String[] stringArray) throws IsolateStartupException {
        Isolate isolate = new Isolate(string, stringArray);
        isolate.start();
    }

    private static native void constructCurrentThread();

    private static native String getIsolateMain();

    private static native void executeMain(Class var0);

    public static void isolateEntryPoint(Isolate isolate) throws ClassNotFoundException {
        Sys.constructCurrentThread();
        Class<?> clazz = Class.forName(Sys.getIsolateMain());
        Sys.executeMain(clazz);
    }

    public static native void startProfile();

    public static native void stopProfile();

    public static void unwind() {
    }

    public static long unwind(long l) {
        return l;
    }

    public static double unwind(double d) {
        return d;
    }

    public static float unwind(float f) {
        return f;
    }

    public static int unwind(int n) {
        return n;
    }

    public static Object unwind(Object object) {
        return object;
    }

    public static void unwindFromInvoke() {
        Sys.unwind();
    }
}
