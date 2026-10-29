/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class cu
extends ab {
    private int[] v;
    private int[] w;
    private int x;
    private Image[] y;
    private int z;
    private int A;

    public cu(Image image, int n2, Image[] imageArray) {
        this.a(image, n2);
        this.y = imageArray;
        this.t = 3;
    }

    public void a(int n2, int n3, int n4, int n5, int n6) {
        this.x = 1;
        this.u = true;
        this.z = n6;
        this.A = 5;
    }

    public void a(int[] nArray, int[] nArray2) {
        this.v = nArray;
        this.w = nArray2;
    }

    public void a() {
        if (this.A <= 0) {
            if (this.x < this.v.length) {
                ++this.x;
            }
            this.A = 5;
        } else {
            --this.A;
        }
        if (this.z > 0) {
            --this.z;
            if (this.z == 0) {
                this.u = false;
            }
        }
    }

    public void a(Graphics graphics, int n2, int n3) {
        if (!this.u) {
            return;
        }
        int n4 = 0;
        while (n4 < this.x) {
            super.a(graphics, this.v[n4], this.w[n4]);
            ++n4;
        }
        n4 = this.x - 1;
        int n5 = 0;
        while (n5 < n4) {
            this.a(graphics, this.v[n5], this.w[n5], this.v[n5 + 1], this.w[n5 + 1]);
            ++n5;
        }
    }

    private void a(Graphics graphics, int n2, int n3, int n4, int n5) {
        int n6 = n2;
        int n7 = n3;
        int n8 = 0;
        int n9 = 0;
        boolean bl2 = true;
        boolean bl3 = true;
        do {
            if (bl2) {
                bl2 = n6 > n4;
                n8 = bl2 ? n6 - 21 : n6 + 21;
                boolean bl4 = bl2 = bl2 == n8 > n4;
                if (!bl2) {
                    n8 = n4;
                }
            }
            if (bl3) {
                bl3 = n7 > n5;
                n9 = bl3 ? n7 - 21 : n7 + 21;
                boolean bl5 = bl3 = bl3 == n9 > n5;
                if (!bl3) {
                    n9 = n5;
                }
            }
            if (n6 < n8) {
                if (n7 < n9) {
                    graphics.drawImage(this.y[0], n6, n7, 20);
                } else if (n7 > n9) {
                    graphics.drawRegion(this.y[0], 0, 0, this.y[0].getWidth(), this.y[0].getHeight(), 2, n6, n9, 20);
                } else {
                    graphics.drawImage(this.y[1], n6, n7, 6);
                }
            } else if (n6 > n8) {
                if (n7 < n9) {
                    graphics.drawRegion(this.y[0], 0, 0, this.y[0].getWidth(), this.y[0].getHeight(), 2, n8, n7, 20);
                } else if (n7 > n9) {
                    graphics.drawImage(this.y[0], n8, n9, 20);
                } else {
                    graphics.drawImage(this.y[1], n8, n9, 6);
                }
            } else if (n7 < n9) {
                graphics.drawRegion(this.y[1], 0, 0, this.y[1].getWidth(), this.y[1].getHeight(), 5, n6, n7, 17);
            } else if (n7 > n9) {
                graphics.drawRegion(this.y[1], 0, 0, this.y[1].getWidth(), this.y[1].getHeight(), 5, n6, n9, 17);
            }
            n6 = n8;
            n7 = n9;
        } while (bl2 || bl3);
    }
}

