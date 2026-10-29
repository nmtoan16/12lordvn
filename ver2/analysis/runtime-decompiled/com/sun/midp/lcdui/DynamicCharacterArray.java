/*
 * Decompiled with CFR 0.152.
 */
package com.sun.midp.lcdui;

public class DynamicCharacterArray {
    protected char[] buffer;
    protected int length;

    public DynamicCharacterArray(int n) {
        this(null, n);
    }

    public DynamicCharacterArray(String string) {
        this(string.toCharArray());
    }

    public DynamicCharacterArray(char[] cArray) {
        this(cArray, cArray.length);
    }

    public DynamicCharacterArray(char[] cArray, int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        if (cArray != null) {
            if (cArray.length > n) {
                throw new IllegalArgumentException();
            }
            this.length = cArray.length;
            this.buffer = new char[n];
            System.arraycopy(cArray, 0, this.buffer, 0, this.length);
        } else {
            this.buffer = new char[n];
        }
    }

    public int insert(char[] cArray, int n, int n2, int n3) {
        if (n3 < 0) {
            n3 = 0;
        } else if (n3 > this.length) {
            n3 = this.length;
        }
        if (n < 0 || n > cArray.length || n2 < 0 || n2 > cArray.length || n + n2 < 0 || n + n2 > cArray.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (this.length + n2 > this.buffer.length) {
            throw new IllegalArgumentException();
        }
        if (cArray.length != 0) {
            System.arraycopy(this.buffer, n3, this.buffer, n3 + n2, this.length - n3);
            System.arraycopy(cArray, n, this.buffer, n3, n2);
            this.length += n2;
        }
        return n3;
    }

    public int insert(int n, char c) {
        char[] cArray = new char[]{c};
        return this.insert(cArray, 0, 1, n);
    }

    public int insert(int n, String string) {
        return this.insert(string.toCharArray(), 0, string.length(), n);
    }

    public void append(char c) {
        this.insert(this.length, c);
    }

    public void set(char[] cArray, int n, int n2) {
        if (cArray == null) {
            this.length = 0;
            return;
        }
        if (n < 0 || n > cArray.length || n2 < 0 || n2 > cArray.length || n + n2 < 0 || n + n2 > cArray.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (n2 > this.buffer.length) {
            throw new IllegalArgumentException();
        }
        System.arraycopy(cArray, n, this.buffer, 0, n2);
        this.length = n2;
    }

    public void get(char[] cArray) {
        this.getChars(0, this.buffer.length, cArray, 0);
    }

    public void getChars(int n, int n2, char[] cArray, int n3) {
        System.arraycopy(this.buffer, n, cArray, n3, n2);
    }

    public char[] toCharArray() {
        char[] cArray = new char[this.length];
        System.arraycopy(this.buffer, 0, cArray, 0, cArray.length);
        return cArray;
    }

    public void delete(int n, int n2) {
        if (n < 0 || n2 < 0 || n + n2 < 0 || n + n2 > this.length) {
            throw new StringIndexOutOfBoundsException();
        }
        if (n + n2 < this.length) {
            System.arraycopy(this.buffer, n + n2, this.buffer, n, this.length - (n + n2));
        }
        this.length -= n2;
    }

    public void setCapacity(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        if (this.buffer.length == n) {
            return;
        }
        if (this.length > n) {
            this.length = n;
        }
        char[] cArray = new char[n];
        System.arraycopy(this.buffer, 0, cArray, 0, this.length);
        this.buffer = cArray;
    }

    public int capacity() {
        return this.buffer.length;
    }

    public int length() {
        return this.length;
    }

    public char charAt(int n) {
        if (n < 0 || n > this.length) {
            throw new IndexOutOfBoundsException();
        }
        return this.buffer[n];
    }

    public void setCharAt(int n, char c) {
        if (n < 0 || n > this.length) {
            throw new IndexOutOfBoundsException();
        }
        this.buffer[n] = c;
    }

    public String toString() {
        return String.valueOf(this.buffer, 0, this.length);
    }
}
