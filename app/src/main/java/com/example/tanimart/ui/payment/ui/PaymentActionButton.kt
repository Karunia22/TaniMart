
package com.example.tanimart.ui.payment.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PaymentActionButton(
    isLoading: Boolean,
    enabled: Boolean = true,
    label: String = "Konfirmasi Pembayaran",
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled && !isLoading,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Text(label)
        }
    }
}
