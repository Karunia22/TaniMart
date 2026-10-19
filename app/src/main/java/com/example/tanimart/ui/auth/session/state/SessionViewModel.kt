package com.example.tanimart.ui.auth.session.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanimart.data.auth.SessionRepository
import com.example.tanimart.data.network.RetrofitClient
import com.example.tanimart.data.network.TokenStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface SessionState {

    data object Checking : SessionState

    data object Authenticated : SessionState

    data object Unauthenticated : SessionState
}

class SessionViewModel : ViewModel() {

    private val repository = SessionRepository(
        RetrofitClient.userApi
    )

    private val _state =
        MutableStateFlow<SessionState>(SessionState.Checking)

    val state: StateFlow<SessionState> =
        _state.asStateFlow()

    init {
        checkSession()
    }

    fun refresh() {
        checkSession()
    }

    fun markAuthenticated() {
        _state.value = SessionState.Authenticated
    }

    fun markLoggedOut() {
        _state.value = SessionState.Unauthenticated
    }

    private fun checkSession() {

        val token = TokenStorage.getToken()

        if (token.isNullOrBlank()) {
            _state.value = SessionState.Unauthenticated
            return
        }

        viewModelScope.launch {

            try {
                repository.getUser()

                _state.value = SessionState.Authenticated

            } catch (e: retrofit2.HttpException) {
                if (e.code() == 401 || e.code() == 403) {
                    TokenStorage.clearToken()
                    _state.value = SessionState.Unauthenticated
                } else {
                    _state.value = SessionState.Authenticated
                }

            } catch (e: java.io.IOException) {
                _state.value = SessionState.Authenticated

            } catch (e: Exception) {
                TokenStorage.clearToken()
                _state.value = SessionState.Unauthenticated
            }
        }
    }
}