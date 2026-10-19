package com.example.tanimart.ui.seller.orders.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tanimart.ui.order.data.OrderFilter
import com.example.tanimart.ui.seller.orders.data.SellerOrdersUiState

@Composable
fun SellerOrdersContent(
    uiState: SellerOrdersUiState,
    onFilterSelected: (OrderFilter) -> Unit,
    onRetry: () -> Unit,
    onOrderClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    onMarkAsShipped: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .padding(top = 24.dp),
    ) {
        SellerOrdersHeader(
            onBackClick
        )

        Spacer(Modifier.height(20.dp))

        SellerOrdersStatusFilter(
            selectedFilter = uiState.selectedFilter,
            onFilterSelected = onFilterSelected,
        )

        Spacer(Modifier.height(16.dp))

        when {
            uiState.isLoading && uiState.orders.isEmpty() -> {
                SellerOrdersEmptyState(
                    title = "Memuat pesanan",
                    message = "Mohon tunggu sebentar.",
                    isLoading = true,
                    modifier = Modifier.weight(1f),
                )
            }

            uiState.error != null && uiState.orders.isEmpty() -> {
                SellerOrdersEmptyState(
                    title = "Gagal memuat pesanan",
                    message = uiState.error,
                    actionLabel = "Coba lagi",
                    onAction = onRetry,
                    modifier = Modifier.weight(1f),
                )
            }

            uiState.filteredOrders.isEmpty() -> {
                SellerOrdersEmptyState(
                    title = "Belum ada pesanan",
                    message = "Pesanan untuk produk tokomu akan muncul di sini.",
                    modifier = Modifier.weight(1f),
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(
                        items = uiState.filteredOrders,
                        key = { it.id },
                    ) { order ->
                        SellerOrderCard(
                            order = order,
                            onClick = onOrderClick,
                            onMarkAsShipped = onMarkAsShipped,
                            isUpdating = order.id in uiState.updatingOrderIds,
                        )
                    }
                }
            }
        }
    }
}