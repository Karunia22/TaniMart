package com.example.tanimart.data.order

import com.google.gson.annotations.SerializedName

data class SellerOrderListResponse(
    val message: String,
    val orders: List<SellerOrderDto>,
)

data class SellerOrderDto(
    val id: Int,

    @SerializedName("nomor_order")
    val nomorOrder: String? = null,

    @SerializedName("total_harga")
    val totalHarga: Double = 0.0,

    val status: String,

    val user: SellerOrderCustomerDto? = null,

    val address: AddressDto? = null,

    val items: List<SellerOrderItemDto> = emptyList(),
)

data class SellerOrderCustomerDto(
    val id: Int,
    val name: String,
    val email: String? = null,
)

data class SellerOrderItemDto(
    val id: Int,
    @SerializedName("product_id")
    val productId: Int? = null,

    val product: SellerOrderProductDto? = null,

    val jumlah: Int = 0,
    val harga: Double = 0.0,
)

data class SellerOrderProductDto(
    val id: Int,
    @SerializedName("nama_produk")
    val namaProduk: String,
)