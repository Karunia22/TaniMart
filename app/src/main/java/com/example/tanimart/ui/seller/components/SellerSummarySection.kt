package com.example.tanimart.ui.seller.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tanimart.data.product.Product

@Composable
fun SellerSummarySection(
    products: List<Product>
) {
    val activeProducts = products.count {
        it.status == "aktif"
    }

    val totalStock = products.sumOf {
        it.stock
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SellerSummaryCard(
                title = "Total Produk",
                value = products.size.toString(),
                modifier = Modifier.weight(1f)
            )

            SellerSummaryCard(
                title = "Produk Aktif",
                value = activeProducts.toString(),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(12.dp))

        SellerSummaryCard(
            title = "Total Stok",
            value = totalStock.toString(),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
