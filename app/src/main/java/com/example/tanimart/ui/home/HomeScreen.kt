package com.example.tanimart.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tanimart.data.product.Product
import com.example.tanimart.ui.home.data.QuickMenu
import com.example.tanimart.ui.home.ui.ProductGrid
import com.example.tanimart.ui.home.ui.TaniMartHomeBanner
import com.example.tanimart.ui.home.ui.TaniMartHomeHeader
import com.example.tanimart.ui.home.ui.TaniMartQuickMenu

@Composable
fun HomeScreen(
    products: List<Product>,
    onProductClick: (Int) -> Unit,
    onMenuClick: (QuickMenu) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {
            TaniMartHomeHeader(
                name = "Arjis"
            )
        }

        item {
            TaniMartHomeBanner()
        }

        item {
            TaniMartQuickMenu(
                onMenuClick = onMenuClick
            )
        }

        item {
            Text(
                text = "Produk Pilihan",
                style = MaterialTheme.typography.titleLarge
            )
        }

        item {
            ProductGrid(
                products = products,
                onProductClick = onProductClick
            )
        }
    }
}