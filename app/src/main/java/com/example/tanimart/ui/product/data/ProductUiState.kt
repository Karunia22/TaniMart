package com.example.tanimart.ui.product.data

import com.example.tanimart.data.product.Product

data class ProductUiState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)