package com.example.tanimart.ui.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tanimart.R
import com.example.tanimart.ui.auth.login.data.LoginUiEvent
import com.example.tanimart.ui.auth.login.data.LoginUiState
import com.example.tanimart.ui.components.TaniMartButton
import com.example.tanimart.ui.components.TaniMartPasswordField
import com.example.tanimart.ui.components.TaniMartTextField
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    uiState: LoginUiState,
    uiEvent: kotlinx.coroutines.flow.SharedFlow<LoginUiEvent>,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(uiEvent) {
        uiEvent.collectLatest { event ->
            when (event) {
                is LoginUiEvent.ShowValidationError -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                LoginUiEvent.LoginSuccess -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Login berhasil!"
                        )
                        onLoginSuccess()
                    }
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo TaniMart",
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Selamat Datang!",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Masuk untuk melanjutkan ke TaniMart",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(32.dp))

            TaniMartTextField(
                value = uiState.email,
                onValueChange = onEmailChange,
                label = "Email",
                placeholder = "Masukkan email"
            )

            Spacer(modifier = Modifier.height(16.dp))

            TaniMartPasswordField(
                value = uiState.password,
                onValueChange = onPasswordChange,
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = onForgotPasswordClick
            ) {
                Text(
                    text = "Lupa password?",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TaniMartButton(
                text = "Masuk",
                onClick = onLoginClick,
                enabled = !uiState.isLoading
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = onRegisterClick
            ) {
                Text(
                    text = "Belum punya akun? Daftar",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
