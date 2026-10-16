package com.example.tanimart.data.auth

class RegisterRepository(
    private val api: RegisterApi
) {
    suspend fun register(
        request: RegisterRequest
    ): RegisterResponse {
        return api.register(request)
    }
}