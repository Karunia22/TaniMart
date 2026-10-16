package com.example.tanimart.ui.seller.products.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProductFormFields(
    name: String,
    onNameChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    price: String,
    onPriceChange: (String) -> Unit,
    stock: String,
    onStockChange: (String) -> Unit,
    weight: String,
    onWeightChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { androidx.compose.material3.Text("Nama produk") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChange,
            label = { androidx.compose.material3.Text("Deskripsi") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        OutlinedTextField(
            value = price,
            onValueChange = onPriceChange,
            label = { androidx.compose.material3.Text("Harga (Rp)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = stock,
            onValueChange = onStockChange,
            label = { androidx.compose.material3.Text("Stok") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = weight,
            onValueChange = onWeightChange,
            label = { androidx.compose.material3.Text("Berat (gram)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
    }
}