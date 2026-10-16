package com.example.tanimart.data.address

import com.google.gson.annotations.SerializedName

data class AddressSingleResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("address")
    val address: AddressResponse
)