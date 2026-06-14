package com.example.drivez.ui.conversa_prestador

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.drivez.core.network.theme.AppColors
import com.example.drivez.data.model.Contato
import com.example.drivez.ui.components.AplicationTopBar
import com.example.drivez.ui.components.BottomPrestadorBar
import com.example.drivez.ui.components.ClienteCardContato
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


@Composable
fun ContatosPrestadorScreen(navController: NavController) {
    var listaDeContatosClientes by remember { mutableStateOf(listOf<Contato>()) }
    var clientesDaApi by remember { mutableStateOf(listOf<Contato>()) }

    val db = FirebaseFirestore.getInstance()

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            try {
                val url = URL("https://backend-drivez-atgfavb2cuccgrah.eastus2-01.azurewebsites.net/v1/drivez/cliente")
                val conexao = url.openConnection() as HttpURLConnection
                conexao.requestMethod = "GET"
                conexao.connectTimeout = 10000
                conexao.readTimeout = 10000

                if (conexao.responseCode == 200) {
                    val reader = BufferedReader(InputStreamReader(conexao.inputStream))
                    val respostaCompleta = StringBuilder()
                    var linha: String?
                    while (reader.readLine().also { linha = it } != null) {
                        respostaCompleta.append(linha)
                    }
                    reader.close()

                    val jsonObjetoPrincipal = JSONObject(respostaCompleta.toString())
                    val jsonArrayResponse = jsonObjetoPrincipal.getJSONArray("response")

                    val listaTemporariaApi = mutableListOf<Contato>()

                    for (i in 0 until jsonArrayResponse.length()) {
                        val itemCliente = jsonArrayResponse.getJSONObject(i)

                        val idCliente = itemCliente.getInt("id_cliente").toString()
                        val nomeCliente = itemCliente.getString("nome")

                        val fotoUrl = if (!itemCliente.isNull("img_perfil")) {
                            itemCliente.getString("img_perfil")
                        } else {
                            null
                        }

                        listaTemporariaApi.add(
                            Contato(
                                id = idCliente,
                                name = nomeCliente,
                                ultimaMensagem = "Cliente cadastrado no sistema",
                                perfilImgUrl = fotoUrl
                            )
                        )
                    }

                    withContext(Dispatchers.Main) {
                        val listaInvertada = listaTemporariaApi.reversed()
                        clientesDaApi = listaInvertada
                        listaDeContatosClientes = listaInvertada
                    }
                }
                conexao.disconnect()
            } catch (e: Exception) {
                println("DriveZ-API-Erro: ${e.message}")
            }
        }
    }

    // 2. Mesclagem inteligente com o Firebase
    LaunchedEffect(clientesDaApi) {
        db.collection("chats")
            .addSnapshotListener { snapshots, error ->
                if (error != null || snapshots == null) return@addSnapshotListener

                val chatsAtivosNoFirebase = snapshots.map { doc ->
                    Contato(
                        id = doc.id,
                        name = doc.getString("clienteNome") ?: "",
                        ultimaMensagem = doc.getString("ultimaMensagem") ?: "Sem mensagens",
                        perfilImgUrl = doc.getString("clienteFotoUrl")
                    )
                }

                val listaFinalMesclada = clientesDaApi.map { clienteApi ->
                    val chatCorrespondente = chatsAtivosNoFirebase.find {
                        it.id == clienteApi.id || it.name.contains(clienteApi.name, ignoreCase = true)
                    }

                    if (chatCorrespondente != null) {
                        clienteApi.copy(
                            id = chatCorrespondente.id,
                            ultimaMensagem = chatCorrespondente.ultimaMensagem,
                            perfilImgUrl = clienteApi.perfilImgUrl
                        )
                    } else {
                        clienteApi
                    }
                }

                listaDeContatosClientes = listaFinalMesclada
            }
    }

    Scaffold(
        topBar = {
            AplicationTopBar(
                navController = navController,
                titulo = "Contatos",
                retornavel = false,
                backgroundColor = AppColors.PrimaryRed,
                textColor = AppColors.TextWhite,
                iconColor = AppColors.TextWhite
            )
        },
        bottomBar = {
            BottomPrestadorBar(navController = navController)
        },
        containerColor = Color.White
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 15.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(listaDeContatosClientes) { contato ->
                    ClienteCardContato(contato = contato, navController = navController)
                }
            }
        }
    }
}