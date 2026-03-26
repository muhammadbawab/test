package com.example.test

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ─────────────────────────────────────────────────────────────────────────────
// Share Handler — platform implementation is provided by each platform (see MainActivity)
// ─────────────────────────────────────────────────────────────────────────────

interface ShareHandler {
    fun share(text: String)
}

val LocalShareHandler = staticCompositionLocalOf<ShareHandler> {
    object : ShareHandler {
        override fun share(text: String) {} // No-op default
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// App Color Palette  (Mu'alim Al-Qur'an warm-beige theme)
// ─────────────────────────────────────────────────────────────────────────────

private val AppBackground   = Color(0xFFFAF6EE)
private val AppOrange       = Color(0xFFE07B39)
private val AppGreen        = Color(0xFF2A7A3B)
private val AppText         = Color(0xFF1C1C1C)
private val AppSubText      = Color(0xFF888888)
private val AppDivider      = Color(0xFFEEEEEE)
private val AppSelectedBlue = Color(0xFF1565C0)

// ─────────────────────────────────────────────────────────────────────────────
// Navigation
// ─────────────────────────────────────────────────────────────────────────────

sealed class Screen(val label: String, val icon: ImageVector) {
    object Home     : Screen("Home",     Icons.Filled.Home)
    object Login    : Screen("Login",    Icons.Filled.Person)
    object Share    : Screen("Share",    Icons.Filled.Share)
    object Language : Screen("Language", Icons.Filled.Language)   // requires materialIconsExtended
    object More     : Screen("More",     Icons.Filled.MoreHoriz)  // requires materialIconsExtended
}

private val allScreens = listOf(
    Screen.Home, Screen.Login, Screen.Share, Screen.Language, Screen.More
)

// ─────────────────────────────────────────────────────────────────────────────
// App Entry Point
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun App() {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary          = AppOrange,
            background       = AppBackground,
            surface          = Color.White,
            onBackground     = AppText,
            onSurface        = AppText,
            onSurfaceVariant = AppSubText,
            outline          = AppDivider,
        )
    ) {
        MainScreen()
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Main Screen — Scaffold + Bottom Navigation
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun MainScreen() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }

    Scaffold(
        containerColor = AppBackground,
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 0.dp
            ) {
                allScreens.forEach { screen ->
                    NavigationBarItem(
                        selected = currentScreen == screen,
                        onClick  = { currentScreen = screen },
                        icon     = {
                            Icon(
                                imageVector        = screen.icon,
                                contentDescription = screen.label,
                                modifier           = Modifier.size(22.dp)
                            )
                        },
                        label   = { Text(screen.label, fontSize = 10.sp) },
                        colors  = NavigationBarItemDefaults.colors(
                            selectedIconColor   = AppOrange,
                            selectedTextColor   = AppOrange,
                            unselectedIconColor = AppSubText,
                            unselectedTextColor = AppSubText,
                            indicatorColor      = AppOrange.copy(alpha = 0.12f)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (currentScreen) {
                Screen.Home     -> HomeScreen()
                Screen.Login    -> LoginScreen()
                Screen.Share    -> ShareScreen()
                Screen.Language -> LanguageScreen()
                Screen.More     -> MoreScreen(onLanguageTap = { currentScreen = Screen.Language })
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Home Screen — Logo + 6 Course Banners
// ─────────────────────────────────────────────────────────────────────────────

data class BannerItem(
    val title      : String,
    val colorStart : Color,
    val colorEnd   : Color,
    val icon       : ImageVector
)

private val banners = listOf(
    BannerItem("Alphabets, Letters and Words",
        Color(0xFF0D47A1), Color(0xFF42A5F5), Icons.Filled.Abc),
    BannerItem("Qur'an Recitation",
        Color(0xFF1B5E20), Color(0xFF66BB6A), Icons.Filled.RecordVoiceOver),
    BannerItem("Meaning of the Qur'an",
        Color(0xFFBF360C), Color(0xFFFFCA28), Icons.Filled.AutoStories),
    BannerItem("Tajweed",
        Color(0xFF880E4F), Color(0xFFF48FB1), Icons.Filled.MusicNote),
    BannerItem("Memorizing the Qur'an",
        Color(0xFF4A148C), Color(0xFFCE93D8), Icons.Filled.Psychology),
    BannerItem("Qur'anic Language",
        Color(0xFF004D40), Color(0xFF4DB6AC), Icons.Filled.Translate),
)

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

        // ── Logo ────────────────────────────────────────────
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
            "Mu'alim Al-Qur'an",
            fontSize   = 22.sp,
            fontWeight = FontWeight.Bold,
            color      = AppText
        )
        Text(
            "Connecting People with The Qur'an",
            fontSize   = 13.sp,
            fontWeight = FontWeight.Medium,
            color      = AppOrange
        )

        Spacer(Modifier.height(28.dp))

        // ── Course Banners ──────────────────────────────────
        banners.forEach { banner ->
            BannerCard(banner)
            Spacer(Modifier.height(18.dp))
        }

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
fun BannerCard(banner: BannerItem) {
    Card(
        modifier  = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .clickable { /* TODO: navigate to course */ },
        shape     = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors    = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            // Gradient top (simulates the course photo)
            Box(
                modifier         = Modifier
                    .fillMaxWidth()
                    .height(165.dp)
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
                    tint               = Color.White.copy(alpha = 0.22f),
                    modifier           = Modifier.size(110.dp)
                )
            }
            // Title
            Box(
                modifier         = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text       = banner.title,
                    fontSize   = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color      = AppText,
                    textAlign  = TextAlign.Center
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Login Screen
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun LoginScreen() {
    var username        by remember { mutableStateOf("") }
    var password        by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading       by remember { mutableStateOf(false) }
    var errorMessage    by remember { mutableStateOf<String?>(null) }

    Column(
        modifier            = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(52.dp))

        Text("Welcome Back",    fontSize = 30.sp, fontWeight = FontWeight.Bold, color = AppText)
        Spacer(Modifier.height(6.dp))
        Text("Sign in to your account", fontSize = 14.sp, color = AppSubText)
        Spacer(Modifier.height(36.dp))

        // Error banner
        errorMessage?.let { msg ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors   = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer),
                shape    = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier          = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Filled.Warning, contentDescription = null,
                        tint     = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(msg,
                        color    = MaterialTheme.colorScheme.onErrorContainer,
                        fontSize = 13.sp)
                }
            }
            Spacer(Modifier.height(16.dp))
        }

        OutlinedTextField(
            value           = username,
            onValueChange   = { username = it; errorMessage = null },
            label           = { Text("Username or Email") },
            leadingIcon     = { Icon(Icons.Filled.Person, contentDescription = null) },
            modifier        = Modifier.fillMaxWidth(),
            singleLine      = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            shape           = RoundedCornerShape(12.dp)
        )

        Spacer(Modifier.height(14.dp))

        OutlinedTextField(
            value                = password,
            onValueChange        = { password = it; errorMessage = null },
            label                = { Text("Password") },
            leadingIcon          = { Icon(Icons.Filled.Lock, contentDescription = null) },
            trailingIcon         = {
                TextButton(
                    onClick        = { passwordVisible = !passwordVisible },
                    contentPadding = PaddingValues(horizontal = 12.dp)
                ) {
                    Text(
                        if (passwordVisible) "Hide" else "Show",
                        fontSize = 12.sp, color = AppOrange
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            modifier             = Modifier.fillMaxWidth(),
            singleLine           = true,
            keyboardOptions      = KeyboardOptions(keyboardType = KeyboardType.Password),
            shape                = RoundedCornerShape(12.dp)
        )

        TextButton(
            onClick  = { /* TODO: forgot password */ },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Forgot Password?", fontSize = 13.sp, color = AppOrange)
        }

        Spacer(Modifier.height(6.dp))

        Button(
            onClick = {
                when {
                    username.isBlank() && password.isBlank() ->
                        errorMessage = "Please enter your username and password."
                    username.isBlank() ->
                        errorMessage = "Please enter your username or email."
                    password.isBlank() ->
                        errorMessage = "Please enter your password."
                    else -> { isLoading = true; errorMessage = null }
                }
            },
            enabled  = !isLoading,
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape    = RoundedCornerShape(12.dp),
            colors   = ButtonDefaults.buttonColors(containerColor = AppOrange)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier    = Modifier.size(22.dp),
                    color       = Color.White,
                    strokeWidth = 2.5.dp
                )
            } else {
                Text("Sign In", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
        }

        Spacer(Modifier.height(28.dp))

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            HorizontalDivider(modifier = Modifier.weight(1f))
            Text("   or continue with   ", fontSize = 13.sp, color = AppSubText)
            HorizontalDivider(modifier = Modifier.weight(1f))
        }

        Spacer(Modifier.height(24.dp))

        OutlinedButton(
            onClick  = { /* TODO: Google Sign-In */ },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape    = RoundedCornerShape(12.dp),
            border   = BorderStroke(1.dp, AppDivider)
        ) {
            Text("G", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4285F4))
            Spacer(Modifier.width(12.dp))
            Text("Continue with Google", fontSize = 15.sp, color = AppText)
        }

        Spacer(Modifier.height(12.dp))

        OutlinedButton(
            onClick  = { /* TODO: Apple Sign-In */ },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape    = RoundedCornerShape(12.dp),
            border   = BorderStroke(1.dp, AppDivider)
        ) {
            Text("\uF8FF", fontSize = 20.sp, color = AppText)
            Spacer(Modifier.width(12.dp))
            Text("Continue with Apple", fontSize = 15.sp, color = AppText)
        }

        Spacer(Modifier.height(36.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Text("Don't have an account?", color = AppSubText, fontSize = 14.sp)
            TextButton(onClick = { /* TODO: register */ }) {
                Text("Sign Up", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = AppOrange)
            }
        }

        Spacer(Modifier.height(24.dp))
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Share Screen
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun ShareScreen() {
    val shareHandler = LocalShareHandler.current

    Column(
        modifier              = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(24.dp),
        horizontalAlignment   = Alignment.CenterHorizontally,
        verticalArrangement   = Arrangement.Center
    ) {
        Box(
            modifier         = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(AppOrange.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.Share, contentDescription = null,
                tint = AppOrange, modifier = Modifier.size(44.dp))
        }

        Spacer(Modifier.height(24.dp))

        Text(
            "Share this app!",
            fontSize   = 24.sp,
            fontWeight = FontWeight.Bold,
            color      = AppText
        )

        Spacer(Modifier.height(10.dp))

        Text(
            "Help others connect with the Qur'an\nby sharing Mu'alim Al-Qur'an",
            fontSize  = 14.sp,
            color     = AppSubText,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(36.dp))

        Button(
            onClick  = {
                shareHandler.share(
                    "Check out Mu'alim Al-Qur'an – Connecting People with The Qur'an!\n" +
                            "Download it here: https://example.com/mualimaiquran"
                )
            },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape    = RoundedCornerShape(12.dp),
            colors   = ButtonDefaults.buttonColors(containerColor = AppOrange)
        ) {
            Icon(Icons.Filled.Share, contentDescription = null, modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(8.dp))
            Text("Share Now", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Language Screen
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

@Composable
fun LanguageScreen() {
    var selectedLanguage by remember { mutableStateOf("English") }

    Column(modifier = Modifier.fillMaxSize().background(AppBackground)) {

        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Text(
                "Select Language",
                fontSize   = 18.sp,
                fontWeight = FontWeight.Bold,
                color      = AppText
            )
        }
        HorizontalDivider(color = AppDivider)

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
                        lang.name,
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

// ─────────────────────────────────────────────────────────────────────────────
// More Screen — Two Sections
// ─────────────────────────────────────────────────────────────────────────────

data class MoreItem(val icon: ImageVector, val title: String)

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
        modifier       = Modifier.fillMaxSize().background(AppBackground),
        contentPadding = PaddingValues(vertical = 24.dp)
    ) {
        item {
            MoreSection(title = "Information", items = infoItems) { /* TODO */ }
            Spacer(Modifier.height(28.dp))
            MoreSection(title = "Support", items = supportItems) { item ->
                if (item.title == "Language") onLanguageTap()
            }
        }
    }
}

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
        modifier  = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
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