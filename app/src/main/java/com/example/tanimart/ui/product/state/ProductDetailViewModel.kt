package com.example.tanimart.ui.product.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanimart.data.network.RetrofitClient
import com.example.tanimart.data.product.Product
import com.example.tanimart.data.product.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ProductDetailUiState(
    val product: Product? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class ProductDetailViewModel : ViewModel() {

    private val repository = ProductRepository(
        RetrofitClient.productApi
    )

    private val _uiState =
        MutableStateFlow(ProductDetailUiState())

    val uiState = _uiState.asStateFlow()

    fun loadProduct(productId: Int) {
        viewModelScope.launch {
            _uiState.value = ProductDetailUiState(
                isLoading = true
            )

            try {
                val product = repository.getProduct(productId)

                _uiState.value = ProductDetailUiState(
                    product = product
                )
            } catch (e: Exception) {
                _uiState.value = ProductDetailUiState(
                    errorMessage = e.message
                        ?: "Gagal memuat detail produk."
                )
            }
        }
    }
}
