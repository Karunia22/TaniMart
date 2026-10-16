package com.example.tanimart.ui.product.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tanimart.data.product.Product
import com.example.tanimart.ui.components.TaniMartProductDetail
import kotlinx.coroutines.launch
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tanimart.ui.product.state.ProductDetailViewModel

@Composable
fun ProductDetailScreen(
    productId: Int,
    onBackClick: () -> Unit,
    onAddToCartClick: (Product, Int) -> Unit,
    onBuyNowClick: (Product, Int) -> Unit,
    viewModel: ProductDetailViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
    }

    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        uiState.errorMessage != null -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(uiState.errorMessage ?: "Gagal memuat produk")

                Button(
                    onClick = {
                        viewModel.loadProduct(productId)
                    }
                ) {
                    Text("Coba lagi")
                }

                TextButton(
                    onClick = onBackClick
                ) {
                    Text("Kembali")
                }
            }
        }

        uiState.product != null -> {
            val product = uiState.product!!

            var quantity by remember(product.id) {
                mutableIntStateOf(1)
            }

            val snackbarHostState = remember {
                SnackbarHostState()
            }

            val scope = rememberCoroutineScope()


            Scaffold(
                snackbarHost = {
                    SnackbarHost(snackbarHostState)
                },
                bottomBar = {
                    Surface(
                        tonalElevation = 3.dp,
                        shadowElevation = 4.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .navigationBarsPadding()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            OutlinedButton(
                                onClick = {
                                    if (product.stock > 0 &&
                                        quantity <= product.stock
                                    ) {
                                        onAddToCartClick(product, quantity)

                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                "${product.name} berhasil ditambahkan ke keranjang"
                                            )
                                        }
                                    }
                                },
                                enabled = product.stock > 0 && quantity > 0,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Keranjang")
                            }

                            Button(
                                onClick = {
                                    if (product.stock > 0 &&
                                        quantity <= product.stock
                                    ) {
                                        onBuyNowClick(product, quantity)
                                    }
                                },
                                enabled = product.stock > 0 && quantity > 0,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Beli Sekarang")
                            }
                        }
                    }
                }
            ) { innerPadding ->

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentPadding = PaddingValues(
                        start = 0.dp,
                        top = 0.dp,
                        end = 0.dp,
                        bottom = 24.dp
                    )
                ) {
                    item(key = "back_button") {
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Kembali"
                            )
                        }
                    }

                    item(key = "product_detail") {
                        TaniMartProductDetail(
                            product = product,
                            quantity = quantity,
                            onDecrease = {
                                if (quantity > 1) quantity--
                            },
                            onIncrease = {
                                if (quantity < product.stock) quantity++
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp)
                        )
                    }
                }
            }
        }
    }
}
