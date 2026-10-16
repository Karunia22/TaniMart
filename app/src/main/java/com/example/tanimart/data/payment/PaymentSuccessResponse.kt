
package com.example.tanimart.data.payment

import com.google.gson.annotations.SerializedName

data class PaymentSuccessResponse(
    val message: String,
    val simulation: Boolean,
    val payment: PaymentSuccessDto,
    val order: PaymentSuccessOrderDto
)

data class PaymentSuccessDto(
    val id: Int,
    val status: String
)

data class PaymentSuccessOrderDto(
    val id: Int,
    val status: String
)