package com.example.tanimart.data.seller

import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Response

class SellerApplicationRepository(
    private val api: SellerApplicationApi
) {

    private val gson = Gson()

    suspend fun submit(
        request: SellerApplicationRequest
    ): Result<SellerApplicationDto> {
        return try {
            val response = api.submit(request)
            val data = response.body()?.data

            if (response.isSuccessful && data != null) {
                Result.success(data)
            } else {
                Result.failure(
                    Exception(
                        extractMessage(
                            response,
                            fallback = "Gagal mengirim pengajuan penjual."
                        )
                    )
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun mine(): Result<List<SellerApplicationDto>> {
        return try {
            val response = api.mine()

            if (response.isSuccessful) {
                Result.success(response.body()?.data ?: emptyList())
            } else {
                Result.failure(
                    Exception(
                        extractMessage(
                            response,
                            fallback = "Gagal memuat pengajuan penjual."
                        )
                    )
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Mengambil pesan `message` dari body error backend (jika ada).
     */
    private fun extractMessage(
        response: Response<*>,
        fallback: String
    ): String {
        val raw = try {
            response.errorBody()?.string()
        } catch (e: Exception) {
            null
        }

        if (raw.isNullOrBlank()) {
            return "$fallback (HTTP ${response.code()})"
        }

        return try {
            val json = gson.fromJson(raw, JsonObject::class.java)
            val message = json?.get("message")?.asString

            if (!message.isNullOrBlank()) {
                message
            } else {
                "$fallback (HTTP ${response.code()})"
            }
        } catch (e: Exception) {
            "$fallback (HTTP ${response.code()})"
        }
    }
}
