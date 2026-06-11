package com.example.drivez.ui.detalhes_solicitacao

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drivez.core.network.HomePrestadorApiService
import com.example.drivez.data.model.ClienteDetalhesResponse
import kotlinx.coroutines.launch

class DetalhesSolicitacaoViewModel(
    private val apiService: HomePrestadorApiService
) : ViewModel() {

    var uiState by mutableStateOf(DetalhesSolicitacaoUiState())
        private set

    fun carregarDetalhesCliente(clienteId: Int) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, erro = null)
            try {
                val response = apiService.getDetalhesCliente(clienteId)
                if (response.isSuccessful && response.body() != null) {
                    uiState = uiState.copy(
                        cliente = response.body(),
                        isLoading = false
                    )
                } else {
                    uiState = uiState.copy(isLoading = false, erro = "Erro ao buscar detalhes do cliente")
                }
            } catch (e: Exception) {
                uiState = uiState.copy(isLoading = false, erro = "Falha de rede: ${e.localizedMessage}")
            }
        }
    }

    fun aceitarPedido(idPedido: Int, idPrestador: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            uiState = uiState.copy(isAccepting = true)
            try {
                val response = apiService.aceitarPedido(idPedido, idPrestador)
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    uiState = uiState.copy(isAccepting = false, erro = "Erro ao aceitar pedido")
                }
            } catch (e: Exception) {
                uiState = uiState.copy(isAccepting = false, erro = "Falha ao aceitar pedido: ${e.localizedMessage}")
            }
        }
    }
}

data class DetalhesSolicitacaoUiState(
    val isLoading: Boolean = false,
    val isAccepting: Boolean = false,
    val cliente: ClienteDetalhesResponse? = null,
    val erro: String? = null
)