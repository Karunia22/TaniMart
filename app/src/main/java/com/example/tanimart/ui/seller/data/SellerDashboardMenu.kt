package com.example.tanimart.ui.seller.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Warehouse
import androidx.compose.ui.graphics.vector.ImageVector

enum class SellerDashboardMenu(
    val title: String,
    val description: String,
    val icon: ImageVector
) {
    PRODUCTS(
        title = "Produk",
        description = "Tambah dan kelola produk",
        icon = Icons.Default.Inventory2
    ),

    ORDERS(
        title = "Pesanan",
        description = "Kelola pesanan pembeli",
        icon = Icons.Default.ShoppingCart
    ),

    SALES(
        title = "Penjualan",
        description = "Ringkasan penjualan",
        icon = Icons.Default.BarChart
    ),

    STOCK(
        title = "Stok",
        description = "Pantau stok produk",
        icon = Icons.Default.Warehouse
    ),

    MY_STORE(
        title = "Toko Saya",
        description = "Informasi toko",
        icon = Icons.Default.Store
    ),

    SETTINGS(
        title = "Pengaturan",
        description = "Pengaturan toko",
        icon = Icons.Default.Settings
    )
}