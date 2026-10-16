
package com.example.tanimart.data.order

class OrderRepository(
    private val orderApi: OrderApiService
) {

    suspend fun getOrders(): Result<OrderListResponse> {
        return try {
            val response = orderApi.getOrders()

            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(
                    Exception("Respons daftar pesanan kosong.")
                )
            } else {
                Result.failure(
                    Exception(
                        "Gagal mengambil pesanan: HTTP ${response.code()}"
                    )
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getOrderDetail(
        orderId: Int
    ): Result<OrderDetailResponse> {
        return try {
            val response = orderApi.getOrderDetail(orderId)

            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(
                    Exception("Respons detail pesanan kosong.")
                )
            } else {
                Result.failure(
                    Exception(
                        "Gagal mengambil detail pesanan: HTTP ${response.code()}"
                    )
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
