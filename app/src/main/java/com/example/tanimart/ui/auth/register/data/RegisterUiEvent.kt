package com.example.tanimart.ui.auth.register.data

sealed interface RegisterUiEvent {

    data class ShowValidationError(
        val message: String
    ) : RegisterUiEvent

    data class RegisterSuccess(
        val message: String
    ) : RegisterUiEvent

    data class RegisterError(
        val message: String
    ) : RegisterUiEvent
}