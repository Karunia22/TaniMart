package com.example.tanimart.ui.auth.login.data

sealed interface LoginUiEvent {

    data class ShowValidationError(
        val message: String
    ) : LoginUiEvent

    data object LoginSuccess : LoginUiEvent
}