package com.example.tanimart.data.product

import com.google.gson.annotations.SerializedName

data class CreateProductRequest(
    @SerializedName("nama_produk")
    val name: String,

    @SerializedName("deskripsi")
    val description: String?,

    @SerializedName("harga")
    val price: Double,

    @SerializedName("stok")
    val stock: Int,

    @SerializedName("berat")
    val weight: Int,

    @SerializedName("status")
    val status: String = "aktif"
)