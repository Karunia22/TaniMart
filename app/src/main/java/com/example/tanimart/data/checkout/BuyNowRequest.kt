
package com.example.tanimart.data.checkout

import com.google.gson.annotations.SerializedName

data class BuyNowRequest(
    @SerializedName("product_id")
    val productId: Int,

    val quantity: Int,

    @SerializedName("address_id")
    val addressId: Int
)
