package net.consentmanager.kmm.cmpsdkdemoapp

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.preference.PreferenceManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import net.consentmanager.sdk.CmpManager

@Composable
fun CMPDemoScreen(cmpManager: CmpManager) {
    var toastMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current  // Add this line to get the context

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "CMP Manager Methods",
                style = MaterialTheme.typography.headlineMedium
            )

            DemoButton(
                text = "Has User Choice?",
                onClick = {
                    val hasConsent = cmpManager.hasConsent()
                    toastMessage = "Has Consent: $hasConsent"
                }
            )

            DemoButton(
                text = "Has Purpose ID c53?",
                onClick = {
                    val hasPurpose = cmpManager.hasPurpose("c53")
                    toastMessage = "Has Purpose: $hasPurpose"
                }
            )

            DemoButton(
                text = "Has Vendor ID s2789?",
                onClick = {
                    val hasVendor = cmpManager.hasVendor("s2789")
                    toastMessage = "Has Vendor: $hasVendor"
                }
            )

            DemoButton(
                text = "Get CMP String",
                onClick = {
                    val cmpString = cmpManager.exportCmpString()
                    toastMessage = "CMP String: $cmpString"
                }
            )

            DemoButton(
                text = "Get All Purposes",
                onClick = {
                    val allPurposes = cmpManager.getAllPurposes()
                    toastMessage = "All Purposes: $allPurposes"
                }
            )

            DemoButton(
                text = "Get Enabled Purposes",
                onClick = {
                    val enabledPurposes = cmpManager.getEnabledPurposes()
                    toastMessage = "Enabled Purposes: $enabledPurposes"
                }
            )

            DemoButton(
                text = "Get Disabled Purposes",
                onClick = {
                    val disabledPurposes = cmpManager.getDisabledPurposes()
                    toastMessage = "Disabled Purposes: ${disabledPurposes.joinToString(", ")}"
                }
            )

            DemoButton(
                text = "Get All Vendors",
                onClick = {
                    val allVendors = cmpManager.getAllVendors()
                    toastMessage = "All Vendors: $allVendors"
                }
            )

            DemoButton(
                text = "Get Enabled Vendors",
                onClick = {
                    val enabledVendors = cmpManager.getEnabledVendors()
                    toastMessage = "Enabled Vendors: $enabledVendors"
                }
            )

            DemoButton(
                text = "Get Disabled Vendors",
                onClick = {
                    val disabledVendors = cmpManager.getDisabledVendors()
                    toastMessage = "Disabled Vendors: ${disabledVendors.joinToString(", ")}"
                }
            )

            DemoButton(
                text = "Check and Open Consent Layer",
                onClick = {
                    cmpManager.openConsentLayerOnCheck(context)
                }
            )

            DemoButton(
                text = "Check Consent Required",
                onClick = {         cmpManager.checkConsentIsRequired(
                    onCheckIsConsentRequiredCallback = { needsConsent ->
                        toastMessage = "Needs Consent: $needsConsent"
                    },
                    isCached = true
                )
                }
            )

            DemoButton(
                text = "Enable Vendors s2790 and s2791",
                onClick = {
                    cmpManager.enableVendorList(
                        vendors = listOf("s2790", "s2791"),
                        onConsentReceivedCallback = {
                            toastMessage = "Vendors Enabled"
                        }
                    )
                }
            )

            DemoButton(
                text = "Disable Vendors s2790 and s2791",
                onClick = {
                    cmpManager.disableVendorList(
                        vendors = listOf("s2790", "s2791"),
                        onConsentReceivedCallback = {
                            toastMessage = "Vendors Enabled"
                        }
                    )
                }
            )

            DemoButton(
                text = "Enable Purposes c52 and c53",
                onClick = {
                    cmpManager.enablePurposeList(
                        purposes = listOf("c52", "c53"),
                        onConsentReceivedCallback = {
                            toastMessage = "Vendors Enabled"
                        }
                    )
                }
            )

            DemoButton(
                text = "Disable Purposes c52 and c53",
                onClick = {
                    cmpManager.disablePurposeList(
                        purposes = listOf("c52", "c53"),
                        onConsentReceivedCallback = {
                            toastMessage = "Vendors Enabled"
                        }
                    )
                }
            )

            DemoButton(
                text = "Reject All",
                onClick = {
                    cmpManager.rejectAll {
                        toastMessage = "All consents rejected"
                    }
                }
            )

            DemoButton(
                text = "Accept All",
                onClick = {
                    cmpManager.acceptAll {
                        toastMessage = "All consents accepted"
                    }
                }
            )

            DemoButton(
                text = "Open Consent Layer",
                onClick = {
                    cmpManager.openConsentLayer(context)
                }
            )

            DemoButton(
                text = "Reset",
                onClick = {
                    cmpManager.reset()
                    toastMessage = "Consent data reset"
                }
            )

            DemoButton(
                text = "Import CMP String",
                onClick = {
                    cmpManager.importCmpString(
                        "Q1FERkg3QVFERkg3QUFmR01CSVRCQkVnQUFBQUFBQUFBQWlnQUFBQUFBQUEjXzUxXzUyXzUzXzU0XzU1XzU2XyNfczI3ODlfczI3OTBfczI3OTFfczI2OTdfczk3MV9VXyMxLS0tIw",
                        importCallback = { success, message ->  // Add these two parameters
                            toastMessage = if (success) "Imported successfully" else "Import failed: $message"
                        }
                    )
                }
            )

            DemoButton(
                text = "Export TCF Strings to Clipboard",
                onClick = {
                    val preferences = PreferenceManager.getDefaultSharedPreferences(context)
                    val allEntries = preferences.all.entries.joinToString("\n") { (key, value) ->
                        "$key: $value"
                    }

                    try {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("TCF Strings", allEntries)
                        clipboard.setPrimaryClip(clip)
                        toastMessage = "TCF strings exported to clipboard"
                    } catch (e: Exception) {
                        toastMessage = "Failed to copy to clipboard: ${e.message}"
                    }
                }
            )
        }

        toastMessage?.let { message ->
            Toast(message = message) {
                toastMessage = null
            }
        }
    }
}

@Composable
fun DemoButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text)
    }
}

@Composable
fun Toast(
    message: String,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Snackbar(
            modifier = Modifier.padding(16.dp),
            action = {
                TextButton(onClick = onDismiss) {
                    Text("Dismiss")
                }
            }
        ) {
            Text(message)
        }
    }
}
