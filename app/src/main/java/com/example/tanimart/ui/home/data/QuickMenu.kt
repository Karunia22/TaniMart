package com.example.tanimart.ui.home.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

enum class QuickMenu(
    val label: String,
    val icon: ImageVector
) {
    CART(
        label = "Keranjang",
        icon = Icons.Default.ShoppingCart
    ),
    ORDER(
        label = "Pesanan",
        icon = Icons.Default.ReceiptLong
    ),
    ADDRESS(
        label = "Alamat",
        icon = Icons.Default.LocationOn
    )
}