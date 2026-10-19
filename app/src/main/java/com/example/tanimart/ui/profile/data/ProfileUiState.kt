package com.example.tanimart.ui.profile.data

data class ProfileUiState(
    val name: String = "",
    val email: String = "",
    val role: String = "",
    val roles: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    val isSeller: Boolean
        get() = roles.contains("seller") || role == "seller"
}
