
package com.example.tanimart.data.payment

import com.google.gson.annotations.SerializedName

data class PaymentResponse(
    val message: String,
    val simulation: Boolean,
    val payment: PaymentDto,
    val instructions: PaymentInstructions,
    @SerializedName("expires_at")
    val expiresAt: String
)

data class PaymentDto(
    val id: Int,
    val metode: String,
    val bank: String?,
    val jumlah: String,
    val status: String,
    val referensi: String
)

data class PaymentInstructions(
    val bank: String?,
    @SerializedName("nomor_rekening")
    val nomorRekening: String?,
    @SerializedName("nama_penerima")
    val namaPenerima: String?,
    @SerializedName("qr_code")
    val qrCode: String?,
    val petunjuk: String
)
