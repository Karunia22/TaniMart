package com.example.tanimart.ui.seller.products.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tanimart.data.product.Product
import com.example.tanimart.ui.seller.products.components.MySellerProductItem

@Composable
fun SellerProductsContent(
    products: List<Product>,
    onProductClick: (Product) -> Unit,
    onEditProductClick: (Product) -> Unit,
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

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = products,
                key = { product -> product.id }
            ) { product ->
                MySellerProductItem(
                    product = product,
                    onProductClick = {
                        onProductClick(product)
                    },
                    onEditClick = {
                        onEditProductClick(product)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}