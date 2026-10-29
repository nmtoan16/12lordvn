/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Command
 *  javax.microedition.lcdui.CommandListener
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.lcdui.Form
 */
import com.mg.smsgame.main.MGMIDlet;
import java.util.Date;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;

public class d
extends Form
implements CommandListener {
    private static d a;
    private int b = 255;
    private int c = 0;

    protected d() {
        super("All log");
        this.setCommandListener(this);
        this.addCommand(new Command("Back", 2, 1));
        this.addCommand(new Command("Clear", 4, 1));
        this.append("Start at " + new Date(System.currentTimeMillis()).toString());
    }

    public void commandAction(Command command, Displayable displayable) {
        if (command.getLabel().equals("Back")) {
            MGMIDlet.f().a.setCurrent((Displayable)cz.d());
        } else if (command.getLabel().equals("Clear")) {
            this.deleteAll();
            this.c = 0;
            this.append("Start at " + new Date(System.currentTimeMillis()).toString());
        }
    }

    public static d a() {
        if (a == null) {
            a = new d();
        }
        return a;
    }

    private void b(String string) {
        if (!cx.F) {
            return;
        }
        if ((this.b & 1) == 0) {
            return;
        }
        if (this.c >= 50) {
            d.a().delete(0);
        }
        this.append(string);
        System.out.println(string);
        ++this.c;
    }

    public static void a(String string) {
        d.a().b(string);
    }

    private void b(Throwable throwable) {
        if (!cx.F) {
            return;
        }
        if ((this.b & 2) == 0) {
            return;
        }
        if (this.c >= 50) {
            d.a().delete(0);
        }
        throwable.printStackTrace();
        this.append(throwable.toString());
        ++this.c;
    }

    public static void a(Throwable throwable) {
        d.a().b(throwable);
    }
}

