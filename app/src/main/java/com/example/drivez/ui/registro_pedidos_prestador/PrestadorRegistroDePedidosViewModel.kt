package com.example.drivez.ui.registro_pedidos_prestador

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.util.Log
import com.example.drivez.core.network.HomePrestadorApiService
import com.example.drivez.core.network.PedidoApiService
import com.example.drivez.core.session.SessionManager
import com.example.drivez.data.model.Pedido
import com.example.drivez.data.model.StatusPedido
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class PrestadorRegistroDePedidosViewModel(
    private val apiService: HomePrestadorApiService,
    private val pedidoApiService: PedidoApiService,
    private val sessionManager: SessionManager? = null
) : ViewModel() {

    var uiState by mutableStateOf(PrestadorRegistroDePedidosUiState())
        private set

    init {
        carregarPedidos()
    }

    fun carregarPedidos() {
        val prestadorIdLogado = sessionManager?.getUserId() ?: -1
        Log.d("HISTORICO_DEBUG", "ID Prestador na Sessão: $prestadorIdLogado")

        if (prestadorIdLogado == -1) {
            uiState = uiState.copy(erro = "Usuário não logado", isLoading = false)
            return
        }

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, erro = null)
            try {
                val response = pedidoApiService.getHistoricoPedidosPrestador(prestadorIdLogado)
                Log.d("HISTORICO_DEBUG", "Chamada: ${response.raw().request.url}")
                Log.d("HISTORICO_DEBUG", "Resposta API Code: ${response.code()}")
                
                if (response.isSuccessful && response.body() != null) {
                    val apiResponse = response.body()!!
                    val pedidosRaw = apiResponse.response

                    Log.d("HISTORICO_DEBUG", "Total de pedidos recebidos da API: ${pedidosRaw.size}")
                    pedidosRaw.forEach { 
                        Log.d("HISTORICO_DEBUG", "Pedido ID: ${it.id}, Prestador ID no Pedido: ${it.prestadorId}, Buscando por: $prestadorIdLogado")
                    }

                    val pedidosMapped = pedidosRaw
                        .filter { 
                            val isMatch = it.prestadorId == prestadorIdLogado
                            Log.d("HISTORICO_DEBUG", "Filtro: Pedido ${it.id} (Prestador ${it.prestadorId}) == Logado ($prestadorIdLogado) ? $isMatch")
                            isMatch
                        }
                        .map { p ->
                        async {
                            var nomeCliente = p.clienteNome ?: "Cliente"
                            var fotoCliente = "https://backend-drivez-atgfavb2cuccgrah.eastus2-01.azurewebsites.net/v1/drivez/cliente/foto/${p.clienteId}"

                            try {
                                val clienteRes = apiService.getDetalhesCliente(p.clienteId)
                                if (clienteRes.isSuccessful && clienteRes.body() != null) {
                                    val apiResWrap = clienteRes.body()!!
                                    val detalhes = apiResWrap.response
                                    
                                    if (detalhes != null) {
                                        nomeCliente = detalhes.nome ?: nomeCliente
                                        val img = detalhes.imgPerfil
                                        fotoCliente = if (!img.isNullOrBlank()) {
                                            if (img.startsWith("http")) img else "https://backend-drivez-atgfavb2cuccgrah.eastus2-01.azurewebsites.net/v1/drivez/cliente/foto/${p.clienteId}"
                                        } else {
                                            "https://backend-drivez-atgfavb2cuccgrah.eastus2-01.azurewebsites.net/v1/drivez/cliente/foto/${p.clienteId}"
                                        }
                                    }
                                } else {
                                    // Caso a API de detalhes falhe (404), usamos o nome que veio no pedido raw e geramos a URL da foto pelo ID
                                    nomeCliente = p.clienteNome ?: "Cliente DriveZ"
                                    fotoCliente = "https://backend-drivez-atgfavb2cuccgrah.eastus2-01.azurewebsites.net/v1/drivez/cliente/foto/${p.clienteId}"
                                    Log.w("HISTORICO_DEBUG", "Detalhes do cliente ${p.clienteId} não encontrados. Usando fallback de imagem.")
                                }
                            } catch (e: Exception) {
                                Log.e("HISTORICO_DEBUG", "Erro ao carregar cliente ${p.clienteId}", e)
                            }

                            Log.d("HISTORICO_DEBUG", "Pedido ${p.id} -> Nome: $nomeCliente | URL Foto: $fotoCliente")

                            Pedido(
                                id = p.id,
                                status = when (p.status?.uppercase()) {
                                    "PENDENTE" -> StatusPedido.PENDENTE
                                    "EM_ANDAMENTO" -> StatusPedido.EM_ANDAMENTO
                                    "FINALIZADO" -> StatusPedido.FINALIZADO
                                    else -> StatusPedido.FINALIZADO
                                },
                                dataSolicitacao = p.dataSolicitacao ?: "",
                                enderecoOrigem = p.enderecoOrigem ?: "",
                                enderecoDestino = p.enderecoDestino ?: "",
                                descricao = p.descricaoServico ?: "",
                                distancia = p.distancia ?: "",
                                prestadorId = p.prestadorId ?: 0,
                                clienteId = p.clienteId,
                                clienteNome = nomeCliente,
                                prestadorNome = p.prestadorNome,
                                clienteImgUrl = fotoCliente
                            )
                        }
                    }.awaitAll()

                    uiState = uiState.copy(listaPedidos = pedidosMapped, isLoading = false)
                    Log.d("HISTORICO_DEBUG", "Estado atualizado com ${pedidosMapped.size} pedidos")
                } else {
                    uiState = uiState.copy(isLoading = false, erro = "API retornou erro ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("HISTORICO_DEBUG", "Erro fatal", e)
                uiState = uiState.copy(isLoading = false, erro = "Erro de conexão: ${e.message}")
            }
        }
    }
}
