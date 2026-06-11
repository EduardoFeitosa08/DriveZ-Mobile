package com.example.drivez.ui.home_prestador

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drivez.core.network.HomePrestadorApiService
import com.example.drivez.data.model.Cliente
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class HomePrestadorViewModel(
    private val apiService: HomePrestadorApiService
) : ViewModel() {

    var uiState by mutableStateOf(HomePrestadorUiState())
        private set

    init {
        carregarPedidosEClientes()
    }

    fun carregarPedidosEClientes() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, erro = null)
            try {
                val pedidosResponse = apiService.getTodosOsPedidos()

                if (pedidosResponse.isSuccessful && pedidosResponse.body() != null) {
                    val listaPedidos = pedidosResponse.body()!!

                    val listaClientesEnriched = listaPedidos.map { pedido ->
                        async {
                            try {
                                val clienteResponse = apiService.getDetalhesCliente(pedido.clienteId)
                                if (clienteResponse.isSuccessful && clienteResponse.body() != null) {
                                    val dadosCliente = clienteResponse.body()!!

                                    Cliente(
                                        id = pedido.id,
                                        nome = dadosCliente.nome,
                                        telefone = dadosCliente.telefone,
                                        email = dadosCliente.email,
                                        imgPerfil = dadosCliente.imgPerfil,
                                        distancia = pedido.distancia ?: 0.0,
                                        cpf = dadosCliente.cpf,
                                        cnpj = dadosCliente.cnpj
                                    )
                                } else {
                                    null
                                }
                            } catch (e: Exception) {
                                null
                            }
                        }
                    }.awaitAll().filterNotNull()

                    uiState = uiState.copy(
                        listaClientes = listaClientesEnriched,
                        isLoading = false
                    )

                } else {
                    uiState = uiState.copy(isLoading = false, erro = "Erro ao buscar pedidos")
                }
            } catch (e: Exception) {
                uiState = uiState.copy(isLoading = false, erro = "Falha de rede: ${e.localizedMessage}")
            }
        }
    }
}