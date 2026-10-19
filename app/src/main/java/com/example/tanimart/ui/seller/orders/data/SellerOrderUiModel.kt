package com.example.tanimart.ui.seller.orders.data

import com.example.tanimart.ui.order.data.OrderStatus

data class SellerOrderUiModel(
    val id: Int,
    val nomorOrder: String,
    val customerName: String,
    val status: OrderStatus,
    val totalHarga: Long,
    val items: List<SellerOrderItemUiModel>,
)

data class SellerOrderItemUiModel(
    val id: Int,
    val namaProduk: String,
    val jumlah: Int,
    val harga: Long,
)
