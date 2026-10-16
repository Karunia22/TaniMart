package com.example.tanimart.data.network

import android.content.Context
import android.content.SharedPreferences

object TokenStorage {

    private const val PREF_NAME = "tanimart_session"
    private const val KEY_TOKEN = "auth_token"

    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
    }

    fun saveToken(token: String) {
        preferences.edit()
            .putString(KEY_TOKEN, token)
            .apply()
    }

    fun getToken(): String? {
        return preferences.getString(KEY_TOKEN, null)
    }

    fun clearToken() {
        preferences.edit()
            .remove(KEY_TOKEN)
            .apply()
    }
}