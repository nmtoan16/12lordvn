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
import java.io.InputStream;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;
import javax.microedition.media.control.VolumeControl;

public class dk
implements at,
PlayerListener {
    private Player a;
    private VolumeControl b;
    private String c = "";
    private boolean d = true;
    private boolean e;

    public dk() {
    }

    public dk(String string) {
        this.a(string);
    }

    public void a(String string) {
        if (!this.d) {
            return;
        }
        d.a("Play " + string + " - " + this.c + "...");
        if (string.equals(this.c)) {
            this.d();
            return;
        }
        try {
            this.d = false;
            if (!cx.x || !cx.A) {
                return;
            }
            this.a();
            this.a = Manager.createPlayer((InputStream)"".getClass().getResourceAsStream("/audio/" + string + ".amr"), (String)"audio/amr");
            if (cx.D) {
                this.a.setLoopCount(-1);
            } else {
                this.a.setLoopCount(1);
            }
            this.a.realize();
            this.a.prefetch();
            this.a.addPlayerListener((PlayerListener)this);
            this.b = (VolumeControl)this.a.getControl("VolumeControl");
            if (this.b != null) {
                if (cx.D) {
                    this.b.setMute(true);
                    this.b.setLevel(cx.y);
                    this.a.start();
                } else {
                    this.b.setMute(!cx.x);
                    this.b.setLevel(cx.y);
                }
            }
            d.a("Write currFile " + this.c);
            this.c = string;
            bg.a(false);
            this.d = true;
        }
        catch (Throwable throwable) {
            this.d = true;
            d.a("[Create Effect Sound" + throwable);
            bg.a(true);
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
        if (!cx.A) {
            return;
        }
        if (this.a == null && !this.c.equals("")) {
            this.a(this.c);
        }
        ci.a().a(this);
    }

    public void d() {
        if (this.a != null) {
            try {
                this.a.stop();
                this.a.setMediaTime(0L);
            }
            catch (Throwable throwable) {
                this.a();
                d.a("[STOP EFFECT Music]: " + throwable);
            }
        }
    }

    public void c() {
        block7: {
            try {
                if (cx.D) {
                    this.a.setMediaTime(0L);
                    if (this.b != null) {
                        this.b.setMute(!cx.x);
                    }
                } else {
                    if (cx.V) {
                        cf.a().b();
                    }
                    this.a.setMediaTime(0L);
                    if (this.b != null) {
                        this.b.setMute(!cx.x);
                        this.b.setLevel(cx.y);
                    }
                    this.a.start();
                }
            }
            catch (Exception exception) {
                d.a("[Play Effect Sound" + exception);
                bg.a(true);
                if (cx.A) break block7;
                this.a();
            }
        }
    }

    public boolean e() {
        return this.e;
    }

    public void playerUpdate(Player player, String string, Object object) {
        if (string.equals("endOfMedia")) {
            try {
                if (cx.D) {
                    if (this.b != null) {
                        this.b.setLevel(cx.y);
                        this.b.setMute(true);
                    }
                } else {
                    if (this.b != null) {
                        this.b.setMute(!cx.x);
                        this.b.setLevel(cx.y);
                    }
                    if (cx.V) {
                        this.d();
                        cf.a().e();
                    }
                }
            }
            catch (Exception exception) {
                try {
                    this.a.setMediaTime(0L);
                }
                catch (Exception exception2) {
                    // empty catch block
                }
            }
            this.e = false;
        } else if (string.equals("started")) {
            this.e = true;
        } else if (string.equals("stopped")) {
            this.e = false;
        }
    }
}

