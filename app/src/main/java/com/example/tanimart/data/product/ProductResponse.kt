package com.example.tanimart.data.product

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("seller_id")
    val sellerId: Int,

    @SerializedName("nama_produk")
    val name: String,

    @SerializedName("deskripsi")
    val description: String?,

    @SerializedName("harga")
    val price: String,

    @SerializedName("stok")
    val stock: Int,

    @SerializedName("berat")
    val weight: Int,

    @SerializedName("status")
    val status: String,

    @SerializedName("seller")
    val seller: SellerResponse?,

    @SerializedName("images")
    val images: List<ProductImageResponse> = emptyList()
)

data class SellerResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String
)

data class ProductImageResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("product_id")
    val productId: Int,

    @SerializedName("image")
    val image: String,

    @SerializedName("is_primary")
    val isPrimary: Boolean,

    @SerializedName("url")
    val url: String
)