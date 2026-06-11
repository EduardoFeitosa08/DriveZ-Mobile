package com.example.drivez.core.network

import com.example.drivez.data.model.ClienteDetalhesResponse
import com.example.drivez.data.model.PedidoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HomePrestadorApiService {

    @GET("pedidos")
    suspend fun getTodosOsPedidos(): Response<List<PedidoResponse>>

    @GET("clientes/{id}")
    suspend fun getDetalhesCliente(
        @Path("id") clienteId: Int
    ): Response<ClienteDetalhesResponse>
}