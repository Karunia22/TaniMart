package com.example.tanimart.ui.payment.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanimart.data.payment.PaymentRepository
import com.example.tanimart.data.payment.PaymentRequest
import com.example.tanimart.ui.payment.data.PaymentUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class PaymentViewModel(
    private val repository: PaymentRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PaymentUiState())
    val uiState = _uiState.asStateFlow()


    fun createPayment(
        orderId: Int,
        method: String,
        bank: String? = null
    ) {
        if (_uiState.value.isLoading) return

        _uiState.update {
            it.copy(
                isLoading = true,
                errorMessage = null,
                paymentResponse = null,
                successResponse = null,
                orderId = orderId
            )
        }

        viewModelScope.launch {
            try {
                val response = repository.createPayment(
                    orderId = orderId,
                    request = PaymentRequest(
                        metode = method,
                        bank = bank
                    )
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        paymentResponse = response,
                        successResponse = null,
                        orderId = orderId
                    )
                }
            } catch (e: HttpException) {
                val message = when (e.code()) {
                    401 -> "Sesi login berakhir. Silakan login kembali."
                    403 -> "Kamu tidak memiliki akses ke pesanan ini."
                    422 -> "Pesanan tidak dapat dibayar atau batas pembayaran berakhir."
                    else -> "Gagal membuat pembayaran. HTTP ${e.code()}."
                }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        paymentResponse = null,
                        successResponse = null,
                        errorMessage = message
                    )
                }
            } catch (e: IOException) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        paymentResponse = null,
                        successResponse = null,
                        errorMessage = "Tidak dapat terhubung ke server."
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        paymentResponse = null,
                        successResponse = null,
                        errorMessage = e.message ?: "Terjadi kesalahan."
                    )
                }
            }
        }
    }

    fun simulateSuccess(orderId: Int) {
        if (_uiState.value.isLoading) return

        if (_uiState.value.paymentResponse == null) {
            _uiState.update {
                it.copy(errorMessage = "Buat instruksi pembayaran terlebih dahulu.")
            }
            return
        }

        _uiState.update {
            it.copy(
                isLoading = true,
                errorMessage = null,
                successResponse = null
            )
        }

        viewModelScope.launch {
            try {
                val response = repository.simulateSuccess(orderId)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        successResponse = response
                    )
                }
            } catch (e: HttpException) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = when (e.code()) {
                            401 -> "Silakan login kembali."
                            403 -> "Kamu tidak memiliki akses ke pesanan ini."
                            404 -> "Instruksi pembayaran belum dibuat."
                            422 -> "Pembayaran tidak dapat dikonfirmasi."
                            else -> "Simulasi gagal. HTTP ${e.code()}."
                        }
                    )
                }
            } catch (e: IOException) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Tidak dapat terhubung ke server."
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Terjadi kesalahan."
                    )
                }
            }
        }
    }

    fun clearError() {
        _uiState.update {
            it.copy(errorMessage = null)
        }
    }
}
