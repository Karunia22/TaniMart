package com.example.tanimart.ui.profile.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Store
import androidx.compose.ui.graphics.vector.ImageVector

enum class ProfileMenu(
    val label: String,
    val icon: ImageVector
) {
    ACCOUNT(
        label = "Informasi Akun",
        icon = Icons.Default.Edit
    ),

    ADDRESS(
        label = "Alamat",
        icon = Icons.Default.LocationOn
    ),

    ORDER(
        label = "Pesanan",
        icon = Icons.Default.ReceiptLong
    ),
    TOKO_SAYA(
        label = "Toko Saya",
        icon = Icons.Default.Store
    ),
    LOGOUT(
        label = "Keluar",
        icon = Icons.Default.Logout
    )
}