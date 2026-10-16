package com.example.tanimart.ui.navigation.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
) {
    HOME(
        label = "Beranda",
        icon = Icons.Default.Home,
        route = "home"
    ),

    PRODUCT(
        label = "Produk",
        icon = Icons.Default.ShoppingBag,
        route = "products"
    ),

    PROFILE(
        label = "Profil",
        icon = Icons.Default.Person,
        route = "profile"
    )
}