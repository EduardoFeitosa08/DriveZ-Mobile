package com.example.drivez.data.model

import retrofit2.http.GET
import retrofit2.http.Path

interface PedidoApiService {
    @GET("v1/drivez/pedidos/historico/{prestadorId}")
    suspend fun getHistoricoPedidos(
        @Path("prestadorId") prestadorId: Int
    ): List<Pedido>
}