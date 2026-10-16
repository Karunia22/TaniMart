package com.example.tanimart.ui.auth.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tanimart.ui.auth.register.data.RegisterUiEvent
import com.example.tanimart.ui.auth.register.data.RegisterUiState
import com.example.tanimart.ui.components.TaniMartButton
import com.example.tanimart.ui.components.TaniMartPasswordField
import com.example.tanimart.ui.components.TaniMartTextField
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RegisterScreen(
    uiState: RegisterUiState,
    uiEvent: SharedFlow<RegisterUiEvent>,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(uiEvent) {
        uiEvent.collectLatest { event ->
            when (event) {
                is RegisterUiEvent.ShowValidationError -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is RegisterUiEvent.RegisterError -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is RegisterUiEvent.RegisterSuccess -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )

                    onRegisterSuccess()
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
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

            Text(
                text = "Buat Akun",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            // Nama
            TaniMartTextField(
                value = uiState.name,
                onValueChange = onNameChange,
                label = "Nama",
                placeholder = "Masukkan nama",
                isError = uiState.nameError != null,
                errorMessage = uiState.nameError
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            // Email
            TaniMartTextField(
                value = uiState.email,
                onValueChange = onEmailChange,
                label = "Email",
                placeholder = "Masukkan email",
                isError = uiState.emailError != null,
                errorMessage = uiState.emailError
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            // Password
            TaniMartPasswordField(
                value = uiState.password,
                onValueChange = onPasswordChange,
                label = "Password",
                placeholder = "Masukkan password",
                isError = uiState.passwordError != null,
                errorMessage = uiState.passwordError
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            // Konfirmasi password
            TaniMartPasswordField(
                value = uiState.confirmPassword,
                onValueChange = onConfirmPasswordChange,
                label = "Konfirmasi Password",
                placeholder = "Ulangi password",
                isError = uiState.confirmPasswordError != null,
                errorMessage = uiState.confirmPasswordError
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            // Tombol daftar
            TaniMartButton(
                text = if (uiState.isLoading) {
                    "Memproses..."
                } else {
                    "Daftar"
                },
                onClick = onRegisterClick,
                enabled = !uiState.isLoading
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            // Kembali ke login
            TextButton(
                onClick = onLoginClick,
                enabled = !uiState.isLoading
            ) {
                Text(
                    text = "Sudah punya akun? Masuk"
                )
            }
        }
    }
}
