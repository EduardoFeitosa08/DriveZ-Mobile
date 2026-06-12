package com.example.drivez.ui.registro_pedidos_cliente

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.util.Log
import com.example.drivez.core.network.PedidoApiService
import com.example.drivez.core.session.SessionManager
import com.example.drivez.data.model.Pedido
import com.example.drivez.data.model.StatusPedido
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ClienteRegistroDePedidosViewModel(
    private val apiService: PedidoApiService,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _pedidos = MutableStateFlow<List<Pedido>>(emptyList())
    val pedidos: StateFlow<List<Pedido>> = _pedidos.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        val userId = sessionManager.getUserId()
        Log.d("HISTORICO", "Iniciando busca para o usuário ID: $userId")
        if (userId != -1) {
            carregarHistoricoDePedidos(userId.toString())
        } else {
            _error.value = "Usuário não logado (ID -1)"
            Log.e("HISTORICO", "Erro: Usuário não logado no SessionManager")
        }
    }

    fun carregarHistoricoDePedidos(clienteId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val id = clienteId.toIntOrNull() ?: 0
                val response = apiService.getHistoricoPedidosCliente(id)
                
                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    Log.d("HISTORICO", "Sucesso API. Itens recebidos: ${body.response.size}")
                    
                    val pedidosMapped = body.response.map { p ->
                        Pedido(
                            id = p.id,
                            status = when (p.status?.uppercase()) {
                                "PENDENTE" -> StatusPedido.PENDENTE
                                "EM_ANDAMENTO" -> StatusPedido.EM_ANDAMENTO
                                "FINALIZADO" -> StatusPedido.FINALIZADO
                                else -> StatusPedido.FINALIZADO
                            },
                            dataSolicitacao = p.dataSolicitacao ?: "",
                            enderecoOrigem = p.enderecoOrigem ?: "Origem",
                            enderecoDestino = p.enderecoDestino ?: "Destino",
                            descricao = p.descricaoServico ?: "Sem descrição",
                            distancia = p.distancia ?: "0 km",
                            prestadorId = p.prestadorId ?: 0,
                            clienteId = p.clienteId,
                            prestadorNome = p.prestadorNome,
                            clienteNome = p.clienteNome
                        )
                    }
                    _pedidos.value = pedidosMapped
                    if (pedidosMapped.isEmpty()) {
                        Log.w("HISTORICO", "A lista mapeada está vazia para o cliente $id")
                    }
                } else {
                    val erroMsg = "Erro API: ${response.code()} - ${response.message()}"
                    _error.value = erroMsg
                    Log.e("HISTORICO", erroMsg)
                }
            } catch (e: Exception) {
                _error.value = "Falha na conexão: ${e.localizedMessage}"
                Log.e("HISTORICO", "Exceção ao carregar pedidos", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
