/*
 * Decompiled with CFR 0.152.
 */
import com.mic.smsgame.suquan.a;
import com.mic.smsgame.suquan.e;

public class t
extends ch
implements at,
e {
    public t(bu bu2, int n2) {
        super(bu2, n2);
    }

    public void a() {
        System.out.println("start AI");
        ci.a().a(this);
    }

    public void b() {
    }

    public void c() {
        block13: {
            try {
                int n2;
                try {
                    Thread.sleep(1000L);
                }
                catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                this.b.c(120);
                di di2 = this.b.q();
                if (di2 == null) break block13;
                int n3 = 0;
                int n4 = 0;
                int n5 = di2.b() - 1;
                while (n5 > -1) {
                    n2 = 0;
                    a a2 = (a)di2.b(n5);
                    int n6 = this.b.d(a2.a, a2.b);
                    int n7 = this.b.d(a2.c, a2.d);
                    int[] nArray = new int[]{n7, n7, n6, n6};
                    int n8 = a2.e.length - 1;
                    while (n8 > -1) {
                        int n9 = a2.e[n8] & 0xFF;
                        if (n9 >= 3) {
                            nArray[n8] = n8 == 0 || n8 == 2 ? this.b.a(nArray[n8], a2.a, a2.b, (a2.e[n8] & 0xFF0000) >> 16, (a2.e[n8] & 0xFF00) >> 8) : this.b.b(nArray[n8], a2.a, a2.b, (a2.e[n8] & 0xFF0000) >> 16, (a2.e[n8] & 0xFF00) >> 8);
                            int n10 = this.b.e(nArray[n8], n9);
                            n2 += n10;
                        }
                        --n8;
                    }
                    if (n2 >= n3 && !this.b.e(this.a)) {
                        n4 = n5;
                        n3 = n2;
                    }
                    --n5;
                }
                a a3 = (a)di2.b(n4);
                n2 = this.b.d();
                if (n2 >= 1 && ay.a(100) < 50) {
                    try {
                        Thread.sleep(1000L);
                    }
                    catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    this.b.a(ay.a(n2));
                    break block13;
                }
                this.b.a(a3);
                try {
                    Thread.sleep(1000L);
                }
                catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                this.b.a(a3.a, a3.b, a3.c, a3.d);
            }
            catch (Exception exception) {
                d.a(exception);
                exception.printStackTrace();
            }
        }
    }
}

