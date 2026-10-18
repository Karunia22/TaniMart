package com.example.tanimart.ui.seller.orders.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanimart.data.order.OrderRepository
import com.example.tanimart.ui.seller.orders.data.SellerOrdersUiState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SellerOrdersViewModel(
    private val repository: OrderRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SellerOrdersUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadOrders()
    }

    fun selectStatus(status: String) {
        _uiState.update { it.copy(selectedStatus = status) }
    }

    fun loadOrders() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, error = null)
            }

            try {
                repository.getSellerOrders()
                    .onSuccess { response ->
                        _uiState.update {
                            it.copy(
                                orders = response.orders,
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
            } catch (e: CancellationException) {
                throw e
            }
        }
    }

    fun markOrderAsShipped(orderId: Int) {
        if (orderId in _uiState.value.updatingOrderIds) return

        val order = _uiState.value.orders
            .firstOrNull { it.id == orderId }
            ?: return

        if (!order.status.equals("diproses", ignoreCase = true)) {
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
