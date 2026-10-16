package com.example.tanimart.data.auth

import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApi {

    @POST("register")
    suspend fun register(
        @Body request: RegisterRequest
    ): RegisterResponse
}