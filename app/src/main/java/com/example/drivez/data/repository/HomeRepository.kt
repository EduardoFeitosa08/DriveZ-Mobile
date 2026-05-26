package com.example.drivez.data.repository

import com.example.drivez.core.network.HomeApiService
import com.example.drivez.core.network.RetrofitClient
import com.example.drivez.data.model.Prestador

class HomeRepository(
    private val apiService: HomeApiService = RetrofitClient.homeApiService
) {
    suspend fun obterPrestadores(): List<Prestador> {
        return apiService.buscarPrestadores()
    }
}