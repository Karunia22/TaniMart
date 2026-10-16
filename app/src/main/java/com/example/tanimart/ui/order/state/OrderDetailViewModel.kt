
package com.example.tanimart.ui.order.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanimart.data.network.RetrofitClient
import com.example.tanimart.data.order.OrderDto
import com.example.tanimart.data.order.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class OrderDetailUiState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val order: OrderDto? = null
)

class OrderDetailViewModel(
    private val repository: OrderRepository =
        RetrofitClient.orderRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(OrderDetailUiState())
    val uiState = _uiState.asStateFlow()

    fun loadOrder(orderId: Int) {
        viewModelScope.launch {
            _uiState.value = OrderDetailUiState(isLoading = true)

            repository.getOrderDetail(orderId)
                .onSuccess { response ->
                    _uiState.value = OrderDetailUiState(
                        isLoading = false,
                        order = response.order
                    )
                }
                .onFailure { error ->
                    _uiState.value = OrderDetailUiState(
                        isLoading = false,
                        errorMessage = error.message
                            ?: "Gagal memuat detail pesanan."
                    )
                }
        }
    }
}