
package com.example.tanimart.data.order

import com.example.tanimart.ui.order.data.OrderStatus
import com.example.tanimart.ui.order.data.OrderUiModel

fun OrderDto.toUiModel(): OrderUiModel {
    return OrderUiModel(
        id = id,
        nomorOrder = nomorOrder,
        namaProduk = items.firstOrNull()?.namaProduk
            ?: "Produk tidak tersedia",
        jumlahItem = items.sumOf { it.jumlah },
        totalHarga = totalHarga.toLong(),
        status = when (status.lowercase()) {
            "menunggu_pembayaran" -> OrderStatus.MENUNGGU_PEMBAYARAN
            "diproses" -> OrderStatus.DIPROSES
            "selesai" -> OrderStatus.SELESAI
            "dibatalkan" -> OrderStatus.DIBATALKAN
            else -> OrderStatus.MENUNGGU_PEMBAYARAN
        }
    )
}
