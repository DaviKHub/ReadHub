package com.kukareku.readonline.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

data class OLDoc(val title: String?, val author_name: List<String>?, val cover_i: Int?, val ebook_count_i: Int?)
data class OLSearchResponse(val docs: List<OLDoc> = emptyList())

interface OpenLibraryService {
    @GET("search.json")
    suspend fun search(@Query("q") q: String): OLSearchResponse

    companion object {
        fun create(): OpenLibraryService {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
            val client = OkHttpClient.Builder().addInterceptor(logger).build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://openlibrary.org/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(OpenLibraryService::class.java)
        }
    }
}
