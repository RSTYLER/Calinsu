// Network.kt
package ru.example.canlisu.data.net

import android.annotation.SuppressLint
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.example.canlisu.data.auth.AuthApi

object Network {
    // Поменяй на свой хост: для эмулятора локальной машины — 10.0.2.2
    @SuppressLint("AuthLeak")
    private const val BASE_URL = "postgresql://neondb_owner:npg_yZt1jS0oMfhr@ep-wispy-forest-a2cgtub3-pooler.eu-central-1.aws.neon.tech/neondb?sslmode=require&channel_binding=require"

    private val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()
    }

    val authApi: AuthApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }
}
