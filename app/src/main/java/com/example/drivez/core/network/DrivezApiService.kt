package com.example.drivez.core.network

import com.example.drivez.data.dto.ApiResponseAvaliacao
import com.example.drivez.data.dto.ApiResponseWrapper
import com.example.drivez.data.dto.ClienteResponseWrapper
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.Response

interface DrivezApiService {
    @GET("pedidos")
    suspend fun obterPedidosPendentes(): ApiResponseWrapper

    @GET("cliente/{id}")
    suspend fun obterClientePorId(@Path("id") id: Long): ClienteResponseWrapper

    @GET("avaliacoes/mediaCliente/{idCliente}")
    suspend fun obterMediaCliente(
        @Path("idCliente") idCliente: Long
    ): ApiResponseAvaliacao

    @Headers("Content-Type: application/json")
    @PUT("pedido/{id}/status")
    suspend fun atualizarStatusPedido(
        @Path("id") idPedido: Long,
        @Body status: Map<String, String>
    ): Response<Void>
}