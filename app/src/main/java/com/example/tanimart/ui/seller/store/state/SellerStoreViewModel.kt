package com.example.tanimart.ui.seller.store.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanimart.data.network.RetrofitClient
import com.example.tanimart.data.seller.ShopRepository
import com.example.tanimart.data.seller.ShopUpdateRequest
import com.example.tanimart.ui.seller.store.data.StoreUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SellerStoreViewModel(
    private val repository: ShopRepository =
        ShopRepository(RetrofitClient.shopApi)
) : ViewModel() {

    private val _uiState = MutableStateFlow(StoreUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadShop()
    }

    fun onNamaTokoChange(value: String) {
        _uiState.update { it.copy(namaToko = value, errorMessage = null) }
    }

    fun onDeskripsiChange(value: String) {
        _uiState.update { it.copy(deskripsi = value, errorMessage = null) }
    }

    fun onAlamatUsahaChange(value: String) {
        _uiState.update { it.copy(alamatUsaha = value, errorMessage = null) }
    }

    fun onNoTeleponChange(value: String) {
        _uiState.update { it.copy(noTelepon = value, errorMessage = null) }
    }

    fun loadShop() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            repository.getShop()
                .onSuccess { shop ->
                    _uiState.update {
                        it.copy(
                            shop = shop,
                            namaToko = shop.namaToko,
                            deskripsi = shop.deskripsi.orEmpty(),
                            alamatUsaha = shop.alamatUsaha,
                            noTelepon = shop.noTelepon,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message
                                ?: "Gagal memuat data toko."
                        )
                    }
                }
        }
    }

    fun save() {
        val state = _uiState.value

        if (state.namaToko.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Nama toko wajib diisi.") }
            return
        }

        if (state.alamatUsaha.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Alamat usaha wajib diisi.") }
            return
        }

        if (state.noTelepon.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Nomor telepon wajib diisi.") }
            return
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isSaving = true,
                    errorMessage = null,
                    successMessage = null
                )
            }

            repository.updateShop(
                ShopUpdateRequest(
                    namaToko = state.namaToko.trim(),
                    deskripsi = state.deskripsi.trim().ifBlank { null },
                    alamatUsaha = state.alamatUsaha.trim(),
                    noTelepon = state.noTelepon.trim()
                )
            )
                .onSuccess { shop ->
                    _uiState.update {
                        it.copy(
                            shop = shop,
                            isSaving = false,
                            successMessage = "Data toko berhasil disimpan.",
                            errorMessage = null
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isSaving = false,
                            errorMessage = error.message
                                ?: "Gagal menyimpan data toko."
                        )
                    }
                }
        }
    }
}
