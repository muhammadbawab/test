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
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import test.composeapp.generated.resources.Res
import test.composeapp.generated.resources.bg_custom
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

// Title box height — half sits over the image, half hangs below
private val TitleBoxHeight = 80.dp
private val TitleOverlap   = TitleBoxHeight / 2   // 40dp overlap into image

// ─────────────────────────────────────────────────────────────────────────────
// Home Screen
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun HomeScreen() {
    Box(modifier = Modifier.fillMaxSize()) {

        // ── Background Islamic Pattern ─────────────────────────
        Image(
            painter            = painterResource(Res.drawable.bg_custom),
            contentDescription = null,
            contentScale       = ContentScale.Crop,
            modifier           = Modifier.fillMaxSize(),
            alpha              = 0.07f
        )

        // ── Scrollable Content ─────────────────────────────────
        Column(
            modifier            = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(36.dp))

            // ── Real Logo ──────────────────────────────────────
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

            // ── Course Banners ─────────────────────────────────
            banners.forEach { banner ->
                BannerCard(banner)
                // Extra space = normal gap + the half of title box hanging below
                Spacer(Modifier.height(TitleOverlap + 16.dp))
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Banner Card
//
// Layer 1 (back)  — rounded course photo, fixed height
// Layer 2 (front) — white+alpha rounded title box, vertically centered
//                   on the BOTTOM EDGE of the photo using offset.
//                   Top half overlaps photo (image bleeds through alpha).
//                   Bottom half hangs below photo.
//                   Shadow makes it pop as a separate floating element.
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun BannerCard(banner: BannerItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .clickable { /* TODO: navigate to course detail */ }
    ) {

        // ── Layer 1: Rounded course photo ─────────────────────
        Image(
            painter            = painterResource(banner.imageRes),
            contentDescription = banner.title,
            contentScale       = ContentScale.Crop,
            modifier           = Modifier
                .fillMaxWidth()
                .height(190.dp)
                .clip(RoundedCornerShape(18.dp))
        )

        // ── Layer 2: Floating white title box ─────────────────
        // offset(y = TitleOverlap) pushes the box down by 40dp
        // so its top half overlaps the photo and bottom half
        // hangs below — straddling the photo's bottom edge.
        Card(
            modifier  = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp)
                .height(TitleBoxHeight)
                .align(Alignment.BottomCenter)
                .offset(y = TitleOverlap),
            shape     = RoundedCornerShape(14.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors    = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.92f)
            )
        ) {
            Column(
                modifier            = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text       = banner.title,
                    fontSize   = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color      = AppText,
                    textAlign  = TextAlign.Center,
                    modifier   = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .padding(top = 16.dp)
                )
                // Orange accent line flush at bottom of title box
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
