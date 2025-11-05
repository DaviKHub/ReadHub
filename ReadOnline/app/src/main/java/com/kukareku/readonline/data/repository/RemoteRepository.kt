package com.kukareku.readonline.data.repository

import android.content.Context
import com.kukareku.readonline.app.data.network.ApiClient
import com.readonline.app.data.network.LoginRequest
import com.readonline.app.data.network.RegisterRequest
import com.readonline.app.data.network.BookSearchResponse
import retrofit2.Response

class RemoteRepository(private val context: Context) {
    private val prefs = context.getSharedPreferences("session", Context.MODE_PRIVATE)

    suspend fun login(email: String, password: String): Response<com.readonline.app.data.network.LoginResponse> {
        return ApiClient.instance.login(LoginRequest(email, password))
    }

    suspend fun register(email: String, password: String, displayName: String): Response<com.readonline.app.data.network.RegisterResponse> {
        return ApiClient.instance.register(RegisterRequest(email, password, displayName))
    }

    suspend fun searchBooks(query: String): Response<BookSearchResponse> {
        return ApiClient.instance.searchBooks(query)
    }

    fun saveToken(token: String) {
        prefs.edit().putString("auth_token", token).apply()
    }

    fun getToken(): String? {
        return prefs.getString("auth_token", null)
    }
}
