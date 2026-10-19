package com.example.tanimart.data.seller

import com.google.gson.annotations.SerializedName

data class ShopDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("nama_toko")
    val namaToko: String,

    @SerializedName("slug")
    val slug: String? = null,

    @SerializedName("deskripsi")
    val deskripsi: String? = null,

    @SerializedName("logo")
    val logo: String? = null,

    @SerializedName("banner")
    val banner: String? = null,

    @SerializedName("alamat_usaha")
    val alamatUsaha: String,

    @SerializedName("no_telepon")
    val noTelepon: String,

    @SerializedName("status")
    val status: String
)

data class ShopResponse(
    @SerializedName("message")
    val message: String? = null,

    @SerializedName("shop")
    val shop: ShopDto? = null
)

data class ShopUpdateRequest(
    @SerializedName("nama_toko")
    val namaToko: String,

    @SerializedName("deskripsi")
    val deskripsi: String?,

    @SerializedName("alamat_usaha")
    val alamatUsaha: String,

    @SerializedName("no_telepon")
    val noTelepon: String
)
