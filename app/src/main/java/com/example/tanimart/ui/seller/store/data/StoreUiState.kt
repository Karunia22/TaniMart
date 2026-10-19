package com.example.tanimart.ui.seller.store.data

import com.example.tanimart.data.seller.ShopDto

data class StoreUiState(
    val shop: ShopDto? = null,

    // Field form pengaturan.
    val namaToko: String = "",
    val deskripsi: String = "",
    val alamatUsaha: String = "",
    val noTelepon: String = "",

    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)
