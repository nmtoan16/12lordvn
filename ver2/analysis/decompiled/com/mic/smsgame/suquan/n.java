/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
package com.mic.smsgame.suquan;

import javax.microedition.lcdui.Graphics;

public class n
extends dq {
    private boolean a = false;
    private int v;

    public void a(int n2, int n3, int n4, int n5, int n6) {
        this.d(n2, n3);
        this.i = n4;
        this.j = n5;
        this.u = true;
        this.k = this.b(this.p, n4, 12) + ay.a(3);
        this.l = this.b(this.q, n5, 12) + ay.a(3);
        this.a = false;
        this.v = n6;
    }

    public void a() {
        if (this.v > 0) {
            --this.v;
            return;
        }
        if (this.a) {
            ++this.l;
            ++this.k;
        } else {
            this.k = this.b(this.p, this.i, 4) + ay.a(2);
            this.l = this.b(this.q, this.j, 8) + ay.a(2);
        }
        if (this.c(this.i, this.j, this.k, this.l)) {
            if (this.a) {
                this.u = false;
            } else {
                this.i = this.p + ay.a(20);
                this.j = this.q + 40 + ay.a(100);
                this.k = this.b(this.p, this.i, 14) + ay.a(2);
                this.l = this.b(this.q, this.j, 10) + ay.a(2);
                this.a = true;
                this.c(this.i, this.j, this.k, this.l);
            }
        }
    }

    public void a(Graphics graphics, int n2, int n3) {
        if (this.v > 0) {
            return;
        }
        super.a(graphics, n2, n3);
    }
}

