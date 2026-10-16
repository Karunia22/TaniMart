package com.example.tanimart.ui.seller.products.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerProductsTopBar(
    onBackClick: () -> Unit,
    onAddProductClick: () -> Unit
) {
    TopAppBar(
        title = { Text("Produk Saya") },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Kembali"
                )
            }
        },
        actions = {
            IconButton(onClick = onAddProductClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah produk"
                )
            }
        }
    )
}