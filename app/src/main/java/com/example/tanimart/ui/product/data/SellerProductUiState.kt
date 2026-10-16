package com.example.tanimart.ui.product.data

import com.example.tanimart.data.product.Product

data class SellerProductUiState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)