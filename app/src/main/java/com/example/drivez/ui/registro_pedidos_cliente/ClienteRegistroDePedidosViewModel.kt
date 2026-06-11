package com.example.drivez.ui.registro_pedidos_cliente

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drivez.core.PedidoApiService
import com.example.drivez.data.model.Pedido
import com.example.drivez.data.model.StatusPedido
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ClienteRegistroDePedidosViewModel(
    private val apiService: com.example.drivez.core.PedidoApiService
) : ViewModel() {

    private val _pedidos = MutableStateFlow<List<Pedido>>(emptyList())
    val pedidos: StateFlow<List<Pedido>> = _pedidos.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        // Id fixo para teste: No seu JSON, o cliente 12 tem vários pedidos
        carregarHistoricoDePedidos("12")
    }

    fun carregarHistoricoDePedidos(clienteId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val responseWrapper = apiService.obterHistoricoPedidos(clienteId)
                val pedidosMapped = responseWrapper.response.map { p ->
                    Pedido(
                        id = p.id,
                        status = StatusPedido.FINALIZADO,
                        dataSolicitacao = p.dataSolicitacao ?: "",
                        enderecoOrigem = p.enderecoOrigem ?: "Origem",
                        enderecoDestino = p.enderecoDestino ?: "Destino",
                        descricao = p.descricaoServico ?: "Sem descrição",
                        distancia = p.distancia ?: "0 km",
                        prestadorId = p.prestadorId ?: 0,
                        clienteId = p.clienteId
                    )
                }
                _pedidos.value = pedidosMapped
            } catch (e: Exception) {
                _error.value = "Erro ao carregar histórico: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
