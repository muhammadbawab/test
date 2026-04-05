package com.example.test

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ─────────────────────────────────────────────────────────────────────────────
// Share Screen
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun ShareScreen() {
    val shareHandler = LocalShareHandler.current

    Column(
        modifier            = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // ── Share Icon Circle ────────────────────────────────
        Box(
            modifier         = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(AppOrange.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector        = Icons.Filled.Share,
                contentDescription = null,
                tint               = AppOrange,
                modifier           = Modifier.size(44.dp)
            )
        }

        Spacer(Modifier.height(24.dp))

        Text(
            text       = "Share this app!",
            fontSize   = 24.sp,
            fontWeight = FontWeight.Bold,
            color      = AppText
        )

        Spacer(Modifier.height(10.dp))

        Text(
            text      = "Help others connect with the Qur'an\nby sharing Mu'alim Al-Qur'an",
            fontSize  = 14.sp,
            color     = AppSubText,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(36.dp))

        // ── Share Button ─────────────────────────────────────
        Button(
            onClick  = {
                shareHandler.share(
                    "Check out Mu'alim Al-Qur'an – Connecting People with The Qur'an!\n" +
                    "Download it here: https://example.com/mualimaiquran"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape    = RoundedCornerShape(12.dp),
            colors   = ButtonDefaults.buttonColors(containerColor = AppOrange)
        ) {
            Icon(
                imageVector        = Icons.Filled.Share,
                contentDescription = null,
                modifier           = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text       = "Share Now",
                fontSize   = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
