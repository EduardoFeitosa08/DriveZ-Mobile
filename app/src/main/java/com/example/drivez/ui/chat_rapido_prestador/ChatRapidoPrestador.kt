package com.example.drivez.ui.chat_rapido_prestador

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.drivez.R
import com.example.drivez.core.network.theme.AppColors
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

data class MensagemDescartavel(
    val id: String = "",
    val enviadoPorPrestador: Boolean = true,
    val texto: String = "",
    val horario: String = ""
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRapidoDescartavelScreen(
    navController: NavController,
    salaId: String,
    clienteNome: String
) {
    val db = remember { FirebaseFirestore.getInstance() }

    val salaFixaTcc = "131"

    var textoMensagem by remember { mutableStateOf("") }
    var listaMensagens by remember { mutableStateOf<List<MensagemDescartavel>>(emptyList()) }

    // 🌟 CORRIGIDO: Agora escuta a coleção "chats" na sala "131"
    LaunchedEffect(salaFixaTcc) {
        db.collection("chats")
            .document(salaFixaTcc)
            .collection("messages")
            .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.ASCENDING)
            .addSnapshotListener { snapshots, error ->
                if (snapshots != null) {
                    val temporaria = mutableListOf<MensagemDescartavel>()
                    for (doc in snapshots.documents) {
                        val texto = doc.getString("text") ?: ""
                        val quemEnviou = doc.getString("sender") ?: ""

                        val dePrestador = quemEnviou != "cliente"

                        val timestamp = doc.getTimestamp("createdAt")
                        val horaFormatada = if (timestamp != null) {
                            val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
                            sdf.format(timestamp.toDate())
                        } else {
                            ""
                        }

                        temporaria.add(
                            MensagemDescartavel(
                                id = doc.id,
                                enviadoPorPrestador = dePrestador,
                                texto = texto,
                                horario = horaFormatada
                            )
                        )
                    }
                    listaMensagens = temporaria
                }
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(clienteNome, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = AppColors.TextWhite)
                        Text("Chat de Emergência Descartável", fontSize = 12.sp, color = AppColors.BackgroundGray)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(painter = painterResource(R.drawable.baseline_arrow_back_24), contentDescription = "Voltar", tint = AppColors.TextWhite)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AppColors.PrimaryRed)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(AppColors.BackgroundGray)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp)
            ) {
                items(listaMensagens, key = { it.id }) { msg ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = if (msg.enviadoPorPrestador) Alignment.CenterEnd else Alignment.CenterStart
                    ) {
                        Card(
                            shape = RoundedCornerShape(
                                topStart = 12.dp,
                                topEnd = 12.dp,
                                bottomStart = if (msg.enviadoPorPrestador) 12.dp else 0.dp,
                                bottomEnd = if (msg.enviadoPorPrestador) 0.dp else 12.dp
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = if (msg.enviadoPorPrestador) AppColors.PrimaryRed else AppColors.CardBackground
                            ),
                            modifier = Modifier.widthIn(max = 280.dp)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = msg.texto,
                                    color = if (msg.enviadoPorPrestador) AppColors.TextWhite else AppColors.TitleGray,
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = msg.horario,
                                    color = if (msg.enviadoPorPrestador) AppColors.BackgroundGray else AppColors.PlaceholderGray,
                                    fontSize = 10.sp,
                                    modifier = Modifier.align(Alignment.End)
                                )
                            }
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppColors.CardBackground)
                    .padding(8.dp)
                    .navigationBarsPadding()
                    .imePadding(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = textoMensagem,
                    onValueChange = { textoMensagem = it },
                    placeholder = { Text("Digite uma mensagem rápida...", color = AppColors.PlaceholderGray) },
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedTextColor = AppColors.TitleGray,
                        unfocusedTextColor = AppColors.TitleGray
                    )
                )

                IconButton(
                    onClick = {
                        val mensagemLimpa = textoMensagem.trim()

                        if (mensagemLimpa.isNotEmpty()) {
                            val payload = mapOf(
                                "text" to mensagemLimpa,
                                "sender" to "prestador",
                                "senderName" to "Prestador DriveZ",
                                "createdAt" to com.google.firebase.firestore.FieldValue.serverTimestamp()
                            )

                            println("DriveZ-Firestore: Tentando injetar na sala 131...")

                            db.collection("chats")
                                .document(salaFixaTcc) // 🌟 Enviando para a coleção "chats" / documento 131
                                .collection("messages")
                                .add(payload)
                                .addOnSuccessListener { documento ->
                                    println("DriveZ-Firestore: MENSAGEM GRAVADA COM SUCESSO! ID: ${documento.id}")
                                    textoMensagem = ""
                                }
                                .addOnFailureListener { jsonErro ->
                                    println("DriveZ-Firestore: ERRO CRÍTICO AO GRAVAR: ${jsonErro.localizedMessage}")
                                    jsonErro.printStackTrace()
                                }
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.send_icon),
                        tint = AppColors.PrimaryRed,
                        contentDescription = "Enviar"
                    )
                }
            }
        }
    }
}