
package com.example.tanimart.data.checkout

import retrofit2.http.Body
import retrofit2.http.POST

interface CheckoutApi {

    @POST("orders/checkout")
    suspend fun checkout(
        @Body request: CheckoutRequest
    ): CheckoutResponse

    @POST("orders/buy-now")
    suspend fun buyNow(
        @Body request: BuyNowRequest
    ): CheckoutResponse
}
