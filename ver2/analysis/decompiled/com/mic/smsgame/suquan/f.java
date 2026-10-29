/*
 * Decompiled with CFR 0.152.
 */
package com.mic.smsgame.suquan;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class f
extends bg {
    public static final void a() {
        dr.c(bg.b, 100);
        dr.c(bg.b, 101);
        dr.c(bg.b, 103);
        dr.c(bg.b, 104);
        dr.c(bg.b, 105);
        f.f();
        bg.a();
    }

    public static final void a(int n2, String string) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream.writeUTF(string);
            dataOutputStream.flush();
            dr.a(bg.b, n2, byteArrayOutputStream.toByteArray());
            dr.a(bg.b);
            dataOutputStream.close();
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    public static final String a(int n2) {
        byte[] byArray;
        block3: {
            try {
                byArray = dr.b(bg.b, 104);
                if (byArray != null) break block3;
                return "";
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
                return null;
            }
        }
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(byArray));
        String string = dataInputStream.readUTF();
        dataInputStream.close();
        return string;
    }

    public static final void a(int n2, int n3) {
        dr.a(bg.b, n2, ai.a(n3));
        dr.a(bg.b);
    }

    public static final int b(int n2) {
        byte[] byArray = dr.b(bg.b, n2);
        if (byArray == null) {
            return 0;
        }
        return ai.a(byArray);
    }

    public static final void a(byte[] byArray) {
        dr.a(bg.b, 100, byArray);
        dr.a(bg.b);
    }

    public static final byte[] b() {
        byte[] byArray = dr.b(bg.b, 100);
        if (byArray == null) {
            return null;
        }
        return byArray;
    }

    public static final boolean c() {
        return dr.a(bg.b, 100);
    }

    public static final void a(byte[] byArray, int n2, int n3) {
        dr.a(bg.c, 106, byArray, n2, n3);
        dr.a(bg.c);
    }

    public static final byte[] d() {
        byte[] byArray = dr.b(bg.c, 106);
        return byArray;
    }

    public static final boolean e() {
        return dr.a(bg.c, 106);
    }

    public static final void f() {
        dr.c(bg.c, 106);
        dr.a(bg.c);
    }
}

