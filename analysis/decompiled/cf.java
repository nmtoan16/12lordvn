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
import java.io.InputStream;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;
import javax.microedition.media.control.TempoControl;
import javax.microedition.media.control.VolumeControl;

public class cf
implements at,
PlayerListener {
    public static cf a;
    private Player b;
    private String c = "";
    private VolumeControl d;
    private TempoControl e;
    private int f = 100;
    private boolean g;

    protected cf() {
    }

    public static cf a() {
        if (a == null) {
            a = new cf();
        }
        return a;
    }

    public void a(String string, int n2) {
        try {
            this.b(string, n2);
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
            d.a("Create Back ground 1: " + throwable);
            this.d();
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
        if (this.b != null && this.c.equals(string)) {
            this.f = cx.z;
            return;
        }
        this.d();
        this.b = Manager.createPlayer((InputStream)"".getClass().getResourceAsStream("/audio/" + string + ".mid"), (String)"audio/midi");
        this.b.setLoopCount(n2);
        this.b.realize();
        this.b.prefetch();
        this.b.addPlayerListener((PlayerListener)this);
        this.d = (VolumeControl)this.b.getControl("VolumeControl");
        if (this.d != null) {
            this.d.setLevel(cx.y);
            this.d.setMute(!cx.x);
        }
        this.e = (TempoControl)this.b.getControl("TempoControl");
        if (this.e != null) {
            this.f = cx.z = this.e.getTempo();
            this.e.setTempo(this.f);
            d.a("Temp when create=" + this.f);
        }
        this.c = string;
        this.g = false;
    }

    public void b() {
        if (this.b != null) {
            try {
                this.b.stop();
            }
            catch (Throwable throwable) {
                this.d();
                d.a("[STOP BK Music]: " + throwable);
            }
        }
    }

    public void d() {
        if (this.b != null) {
            try {
                this.b.stop();
                this.b.deallocate();
                this.b.close();
                this.b = null;
                this.d = null;
            }
            catch (MediaException mediaException) {
                this.b = null;
                this.d = null;
                d.a("[Destroy BK Music]: " + (Object)((Object)mediaException));
            }
        }
        this.c = "";
    }

    public void e() {
        if (!cx.x) {
            return;
        }
        ci.a().a(this);
    }

    public void c() {
        try {
            if (this.b != null) {
                if (this.d != null) {
                    this.d.setMute(!cx.x);
                    this.d.setLevel(cx.y);
                }
                this.b.start();
            }
        }
        catch (Exception exception) {
            d.a("[Player BK Music]: " + exception);
            this.d();
        }
    }

    public int f() {
        return this.f;
    }

    public void a(int n2) {
        this.f = n2;
        if (this.e != null) {
            try {
                this.e.setTempo(n2);
            }
            catch (Exception exception) {
                this.f = cx.z;
                this.e.setTempo(this.f);
            }
        }
    }

    public void playerUpdate(Player player, String string, Object object) {
        if (string.equals("endOfMedia")) {
            if (this.d != null) {
                this.d.setLevel(cx.y);
                this.d.setMute(!cx.x);
            }
            if (this.e != null) {
                try {
                    this.e.setTempo(this.f);
                }
                catch (Exception exception) {
                    this.f = cx.z;
                    this.e.setTempo(this.f);
                }
            }
        } else if (string.equals("started")) {
            this.g = true;
        } else if (string.equals("stopped")) {
            this.g = false;
        }
    }
}

