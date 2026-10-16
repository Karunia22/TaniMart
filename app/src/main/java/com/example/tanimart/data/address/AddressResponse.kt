package com.example.tanimart.data.address

import com.google.gson.annotations.SerializedName

data class AddressResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("nama_penerima")
    val recipientName: String,

    @SerializedName("no_telepon")
    val phone: String,

    @SerializedName("alamat")
    val address: String,

    @SerializedName("kota")
    val city: String,

    @SerializedName("provinsi")
    val province: String,

    @SerializedName("kode_pos")
    val postalCode: String,

    @SerializedName("is_primary")
    val isPrimary: Boolean
)