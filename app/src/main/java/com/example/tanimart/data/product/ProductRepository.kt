package com.example.tanimart.data.product

import okhttp3.MultipartBody
import okhttp3.RequestBody

class ProductRepository(
    private val api: ProductApi
) {
    suspend fun getProducts(): List<Product> {
        return api.getProducts()
            .products
            .map { it.toProduct() }
    }

    suspend fun getProduct(productId: Int): Product {
        return api.getProduct(productId)
            .product
            .toProduct()
    }

    suspend fun getMyProducts(): List<Product> {
        return api.getMyProducts()
            .products
            .map { it.toProduct() }
    }

    suspend fun createProduct(
        name: RequestBody,
        description: RequestBody,
        price: RequestBody,
        stock: RequestBody,
        weight: RequestBody,
        status: RequestBody,
        images: List<MultipartBody.Part>
    ): CreateProductResponse {
        return api.createProduct(
            name = name,
            description = description,
            price = price,
            stock = stock,
            weight = weight,
            status = status,
            images = images
        )
    }

    suspend fun updateProduct(
        productId: Int,
        fields: Map<String, String>
    ): CreateProductResponse {
        return api.updateProduct(
            productId = productId,
            fields = fields
        )
    }
}