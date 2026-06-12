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
                    val apiRes = response.body()!!
                    val dados = apiRes.response
                    
                    val img = dados.imgPerfil
                    val fotoUrl = if (!img.isNullOrBlank()) {
                        if (img.startsWith("http")) img else "https://backend-drivez-atgfavb2cuccgrah.eastus2-01.azurewebsites.net/v1/drivez/cliente/foto/${clienteId}"
                    } else {
                        "https://backend-drivez-atgfavb2cuccgrah.eastus2-01.azurewebsites.net/v1/drivez/cliente/foto/${clienteId}"
                    }
                    
                    uiState = uiState.copy(
                        cliente = dados.copy(imgPerfil = fotoUrl),
                        isLoading = false
                    )
                } else {
                    // Fallback para erro 404 ou outros
                    uiState = uiState.copy(
                        cliente = ClienteDetalhesResponse(id = clienteId, nome = "Cliente", imgPerfil = "https://backend-drivez-atgfavb2cuccgrah.eastus2-01.azurewebsites.net/v1/drivez/cliente/foto/${clienteId}", telefone = null, email = null),
                        isLoading = false
                    )
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