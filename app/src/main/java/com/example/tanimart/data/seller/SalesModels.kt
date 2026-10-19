package com.example.tanimart.data.seller

import com.google.gson.annotations.SerializedName

data class SalesSummaryDto(
    @SerializedName("total_pendapatan")
    val totalPendapatan: Double = 0.0,

    @SerializedName("total_pesanan")
    val totalPesanan: Int = 0,

    @SerializedName("total_produk_terjual")
    val totalProdukTerjual: Int = 0,

    @SerializedName("pesanan_per_status")
    val pesananPerStatus: Map<String, Int> = emptyMap()
)

data class SalesSummaryResponse(
    @SerializedName("message")
    val message: String? = null,

    @SerializedName("summary")
    val summary: SalesSummaryDto? = null
)
