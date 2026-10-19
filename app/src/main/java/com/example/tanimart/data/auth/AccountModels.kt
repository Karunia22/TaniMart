
package com.example.tanimart.data.auth

data class AccountResponse(
    val user: AccountUser,
    val primary_address: PrimaryAddress?
)

data class AccountUser(
    val id: Int,
    val name: String,
    val email: String,
    val status: String,
    val role: String,
    val roles: List<String> = emptyList()
)

data class PrimaryAddress(
    val id: Int,
    val nama_penerima: String,
    val no_telepon: String,
    val alamat: String,
    val kota: String,
    val provinsi: String,
    val kode_pos: String,
    val is_primary: Boolean
)

data class UpdateNameRequest(
    val name: String
)

data class UpdatePasswordRequest(
    val current_password: String,
    val password: String,
    val password_confirmation: String
)

data class AccountMessageResponse(
    val message: String,
    val user: AccountUser? = null
)
