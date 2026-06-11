package com.example.drivez.ui.home_prestador

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.drivez.core.network.HomePrestadorApiService
import com.example.drivez.core.network.RetrofitClient
import com.example.drivez.data.dto.ClientePedidoDto
import com.example.drivez.ui.components.CardPedido
import kotlinx.coroutines.launch
import android.util.Log
import retrofit2.HttpException
import com.example.drivez.network.DrivezApiService
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

// Estrutura do Estado da UI que você já utiliza
data class HomePrestadorState(
    val isLoading: Boolean = false,
    val erro: String? = null,
    val listaClientes: List<ClientePedidoDto> = emptyList(),
    val latitudePrestador: Double = -23.5325, // Coordenadas padrão (ex: Jandira/Osasco)
    val longitudePrestador: Double = -46.7916
)

class HomePrestadorViewModel(
    private val apiService: DrivezApiService // Seu serviço do Retrofit injetado
) : ViewModel() {

    // Estado observável pela HomePrestadorScreen
    var uiState by mutableStateOf(HomePrestadorState())
        private set

    fun carregarPedidosEClientes() {
        viewModelScope.launch {
            // Ativa o loading na tela e limpa erros antigos
            uiState = uiState.copy(isLoading = true, erro = null)

            try {
                // 1. Puxa a lista de pedidos pendentes da Azure
                // (Substitua pelo nome real do método que você usa para listar os pedidos)
                val pedidosResponse = apiService.obterPedidosPendentes()

                if (pedidosResponse.isEmpty()) {
                    uiState = uiState.copy(listaClientes = emptyList(), isLoading = false)
                    return@launch
                }

                // 2. Mapeia a lista de pedidos buscando as informações complementares em paralelo
                val listaMapeadaComNotas = pedidosResponse.map { pedido ->
                    async {
                        // Busca o perfil do cliente dono do pedido (sua lógica atual)
                        val clienteDono = try {
                            apiService.obterClientePorId(pedido.clienteId)
                        } catch (e: Exception) {
                            null
                        }

                        // NOVA REQUISIÇÃO: Busca a média de avaliação do cliente na rota da Azure
                        val notaCliente = try {
                            val avaliacaoResponse = apiService.obterMediaCliente(pedido.clienteId)
                            // Converte a String "3.4" da API para Double. Se falhar ou for nulo, joga 0.0
                            avaliacaoResponse.dados.mediaNota.toDoubleOrNull() ?: 0.0
                        } catch (e: Exception) {
                            // Se a API de notas falhar ou o cliente não tiver notas, definimos como 0.0
                            // O componente de estrelas interceptará o 0.0 e desenhará 5 estrelas
                            0.0
                        }

                        // Monta o DTO final que a tela espera receber, agora incluindo a nota real
                        ClientePedidoDto(
                            pedidoId = pedido.id,
                            descricaoItem = pedido.descricao,
                            valorCombustivel = 35.0, // Valor mockado ou vindo do pedido
                            statusPedido = "PENDENTE",
                            enderecoOrigem = pedido.endereco_origem ?: "Endereço não informado",
                            clienteId = pedido.clienteId,
                            nomeCliente = clienteDono?.nome ?: "Cliente DriveZ ${pedido.clienteId}",
                            fotoUrl = clienteDono?.imgPerfil,
                            mediaNota = notaCliente // <-- Injeta a nota final aqui
                        )
                    }
                }.awaitAll() // Aguarda todas as requisições assíncronas terminarem juntas

                // Atualiza o estado da tela com a lista final preenchida
                uiState = uiState.copy(
                    listaClientes = listaMapeadaComNotas,
                    isLoading = false
                )

            } catch (e: Exception) {
                // Trata falhas gerais de rede ou servidor
                uiState = uiState.copy(
                    erro = "Erro ao carregar solicitações: ${e.localizedMessage ?: e.message}",
                    isLoading = false
                )
            }
        }
    }
}

@Composable
fun PainelPedidos(
    lista: List<ClientePedidoDto>,
    navController: NavController
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(lista) { item ->
            CardPedido(pedidoDto = item, navController = navController)
        }
    }
}