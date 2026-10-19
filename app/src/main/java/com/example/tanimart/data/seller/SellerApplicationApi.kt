package com.example.tanimart.data.seller

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SellerApplicationApi {

    @POST("seller-applications")
    suspend fun submit(
        @Body request: SellerApplicationRequest
    ): Response<SellerApplicationResponse>

    @GET("seller-applications/mine")
    suspend fun mine(): Response<SellerApplicationListResponse>
}
