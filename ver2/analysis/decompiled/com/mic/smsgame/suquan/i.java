/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
package com.mic.smsgame.suquan;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class i
extends dq {
    private int a = 0;
    private int v;
    private int w;
    private int x;
    private int y;
    private int z;
    private int A;
    private boolean B;

    public void a(int n2, int n3) {
        this.w = n2;
        this.y = n3;
    }

    public i(int n2, int n3) {
        super(n2, n3);
        this.b(false);
    }

    public void a(int n2, int n3, Image image) {
        this.d(n2, n3);
        this.a(image, 1);
        this.a_(1);
    }

    public void a(int n2, int n3, int n4, int n5, Image image) {
        this.b(n2, n3, n4, n5);
        this.a(image, 1);
        this.a_(4);
    }

    public void a(int n2, int n3, int n4, int n5, Image image, int n6) {
        this.b(n2, n3, n4, n5);
        this.a(image, 1);
        this.a_(3);
        this.a = n6;
    }

    public void a(int n2, int n3, Image image, int n4) {
        this.d(n2, n3);
        this.a(image, n4);
        this.a_(2);
    }

    public void b(int n2, int n3) {
        this.v = n2;
        this.a_(5);
        this.a = n3;
    }

    private void b(int n2, int n3, int n4, int n5) {
        this.d(n2, n3);
        this.c(n4, n5);
    }

    public void a_(int n2) {
        super.a_(n2);
        this.g = 0;
    }

    public void a(Graphics graphics, int n2, int n3) {
        if (this.c == 5) {
            int n4 = this.p - this.v / 2;
            int n5 = this.q - this.v / 2;
            y.a(graphics, y.d);
            y.a(graphics, y.d, n4, n5, this.v, this.v);
            super.a(graphics, n2, n3);
            y.b(graphics, y.d);
        } else {
            if (this.B && this.c == 1) {
                n2 += this.z;
                n3 += this.A;
            }
            super.a(graphics, n2, n3);
        }
    }

    public void a() {
        if (this.x < this.y) {
            ++this.x;
            if (this.x == this.w) {
                this.B = true;
            }
            if (this.B) {
                this.z = ay.a() % 2;
                this.A = ay.a() % 2;
                if (this.x == this.w + 3) {
                    this.B = false;
                }
            }
        } else {
            this.x = 0;
        }
        if (this.a > 0) {
            --this.a;
            return;
        }
        switch (this.c) {
            case 1: {
                break;
            }
            case 6: {
                this.a_(0);
                break;
            }
            case 2: {
                if (this.g < this.d[2].length - 1) break;
                this.a_(0);
                break;
            }
            case 3: {
                if (!this.f(this.j, this.b(this.q, this.j, 2))) break;
                this.a_(1);
                break;
            }
            case 4: {
                if (!this.c(this.i, this.j, this.b(this.p, this.i, 2), this.b(this.q, this.j, 2))) break;
                this.a_(1);
                break;
            }
            case 0: {
                break;
            }
            case 5: {
                if (this.v <= 0) {
                    this.a_(0);
                    return;
                }
                this.v -= 3;
                if (this.v >= 0) break;
                this.v = 0;
            }
        }
    }
}

