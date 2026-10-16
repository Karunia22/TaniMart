package com.example.tanimart.ui.seller.products.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProductFormActions(
    isSaving: Boolean,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedButton(
            onClick = onCancel,
            enabled = !isSaving,
            modifier = Modifier.weight(1f)
        ) {
            Text("Batal")
        }

        Button(
            onClick = onSave,
            enabled = !isSaving,
            modifier = Modifier.weight(1f)
        ) {
            Text(if (isSaving) "Menyimpan..." else "Simpan")
        }
    }
}