package net.consentmanager.kmm.cmpsdkdemoapp

import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val config = CmpConfig.apply {
            id = "Your Code-ID goes here" // TODO replace with your own Code-ID from your CMP
            domain = "delivery.consentmanager.net"
            language = "DE"
            appName = "CMDemoAppSDKv2Kotlin"
        }
        CmpUIConfig.uiStrategy = CmpUIStrategy.POPUP

        cmpManager = CmpManager.createInstance(this, config)
        cmpManager.initialize(this)
        showCMPDemoScreen()
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
