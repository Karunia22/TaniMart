package com.example.tanimart.data.seller

import com.example.tanimart.data.network.apiErrorMessage

class SalesRepository(
    private val api: SalesApi
) {

    suspend fun getSummary(): Result<SalesSummaryDto> {
        return try {
            val response = api.getSummary()
            val summary = response.body()?.summary

            if (response.isSuccessful && summary != null) {
                Result.success(summary)
            } else {
                Result.failure(
                    Exception(
                        apiErrorMessage(
                            response,
                            fallback = "Gagal memuat ringkasan penjualan."
                        )
                    )
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun getChart(period: String? = null): Result<SalesChartData> {
        return try {
            val response = api.getChart(period)
            val chart = response.body()?.chart
            if (response.isSuccessful && chart != null) {
                Result.success(chart)
            } else {
                Result.failure(
                    Exception(
                        apiErrorMessage(
                            response,
                            fallback = "Gagal memuat grafik penjualan."
                        )
                    )
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
