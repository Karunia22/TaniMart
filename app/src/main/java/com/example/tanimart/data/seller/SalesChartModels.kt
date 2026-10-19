package com.example.tanimart.data.seller

import com.google.gson.annotations.SerializedName

data class SalesChartDaily(
    @SerializedName("labels")
    val labels: List<String> = emptyList(),
    @SerializedName("revenues")
    val revenues: List<Double> = emptyList()
)

data class SalesChartMonthly(
    @SerializedName("labels")
    val labels: List<String> = emptyList(),
    @SerializedName("revenues")
    val revenues: List<Double> = emptyList()
)

data class SalesChartYearly(
    @SerializedName("labels")
    val labels: List<String> = emptyList(),
    @SerializedName("revenues")
    val revenues: List<Double> = emptyList()
)

data class SalesChartData(
    @SerializedName("daily")
    val daily: SalesChartDaily = SalesChartDaily(),
    @SerializedName("monthly")
    val monthly: SalesChartMonthly = SalesChartMonthly(),
    @SerializedName("yearly")
    val yearly: SalesChartYearly = SalesChartYearly()
)

data class SalesChartResponse(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("chart")
    val chart: SalesChartData? = null
)
