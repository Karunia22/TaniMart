package com.example.tanimart.ui.seller.sales.data

import com.example.tanimart.data.seller.SalesChartData
import com.example.tanimart.data.seller.SalesSummaryDto

data class SalesUiState(
    val summary: SalesSummaryDto? = null,
    val chart: SalesChartData? = null,
    val selectedPeriod: String = "30d",
    val isLoading: Boolean = false,
    val isLoadingChart: Boolean = false,
    val errorMessage: String? = null
)
