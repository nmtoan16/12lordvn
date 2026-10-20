/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.wireless.messaging.MessageConnection
 *  javax.wireless.messaging.MessageListener
 */
import com.mg.smsgame.main.a;
import java.io.IOException;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.MessageListener;

class dm
implements MessageListener {
    private static dm a;
    private static ct b;
    private ba c;
    private static MessageConnection d;

    protected dm() {
        this.a(an.b());
    }

    public static dm a() {
        if (a == null) {
            a = new dm();
        }
        return a;
    }

    public static void a(MessageConnection messageConnection) {
        dm.a().b(messageConnection);
    }

    private void b(MessageConnection messageConnection) {
        d = messageConnection;
        try {
            d.setMessageListener((MessageListener)this);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    public void notifyIncomingMessage(MessageConnection messageConnection) {
        b = new ct(this.c, messageConnection);
        b.a();
    }

    public static void a(String string, String string2, a a2) {
        dm.a().b(string, string2, a2);
    }

    private void b(String string, String string2, a a2) {
        cs.a().a(new bc(string, string2, a2));
    }

    public void a(ba ba2) {
        this.c = ba2;
    }
}

