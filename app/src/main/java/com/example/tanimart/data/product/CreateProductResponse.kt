package com.example.tanimart.data.product

import com.google.gson.annotations.SerializedName

data class CreateProductResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("product")
    val product: ProductResponse
)