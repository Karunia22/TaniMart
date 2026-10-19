package com.example.tanimart.ui.seller.application.data

enum class SellerApplicationStatus {
    PENDING,
    APPROVED,
    REJECTED,
    UNKNOWN;

    companion object {
        fun from(raw: String?): SellerApplicationStatus {
            return when (raw?.lowercase()) {
                "pending" -> PENDING
                "approved" -> APPROVED
                "rejected" -> REJECTED
                else -> UNKNOWN
            }
        }
    }
}

data class SellerApplicationUiModel(
    val id: Int,
    val namaToko: String,
    val deskripsiToko: String?,
    val alamatUsaha: String,
    val noTelepon: String,
    val status: SellerApplicationStatus,
    val catatanAdmin: String?,
    val createdAt: String?
)

data class SellerRegisterUiState(
    val namaToko: String = "",
    val deskripsiToko: String = "",
    val alamatUsaha: String = "",
    val noTelepon: String = "",

    val existingApplication: SellerApplicationUiModel? = null,
    val showForm: Boolean = false,

    val isLoading: Boolean = false,
    val isSubmitting: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)
