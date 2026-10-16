package com.example.tanimart.data.cart

import com.google.gson.annotations.SerializedName

data class AddCartItemRequest(
    @SerializedName("product_id")
    val productId: Int,
    val jumlah: Int
)

data class UpdateCartItemRequest(
    val jumlah: Int
)