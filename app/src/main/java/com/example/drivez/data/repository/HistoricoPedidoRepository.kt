package com.example.drivez.data.repository

import com.example.drivez.core.network.PedidoApiService
import com.example.drivez.core.network.RetrofitClient
import com.example.drivez.data.model.Pedido
import com.example.drivez.data.model.StatusPedido

class HistoricoPedidoRepository {

    private val apiService: PedidoApiService = RetrofitClient.historicoPedidoApiService

    suspend fun obterHistoricoPedidosPrestador(prestadorId: Int): List<Pedido> {
        return try {
            val response = apiService.getHistoricoPedidosPrestador(prestadorId)
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.response.map { res ->
                    Pedido(
                        id = res.id,
                        status = mapStatus(res.status),
                        dataSolicitacao = res.dataSolicitacao ?: "Data desconhecida",
                        enderecoOrigem = res.enderecoOrigem ?: "Origem não informada",
                        enderecoDestino = res.enderecoDestino ?: "Destino não informado",
                        descricao = res.descricaoServico ?: "Sem descrição",
                        distancia = "${res.distancia ?: "0"} km",
                        prestadorId = res.prestadorId ?: 0,
                        clienteId = res.clienteId,
                        prestadorNome = res.prestadorNome,
                        clienteNome = res.clienteNome
                    )
                }
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun obterHistoricoPedidosCliente(clienteId: Int): List<Pedido> {
        return try {
            val response = apiService.getHistoricoPedidosCliente(clienteId)
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.response.map { res ->
                    Pedido(
                        id = res.id,
                        status = mapStatus(res.status),
                        dataSolicitacao = res.dataSolicitacao ?: "Data desconhecida",
                        enderecoOrigem = res.enderecoOrigem ?: "Origem não informada",
                        enderecoDestino = res.enderecoDestino ?: "Destino não informado",
                        descricao = res.descricaoServico ?: "Sem descrição",
                        distancia = "${res.distancia ?: "0"} km",
                        prestadorId = res.prestadorId ?: 0,
                        clienteId = res.clienteId,
                        prestadorNome = res.prestadorNome,
                        clienteNome = res.clienteNome
                    )
                }
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun mapStatus(status: String?): StatusPedido {
        return when (status?.uppercase()) {
            "PENDENTE" -> StatusPedido.PENDENTE
            "EM_ANDAMENTO" -> StatusPedido.EM_ANDAMENTO
            "FINALIZADO" -> StatusPedido.FINALIZADO
            else -> StatusPedido.FINALIZADO
        }
    }
}
