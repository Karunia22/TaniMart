package com.example.tanimart.ui.seller.orders.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

val sellerOrderStatuses = listOf(
    "Semua",
    "Menunggu Pembayaran",
    "Diproses",
    "Dikirim",
    "Selesai",
    "Dibatalkan",
)

@Composable
fun SellerOrdersStatusFilter(
    selectedStatus: String,
    onStatusSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(sellerOrderStatuses) { status ->
            FilterChip(
                selected = selectedStatus == status,
                onClick = { onStatusSelected(status) },
                label = { Text(status) },
            )
        }
    }
}