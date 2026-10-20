/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.wireless.messaging.MessageConnection
 *  javax.wireless.messaging.TextMessage
 */
import com.mg.smsgame.main.b;
import java.io.IOException;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;
import javay.microedition.in.Connector;

class cs
implements b,
Runnable {
    private bc a;
    private bc b;
    private static cs c;
    private boolean d;
    private Object e = new Object();

    public static cs a() {
        if (c == null) {
            c = new cs();
        }
        cz.f();
        return c;
    }

    protected cs() {
        Thread thread = new Thread(this);
        thread.start();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void run() {
        this.d = false;
        while (!this.d) {
            Object object;
            if (this.a == null) {
                try {
                    object = this.e;
                    synchronized (object) {
                        this.e.wait();
                    }
                }
                catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                continue;
            }
            cx.H = true;
            cz.d().a(cz.b("Ch\u00fa \u00fd", "\u0110ang g\u1eedi tin nh\u1eafn SMS", false), false);
            object = "sms://" + this.a.a;
            MessageConnection messageConnection = null;
            try {
                try {
                    messageConnection = (MessageConnection)Connector.open((String)object);
                    TextMessage textMessage = (TextMessage)messageConnection.newMessage("text");
                    textMessage.setAddress((String)object);
                    textMessage.setPayloadText(this.a.b);
                    try {
                        al.c();
                    }
                    catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                    cz.d().a(false);
                    if (this.a.c != null) {
                        this.a.c.a();
                    }
                    cx.H = false;
                    this.a = null;
                }
                catch (Throwable throwable) {
                    throwable.printStackTrace();
                    this.b = this.a;
                    this.a = null;
                    cz.d().a(false);
                    ae ae2 = cz.a("Ch\u00fa \u00fd", "Kh\u00f4ng th\u1ec3 g\u1eedi tin SMS. B\u1ea1n c\u00f3 mu\u1ed1n g\u1eedi l\u1ea1i kh\u00f4ng?", "C\u00f3", 1, "Kh\u00f4ng", 2, 1);
                    ae2.a(this);
                    cz.d().a(ae2);
                    if (messageConnection != null) {
                        try {
                            messageConnection.close();
                        }
                        catch (IOException iOException) {
                            iOException.printStackTrace();
                        }
                    }
                    if (cx.F || !al.a()) continue;
                    al.b();
                    continue;
                }
            }
            catch (Throwable throwable) {
                if (messageConnection != null) {
                    try {
                        messageConnection.close();
                    }
                    catch (IOException iOException) {
                        iOException.printStackTrace();
                    }
                }
                if (!cx.F && al.a()) {
                    al.b();
                }
                throw throwable;
            }
            if (messageConnection != null) {
                try {
                    messageConnection.close();
                }
                catch (IOException iOException) {
                    iOException.printStackTrace();
                }
            }
            if (cx.F || !al.a()) continue;
            al.b();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void a(bc bc2) {
        if (bc2 == null) {
            return;
        }
        this.a = bc2;
        Object object = this.e;
        synchronized (object) {
            this.e.notify();
        }
    }

    public void b(int n2, int n3) {
        if (n3 == 2) {
            cx.H = false;
            cz.d().a(false);
            cz.d().a(true);
        } else if (n3 == 1) {
            cz.d().a(false);
            this.a(this.b);
            this.b = null;
        }
    }
}

