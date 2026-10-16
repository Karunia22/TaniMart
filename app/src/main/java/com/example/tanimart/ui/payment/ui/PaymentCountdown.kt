
package com.example.tanimart.ui.payment.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import java.time.Instant
import java.time.format.DateTimeParseException

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PaymentCountdown(
    expiresAt: String,
    onExpired: () -> Unit,
    modifier: Modifier = Modifier
) {
    val expiryMillis = remember(expiresAt) {
        try {
            Instant.parse(expiresAt).toEpochMilli()
        } catch (_: DateTimeParseException) {
            0L
        }
    }

    var remainingMillis by remember(expiresAt) {
        mutableLongStateOf(
            (expiryMillis - System.currentTimeMillis()).coerceAtLeast(0L)
        )
    }

    LaunchedEffect(expiryMillis) {
        if (expiryMillis <= 0L) return@LaunchedEffect

        while (true) {
            val remaining =
                (expiryMillis - System.currentTimeMillis()).coerceAtLeast(0L)

            remainingMillis = remaining

            if (remaining == 0L) {
                onExpired()
                break
            }

            delay(1000L)
        }
    }

    val totalSeconds = (remainingMillis + 999L) / 1000L
    val minutes = totalSeconds / 60L
    val seconds = totalSeconds % 60L

    Text(
        text = if (expiryMillis <= 0L) {
            "Waktu pembayaran tidak tersedia"
        } else if (remainingMillis == 0L) {
            "Waktu pembayaran telah habis"
        } else {
            "Sisa waktu pembayaran: %02d:%02d".format(minutes, seconds)
        },
        color = if (remainingMillis <= 60_000L) {
            MaterialTheme.colorScheme.error
        } else {
            MaterialTheme.colorScheme.onSurface
        },
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier
    )
}
