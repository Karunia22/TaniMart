package com.example.tanimart.ui.seller.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SellerProductEmpty() {
    Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Belum ada produk",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = "Produk yang kamu tambahkan akan tampil di sini.",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}