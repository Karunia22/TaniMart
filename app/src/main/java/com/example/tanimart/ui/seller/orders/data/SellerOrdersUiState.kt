package com.example.tanimart.ui.seller.orders.data

import com.example.tanimart.data.order.SellerOrderDto

data class SellerOrdersUiState(
    val orders: List<SellerOrderDto> = emptyList(),
    val selectedStatus: String = "Semua",
    val isLoading: Boolean = false,
    val error: String? = null,
    val updatingOrderIds: Set<Int> = emptySet(),
) {
    val filteredOrders: List<SellerOrderDto>
        get() = if (selectedStatus == "Semua") {
            orders
        } else {
            orders.filter {
                it.status.equals(selectedStatus, ignoreCase = true)
            }
        }
}