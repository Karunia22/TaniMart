package com.example.tanimart.ui.checkout.ui

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
fun CheckoutSummary(
    items: List<CartItem>,
    modifier: Modifier = Modifier
) {
    val subtotal = items.sumOf { item ->
        item.product.price * item.quantity
    }

    val totalQuantity = items.sumOf { item ->
        item.quantity
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Ringkasan Pembayaran",
            style = MaterialTheme.typography.titleMedium
        )

        SummaryRow(
            label = "Jumlah Produk",
            value = totalQuantity.toString()
        )

        SummaryRow(
            label = "Subtotal",
            value = formatRupiah(subtotal)
        )

        HorizontalDivider()

        SummaryRow(
            label = "Total",
            value = formatRupiah(subtotal),
            bold = true
        )
    }
}

@Composable
private fun SummaryRow(
    label: String,
    value: String,
    bold: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontWeight = if (bold) {
                FontWeight.SemiBold
            } else {
                FontWeight.Normal
            }
        )

        Text(
            text = value,
            color = if (bold) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurface
            },
            fontWeight = if (bold) {
                FontWeight.SemiBold
            } else {
                FontWeight.Normal
            }
        )
    }
}