/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.midp.events.EventQueue
 *  com.sun.midp.lcdui.DisplayContainer
 *  com.sun.midp.lcdui.ForegroundController
 *  com.sun.midp.lcdui.ForegroundEventListener
 *  com.sun.midp.lcdui.LCDUIEnvironment
 *  com.sun.midp.log.Logging
 *  com.sun.midp.main.CldcForegroundController
 *  com.sun.midp.main.CldcMIDletStateListener
 *  com.sun.midp.main.CldcMIDletSuiteLoader$SecurityTrusted
 *  com.sun.midp.main.Configuration
 *  com.sun.midp.main.MIDletControllerEventProducer
 *  com.sun.midp.midlet.MIDletEventListener
 *  com.sun.midp.midlet.MIDletStateHandler
 *  com.sun.midp.midlet.MIDletSuite
 *  com.sun.midp.midlet.MIDletSuiteExceptionListener
 *  com.sun.midp.midletsuite.MIDletSuiteImpl
 *  com.sun.midp.midletsuite.MIDletSuiteLockedException
 *  com.sun.midp.midletsuite.MIDletSuiteStorage
 *  com.sun.midp.midletsuite.SuiteContainerAdapter
 *  com.sun.midp.publickeystore.WebPublicKeyStore
 *  com.sun.midp.rms.RecordStoreRegistry
 *  com.sun.midp.rms.RmsEnvironment
 *  com.sun.midp.rms.SuiteContainer
 *  com.sun.midp.security.ImplicitlyTrustedClass
 *  com.sun.midp.security.SecurityInitializer
 *  com.sun.midp.security.SecurityToken
 */
package com.sun.midp.main;

import com.sun.midp.events.EventQueue;
import com.sun.midp.lcdui.DisplayContainer;
import com.sun.midp.lcdui.ForegroundController;
import com.sun.midp.lcdui.ForegroundEventListener;
import com.sun.midp.lcdui.LCDUIEnvironment;
import com.sun.midp.log.Logging;
import com.sun.midp.main.CldcForegroundController;
import com.sun.midp.main.CldcMIDletStateListener;
import com.sun.midp.main.CldcMIDletSuiteLoader;
import com.sun.midp.main.Configuration;
import com.sun.midp.main.MIDletControllerEventProducer;
import com.sun.midp.main.MIDletSuiteUtils;
import com.sun.midp.midlet.MIDletEventListener;
import com.sun.midp.midlet.MIDletStateHandler;
import com.sun.midp.midlet.MIDletSuite;
import com.sun.midp.midlet.MIDletSuiteExceptionListener;
import com.sun.midp.midletsuite.MIDletSuiteImpl;
import com.sun.midp.midletsuite.MIDletSuiteLockedException;
import com.sun.midp.midletsuite.MIDletSuiteStorage;
import com.sun.midp.midletsuite.SuiteContainerAdapter;
import com.sun.midp.publickeystore.WebPublicKeyStore;
import com.sun.midp.rms.RecordStoreRegistry;
import com.sun.midp.rms.RmsEnvironment;
import com.sun.midp.rms.SuiteContainer;
import com.sun.midp.security.ImplicitlyTrustedClass;
import com.sun.midp.security.SecurityInitializer;
import com.sun.midp.security.SecurityToken;

