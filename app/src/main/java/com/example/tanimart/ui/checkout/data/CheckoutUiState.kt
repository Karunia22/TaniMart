package com.example.tanimart.ui.checkout.data

import com.example.tanimart.data.checkout.CheckoutResponse


data class CheckoutUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val checkoutResponse: CheckoutResponse? = null
)