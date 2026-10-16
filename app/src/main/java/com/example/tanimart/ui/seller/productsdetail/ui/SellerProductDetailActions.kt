package com.example.tanimart.ui.seller.productsdetail.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon

@Composable
fun SellerProductDetailActions(
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onEditClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = null
        )

        Text(
            text = "Edit Produk",
            modifier = Modifier.padding(start = 8.dp),
            style = MaterialTheme.typography.labelLarge
        )
    }
}