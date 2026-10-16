package com.example.tanimart.data.product

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val imageUrl: String? = null,
    val images: List<String> = emptyList(),
    val description: String? = null,
    val stock: Int = 0,
    val weight: Int = 0,
    val sellerName: String? = null,
    val status: String = "aktif"
)