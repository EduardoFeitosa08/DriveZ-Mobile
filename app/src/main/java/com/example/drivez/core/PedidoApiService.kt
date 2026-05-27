package com.example.drivez.core

import com.example.drivez.data.model.Pedido
import retrofit2.http.GET
import retrofit2.http.Path

interface PedidoApiService {

    @GET("api/pedidos/historico/cliente/{clienteId}")
    suspend fun obterHistoricoPedidos(@Path("clienteId") clienteId: String): List<Pedido>
}