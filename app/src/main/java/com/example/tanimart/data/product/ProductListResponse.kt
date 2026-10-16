package com.example.tanimart.data.product

import com.google.gson.annotations.SerializedName

data class ProductListResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("products")
    val products: List<ProductResponse>
)