package com.example.tanimart.ui.payment.ui

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter

@Composable
fun QrCodeImage(
    content: String,
    modifier: Modifier = Modifier
) {
    val bitmap = remember(content) {
        runCatching {
            val matrix = MultiFormatWriter().encode(
                content,
                BarcodeFormat.QR_CODE,
                512,
                512
            )

            Bitmap.createBitmap(
                512,
                512,
                Bitmap.Config.ARGB_8888
            ).apply {
                for (x in 0 until 512) {
                    for (y in 0 until 512) {
                        setPixel(
                            x,
                            y,
                            if (matrix[x, y]) {
                                android.graphics.Color.BLACK
                            } else {
                                android.graphics.Color.WHITE
                            }
                        )
                    }
                }
            }
        }.getOrNull()
    }

    bitmap?.let {
        Image(
            bitmap = it.asImageBitmap(),
            contentDescription = "Kode QR pembayaran demonstrasi",
            modifier = modifier.size(220.dp)
        )
    }
}
