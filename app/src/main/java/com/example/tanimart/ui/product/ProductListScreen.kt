package com.example.tanimart.ui.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tanimart.data.product.Product
import com.example.tanimart.ui.home.ui.ProductGrid
import com.example.tanimart.ui.product.ui.TaniMartSearchBar

@Composable
fun ProductListScreen(
    products: List<Product>,
    onProductClick: (Int) -> Unit
) {
    var searchQuery by remember {
        mutableStateOf("")
    }

    var filteredProducts by remember {
        mutableStateOf(products)
    }

    LaunchedEffect(searchQuery, products) {
        filteredProducts = if (searchQuery.isBlank()) {
            products
        } else {
            products.filter { product ->
                product.name.contains(
                    searchQuery,
                    ignoreCase = true
                )
            }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 20.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            TaniMartSearchBar(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                }
            )
        }

        item {
            Text(
                text = "Produk",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        item {
            ProductGrid(
                products = filteredProducts,
                onProductClick = onProductClick
            )
        }
    }
}