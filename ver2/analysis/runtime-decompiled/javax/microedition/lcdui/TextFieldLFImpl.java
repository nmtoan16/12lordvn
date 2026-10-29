/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.midp.lcdui.TextPolicy
 *  javax.microedition.lcdui.DisplayableLFImpl
 *  javax.microedition.lcdui.Item
 *  javax.microedition.lcdui.ItemLFImpl
 *  javax.microedition.lcdui.TextBox
 *  javax.microedition.lcdui.TextField
 *  javax.microedition.lcdui.TextFieldLF
 */
package javax.microedition.lcdui;

import com.sun.midp.lcdui.DynamicCharacterArray;
import com.sun.midp.lcdui.TextPolicy;
import javax.microedition.lcdui.DisplayableLFImpl;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemLFImpl;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;
import javax.microedition.lcdui.TextFieldLF;

class TextFieldLFImpl
extends ItemLFImpl
implements TextFieldLF {
    TextField tf;

    TextFieldLFImpl(TextField textField) {
        super((Item)textField);
        this.tf = textField;
    }

    public boolean lUpdateContents() {
        if (this.nativeId == 0) {
            return false;
        }
        return this.getString0(this.nativeId, this.tf.buffer);
    }

    public int lGetPreferredWidth(int n) {
        if (this.tf.owner instanceof TextBox) {
            return ((DisplayableLFImpl)this.tf.owner.getLF()).width;
        }
        return super.lGetPreferredWidth(n);
    }

    public int lGetPreferredHeight(int n) {
        int n2 = super.lGetPreferredHeight(n);
        if (this.tf.owner instanceof TextBox && ((DisplayableLFImpl)this.tf.owner.getLF()).height > n2) {
            n2 = ((DisplayableLFImpl)this.tf.owner.getLF()).height;
        }
        return n2;
    }

    private native boolean getString0(int var1, DynamicCharacterArray var2);

    private native void setString0(int var1, DynamicCharacterArray var2);

    public void lSetChars() {
        if (this.nativeId != 0) {
            this.setString0(this.nativeId, this.tf.buffer);
        }
        this.lRequestInvalidate(true, true);
    }

    public void lInsert(char[] cArray, int n, int n2, int n3) {
        this.lSetChars();
    }

    public void lDelete(int n, int n2) {
        this.lSetChars();
    }

    public void lSetMaxSize(int n) {
        if (this.nativeId != 0) {
            this.setMaxSize0(this.nativeId, n);
        }
        this.lRequestInvalidate(true, true);
    }

    private native void setMaxSize0(int var1, int var2);

    public int lGetCaretPosition() {
        return this.nativeId != 0 ? this.getCaretPosition0(this.nativeId) : this.tf.buffer.length();
    }

    private native int getCaretPosition0(int var1);

    public void lSetConstraints() {
        if (this.nativeId != 0) {
            this.setConstraints0(this.nativeId, this.tf.constraints);
        }
    }

    private native void setConstraints0(int var1, int var2);

    public boolean lValidate(DynamicCharacterArray dynamicCharacterArray, int n) {
        return TextPolicy.isValidString((DynamicCharacterArray)dynamicCharacterArray, (int)n);
    }

    public void lSetInitialInputMode(String string) {
    }

    public void itemDeleted() {
        this.uCallTraverseOut();
    }

    boolean uCallPeerStateChanged(int n) {
        return true;
    }

    boolean equateNLA() {
        if (super.equateNLA()) {
            return true;
        }
        return (this.tf.layout & 0x4000) != 16384;
    }

    boolean equateNLB() {
        if (super.equateNLB()) {
            return true;
        }
        return (this.tf.layout & 0x4000) != 16384;
    }

    void createNativeResource(int n) {
        this.nativeId = this.createNativeResource0(n, this.tf.label, this.tf.owner instanceof TextBox ? -1 : this.tf.layout, this.tf.buffer, this.tf.constraints, this.tf.initialInputMode);
    }

    private native int createNativeResource0(int var1, String var2, int var3, DynamicCharacterArray var4, int var5, String var6);

    void lHideNativeResource() {
        this.lUpdateContents();
        super.lHideNativeResource();
    }
}
