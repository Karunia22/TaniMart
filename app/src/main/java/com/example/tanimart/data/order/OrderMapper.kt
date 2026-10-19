package com.example.tanimart.data.order

import com.example.tanimart.ui.order.data.OrderUiModel
import com.example.tanimart.ui.order.data.toOrderStatus
import com.example.tanimart.ui.seller.orders.data.SellerOrderItemUiModel
import com.example.tanimart.ui.seller.orders.data.SellerOrderUiModel

fun OrderDto.toUiModel(): OrderUiModel {
    return OrderUiModel(
        id = id,
        nomorOrder = nomorOrder,
        namaProduk = items.firstOrNull()?.namaProduk
            ?: "Produk tidak tersedia",
        jumlahItem = items.sumOf { it.jumlah },
        totalHarga = totalHarga.toLong(),
        status = status.toOrderStatus()
    )
}

fun SellerOrderDto.toUiModel(): SellerOrderUiModel {
    return SellerOrderUiModel(
        id = id,
        nomorOrder = nomorOrder ?: "Pesanan #$id",
        customerName = user?.name ?: "Pelanggan",
        status = status.toOrderStatus(),
        totalHarga = totalHarga.toLong(),
        items = items.map { it.toUiModel() }
    )
}

private fun SellerOrderItemDto.toUiModel(): SellerOrderItemUiModel {
    return SellerOrderItemUiModel(
        id = id,
        namaProduk = product?.namaProduk ?: "Produk",
        jumlah = jumlah,
        harga = harga.toLong()
    )
}
