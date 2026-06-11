package com.example.drivez.ui.registro_pedidos_prestador

import com.example.drivez.data.model.Pedido

data class PrestadorRegistroDePedidosUiState(
    val listaPedidos: List<Pedido> = emptyList(),
    val isLoading: Boolean = false,
    val erro: String? = null
)