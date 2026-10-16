
package com.example.tanimart.data.order

import com.google.gson.annotations.SerializedName

data class OrderListResponse(
    val message: String,
    val orders: List<OrderDto>
)

data class OrderDetailResponse(
    val message: String,
    val order: OrderDto
)

data class OrderDto(
    val id: Int,

    @SerializedName("nomor_order")
    val nomorOrder: String,

    @SerializedName("total_harga")
    val totalHarga: Double,

    val status: String,

    @SerializedName("address_id")
    val addressId: Int?,

    val address: AddressDto?,

    val items: List<OrderItemDto>
)

data class AddressDto(
    val id: Int,
    val nama: String? = null,
    val alamat: String? = null
)

data class OrderItemDto(
    val id: Int,
    @SerializedName("product_id")
    val productId: Int?,

    @SerializedName("nama_produk")
    val namaProduk: String,

    val harga: Double,
    val jumlah: Int,
    val subtotal: Double
)
