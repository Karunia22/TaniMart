package com.example.tanimart.ui.auth.register.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanimart.data.auth.RegisterRepository
import com.example.tanimart.data.auth.RegisterRequest
import com.example.tanimart.data.network.RetrofitClient
import com.example.tanimart.ui.auth.register.data.RegisterUiEvent
import com.example.tanimart.ui.auth.register.data.RegisterUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import com.example.tanimart.data.network.TokenStorage
import kotlinx.coroutines.CancellationException
import java.io.IOException

class RegisterViewModel : ViewModel() {

    private val repository =
        RegisterRepository(RetrofitClient.registerApi)

    private val _uiState =
        MutableStateFlow(RegisterUiState())

    val uiState =
        _uiState.asStateFlow()

    private val _uiEvent =
        MutableSharedFlow<RegisterUiEvent>()

    val uiEvent: SharedFlow<RegisterUiEvent> =
        _uiEvent.asSharedFlow()


    fun onNameChange(value: String) {
        _uiState.value = _uiState.value.copy(
            name = value,
            nameError = null
        )
    }

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

    fun onConfirmPasswordChange(value: String) {
        _uiState.value = _uiState.value.copy(
            confirmPassword = value,
            confirmPasswordError = null
        )
    }

    fun register() {

        val state = _uiState.value

        var nameError: String? = null
        var emailError: String? = null
        var passwordError: String? = null
        var confirmPasswordError: String? = null

        // Nama
        if (state.name.isBlank()) {
            nameError = "Nama wajib diisi"
        } else if (state.name.trim().length < 3) {
            nameError = "Nama minimal 3 karakter"
        }

        // Email
        if (state.email.isBlank()) {
            emailError = "Email wajib diisi"
        } else if (
            !android.util.Patterns.EMAIL_ADDRESS
                .matcher(state.email)
                .matches()
        ) {
            emailError = "Format email tidak valid"
        }

        // Password
        if (state.password.isBlank()) {
            passwordError = "Password wajib diisi"
        } else if (state.password.length < 8) {
            passwordError = "Password minimal 8 karakter"
        }

        // Konfirmasi password
        if (state.confirmPassword.isBlank()) {
            confirmPasswordError =
                "Konfirmasi password wajib diisi"
        } else if (state.confirmPassword != state.password) {
            confirmPasswordError =
                "Konfirmasi password tidak sama"
        }

        _uiState.value = state.copy(
            nameError = nameError,
            emailError = emailError,
            passwordError = passwordError,
            confirmPasswordError = confirmPasswordError
        )

        val errors = listOfNotNull(
            nameError?.let { "Nama: $it" },
            emailError?.let { "Email: $it" },
            passwordError?.let { "Password: $it" },
            confirmPasswordError?.let {
                "Konfirmasi password: $it"
            }
        )

        // Validasi gagal
        if (errors.isNotEmpty()) {

            viewModelScope.launch {
                _uiEvent.emit(
                    RegisterUiEvent.ShowValidationError(
                        errors.joinToString("\n")
                    )
                )
            }

            return
        }

        // Cegah request ganda
        if (_uiState.value.isLoading) {
            return
        }

        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true
            )

            try {

                val request = RegisterRequest(
                    name = state.name.trim(),
                    email = state.email.trim(),
                    password = state.password,
                    passwordConfirmation = state.confirmPassword
                )

                val response = repository.register(request)

                if (!response.token.isNullOrBlank()) {
                    TokenStorage.saveToken(response.token)
                }

                _uiState.value = _uiState.value.copy(
                    isLoading = false
                )

                _uiEvent.emit(
                    RegisterUiEvent.RegisterSuccess(
                        response.message
                    )
                )


            } catch (e: CancellationException) {
                throw e

            } catch (e: HttpException) {
                val message = when (e.code()) {
                    422 -> "Email sudah terdaftar atau data tidak valid."
                    409 -> "Akun dengan email tersebut sudah terdaftar."
                    else -> "Registrasi gagal. Silakan coba lagi."
                }

                _uiEvent.emit(
                    RegisterUiEvent.RegisterError(message)
                )

            } catch (e: IOException) {
                _uiEvent.emit(
                    RegisterUiEvent.RegisterError(
                        "Tidak dapat terhubung ke server. Periksa koneksi internet."
                    )
                )

            } catch (e: Exception) {
                _uiEvent.emit(
                    RegisterUiEvent.RegisterError(
                        "Terjadi kesalahan saat registrasi. Silakan coba lagi."
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