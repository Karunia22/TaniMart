
package com.example.tanimart.data.payment

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface PaymentApi {

    @POST("orders/{orderId}/payment")
    suspend fun createPayment(
        @Path("orderId") orderId: Int,
        @Body request: PaymentRequest
    ): PaymentResponse


    @POST("orders/{orderId}/payment/simulate-success")
    suspend fun simulateSuccess(
        @Path("orderId") orderId: Int
    ): PaymentSuccessResponse

}
