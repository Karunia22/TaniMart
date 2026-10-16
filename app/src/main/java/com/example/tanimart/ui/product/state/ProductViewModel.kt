package com.example.tanimart.ui.product.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanimart.data.network.RetrofitClient
import com.example.tanimart.data.product.ProductRepository
import com.example.tanimart.ui.product.data.ProductUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val repository =
        ProductRepository(RetrofitClient.productApi)

    private val _uiState =
        MutableStateFlow(ProductUiState())

    val uiState =
        _uiState.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val products = repository.getProducts()

                _uiState.value = ProductUiState(
                    products = products,
                    isLoading = false
                )

            } catch (e: Exception) {

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage =
                        e.message ?: "Gagal memuat produk"
                )
            }
        }
    }
}