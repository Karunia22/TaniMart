
package com.example.tanimart.ui.order

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tanimart.ui.order.state.OrderViewModel
import com.example.tanimart.ui.order.ui.OrderCard
import com.example.tanimart.ui.order.ui.OrderEmptyState
import com.example.tanimart.ui.order.ui.OrderError
import com.example.tanimart.ui.order.ui.OrderFilterBar
import com.example.tanimart.ui.order.ui.OrderHeader
import com.example.tanimart.ui.order.ui.OrderLoading

@Composable
fun OrderScreen(
    viewModel: OrderViewModel,
    onBackClick: () -> Unit,
    onOrderClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val filterStatus = uiState.selectedFilter.status

    val filteredOrders = uiState.orders.filter { order ->
        filterStatus == null || order.status == filterStatus
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 8.dp)
    ) {
        OrderHeader(
            onBackClick = onBackClick
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OrderFilterBar(
                selectedFilter = uiState.selectedFilter,
                onFilterSelected = viewModel::selectFilter
            )

            when {
                uiState.isLoading -> {
                    OrderLoading()
                }

                uiState.errorMessage != null -> {
                    OrderError(
                        message = uiState.errorMessage.orEmpty(),
                        onRetry = viewModel::retry
                    )
                }

                filteredOrders.isEmpty() -> {
                    OrderEmptyState()
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        contentPadding = PaddingValues(
                            top = 4.dp,
                            bottom = 24.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = filteredOrders,
                            key = { it.id }
                        ) { order ->
                            OrderCard(
                                order = order,
                                onClick = {
                                    onOrderClick(order.id)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
