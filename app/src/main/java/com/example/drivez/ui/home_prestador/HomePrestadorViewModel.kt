package com.example.drivez.ui.home_prestador

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.drivez.core.network.RetrofitClient
import com.example.drivez.data.dto.ClientePedidoDto
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class HomePrestadorState(
    val listaClientes: List<ClientePedidoDto> = emptyList(),
    val isLoading: Boolean = false,
    val erro: String? = null,
    val latitudePrestador: Double = 0.0,
    val longitudePrestador: Double = 0.0
)

data class EmergenciaUiState(
    val idPedido: Long = 0L,
    val nomeCliente: String = "",
    val fotoCliente: String = "",
    val origem: String = "",
    val destino: String = "",
    val descricao: String = "",
    val notaCliente: Double = 5.0,
    val isLoading: Boolean = false
)

class HomePrestadorViewModel : ViewModel() {

    var uiState by mutableStateOf(HomePrestadorState())
        private set

    var emergenciaState by mutableStateOf(EmergenciaUiState())
        private set

    private var pollingJob: Job? = null

    fun carregarPedidosEClientes() {
        viewModelScope.launch {
            try {
                uiState = uiState.copy(isLoading = true, erro = null)

                // Busca a resposta envelopada da Azure
                val pedidosWrapper = RetrofitClient.drivezApiService.obterPedidosPendentes()
                val listaPedidosPura = pedidosWrapper.response

                if (listaPedidosPura.isEmpty()) {
                    uiState = uiState.copy(listaClientes = emptyList(), isLoading = false)
                    return@launch
                }

                delay(600)

                val listaMapeadaComNotas = listaPedidosPura.map { pedido ->
                    async {
                        val idClienteLong = pedido.id_cliente?.toLong() ?: 0L

                        val wrapperCliente = try {
                            RetrofitClient.drivezApiService.obterClientePorId(idClienteLong)
                        } catch (e: Exception) {
                            null
                        }
                        val clienteDono = wrapperCliente?.response

                        val notaCliente = try {
                            val avaliacaoResponse = RetrofitClient.drivezApiService.obterMediaCliente(idClienteLong)
                            avaliacaoResponse.dados.mediaNota.toDoubleOrNull() ?: 5.0
                        } catch (e: Exception) {
                            5.0
                        }

                        ClientePedidoDto(
                            pedidoId = pedido.id_pedido?.toLong() ?: 0L,
                            descricaoItem = pedido.descricao ?: "Sem descrição",
                            valorCombustivel = 35.0,
                            statusPedido = "PENDENTE",
                            enderecoOrigem = pedido.endereco_origem ?: "Endereço não informado",
                            clienteId = idClienteLong,
                            nomeCliente = if (!clienteDono?.nome.isNullOrBlank()) clienteDono!!.nome else "Cliente #$idClienteLong",
                            fotoUrl = clienteDono?.imgPerfil,
                            mediaNota = notaCliente
                        )
                    }
                }.awaitAll()

                uiState = uiState.copy(
                    listaClientes = listaMapeadaComNotas,
                    isLoading = false
                )

            } catch (e: Exception) {
                uiState = uiState.copy(
                    erro = "Erro ao carregar solicitações: ${e.message}",
                    isLoading = false
                )
            }
        }
    }

    fun iniciarMonitoramentoEmergencia(navController: NavController) {
        // Impede que mais de um loop seja criado ao mesmo tempo
        if (pollingJob?.isActive == true) return

        pollingJob = viewModelScope.launch {
            while (true) {
                try {
                    val pedidosWrapper = RetrofitClient.drivezApiService.obterPedidosPendentes()
                    val lista = pedidosWrapper.response

                    val pedidoGuinchoEmergencia = lista.firstOrNull { pedido ->
                        pedido.descricao?.contains("guincho", ignoreCase = true) == true
                    }

                    if (pedidoGuinchoEmergencia != null) {
                        val idCliente = pedidoGuinchoEmergencia.id_cliente?.toLong() ?: 0L

                        val wrapperCliente = try {
                            RetrofitClient.drivezApiService.obterClientePorId(idCliente)
                        } catch (e: Exception) { null }
                        val cliente = wrapperCliente?.response

                        emergenciaState = EmergenciaUiState(
                            idPedido = pedidoGuinchoEmergencia.id_pedido?.toLong() ?: 0L,
                            nomeCliente = cliente?.nome ?: "Cliente DriveZ #$idCliente",
                            fotoCliente = cliente?.imgPerfil ?: "",
                            origem = pedidoGuinchoEmergencia.endereco_origem ?: "Não informada",
                            destino = pedidoGuinchoEmergencia.endereco_destino ?: "Não informado",
                            descricao = pedidoGuinchoEmergencia.descricao ?: "Emergência de Guincho",
                            notaCliente = 4.8
                        )

                        pararMonitoramentoEmergencia()

                        navController.navigate("home/prestador/detalhes_solicitacao/emergencia/$idCliente")

                        break
                    }
                } catch (e: Exception) {
                    println("DriveZ-SOS-Polling: Buscando atualizações na Azure...")
                }

                delay(5000)
            }
        }
    }

    fun pararMonitoramentoEmergencia() {
        pollingJob?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        pararMonitoramentoEmergencia()
    }

    fun aceitarPedidoEmergencia(pedidoId: Long, idPrestadorLogado: Int, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val corpoAtualizacao = mapOf("status" to "EM_ANDAMENTO")
                RetrofitClient.drivezApiService.atualizarStatusPedido(pedidoId, corpoAtualizacao)

                try {
                    val urlCompleta = EnvConfig.SUPABASE_URL.replace("https://", "").replace(".supabase.co", "")
                    val apiKey = EnvConfig.SUPABASE_KEY

                    val dadosParaOVue = mapOf(
                        "status" to "aceito",
                        "id_prestador" to idPrestadorLogado.toString()
                    )

                    RetrofitClient.drivezApiService.atualizarSupabaseRealtime(
                        apiKey = apiKey,
                        bearerToken = "Bearer $apiKey",
                        supabaseId = urlCompleta,
                        queryId = "eq.$pedidoId",
                        corpo = dadosParaOVue
                    )
                    println("DriveZ-SOS: Tabela 'solicitacoes' atualizada com sucesso!")
                } catch (supabaseError: Exception) {
                    println("DriveZ-SOS-Erro: Falha ao falar com o Supabase: ${supabaseError.message}")
                }

                withContext(Dispatchers.Main) {
                    onSuccess()
                }

            } catch (e: Exception) {
                println("DriveZ-Erro-Aceitar: Falha na Azure -> ${e.message}")
                withContext(Dispatchers.Main) { onSuccess() }
            }
        }
    }
}