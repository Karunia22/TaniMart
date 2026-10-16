
package com.example.tanimart.ui.order.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanimart.data.network.RetrofitClient
import com.example.tanimart.data.order.OrderRepository
import com.example.tanimart.data.order.toUiModel
import com.example.tanimart.ui.order.data.OrderFilter
import com.example.tanimart.ui.order.data.OrderUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrderViewModel(
    private val repository: OrderRepository =
        RetrofitClient.orderRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(OrderUiState())
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    init {
        loadOrders()
    }

    fun selectFilter(filter: OrderFilter) {
        _uiState.update {
            it.copy(selectedFilter = filter)
        }
    }

    fun retry() {
        loadOrders()
    }

    fun refreshOrders() {
        loadOrders()
    }

    private fun loadOrders() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, errorMessage = null)
            }

            repository.getOrders()
                .onSuccess { response ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = null,
                            orders = response.orders.map { order ->
                                order.toUiModel()
                            }
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message
                                ?: "Gagal memuat pesanan."
                        )
                    }
                }
        }
    }
}