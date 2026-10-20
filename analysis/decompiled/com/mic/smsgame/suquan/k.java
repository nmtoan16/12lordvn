/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.media.Manager
 *  javax.microedition.media.MediaException
 *  javax.microedition.media.Player
 *  javax.microedition.media.PlayerListener
 *  javax.microedition.media.control.TempoControl
 *  javax.microedition.media.control.VolumeControl
 */
package com.mic.smsgame.suquan;

import java.io.InputStream;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;
import javax.microedition.media.control.TempoControl;
import javax.microedition.media.control.VolumeControl;

public class k
implements at,
PlayerListener {
    private Player a;
    private String b = "";
    private VolumeControl c;
    private TempoControl d;
    private int e = 100;

    public void a(String string, int n2) {
        try {
            this.b(string, n2);
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
            d.a("Create Back ground 1: " + throwable);
            this.b();
            try {
                this.b(string, n2);
            }
            catch (Exception exception) {
                d.a("Back ground 2: " + exception);
            }
        }
    }

    private void b(String string, int n2) {
        if (!cx.x) {
            return;
        }
        if (this.a != null && this.b.equals(string)) {
            this.e = cx.z;
            return;
        }
        this.b();
        this.a = Manager.createPlayer((InputStream)"".getClass().getResourceAsStream("/audio/" + string + ".mid"), (String)"audio/midi");
        this.a.setLoopCount(n2);
        this.a.realize();
        this.a.addPlayerListener((PlayerListener)this);
        this.c = (VolumeControl)this.a.getControl("VolumeControl");
        if (this.c != null) {
            this.c.setLevel(cx.y);
            this.c.setMute(!cx.x);
        }
        this.d = (TempoControl)this.a.getControl("TempoControl");
        if (this.d != null) {
            this.e = cx.z = this.d.getTempo();
            this.d.setTempo(this.e);
            d.a("Temp when create=" + this.e);
        }
        this.b = string;
    }

    public void a() {
        if (this.a != null) {
            try {
                this.a.stop();
                this.a.deallocate();
            }
            catch (MediaException mediaException) {
                this.b();
                d.a("[Destroy BK Music]: " + (Object)((Object)mediaException));
            }
        }
    }

    public void b() {
        if (this.a != null) {
            try {
                this.a.stop();
                this.a.deallocate();
                this.a.close();
                this.a = null;
                this.c = null;
            }
            catch (MediaException mediaException) {
                this.a = null;
                this.c = null;
                d.a("[Destroy BK Music]: " + (Object)((Object)mediaException));
            }
        }
        this.b = "";
    }

    public void d() {
        if (!cx.x) {
            return;
        }
        ci.a().a(this);
    }

    public void c() {
        try {
            if (this.a != null) {
                this.a.prefetch();
                this.a.setMediaTime(0L);
                if (this.c != null) {
                    this.c.setMute(!cx.x);
                    this.c.setLevel(cx.y);
                }
                this.a.start();
            }
        }
        catch (Exception exception) {
            d.a("[Player BK Music]: " + exception);
            this.b();
        }
    }

    public void a(int n2) {
        this.e = n2;
        if (this.d != null) {
            try {
                this.d.setTempo(n2);
            }
            catch (Exception exception) {
                this.e = cx.z;
                this.d.setTempo(this.e);
            }
        }
    }

    public void playerUpdate(Player player, String string, Object object) {
        if (cx.x && string.equals("endOfMedia")) {
            this.a();
        }
    }
}

