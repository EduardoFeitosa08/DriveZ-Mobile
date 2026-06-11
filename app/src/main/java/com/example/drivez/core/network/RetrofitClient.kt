package com.example.drivez.core.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "https://backend-drivez-atgfavb2cuccgrah.eastus2-01.azurewebsites.net/v1/drivez/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authApiService: AuthApiService by lazy {
        retrofit.create(AuthApiService::class.java)
    }
    val homeApiService: HomeApiService by lazy{
        retrofit.create(HomeApiService::class.java)
    }
    val homePrestadorApiService: HomePrestadorApiService by lazy {
        retrofit.create(HomePrestadorApiService::class.java)
    }
    // Agora vai usar a interface correta que está na mesma pasta (network)
    val historicoPedidoApiService: PedidoApiService by lazy {
        retrofit.create(PedidoApiService::class.java)
    }
}

