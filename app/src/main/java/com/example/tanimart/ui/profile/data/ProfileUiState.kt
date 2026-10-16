package com.example.tanimart.ui.profile.data

data class ProfileUiState(
    val name: String = "",
    val email: String = "",
    val role: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)