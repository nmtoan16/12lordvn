/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.wireless.messaging.BinaryMessage
 *  javax.wireless.messaging.Message
 *  javax.wireless.messaging.MessageConnection
 *  javax.wireless.messaging.TextMessage
 */
import java.io.IOException;
import javax.wireless.messaging.BinaryMessage;
import javax.wireless.messaging.Message;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;

class ct
implements Runnable {
    private Message a;
    private MessageConnection b;
    private String c;
    private String d = "";
    private ba e;

    ct(ba ba2, MessageConnection messageConnection) {
        this.b = messageConnection;
        this.e = ba2;
    }

    public void a() {
        Thread thread = new Thread(this);
        thread.start();
    }

    public void run() {
        while (!cx.N) {
            try {
                Thread.sleep(100L);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        this.b();
    }

    void b() {
        try {
            this.a = this.b.receive();
            if (this.a != null) {
                String string = this.c = this.a.getAddress();
                if (this.a instanceof TextMessage) {
                    this.d = ((TextMessage)this.a).getPayloadText();
                    if (this.e != null) {
                        this.e.a(this.d, string);
                    }
                } else {
                    byte[] byArray = ((BinaryMessage)this.a).getPayloadData();
                    this.d = new String(byArray);
                    if (this.e != null) {
                        this.e.a(this.d, string);
                    }
                }
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }
}

