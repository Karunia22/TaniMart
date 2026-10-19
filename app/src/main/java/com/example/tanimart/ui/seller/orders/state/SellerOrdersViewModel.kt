package com.example.tanimart.ui.seller.orders.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanimart.data.network.RetrofitClient
import com.example.tanimart.data.order.OrderRepository
import com.example.tanimart.data.order.toUiModel
import com.example.tanimart.ui.order.data.OrderFilter
import com.example.tanimart.ui.order.data.OrderStatus
import com.example.tanimart.ui.seller.orders.data.SellerOrdersUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SellerOrdersViewModel(
    private val repository: OrderRepository = RetrofitClient.orderRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SellerOrdersUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadOrders()
    }

    fun selectFilter(filter: OrderFilter) {
        _uiState.update { it.copy(selectedFilter = filter) }
    }

    fun loadOrders() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, error = null)
            }

            repository.getSellerOrders()
                .onSuccess { response ->
                    _uiState.update {
                        it.copy(
                            orders = response.orders.map { order ->
                                order.toUiModel()
                            },
                            isLoading = false,
                            error = null,
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message
                                ?: "Gagal memuat pesanan.",
                        )
                    }
                }
        }
    }

    fun markOrderAsShipped(orderId: Int) {
        if (orderId in _uiState.value.updatingOrderIds) return

        val order = _uiState.value.orders
            .firstOrNull { it.id == orderId }
            ?: return

        if (order.status != OrderStatus.DIPROSES) {
            return
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    updatingOrderIds = it.updatingOrderIds + orderId,
                    error = null,
                )
            }

            try {
                repository.updateSellerOrderStatus(
                    orderId = orderId,
                    status = "dikirim",
                ).onSuccess {
                    loadOrders()
                }.onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            error = exception.message
                                ?: "Gagal mengubah status pesanan.",
                        )
                    }
                }
            } finally {
                _uiState.update {
                    it.copy(
                        updatingOrderIds = it.updatingOrderIds - orderId,
                    )
                }
            }
        }
    }
}
