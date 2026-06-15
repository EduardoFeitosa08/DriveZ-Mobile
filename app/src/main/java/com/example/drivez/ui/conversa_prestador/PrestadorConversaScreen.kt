package com.example.drivez.ui.conversa_prestador

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.drivez.R
import com.example.drivez.core.network.theme.AppColors
import com.example.drivez.data.model.Mensagem
import com.example.drivez.data.model.RemetenteMensagem
import com.example.drivez.data.model.StatusMensagem
import com.example.drivez.fontFamily
import com.example.drivez.ui.components.BalaoChat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.FieldValue
import com.google.firebase.Timestamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

fun formatarTimestampParaHora(firebaseTimestamp: Timestamp?): String {
    val data = firebaseTimestamp?.toDate() ?: Date()

    val formatador = SimpleDateFormat("HH:mm", Locale.getDefault())
    return formatador.format(data)
}

suspend fun buscarDadosClienteNoAzure(contatoId: String): Pair<String, String?>? {
    return withContext(Dispatchers.IO) {
        try {
            val url = URL("https://backend-drivez-atgfavb2cuccgrah.eastus2-01.azurewebsites.net/v1/drivez/cliente/$contatoId")
            val conexao = url.openConnection() as HttpURLConnection
            conexao.requestMethod = "GET"
            conexao.connectTimeout = 8000
            conexao.readTimeout = 8000

            if (conexao.responseCode == 200) {
                val reader = BufferedReader(InputStreamReader(conexao.inputStream))
                val respostaCompleta = StringBuilder()
                var linha: String?
                while (reader.readLine().also { linha = it } != null) {
                    respostaCompleta.append(linha)
                }
                reader.close()

                val jsonObjetoPrincipal = JSONObject(respostaCompleta.toString())
                if (jsonObjetoPrincipal.has("response")) {
                    val dadosCliente = jsonObjetoPrincipal.getJSONObject("response")

                    val nome = dadosCliente.getString("nome")
                    val foto = if (!dadosCliente.isNull("img_perfil")) dadosCliente.getString("img_perfil") else null

                    return@withContext Pair(nome, foto)
                }
            }
            conexao.disconnect()
        } catch (e: Exception) {
            println("DriveZ-Azure-Error: Falha catastrófica no parser: ${e.message}")
        }
        return@withContext null
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrestadorConversaScreen(navController: NavController, contatoId: String) {
    var textoState by remember { mutableStateOf("") }
    var listaDeMensagens by remember { mutableStateOf(listOf<Mensagem>()) }

    var nomeClienteHeader by remember { mutableStateOf("Carregando...") }
    var fotoClienteHeader by remember { mutableStateOf<String?>(null) }

    val db = FirebaseFirestore.getInstance()
    val listState = rememberLazyListState()

    LaunchedEffect(contatoId) {
        println("DriveZ-Rota: A tela de conversa abriu RECEBENDO o contatoId igual a: '$contatoId'")
    }

    LaunchedEffect(contatoId) {
        nomeClienteHeader = "Buscando dados..."

        db.collection("chats").document(contatoId).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val nomeFb = document.getString("clienteNome")
                    val fotoFb = document.getString("clienteFotoUrl")
                    if (!nomeFb.isNullOrBlank()) {
                        nomeClienteHeader = nomeFb
                        fotoClienteHeader = fotoFb
                        return@addOnSuccessListener
                    }
                }
            }

        val dadosDoAzure = buscarDadosClienteNoAzure(contatoId)
        if (dadosDoAzure != null) {
            nomeClienteHeader = dadosDoAzure.first
            fotoClienteHeader = dadosDoAzure.second
        } else {
            nomeClienteHeader = "Cliente #$contatoId"
        }
    }

    LaunchedEffect(contatoId) {
        db.collection("chats")
            .document(contatoId)
            .collection("messages")
            .orderBy("createdAt", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshots, error ->
                if (error != null) {
                    println("DriveZ-Erro-Firebase: ${error.message}")
                    return@addSnapshotListener
                }

                if (snapshots != null) {
                    println("DriveZ-Debug: O Firebase detectou ${snapshots.size()} documentos totais nesta sala.")

                    val listaTemporaria = mutableListOf<Mensagem>()

                    for (doc in snapshots.documents) {
                        try {
                            val texto = doc.getString("text") ?: ""
                            val imgUrl = doc.getString("imgUrl")

                            val senderBruto = doc.get("sender")
                            val senderStr = senderBruto?.toString()?.trim() ?: "prestador"

                            val remetente = if (senderStr.lowercase() == "prestador") {
                                RemetenteMensagem.PRESTADOR
                            } else {
                                RemetenteMensagem.CLIENTE
                            }

                            // 🌟 ALTERADO: Lendo 'createdAt' em vez de 'timestamp' para extrair a hora formatada
                            val firebaseTimestamp = doc.getTimestamp("createdAt")
                            val horaFormatada = formatarTimestampParaHora(firebaseTimestamp)

                            listaTemporaria.add(
                                Mensagem(
                                    id = doc.id,
                                    contatoId = contatoId,
                                    remententeId = senderStr,
                                    texto = texto,
                                    imgUrl = imgUrl,
                                    horario = horaFormatada,
                                    status = StatusMensagem.LIDA,
                                    remetenteMensagem = remetente
                                )
                            )
                        } catch (e: Exception) {
                            println("DriveZ-Debug: Falha ao mapear o documento [${doc.id}]. Erro: ${e.message}")
                        }
                    }

                    listaDeMensagens = listaTemporaria
                }
            }
    }

    LaunchedEffect(listaDeMensagens.size) {
        if (listaDeMensagens.isNotEmpty()) {
            listState.animateScrollToItem(listaDeMensagens.size - 1)
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            TopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        val modeloImagemHeader = if (!fotoClienteHeader.isNullOrBlank() &&
                            fotoClienteHeader != "null" &&
                            fotoClienteHeader!!.startsWith("http")) {
                            fotoClienteHeader
                        } else {
                            null
                        }

                        AsyncImage(
                            model = modeloImagemHeader,
                            placeholder = painterResource(R.drawable.baseline_person_24),
                            error = painterResource(R.drawable.baseline_person_24),
                            contentDescription = "Foto de perfil",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(42.dp)
                                .clip(RoundedCornerShape(100))
                                .border(1.dp, Color.White, RoundedCornerShape(100))
                        )

                        Text(
                            text = nomeClienteHeader,
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_arrow_back_24),
                            contentDescription = "Voltar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppColors.PrimaryRed,
                    scrolledContainerColor = AppColors.PrimaryRed
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF7F9FC))
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(bottom = 16.dp, top = 16.dp, start = 12.dp, end = 12.dp),
                state = listState,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(listaDeMensagens) { msg ->
                    BalaoChat(mensagem = msg)
                }
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding()
                    .navigationBarsPadding(),
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(25.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(25.dp))
                        .background(Color(0xFFF1F3F6)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = textoState,
                        onValueChange = { textoState = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Digite uma mensagem...", color = Color.Gray) },
                        maxLines = 4,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )

                    IconButton(
                        onClick = {
                            val textoEnviar = textoState.trim()
                            if (textoEnviar.isNotBlank()) {
                                // 🌟 ALTERADO: Mudado de "timestamp" para "createdAt" para casar com a ordenação e com o banco
                                val novaMensagemFirebase = mapOf(
                                    "text" to textoEnviar,
                                    "sender" to "prestador",
                                    "createdAt" to FieldValue.serverTimestamp()
                                )
                                db.collection("chats")
                                    .document(contatoId)
                                    .collection("messages")
                                    .add(novaMensagemFirebase)
                                    .addOnSuccessListener {
                                        textoState = ""
                                    }
                            }
                        },
                        enabled = textoState.isNotBlank()
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.send_icon),
                            contentDescription = "Enviar Mensagem",
                            tint = if (textoState.isNotBlank()) AppColors.PrimaryRed else Color.Gray
                        )
                    }
                }
            }
        }
    }
}