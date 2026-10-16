
package com.example.tanimart.ui.payment.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private val banks = listOf(
    "BCA",
    "BRI",
    "BNI",
    "MANDIRI"
)

@Composable
fun PaymentBankSelector(
    selectedBank: String,
    onBankSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Pilih Bank")

        banks.forEach { bank ->
            OutlinedButton(
                onClick = { onBankSelected(bank) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (selectedBank == bank) {
                        "✓ $bank"
                    } else {
                        bank
                    }
                )
            }
        }
    }
}
