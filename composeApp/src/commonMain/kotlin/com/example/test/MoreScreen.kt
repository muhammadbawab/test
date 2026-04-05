package com.example.test

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ─────────────────────────────────────────────────────────────────────────────
// More Item Data Model
// ─────────────────────────────────────────────────────────────────────────────

data class MoreItem(val icon: ImageVector, val title: String)

// ─────────────────────────────────────────────────────────────────────────────
// More Screen
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun MoreScreen(onLanguageTap: () -> Unit) {

    val infoItems = listOf(
        MoreItem(Icons.Filled.Info,          "About Us"),
        MoreItem(Icons.Filled.Security,      "Security & Privacy Policy"),
        MoreItem(Icons.Filled.Description,   "Terms of Use"),
        MoreItem(Icons.Filled.Accessibility, "Accessibility"),
    )

    val supportItems = listOf(
        MoreItem(Icons.Filled.Feedback, "Feedback"),
        MoreItem(Icons.Filled.Download, "Downloads"),
        MoreItem(Icons.Filled.Language, "Language"),
    )

    LazyColumn(
        modifier       = Modifier
            .fillMaxSize()
            .background(AppBackground),
        contentPadding = PaddingValues(vertical = 24.dp)
    ) {
        item {
            MoreSection(
                title       = "Information",
                items       = infoItems,
                onItemClick = { /* TODO: navigate to each page */ }
            )
            Spacer(Modifier.height(28.dp))
            MoreSection(
                title       = "Support",
                items       = supportItems,
                onItemClick = { item ->
                    if (item.title == "Language") onLanguageTap()
                    // TODO: handle Feedback and Downloads
                }
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// More Section — Reusable grouped list card
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun MoreSection(
    title       : String,
    items       : List<MoreItem>,
    onItemClick : (MoreItem) -> Unit
) {
    Text(
        text       = title.uppercase(),
        fontSize   = 11.sp,
        fontWeight = FontWeight.SemiBold,
        color      = AppSubText,
        modifier   = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 8.dp)
    )

    Card(
        modifier  = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape     = RoundedCornerShape(14.dp),
        colors    = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            items.forEachIndexed { index, item ->
                Row(
                    modifier          = Modifier
                        .fillMaxWidth()
                        .clickable { onItemClick(item) }
                        .padding(horizontal = 16.dp, vertical = 15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector        = item.icon,
                        contentDescription = null,
                        tint               = AppOrange,
                        modifier           = Modifier.size(22.dp)
                    )
                    Spacer(Modifier.width(14.dp))
                    Text(
                        text     = item.title,
                        fontSize = 15.sp,
                        color    = AppText,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector        = Icons.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint               = AppSubText,
                        modifier           = Modifier.size(22.dp)
                    )
                }

                if (index < items.lastIndex) {
                    HorizontalDivider(
                        color    = AppDivider,
                        modifier = Modifier.padding(start = 52.dp)
                    )
                }
            }
        }
    }
}
