package com.example.tanimart.data.cart

import com.example.tanimart.ui.cart.data.CartItem

class CartRepository(
    private val cartApi: CartApi
) {
    suspend fun getCart(): List<CartItem> {
        val response = cartApi.getCart()

        return response.cart?.items?.map { dto ->
            dto.toCartItem()
        } ?: emptyList()
    }

    suspend fun addItem(
        productId: Int,
        quantity: Int
    ): List<CartItem> {
        cartApi.addItem(
            AddCartItemRequest(
                productId = productId,
                jumlah = quantity
            )
        )

        return getCart()
    }

    suspend fun updateQuantity(
        cartItemId: Int,
        quantity: Int
    ): List<CartItem> {
        cartApi.updateItem(
            cartItemId = cartItemId,
            request = UpdateCartItemRequest(
                jumlah = quantity
            )
        )

        return getCart()
    }

    suspend fun deleteItem(
        cartItemId: Int
    ): List<CartItem> {
        cartApi.deleteItem(cartItemId)
        return getCart()
    }

    suspend fun clearCart(): List<CartItem> {
        cartApi.clearCart()
        return getCart()
    }
}