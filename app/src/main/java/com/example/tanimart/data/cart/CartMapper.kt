package com.example.tanimart.data.cart

import com.example.tanimart.data.network.ApiConfig
import com.example.tanimart.data.product.Product
import com.example.tanimart.ui.cart.data.CartItem

fun CartItemDto.toCartItem(
    selected: Boolean = true
): CartItem {
    val primaryImage = product.images.firstOrNull {
        it.isPrimary == 1
    } ?: product.images.firstOrNull()

    return CartItem(
        id = id,
        quantity = jumlah,
        selected = selected,
        product = Product(
            id = product.id,
            name = product.namaProduk,
            price = product.harga.toDoubleOrNull() ?: 0.0,
            imageUrl = primaryImage?.url?.let { ApiConfig.toAbsoluteImageUrl(it) },
            images = product.images.map { ApiConfig.toAbsoluteImageUrl(it.url) },
            description = product.deskripsi,
            stock = product.stok,
            weight = product.berat,
            sellerName = product.seller?.name
        )
    )
}