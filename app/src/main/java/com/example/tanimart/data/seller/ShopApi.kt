package com.example.tanimart.data.seller

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface ShopApi {

    @GET("seller/shop")
    suspend fun getShop(): Response<ShopResponse>

    @PATCH("seller/shop")
    suspend fun updateShop(
        @Body request: ShopUpdateRequest
    ): Response<ShopResponse>
}
