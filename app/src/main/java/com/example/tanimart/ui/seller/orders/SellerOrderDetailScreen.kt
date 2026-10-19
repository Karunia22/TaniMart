package com.example.tanimart.ui.seller.orders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tanimart.ui.components.formatRupiah
import com.example.tanimart.ui.seller.orders.data.SellerOrderUiModel
import com.example.tanimart.ui.seller.orders.state.SellerOrdersViewModel
import com.example.tanimart.ui.seller.orders.ui.SellerOrdersEmptyState
import com.example.tanimart.ui.seller.orders.ui.SellerOrdersHeader

@Composable
fun SellerOrderDetailScreen(
    orderId: Int,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: SellerOrdersViewModel = viewModel()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val order = uiState.orders.firstOrNull { it.id == orderId }

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        SellerOrdersHeader(onBackClick = onBackClick)

        when {
            order != null -> {
                SellerOrderDetailContent(order = order)
            }

            uiState.isLoading -> {
                SellerOrdersEmptyState(
                    title = "Memuat pesanan",
                    message = "Mohon tunggu sebentar.",
                    isLoading = true,
                    modifier = Modifier.weight(1f),
                )
            }

            else -> {
                SellerOrdersEmptyState(
                    title = "Pesanan tidak ditemukan",
                    message = "Detail pesanan ini tidak tersedia.",
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun SellerOrderDetailContent(
    order: SellerOrderUiModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        DetailSection(title = "Nomor Pesanan") {
            Text(
                text = order.nomorOrder,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
            )
        }

        DetailSection(title = "Status") {
            Text(
                text = order.status.label,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        DetailSection(title = "Pelanggan") {
            Text(
                text = order.customerName,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        DetailSection(title = "Produk") {
            order.items.forEach { item ->
                Column(
                    modifier = Modifier.padding(vertical = 4.dp),
                ) {
                    Text(
                        text = item.namaProduk,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        text = "${item.jumlah} × ${formatRupiah(item.harga.toDouble())}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }

        DetailSection(title = "Total Pesanan") {
            Text(
                text = formatRupiah(order.totalHarga.toDouble()),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
private fun DetailSection(
    title: String,
    content: @Composable () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(Modifier.height(2.dp))
        content()
    }
}
