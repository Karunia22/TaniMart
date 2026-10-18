package com.example.tanimart.ui.seller.orders.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tanimart.data.order.SellerOrderDto
import java.util.Locale

@Composable
fun SellerOrderCard(
    order: SellerOrderDto,
    onClick: (Int) -> Unit,
    onMarkAsShipped: (Int) -> Unit,
    isUpdating: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = { onClick(order.id) },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = order.nomorOrder ?: "Pesanan #${order.id}",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleSmall,
                )

                Text(
                    text = order.status,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelMedium,
                )
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = order.user?.name ?: "Pelanggan",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium,
            )

            Spacer(Modifier.height(12.dp))

            order.items.forEach { item ->
                Text(
                    text = "${item.product?.namaProduk ?: "Produk"} × ${item.jumlah}",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 3.dp),
                )
            }

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "Total pesanan",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall,
                )

                Text(
                    text = "Rp${
                        "%,.0f".format(
                            Locale("id", "ID"),
                            order.totalHarga,
                        )
                    }",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleSmall,
                )
            }

            if (order.status.equals("diproses", ignoreCase = true)) {
                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = {
                        onMarkAsShipped(order.id)
                    },
                    enabled = !isUpdating,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    if (isUpdating) {
                        CircularProgressIndicator(
                            modifier = Modifier.height(18.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                        Spacer(Modifier.height(4.dp))
                        Text("Memperbarui...")
                    } else {
                        Text("Tandai Dikirim")
                    }
                }
            }
        }
    }
}