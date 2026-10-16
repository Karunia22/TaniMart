
package com.example.tanimart.data.checkout

import com.google.gson.annotations.SerializedName

data class CheckoutRequest(
    @SerializedName("address_id")
    val addressId: Int,

    @SerializedName("cart_item_ids")
    val cartItemIds: List<Int>
)
