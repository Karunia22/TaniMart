package com.example.tanimart.ui.seller

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tanimart.ui.product.state.SellerProductViewModel
import com.example.tanimart.ui.seller.components.SellerDashboardHeader
import com.example.tanimart.ui.seller.components.SellerDashboardMenuSection
import com.example.tanimart.ui.seller.components.SellerProductEmpty
import com.example.tanimart.ui.seller.components.SellerProductError
import com.example.tanimart.ui.seller.components.SellerProductLoading
import com.example.tanimart.ui.seller.components.SellerProductSection
import com.example.tanimart.ui.seller.components.SellerSummarySection
import com.example.tanimart.ui.seller.data.SellerDashboardMenu

@Composable
fun SellerDashboardScreen(
    viewModel: SellerProductViewModel,
    onMenuClick: (SellerDashboardMenu) -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(
            top = 16.dp,
            bottom = 24.dp
        ),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            SellerDashboardHeader()
        }

        item {
            SellerSummarySection(
                products = state.products
            )
        }

        item {
            SellerDashboardMenuSection(
                onMenuClick = onMenuClick
            )
        }

        item {
            when {
                state.isLoading -> {
                    SellerProductLoading()
                }

                state.errorMessage != null -> {
                    SellerProductError(
                        message = state.errorMessage
                            ?: "Gagal memuat produk",
                        onRetry = viewModel::loadProducts
                    )
                }

                state.products.isEmpty() -> {
                    SellerProductEmpty()
                }

                else -> {
                    SellerProductSection(
                        products = state.products
                    )
                }
            }
        }
    }
}