package com.example.tanimart.ui.seller.orders.data

import com.example.tanimart.ui.order.data.OrderFilter

data class SellerOrdersUiState(
    val orders: List<SellerOrderUiModel> = emptyList(),
    val selectedFilter: OrderFilter = OrderFilter.SEMUA,
    val isLoading: Boolean = false,
    val error: String? = null,
    val updatingOrderIds: Set<Int> = emptySet(),
) {
    val filteredOrders: List<SellerOrderUiModel>
        get() {
            val status = selectedFilter.status ?: return orders
            return orders.filter { it.status == status }
        }
}
