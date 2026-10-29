/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.midp.content.CHManager
 *  com.sun.midp.i18n.Resource
 *  com.sun.midp.io.j2me.push.PushRegistryInternal
 *  com.sun.midp.jsr.JSRInitializer
 *  com.sun.midp.lcdui.ForegroundEventProducer
 *  com.sun.midp.main.AmsUtil
 *  com.sun.midp.main.CldcMIDletSuiteLoader
 *  com.sun.midp.main.CommandState
 *  com.sun.midp.main.Configuration
 *  com.sun.midp.main.MIDletControllerEventProducer
 *  com.sun.midp.main.MIDletProxy
 *  com.sun.midp.main.MIDletProxyList
 *  com.sun.midp.main.MIDletSuiteUtils
 *  com.sun.midp.midlet.MIDletEventProducer
 *  com.sun.midp.security.SecurityToken
 */
package com.sun.midp.main;

import com.sun.midp.content.CHManager;
import com.sun.midp.i18n.Resource;
import com.sun.midp.io.j2me.push.PushRegistryInternal;
import com.sun.midp.jsr.JSRInitializer;
import com.sun.midp.lcdui.ForegroundEventProducer;
import com.sun.midp.main.AmsUtil;
import com.sun.midp.main.CldcMIDletSuiteLoader;
import com.sun.midp.main.CommandState;
import com.sun.midp.main.Configuration;
import com.sun.midp.main.MIDletControllerEventProducer;
import com.sun.midp.main.MIDletProxy;
import com.sun.midp.main.MIDletProxyList;
import com.sun.midp.main.MIDletSuiteUtils;
import com.sun.midp.midlet.MIDletEventProducer;
import com.sun.midp.security.SecurityToken;

public class MIDletSuiteLoader
extends CldcMIDletSuiteLoader {
    protected CommandState state = CommandState.getCommandState();
    protected int disableAlerts = -1;
    protected MIDletEventProducer midletEventProducer;
    protected static ForegroundEventProducer foregroundEventProducer;
    protected MIDletProxyList midletProxyList;

    protected void init() {
        MIDletSuiteUtils.registerAmsIsolateId();
        super.init();
    }

    protected void createSuiteEnvironment() {
        super.createSuiteEnvironment();
        this.midletEventProducer = new MIDletEventProducer(this.eventQueue);
        foregroundEventProducer = new ForegroundEventProducer(this.eventQueue);
        this.midletProxyList = new MIDletProxyList(this.eventQueue);
    }

    protected void initGlobalSystems() {
        JSRInitializer.init();
        PushRegistryInternal.startListening((SecurityToken)internalSecurityToken);
        CHManager.getManager((SecurityToken)internalSecurityToken).init(this.midletProxyList, this.eventQueue);
    }

    protected void initSuiteEnvironment() {
        super.initSuiteEnvironment();
        AmsUtil.initClass((MIDletProxyList)this.midletProxyList, (MIDletControllerEventProducer)this.midletControllerEventProducer);
        MIDletProxy.initClass((ForegroundEventProducer)foregroundEventProducer, (MIDletEventProducer)this.midletEventProducer);
        MIDletProxyList.initClass((MIDletProxyList)this.midletProxyList);
        this.initGlobalSystems();
    }

    protected void checkForShutdown() {
        if (MIDletProxyList.shutdownInProgress()) {
            this.state.status = 1;
            this.midletProxyList.waitForShutdownToComplete();
        } else {
            this.state.status = 0;
        }
    }

    protected void closeSuite() {
    }

    protected void done() {
        super.done();
        this.state.suiteId = 0;
        this.state.midletClassName = null;
        if (this.state.status != 1) {
            if (MIDletSuiteUtils.lastMidletSuiteToRun != 0) {
                this.state.lastSuiteId = MIDletSuiteUtils.lastMidletSuiteToRun;
                this.state.lastMidletClassName = MIDletSuiteUtils.lastMidletToRun;
                this.state.lastArg0 = MIDletSuiteUtils.arg0ForLastMidlet;
                this.state.lastArg1 = MIDletSuiteUtils.arg1ForLastMidlet;
            }
            if (MIDletSuiteUtils.nextMidletSuiteToRun != 0) {
                this.state.suiteId = MIDletSuiteUtils.nextMidletSuiteToRun;
                this.state.midletClassName = MIDletSuiteUtils.nextMidletToRun;
                this.state.arg0 = MIDletSuiteUtils.arg0ForNextMidlet;
                this.state.arg1 = MIDletSuiteUtils.arg1ForNextMidlet;
                this.state.arg2 = MIDletSuiteUtils.arg2ForNextMidlet;
                this.state.runtimeInfo.memoryReserved = MIDletSuiteUtils.memoryReserved;
                this.state.runtimeInfo.memoryTotal = MIDletSuiteUtils.memoryTotal;
                this.state.runtimeInfo.priority = MIDletSuiteUtils.priority;
                this.state.runtimeInfo.profileName = MIDletSuiteUtils.profileName;
                this.state.isDebugMode = MIDletSuiteUtils.isDebugMode;
            } else if (this.state.lastSuiteId != 0) {
                this.state.suiteId = this.state.lastSuiteId;
                this.state.midletClassName = this.state.lastMidletClassName;
                this.state.arg0 = this.state.lastArg0;
                this.state.arg1 = this.state.lastArg1;
                this.state.lastSuiteId = 0;
                this.state.lastMidletClassName = null;
                this.state.lastArg0 = null;
                this.state.lastArg1 = null;
                this.state.status = 0;
            }
        }
        this.state.save();
        JSRInitializer.cleanup();
    }

    protected void exitLoader() {
        CommandState.exitInternal((int)2001);
    }

    protected void displayException(SecurityToken securityToken, String string) {
        MIDletSuiteUtils.displayException((SecurityToken)internalSecurityToken, (String)string);
    }

    protected void reportError(int n, String string) {
        this.state.status = n;
        if (this.disableAlerts < 0) {
            this.disableAlerts = Configuration.getIntProperty((String)"DisableStartupErrorAlert", (int)0);
        }
        int n2 = MIDletSuiteLoader.getErrorMessageId((int)n);
        String string2 = Resource.getString((int)n2);
        if (string != null) {
            string2 = string2 + "\n\n" + string;
        }
        if (this.disableAlerts == 0) {
            this.displayException(internalSecurityToken, string2);
        }
    }

    public static void main(String[] stringArray) {
        try {
            MIDletSuiteLoader mIDletSuiteLoader = new MIDletSuiteLoader();
            mIDletSuiteLoader.runMIDletSuite();
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private MIDletSuiteLoader() {
        this.externalAppId = 0;
        this.midletDisplayName = null;
        this.args = new String[]{this.state.arg0, this.state.arg1, this.state.arg2};
        this.suiteId = this.state.suiteId;
        this.midletClassName = this.state.midletClassName;
        this.state.arg0 = null;
        this.state.arg1 = null;
        this.state.arg2 = null;
    }
}
