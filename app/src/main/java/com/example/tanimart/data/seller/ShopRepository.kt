package com.example.tanimart.data.seller

import com.example.tanimart.data.network.apiErrorMessage

class ShopRepository(
    private val api: ShopApi
) {

    suspend fun getShop(): Result<ShopDto> {
        return try {
            val response = api.getShop()
            val shop = response.body()?.shop

            if (response.isSuccessful && shop != null) {
                Result.success(shop)
            } else {
                Result.failure(
                    Exception(
                        apiErrorMessage(
                            response,
                            fallback = "Gagal memuat data toko."
                        )
                    )
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateShop(
        request: ShopUpdateRequest
    ): Result<ShopDto> {
        return try {
            val response = api.updateShop(request)
            val shop = response.body()?.shop

            if (response.isSuccessful && shop != null) {
                Result.success(shop)
            } else {
                Result.failure(
                    Exception(
                        apiErrorMessage(
                            response,
                            fallback = "Gagal memperbarui data toko."
                        )
                    )
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
