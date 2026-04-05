package com.example.test

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ─────────────────────────────────────────────────────────────────────────────
// App Color Palette
// ─────────────────────────────────────────────────────────────────────────────

val AppBackground   = Color.White
val AppOrange       = Color(0xFFE07B39)
val AppGreen        = Color(0xFF2A7A3B)
val AppText         = Color(0xFF1C1C1C)
val AppSubText      = Color(0xFF888888)
val AppDivider      = Color(0xFFEEEEEE)
val AppSelectedBlue = Color(0xFF1565C0)

// ─────────────────────────────────────────────────────────────────────────────
// Navigation Screens
// ─────────────────────────────────────────────────────────────────────────────

sealed class Screen(val label: String, val icon: ImageVector) {
    object Home     : Screen("Home",     Icons.Filled.Home)
    object Login    : Screen("Login",    Icons.Filled.Person)
    object Share    : Screen("Share",    Icons.Filled.Share)
    object Language : Screen("Language", Icons.Filled.Language)
    object More     : Screen("More",     Icons.Filled.MoreHoriz)
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
// Main Screen — Scaffold + Bottom Navigation Bar
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
                        label  = { Text(screen.label, fontSize = 10.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor   = AppOrange,
                            selectedTextColor   = AppOrange,
                            unselectedIconColor = AppSubText,
                            unselectedTextColor = AppSubText,
                            indicatorColor      = Color.Transparent  // no bubble
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
                Screen.More     -> MoreScreen(
                    onLanguageTap = { currentScreen = Screen.Language }
                )
            }
        }
    }
}
