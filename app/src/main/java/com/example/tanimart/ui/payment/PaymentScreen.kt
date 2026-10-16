package com.example.tanimart.ui.payment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tanimart.ui.payment.state.PaymentViewModel
import com.example.tanimart.ui.payment.ui.PaymentActionButton
import com.example.tanimart.ui.payment.ui.PaymentBankSelector
import com.example.tanimart.ui.payment.ui.PaymentHeader
import com.example.tanimart.ui.payment.ui.PaymentInstructionCard
import com.example.tanimart.ui.payment.ui.PaymentMethodSection

@Composable
fun PaymentScreen(
    orderId: Int,
    viewModel: PaymentViewModel,
    onBackClick: () -> Unit,
    onPaymentCreated: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var hasNavigated by rememberSaveable(orderId) {
        mutableStateOf(false)
    }

    LaunchedEffect(
        uiState.paymentResponse,
        uiState.orderId,
        orderId
    ) {
        if (
            uiState.paymentResponse != null &&
            uiState.orderId == orderId &&
            !hasNavigated
        ) {
            hasNavigated = true
            onPaymentCreated()
        }
    }

    var selectedMethod by rememberSaveable {
        mutableStateOf("transfer")
    }

    var selectedBank by rememberSaveable {
        mutableStateOf("BCA")
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            PaymentHeader(onBackClick = onBackClick)
        }

        item {
            Text("Nomor pesanan: #$orderId")
        }

        item {
            PaymentMethodSection(
                selectedMethod = selectedMethod,
                onMethodSelected = { selectedMethod = it }
            )
        }

        if (selectedMethod == "transfer") {
            item {
                PaymentBankSelector(
                    selectedBank = selectedBank,
                    onBankSelected = { selectedBank = it }
                )
            }
        }

        uiState.errorMessage?.let { message ->
            item {
                Text(text = message)
            }
        }

        item {
            PaymentActionButton(
                isLoading = uiState.isLoading,
                enabled = !uiState.isLoading &&
                        (uiState.paymentResponse == null ||
                                uiState.orderId != orderId),
                onClick = {
                    viewModel.createPayment(
                        orderId = orderId,
                        method = selectedMethod,
                        bank = if (selectedMethod == "transfer") {
                            selectedBank
                        } else {
                            null
                        }
                    )
                }
            )
        }
    }
}
