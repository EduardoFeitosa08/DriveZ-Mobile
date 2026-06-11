package com.example.drivez.ui.registro_pedidos_prestador

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drivez.core.network.HomePrestadorApiService
import com.example.drivez.data.model.Pedido
import com.example.drivez.data.model.StatusPedido
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class PrestadorRegistroDePedidosViewModel(
    private val apiService: HomePrestadorApiService
) : ViewModel() {

    var uiState by mutableStateOf(PrestadorRegistroDePedidosUiState())
        private set

    init {
        carregarPedidos()
    }

    fun carregarPedidos() {
        val prestadorIdLogado = 1 // ID fixo para teste, deve vir da sessão futuramente

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, erro = null)
            try {
                val response = apiService.getTodosOsPedidos()
                if (response.isSuccessful && response.body() != null) {
                    val pedidosRaw = response.body()!!.response
                        .filter { it.prestadorId == prestadorIdLogado }

                    val pedidosMapped = pedidosRaw.map { pedidoResponse ->
                        async {
                            var imgUrl: String? = null
                            var nomeCliente: String? = null
                            try {
                                val clienteResponse = apiService.getDetalhesCliente(pedidoResponse.clienteId)
                                if (clienteResponse.isSuccessful) {
                                    imgUrl = clienteResponse.body()?.imgPerfil
                                    nomeCliente = clienteResponse.body()?.nome
                                }
                            } catch (e: Exception) {
                                // Silently fail image fetch
                            }

                            Pedido(
                                id = pedidoResponse.id,
                                status = when (pedidoResponse.status?.uppercase()) {
                                    "PENDENTE" -> StatusPedido.PENDENTE
                                    "EM_ANDAMENTO" -> StatusPedido.EM_ANDAMENTO
                                    "FINALIZADO" -> StatusPedido.FINALIZADO
                                    else -> StatusPedido.FINALIZADO
                                },
                                dataSolicitacao = pedidoResponse.dataSolicitacao ?: "Data desconhecida",
                                enderecoOrigem = pedidoResponse.enderecoOrigem ?: "Origem padrão",
                                enderecoDestino = pedidoResponse.enderecoDestino ?: "Destino padrão",
                                descricao = pedidoResponse.descricaoServico ?: "Sem descrição",
                                distancia = "${pedidoResponse.distancia ?: "0"} km",
                                prestadorId = pedidoResponse.prestadorId ?: 0,
                                clienteId = pedidoResponse.clienteId,
                                clienteNome = nomeCliente,
                                clienteImgUrl = imgUrl
                            )
                        }
                    }.awaitAll()

                    uiState = uiState.copy(listaPedidos = pedidosMapped, isLoading = false)
                } else {
                    uiState = uiState.copy(isLoading = false, erro = "Erro ao carregar histórico")
                }
            } catch (e: Exception) {
                uiState = uiState.copy(isLoading = false, erro = "Falha na rede: ${e.localizedMessage}")
            }
        }
    }
}