package com.example.tanimart.ui.order.data

data class OrderUiModel(
    val id: Int,
    val nomorOrder: String,
    val namaProduk: String,
    val jumlahItem: Int,
    val totalHarga: Long,
    val status: OrderStatus
)

enum class OrderStatus {
    MENUNGGU_PEMBAYARAN,
    DIPROSES,
    SELESAI,
    DIBATALKAN
}

enum class OrderFilter(val label: String) {
    SEMUA("Semua"),
    MENUNGGU("Menunggu"),
    DIPROSES("Diproses"),
    SELESAI("Selesai"),
    DIBATALKAN("Dibatalkan")
}