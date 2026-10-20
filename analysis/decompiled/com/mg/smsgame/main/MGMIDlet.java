/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.io.PushRegistry
 *  javax.microedition.lcdui.Display
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.midlet.MIDlet
 *  javax.wireless.messaging.MessageConnection
 */
package com.mg.smsgame.main;

import java.io.IOException;
import javax.microedition.io.PushRegistry;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;
import javax.wireless.messaging.MessageConnection;
import javay.microedition.in.Connector;

public abstract class MGMIDlet
extends MIDlet {
    public Display a;
    public static boolean b = false;
    private boolean f = false;
    protected long c = 0L;
    public boolean d;
    protected static MGMIDlet e;

    public MGMIDlet(boolean bl2, String string, String string2, String string3, String string4) {
        cx.F = bl2;
        if (cx.F) {
            an.h = 0;
            an.g = 0;
            an.f = 0;
            an.e = 0;
            an.i = 0;
            an.j = 0;
        }
        if (string != null) {
            an.a = string;
        }
        if (string2 != null) {
            an.b = string2;
        }
        if (string3 != null) {
            an.d = string3;
        }
        if (string4 != null) {
            an.c = string4;
        }
        an.k = String.valueOf(an.a) + an.b;
        this.a();
        this.f = false;
        String string5 = this.getAppProperty("Distributor-Name");
        if (string5 == null || string5.length() == 0) {
            string5 = "asao";
        }
        an.l = string5;
        try {
            this.g();
            this.h();
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        this.i();
        this.a = Display.getDisplay((MIDlet)this);
        e = this;
        this.a.setCurrent((Displayable)new cn());
    }

    public void a(String string) {
        MGMIDlet.f().platformRequest(string);
        MGMIDlet.f().notifyDestroyed();
    }

    private void g() {
        int n2;
        String string = System.getProperty("microedition.platform");
        cx.V = false;
        if (string != null && (n2 = string.toLowerCase().trim().indexOf("nokia")) >= 0) {
            cx.V = true;
            if (System.getProperty("com.nokia.mid.imei") != null || System.getProperty("com.nokia.mid.imsi") != null || System.getProperty("com.nokia.mid.networkid") != null || System.getProperty("com.nokia.mid.networksignal") != null || System.getProperty("com.nokia.mid.networkavailability") != null || System.getProperty("com.nokia.mid.batterylevel") != null || System.getProperty("com.nokia.mid.countrycode") != null) {
                cx.V = false;
            }
        }
        d.a("NokiaLessThanSerials60=" + cx.V);
    }

    private void h() {
        int n2;
        cx.D = false;
        String string = System.getProperty("com.sonyericsson.java.platform");
        int n3 = (string = string.substring(3)).indexOf(".");
        if (n3 > 0) {
            string = string.substring(0, n3);
        }
        if ((n2 = Integer.parseInt(string)) < 8) {
            cx.D = true;
        }
    }

    protected void destroyApp(boolean bl2) {
        ci.a().b();
    }

    protected void pauseApp() {
        this.c = System.currentTimeMillis();
        this.b();
    }

    protected void startApp() {
        if (!this.f) {
            this.f = true;
            if (cx.C) {
                this.j();
            }
            this.d = true;
        } else {
            long l2 = System.currentTimeMillis() - this.c;
            this.a(l2);
        }
    }

    private void i() {
        String[] stringArray = PushRegistry.listConnections((boolean)false);
        if (stringArray == null || stringArray.length == 0) {
            try {
                String string = "sms://:" + an.d;
                String string2 = ((Object)((Object)this)).getClass().getName();
                PushRegistry.registerConnection((String)string, (String)string2, (String)"*");
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    private void j() {
        if (b) {
            return;
        }
        String[] stringArray = PushRegistry.listConnections((boolean)true);
        if (stringArray != null && stringArray.length > 0) {
            try {
                MessageConnection messageConnection = (MessageConnection)Connector.open("sms://:" + an.d);
                an.a(messageConnection);
                b = true;
            }
            catch (IOException iOException) {}
        } else {
            try {
                MessageConnection messageConnection = (MessageConnection)Connector.open("sms://:" + an.d);
                an.a(messageConnection);
                b = false;
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    public final void c() {
        bg.i();
        this.a.setCurrent((Displayable)cz.d());
        this.a(b);
        if (!cx.F && al.a()) {
            al.e();
        }
        if (!al.a(true)) {
            al.e();
        }
        cz.d().b();
    }

    public void d() {
        this.notifyDestroyed();
    }

    public void e() {
        this.a.setCurrent((Displayable)d.a());
    }

    public static MGMIDlet f() {
        return e;
    }

    protected abstract void a();

    protected abstract void a(boolean var1);

    protected abstract void b();

    protected abstract void a(long var1);
}

