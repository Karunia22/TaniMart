package com.example.tanimart.ui.account.state


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanimart.data.auth.AccountRepository
import com.example.tanimart.data.auth.AccountResponse
import com.example.tanimart.ui.account.data.AccountUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class AccountViewModel(
    private val repository: AccountRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AccountUiState())
    val uiState: StateFlow<AccountUiState> =
        _uiState.asStateFlow()

    fun loadAccount() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null,
                message = null
            )

            repository.getAccount()
                .onSuccess { account ->
                    _uiState.value = _uiState.value.copy(
                        account = account,
                        isLoading = false
                    )
                }
                .onFailure { throwable ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = throwable.message
                            ?: "Gagal memuat informasi akun."
                    )
                }
        }
    }

    fun updateName(name: String) {
        if (name.isBlank()) {
            _uiState.value = _uiState.value.copy(
                error = "Nama tidak boleh kosong.",
                message = null
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isSaving = true,
                error = null,
                message = null
            )

            repository.updateName(name.trim())
                .onSuccess { result ->
                    _uiState.value = _uiState.value.copy(
                        isSaving = false,
                        message = result.message
                    )
                    loadAccount()
                }
                .onFailure { throwable ->
                    _uiState.value = _uiState.value.copy(
                        isSaving = false,
                        error = throwable.message
                            ?: "Gagal memperbarui nama."
                    )
                }
        }
    }

    fun updatePassword(
        currentPassword: String,
        newPassword: String,
        confirmation: String
    ) {
        if (currentPassword.isBlank()) {
            setError("Kata sandi saat ini wajib diisi.")
            return
        }

        if (newPassword.length < 8) {
            setError("Kata sandi baru minimal 8 karakter.")
            return
        }

        if (newPassword != confirmation) {
            setError("Konfirmasi kata sandi tidak cocok.")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isSaving = true,
                error = null,
                message = null
            )

            repository.updatePassword(
                currentPassword = currentPassword,
                newPassword = newPassword,
                passwordConfirmation = confirmation
            )
                .onSuccess { result ->
                    _uiState.value = _uiState.value.copy(
                        isSaving = false,
                        message = result.message
                    )
                }
                .onFailure { throwable ->
                    _uiState.value = _uiState.value.copy(
                        isSaving = false,
                        error = throwable.message
                            ?: "Gagal mengubah kata sandi."
                    )
                }
        }
    }

    fun clearFeedback() {
        _uiState.value = _uiState.value.copy(
            message = null,
            error = null
        )
    }

    private fun setError(message: String) {
        _uiState.value = _uiState.value.copy(
            error = message,
            message = null
        )
    }
}
