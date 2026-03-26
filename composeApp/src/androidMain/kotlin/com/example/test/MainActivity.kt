package com.example.test

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {

    // Android-specific share implementation using the native share sheet
    private val androidShareHandler by lazy {
        object : ShareHandler {
            override fun share(text: String) {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, text)
                }
                startActivity(Intent.createChooser(intent, null))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider(LocalShareHandler provides androidShareHandler) {
                App()
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}