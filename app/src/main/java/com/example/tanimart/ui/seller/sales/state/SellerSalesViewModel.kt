package com.example.tanimart.ui.seller.sales.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanimart.data.network.RetrofitClient
import com.example.tanimart.data.seller.SalesRepository
import com.example.tanimart.ui.seller.sales.data.SalesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SellerSalesViewModel(
    private val repository: SalesRepository =
        SalesRepository(RetrofitClient.salesApi)
) : ViewModel() {

    private val _uiState = MutableStateFlow(SalesUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadAll()
    }

    fun loadAll() {
        loadSummary()
        loadChart(uiState.value.selectedPeriod)
    }

    fun selectPeriod(period: String) {
        _uiState.update { it.copy(selectedPeriod = period) }
        loadChart(period)
    }

    fun loadSummary() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            repository.getSummary()
                .onSuccess { summary ->
                    _uiState.update {
                        it.copy(
                            summary = summary,
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
                                ?: "Gagal memuat ringkasan penjualan."
                        )
                    }
                }
        }
    }

    fun loadChart(period: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingChart = true, errorMessage = null) }

            repository.getChart(period)
                .onSuccess { chart ->
                    _uiState.update {
                        it.copy(
                            chart = chart,
                            isLoadingChart = false,
                            errorMessage = null
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoadingChart = false,
                            errorMessage = error.message
                                ?: "Gagal memuat grafik penjualan."
                        )
                    }
                }
        }
    }
}
