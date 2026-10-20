/*
 * Decompiled with CFR 0.152.
 */
package com.mic.smsgame.suquan;

import com.mic.smsgame.suquan.e;

public abstract class j
implements e {
    protected void b(int[][] nArray) {
        int n2 = 9;
        while (n2 >= 2) {
            int n3 = 9;
            while (n3 >= 2) {
                nArray[n2][n3] = 7;
                --n3;
            }
            --n2;
        }
    }

    public abstract void a(int[][] var1);

    public abstract void a(int[][] var1, int var2, int var3);
}

