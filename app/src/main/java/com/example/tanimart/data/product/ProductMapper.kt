package com.example.tanimart.data.product

private const val IMAGE_BASE_URL =
    "http://192.168.1.91:8000"

fun ProductResponse.toProduct(): Product {
    val imageUrls = images
        .sortedByDescending { it.isPrimary }
        .map { image ->
            when {
                image.url.startsWith("http://localhost:8000") ->
                    image.url.replace(
                        "http://localhost:8000",
                        IMAGE_BASE_URL
                    )

                image.url.startsWith("http://192.168.1.91:8000") ->
                    image.url.replace(
                        "http://192.168.1.91:8000",
                        IMAGE_BASE_URL
                    )

                image.url.startsWith("/storage/") ->
                    "$IMAGE_BASE_URL${image.url}"

                else -> image.url
            }
        }

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
