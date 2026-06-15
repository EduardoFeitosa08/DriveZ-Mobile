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
import kotlinx.coroutines.*
import retrofit2.HttpException

data class HomePrestadorState(
    val listaClientes: List<ClientePedidoDto> = emptyList(),
    val isLoading: Boolean = false,
    val erro: String? = null,
    val latitudePrestador: Double = 0.0,
    val longitudePrestador: Double = 0.0
)

data class EmergenciaUiState(
    val idPedido: Long = 0L,
    val idCliente: Long = 0L,
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

                val pedidosWrapper = RetrofitClient.drivezApiService.obterPedidosPendentes()
                val listaPedidosPura = pedidosWrapper.response

                if (listaPedidosPura.isEmpty()) {
                    uiState = uiState.copy(listaClientes = emptyList(), isLoading = false)
                    return@launch
                }

                delay(300)

                val listaMapeadaComNotas = listaPedidosPura.map { pedido ->
                    async {
                        val idClienteLong = pedido.id_cliente?.toLong() ?: 0L

                        val wrapperCliente = try {
                            RetrofitClient.drivezApiService.obterClientePorId(idClienteLong)
                        } catch (e: Exception) { null }
                        val clienteDono = wrapperCliente?.response

                        val notaCliente = try {
                            val avaliacaoResponse = RetrofitClient.drivezApiService.obterMediaCliente(idClienteLong)
                            avaliacaoResponse.dados.mediaNota.toDoubleOrNull() ?: 5.0
                        } catch (e: Exception) { 5.0 }

                        ClientePedidoDto(
                            pedidoId = pedido.id_pedido?.toLong() ?: 0L,
                            descricaoItem = pedido.descricao ?: "Sem descrição",
                            valorCombustivel = 35.0,
                            statusPedido = pedido.status ?: "PENDENTE",
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
        if (pollingJob?.isActive == true) return

        pollingJob = viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                try {
                    val pedidosWrapper = RetrofitClient.drivezApiService.obterPedidosPendentes()
                    val lista = pedidosWrapper.response

                    val pedidoGuinchoEmergencia = lista.firstOrNull { pedido ->
                        val desc = pedido.descricao?.lowercase() ?: ""
                        val status = pedido.status?.lowercase() ?: ""

                        val eEmergencia = desc.contains("guincho") || desc.contains("emergencia") || desc.contains("socorro")

                        val estaAtivo = status != "concluido" && status != "cancelado"

                        eEmergencia && estaAtivo
                    }

                    if (pedidoGuinchoEmergencia != null) {
                        val idCliente = pedidoGuinchoEmergencia.id_cliente?.toLong() ?: 0L

                        val wrapperCliente = try {
                            RetrofitClient.drivezApiService.obterClientePorId(idCliente)
                        } catch (e: Exception) { null }
                        val cliente = wrapperCliente?.response

                        withContext(Dispatchers.Main) {
                            emergenciaState = EmergenciaUiState(
                                idPedido = pedidoGuinchoEmergencia.id_pedido?.toLong() ?: 0L,
                                idCliente = pedidoGuinchoEmergencia.id_cliente?.toLong() ?: 0L,
                                nomeCliente = cliente?.nome ?: "Cliente DriveZ #$idCliente",
                                fotoCliente = cliente?.imgPerfil ?: "",
                                origem = pedidoGuinchoEmergencia.endereco_origem ?: "Não informada",
                                destino = pedidoGuinchoEmergencia.endereco_destino ?: "Não informado",
                                descricao = pedidoGuinchoEmergencia.descricao ?: "Emergência de Guincho",
                                notaCliente = 4.8,
                                isLoading = false
                            )

                            pararMonitoramentoEmergencia()

                            navController.navigate("home/prestador/detalhes_solicitacao/emergencia/$idCliente")
                        }
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

    fun aceitarPedidoEmergencia(pedidoId: Long, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val cuerpoActualizacao = mapOf("status" to "em_andamento")
                println("DriveZ-API-PUT: Tentando aceitar pedido #$pedidoId para EM_ANDAMENTO...")

                val resposta = RetrofitClient.drivezApiService.atualizarStatusPedido(pedidoId, cuerpoActualizacao)

                if (resposta.isSuccessful) {
                    println("DriveZ-API-PUT-Sucesso Real: Pedido #$pedidoId atualizado para EM_ANDAMENTO na Azure!")
                    withContext(Dispatchers.Main) {
                        onSuccess()
                    }
                } else {
                    val mensagemErroApi = resposta.errorBody()?.string()
                    println("DriveZ-API-PUT-Rejeitado pela Azure (Erro ${resposta.code()}): 👉 $mensagemErroApi")

                    withContext(Dispatchers.Main) { onSuccess() }
                }

            } catch (e: Exception) {
                println("DriveZ-API-PUT-Erro Rede: Falha catastrófica ao aceitar pedido #$pedidoId. Motivo: ${e.message}")
                e.printStackTrace()
                withContext(Dispatchers.Main) { onSuccess() }
            }
        }
    }

    fun concluirPedido(pedidoId: Long, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val corpoAtualizacao = mapOf("status" to "concluido")
                println("DriveZ-API-PUT: Tentando concluir pedido #$pedidoId na Azure...")

                val resposta = RetrofitClient.drivezApiService.atualizarStatusPedido(pedidoId, corpoAtualizacao)

                if (resposta.isSuccessful) {
                    println("DriveZ-API-PUT-Sucesso Real: Pedido #$pedidoId CONCLUÍDO com sucesso na Azure!")
                    withContext(Dispatchers.Main) {
                        onSuccess()
                    }
                } else {
                    val mensagemErroApi = resposta.errorBody()?.string()
                    println("DriveZ-API-PUT-Rejeitado pela Azure (Erro ${resposta.code()}): 👉 $mensagemErroApi")

                    withContext(Dispatchers.Main) { onSuccess() }
                }

            } catch (e: Exception) {
                println("DriveZ-API-PUT-Erro Rede: Falha catastrófica ao concluir pedido #$pedidoId. Motivo: ${e.message}")
                e.printStackTrace()
                withContext(Dispatchers.Main) { onSuccess() }
            }
        }
    }
}