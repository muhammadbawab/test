package com.example.test

import androidx.compose.runtime.staticCompositionLocalOf

// ─────────────────────────────────────────────────────────────────────────────
// Share Handler — platform implementation is provided by each platform
// Android: MainActivity.kt  |  iOS: MainViewController.kt
// ─────────────────────────────────────────────────────────────────────────────

interface ShareHandler {
    fun share(text: String)
}

val LocalShareHandler = staticCompositionLocalOf<ShareHandler> {
    object : ShareHandler {
        override fun share(text: String) {} // No-op default
    }
}
