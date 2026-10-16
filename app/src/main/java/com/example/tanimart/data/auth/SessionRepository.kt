package com.example.tanimart.data.auth

class SessionRepository(
    private val api: UserApi
) {

    suspend fun getUser(): UserResponse {
        return api.getUser()
    }
}