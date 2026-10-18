
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

    suspend fun getSellerOrders(): Result<SellerOrderListResponse> {
        return try {
            val response = orderApi.getSellerOrders()

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(
                        Exception("Respons pesanan seller kosong.")
                    )
                }
            } else {
                Result.failure(
                    Exception(
                        "Gagal mengambil pesanan seller: HTTP ${response.code()}"
                    )
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateSellerOrderStatus(
        orderId: Int,
        status: String,
    ): Result<UpdateOrderStatusResponse> = runCatching {
        val response = orderApi.updateSellerOrderStatus(
            orderId = orderId,
            request = UpdateOrderStatusRequest(status = status),
        )

        if (response.isSuccessful) {
            response.body()
                ?: error("Respons server kosong.")
        } else {
            error("Gagal mengubah status pesanan: HTTP ${response.code()}")
        }
    }
}
