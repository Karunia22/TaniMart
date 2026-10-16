package com.example.tanimart.ui.order.data

data class OrderUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val orders: List<OrderUiModel> = emptyList(),
    val selectedFilter: OrderFilter = OrderFilter.SEMUA
)