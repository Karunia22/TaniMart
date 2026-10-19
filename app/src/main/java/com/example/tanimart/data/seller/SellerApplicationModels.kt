package com.example.tanimart.data.seller

import com.google.gson.annotations.SerializedName

data class SellerApplicationRequest(
    @SerializedName("nama_toko")
    val namaToko: String,

    @SerializedName("deskripsi_toko")
    val deskripsiToko: String?,

    @SerializedName("alamat_usaha")
    val alamatUsaha: String,

    @SerializedName("no_telepon")
    val noTelepon: String
)

data class SellerApplicationDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("nama_toko")
    val namaToko: String,

    @SerializedName("deskripsi_toko")
    val deskripsiToko: String? = null,

    @SerializedName("alamat_usaha")
    val alamatUsaha: String,

    @SerializedName("no_telepon")
    val noTelepon: String,

    @SerializedName("status_pengajuan")
    val statusPengajuan: String,

    @SerializedName("catatan_admin")
    val catatanAdmin: String? = null,

    @SerializedName("created_at")
    val createdAt: String? = null
)

data class SellerApplicationListResponse(
    @SerializedName("data")
    val data: List<SellerApplicationDto> = emptyList()
)

data class SellerApplicationResponse(
    @SerializedName("message")
    val message: String? = null,

    @SerializedName("data")
    val data: SellerApplicationDto? = null
)
