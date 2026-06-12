package com.example.drivez.core.network

import com.example.drivez.data.dto.ApiResponseAvaliacao
import com.example.drivez.data.dto.ApiResponseWrapper
import com.example.drivez.data.dto.ClienteResponseDto
import com.example.drivez.data.dto.ClienteResponseWrapper
import com.example.drivez.data.dto.PedidoResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DrivezApiService {
    @GET("pedidos") // Mantém o seu endpoint normal
    suspend fun obterPedidosPendentes(): ApiResponseWrapper

    @GET("cliente/{id}")
    suspend fun obterClientePorId(@Path("id") id: Long): ClienteResponseWrapper

    @GET("avaliacoes/mediaCliente/{idCliente}")
    suspend fun obterMediaCliente(
        @Path("idCliente") idCliente: Long
    ): ApiResponseAvaliacao
}