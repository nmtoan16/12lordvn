/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Command
 *  javax.microedition.lcdui.CommandListener
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.lcdui.TextBox
 */
package com.mic.smsgame.suquan;

import com.mg.smsgame.main.MGMIDlet;
import com.mic.smsgame.suquan.ac;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.TextBox;

class r
implements CommandListener {
    final ac a;
    private final TextBox b;

    r(ac ac2, TextBox textBox) {
        this.a = ac2;
        this.b = textBox;
    }

    public void commandAction(Command command, Displayable displayable) {
        if (command.getLabel().equals("G\u1eedi")) {
            if (this.b.getString() == null || "".equals(this.b.getString())) {
                ae ae2 = cz.a("Ch\u00fa \u00fd", "B\u1ea1n ph\u1ea3i nh\u1eadp v\u00e0o s\u1ed1 \u0111i\u1ec7n tho\u1ea1i ng\u01b0\u1eddi nh\u1eadn!", "\u0110\u00f3ng", 5, 1);
                ae2.a(this.a);
                cz.d().a(ae2, false);
            } else {
                an.a(this.b.getString());
            }
            MGMIDlet.f().a.setCurrent((Displayable)cz.d());
        } else {
            MGMIDlet.f().a.setCurrent((Displayable)cz.d());
        }
    }
}

