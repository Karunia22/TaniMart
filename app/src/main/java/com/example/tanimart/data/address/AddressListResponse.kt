package com.example.tanimart.data.address

import com.google.gson.annotations.SerializedName

data class AddressListResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("addresses")
    val addresses: List<AddressResponse>
)