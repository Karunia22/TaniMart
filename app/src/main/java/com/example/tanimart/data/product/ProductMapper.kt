package com.example.tanimart.data.product

import com.example.tanimart.data.network.ApiConfig

fun ProductResponse.toProduct(): Product {
    val imageUrls = images
        .sortedByDescending { it.isPrimary }
        .map { image -> ApiConfig.toAbsoluteImageUrl(image.url) }

    return Product(
        id = id,
        name = name,
        price = price.toDoubleOrNull() ?: 0.0,
        imageUrl = imageUrls.firstOrNull(),
        images = imageUrls,
        description = description,
        stock = stock,
        weight = weight,
        sellerName = seller?.name,
        status = status
    )
}
