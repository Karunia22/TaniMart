package com.example.tanimart.data.seller

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Streaming

interface SalesApi {

    @GET("seller/sales/summary")
    suspend fun getSummary(): Response<SalesSummaryResponse>

    @GET("seller/sales/chart")
    suspend fun getChart(
        @Query("period") period: String? = null
    ): Response<SalesChartResponse>

    @Streaming
    @GET("seller/sales/report")
    suspend fun downloadReport(
        @Query("period") period: String? = null,
        @Query("month") month: String? = null,
        @Query("year") year: Int? = null
    ): Response<ResponseBody>
}
