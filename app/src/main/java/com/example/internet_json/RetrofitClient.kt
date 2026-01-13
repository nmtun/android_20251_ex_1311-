package com.example.internet_json

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    const val BASE_URL = "https://lebavui.io.vn/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }

    fun getImageUrl(thumbnail: String?): String {
        if (thumbnail.isNullOrEmpty()) return ""

        return if (thumbnail.startsWith("http://") || thumbnail.startsWith("https://")) {
            thumbnail
        } else {
            val path = if (thumbnail.startsWith("/")) thumbnail.substring(1) else thumbnail
            BASE_URL + path
        }
    }
}

