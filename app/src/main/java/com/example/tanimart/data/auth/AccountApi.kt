
package com.example.tanimart.data.auth

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface AccountApi {

    @GET("account")
    suspend fun getAccount(): Response<AccountResponse>

    @PATCH("account/name")
    suspend fun updateName(
        @Body request: UpdateNameRequest
    ): Response<AccountMessageResponse>

    @PATCH("account/password")
    suspend fun updatePassword(
        @Body request: UpdatePasswordRequest
    ): Response<AccountMessageResponse>
}
