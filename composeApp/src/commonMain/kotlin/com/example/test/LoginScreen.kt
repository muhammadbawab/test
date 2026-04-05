package com.example.test

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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

        Text(
            text       = "Welcome Back",
            fontSize   = 30.sp,
            fontWeight = FontWeight.Bold,
            color      = AppText
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text     = "Sign in to your account",
            fontSize = 14.sp,
            color    = AppSubText
        )
        Spacer(Modifier.height(36.dp))

        // ── Error Banner ─────────────────────────────────────
        errorMessage?.let { msg ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors   = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier          = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector        = Icons.Filled.Warning,
                        contentDescription = null,
                        tint               = MaterialTheme.colorScheme.onErrorContainer,
                        modifier           = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text     = msg,
                        color    = MaterialTheme.colorScheme.onErrorContainer,
                        fontSize = 13.sp
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
        }

        // ── Username ─────────────────────────────────────────
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

        // ── Password ─────────────────────────────────────────
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
                        text     = if (passwordVisible) "Hide" else "Show",
                        fontSize = 12.sp,
                        color    = AppOrange
                    )
                }
            },
            visualTransformation = if (passwordVisible)
                VisualTransformation.None else PasswordVisualTransformation(),
            modifier             = Modifier.fillMaxWidth(),
            singleLine           = true,
            keyboardOptions      = KeyboardOptions(keyboardType = KeyboardType.Password),
            shape                = RoundedCornerShape(12.dp)
        )

        // ── Forgot Password ──────────────────────────────────
        TextButton(
            onClick  = { /* TODO: navigate to forgot password */ },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Forgot Password?", fontSize = 13.sp, color = AppOrange)
        }

        Spacer(Modifier.height(6.dp))

        // ── Sign In Button ───────────────────────────────────
        Button(
            onClick = {
                when {
                    username.isBlank() && password.isBlank() ->
                        errorMessage = "Please enter your username and password."
                    username.isBlank() ->
                        errorMessage = "Please enter your username or email."
                    password.isBlank() ->
                        errorMessage = "Please enter your password."
                    else -> {
                        isLoading = true
                        errorMessage = null
                        // TODO: call authentication logic
                    }
                }
            },
            enabled  = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
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

        // ── Divider ──────────────────────────────────────────
        Row(
            modifier          = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(modifier = Modifier.weight(1f))
            Text(
                text     = "   or continue with   ",
                fontSize = 13.sp,
                color    = AppSubText
            )
            HorizontalDivider(modifier = Modifier.weight(1f))
        }

        Spacer(Modifier.height(24.dp))

        // ── Google Button ────────────────────────────────────
        OutlinedButton(
            onClick  = { /* TODO: Google Sign-In */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape    = RoundedCornerShape(12.dp),
            border   = BorderStroke(1.dp, AppDivider)
        ) {
            Text(
                text       = "G",
                fontSize   = 20.sp,
                fontWeight = FontWeight.Bold,
                color      = Color(0xFF4285F4)
            )
            Spacer(Modifier.width(12.dp))
            Text("Continue with Google", fontSize = 15.sp, color = AppText)
        }

        Spacer(Modifier.height(12.dp))

        // ── Apple Button ─────────────────────────────────────
        OutlinedButton(
            onClick  = { /* TODO: Apple Sign-In */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape    = RoundedCornerShape(12.dp),
            border   = BorderStroke(1.dp, AppDivider)
        ) {
            Text("\uF8FF", fontSize = 20.sp, color = AppText)
            Spacer(Modifier.width(12.dp))
            Text("Continue with Apple", fontSize = 15.sp, color = AppText)
        }

        Spacer(Modifier.height(36.dp))

        // ── Sign Up Link ─────────────────────────────────────
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Text("Don't have an account?", color = AppSubText, fontSize = 14.sp)
            TextButton(onClick = { /* TODO: navigate to register */ }) {
                Text(
                    text       = "Sign Up",
                    fontWeight = FontWeight.SemiBold,
                    fontSize   = 14.sp,
                    color      = AppOrange
                )
            }
        }

        Spacer(Modifier.height(24.dp))
    }
}