abstract class CldcMIDletSuiteLoader
implements MIDletSuiteExceptionListener {
    protected int isolateId;
    protected int amsIsolateId;
    protected int suiteId;
    protected int externalAppId;
    protected String midletClassName;
    protected String midletDisplayName;
    protected String[] args;
    protected static SecurityToken internalSecurityToken = SecurityInitializer.requestToken((ImplicitlyTrustedClass)new SecurityTrusted(null));
    protected EventQueue eventQueue;
    protected MIDletSuite midletSuite;
    protected ForegroundController foregroundController;
    protected DisplayContainer displayContainer;
    protected LCDUIEnvironment lcduiEnvironment;
    protected MIDletStateHandler midletStateHandler;
    protected MIDletControllerEventProducer midletControllerEventProducer;
    protected MIDletEventListener midletEventListener;
    protected ForegroundEventListener foregroundEventListener;

    CldcMIDletSuiteLoader() {
    }

    protected abstract void reportError(int var1, String var2);

    protected void reportError(int n) {
        this.reportError(n, null);
    }

    protected void setSuiteProperties() {
        if (this.args != null) {
            for (int i = 0; i < this.args.length; ++i) {
                if (this.args[i] == null) continue;
                this.midletSuite.setTempProperty(internalSecurityToken, "arg-" + i, this.args[i]);
            }
        }
    }

    protected void init() {
        this.isolateId = MIDletSuiteUtils.getIsolateId();
        this.amsIsolateId = MIDletSuiteUtils.getAmsIsolateId();
        MIDletSuiteUtils.vmBeginStartUp(this.isolateId);
        WebPublicKeyStore.initKeystoreLocation((SecurityToken)internalSecurityToken, (String)Configuration.getProperty((String)"com.sun.midp.publickeystore.WebPublicKeyStore"));
        SecurityInitializer.initSystem();
        this.eventQueue = EventQueue.getEventQueue((SecurityToken)internalSecurityToken);
    }

    protected void createSuiteEnvironment() {
        this.midletControllerEventProducer = new MIDletControllerEventProducer(this.eventQueue, this.amsIsolateId, this.isolateId);
        this.foregroundController = new CldcForegroundController(this.midletControllerEventProducer);
        this.lcduiEnvironment = new LCDUIEnvironment(internalSecurityToken, this.eventQueue, this.isolateId, this.foregroundController);
        if (this.lcduiEnvironment == null) {
            throw new RuntimeException("Suite environment not complete.");
        }
        this.displayContainer = this.lcduiEnvironment.getDisplayContainer();
        this.foregroundEventListener = new ForegroundEventListener(this.eventQueue, this.displayContainer);
        this.midletStateHandler = MIDletStateHandler.getMidletStateHandler();
        this.midletStateHandler.initMIDletStateHandler(internalSecurityToken, new CldcMIDletStateListener(internalSecurityToken, this.displayContainer, this.midletControllerEventProducer));
        this.midletEventListener = new MIDletEventListener(internalSecurityToken, this.midletStateHandler, this.eventQueue);
        SuiteContainerAdapter suiteContainerAdapter = new SuiteContainerAdapter(MIDletSuiteStorage.getMIDletSuiteStorage((SecurityToken)internalSecurityToken));
        RmsEnvironment.init((SecurityToken)internalSecurityToken, (SuiteContainer)suiteContainerAdapter);
    }

    protected void done() {
        RecordStoreRegistry.shutdown((SecurityToken)internalSecurityToken);
        this.eventQueue.shutdown();
    }

    protected void initSuiteEnvironment() {
        this.lcduiEnvironment.setTrustedState(this.midletSuite.isTrusted());
    }

    protected void startSuite() throws Exception {
        MIDletSuiteUtils.vmEndStartUp(this.isolateId);
        this.midletStateHandler.startSuite((MIDletSuiteExceptionListener)this, this.midletSuite, this.externalAppId, this.midletClassName);
    }

    protected void closeSuite() {
        if (this.midletSuite != null) {
            this.midletSuite.close();
        }
        if (this.lcduiEnvironment != null) {
            this.lcduiEnvironment.shutDown();
        }
    }

    protected void checkForShutdown() {
    }

    protected abstract void exitLoader();

    protected MIDletSuite createMIDletSuite() throws Exception {
        MIDletSuiteStorage mIDletSuiteStorage = MIDletSuiteStorage.getMIDletSuiteStorage((SecurityToken)internalSecurityToken);
        MIDletSuiteImpl mIDletSuiteImpl = mIDletSuiteStorage.getMIDletSuite(this.suiteId, false);
        Logging.initLogSettings((int)this.suiteId);
        return mIDletSuiteImpl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void runMIDletSuite() {
        this.init();
        try {
            this.createSuiteEnvironment();
            if (this.foregroundController == null || this.displayContainer == null || this.midletStateHandler == null) {
                throw new RuntimeException("Suite environment not complete.");
            }
            this.midletSuite = this.createMIDletSuite();
            if (this.midletSuite == null) {
                this.reportError(-2);
                return;
            }
            this.initSuiteEnvironment();
            this.setSuiteProperties();
            this.startSuite();
            this.checkForShutdown();
        }
        catch (Throwable throwable) {
            this.handleException(throwable);
        }
        finally {
            this.closeSuite();
            this.done();
            this.exitLoader();
        }
    }

    protected static int getErrorCode(Throwable throwable) {
        if (throwable instanceof ClassNotFoundException) {
            return -3;
        }
        if (throwable instanceof InstantiationException) {
            return -4;
        }
        if (throwable instanceof IllegalAccessException) {
            return -5;
        }
        if (throwable instanceof OutOfMemoryError) {
            return -6;
        }
        if (throwable instanceof MIDletSuiteLockedException) {
            return -14;
        }
        return -1;
    }

    protected static int getErrorMessageId(int n) {
        switch (n) {
            case -12: {
                return 27;
            }
            case -2: {
                return 22;
            }
            case -3: {
                return 23;
            }
            case -4: {
                return 24;
            }
            case -5: {
                return 24;
            }
            case -6: {
                return 25;
            }
        }
        return 26;
    }

    public void handleException(Throwable throwable) {
        throwable.printStackTrace();
        int n = CldcMIDletSuiteLoader.getErrorCode(throwable);
        this.reportError(n, throwable.getMessage());
    }
}
