package com.example.drivez.ui.home_prestador

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drivez.core.network.HomePrestadorApiService
import com.example.drivez.core.session.SessionManager
import com.example.drivez.data.model.Cliente
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class HomePrestadorViewModel(
    private val apiService: HomePrestadorApiService,
    private val sessionManager: SessionManager
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
                    val listaPedidos = pedidosResponse.body()!!.response

                    val listaClientesEnriched = listaPedidos.map { pedido ->
                        async {
                            try {
                                val clienteResponse = apiService.getDetalhesCliente(pedido.clienteId)
                                if (clienteResponse.isSuccessful && clienteResponse.body() != null) {
                                    val apiRes = clienteResponse.body()!!
                                    val dadosCliente = apiRes.response

                                    val img = dadosCliente.imgPerfil
                                    val fotoUrl = if (!img.isNullOrBlank()) {
                                        if (img.startsWith("http")) img else "https://backend-drivez-atgfavb2cuccgrah.eastus2-01.azurewebsites.net/v1/drivez/cliente/foto/${pedido.clienteId}"
                                    } else {
                                        "https://backend-drivez-atgfavb2cuccgrah.eastus2-01.azurewebsites.net/v1/drivez/cliente/foto/${pedido.clienteId}"
                                    }

                                    Cliente(
                                        id = pedido.clienteId,
                                        nome = dadosCliente.nome ?: "Cliente DriveZ",
                                        telefone = dadosCliente.telefone ?: "",
                                        email = dadosCliente.email ?: "",
                                        imgPerfil = fotoUrl,
                                        distancia = pedido.distancia?.toDoubleOrNull() ?: 0.0,
                                        cpf = dadosCliente.cpf,
                                        cnpj = dadosCliente.cnpj
                                    )
                                } else {
                                    // Fallback se a API de detalhes falhar
                                    Cliente(
                                        id = pedido.clienteId,
                                        nome = "Cliente DriveZ",
                                        telefone = "",
                                        email = "",
                                        imgPerfil = "https://backend-drivez-atgfavb2cuccgrah.eastus2-01.azurewebsites.net/v1/drivez/cliente/foto/${pedido.clienteId}",
                                        distancia = pedido.distancia?.toDoubleOrNull() ?: 0.0,
                                        cpf = null,
                                        cnpj = null
                                    )
                                }
                            } catch (e: Exception) {
                                // Fallback em caso de erro de rede
                                Cliente(
                                    id = pedido.clienteId,
                                    nome = "Cliente DriveZ",
                                    telefone = "",
                                    email = "",
                                    imgPerfil = "https://backend-drivez-atgfavb2cuccgrah.eastus2-01.azurewebsites.net/v1/drivez/cliente/foto/${pedido.clienteId}",
                                    distancia = pedido.distancia?.toDoubleOrNull() ?: 0.0,
                                    cpf = null,
                                    cnpj = null
                                )
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