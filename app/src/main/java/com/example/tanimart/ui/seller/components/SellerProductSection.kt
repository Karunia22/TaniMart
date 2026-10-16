package com.example.tanimart.ui.seller.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tanimart.data.product.Product

@Composable
fun SellerProductSection(
    products: List<Product>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Produk Saya",
            style = MaterialTheme.typography.titleLarge
        )

        products.forEach { product ->
            SellerProductCard(
                product = product,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}