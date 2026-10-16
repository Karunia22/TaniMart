package com.example.tanimart.ui.seller.products

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tanimart.data.product.Product
import com.example.tanimart.ui.product.state.SellerProductViewModel
import com.example.tanimart.ui.seller.products.ui.SellerProductsContent
import com.example.tanimart.ui.seller.products.ui.SellerProductsEmpty
import com.example.tanimart.ui.seller.products.ui.SellerProductsError
import com.example.tanimart.ui.seller.products.ui.SellerProductsLoading
import com.example.tanimart.ui.seller.products.ui.SellerProductsTopBar

@Composable
fun SellerProductsScreen(
    onBackClick: () -> Unit,
    onAddProductClick: () -> Unit,
    onProductClick: (Product) -> Unit,
    onEditProductClick: (Product) -> Unit,
    viewModel: SellerProductViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadProducts()
    }

    Scaffold(
        topBar = {
            SellerProductsTopBar(
                onBackClick = onBackClick,
                onAddProductClick = onAddProductClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
            when {
                state.isLoading -> {
                    SellerProductsLoading()
                }

                state.errorMessage != null -> {
                    SellerProductsError(
                        message = state.errorMessage
                            ?: "Gagal memuat produk",
                        onRetry = viewModel::loadProducts
                    )
                }

                state.products.isEmpty() -> {
                    SellerProductsEmpty(
                        onAddProductClick = onAddProductClick
                    )
                }

                else -> {
                    SellerProductsContent(
                        products = state.products,
                        onProductClick = onProductClick,
                        onEditProductClick = onEditProductClick
                    )
                }
            }
        }
    }
}