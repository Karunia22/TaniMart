package com.example.tanimart.ui.seller.productsdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tanimart.data.product.Product
import com.example.tanimart.ui.product.state.ProductDetailViewModel
import com.example.tanimart.ui.seller.productsdetail.ui.SellerProductDetailActions
import com.example.tanimart.ui.seller.productsdetail.ui.SellerProductDetailTopBar
import com.example.tanimart.ui.seller.productsdetail.ui.SellerProductImageGallery
import com.example.tanimart.ui.seller.productsdetail.ui.SellerProductInfoSection
import com.example.tanimart.ui.seller.productsdetail.ui.SellerProductInventoryCard

@Composable
fun SellerProductDetailScreen(
    productId: Int,
    onBackClick: () -> Unit,
    onEditClick: (Product) -> Unit,
    viewModel: ProductDetailViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
    }

    Scaffold(
        topBar = {
            SellerProductDetailTopBar(
                onBackClick = onBackClick,
                onEditClick = {
                    uiState.product?.let(onEditClick)
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator()
                }

                uiState.product != null -> {
                    val product = uiState.product!!

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(vertical = 8.dp),
                        verticalArrangement = Arrangement.Top
                    ) {
                        SellerProductImageGallery(
                            imageUrls = product.images.ifEmpty {
                                listOfNotNull(product.imageUrl)
                            },
                            productName = product.name,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        SellerProductInfoSection(
                            productName = product.name,
                            productPrice = product.price,
                            productDescription = product.description
                        )

                        SellerProductInventoryCard(
                            stock = product.stock,
                            weight = product.weight,
                            status = product.status,
                            sellerName = product.sellerName
                        )

                        SellerProductDetailActions(
                            onEditClick = {
                                onEditClick(product)
                            }
                        )
                    }
                }

                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = uiState.errorMessage
                                ?: "Produk tidak ditemukan.",
                            style = MaterialTheme.typography.bodyLarge
                        )

                        TextButton(
                            onClick = {
                                viewModel.loadProduct(productId)
                            }
                        ) {
                            Text("Coba Lagi")
                        }
                    }
                }
            }
        }
    }
}