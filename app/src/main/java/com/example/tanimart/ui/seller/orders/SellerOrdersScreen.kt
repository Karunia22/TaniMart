package com.example.tanimart.ui.seller.orders

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tanimart.ui.seller.orders.state.SellerOrdersViewModel
import com.example.tanimart.ui.seller.orders.ui.SellerOrdersContent

@Composable
fun SellerOrdersScreen(
    onOrderClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: SellerOrdersViewModel = viewModel()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SellerOrdersContent(
        uiState = uiState,
        onFilterSelected = viewModel::selectFilter,
        onRetry = viewModel::loadOrders,
        onOrderClick = onOrderClick,
        onBackClick = onBackClick,
        onMarkAsShipped = viewModel::markOrderAsShipped,
        modifier = modifier,
    )
}
