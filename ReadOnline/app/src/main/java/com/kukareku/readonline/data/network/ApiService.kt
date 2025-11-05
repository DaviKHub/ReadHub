package com.kukareku.readonline.data.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val success: Boolean, val token: String?, val userId: Long?, val message: String?)
data class RegisterRequest(val email: String, val password: String, val displayName: String)
data class RegisterResponse(val success: Boolean, val token: String?, val userId: Long?, val message: String?)

// path: data/network/ApiService.kt (дополни существующий)
data class BookSearchResponseDoc(
    val title: String?,
    val author_name: List<String>?,
    val cover_i: Int?,
    val ebook_count_i: Int?,
    // возможные поля, которые иногда встречаются в Open Library / других API:
    val ia: List<String>?,          // Internet Archive identifiers (может давать прямой доступ)
    val has_fulltext: Boolean? = null,
    val seed: List<String>?,
    // кастомное имя поля: если твой бекенд возвращает прямую ссылку
    val ebook_url: String? = null
)
data class BookSearchResponse(val docs: List<BookSearchResponseDoc>)

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @GET("search.json")
    suspend fun searchBooks(@Query("q") query: String): Response<BookSearchResponse>
}

