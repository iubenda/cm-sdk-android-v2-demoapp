package net.consentmanager.kmm.cmpsdkdemoapp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.ComponentActivity;

import net.consentmanager.sdk.CmpManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CMPDemoActivity extends ComponentActivity {

    private static final String TAG = "CMPDemoActivity";
    private CmpManager cmpManager;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmp_demo);

        mainHandler = new Handler(Looper.getMainLooper());

        try {
            cmpManager = CMPManagerSingleton.getInstance();
            Log.d(TAG, "CmpManager instance retrieved successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error getting CmpManager instance: " + e.getMessage());
        }

        setupButtons();
    }

    private void setupButtons() {
        Log.d(TAG, "Setting up buttons");
        setupButton(R.id.btnHasConsent, v -> checkHasConsent());
        setupButton(R.id.btnHasPurpose, v -> checkHasPurpose());
        setupButton(R.id.btnHasVendor, v -> checkHasVendor());
        setupButton(R.id.btnGetCmpString, v -> getCmpString());
        setupButton(R.id.btnGetAllPurposes, v -> getAllPurposes());
        setupButton(R.id.btnGetEnabledPurposes, v -> getEnabledPurposes());
        setupButton(R.id.btnGetDisabledPurposes, v -> getDisabledPurposes());
        setupButton(R.id.btnGetAllVendors, v -> getAllVendors());
        setupButton(R.id.btnGetEnabledVendors, v -> getEnabledVendors());
        setupButton(R.id.btnGetDisabledVendors, v -> getDisabledVendors());
        setupButton(R.id.btnCheckAndOpenConsentLayer, v -> checkAndOpenConsentLayer());
        setupButton(R.id.btnCheckConsentRequired, v -> checkConsentRequired());
        setupButton(R.id.btnEnableVendorList, v -> enableVendorList());
        setupButton(R.id.btnDisableVendorList, v -> disableVendorList());
        setupButton(R.id.btnEnablePurposeList, v -> enablePurposeList());
        setupButton(R.id.btnDisablePurposeList, v -> disablePurposeList());
        setupButton(R.id.btnRejectAll, v -> rejectAll());
        setupButton(R.id.btnAcceptAll, v -> acceptAll());
        setupButton(R.id.btnReset, v -> reset());
        setupButton(R.id.btnOpenConsentLayer, v -> openConsentLayer());
        setupButton(R.id.btnImportCmpString, v -> importCmpString());
        setupButton(R.id.btnExportTcfStrings, v -> exportTCFStrings());
    }

    private void setupButton(int buttonId, View.OnClickListener listener) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(listener);
    }

    private void checkHasConsent() {
        Log.d(TAG, "checkHasConsent method called");
        try {
            boolean hasConsent = cmpManager.hasConsent();
            Log.d(TAG, "Has consent result: " + hasConsent);
            showToast("Has Consent: " + hasConsent);
        } catch (Exception e) {
            Log.e(TAG, "Error checking consent: " + e.getMessage());
            showToast("Error checking consent: " + e.getMessage());
        }
    }

    private void checkHasPurpose() {
        boolean hasPurpose = cmpManager.hasPurpose("c53", false);
        showToast("Has Purpose c53: " + hasPurpose);
    }

    private void checkHasVendor() {
        boolean hasVendor = cmpManager.hasVendor("s2789", false);
        showToast("Has Vendor s2789: " + hasVendor);
    }

    private void getCmpString() {
        String cmpString = cmpManager.exportCmpString();
        showToast("CMP String: " + cmpString);
    }

    private void getAllPurposes() {
        String allPurposes = Arrays.toString(cmpManager.getAllPurposes().toCharArray());
        showToast("All Purposes: " + allPurposes);
    }

    private void getEnabledPurposes() {
        String enabledPurposes = Arrays.toString(cmpManager.getEnabledPurposes().toCharArray());
        showToast("Enabled Purposes: " + enabledPurposes);
    }

    private void getDisabledPurposes() {
        String disabledPurposes = Arrays.toString(cmpManager.getDisabledPurposes().toArray());
        showToast("Disabled Purposes: " + disabledPurposes);
    }

    private void getAllVendors() {
        String allVendors = Arrays.toString(cmpManager.getAllVendors().toCharArray());
        showToast("All Vendors: " + allVendors);
    }

    private void getEnabledVendors() {
        String enabledVendors = Arrays.toString(cmpManager.getEnabledVendors().toCharArray());
        showToast("Enabled Vendors: " + enabledVendors);
    }

    private void getDisabledVendors() {
        String disabledVendors = Arrays.toString(cmpManager.getDisabledVendors().toArray());
        showToast("Disabled Vendors: " + disabledVendors);
    }

    private void reset() {
        cmpManager.reset();
        showToast("Consent data reset");
    }

    private void checkAndOpenConsentLayer() {
        cmpManager.openConsentLayerOnCheck(this);
    }

    private void checkConsentRequired() {
        cmpManager.checkConsentIsRequired(
                needsConsent -> showToast("Needs Consent: " + needsConsent),
                true
        );
    }

    private void enableVendorList() {
        cmpManager.enableVendorList(
                Arrays.asList("s2790", "s2791"),
                () -> showToast("Vendors Enabled")
        );
    }

    private void disableVendorList() {
        cmpManager.disableVendorList(
                Arrays.asList("s2790", "s2791"),
                () -> showToast("Vendors Disabled")
        );
    }

    private void enablePurposeList() {
        cmpManager.enablePurposeList(
                Arrays.asList("c52", "c53"),
                true,
                () -> showToast("Purposes enabled")
        );
    }

    private void disablePurposeList() {
        cmpManager.disablePurposeList(
                Arrays.asList("c52", "c53"),
                true,
                () -> showToast("Purposes disabled")
        );
    }

    private void rejectAll() {
        cmpManager.rejectAll(() -> showToast("All consents rejected"));
    }

    private void acceptAll() {
        cmpManager.acceptAll(() -> showToast("All consents accepted"));
    }

    private void openConsentLayer() {
        cmpManager.openConsentLayer(this);
    }

    private void importCmpString() {
        cmpManager.importCmpString(
                "Q1FERkg3QVFERkg3QUFmR01CSVRCQkVnQUFBQUFBQUFBQWlnQUFBQUFBQUEjXzUxXzUyXzUzXzU0XzU1XzU2XyNfczI3ODlfczI3OTBfczI3OTFfczI2OTdfczk3MV9VXyMxLS0tIw",
                (success, message) -> showToast(success ? "Imported successfully" : "Import failed: " + message)
        );
    }

    private void exportTCFStrings() {
        try {
            String allEntries = PreferenceManager
                    .getDefaultSharedPreferences(this)
                    .getAll()
                    .entrySet()
                    .stream()
                    .map(entry -> entry.getKey() + ": " + entry.getValue())
                    .collect(java.util.stream.Collectors.joining("\n"));

            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("TCF Strings", allEntries);
            clipboard.setPrimaryClip(clip);
            showToast("TCF strings exported to clipboard");
        } catch (Exception e) {
            showToast("Failed to copy to clipboard: " + e.getMessage());
        }
    }

    private void showToast(String message) {
        Log.d(TAG, "Showing toast: " + message);
        mainHandler.post(() -> Toast.makeText(CMPDemoActivity.this, message, Toast.LENGTH_SHORT).show());
    }
}