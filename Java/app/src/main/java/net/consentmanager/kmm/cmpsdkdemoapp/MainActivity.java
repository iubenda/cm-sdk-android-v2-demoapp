package net.consentmanager.kmm.cmpsdkdemoapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;

import net.consentmanager.sdk.CmpManager;
import net.consentmanager.sdk.common.CmpError;
import net.consentmanager.sdk.common.callbacks.CmpLayerAppEventListenerInterface;
import net.consentmanager.sdk.consentlayer.model.CmpConfig;
import net.consentmanager.sdk.consentlayer.model.CmpUIConfig;
import net.consentmanager.sdk.consentlayer.model.CmpUIStrategy;

public class MainActivity extends ComponentActivity implements CmpLayerAppEventListenerInterface {
    private CmpManager cmpManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configure CMP
        CmpConfig.INSTANCE.setId("Your Code-ID goes here"); // TODO: Replace with your Code-ID
        CmpConfig.INSTANCE.setDomain("delivery.consentmanager.net");
        CmpConfig.INSTANCE.setLanguage("EN");
        CmpConfig.INSTANCE.setAppName("CMDemoAppSDKv2Java");

        // Set UI Strategy
        CmpUIConfig.INSTANCE.setUiStrategy(CmpUIStrategy.POPUP);

        setContentView(new View(this));

        // Initialize CMP Manager
        cmpManager = CMPManagerSingleton.initialize(this, CmpConfig.INSTANCE, this);

        setContentView(R.layout.activity_cmp_demo);
        showCMPDemoScreen();
    }

    private void showCMPDemoScreen() {
        Intent intent = new Intent(this, CMPDemoActivity.class);
        startActivity(intent);
        //finish(); // Uncomment if you want to close MainActivity
    }

    @Override
    public void onOpenRequest() {
        Log.d("CMPLayer", "onOpenRequest");
    }

    @Override
    public void onCloseRequest() {
        Log.d("CMPLayer", "onCloseRequest");
    }

    @Override
    public void onError(@NonNull CmpError error, @NonNull String message) {
        // Handle error event
        Log.e("CMPLayer", "Error: " + error + ", Message: " + message);
    }
}
