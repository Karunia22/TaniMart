package com.example.tanimart.data.network

import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Response

/**
 * Mengambil pesan `message` dari body error backend (jika ada).
 * Berguna agar pesan validasi/kesalahan dari Laravel bisa ditampilkan ke pengguna.
 */
fun apiErrorMessage(response: Response<*>, fallback: String): String {
    val raw = try {
        response.errorBody()?.string()
    } catch (e: Exception) {
        null
    }

    if (raw.isNullOrBlank()) {
        return "$fallback (HTTP ${response.code()})"
    }

    return try {
        val json = Gson().fromJson(raw, JsonObject::class.java)
        val message = json?.get("message")?.asString

        if (!message.isNullOrBlank()) {
            message
        } else {
            "$fallback (HTTP ${response.code()})"
        }
    } catch (e: Exception) {
        "$fallback (HTTP ${response.code()})"
    }
}
