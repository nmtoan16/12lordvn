/*
 * Decompiled with CFR 0.152.
 */
import com.mic.smsgame.suquan.f;

public class dd {
    public static int a = -1;
    public static int b = 0;
    public static int c = -1;
    public static int d = -1;
    public static c e;
    public static c f;
    public static int g;
    public static boolean h;
    public static int i;
    public static int j;
    public static boolean k;
    private static final int[][] l;
    private static final int[][] m;
    private static final int[][] n;
    private static final int[][] o;
    private static final int[] p;
    private static final int[][] q;

    static {
        g = -1;
        h = false;
        i = 0;
        j = 0;
        k = false;
        int[][] nArrayArray = new int[46][];
        int[] nArray = new int[6];
        nArray[0] = 1;
        nArray[2] = 8;
        nArray[3] = 30;
        nArray[4] = 10;
        nArrayArray[0] = nArray;
        int[] nArray2 = new int[6];
        nArray2[2] = -1;
        nArrayArray[1] = nArray2;
        int[] nArray3 = new int[6];
        nArray3[1] = 1;
        nArray3[2] = -1;
        nArray3[3] = 40;
        nArray3[4] = 20;
        nArray3[5] = 1;
        nArrayArray[2] = nArray3;
        int[] nArray4 = new int[6];
        nArray4[2] = -1;
        nArrayArray[3] = nArray4;
        nArrayArray[4] = new int[]{2, 24, 42, 50, 30, 1};
        int[] nArray5 = new int[6];
        nArray5[2] = -1;
        nArrayArray[5] = nArray5;
        int[] nArray6 = new int[6];
        nArray6[1] = 3;
        nArray6[2] = 18;
        nArray6[3] = 70;
        nArray6[4] = 50;
        nArray6[5] = 1;
        nArrayArray[6] = nArray6;
        int[] nArray7 = new int[6];
        nArray7[2] = -1;
        nArrayArray[7] = nArray7;
        nArrayArray[8] = new int[]{2, 2, 22, 100, 80, 1};
        int[] nArray8 = new int[6];
        nArray8[2] = -1;
        nArrayArray[9] = nArray8;
        nArrayArray[10] = new int[]{2, 8, 27, 150, 120, 1};
        int[] nArray9 = new int[6];
        nArray9[1] = 8;
        nArray9[2] = -1;
        nArrayArray[11] = nArray9;
        nArrayArray[12] = new int[]{2, 3, 20, 150, 120, 1};
        int[] nArray10 = new int[6];
        nArray10[1] = 3;
        nArray10[2] = -1;
        nArrayArray[13] = nArray10;
        int[] nArray11 = new int[6];
        nArray11[0] = 2;
        nArray11[1] = 27;
        nArray11[2] = 21;
        nArray11[3] = 50;
        nArray11[4] = 50;
        nArrayArray[14] = nArray11;
        nArrayArray[15] = new int[]{2, 5, 20, 200, 150, 1};
        int[] nArray12 = new int[6];
        nArray12[1] = 3;
        nArray12[2] = -1;
        nArrayArray[16] = nArray12;
        int[] nArray13 = new int[6];
        nArray13[0] = 2;
        nArray13[1] = 25;
        nArray13[2] = 25;
        nArray13[3] = 50;
        nArray13[4] = 50;
        nArrayArray[17] = nArray13;
        nArrayArray[18] = new int[]{2, 4, 24, 300, 170, 1};
        int[] nArray14 = new int[6];
        nArray14[1] = 4;
        nArray14[2] = -1;
        nArrayArray[19] = nArray14;
        nArrayArray[20] = new int[]{2, 4, 29, 400, 200, 1};
        int[] nArray15 = new int[6];
        nArray15[1] = 4;
        nArray15[2] = -1;
        nArrayArray[21] = nArray15;
        int[] nArray16 = new int[6];
        nArray16[0] = 2;
        nArray16[1] = 7;
        nArray16[2] = 29;
        nArray16[3] = 500;
        nArray16[4] = 250;
        nArrayArray[22] = nArray16;
        nArrayArray[23] = new int[]{2, 26, 28, 700, 300, 1};
        int[] nArray17 = new int[6];
        nArray17[1] = 7;
        nArray17[2] = -1;
        nArrayArray[24] = nArray17;
        nArrayArray[25] = new int[]{2, 18, 26, 900, 350, 1};
        int[] nArray18 = new int[6];
        nArray18[1] = 5;
        nArray18[2] = -1;
        nArrayArray[26] = nArray18;
        int[] nArray19 = new int[6];
        nArray19[0] = 2;
        nArray19[1] = 6;
        nArray19[2] = 31;
        nArray19[3] = 1200;
        nArray19[4] = 450;
        nArrayArray[27] = nArray19;
        nArrayArray[28] = new int[]{2, 19, 30, 1500, 500, 1};
        int[] nArray20 = new int[6];
        nArray20[1] = 6;
        nArray20[2] = -1;
        nArrayArray[29] = nArray20;
        nArrayArray[30] = new int[]{2, 10, 32, 1800, 550, 1};
        int[] nArray21 = new int[6];
        nArray21[1] = 10;
        nArray21[2] = -1;
        nArrayArray[31] = nArray21;
        nArrayArray[32] = new int[]{2, 28, 35, 1600, 500, 1};
        nArrayArray[33] = new int[]{2, 9, 34, 2100, 600, 1};
        int[] nArray22 = new int[6];
        nArray22[1] = 9;
        nArray22[2] = -1;
        nArrayArray[34] = nArray22;
        int[] nArray23 = new int[6];
        nArray23[0] = 2;
        nArray23[1] = 39;
        nArray23[2] = 37;
        nArray23[3] = 1800;
        nArray23[4] = 500;
        nArrayArray[35] = nArray23;
        nArrayArray[36] = new int[]{2, 11, 36, 2500, 650, 1};
        int[] nArray24 = new int[6];
        nArray24[1] = 11;
        nArray24[2] = -1;
        nArrayArray[37] = nArray24;
        nArrayArray[38] = new int[]{2, 1, 33, 3000, 750, 1};
        int[] nArray25 = new int[6];
        nArray25[1] = 12;
        nArray25[2] = -1;
        nArrayArray[39] = nArray25;
        int[] nArray26 = new int[6];
        nArray26[0] = 2;
        nArray26[1] = 30;
        nArray26[2] = 41;
        nArray26[3] = 2500;
        nArray26[4] = 500;
        nArrayArray[40] = nArray26;
        nArrayArray[41] = new int[]{2, 13, 38, 4000, 850, 1};
        nArrayArray[42] = new int[]{2, 13, 40, 5000, 950, 1};
        int[] nArray27 = new int[6];
        nArray27[1] = 13;
        nArray27[2] = -1;
        nArrayArray[43] = nArray27;
        int[] nArray28 = new int[6];
        nArray28[0] = 2;
        nArray28[2] = 74;
        nArray28[5] = 2;
        nArrayArray[44] = nArray28;
        int[] nArray29 = new int[6];
        nArray29[2] = -1;
        nArrayArray[45] = nArray29;
        l = nArrayArray;
        int[][] nArrayArray2 = new int[46][];
        nArrayArray2[1] = new int[]{1, 32, 33};
        nArrayArray2[3] = new int[]{24};
        nArrayArray2[5] = new int[]{3, 34};
        nArrayArray2[7] = new int[]{22, 23, 31, 2};
        nArrayArray2[9] = new int[]{8, 18, 36, 8, 16, 15};
        nArrayArray2[11] = new int[]{5, 27};
        nArrayArray2[13] = new int[]{17};
        nArrayArray2[16] = new int[]{25, 4, 14};
        nArrayArray2[19] = new int[]{7, 35};
        nArrayArray2[22] = new int[]{26};
        nArrayArray2[26] = new int[]{37, 6, 17};
        nArrayArray2[27] = new int[]{19};
        nArrayArray2[29] = new int[]{38, 10};
        nArrayArray2[31] = new int[]{9, 28, 20, 21};
        nArrayArray2[34] = new int[]{11, 39};
        nArrayArray2[37] = new int[]{12, 29};
        nArrayArray2[39] = new int[]{30, 13};
        nArrayArray2[43] = new int[]{40};
        m = nArrayArray2;
        int[][] nArrayArray3 = new int[8][];
        int[] nArray30 = new int[5];
        nArray30[0] = 2;
        nArray30[1] = 23;
        nArray30[2] = 47;
        nArray30[4] = 10;
        nArrayArray3[0] = nArray30;
        int[] nArray31 = new int[5];
        nArray31[0] = 2;
        nArray31[1] = 16;
        nArray31[2] = 9;
        nArray31[3] = 3;
        nArrayArray3[1] = nArray31;
        int[] nArray32 = new int[5];
        nArray32[0] = 2;
        nArray32[1] = 15;
        nArray32[2] = 12;
        nArray32[4] = 20;
        nArrayArray3[2] = nArray32;
        nArrayArray3[3] = new int[]{2, 14, 14, 2, 20};
        nArrayArray3[4] = new int[]{2, 22, 13, 1, 10};
        int[] nArray33 = new int[5];
        nArray33[1] = 17;
        nArray33[2] = -1;
        nArray33[3] = 3;
        nArrayArray3[5] = nArray33;
        int[] nArray34 = new int[5];
        nArray34[1] = 20;
        nArray34[2] = -1;
        nArrayArray3[6] = nArray34;
        nArrayArray3[7] = new int[]{2, 21, 73, 3, 50};
        n = nArrayArray3;
        int[][] nArrayArray4 = new int[77][];
        int[] nArray35 = new int[6];
        nArray35[0] = 1;
        nArray35[4] = 1;
        nArray35[5] = 2;
        nArrayArray4[0] = nArray35;
        int[] nArray36 = new int[6];
        nArray36[0] = 1;
        nArray36[3] = 3;
        nArray36[4] = 4;
        nArray36[5] = 5;
        nArrayArray4[1] = nArray36;
        int[] nArray37 = new int[6];
        nArray37[0] = 1;
        nArray37[3] = 6;
        nArray37[4] = 7;
        nArray37[5] = 8;
        nArrayArray4[2] = nArray37;
        int[] nArray38 = new int[6];
        nArray38[0] = 1;
        nArray38[4] = 1;
        nArray38[5] = 2;
        nArrayArray4[3] = nArray38;
        int[] nArray39 = new int[6];
        nArray39[0] = 1;
        nArray39[3] = 3;
        nArray39[4] = 4;
        nArray39[5] = 5;
        nArrayArray4[4] = nArray39;
        int[] nArray40 = new int[6];
        nArray40[0] = 1;
        nArray40[3] = 6;
        nArray40[4] = 7;
        nArray40[5] = 8;
        nArrayArray4[5] = nArray40;
        nArrayArray4[6] = new int[]{1, 60, 5, 6, 7, 8};
        int[] nArray41 = new int[6];
        nArray41[0] = 1;
        nArray41[1] = 30;
        nArray41[2] = 2;
        nArray41[4] = 1;
        nArray41[5] = 2;
        nArrayArray4[7] = nArray41;
        int[] nArray42 = new int[6];
        nArray42[0] = 1;
        nArray42[3] = 3;
        nArray42[4] = 4;
        nArray42[5] = 5;
        nArrayArray4[8] = nArray42;
        nArrayArray4[9] = new int[]{20, 60, 4, 3, 4, 5};
        int[] nArray43 = new int[6];
        nArray43[0] = 1;
        nArray43[4] = 1;
        nArray43[5] = 2;
        nArrayArray4[10] = nArray43;
        int[] nArray44 = new int[6];
        nArray44[0] = 1;
        nArray44[4] = 1;
        nArray44[5] = 2;
        nArrayArray4[11] = nArray44;
        nArrayArray4[12] = new int[]{25, 75, 5, 6, 7};
        int[] nArray45 = new int[5];
        nArray45[0] = 15;
        nArray45[1] = 45;
        nArray45[2] = 3;
        nArray45[4] = 3;
        nArrayArray4[13] = nArray45;
        nArrayArray4[14] = new int[]{30, 90, 6, 3, 4};
        int[] nArray46 = new int[6];
        nArray46[0] = 1;
        nArray46[4] = 1;
        nArray46[5] = 2;
        nArrayArray4[15] = nArray46;
        int[] nArray47 = new int[6];
        nArray47[0] = 1;
        nArray47[4] = 1;
        nArray47[5] = 2;
        nArrayArray4[16] = nArray47;
        int[] nArray48 = new int[6];
        nArray48[0] = 1;
        nArray48[3] = 3;
        nArray48[4] = 4;
        nArray48[5] = 5;
        nArrayArray4[17] = nArray48;
        int[] nArray49 = new int[6];
        nArray49[0] = 1;
        nArray49[4] = 1;
        nArray49[5] = 2;
        nArrayArray4[18] = nArray49;
        int[] nArray50 = new int[6];
        nArray50[0] = 1;
        nArray50[4] = 1;
        nArray50[5] = 2;
        nArrayArray4[19] = nArray50;
        nArrayArray4[20] = new int[]{40, 40, 4, 3, 4, 5};
        int[] nArray51 = new int[5];
        nArray51[0] = 25;
        nArray51[3] = 3;
        nArray51[4] = 4;
        nArrayArray4[21] = nArray51;
        nArrayArray4[22] = new int[]{20, 20, 2, 3, 4};
        int[] nArray52 = new int[5];
        nArray52[0] = 10;
        nArray52[3] = 6;
        nArray52[4] = 7;
        nArrayArray4[23] = nArray52;
        nArrayArray4[24] = new int[]{50, 50, 5, 7, 7, 8};
        int[] nArray53 = new int[5];
        nArray53[0] = 30;
        nArray53[3] = 6;
        nArray53[4] = 7;
        nArrayArray4[25] = nArray53;
        int[] nArray54 = new int[6];
        nArray54[0] = 40;
        nArray54[1] = 40;
        nArray54[2] = 4;
        nArray54[4] = 1;
        nArray54[5] = 2;
        nArrayArray4[26] = nArray54;
        int[] nArray55 = new int[5];
        nArray55[0] = 30;
        nArray55[1] = 30;
        nArray55[2] = 3;
        nArray55[4] = 1;
        nArrayArray4[27] = nArray55;
        nArrayArray4[28] = new int[]{60, 60, 6, 1, 1, 2};
        nArrayArray4[29] = new int[]{45, 50, 5, 3, 4, 5};
        nArrayArray4[30] = new int[]{70, 70, 7, 7, 7, 8};
        int[] nArray56 = new int[6];
        nArray56[0] = 45;
        nArray56[3] = 3;
        nArray56[4] = 4;
        nArray56[5] = 5;
        nArrayArray4[31] = nArray56;
        nArrayArray4[32] = new int[]{80, 80, 8, 4, 4, 5};
        nArrayArray4[33] = new int[]{60, 60, 6, 4, 4, 5};
        nArrayArray4[34] = new int[]{90, 90, 9, 2, 2, 2};
        int[] nArray57 = new int[6];
        nArray57[0] = 55;
        nArray57[3] = 6;
        nArray57[4] = 7;
        nArray57[5] = 8;
        nArrayArray4[35] = nArray57;
        nArrayArray4[36] = new int[]{70, 70, 7, 1, 1, 2};
        int[] nArray58 = new int[6];
        nArray58[0] = 60;
        nArray58[1] = 60;
        nArray58[2] = 6;
        nArray58[4] = 1;
        nArray58[5] = 2;
        nArrayArray4[37] = nArray58;
        nArrayArray4[38] = new int[]{95, 300, 15, 5, 5, 5};
        int[] nArray59 = new int[6];
        nArray59[0] = 65;
        nArray59[3] = 7;
        nArray59[4] = 7;
        nArray59[5] = 8;
        nArrayArray4[39] = nArray59;
        nArrayArray4[40] = new int[]{99, 500, 20, 8, 8, 8};
        int[] nArray60 = new int[6];
        nArray60[0] = 75;
        nArray60[3] = 7;
        nArray60[4] = 7;
        nArray60[5] = 8;
        nArrayArray4[41] = nArray60;
        int[] nArray61 = new int[4];
        nArray61[0] = 7;
        nArrayArray4[42] = nArray61;
        int[] nArray62 = new int[4];
        nArray62[0] = 3;
        nArrayArray4[43] = nArray62;
        int[] nArray63 = new int[4];
        nArray63[0] = 6;
        nArray63[3] = 3;
        nArrayArray4[44] = nArray63;
        int[] nArray64 = new int[4];
        nArray64[0] = 9;
        nArray64[3] = 6;
        nArrayArray4[45] = nArray64;
        int[] nArray65 = new int[5];
        nArray65[0] = 12;
        nArray65[4] = 1;
        nArrayArray4[46] = nArray65;
        int[] nArray66 = new int[5];
        nArray66[0] = 15;
        nArray66[3] = 3;
        nArray66[4] = 4;
        nArrayArray4[47] = nArray66;
        int[] nArray67 = new int[5];
        nArray67[0] = 18;
        nArray67[3] = 5;
        nArray67[4] = 6;
        nArrayArray4[48] = nArray67;
        int[] nArray68 = new int[5];
        nArray68[0] = 21;
        nArray68[4] = 1;
        nArrayArray4[49] = nArray68;
        int[] nArray69 = new int[5];
        nArray69[0] = 24;
        nArray69[3] = 3;
        nArray69[4] = 4;
        nArrayArray4[50] = nArray69;
        int[] nArray70 = new int[5];
        nArray70[0] = 27;
        nArray70[3] = 5;
        nArray70[4] = 6;
        nArrayArray4[51] = nArray70;
        int[] nArray71 = new int[6];
        nArray71[0] = 30;
        nArray71[4] = 1;
        nArray71[5] = 2;
        nArrayArray4[52] = nArray71;
        int[] nArray72 = new int[6];
        nArray72[0] = 33;
        nArray72[3] = 3;
        nArray72[4] = 4;
        nArray72[5] = 5;
        nArrayArray4[53] = nArray72;
        int[] nArray73 = new int[6];
        nArray73[0] = 36;
        nArray73[3] = 6;
        nArray73[4] = 7;
        nArray73[5] = 8;
        nArrayArray4[54] = nArray73;
        int[] nArray74 = new int[6];
        nArray74[0] = 39;
        nArray74[4] = 1;
        nArray74[5] = 2;
        nArrayArray4[55] = nArray74;
        int[] nArray75 = new int[6];
        nArray75[0] = 42;
        nArray75[3] = 3;
        nArray75[4] = 4;
        nArray75[5] = 5;
        nArrayArray4[56] = nArray75;
        int[] nArray76 = new int[6];
        nArray76[0] = 45;
        nArray76[3] = 6;
        nArray76[4] = 7;
        nArray76[5] = 8;
        nArrayArray4[57] = nArray76;
        int[] nArray77 = new int[6];
        nArray77[0] = 48;
        nArray77[3] = 1;
        nArray77[4] = 1;
        nArray77[5] = 2;
        nArrayArray4[58] = nArray77;
        int[] nArray78 = new int[6];
        nArray78[0] = 51;
        nArray78[3] = 4;
        nArray78[4] = 4;
        nArray78[5] = 5;
        nArrayArray4[59] = nArray78;
        int[] nArray79 = new int[6];
        nArray79[0] = 54;
        nArray79[3] = 7;
        nArray79[4] = 7;
        nArray79[5] = 8;
        nArrayArray4[60] = nArray79;
        int[] nArray80 = new int[6];
        nArray80[0] = 57;
        nArray80[3] = 1;
        nArray80[4] = 1;
        nArray80[5] = 2;
        nArrayArray4[61] = nArray80;
        int[] nArray81 = new int[6];
        nArray81[0] = 60;
        nArray81[3] = 4;
        nArray81[4] = 4;
        nArray81[5] = 5;
        nArrayArray4[62] = nArray81;
        int[] nArray82 = new int[6];
        nArray82[0] = 63;
        nArray82[3] = 7;
        nArray82[4] = 7;
        nArray82[5] = 8;
        nArrayArray4[63] = nArray82;
        int[] nArray83 = new int[5];
        nArray83[0] = 66;
        nArray83[3] = 6;
        nArray83[4] = 7;
        nArrayArray4[64] = nArray83;
        int[] nArray84 = new int[5];
        nArray84[0] = 69;
        nArray84[3] = 7;
        nArray84[4] = 8;
        nArrayArray4[65] = nArray84;
        int[] nArray85 = new int[5];
        nArray85[0] = 72;
        nArray85[3] = 8;
        nArray85[4] = 8;
        nArrayArray4[66] = nArray85;
        int[] nArray86 = new int[5];
        nArray86[0] = 75;
        nArray86[3] = 1;
        nArray86[4] = 2;
        nArrayArray4[67] = nArray86;
        int[] nArray87 = new int[5];
        nArray87[0] = 78;
        nArray87[3] = 1;
        nArray87[4] = 2;
        nArrayArray4[68] = nArray87;
        int[] nArray88 = new int[5];
        nArray88[0] = 81;
        nArray88[3] = 1;
        nArray88[4] = 2;
        nArrayArray4[69] = nArray88;
        int[] nArray89 = new int[5];
        nArray89[0] = 84;
        nArray89[3] = 4;
        nArray89[4] = 5;
        nArrayArray4[70] = nArray89;
        int[] nArray90 = new int[5];
        nArray90[0] = 87;
        nArray90[3] = 4;
        nArray90[4] = 5;
        nArrayArray4[71] = nArray90;
        int[] nArray91 = new int[5];
        nArray91[0] = 90;
        nArray91[3] = 4;
        nArray91[4] = 5;
        nArrayArray4[72] = nArray91;
        int[] nArray92 = new int[5];
        nArray92[0] = 93;
        nArray92[3] = 6;
        nArray92[4] = 7;
        nArrayArray4[73] = nArray92;
        nArrayArray4[74] = new int[]{96, 1000, 40, 6, 7, 8};
        int[] nArray93 = new int[3];
        nArray93[0] = 1;
        nArrayArray4[75] = nArray93;
        int[] nArray94 = new int[6];
        nArray94[0] = 40;
        nArray94[4] = 1;
        nArray94[5] = 2;
        nArrayArray4[76] = nArray94;
        o = nArrayArray4;
        p = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 25, 75, 21, 35, -1, 41, 23, 75, 75, 75, 39, 75, 31, 76, 37, -1};
        int[][] nArrayArray5 = new int[5][];
        nArrayArray5[0] = new int[]{8, -5, 5, 50};
        int[] nArray95 = new int[4];
        nArray95[0] = 7;
        nArray95[2] = 10;
        nArray95[3] = 80;
        nArrayArray5[1] = nArray95;
        nArrayArray5[2] = new int[]{9, 5, 20, 140};
        nArrayArray5[3] = new int[]{14, 10, 40, 230};
        nArrayArray5[4] = new int[]{6, 20, 100, 500};
        q = nArrayArray5;
    }

    public static final void a(int n2) {
        com.mic.smsgame.suquan.f.a(101, n2);
    }

    public static final int a() {
        return com.mic.smsgame.suquan.f.b(101);
    }

    public static final void b(int n2) {
        com.mic.smsgame.suquan.f.a(103, n2);
    }

    public static final int b() {
        return com.mic.smsgame.suquan.f.b(103);
    }

    public static final String c() {
        String string = com.mic.smsgame.suquan.f.a(104);
        if (ao.a(string)) {
            string = "PLAYER";
        }
        return string;
    }

    public static final void a(String string) {
        if (string == null) {
            string = "PLAYER";
        }
        com.mic.smsgame.suquan.f.a(104, string);
    }

    public static final int d() {
        return com.mic.smsgame.suquan.f.b(105);
    }

    public static final void c(int n2) {
        com.mic.smsgame.suquan.f.a(105, n2);
    }

    public static final int e() {
        return l.length;
    }

    public static final int d(int n2) {
        try {
            return l[n2][0];
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            return 0;
        }
    }

    public static final int e(int n2) {
        try {
            return l[n2][1];
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            return 0;
        }
    }

    public static final boolean f(int n2) {
        try {
            return l[n2][5] == 1;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            return false;
        }
    }

    public static final boolean g(int n2) {
        try {
            return l[n2][5] == 2;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            return false;
        }
    }

    public static final c h(int n2) {
        block3: {
            try {
                if (l[n2][2] >= 0) break block3;
                return null;
            }
            catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                return null;
            }
        }
        return dd.t(l[n2][2]);
    }

    public static final int i(int n2) {
        try {
            return l[n2][3];
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            return 0;
        }
    }

    public static final int j(int n2) {
        try {
            return l[n2][4];
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            return 0;
        }
    }

    public static final boolean k(int n2) {
        try {
            return l[n2][3] != 0 || l[n2][4] != 0;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            return false;
        }
    }

    public static final int[] l(int n2) {
        return m[n2];
    }

    public static final int f() {
        return n.length;
    }

    public static final boolean m(int n2) {
        block3: {
            try {
                if (n2 >= 0 && n2 < n.length) break block3;
                return false;
            }
            catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                return false;
            }
        }
        return n[n2][3] != 0 || n[n2][4] != 0;
    }

    public static final int n(int n2) {
        try {
            return n[n2][3];
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            return 0;
        }
    }

    public static final int o(int n2) {
        try {
            return n[n2][4];
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            return 0;
        }
    }

    public static final int p(int n2) {
        try {
            return n[n2][0];
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            return 0;
        }
    }

    public static final int q(int n2) {
        try {
            return n[n2][1];
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            return 0;
        }
    }

    public static final int r(int n2) {
        return n[n2][2];
    }

    public static final c s(int n2) {
        block3: {
            try {
                if (n[n2][2] >= 0) break block3;
                return null;
            }
            catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                return null;
            }
        }
        return dd.t(n[n2][2]);
    }

    public static final c a(int n2, int n3) {
        if (n2 < 0 || n2 >= p.length) {
            return null;
        }
        if (p[n2] < 0) {
            return null;
        }
        c c2 = null;
        c2 = p[n2] == 75 ? dd.y(n3) : dd.t(p[n2]);
        return c2;
    }

    public static final c t(int n2) {
        return dd.b(n2, -1);
    }

    public static final c b(int n2, int n3) {
        if (n2 < 0 || n2 >= o.length) {
            return null;
        }
        c c2 = new c();
        c2.a = n2;
        c2.e = n3 < 0 ? o[n2][0] : n3;
        c2.c = c2.b = dd.w(c2.e) + o[n2][1];
        c2.d = dd.v(c2.e) + o[n2][2];
        int n4 = o[n2].length - 3;
        c2.f = new int[n4];
        int n5 = 0;
        while (n5 < n4) {
            c2.f[n5] = o[n2][n5 + 3];
            ++n5;
        }
        return c2;
    }

    public static final boolean c(int n2, int n3) {
        int n4 = dd.u(n2);
        return n3 >= n4;
    }

    public static final int u(int n2) {
        if (n2 <= 1) {
            return 0;
        }
        return 16 * (n2 * n2) - 25;
    }

    public static final int v(int n2) {
        return n2 / 2 + 5;
    }

    public static final int w(int n2) {
        return 100 + n2 * 5;
    }

    public static final int d(int n2, int n3) {
        if (n3 < 42) {
            return n2 * n2 * 2 / 10 + 10;
        }
        return n2 * n2 * 1 / 10 + 10;
    }

    public static final int a(c c2, c c3) {
        if (c3 == null) {
            return 0;
        }
        int n2 = dd.d(c3.e, c3.a);
        int n3 = c3.e - c2.e;
        if (n3 < -5) {
            return 0;
        }
        if (n3 > 5) {
            return n2 / 2;
        }
        if (n3 <= 0) {
            return n2;
        }
        int n4 = n2;
        int n5 = c2.c * 100 / c2.b;
        if (n5 > 70) {
            n4 += n5 * n2 / 100;
        }
        return n4 += n3 * 5 * n2 / 100;
    }

    public static final int a(c c2, c c3, int n2) {
        int n3 = dd.u(c2.e);
        int n4 = 0;
        int n5 = c3.e - c2.e;
        n4 = n5 >= 0 ? n2 * 3 / 100 : (n5 < -5 ? n2 * 5 / 100 : (-n5 * 4 + 30) * n2 / 1000);
        if (n2 - n4 <= n3) {
            n4 = n2 - n3;
        }
        return n4;
    }

    public static final c x(int n2) {
        int n3 = ay.a(43, 73);
        int n4 = ay.a(bq.d[n2][2], bq.d[n2][3]);
        if (n4 < 1) {
            n4 = 1;
        }
        return dd.b(n3, n4);
    }

    public static final c y(int n2) {
        int n3 = n2 - ay.a(6);
        if (n3 < 1) {
            n3 = 1;
        }
        return dd.b(75, n3);
    }

    public static final int z(int n2) {
        return p[n2];
    }

    public static final ae a(boolean bl2) {
        return new cr(bl2);
    }

    public static final ae g() {
        int n2 = dd.b();
        int n3 = dd.a(e, f, n2);
        String string = "B\u1ea1n b\u1ecb m\u1ea5t 1 m\u1ea1ng ch\u01a1i.";
        if (a < 0) {
            if (n3 > 0) {
                string = String.valueOf(string) + " V\u00e0 b\u1ecb ph\u1ea1t {e} kinh nghi\u1ec7m.";
            }
            if (bg.l()) {
                string = String.valueOf(string) + " B\u1ea1n c\u00f3 mu\u1ed1n \u0111\u00e1nh l\u1ea1i kh\u00f4ng?";
            }
        }
        string = ao.a(string, "{e}", "" + n3);
        return cz.a("Ch\u00fa \u00fd", string, null, null, 1);
    }

    public static final void h() {
        dd.a(1);
        k = true;
        bg.n();
    }

    public static final void i() {
        dd.a(2);
        int n2 = dd.b();
        if (a >= 0) {
            dd.e.c = b;
            n2 += j;
        } else {
            dd.e.c = dd.e.c > dd.e.b ? dd.e.b : dd.e.c;
            n2 += dd.a(e, f) + j;
            bg.a(2, i, 6);
        }
        dd.b(n2);
        while (dd.c(dd.e.e + 1, n2)) {
            e.a();
        }
        bg.c(1);
    }

    public static final void j() {
        dd.a(3);
        if (a < 0) {
            int n2 = dd.b();
            dd.b(n2 - dd.a(e, f, n2));
            dd.e.c = dd.e.b;
        } else {
            dd.e.c = b;
        }
        j = 0;
        i = 0;
    }

    public static final int[] k() {
        int[] nArray = new int[q.length];
        int n2 = 0;
        while (n2 < nArray.length) {
            nArray[n2] = q[n2][0];
            ++n2;
        }
        return nArray;
    }

    public static final c e(int n2, int n3) {
        if ((n3 += q[n2][1]) < 1) {
            n3 = 1;
        }
        return dd.b(q[n2][0], n3);
    }

    public static final int A(int n2) {
        return q[n2][3];
    }

    public static final int f(int n2, int n3) {
        int n4 = (dd.u(n3 + 1) - dd.u(n3)) * q[n2][2] / 100;
        return n4;
    }

    public static final int B(int n2) {
        return q[n2][2];
    }
}

