package com.example.drivez.data.repository

import com.example.drivez.data.model.Pedido
import com.example.drivez.core.network.PedidoApiService
import com.example.drivez.core.network.RetrofitClient

class HistoricoPedidoRepository {

    private val apiService: PedidoApiService = RetrofitClient.historicoPedidoApiService

    suspend fun obterHistoricoPedidos(prestadorId: Int): List<Pedido> {
        return apiService.getHistoricoPedidos(prestadorId)
    }
}