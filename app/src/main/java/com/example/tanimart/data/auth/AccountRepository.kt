
package com.example.tanimart.data.auth

class AccountRepository(
    private val accountApi: AccountApi
) {
    suspend fun getAccount(): Result<AccountResponse> {
        return runCatching {
            val response = accountApi.getAccount()

            if (response.isSuccessful) {
                response.body()
                    ?: throw Exception("Respons akun kosong.")
            } else {
                throw Exception(
                    "Gagal mengambil akun (${response.code()})."
                )
            }
        }
    }

    suspend fun updateName(name: String): Result<AccountMessageResponse> {
        return runCatching {
            val response = accountApi.updateName(
                UpdateNameRequest(name = name)
            )

            if (response.isSuccessful) {
                response.body()
                    ?: throw Exception("Respons server kosong.")
            } else {
                throw Exception(
                    "Gagal memperbarui nama (${response.code()})."
                )
            }
        }
    }

    suspend fun updatePassword(
        currentPassword: String,
        newPassword: String,
        passwordConfirmation: String
    ): Result<AccountMessageResponse> {
        return runCatching {
            val response = accountApi.updatePassword(
                UpdatePasswordRequest(
                    current_password = currentPassword,
                    password = newPassword,
                    password_confirmation = passwordConfirmation
                )
            )

            if (response.isSuccessful) {
                response.body()
                    ?: throw Exception("Respons server kosong.")
            } else {
                throw Exception(
                    "Gagal mengubah kata sandi (${response.code()})."
                )
            }
        }
    }
}
