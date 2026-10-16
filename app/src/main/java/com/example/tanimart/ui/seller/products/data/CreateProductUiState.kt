package com.example.tanimart.ui.seller.products.data

import com.example.tanimart.data.product.Product

data class CreateProductUiState(
    val isSaving: Boolean = false,
    val isSuccess: Boolean = false,
    val message: String? = null,
    val product: Product? = null
)