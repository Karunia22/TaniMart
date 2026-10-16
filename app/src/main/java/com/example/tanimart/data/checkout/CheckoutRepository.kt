
package com.example.tanimart.data.checkout

class CheckoutRepository(
    private val api: CheckoutApi
) {
    suspend fun checkout(
        request: CheckoutRequest
    ): CheckoutResponse {
        return api.checkout(request)
    }

    suspend fun buyNow(
        request: BuyNowRequest
    ): CheckoutResponse {
        return api.buyNow(request)
    }
}
