package com.example.tanimart.data.auth

import retrofit2.http.GET

interface UserApi {

    @GET("user")
    suspend fun getUser(): UserResponse
}