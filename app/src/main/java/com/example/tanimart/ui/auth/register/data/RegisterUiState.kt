package com.example.tanimart.ui.auth.register.data

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",

    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,

    val isLoading: Boolean = false,

    val showValidationDialog: Boolean = false,
    val validationMessage: String = ""
)
