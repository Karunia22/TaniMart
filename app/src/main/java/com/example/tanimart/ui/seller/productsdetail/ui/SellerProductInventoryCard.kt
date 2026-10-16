package com.example.tanimart.ui.seller.productsdetail.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SellerProductInventoryCard(
    stock: Int,
    weight: Int,
    status: String,
    sellerName: String?,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Informasi Produk",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InventoryInfoItem(
                    label = "Stok",
                    value = "$stock unit"
                )

                InventoryInfoItem(
                    label = "Berat",
                    value = "$weight gram"
                )
            }

            InventoryInfoItem(
                label = "Status Produk",
                value = when (status.lowercase()) {
                    "aktif" -> "Aktif"
                    "nonaktif" -> "Nonaktif"
                    else -> status
                }
            )

            InventoryInfoItem(
                label = "Penjual",
                value = sellerName
                    ?.takeIf { it.isNotBlank() }
                    ?: "Tidak diketahui"
            )
        }
    }
}

@Composable
private fun InventoryInfoItem(
    label: String,
    value: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
    }
}