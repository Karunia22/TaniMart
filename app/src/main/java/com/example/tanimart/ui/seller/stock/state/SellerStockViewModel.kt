package com.example.tanimart.ui.seller.stock.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanimart.data.network.RetrofitClient
import com.example.tanimart.data.product.ProductRepository
import com.example.tanimart.ui.seller.stock.data.StockUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SellerStockViewModel(
    private val repository: ProductRepository =
        ProductRepository(RetrofitClient.productApi)
) : ViewModel() {

    private val _uiState = MutableStateFlow(StockUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                val products = repository.getMyProducts()
                _uiState.update {
                    it.copy(products = products, isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message
                            ?: "Gagal memuat data stok."
                    )
                }
            }
        }
    }

    fun updateStock(productId: Int, newStock: Int) {
        if (newStock < 0) return
        if (_uiState.value.updatingProductId == productId) return

        viewModelScope.launch {
            _uiState.update {
                it.copy(updatingProductId = productId, errorMessage = null)
            }

            try {
                repository.updateProduct(
                    productId = productId,
                    fields = mapOf("stok" to newStock.toString())
                )

                _uiState.update { state ->
                    state.copy(
                        products = state.products.map { product ->
                            if (product.id == productId) {
                                product.copy(stock = newStock)
                            } else {
                                product
                            }
                        },
                        updatingProductId = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        updatingProductId = null,
                        errorMessage = e.message
                            ?: "Gagal memperbarui stok."
                    )
                }
            }
        }
    }
}
