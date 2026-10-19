package com.example.tanimart.ui.seller.application.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanimart.data.network.RetrofitClient
import com.example.tanimart.data.seller.SellerApplicationDto
import com.example.tanimart.data.seller.SellerApplicationRepository
import com.example.tanimart.data.seller.SellerApplicationRequest
import com.example.tanimart.ui.seller.application.data.SellerApplicationStatus
import com.example.tanimart.ui.seller.application.data.SellerApplicationUiModel
import com.example.tanimart.ui.seller.application.data.SellerRegisterUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SellerRegisterViewModel(
    private val repository: SellerApplicationRepository =
        SellerApplicationRepository(RetrofitClient.sellerApplicationApi)
) : ViewModel() {

    private val _uiState = MutableStateFlow(SellerRegisterUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadApplication()
    }

    fun onNamaTokoChange(value: String) {
        _uiState.update { it.copy(namaToko = value, errorMessage = null) }
    }

    fun onDeskripsiTokoChange(value: String) {
        _uiState.update { it.copy(deskripsiToko = value, errorMessage = null) }
    }

    fun onAlamatUsahaChange(value: String) {
        _uiState.update { it.copy(alamatUsaha = value, errorMessage = null) }
    }

    fun onNoTeleponChange(value: String) {
        _uiState.update { it.copy(noTelepon = value, errorMessage = null) }
    }

    /**
     * Menampilkan form kembali agar pengguna dapat mengajukan ulang
     * (dipakai ketika pengajuan sebelumnya ditolak).
     */
    fun startNewApplication() {
        _uiState.update {
            it.copy(
                existingApplication = null,
                isSubmitting = false,
                errorMessage = null
            )
        }
    }

    fun loadApplication() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            repository.mine()
                .onSuccess { applications ->
                    _uiState.update {
                        it.copy(
                            existingApplication = applications
                                .firstOrNull()
                                ?.toUiModel(),
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
                                ?: "Gagal memuat data pengajuan."
                        )
                    }
                }
        }
    }

    fun submit() {
        val state = _uiState.value

        val validationError = validate(state)
        if (validationError != null) {
            _uiState.update { it.copy(errorMessage = validationError) }
            return
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(isSubmitting = true, errorMessage = null)
            }

            repository.submit(
                SellerApplicationRequest(
                    namaToko = state.namaToko.trim(),
                    deskripsiToko = state.deskripsiToko.trim()
                        .ifBlank { null },
                    alamatUsaha = state.alamatUsaha.trim(),
                    noTelepon = state.noTelepon.trim()
                )
            )
                .onSuccess { application ->
                    _uiState.update {
                        it.copy(
                            existingApplication = application.toUiModel(),
                            isSubmitting = false,
                            errorMessage = null
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isSubmitting = false,
                            errorMessage = error.message
                                ?: "Gagal mengirim pengajuan."
                        )
                    }
                }
        }
    }

    private fun validate(state: SellerRegisterUiState): String? {
        return when {
            state.namaToko.isBlank() ->
                "Nama toko wajib diisi."

            state.alamatUsaha.isBlank() ->
                "Alamat usaha wajib diisi."

            state.noTelepon.isBlank() ->
                "Nomor telepon wajib diisi."

            else -> null
        }
    }
}

private fun SellerApplicationDto.toUiModel(): SellerApplicationUiModel {
    return SellerApplicationUiModel(
        id = id,
        namaToko = namaToko,
        deskripsiToko = deskripsiToko,
        alamatUsaha = alamatUsaha,
        noTelepon = noTelepon,
        status = SellerApplicationStatus.from(statusPengajuan),
        catatanAdmin = catatanAdmin,
        createdAt = createdAt
    )
}
