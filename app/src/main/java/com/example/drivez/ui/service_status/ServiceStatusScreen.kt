package com.example.drivez.ui.service_status

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.drivez.R
import com.example.drivez.core.network.theme.AppColors
import com.example.drivez.core.network.theme.fontFamily
import com.example.drivez.data.model.*
import com.example.drivez.ui.components.*

@Composable
fun ServiceStatusScreen(navController: NavController, userId: String, isSOS: Boolean) {

    var cardState by remember { mutableStateOf(false) }

    var chatState by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE0E0E0)),
            contentAlignment = Alignment.Center
        ) {
            Text("Google Maps será carregado aqui")
        }

        IconButton(
            onClick = {
                cardState = true
            },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart)
                .background(Color.White, CircleShape)
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_arrow_back_24),
                contentDescription = "Voltar",
                tint = AppColors.DarkBlue
            )
        }

        //Quando o servicoState se tornar true significa que o servico foi concluido e com isso a aplicacao deve
        //exibir a o card de avaliacao do prestador (Pensar na ideia do WebSocket ou na notificacao)

        CardBuscandoPrestador(
            cancelarOnClick = { cardState = true },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp)
                .navigationBarsPadding(),
            isSOS = isSOS
        )
    }
    if (cardState) {
        CardConfirmacao(pergunta = "Cancelar Solicitação?", onBackClick = { cardState = false },
            onConfirmClick = {
                navController.navigate(route = "home/cliente")
            })
    }
    if (chatState) {
        ChatRapidoScreen(backOnClick = { chatState = false })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRapidoScreen(backOnClick: () -> Unit) {
    val contatoTeste = Contato(
        id = "100",
        name = "Rimberio - Guincho",
        ultimaMensagem = "Estou chegando na sua localização.",
        perfilImgUrl = "https://i.pravatar.cc/150?u=1"
    )

    //Lista de Mensagens para fazer o design
    val listaDeMensagens = listOf(
        Mensagem(
            id = "1",
            contatoId = "200", // ID do Prestador
            remententeId = "100", // ID do Cliente
            texto = "Olá, meu carro parou na rodovia.",
            horario = "10:00",
            status = StatusMensagem.LIDA,
            remetenteMensagem = RemetenteMensagem.CLIENTE
        ),
        Mensagem(
            id = "2",
            contatoId = "100",
            remententeId = "200",
            texto = "Bom dia! Qual seria o modelo do veículo?",
            horario = "10:02",
            status = StatusMensagem.LIDA,
            remetenteMensagem = RemetenteMensagem.PRESTADOR
        ),
        Mensagem(
            id = "3",
            contatoId = "200",
            remententeId = "100",
            texto = "É um sedan prata. Segue a foto do local.",
            horario = "10:05",
            status = StatusMensagem.LIDA,
            remetenteMensagem = RemetenteMensagem.CLIENTE
        ),
        Mensagem(
            id = "4",
            contatoId = "100",
            remententeId = "200",
            texto = "Recebido. O guincho chega em 10 minutos!",
            horario = "10:07",
            status = StatusMensagem.ENVIADA,
            remetenteMensagem = RemetenteMensagem.PRESTADOR
        )
    )

    var textoState by remember {
        mutableStateOf("")
    }

    val listState = rememberLazyListState()

    LaunchedEffect(listaDeMensagens.size) {
        if (listaDeMensagens.isNotEmpty()) {
            listState.scrollToItem(listaDeMensagens.size - 1)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Avatar simplificado (substitua por AsyncImage no futuro)
                        Surface(
                            modifier = Modifier.size(40.dp),
                            shape = CircleShape,
                            color = Color.LightGray,
                            tonalElevation = 6.dp,
                            shadowElevation = 8.dp
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_person_24),
                                contentDescription = null
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(text = contatoTeste.name, fontFamily = fontFamily)
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = backOnClick
                    ) {
                        Icon(
                            painterResource(R.drawable.baseline_arrow_back_24),
                            contentDescription = "Voltar"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding(),
                shadowElevation = 8.dp,
                color = Color.White
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = textoState,
                        onValueChange = { textoState = it },
                        placeholder = { Text("Texto") },
                        modifier = Modifier
                            .weight(1f)
                            .clip(CircleShape),
                        shape = CircleShape,
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    textoState = ""
                                }
                            ) {
                                Icon(
                                    painterResource(R.drawable.send_icon),
                                    contentDescription = "Enviar",
                                    tint = Color.DarkGray
                                )
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(listaDeMensagens) { mensagem ->
                BalaoChat(mensagem = mensagem, souOPrestador = false)
            }
        }
    }

}

@Composable
fun CardBuscandoPrestador(cancelarOnClick: () -> Unit, modifier: Modifier = Modifier, isSOS: Boolean) {
    val progresso = remember { Animatable(1f) }
    var resetKey by remember { mutableStateOf(0) }

    LaunchedEffect(resetKey) {
        progresso.snapTo(0f) // Garante que comece do zero imediatamente ao resetar
        progresso.animateTo(
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1 * 60 * 1000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (isSOS) "Aguardando Resposta dos Prestadores Próximos" else "Aguardando Resposta do Prestador",
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.DarkBlue,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(25.dp))
                LinearProgressIndicator(
                    progress = { progresso.value },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(13.dp)
                        .align(Alignment.CenterHorizontally)
                        .border(1.dp, AppColors.DarkBlue, RoundedCornerShape(15.dp)),
                    color = Color.Red
                )

                if (isSOS) {
                    Spacer(modifier = Modifier.height(40.dp))

                    //Depois tem que fazer essa funcao receber o endereco
                    AddressTimeline(
                        origin = "R. João Blesa - 45. Alphaville",
                        destination = "R. João Blesa - 45. Alphaville"
                    )

                    Spacer(modifier = Modifier.height(50.dp))
                } else {
                    Spacer(modifier = Modifier.height(50.dp))
                }

                Button(
                    onClick = cancelarOnClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 40.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.SecondaryRed,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text(
                        text = "Confirmar",
                        color = Color.White,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )
                }
            }
        }
    }
}
