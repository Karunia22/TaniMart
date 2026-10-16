
package com.example.tanimart.ui.order

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tanimart.data.order.OrderDto
import com.example.tanimart.data.order.OrderItemDto
import com.example.tanimart.ui.order.state.OrderDetailViewModel
import com.example.tanimart.ui.order.ui.OrderHeader

@Composable
fun OrderDetailScreen(
    orderId: Int,
    viewModel: OrderDetailViewModel,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(orderId) {
        viewModel.loadOrder(orderId)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        OrderHeader(onBackClick = onBackClick)

        when {
            uiState.isLoading -> {
                Text(
                    text = "Memuat detail pesanan...",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            uiState.errorMessage != null -> {
                Text(
                    text = uiState.errorMessage.orEmpty(),
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.error
                )
            }

            uiState.order == null -> {
                Text(
                    text = "Pesanan tidak ditemukan",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            else -> {
                OrderDetailContent(order = uiState.order!!)
            }
        }
    }
}

@Composable
private fun OrderDetailContent(
    order: OrderDto
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Detail Pesanan",
                style = MaterialTheme.typography.titleLarge
            )
        }

        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Nomor Pesanan",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = order.nomorOrder,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        item {
            Text(
                text = "Informasi Produk",
                style = MaterialTheme.typography.titleMedium
            )
        }

        items(
            count = order.items.size,
            key = { index -> order.items[index].id }
        ) { index ->
            OrderDetailProduct(order.items[index])
        }

        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Status Pesanan",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = order.status.toOrderStatusLabel(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Total Pembayaran",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = order.totalHarga.toRupiah(),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun OrderDetailProduct(
    item: OrderItemDto
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = item.namaProduk,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "Jumlah: ${item.jumlah}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Harga satuan: ${item.harga.toRupiah()}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Subtotal: ${item.subtotal.toRupiah()}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

private fun String.toOrderStatusLabel(): String {
    return when (lowercase()) {
        "menunggu_pembayaran" -> "Menunggu Pembayaran"
        "diproses" -> "Diproses"
        "selesai" -> "Selesai"
        "dibatalkan" -> "Dibatalkan"
        else -> replace("_", " ")
            .replaceFirstChar { it.uppercase() }
    }
}

private fun Double.toRupiah(): String {
    return "Rp " + String.format("%,.0f", this)
        .replace(',', '.')
}
