
package com.example.tanimart.data.payment

import com.google.gson.annotations.SerializedName

data class PaymentRequest(
    val metode: String,
    val bank: String? = null
)
