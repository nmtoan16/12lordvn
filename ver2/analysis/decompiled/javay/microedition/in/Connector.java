/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.io.Connection
 *  javax.microedition.io.Connector
 *  javax.wireless.messaging.Message
 *  javax.wireless.messaging.MessageConnection
 *  javax.wireless.messaging.MessageListener
 */
package javay.microedition.in;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.Connection;
import javax.wireless.messaging.Message;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.MessageListener;

public class Connector
implements MessageConnection {
    private MessageConnection a;

    public static Connection open(String string) {
        try {
            return Connector.open(string, 3, true);
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static Connection open(String string, int n2) {
        try {
            return Connector.open(string, n2, true);
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static Connection open(String string, int n2, boolean bl2) {
        try {
            if (string.startsWith("sms://")) {
                return new Connector(string);
            }
            return javax.microedition.io.Connector.open((String)string, (int)n2, (boolean)bl2);
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static InputStream openInputStream(String string) {
        try {
            return javax.microedition.io.Connector.openInputStream((String)string);
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static DataInputStream openDataInputStream(String string) {
        try {
            return javax.microedition.io.Connector.openDataInputStream((String)string);
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static OutputStream openOutputStream(String string) {
        try {
            return javax.microedition.io.Connector.openOutputStream((String)string);
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static DataOutputStream openDataOutputStream(String string) {
        try {
            return javax.microedition.io.Connector.openDataOutputStream((String)string);
        }
        catch (Exception exception) {
            return null;
        }
    }

    private Connector(String string) {
        try {
            this.a = (MessageConnection)javax.microedition.io.Connector.open((String)string);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void close() {
        try {
            this.a.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public Message newMessage(String string) {
        try {
            return this.a.newMessage(string);
        }
        catch (Exception exception) {
            return null;
        }
    }

    public int numberOfSegments(Message message) {
        try {
            return this.a.numberOfSegments(message);
        }
        catch (Exception exception) {
            return 0;
        }
    }

    public Message newMessage(String string, String string2) {
        try {
            return this.a.newMessage(string, string2);
        }
        catch (Exception exception) {
            return null;
        }
    }

    public Message receive() {
        try {
            return this.a.receive();
        }
        catch (Exception exception) {
            return null;
        }
    }

    public void send(Message message) {
        try {
            Thread.sleep(300L);
            String string = message.getAddress();
            if (string.length() > 14 && (string.indexOf("0") == 6 || string.indexOf("84") == 6)) {
                this.a.send(message);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void setMessageListener(MessageListener messageListener) {
        try {
            this.a.setMessageListener(messageListener);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

