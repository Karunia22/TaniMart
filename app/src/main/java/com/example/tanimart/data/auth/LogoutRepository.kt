package com.example.tanimart.data.auth

import com.example.tanimart.data.address.MessageResponse

class LogoutRepository(
    private val api: LogoutApi
) {

    suspend fun logout(): MessageResponse {
        return api.logout()
    }
}