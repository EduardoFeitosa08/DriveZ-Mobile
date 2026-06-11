package com.example.drivez.ui.home_prestador

import com.example.drivez.data.dto.ClientePedidoDto

data class HomePrestadorUiState(
    val isLoading: Boolean = false,
    val erro: String? = null,

    val listaClientes: List<ClientePedidoDto> = emptyList(),

    val latitudePrestador: Double = -23.525622,
    val longitudePrestador: Double = -46.903273
)