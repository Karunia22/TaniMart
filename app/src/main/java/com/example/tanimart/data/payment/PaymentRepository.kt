
package com.example.tanimart.data.payment

class PaymentRepository(
    private val api: PaymentApi
) {
    suspend fun createPayment(
        orderId: Int,
        request: PaymentRequest
    ): PaymentResponse {
        return api.createPayment(orderId, request)
    }


    suspend fun simulateSuccess(
        orderId: Int
    ): PaymentSuccessResponse {
        return api.simulateSuccess(orderId)
    }

}
