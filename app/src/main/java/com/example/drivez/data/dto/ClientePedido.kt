package com.example.drivez.data.dto

data class ClientePedidoDto(
    val pedidoId: Long,
    val descricaoItem: String,
    val valorCombustivel: Double,
    val statusPedido: String,
    val enderecoOrigem: String,
    val clienteId: Long,
    val nomeCliente: String,
    val fotoUrl: String? = null,
    val mediaNota: Double = 0.0
)