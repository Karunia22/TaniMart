package com.example.tanimart.data.order

data class UpdateOrderStatusRequest(
    val status: String,
)

data class UpdateOrderStatusResponse(
    val message: String,
    val order: SellerOrderDto? = null,
)