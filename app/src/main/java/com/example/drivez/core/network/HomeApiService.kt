package com.example.drivez.core.network

import com.example.drivez.data.model.Prestador
import retrofit2.http.GET

interface HomeApiService {
    @GET("prestador")
    suspend fun buscarPrestadores(): List<Prestador>
}