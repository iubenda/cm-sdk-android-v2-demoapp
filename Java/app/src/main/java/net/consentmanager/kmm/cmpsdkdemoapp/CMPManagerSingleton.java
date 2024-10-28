package net.consentmanager.kmm.cmpsdkdemoapp;

import android.content.Context;
import android.util.Log;

import net.consentmanager.sdk.CmpManager;
import net.consentmanager.sdk.consentlayer.model.CmpConfig;
import net.consentmanager.sdk.common.callbacks.CmpLayerAppEventListenerInterface;

public class CMPManagerSingleton {
    private static volatile CmpManager instance;
    private static final Object lock = new Object();
    private static boolean isInitialized = false;

    private CMPManagerSingleton() {
        // Private constructor to prevent instantiation
    }

    public static CmpManager initialize(Context context, CmpConfig config, CmpLayerAppEventListenerInterface listener) {
        synchronized (lock) {
            if (!isInitialized) {
                instance = CmpManager.createInstance(context, config);
                instance.initialize(context, listener);
                isInitialized = true;
            }
            return instance;
        }
    }

    public static CmpManager getInstance() {
        if (!isInitialized) {
            throw new IllegalStateException("CmpManager not initialized. Call initialize() first.");
        }
        return instance;
    }

    public static void reset() {
        synchronized (lock) {
            instance = null;
            isInitialized = false;
        }
    }
}