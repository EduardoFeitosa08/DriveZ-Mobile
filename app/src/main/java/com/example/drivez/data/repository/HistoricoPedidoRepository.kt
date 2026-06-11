package com.example.drivez.data.repository

import com.example.drivez.core.network.PedidoApiService
import com.example.drivez.core.network.RetrofitClient
import com.example.drivez.data.model.Pedido

//class PedidoRepository(
//    private val apiService: PedidoApiService = RetrofitClient.pedidoApiService
//) {
//    suspend fun obterHistoricoPedidos(clienteId: String): List<Pedido> {
//        return apiService.obtainHistoricoPedidos(clienteId)
//    }
//}