package com.example.tanimart.ui.payment

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tanimart.data.payment.PaymentResponse
import com.example.tanimart.ui.payment.state.PaymentViewModel
import com.example.tanimart.ui.payment.ui.PaymentActionButton
import com.example.tanimart.ui.payment.ui.PaymentHeader
import com.example.tanimart.ui.payment.ui.PaymentInstructionCard

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PaymentInstructionScreen(
    orderId: Int,
    paymentResponse: PaymentResponse,
    viewModel: PaymentViewModel,
    onBackClick: () -> Unit,
    onPaymentSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var showSuccessDialog by remember(orderId) {
        mutableStateOf(false)
    }

    LaunchedEffect(uiState.successResponse) {
        if (uiState.successResponse != null) {
            showSuccessDialog = true
        }
    }
    BackHandler {
        onBackClick()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            PaymentHeader(
                onBackClick = onBackClick
            )
        }

        item {
            Text("Instruksi Pembayaran")
        }

        item {
            Text("Nomor pesanan: #$orderId")
        }

        item {
            PaymentInstructionCard(
                response = paymentResponse,
                onPaymentExpired = {
                    viewModel.clearError()
                }
            )
        }

        uiState.errorMessage?.let { message ->
            item {
                Text(text = message)
            }
        }

        if (uiState.successResponse == null) {
            item {
                PaymentActionButton(
                    isLoading = uiState.isLoading,
                    enabled = !uiState.isLoading,
                    label = "Simulasikan Pembayaran Berhasil",
                    onClick = {
                        viewModel.simulateSuccess(orderId)
                    }
                )
            }
        }
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = {},
            title = {
                Text("Pesanan Berhasil")
            },
            text = {
                Text(
                    "Pembayaran berhasil dikonfirmasi. " +
                            "Pesanan Anda sedang diproses."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showSuccessDialog = false
                        onPaymentSuccess()
                    }
                ) {
                    Text("Kembali")
                }
            }
        )
    }
}
