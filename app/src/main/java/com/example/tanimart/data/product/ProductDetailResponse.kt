
package com.example.tanimart.data.product

import com.google.gson.annotations.SerializedName

data class ProductDetailResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("product")
    val product: ProductResponse
)
