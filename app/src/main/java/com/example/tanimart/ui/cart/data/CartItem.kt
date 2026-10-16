package com.example.tanimart.ui.cart.data

import com.example.tanimart.data.product.Product

data class CartItem(
    val product: Product,
    val quantity: Int = 1,
    val selected: Boolean = true,
    val id: Int? = null
)