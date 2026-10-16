
package com.example.tanimart.ui.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tanimart.ui.cart.data.CartItem
import com.example.tanimart.ui.cart.state.CartViewModel
import com.example.tanimart.ui.cart.ui.CartCheckoutButton
import com.example.tanimart.ui.cart.ui.CartHeader
import com.example.tanimart.ui.cart.ui.CartItemView
import com.example.tanimart.ui.cart.ui.CartSummary

@Composable
fun CartScreen(
    viewModel: CartViewModel,
    onBackClick: () -> Unit,
    onCheckoutClick: (List<CartItem>) -> Unit
) {
    val cartState by viewModel.cartState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val selectedItems = cartState.items.filter {
        it.selected
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            CartHeader(onBackClick = onBackClick)
        }

        // Pesan error koneksi atau server
        if (errorMessage != null) {
            item {
                Text(
                    text = errorMessage.orEmpty(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        if (isLoading && cartState.items.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        } else if (cartState.items.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (errorMessage != null) {
                            "Keranjang belum dapat dimuat. Periksa koneksi lalu coba lagi."
                        } else {
                            "Keranjang kamu masih kosong.\n" +
                                    "Yuk, pilih produk kopi yang kamu suka!"
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (errorMessage != null) {
                item {
                    CartCheckoutButton(
                        enabled = false,
                        onClick = {}
                    )
                }
            }
        } else {
            items(
                items = cartState.items,
                key = { it.product.id }
            ) { item ->
                CartItemView(
                    item = item,
                    onSelectionChange = {
                        viewModel.toggleSelection(item.product.id)
                    },
                    onIncrease = {
                        viewModel.increaseQuantity(item.product.id)
                    },
                    onDecrease = {
                        viewModel.decreaseQuantity(item.product.id)
                    },
                    onDelete = {
                        viewModel.removeFromCart(item.product.id)
                    }
                )
            }

            item {
                CartSummary(items = cartState.items)
            }

            item {
                CartCheckoutButton(
                    enabled = selectedItems.isNotEmpty() && !isLoading,
                    onClick = {
                        onCheckoutClick(selectedItems)
                    }
                )
            }
        }
    }
}