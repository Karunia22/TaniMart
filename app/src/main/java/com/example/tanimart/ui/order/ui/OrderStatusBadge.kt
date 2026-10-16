
package com.example.tanimart.ui.order.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tanimart.ui.order.data.OrderStatus

@Composable
fun OrderStatusBadge(
    status: OrderStatus
) {
    val colors = MaterialTheme.colorScheme

    val (label, containerColor, contentColor) = when (status) {
        OrderStatus.MENUNGGU_PEMBAYARAN -> Triple(
            "Menunggu pembayaran",
            colors.secondaryContainer,
            colors.onSecondaryContainer
        )

        OrderStatus.DIPROSES -> Triple(
            "Sedang diproses",
            colors.primaryContainer,
            colors.onPrimaryContainer
        )

        OrderStatus.SELESAI -> Triple(
            "Selesai",
            colors.tertiaryContainer,
            colors.onTertiaryContainer
        )

        OrderStatus.DIBATALKAN -> Triple(
            "Dibatalkan",
            colors.errorContainer,
            colors.onErrorContainer
        )
    }

    Surface(
        modifier = Modifier,
        shape = RoundedCornerShape(50),
        color = containerColor,
        contentColor = contentColor
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 6.dp
            ),
            style = MaterialTheme.typography.labelMedium
        )
    }
}
