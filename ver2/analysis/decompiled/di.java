/*
 * Decompiled with CFR 0.152.
 */
public class di {
    Object[] a;
    private int b = 0;

    public di() {
    }

    public di(int n2) {
        this();
        this.a = new Object[n2];
    }

    public void a(Object object) {
        if (this.a == null) {
            this.a = new Object[10];
        }
        if (this.b >= this.a.length) {
            Object[] objectArray = new Object[this.a.length + 10];
            System.arraycopy(this.a, 0, objectArray, 0, this.a.length);
            this.a = objectArray;
            objectArray = null;
        }
        this.a[this.b] = object;
        ++this.b;
    }

    public Object a(int n2) {
        if (this.a == null) {
            return null;
        }
        if (n2 < 0 || n2 > this.b()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        Object object = this.a[n2];
        this.a[n2] = null;
        --this.b;
        if (n2 < this.a.length - 1) {
            System.arraycopy(this.a, n2 + 1, this.a, n2, this.b - n2);
        }
        return object;
    }

    public void a() {
        this.a = null;
        this.b = 0;
    }

    public void b(Object object) {
        if (this.a == null) {
            return;
        }
        int n2 = 0;
        while (n2 < this.b) {
            if (this.a[n2].equals(object)) {
                this.a(n2);
                break;
            }
            ++n2;
        }
    }

    public Object b(int n2) {
        if (this.a == null) {
            return null;
        }
        if (n2 >= this.b || n2 < 0) {
            return null;
        }
        return this.a[n2];
    }

    public int b() {
        return this.b;
    }

    public String toString() {
        String string = "";
        if (this.b() == 0) {
            string = "[Size]: 0";
        } else {
            int n2 = 0;
            while (n2 < this.b) {
                string = this.a[n2] != null ? String.valueOf(string) + "\n[Index]: " + n2 + " [Object]: " + this.a[n2].toString() : String.valueOf(string) + "\n[Index]: " + n2 + " [Object]: NULL";
                ++n2;
            }
        }
        return string;
    }
}

