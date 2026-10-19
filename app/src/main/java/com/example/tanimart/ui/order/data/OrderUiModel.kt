package com.example.tanimart.ui.order.data

data class OrderUiModel(
    val id: Int,
    val nomorOrder: String,
    val namaProduk: String,
    val jumlahItem: Int,
    val totalHarga: Long,
    val status: OrderStatus
)

enum class OrderStatus(val label: String) {
    MENUNGGU_PEMBAYARAN("Menunggu pembayaran"),
    DIPROSES("Sedang diproses"),
    DIKIRIM("Sedang dikirim"),
    SELESAI("Selesai"),
    DIBATALKAN("Dibatalkan")
}

/**
 * Mengubah status mentah dari backend menjadi [OrderStatus].
 *
 * Backend dapat mengirim status dengan format underscore
 * ("menunggu_pembayaran") maupun spasi ("Menunggu Pembayaran"),
 * sehingga keduanya dinormalisasi sebelum dicocokkan.
 */
fun String?.toOrderStatus(): OrderStatus {
    return when (this?.trim()?.lowercase()?.replace(" ", "_")) {
        "menunggu_pembayaran" -> OrderStatus.MENUNGGU_PEMBAYARAN
        "diproses" -> OrderStatus.DIPROSES
        "dikirim" -> OrderStatus.DIKIRIM
        "selesai" -> OrderStatus.SELESAI
        "dibatalkan" -> OrderStatus.DIBATALKAN
        else -> OrderStatus.MENUNGGU_PEMBAYARAN
    }
}

/**
 * Filter daftar pesanan. [status] bernilai null untuk filter "Semua".
 */
enum class OrderFilter(val label: String, val status: OrderStatus?) {
    SEMUA("Semua", null),
    MENUNGGU("Menunggu", OrderStatus.MENUNGGU_PEMBAYARAN),
    DIPROSES("Diproses", OrderStatus.DIPROSES),
    DIKIRIM("Dikirim", OrderStatus.DIKIRIM),
    SELESAI("Selesai", OrderStatus.SELESAI),
    DIBATALKAN("Dibatalkan", OrderStatus.DIBATALKAN)
}
