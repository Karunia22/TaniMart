
package com.example.tanimart.data.checkout

import com.google.gson.annotations.SerializedName

data class CheckoutResponse(
    val message: String,
    val order: OrderDto
)

data class OrderDto(
    val id: Int,

    @SerializedName("nomor_order")
    val nomorOrder: String,

    @SerializedName("total_harga")
    val totalHarga: String,

    val status: String
)
