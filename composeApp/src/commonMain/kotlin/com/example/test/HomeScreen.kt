package com.example.test

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import test.composeapp.generated.resources.Res
import test.composeapp.generated.resources.logo
import test.composeapp.generated.resources.s1
import test.composeapp.generated.resources.s2
import test.composeapp.generated.resources.s3
import test.composeapp.generated.resources.s4
import test.composeapp.generated.resources.s5
import test.composeapp.generated.resources.s6

// ─────────────────────────────────────────────────────────────────────────────
// Banner Data Model
// ─────────────────────────────────────────────────────────────────────────────

data class BannerItem(
    val title    : String,
    val imageRes : DrawableResource
)

private val banners = listOf(
    BannerItem("Alphabets, Letters and Words", Res.drawable.s1),
    BannerItem("Qur'an Recitation",            Res.drawable.s2),
    BannerItem("Meaning of the Qur'an",        Res.drawable.s3),
    BannerItem("Tajweed",                       Res.drawable.s4),
    BannerItem("Memorizing the Qur'an",        Res.drawable.s5),
    BannerItem("Qur'anic Language",            Res.drawable.s6),
)

// ─────────────────────────────────────────────────────────────────────────────
// Home Screen
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun HomeScreen() {
    Column(
        modifier            = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(36.dp))

        // ── Real Logo — no crop, full PNG as-is ──────────────
        Image(
            painter            = painterResource(Res.drawable.logo),
            contentDescription = "Mu'alim Al-Qur'an Logo",
            modifier           = Modifier.size(160.dp),
            contentScale       = ContentScale.Fit
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text       = "Mu'alim Al-Qur'an",
            fontSize   = 26.sp,
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

        // ── Course Banners ─────────────────────────────────────
        banners.forEach { banner ->
            BannerCard(banner)
            Spacer(Modifier.height(20.dp))
        }

        Spacer(Modifier.height(16.dp))
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Banner Card
// Photo fills full card height. White rounded title chip floats over
// the bottom portion of the photo with its own elevation and orange
// bottom border — all 4 corners rounded, side margins to give it
// the "floating label" look seen in the original screenshot.
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun BannerCard(banner: BannerItem) {
    Card(
        modifier  = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable { /* TODO: navigate to course detail */ },
        shape     = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors    = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box {

            // ── Full bleed course photo ────────────────────────
            Image(
                painter            = painterResource(banner.imageRes),
                contentDescription = banner.title,
                contentScale       = ContentScale.Crop,
                modifier           = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            )

            // ── Floating white rounded title chip ──────────────
            // Sits at the bottom of the photo, inset from edges,
            // with its own rounded corners on all 4 sides so it
            // looks like a separate elevated label, not a footer.
            Card(
                modifier  = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp) // inset from card edges
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp),    // gap from card bottom
                shape     = RoundedCornerShape(14.dp), // all 4 corners rounded
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors    = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column {
                    // Title text
                    Text(
                        text       = banner.title,
                        fontSize   = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color      = AppText,
                        textAlign  = TextAlign.Center,
                        modifier   = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 14.dp)
                    )
                    // Orange accent line — flush to bottom of chip
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
