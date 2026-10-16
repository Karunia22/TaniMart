package com.example.tanimart.data.auth

import com.example.tanimart.data.address.MessageResponse
import retrofit2.http.POST

interface LogoutApi {

    @POST("logout")
    suspend fun logout(): MessageResponse
}