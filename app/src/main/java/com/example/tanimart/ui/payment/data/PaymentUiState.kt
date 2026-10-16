
package com.example.tanimart.ui.payment.data

import com.example.tanimart.data.payment.PaymentResponse
import com.example.tanimart.data.payment.PaymentSuccessResponse


data class PaymentUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val paymentResponse: PaymentResponse? = null,
    val successResponse: PaymentSuccessResponse? = null,
    val orderId: Int? = null
)

