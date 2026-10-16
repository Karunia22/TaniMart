package com.example.tanimart.ui.cart.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tanimart.ui.cart.data.CartItem
import com.example.tanimart.ui.components.formatRupiah
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CartSummary(
    items: List<CartItem>,
    modifier: Modifier = Modifier
) {
    val total = items
        .filter { it.selected }
        .sumOf { item ->
            item.product.price * item.quantity
        }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Ringkasan",
            style = MaterialTheme.typography.titleMedium
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Jumlah produk"
            )

            Text(
                text = items
                    .filter { it.selected }
                    .sumOf { it.quantity }
                    .toString()
            )
        }

        HorizontalDivider()

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total",
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = formatRupiah(total),
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
