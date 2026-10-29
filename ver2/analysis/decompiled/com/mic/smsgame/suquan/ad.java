/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.media.Manager
 *  javax.microedition.media.MediaException
 *  javax.microedition.media.Player
 *  javax.microedition.media.PlayerListener
 *  javax.microedition.media.control.VolumeControl
 */
package com.mic.smsgame.suquan;

import java.io.InputStream;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;
import javax.microedition.media.control.VolumeControl;

public class ad
implements at,
PlayerListener {
    private Player a;
    private VolumeControl b;
    private String c = "";
    private boolean d;
    private long e;
    private int f;
    private int g = 5000;

    public ad() {
    }

    public ad(String string) {
        this.a(string);
    }

    public void a(String string) {
        this.d = false;
        d.a("Play " + string + " - " + this.c + "...");
        if (this.a != null && string.equals(this.c)) {
            this.d();
            return;
        }
        try {
            if (!cx.x) {
                return;
            }
            this.a();
            this.a = Manager.createPlayer((InputStream)"".getClass().getResourceAsStream("/audio/" + string + ".amr"), (String)"audio/amr");
            this.a.setLoopCount(1);
            this.a.realize();
            this.a.prefetch();
            this.a.addPlayerListener((PlayerListener)this);
            this.b = (VolumeControl)this.a.getControl("VolumeControl");
            if (this.b != null) {
                this.b.setMute(!cx.x);
                this.b.setLevel(cx.y);
            }
            this.c = string;
            d.a("Write currFile " + this.c);
        }
        catch (Throwable throwable) {
            d.a("[Create Effect Sound" + throwable);
            this.a();
        }
    }

    public void a() {
        if (this.a != null) {
            try {
                this.a.stop();
                this.a.deallocate();
                this.a.close();
                this.a = null;
                this.b = null;
                d.a("destroy " + this.c);
            }
            catch (MediaException mediaException) {
                this.a = null;
                this.b = null;
                d.a("[Destroy Effect Sound" + (Object)((Object)mediaException));
            }
        }
    }

    public void b() {
        if (!cx.x) {
            return;
        }
        this.d = true;
        if (this.a == null && !this.c.equals("")) {
            this.a(this.c);
        }
        this.e = System.currentTimeMillis();
        ci.a().a(this);
    }

    public void d() {
        if (this.a != null) {
            try {
                this.a.stop();
                this.d = false;
            }
            catch (Throwable throwable) {
                this.a();
                d.a("[STOP EFFECT Music]: " + throwable);
            }
        }
    }

    public void c() {
        if (!this.d) {
            return;
        }
        try {
            if (cx.V) {
                this.a.stop();
            }
            this.a.setMediaTime(0L);
            if (this.b != null) {
                this.b.setMute(!cx.x);
                this.b.setLevel(cx.y);
            }
            if (this.d) {
                this.a.start();
            }
        }
        catch (Exception exception) {
            this.a();
            this.d = false;
            d.a("[Play Effect Sound " + exception);
        }
    }

    public boolean e() {
        if (this.d) {
            if (this.f > 3) {
                this.d();
            } else if (System.currentTimeMillis() - this.e > (long)this.g) {
                ++this.f;
                this.d();
            }
        }
        return this.d;
    }

    public void playerUpdate(Player player, String string, Object object) {
        if (cx.x) {
            if (string.equals("endOfMedia")) {
                this.d = false;
            } else if (string.equals("started") && !this.d) {
                this.d();
            }
        }
    }
}

