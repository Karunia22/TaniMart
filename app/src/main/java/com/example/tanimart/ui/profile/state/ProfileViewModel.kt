
package com.example.tanimart.ui.profile.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanimart.data.auth.SessionRepository
import com.example.tanimart.data.network.RetrofitClient
import com.example.tanimart.ui.profile.data.ProfileUiState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val repository =
        SessionRepository(RetrofitClient.userApi)

    private val _uiState =
        MutableStateFlow(ProfileUiState())

    val uiState = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    fun refreshProfile() {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val user = repository.getUser()

                _uiState.value = ProfileUiState(
                    name = user.name,
                    email = user.email,
                    role = user.role,
                    isLoading = false,
                    errorMessage = null
                )

            } catch (e: CancellationException) {
                throw e

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage =
                        "Gagal memuat informasi akun. Periksa koneksi dan coba lagi."
                )
            }
        }
    }
}
