
package com.example.tanimart.ui.payment.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun PaymentMethodSection(
    selectedMethod: String,
    onMethodSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Pilih Metode Pembayaran")

        PaymentMethodOption(
            title = "Transfer Bank",
            description = "Pilih bank yang tersedia",
            selected = selectedMethod == "transfer",
            onClick = { onMethodSelected("transfer") }
        )

        PaymentMethodOption(
            title = "QRIS",
            description = "Pembayaran QRIS (simulasi)",
            selected = selectedMethod == "qris",
            onClick = { onMethodSelected("qris") }
        )
    }
}

@Composable
private fun PaymentMethodOption(
    title: String,
    description: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                role = Role.RadioButton,
                onClick = onClick
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        RadioButton(
            selected = selected,
            onClick = null
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(title)
            Text(description)
        }
    }
}
