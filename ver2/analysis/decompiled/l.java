/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.rms.RecordStore
 */
import javax.microedition.rms.RecordStore;

public class l {
    public static RecordStore a(String string, boolean bl2) {
        try {
            return RecordStore.openRecordStore((String)string, (boolean)bl2);
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

    public static void a(RecordStore recordStore) {
        try {
            recordStore.closeRecordStore();
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public static byte[] a(RecordStore recordStore, int n2) {
        byte[] byArray = null;
        try {
            byArray = recordStore.getRecord(n2);
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
            byArray = null;
        }
        return byArray;
    }

    public static int a(RecordStore recordStore, int n2, byte[] byArray, int n3, int n4) {
        try {
            recordStore.setRecord(n2, byArray, n3, n4);
            return n2;
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
            return -1;
        }
    }

    public static int a(RecordStore recordStore, int n2, byte[] byArray) {
        return l.a(recordStore, n2, byArray, 0, byArray.length);
    }

    public static int a(RecordStore recordStore, byte[] byArray, int n2, int n3) {
        try {
            return recordStore.addRecord(byArray, n2, n3);
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
            return -1;
        }
    }

    public static int a(RecordStore recordStore, byte[] byArray) {
        return l.a(recordStore, byArray, 0, byArray.length);
    }

    public static int b(RecordStore recordStore) {
        try {
            return recordStore.getNumRecords();
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
            return 0;
        }
    }

    public static void a(String string) {
        try {
            RecordStore.deleteRecordStore((String)string);
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}

