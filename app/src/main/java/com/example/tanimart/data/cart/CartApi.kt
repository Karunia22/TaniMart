package com.example.tanimart.data.cart

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path


interface CartApi {

    @GET("cart")
    suspend fun getCart(): CartResponse

    @POST("cart/items")
    suspend fun addItem(
        @Body request: AddCartItemRequest
    ): CartItemResponse

    @PATCH("cart/items/{cartItem}")
    suspend fun updateItem(
        @Path("cartItem") cartItemId: Int,
        @Body request: UpdateCartItemRequest
    ): CartItemResponse

    @DELETE("cart/items/{cartItem}")
    suspend fun deleteItem(
        @Path("cartItem") cartItemId: Int
    ): CartMessageResponse

    @DELETE("cart")
    suspend fun clearCart(): CartMessageResponse
}