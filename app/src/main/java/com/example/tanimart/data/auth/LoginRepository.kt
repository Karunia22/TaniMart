package com.example.tanimart.data.auth

class LoginRepository(
    private val api: LoginApi
) {

    suspend fun login(
        email: String,
        password: String
    ): LoginResponse {
        return api.login(
            LoginRequest(
                email = email,
                password = password
            )
        )
    }
}