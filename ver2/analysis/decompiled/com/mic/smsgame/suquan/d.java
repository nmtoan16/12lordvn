/*
 * Decompiled with CFR 0.152.
 */
package com.mic.smsgame.suquan;

import com.mic.smsgame.suquan.e;
import com.mic.smsgame.suquan.j;

public class d
extends j {
    private int[][] a;

    public d() {
        int[][] nArrayArray = new int[12][];
        nArrayArray[0] = new int[]{9, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9};
        nArrayArray[1] = new int[]{9, 11, 10, 10, 10, 10, 10, 10, 10, 10, 11, 9};
        int[] nArray = new int[12];
        nArray[0] = 9;
        nArray[1] = 11;
        nArray[2] = 1;
        nArray[3] = 4;
        nArray[5] = 2;
        nArray[6] = 3;
        nArray[7] = 5;
        nArray[8] = 5;
        nArray[10] = 11;
        nArray[11] = 9;
        nArrayArray[2] = nArray;
        int[] nArray2 = new int[12];
        nArray2[0] = 9;
        nArray2[1] = 11;
        nArray2[2] = 1;
        nArray2[4] = 4;
        nArray2[6] = 2;
        nArray2[7] = 5;
        nArray2[8] = 4;
        nArray2[9] = 1;
        nArray2[10] = 11;
        nArray2[11] = 9;
        nArrayArray[3] = nArray2;
        int[] nArray3 = new int[12];
        nArray3[0] = 9;
        nArray3[1] = 11;
        nArray3[3] = 1;
        nArray3[6] = 3;
        nArray3[7] = 1;
        nArray3[10] = 11;
        nArray3[11] = 9;
        nArrayArray[4] = nArray3;
        int[] nArray4 = new int[12];
        nArray4[0] = 9;
        nArray4[1] = 11;
        nArray4[2] = 1;
        nArray4[4] = 5;
        nArray4[5] = 1;
        nArray4[6] = 1;
        nArray4[9] = 5;
        nArray4[10] = 11;
        nArray4[11] = 9;
        nArrayArray[5] = nArray4;
        nArrayArray[6] = new int[]{9, 11, 1, 3, 2, 4, 2, 5, 2, 6, 11, 9};
        nArrayArray[7] = new int[]{9, 11, 3, 2, 3, 2, 4, 2, 4, 2, 11, 9};
        int[] nArray5 = new int[12];
        nArray5[0] = 9;
        nArray5[1] = 11;
        nArray5[2] = 4;
        nArray5[3] = 4;
        nArray5[4] = 1;
        nArray5[5] = 3;
        nArray5[7] = 4;
        nArray5[8] = 2;
        nArray5[9] = 5;
        nArray5[10] = 11;
        nArray5[11] = 9;
        nArrayArray[8] = nArray5;
        int[] nArray6 = new int[12];
        nArray6[0] = 9;
        nArray6[1] = 11;
        nArray6[2] = 1;
        nArray6[3] = 1;
        nArray6[6] = 1;
        nArray6[7] = 1;
        nArray6[8] = 2;
        nArray6[9] = 3;
        nArray6[10] = 11;
        nArray6[11] = 9;
        nArrayArray[9] = nArray6;
        nArrayArray[10] = new int[]{9, 11, 10, 10, 10, 10, 10, 10, 10, 10, 11, 9};
        nArrayArray[11] = new int[]{9, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9};
        this.a = nArrayArray;
    }

    public void a(int[][] nArray) {
        int n2 = 2;
        while (n2 < 10) {
            int n3 = 2;
            while (n3 < 10) {
                nArray[n2][n3] = this.a[n2][n3];
                ++n3;
            }
            ++n2;
        }
        nArray = this.a;
    }

    public void a(int[][] nArray, int n2, int n3) {
        nArray[n2][n3] = 1 + ay.a(e.h.length - 1);
    }
}

