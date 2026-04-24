package com.example.drivez.data.model

data class Pedido(
    val id: Int,
    val status: StatusPedido,
    val dataSolicitacao: String,
    val enderecoOrigem: String,
    val enderecoDestino: String,
    val descricao: String,
    val distancia: String,
    val prestadorId: Int,
    val clienteId: Int
)