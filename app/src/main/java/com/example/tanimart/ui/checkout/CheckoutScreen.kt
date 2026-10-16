package com.example.tanimart.ui.checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tanimart.ui.address.data.Address
import com.example.tanimart.ui.cart.data.CartItem
import com.example.tanimart.ui.checkout.data.CheckoutUiState
import com.example.tanimart.ui.checkout.ui.CheckoutAddressSection
import com.example.tanimart.ui.checkout.ui.CheckoutButton
import com.example.tanimart.ui.checkout.ui.CheckoutHeader
import com.example.tanimart.ui.checkout.ui.CheckoutProductSection
import com.example.tanimart.ui.checkout.ui.CheckoutSummary

@Composable
fun CheckoutScreen(
    items: List<CartItem>,
    address: Address?,
    uiState: CheckoutUiState,
    onBackClick: () -> Unit,
    onAddressClick: () -> Unit,
    onPaymentClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 8.dp
        ),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            CheckoutHeader(
                onBackClick = onBackClick
            )
        }

        item {
            CheckoutAddressSection(
                address = address,
                onAddressClick = onAddressClick
            )

            if (address == null) {
                Text(
                    text = "Alamat pengiriman belum tersedia. " +
                            "Silakan pilih atau tambahkan alamat " +
                            "sebelum melanjutkan pembayaran."
                )
            }
        }

        item {
            CheckoutProductSection(
                items = items
            )
        }

        item {
            CheckoutSummary(
                items = items
            )
        }

        item {
            CheckoutButton(
                onClick = {
                    if (address != null) {
                        onPaymentClick()
                    } else {
                        onAddressClick()
                    }
                },
                isLoading = uiState.isLoading
            )
        }
    }
}
