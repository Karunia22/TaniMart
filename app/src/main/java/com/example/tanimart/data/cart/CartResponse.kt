package com.example.tanimart.data.cart

import com.google.gson.annotations.SerializedName

data class CartResponse(
    val message: String,
    val cart: CartDto? = null
)

data class CartDto(
    val id: Int,
    val items: List<CartItemDto> = emptyList()
)

data class CartItemDto(
    val id: Int,
    @SerializedName("product_id")
    val productId: Int,
    val jumlah: Int,
    val product: CartProductDto
)

data class CartProductDto(
    val id: Int,
    @SerializedName("nama_produk")
    val namaProduk: String,
    val harga: String,
    val stok: Int,
    val berat: Int,
    val deskripsi: String? = null,
    val images: List<CartProductImageDto> = emptyList(),
    val seller: CartSellerDto? = null
)

data class CartProductImageDto(
    val id: Int,
    val url: String,
    @SerializedName("is_primary")
    val isPrimary: Int = 0
)

data class CartSellerDto(
    val id: Int,
    val name: String
)

data class CartMessageResponse(
    val message: String
)

data class CartItemResponse(
    val message: String,
    val item: CartItemDto? = null
)