package com.example.tanimart.ui.payment.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tanimart.data.payment.PaymentResponse

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PaymentInstructionCard(
    response: PaymentResponse,
    onPaymentExpired: () -> Unit
) {
    val payment = response.payment
    val instructions = response.instructions

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Instruksi Pembayaran")

            PaymentCountdown(
                expiresAt = response.expiresAt,
                onExpired = onPaymentExpired
            )

            Text("Metode: ${payment.metode.uppercase()}")

            if (payment.metode == "transfer") {
                Text("Bank: ${instructions.bank ?: payment.bank ?: "-"}")
                Text(
                    "Nomor rekening: ${
                        instructions.nomorRekening
                            ?: "Belum tersedia (demo)"
                    }"
                )
                Text(
                    "Nama penerima: ${
                        instructions.namaPenerima ?: "TaniMart Demo"
                    }"
                )
            } else {
                val qrContent = instructions.qrCode

                if (!qrContent.isNullOrBlank()) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("Pindai Kode QR")

                        QrCodeImage(
                            content = qrContent
                        )

                        Text(
                            "Mode demonstrasi — pemindaian tidak memindahkan dana."
                        )
                    }
                } else {
                    Text(
                        "Kode QR belum tersedia. Silakan coba lagi."
                    )
                }
            }

            Text(instructions.petunjuk)
            Text("Referensi: ${payment.referensi}")
            Text("Status: ${payment.status}")
        }
    }
}