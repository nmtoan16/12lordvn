/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package com.mic.smsgame.suquan;

import com.mg.smsgame.main.b;
import com.mic.smsgame.suquan.f;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class aa
extends aj
implements b {
    private int f = -1;
    private Image t;
    private Image u;
    private Image v;
    private Image w;
    private int x;
    private int y;
    private bo[] z;
    private bl[] A;
    private int B = 0;
    private int C;
    private int D;
    private int E;
    private int F;
    private int G;
    private int H;
    private int I;
    private int J;
    private int K;
    private int L;
    private int M;
    private bp N;
    private ca O;
    private co P;
    private dq Q;
    private int R;
    private int[] S;
    private int T;
    private dl U;
    private int V;
    private int W;
    private int X;
    private int Y;
    private s Z;
    private boolean aa;
    private int ab;
    private int ac;
    private int ad;
    private int ae;
    private dq af;
    private int ag;
    private boolean ah = true;
    private boolean ai;
    private dk aj;
    private boolean ak;
    private boolean al;
    private boolean am;
    private int an;
    public static boolean c;
    private Image ao;
    private int ap;
    private boolean[] aq;
    private bk ar;
    public static boolean d;
    public static boolean e;

    static {
        d = false;
        e = false;
    }

    public aa() {
        System.gc();
        c = false;
        this.c(false);
        this.a((h)null);
        this.a(this);
        this.t = az.b("/m/hm");
        this.u = az.b("/m/m");
        this.v = az.b("/m/b");
        this.w = az.b("/m/r");
        this.x = this.w.getWidth() / 4;
        this.y = this.w.getHeight();
        this.b_ = this.u.getWidth();
        this.c_ = this.u.getHeight();
        this.U = new dl(0, 0, cx.f, cx.g);
        this.Q = new dq(az.b("/m/mt1"), 3);
        this.Q.c(3);
        this.Q.b(true);
        byte[][] byArrayArray = new byte[1][];
        byte[] byArray = new byte[4];
        byArray[1] = 1;
        byArray[2] = 2;
        byArray[3] = 1;
        byArrayArray[0] = byArray;
        this.Q.a(byArrayArray);
        this.Q.a_(0);
        this.af = new dq(az.b("/m/ar"), 6);
        this.af.c(1);
        this.af.b(true);
        byte[][] byArrayArray2 = new byte[1][];
        byte[] byArray2 = new byte[12];
        byArray2[1] = 1;
        byArray2[2] = 2;
        byArray2[3] = 3;
        byArray2[4] = 4;
        byArray2[5] = 5;
        byArray2[6] = 5;
        byArray2[7] = 4;
        byArray2[8] = 3;
        byArray2[9] = 2;
        byArray2[10] = 1;
        byArrayArray2[0] = byArray2;
        this.af.a(byArrayArray2);
        this.af.a_(0);
        u.a(null);
        this.ak = false;
        this.al = false;
        this.am = false;
        this.ar = new bk("/m/vk", cx.f - 55, 0);
        this.ap = bg.m();
        this.ao = az.b("/m/l");
        this.ah = false;
        this.ag = 0;
        this.u();
        this.v();
        this.D();
        this.f(7);
        this.aj = new dk("vs");
        cf.a().a("m", -1);
        cf.a().a(180184);
        cf.a().e();
        this.q = 7;
    }

    private void u() {
        this.z = new bo[bq.a.length];
        int n2 = 0;
        while (n2 < this.z.length) {
            this.z[n2] = new bo();
            this.z[n2].b = bq.b[n2][0];
            this.z[n2].c = bq.b[n2][1];
            this.z[n2].a = bq.b[n2][2];
            this.z[n2].d = false;
            ++n2;
        }
        this.z[0].d = true;
        this.z[0].e = 0;
        n2 = 0;
        int n3 = dd.f();
        while (n2 < n3) {
            this.z[dd.q((int)n2)].f = n2;
            ++n2;
        }
        this.A = new bl[bq.d.length];
        n2 = 0;
        while (n2 < this.A.length) {
            this.A[n2] = new bl();
            this.A[n2].a = bq.d[n2][0];
            this.A[n2].b = bq.d[n2][1];
            this.A[n2].c = false;
            ++n2;
        }
        this.S = new int[bq.a()];
        n2 = 0;
        while (n2 < this.S.length) {
            this.S[n2] = dd.z(n2);
            ++n2;
        }
        this.B = 0;
        this.D = -1;
        this.C = -1;
        this.R = dd.a();
        this.T = 0;
        this.ae = ay.a(3, 10);
        this.W = dd.b();
        this.X = bg.f(2);
        this.ai = false;
        this.aq = new boolean[dd.k().length];
        n2 = 0;
        while (n2 < this.aq.length) {
            this.aq[n2] = false;
            ++n2;
        }
        this.aq[1] = true;
        this.aq[0] = true;
        this.ab = -1;
        this.ac = -1;
    }

    private void a(int[] nArray) {
        if (nArray == null) {
            return;
        }
        int n2 = 0;
        while (n2 < nArray.length) {
            this.z[nArray[n2]].d = true;
            ++n2;
        }
        n2 = 0;
        while (n2 < this.A.length) {
            if (!this.A[n2].c && this.z[this.A[n2].a].d && this.z[this.A[n2].b].d) {
                this.A[n2].c = true;
            }
            ++n2;
        }
    }

    private void v() {
        if (!com.mic.smsgame.suquan.f.c()) {
            dd.e = dd.t(dd.d());
            return;
        }
        try {
            byte[] byArray = com.mic.smsgame.suquan.f.b();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byArray);
            DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
            this.B = dataInputStream.readInt();
            this.E = dataInputStream.readByte();
            this.D = dataInputStream.readInt();
            this.T = dataInputStream.readInt();
            int n2 = 0;
            while (n2 < this.z.length) {
                this.z[n2].d = dataInputStream.readBoolean();
                this.z[n2].e = dataInputStream.readByte();
                this.z[n2].f = dataInputStream.readByte();
                this.S[n2] = dataInputStream.readByte();
                ++n2;
            }
            n2 = 0;
            while (n2 < this.A.length) {
                this.A[n2].c = dataInputStream.readBoolean();
                ++n2;
            }
            this.ae = dataInputStream.readByte();
            this.L = dataInputStream.readByte();
            this.M = dataInputStream.readByte();
            this.J = dataInputStream.readInt();
            this.K = dataInputStream.readInt();
            this.F = dataInputStream.readInt();
            this.G = dataInputStream.readInt();
            c c2 = new c();
            c2.e = dataInputStream.readByte();
            c2.c = dataInputStream.readInt();
            c2.g = dataInputStream.readInt();
            c2.h = dataInputStream.readInt();
            dd.b = dataInputStream.readInt();
            this.ai = dataInputStream.readBoolean();
            int n3 = 0;
            while (n3 < this.aq.length) {
                this.aq[n3] = dataInputStream.readBoolean();
                ++n3;
            }
            if (dd.e == null) {
                dd.e = dd.t(dd.d());
                dd.e.e = c2.e;
                dd.e.c = c2.c;
                dd.e.h = c2.h;
                dd.e.g = c2.g;
                dd.e.b();
            }
            dataInputStream.close();
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    private void w() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream.writeInt(this.B);
            dataOutputStream.writeByte(this.E);
            dataOutputStream.writeInt(this.D);
            dataOutputStream.writeInt(this.T);
            int n2 = 0;
            while (n2 < this.z.length) {
                dataOutputStream.writeBoolean(this.z[n2].d);
                dataOutputStream.writeByte(this.z[n2].e);
                dataOutputStream.writeByte(this.z[n2].f);
                dataOutputStream.writeByte(this.S[n2]);
                ++n2;
            }
            n2 = 0;
            while (n2 < this.A.length) {
                dataOutputStream.writeBoolean(this.A[n2].c);
                ++n2;
            }
            dataOutputStream.writeByte(this.ae);
            dataOutputStream.writeByte(this.L);
            dataOutputStream.writeByte(this.M);
            dataOutputStream.writeInt(this.J);
            dataOutputStream.writeInt(this.K);
            dataOutputStream.writeInt(this.F);
            dataOutputStream.writeInt(this.G);
            dataOutputStream.writeByte(dd.e.e);
            dataOutputStream.writeInt(dd.e.c);
            dataOutputStream.writeInt(dd.e.g);
            dataOutputStream.writeInt(dd.e.h);
            dataOutputStream.writeInt(dd.b);
            dataOutputStream.writeBoolean(this.ai);
            n2 = 0;
            while (n2 < this.aq.length) {
                dataOutputStream.writeBoolean(this.aq[n2]);
                ++n2;
            }
            dataOutputStream.flush();
            com.mic.smsgame.suquan.f.a(byteArrayOutputStream.toByteArray());
            dataOutputStream.close();
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    private void x() {
        if (this.L == 0) {
            if (this.D < 0) {
                return;
            }
            switch (this.D) {
                case 0: {
                    this.C = 1;
                    break;
                }
                case 1: {
                    this.C = 0;
                    break;
                }
                case 2: {
                    this.C = 3;
                    break;
                }
                case 3: {
                    this.C = 2;
                }
            }
            if (this.C >= 0) {
                this.Y = bq.c[this.B][this.C];
                if (this.Y >= 0 && this.A[this.Y].c) {
                    ++this.ae;
                    this.f(1);
                }
            } else {
                this.f(0);
            }
        } else {
            this.J = -this.J;
            this.K = -this.K;
            this.E = this.B;
            ++this.ae;
            this.f(1);
        }
    }

    private void a(Graphics graphics, bo bo2, bo bo3) {
        if (this.U.a(bo2.b + this.g, bo2.c + this.a_) || this.U.a(bo3.b + this.g, bo3.c + this.a_)) {
            int n2 = dg.a(bo2.b);
            int n3 = dg.a(bo2.c);
            int n4 = dg.a(bo3.b) - n2;
            int n5 = dg.a(bo3.c) - n3;
            int n6 = dg.c(dg.a(n4, n4) + dg.a(n5, n5));
            int n7 = dg.b(dg.a(dg.a(10), n4), n6);
            int n8 = dg.b(dg.a(dg.a(10), n5), n6);
            int n9 = dg.b(dg.b(n6, dg.a(10)));
            int n10 = 0;
            while (n10 < n9) {
                y.a(graphics, this.w, 0, 0, this.x, this.y, dg.b(n2 += n7) + this.g, dg.b(n3 += n8) + this.a_, 3);
                ++n10;
            }
        }
    }

    private void b(Graphics graphics) {
        int n2 = 0;
        while (n2 < this.A.length) {
            if (this.A[n2].c) {
                this.a(graphics, this.z[this.A[n2].a], this.z[this.A[n2].b]);
            }
            ++n2;
        }
        n2 = 0;
        while (n2 < this.z.length) {
            if (this.z[n2].d) {
                if (this.z[n2].a == 1) {
                    if (this.S[n2] < 0) {
                        y.a(graphics, this.w, 9, 0, this.x, this.y, this.g + this.z[n2].b, this.a_ + this.z[n2].c, 3);
                    } else {
                        y.a(graphics, this.w, 18, 0, this.x, this.y, this.g + this.z[n2].b, this.a_ + this.z[n2].c, 3);
                    }
                } else if (this.z[n2].a == 0) {
                    y.a(graphics, this.w, 27, 0, this.x, this.y, this.g + this.z[n2].b, this.a_ + this.z[n2].c, 3);
                }
            }
            ++n2;
        }
    }

    private boolean h(int n2, int n3) {
        int n4;
        int n5;
        int n6 = (cx.f >> 1) - this.g;
        int n7 = (cx.g >> 1) - this.a_;
        int n8 = cx.f >> 1;
        int n9 = cx.g >> 1;
        if (n2 > this.b_ - n8) {
            n2 = this.b_ - n8;
        }
        if (n2 < n8) {
            n2 = n8;
        }
        if (n3 > this.c_ - n9) {
            n3 = this.c_ - n9;
        }
        if (n3 < n9) {
            n3 = n9;
        }
        if (n2 - n6 > 4 || n2 - n6 < 4) {
            n5 = (n2 - n6) / 4;
        } else if (n2 == n6) {
            n5 = 0;
        } else {
            int n10 = n5 = n2 - n6 > 0 ? 1 : -1;
        }
        if (n3 - n7 > 4 || n3 - n7 < 4) {
            n4 = (n3 - n7) / 4;
        } else if (n3 == n7) {
            n4 = 0;
        } else {
            int n11 = n4 = n3 - n7 > 0 ? 1 : -1;
        }
        if (n5 == 0 && n4 == 0) {
            return false;
        }
        this.g -= n5;
        this.a_ -= n4;
        return true;
    }

    public void a(int n2) {
        if (this.f == 1) {
            return;
        }
        switch (this.f) {
            case 0: {
                this.C = -1;
                this.Y = -1;
                switch (n2) {
                    case 14: {
                        this.C = 2;
                        break;
                    }
                    case 15: {
                        this.C = 3;
                        break;
                    }
                    case 12: {
                        this.C = 0;
                        break;
                    }
                    case 13: {
                        this.C = 1;
                    }
                }
                if (this.C < 0) break;
                this.Y = bq.c[this.B][this.C];
                if (this.Y < 0 || !this.A[this.Y].c) break;
                this.f(1);
                break;
            }
            case 9: {
                if (this.P == null) break;
                this.P.a(n2);
            }
        }
    }

    private boolean e(int n2) {
        bo bo2 = this.z[n2];
        if (bo2.e >= 0) {
            return true;
        }
        if (bo2.f >= 0) {
            return true;
        }
        return this.S[n2] >= 0;
    }

    private void y() {
        this.O = null;
        block0 : switch (this.R) {
            case 0: {
                if (this.z[this.B].e >= 0) {
                    int n2 = dd.d(this.z[this.B].e);
                    switch (n2) {
                        case 0: {
                            this.R = 2;
                            this.f(2);
                            dd.a(this.R);
                            break block0;
                        }
                    }
                    dd.f = dd.h(this.z[this.B].e);
                    this.f(3);
                    break;
                }
                if (this.z[this.B].f >= 0) {
                    int n3 = dd.p(this.z[this.B].f);
                    switch (n3) {
                        case 0: {
                            this.R = 2;
                            this.f(2);
                            dd.a(this.R);
                            break block0;
                        }
                    }
                    dd.f = dd.s(this.z[this.B].f);
                    this.f(3);
                    break;
                }
                if (this.S[this.B] < 0) break;
                dd.f = dd.a(this.B, dd.e.e);
                this.f(3);
                break;
            }
            case 2: {
                this.f(5);
                break;
            }
            case 3: {
                this.f(6);
            }
        }
    }

    private void z() {
        ae ae2 = cz.a("Ch\u00fac m\u1eebng", "B\u1ea1n \u0111\u00e3 m\u1edf th\u00eam \u0111\u01b0\u1ee3c 1 m\u1ee9c luy\u1ec7n t\u1eadp t\u1ea1i \u0111\u1ea5u tr\u01b0\u1eddng. H\u00e3y v\u00e0o \u0111\u00f3 th\u1eed s\u1ee9c!", "\u0110\u1ed3ng \u00fd", 5, 1);
        ae2.a(this);
        cz.d().a(ae2, true);
    }

    private void A() {
        this.R = 0;
        dd.a(this.R);
        this.W = dd.b();
        int n2 = 0;
        this.ab = -1;
        this.ac = -1;
        if (this.L == 0) {
            if (this.z[this.B].e >= 0) {
                this.W += dd.i(this.z[this.B].e);
                n2 += dd.j(this.z[this.B].e);
                this.ak = dd.f(this.z[this.B].e);
                this.am = dd.g(this.z[this.B].e);
                if (this.T == 0) {
                    e = true;
                }
                this.C();
            } else if (this.z[this.B].f >= 0) {
                dd.e.h += dd.n(this.z[this.B].f);
                dd.e.g += dd.o(this.z[this.B].f);
                dd.e.c += dd.o(this.z[this.B].f);
                dd.e.b();
                if (dd.r(this.z[this.B].f) == 9) {
                    if (!this.aq[2]) {
                        this.aq[2] = true;
                        this.al = true;
                    }
                } else if (dd.r(this.z[this.B].f) == 14 && !this.aq[3]) {
                    this.aq[3] = true;
                    this.al = true;
                }
                this.z[this.B].f = -1;
            }
            this.S[this.B] = -1;
        }
        dd.b(this.W);
        bg.a(2, n2, 6);
        this.X += n2;
        cz.a().a(true);
        if (dd.c(dd.e.e + 1, this.W)) {
            while (dd.c(dd.e.e + 1, this.W)) {
                dd.e.a();
            }
            this.f(7);
        } else if (this.L > 0) {
            this.f(1);
        } else {
            this.f(7);
        }
        dd.k = false;
        dd.e.b();
        dd.j = 0;
        dd.i = 0;
        if (dd.e.e >= 30 && !this.aq[4]) {
            this.aq[4] = true;
            this.al = true;
        }
        if (this.T >= dd.e()) {
            this.f(10);
        } else if (this.L == 0) {
            this.w();
        }
    }

    private void B() {
        this.R = 0;
        dd.a(this.R);
        this.W = dd.b();
        this.x();
        dd.k = false;
        cz.a().a(true);
        if (this.L == 0) {
            this.w();
        }
    }

    private void C() {
        if (this.T < dd.e()) {
            this.a(dd.l(this.z[this.B].e));
            this.z[this.B].e = -1;
            ++this.T;
            this.z[dd.e((int)this.T)].e = this.T;
            d = true;
            this.D();
        }
    }

    private void D() {
        if (!d) {
            this.ab = -1;
            this.ac = -1;
        } else if (this.T >= 0 && this.T < dd.e()) {
            this.ab = this.z[dd.e((int)this.T)].b;
            this.ac = this.z[dd.e((int)this.T)].c;
            if (this.U.b(this.g + this.ab, this.a_ + this.ac)) {
                this.ab = -1;
                this.ac = -1;
            }
        }
    }

    private void f(int n2) {
        this.E();
        this.f = n2;
        switch (n2) {
            case 7: {
                if (this.ab >= 0 && this.ac >= 0) {
                    this.f(8);
                }
                if (this.L == 0) {
                    this.F = this.z[this.B].b;
                    this.G = this.z[this.B].c;
                }
                this.V = 20;
                break;
            }
            case 0: {
                this.a(this.ar);
                if (this.L > 0) {
                    if (this.R == 1) {
                        this.R = 0;
                        dd.a(this.R);
                        this.L = 0;
                        this.F = this.z[this.B].b;
                        this.G = this.z[this.B].c;
                        this.f(0);
                        break;
                    }
                    if (this.R == 2) {
                        this.f(5);
                        break;
                    }
                    if (this.R == 3) {
                        this.f(6);
                        break;
                    }
                    this.f(1);
                    break;
                }
                if (this.R == 1) {
                    this.R = 0;
                    dd.a(this.R);
                    if (this.D >= 0) {
                        this.x();
                        break;
                    }
                    this.f(0);
                    break;
                }
                this.a(new da("C.H\u00e0ng", 0));
                this.c(new da("T.Tin", 8));
                this.b(new da("Tho\u00e1t", 7));
                if (this.e(this.B)) {
                    dd.d = this.z[this.B].f;
                    dd.c = this.z[this.B].e;
                    this.f(2);
                    break;
                }
                if (this.B != 24) break;
                this.R = 0;
                dd.a(this.R);
                this.f(9);
                break;
            }
            case 2: {
                this.O = null;
                String[][] stringArray = null;
                if (this.z[this.B].e >= 0) {
                    switch (this.R) {
                        case 0: {
                            stringArray = ea.b[this.z[this.B].e];
                            break;
                        }
                        case 2: {
                            stringArray = ea.c[this.z[this.B].e];
                            break;
                        }
                        case 3: {
                            stringArray = ea.d[this.z[this.B].e];
                        }
                    }
                } else if (this.z[this.B].f >= 0) {
                    switch (this.R) {
                        case 0: {
                            stringArray = ea.e[this.z[this.B].f];
                            break;
                        }
                        case 2: {
                            stringArray = ea.f[this.z[this.B].f];
                            break;
                        }
                        case 3: {
                            stringArray = ea.g[this.z[this.B].f];
                        }
                    }
                }
                if (stringArray != null) {
                    this.b(new da("B\u1ecf qua", 3));
                    this.c(new da("Ti\u1ebfp", 13));
                    this.O = new ca(stringArray);
                    break;
                }
                this.y();
                break;
            }
            case 8: {
                this.aa = true;
                this.ad = 15;
                this.a((de)null);
                this.c((de)null);
                this.b(new da("B\u1ecf qua", 6));
                break;
            }
            case 1: {
                d = false;
                this.H = this.F * 10000;
                this.I = this.G * 10000;
                if (this.ah) {
                    this.ag = 0;
                    this.ah = false;
                } else {
                    this.ag = 75;
                }
                if (this.L > 0) break;
                this.D = this.C;
                this.E = this.A[this.Y].a;
                if (this.E == this.B) {
                    this.E = this.A[this.Y].b;
                }
                this.H = this.F * 10000;
                this.I = this.G * 10000;
                long l2 = (this.z[this.E].b - this.F) * 10000;
                long l3 = (this.z[this.E].c - this.G) * 10000;
                long l4 = m.a(l2 * l2 + l3 * l3);
                this.J = (int)(2L * l2 * 10000L / l4);
                this.K = (int)(2L * l3 * 10000L / l4);
                this.L = (int)(l4 / 20000L);
                this.M = this.L >> 1;
                --this.ae;
                break;
            }
            case 3: {
                this.Z = new s();
                this.Z.d(this.F, this.G);
                this.Z.a();
                if (bg.f(5) > 0) {
                    dd.h = true;
                    bg.g(5);
                } else {
                    dd.h = dd.e.e > dd.f.e ? true : (dd.e.e < dd.f.e ? false : ay.a(2) == 0);
                }
                this.N = new bp(dd.e, dd.f);
                this.aj.b();
                break;
            }
            case 9: {
                this.P = new co(this.aq);
                if (this.P.a()) {
                    this.a(new da("\u0110\u00e1nh", 2));
                    this.c(new da(" ", 2));
                }
                this.b(new da("R\u00fat lui", 4));
                break;
            }
            case 6: {
                this.B();
            }
        }
    }

    private void E() {
        this.a((de)null);
        this.c((de)null);
        this.b((de)null);
        dd.a = -1;
        switch (this.f) {
            case 2: {
                this.O = null;
                break;
            }
            case 3: {
                this.N = null;
                this.Z = null;
                break;
            }
            case 9: {
                this.P = null;
            }
        }
    }

    public void b() {
        ad ad2 = new ad(dd.e, this.W, this.X);
        ad2.a(this);
        ad2.c(new da("\u0110\u00f3ng", 5));
        cz.d().a(ad2, true);
    }

    private void F() {
        dd.c = this.T;
        if (this.P != null) {
            dd.b = dd.e.c;
            dd.e.c = dd.e.b;
            this.P.b();
        }
        u.a(null);
        cz.d().e.a(7, 5, true, true);
        cf.a().d();
        this.aj.a();
    }

    public void a() {
        ae ae2;
        Object object;
        if (this.ak) {
            this.ak = false;
            this.an = bg.t();
            if (this.an >= 0) {
                object = "B\u1ea1n \u0111\u00e3 nh\u1eadn \u0111\u01b0\u1ee3c 1 v\u1eadt ph\u1ea9m. B\u1ea1n c\u00f3 mu\u1ed1n m\u1edf v\u1eadt ph\u1ea9m ngay kh\u00f4ng? (Tham kh\u1ea3o gi\u00e1 t\u1ea1i http://free.ola.vn.)";
                if (this.am) {
                    object = "B\u1ea1n \u0111\u00e3 nh\u1eadn \u0111\u01b0\u1ee3c m\u1ed9t v\u1eadt ph\u1ea9m. B\u1ea1n c\u00f3 mu\u1ed1n m\u1edf hay kh\u00f4ng? (*Ch\u00fa \u00fd* \u0110\u00e2y l\u00e0 v\u1eadt ph\u1ea9m cu\u1ed1i c\u00f9ng. H\u00e3y m\u1edf h\u1ebft c\u00e1c v\u1eadt ph\u1ea9m c\u00f2n l\u1ea1i tr\u01b0\u1edbc khi ch\u1ecdn Ch\u01a1i m\u1edbi)";
                }
                ae2 = cz.a("Ch\u00fac m\u1eebng", (String)object, "C\u00f3", 11, "Kh\u00f4ng", 5, 1);
                ae2.a(this);
                cz.d().a(ae2, true);
            }
        }
        if (this.al) {
            this.al = false;
            this.z();
        }
        this.Q.e();
        if (this.ag == 0) {
            this.af.e();
        }
        switch (this.f) {
            case 7: {
                --this.V;
                if (this.V > 0) break;
                this.f(0);
                break;
            }
            case 0: {
                if (e) {
                    e = false;
                    this.G();
                }
                if (this.ag <= 0) break;
                --this.ag;
                break;
            }
            case 1: {
                this.H += this.J;
                this.I += this.K;
                this.F = this.H / 10000;
                this.G = this.I / 10000;
                --this.L;
                if (this.ae == 0 && this.L == this.M) {
                    dd.d = -1;
                    dd.c = -1;
                    this.ae = ay.a(3, 10);
                    dd.f = dd.x(this.Y);
                    this.f(3);
                }
                if (this.L > 0) break;
                this.M = -1;
                this.B = this.E;
                this.f(0);
                break;
            }
            case 8: {
                if (this.aa) {
                    this.aa = this.h(this.ab, this.ac);
                    break;
                }
                --this.ad;
                if (this.ad > 0) break;
                this.f(0);
                break;
            }
            case 3: {
                if (this.Z != null) {
                    this.Z.b();
                    if (!this.Z.c()) break;
                    this.Z = null;
                    this.a(new da("\u0110\u00e1nh", 2));
                    this.c(new da(" ", 2));
                    if (this.D < 0) break;
                    this.b(new da("R\u00fat lui", 4));
                    break;
                }
                if (this.N == null) break;
                this.N.a();
                break;
            }
            case 9: {
                if (!this.ai) {
                    this.ai = true;
                    object = dj.a("\u0110\u1ea5u tr\u01b0\u1eddng", "T\u1eeb b\u00e2y gi\u1edd, b\u1ea1n c\u00f3 th\u1ec3 v\u00e0o \u0111\u1ea5u tr\u01b0\u1eddng \u0111\u1ec3 luy\u1ec7n \u0111i\u1ec3m kinh nghi\u1ec7m.@N\u1ebfu b\u1ea1n \u0111\u00e1nh th\u1eafng, b\u1ea1n s\u1ebd \u0111\u01b0\u1ee3c m\u1ed9t s\u1ed1 \u0111i\u1ec3m kinh nghi\u1ec7m t\u00f9y theo c\u1ea5p \u0111\u1ed9 hi\u1ec7n t\u1ea1i.@N\u1ebfu thua, b\u1ea1n s\u1ebd b\u1ecb m\u1ea5t 1 m\u1ea1ng ch\u01a1i, nh\u01b0ng kh\u00f4ng b\u1ecb tr\u1eeb \u0111i\u1ec3m kinh nghi\u1ec7m.@*L\u01b0u \u00fd:@*-B\u1ea1n ph\u1ea3i tr\u1ea3 v\u00e0ng \u0111\u1ec3 c\u00f3 th\u1ec3 tham gia \u0111\u1ea5u tr\u01b0\u1eddng.@*-V\u00e0ng v\u00e0 \u0111i\u1ec3m kinh nghi\u1ec7m thu th\u1eadp \u0111\u01b0\u1ee3c trong qu\u00e1 tr\u00ecnh \u0111\u00e1nh s\u1ebd kh\u00f4ng c\u00f3 hi\u1ec7u l\u1ef1c.@*-M\u00e1u c\u1ee7a b\u1ea1n s\u1ebd kh\u00f4ng b\u1ecb \u1ea3nh h\u01b0\u1edfng khi tham gia \u0111\u1ea5u tr\u01b0\u1eddng.@*-Hi\u1ec7n t\u1ea1i ch\u1ec9 c\u00f3 2 m\u1ee9c luy\u1ec7n t\u1eadp, h\u00e3y t\u00ecm kh\u00e1m ph\u00e1 \u0111\u1ec3 unlock th\u00eam 3 m\u1ee9c luy\u1ec7n t\u1eadp cao h\u01a1n.", null, null, null, null);
                    ((ar)object).c(new da("\u0110\u1ed3ng \u00fd", 5));
                    ((ar)object).a(this);
                    ((ar)object).a((h)null);
                    cz.d().a((ae)object, true);
                }
                if (this.P == null) break;
                this.P.c();
                break;
            }
            case 2: {
                if (this.O == null) break;
                this.O.b();
                break;
            }
            case 5: {
                object = "";
                if (this.L == 0) {
                    if (dd.k(this.z[this.B].e)) {
                        int n2 = dd.i(this.z[this.B].e);
                        int n3 = dd.j(this.z[this.B].e);
                        object = String.valueOf(object) + "B\u1ea1n \u0111\u00e3 ho\u00e0n th\u00e0nh nhi\u1ec7m v\u1ee5. B\u1ea1n \u0111\u01b0\u1ee3c th\u01b0\u1edfng th\u00eam {e} kinh nghi\u1ec7m, {g} v\u00e0ng.";
                        object = ao.a((String)object, "{e}", "" + n2);
                        object = ao.a((String)object, "{g}", "" + n3);
                    } else if (dd.m(this.z[this.B].f)) {
                        int n4 = dd.o(this.z[this.B].f);
                        int n5 = dd.n(this.z[this.B].f);
                        object = String.valueOf(object) + "B\u1ea1n \u0111\u00e3 ho\u00e0n th\u00e0nh s\u1ef1 ki\u1ec7n. B\u1ea1n \u0111\u01b0\u1ee3c th\u01b0\u1edfng th\u00eam ";
                        boolean bl2 = false;
                        if (n4 > 0) {
                            object = String.valueOf(object) + "{h} m\u00e1u";
                            bl2 = true;
                        }
                        if (n5 > 0) {
                            if (bl2) {
                                object = String.valueOf(object) + ", ";
                            }
                            object = String.valueOf(object) + "{a} t\u1ea5n c\u00f4ng";
                        }
                        object = ao.a((String)object, "{h}", "" + n4);
                        object = ao.a((String)object, "{a}", "" + n5);
                    }
                }
                if (!((String)object).equals("")) {
                    ae2 = cz.a("Ch\u00fac m\u1eebng", (String)object, "\u0110\u1ed3ng \u00fd", 9, 1);
                    ae2.a(this);
                    cz.d().a(ae2, true);
                    break;
                }
                this.A();
                break;
            }
            case 10: {
                cz.d().a(new df(false), false);
            }
        }
        if (this.f != 8) {
            this.h(this.F, this.G);
        }
    }

    private void G() {
        di di2 = new di();
        cp cp2 = new cp(az.b("/m/mt1"), 3);
        byte[][] byArrayArray = new byte[1][];
        byte[] byArray = new byte[4];
        byArray[1] = 1;
        byArray[2] = 2;
        byArray[3] = 1;
        byArrayArray[0] = byArray;
        cp2.a(byArrayArray);
        cp2.a(4);
        cp2.a(true);
        cp2.b(0);
        di2.a(cp2);
        dj dj2 = dj.a("H\u01b0\u1edbng d\u1eabn", "- \u0110\u1ecba \u0111i\u1ec3m c\u00f3 nhi\u1ec7m v\u1ee5 s\u1ebd c\u00f3 d\u1ea5u hi\u1ec7u nh\u1eadn bi\u1ebft:@{i}@- Di chuy\u1ec3n nh\u00e2n v\u1eadt b\u1eb1ng c\u00e1ch b\u1ea5m c\u00e1c ph\u00edm 2, 4, 6, 8 t\u01b0\u01a1ng \u1ee9ng v\u1edbi h\u01b0\u1edbng mu\u1ed1n \u0111i tr\u00ean b\u1ea3n \u0111\u1ed3.@*H\u00e3y v\u00e0o c\u1ee7a h\u00e0ng \u0111\u1ec3 mua th\u00eam v\u1eadt ph\u1ea9m h\u1ed7 tr\u1ee3, gi\u00fap t\u0103ng th\u00eam s\u1ee9c t\u1ea5n c\u00f4ng, v\u00e0 m\u00e1u cho b\u1ea1n.", null, di2, null, null);
        dj2.c(new da("\u0110\u00f3ng", 5));
        dj2.a(this);
        cz.d().a(dj2, true);
    }

    private void e(Graphics graphics) {
        int n2 = 0;
        int n3 = 0;
        int n4 = this.v.getWidth();
        int n5 = this.v.getHeight();
        while (n3 < cx.g) {
            n2 = 0;
            while (n2 < cx.f) {
                graphics.drawImage(this.v, n2, n3, 0);
                n2 += n4;
            }
            n3 += n5;
        }
    }

    public void a(Graphics graphics) {
        graphics.setClip(0, 0, cx.f, cx.g);
        graphics.drawImage(this.u, this.g, this.a_, 0);
        this.b(graphics);
        int n2 = 0;
        while (n2 < this.z.length) {
            if (this.z[n2].d && this.z[n2].e != -1) {
                this.Q.a(graphics, this.g + this.z[n2].b, this.a_ + this.z[n2].c - 27);
            }
            ++n2;
        }
        graphics.drawImage(this.t, this.g + this.F, this.a_ + this.G, 3);
        graphics.drawImage(this.ao, 1, 2, 0);
        bz.d.a(graphics, "x" + this.ap, 13, 1, 0);
        switch (this.f) {
            case 0: {
                if (cx.n) {
                    this.ar.a(this.g + this.F - (this.ar.a() >> 1), this.a_ + this.G - (this.ar.b() >> 1));
                    this.ar.a(graphics);
                }
            }
            case 7: {
                if (this.ag != 0) break;
                this.af.a(graphics, this.g + this.F - 5, this.a_ + this.G - 20);
                break;
            }
            case 2: {
                this.e(graphics);
                if (this.O == null) break;
                this.O.a(graphics);
                break;
            }
            case 3: {
                if (this.Z != null) {
                    this.Z.a(graphics, this.g, this.a_);
                    break;
                }
                this.e(graphics);
                if (this.N == null) break;
                this.N.a(graphics);
                break;
            }
            case 9: {
                this.e(graphics);
                if (this.P == null) break;
                this.P.a(graphics);
            }
        }
    }

    public void b(int n2, int n3) {
        switch (n3) {
            case 0: {
                u.a(null);
                cz.a().a(7, 4, true, true);
                break;
            }
            case 1: {
                cz.d().a(false);
                u.a(null);
                cz.a().a(7, 2, true, true);
                break;
            }
            case 3: {
                this.y();
                break;
            }
            case 6: {
                this.ac = -1;
                this.ab = -1;
                this.f(0);
                break;
            }
            case 2: {
                if (!bg.l()) {
                    ae ae2 = cz.a("Ch\u00fa \u00fd", "B\u1ea1n \u0111\u00e3 h\u1ebft m\u1ea1ng ch\u01a1i. B\u1ea1n c\u00f3 mu\u1ed1n mua th\u00eam m\u1ea1ng ch\u01a1i kh\u00f4ng?. Tham kh\u1ea3o gi\u00e1 t\u1ea1i http://free.ola.vn.", "C\u00f3", 12, "Kh\u00f4ng", 14, 1);
                    ae2.a(this);
                    cz.d().a(ae2, true);
                    break;
                }
                this.F();
                break;
            }
            case 4: {
                if (this.L == 0) {
                    this.x();
                    break;
                }
                this.J = -this.J;
                this.K = -this.K;
                this.E = this.B;
                this.f(1);
                break;
            }
            case 5: {
                cz.d().a(true);
                break;
            }
            case 7: {
                ae ae3 = cz.a("Ch\u00fa \u00fd", "B\u1ea1n c\u00f3 mu\u1ed1n tr\u1edf v\u1ec1 menu hay kh\u00f4ng?", "C\u00f3", 1, "Kh\u00f4ng", 5, 1);
                ae3.a(this);
                cz.d().a(ae3, true);
                break;
            }
            case 8: {
                this.b();
                break;
            }
            case 9: {
                if (dd.m(dd.d)) {
                    this.A();
                    break;
                }
                ae ae4 = dd.a(true);
                ae4.a(this);
                ae4.c(new da("\u0110\u00f3ng", 10));
                cz.d().a(true);
                cz.d().a(ae4, true);
                break;
            }
            case 10: {
                cz.d().a(true);
                this.A();
                break;
            }
            case 11: {
                c = true;
                bg.e(this.an);
                break;
            }
            case 12: {
                cz.d().a(false);
                an.b("", cx.G, new com.mic.smsgame.suquan.ae(this));
                break;
            }
            case 13: {
                if (this.O.a()) break;
                this.y();
                break;
            }
            case 14: {
                cz.d().a(false);
            }
        }
    }

    public void e() {
        long l2 = System.currentTimeMillis();
        if (this.f != 10) {
            this.w();
        }
        d.a("DONE SAVEMAP." + (System.currentTimeMillis() - l2));
    }

    static void a(aa aa2) {
        aa2.F();
    }
}

