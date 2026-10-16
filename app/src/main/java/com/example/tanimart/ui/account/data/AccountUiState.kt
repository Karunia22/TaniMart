package com.example.tanimart.ui.account.data

import com.example.tanimart.data.auth.AccountResponse


data class AccountUiState(
    val account: AccountResponse? = null,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val message: String? = null,
    val error: String? = null
)