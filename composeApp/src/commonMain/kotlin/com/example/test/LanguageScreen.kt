package com.example.test

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ─────────────────────────────────────────────────────────────────────────────
// Language Data Model
// ─────────────────────────────────────────────────────────────────────────────

data class Language(val flag: String, val name: String)

private val languages = listOf(
    Language("🇬🇧", "English"),
    Language("🇸🇦", "Arabic"),
    Language("🇰🇪", "Swahili"),
    Language("🇫🇷", "French"),
    Language("🇵🇹", "Portuguese"),
    Language("🇪🇸", "Spanish"),
    Language("🇷🇺", "Russian"),
    Language("🇩🇪", "German"),
    Language("🇮🇳", "Hindi"),
    Language("🇨🇳", "Chinese"),
    Language("🇵🇰", "Urdu"),
    Language("🇮🇩", "Bahasa"),
)

// ─────────────────────────────────────────────────────────────────────────────
// Language Screen
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun LanguageScreen() {
    var selectedLanguage by remember { mutableStateOf("English") }

    androidx.compose.foundation.layout.Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
    ) {
        // ── Header ───────────────────────────────────────────
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Text(
                text       = "Select Language",
                fontSize   = 18.sp,
                fontWeight = FontWeight.Bold,
                color      = AppText
            )
        }
        HorizontalDivider(color = AppDivider)

        // ── Language List ────────────────────────────────────
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(languages) { index, lang ->
                val isSelected = lang.name == selectedLanguage

                Row(
                    modifier          = Modifier
                        .fillMaxWidth()
                        .background(if (isSelected) AppSelectedBlue else Color.White)
                        .clickable { selectedLanguage = lang.name }
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(lang.flag, fontSize = 26.sp)
                    Spacer(Modifier.width(16.dp))
                    Text(
                        text       = lang.name,
                        fontSize   = 16.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                        color      = if (isSelected) Color.White else AppText
                    )
                }

                if (index < languages.lastIndex) {
                    HorizontalDivider(
                        color    = AppDivider,
                        modifier = Modifier.padding(start = 62.dp)
                    )
                }
            }
        }
    }
}
