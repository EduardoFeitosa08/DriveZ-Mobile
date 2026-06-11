package com.example.drivez.core.network

import com.example.drivez.data.dto.ApiResponseAvaliacao
import retrofit2.http.GET
import retrofit2.http.Path

interface AvaliacaoApiService {

    @GET("v1/drivez/avaliacoes/mediaCliente/{idCliente}")
    suspend fun obterMediaCliente(
        @Path("idCliente") idCliente: Long
    ): ApiResponseAvaliacao
}