package com.example.tanimart.ui.seller.stock.data

import com.example.tanimart.data.product.Product

data class StockUiState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val updatingProductId: Int? = null,
    val errorMessage: String? = null
) {
    companion object {
        const val LOW_STOCK_THRESHOLD = 5
    }
}
