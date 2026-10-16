package com.example.tanimart.ui.checkout.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanimart.data.checkout.BuyNowRequest
import com.example.tanimart.data.checkout.CheckoutRepository
import com.example.tanimart.data.checkout.CheckoutRequest
import com.example.tanimart.ui.checkout.data.CheckoutUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class CheckoutViewModel(
    private val repository: CheckoutRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CheckoutUiState())
    val uiState = _uiState.asStateFlow()

    fun checkout(
        addressId: Int,
        cartItemIds: List<Int>
    ) {
        if (_uiState.value.isLoading) return

        if (cartItemIds.isEmpty()) {
            _uiState.update {
                it.copy(errorMessage = "Pilih minimal satu produk.")
            }
            return
        }

        _uiState.update {
            it.copy(
                isLoading = true,
                errorMessage = null,
                checkoutResponse = null
            )
        }

        viewModelScope.launch {
            try {
                val response = repository.checkout(
                    CheckoutRequest(
                        addressId = addressId,
                        cartItemIds = cartItemIds
                    )
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        checkoutResponse = response
                    )
                }
            } catch (e: HttpException) {
                val message = when (e.code()) {
                    401 -> "Sesi login berakhir. Silakan login kembali."
                    422 -> "Data checkout tidak valid. Periksa alamat dan produk."
                    else -> "Checkout gagal. Kode HTTP: ${e.code()}."
                }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = message
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
    fun buyNow(
        productId: Int,
        quantity: Int,
        addressId: Int
    ) {
        if (_uiState.value.isLoading) return

        _uiState.update {
            it.copy(
                isLoading = true,
                errorMessage = null,
                checkoutResponse = null
            )
        }

        viewModelScope.launch {
            try {
                val response = repository.buyNow(
                    BuyNowRequest(
                        productId = productId,
                        quantity = quantity,
                        addressId = addressId
                    )
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        checkoutResponse = response
                    )
                }
            } catch (e: HttpException) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = when (e.code()) {
                            401 -> "Silakan login kembali."
                            422 -> "Produk, stok, atau alamat tidak valid."
                            else -> "Checkout gagal. HTTP ${e.code()}."
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

    fun clearResult() {
        _uiState.update {
            it.copy(
                errorMessage = null,
                checkoutResponse = null
            )
        }
    }
}
