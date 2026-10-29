/*
 * Decompiled with CFR 0.152.
 */
package com.mic.smsgame.suquan;

import com.mic.smsgame.suquan.e;
import com.mic.smsgame.suquan.j;

public class w
extends j {
    public void a(int[][] nArray) {
        int n2;
        this.b(nArray);
        int n3 = e.h.length;
        boolean bl2 = false;
        int n4 = 2;
        while (n4 < 10) {
            n2 = bl2 ? 3 : 2;
            bl2 = !bl2;
            while (n2 < 10) {
                nArray[n4][n2] = ay.a(n3);
                n2 += 2;
            }
            ++n4;
        }
        n4 = ay.a(5) + 2 + 2;
        if (nArray[n4][n2 = ay.a(6) + 1 + 2] != 7) {
            --n4;
        }
        int n5 = ay.a(4);
        switch (n5) {
            case 0: {
                int n6 = nArray[n4 - 1][n2];
                nArray[n4][n2 + 1] = n6;
                nArray[n4][n2 - 1] = n6;
                break;
            }
            case 1: {
                int n7 = nArray[n4 + 1][n2];
                nArray[n4][n2 + 1] = n7;
                nArray[n4][n2 - 1] = n7;
                break;
            }
            case 2: {
                int n8 = nArray[n4][n2 - 1];
                nArray[n4 + 1][n2] = n8;
                nArray[n4 - 1][n2] = n8;
                break;
            }
            default: {
                int n9 = nArray[n4][n2 + 1];
                nArray[n4 + 1][n2] = n9;
                nArray[n4 - 1][n2] = n9;
            }
        }
        bl2 = true;
        int n10 = 2;
        while (n10 < 10) {
            int n11 = bl2 ? 3 : 2;
            bl2 = !bl2;
            while (n11 < 10) {
                int n12;
                boolean[] blArray = new boolean[e.h.length];
                int n13 = nArray[n10][n11 - 1];
                int n14 = nArray[n10][n11 - 2];
                int n15 = nArray[n10][n11 + 1];
                int n16 = nArray[n10][n11 + 2];
                if (n13 == n14) {
                    blArray[n13] = true;
                }
                if (n15 == n16) {
                    blArray[n15] = true;
                }
                if (n15 == n13) {
                    blArray[n13] = true;
                }
                int n17 = nArray[n10 - 1][n11];
                int n18 = nArray[n10 - 2][n11];
                int n19 = nArray[n10 + 1][n11];
                int n20 = nArray[n10 + 2][n11];
                if (n17 == n18) {
                    blArray[n17] = true;
                }
                if (n19 == n20) {
                    blArray[n19] = true;
                }
                if (n17 == n19) {
                    blArray[n17] = true;
                }
                if (blArray[n12 = ay.a(blArray.length)]) {
                    while (blArray[n12 = (n12 + 1) % blArray.length]) {
                    }
                }
                nArray[n10][n11] = n12;
                n11 += 2;
            }
            ++n10;
        }
    }

    public void a(int[][] nArray, int n2, int n3) {
        nArray[n2][n3] = ay.a(e.h.length);
        if (nArray[n2][n3] == 0 && ay.a(100) < 11) {
            nArray[n2][n3] = 6;
        }
    }
}

