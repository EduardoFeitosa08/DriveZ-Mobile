package com.example.drivez.core.network

import com.example.drivez.data.dto.ApiResponseAvaliacao
import com.example.drivez.data.dto.ApiResponseWrapper
import com.example.drivez.data.dto.ClienteResponseDto
import com.example.drivez.data.dto.ClienteResponseWrapper
import com.example.drivez.data.dto.PedidoResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
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

    @PATCH("pedidos/{id}")
    suspend fun atualizarStatusPedido(
        @Path("id") pedidoId: Long,
        @Body body: Map<String, String>
    )

    @PATCH("https://{supabase_id}.supabase.co/rest/v1/solicitacoes")
    suspend fun atualizarSupabaseRealtime(
        @Header("apikey") apiKey: String,
        @Header("Authorization") bearerToken: String,
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Prefer") prefer: String = "return=minimal",
        @Path("supabase_id", encoded = true) supabaseId: String,
        @retrofit2.http.Query("id") queryId: String,
        @Body corpo: Map<String, String>
    ): retrofit2.Response<Unit>
}