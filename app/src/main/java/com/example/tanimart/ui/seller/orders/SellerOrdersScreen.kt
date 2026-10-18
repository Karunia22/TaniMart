package com.example.tanimart.ui.seller.orders

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tanimart.data.order.OrderRepository
import com.example.tanimart.ui.seller.orders.state.SellerOrdersViewModel
import com.example.tanimart.ui.seller.orders.state.SellerOrdersViewModelFactory
import com.example.tanimart.ui.seller.orders.ui.SellerOrdersContent

@Composable
fun SellerOrdersScreen(
    repository: OrderRepository,
    onOrderClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val factory = remember(repository) {
        SellerOrdersViewModelFactory(repository)
    }

    val viewModel: SellerOrdersViewModel = viewModel(
        factory = factory,
    )

    val uiState by viewModel.uiState.collectAsState()

    SellerOrdersContent(
        uiState = uiState,
        onStatusSelected = viewModel::selectStatus,
        onRetry = viewModel::loadOrders,
        onOrderClick = onOrderClick,
        onBackClick = onBackClick,
        onMarkAsShipped = viewModel::markOrderAsShipped,
        modifier = modifier,
    )
}