
package com.example.tanimart.data.order

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface OrderApiService {

    @GET("orders")
    suspend fun getOrders(): Response<OrderListResponse>

    @GET("orders/{order}")
    suspend fun getOrderDetail(
        @Path("order") orderId: Int
    ): Response<OrderDetailResponse>

    @GET("seller/orders")
    suspend fun getSellerOrders(): Response<SellerOrderListResponse>

    @PATCH("seller/orders/{order}/status")
    suspend fun updateSellerOrderStatus(
        @Path("order") orderId: Int,
        @Body request: UpdateOrderStatusRequest,
    ): Response<UpdateOrderStatusResponse>
}
