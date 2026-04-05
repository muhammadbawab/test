package com.example.test

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ─────────────────────────────────────────────────────────────────────────────
// Banner Data Model
// ─────────────────────────────────────────────────────────────────────────────

data class BannerItem(
    val title      : String,
    val colorStart : Color,
    val colorEnd   : Color,
    val icon       : ImageVector
)

private val banners = listOf(
    BannerItem("Alphabets, Letters and Words",
        Color(0xFF0D47A1), Color(0xFF42A5F5), Icons.Filled.Headphones),
    BannerItem("Qur'an Recitation",
        Color(0xFF1B5E20), Color(0xFF66BB6A), Icons.Filled.Headphones),
    BannerItem("Meaning of the Qur'an",
        Color(0xFFBF360C), Color(0xFFFFCA28), Icons.Filled.Headphones),
    BannerItem("Tajweed",
        Color(0xFF880E4F), Color(0xFFF48FB1), Icons.Filled.Headphones),
    BannerItem("Memorizing the Qur'an",
        Color(0xFF4A148C), Color(0xFFCE93D8), Icons.Filled.Headphones),
    BannerItem("Qur'anic Language",
        Color(0xFF004D40), Color(0xFF4DB6AC), Icons.Filled.Headphones),
)

// ─────────────────────────────────────────────────────────────────────────────
// Home Screen
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun HomeScreen() {
    Column(
        modifier            = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(36.dp))

        // ── Logo ─────────────────────────────────────────────
        Box(
            modifier         = Modifier
                .size(88.dp)
                .clip(CircleShape)
                .background(
                    Brush.verticalGradient(listOf(Color(0xFF2A7A3B), Color(0xFF1B5E20)))
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector        = Icons.Filled.Headphones,
                contentDescription = null,
                tint               = Color.White,
                modifier           = Modifier.size(44.dp)
            )
        }

        Spacer(Modifier.height(14.dp))

        Text(
            text       = "Mu'alim Al-Qur'an",
            fontSize   = 22.sp,
            fontWeight = FontWeight.Bold,
            color      = AppText
        )
        Text(
            text       = "Connecting People with The Qur'an",
            fontSize   = 13.sp,
            fontWeight = FontWeight.Medium,
            color      = AppOrange
        )

        Spacer(Modifier.height(28.dp))

        // ── Course Banners ───────────────────────────────────
        banners.forEach { banner ->
            BannerCard(banner)
            Spacer(Modifier.height(18.dp))
        }

        Spacer(Modifier.height(16.dp))
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Banner Card
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun BannerCard(banner: BannerItem) {
    Card(
        modifier  = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .clickable { /* TODO: navigate to course detail */ },
        shape     = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors    = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box {
            // ── Photo / gradient background ──────────────────
            Box(
                modifier         = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(banner.colorStart, banner.colorEnd)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector        = banner.icon,
                    contentDescription = null,
                    tint               = Color.White.copy(alpha = 0.15f),
                    modifier           = Modifier.size(120.dp)
                )
            }

            // ── Floating white title card ────────────────────
            Card(
                modifier  = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp),
                shape     = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors    = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column {
                    Text(
                        text      = banner.title,
                        fontSize  = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color     = AppText,
                        textAlign = TextAlign.Center,
                        modifier  = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 14.dp)
                    )
                    // Orange accent line at the bottom
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(3.dp)
                            .background(AppOrange)
                    )
                }
            }
        }
    }
}
