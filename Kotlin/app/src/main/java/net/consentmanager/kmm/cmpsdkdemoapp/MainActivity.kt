package net.consentmanager.kmm.cmpsdkdemoapp

import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import net.consentmanager.sdk.CmpManager
import net.consentmanager.sdk.consentlayer.model.CmpConfig
import net.consentmanager.sdk.consentlayer.model.CmpUIConfig
import net.consentmanager.sdk.consentlayer.model.CmpUIStrategy

class MainActivity : AppCompatActivity() {
    private lateinit var cmpManager: CmpManager
    private var isCmpShowing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val config = CmpConfig.apply {
            id = "YOUR-CODE-ID" // TODO replace with your own Code-ID from your CMP
            domain = "delivery.consentmanager.net"
            language = "IT"
            appName = "CMDemoAppKotlin"
        }
        CmpUIConfig.uiStrategy = CmpUIStrategy.POPUP

        cmpManager = CmpManager.createInstance(this, config).apply {
            initialize(this@MainActivity)
            withOpenListener { isCmpShowing = true }
            withCloseListener { isCmpShowing = false }
            withErrorCallback { error, message ->
                Log.e("CMPDemoApp", "Error: $error, Message: $message")
            }
        }
        setupBackPressHandler()
        showCMPDemoScreen()
    }

    private fun setupBackPressHandler() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isCmpShowing) {
                    if (CmpUIConfig.backPressCallback?.onBackPressed() != true) {
                        return
                    }
                }
                isEnabled = false // Disable this callback to allow the system back press to proceed
                onBackPressedDispatcher.onBackPressed()
            }
        })
    }

    private fun showCMPDemoScreen() {
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CMPDemoScreen(cmpManager)
                }
            }
        }
    }
}
