package com.example.drivez.ui.home_prestador

import com.example.drivez.data.model.Cliente

data class HomePrestadorUiState(
    val isLoading: Boolean = false,
    val listaClientes: List<Cliente> = emptyList(),
    val erro: String? = null,
    val latitudePrestador: Double = -23.5276,
    val longitudePrestador: Double = -46.9015
)