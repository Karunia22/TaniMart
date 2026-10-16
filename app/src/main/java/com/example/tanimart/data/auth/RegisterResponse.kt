package com.example.tanimart.data.auth

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("token")
    val token: String?,

    @SerializedName("user")
    val user: RegisterUserResponse?
)

data class RegisterUserResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("role")
    val role: String,

    @SerializedName("status")
    val status: String
)