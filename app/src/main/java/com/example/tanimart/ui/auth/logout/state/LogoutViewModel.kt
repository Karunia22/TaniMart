package com.example.tanimart.ui.auth.logout.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanimart.data.auth.LogoutRepository
import com.example.tanimart.data.network.RetrofitClient
import com.example.tanimart.data.network.TokenStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface LogoutState {

    data object Idle : LogoutState

    data object Loading : LogoutState

    data object Success : LogoutState

    data class Error(
        val message: String
    ) : LogoutState
}

class LogoutViewModel : ViewModel() {

    private val repository = LogoutRepository(
        RetrofitClient.logoutApi
    )

    private val _state =
        MutableStateFlow<LogoutState>(
            LogoutState.Idle
        )

    val state: StateFlow<LogoutState> =
        _state.asStateFlow()

    fun logout() {

        if (_state.value == LogoutState.Loading) {
            return
        }

        viewModelScope.launch {

            _state.value =
                LogoutState.Loading

            try {

                repository.logout()

                // Hapus token lokal setelah
                // logout berhasil di server.
                TokenStorage.clearToken()

                _state.value =
                    LogoutState.Success

            } catch (e: Exception) {

                /*
                 * Token tetap kita hapus.
                 *
                 * Kalau server gagal karena token
                 * sudah tidak valid, user tetap
                 * harus dianggap logout di aplikasi.
                 */
                TokenStorage.clearToken()

                _state.value =
                    LogoutState.Success
            }
        }
    }
}