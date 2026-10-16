package com.example.tanimart.ui.auth.login.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanimart.data.auth.LoginRepository
import com.example.tanimart.data.network.RetrofitClient
import com.example.tanimart.data.network.TokenStorage
import com.example.tanimart.ui.auth.login.data.LoginUiEvent
import com.example.tanimart.ui.auth.login.data.LoginUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException

class LoginViewModel : ViewModel() {

    private val repository = LoginRepository(
        RetrofitClient.loginApi
    )

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<LoginUiEvent>()
    val uiEvent: SharedFlow<LoginUiEvent> =
        _uiEvent.asSharedFlow()

    fun onEmailChange(value: String) {
        _uiState.value = _uiState.value.copy(
            email = value,
            emailError = null
        )
    }

    fun onPasswordChange(value: String) {
        _uiState.value = _uiState.value.copy(
            password = value,
            passwordError = null
        )
    }

    fun login() {
        val state = _uiState.value

        val emailError = when {
            state.email.isBlank() ->
                "Email wajib diisi"

            !android.util.Patterns.EMAIL_ADDRESS
                .matcher(state.email.trim())
                .matches() ->
                "Format email tidak valid"

            else -> null
        }

        val passwordError = if (state.password.isBlank()) {
            "Password wajib diisi"
        } else {
            null
        }

        _uiState.value = state.copy(
            emailError = emailError,
            passwordError = passwordError
        )

        if (emailError != null || passwordError != null) {
            viewModelScope.launch {
                _uiEvent.emit(
                    LoginUiEvent.ShowValidationError(
                        listOfNotNull(
                            emailError,
                            passwordError
                        ).joinToString("\n")
                    )
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true
            )

            try {
                val response = repository.login(
                    email = state.email.trim(),
                    password = state.password
                )

                TokenStorage.saveToken(response.token)

                _uiEvent.emit(LoginUiEvent.LoginSuccess)

            } catch (e: CancellationException) {
                throw e

            } catch (e: HttpException) {
                val message = when (e.code()) {
                    401, 422 -> "Email atau password salah."
                    else -> "Login gagal. Silakan coba lagi."
                }

                _uiEvent.emit(
                    LoginUiEvent.ShowValidationError(message)
                )
            } catch (e: IOException) {
                _uiEvent.emit(
                    LoginUiEvent.ShowValidationError(
                        "Tidak dapat terhubung ke server. Periksa koneksi internet dan server."
                    )
                )

            } catch (e: Exception) {
                _uiEvent.emit(
                    LoginUiEvent.ShowValidationError(
                        "Terjadi kesalahan saat login. Silakan coba lagi."
                    )
                )

            } finally {
                _uiState.value = _uiState.value.copy(
                    isLoading = false
                )
            }
        }
    }
}
