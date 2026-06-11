package com.example.drivez.ui.conversa_cliente

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.drivez.R
import com.example.drivez.core.network.theme.AppColors
import com.example.drivez.core.network.theme.fontFamily
import com.example.drivez.data.model.Contato
import com.example.drivez.data.model.Mensagem
import com.example.drivez.data.model.RemetenteMensagem
import com.example.drivez.data.model.StatusMensagem
import com.example.drivez.ui.components.BalaoChat
import com.example.drivez.ui.components.BotaoFlutuante
import com.example.drivez.ui.components.CardConfirmacao

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClienteConversaScreen(navController: NavController, contatoId: String) {

    //Com o id do contato preciso pesquisar as mensagens do prestador e com isso o id dele
    //ira vir e quando solicitar o servico poderá com o id do prestador encontrar a localizacao dele
    //para comparar com quem pediu e dizer a distancia e também exibir a rua que o prestador está
    //e onde o cliente esta

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
            contatoId = "200",
            remententeId = "100",
            texto = null,
            imgUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRs17aTBsPEIrihX-smPkNhd9ccDK3O8tj-6Q&s",
            horario = "10:05",
            status = StatusMensagem.ENTREGUE,
            remetenteMensagem = RemetenteMensagem.CLIENTE
        ),
        Mensagem(
            id = "5",
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

    var showCard by remember { mutableStateOf(false) }

    LaunchedEffect(listaDeMensagens.size) {
        if (listaDeMensagens.isNotEmpty()) {
            listState.scrollToItem(listaDeMensagens.size - 1)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.height(90.dp),
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_arrow_back_24),
                                contentDescription = "Voltar",
                                tint = Color.White,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                        AsyncImage(
                            model = "${contatoTeste.perfilImgUrl}",
                            placeholder = painterResource(R.drawable.baseline_person_24),
                            error = painterResource(R.drawable.baseline_person_24),
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(100))
                                .border(1.dp, Color.White, RoundedCornerShape(100)),
                        )
                        Text(
                            text = contatoTeste.name,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            fontSize = 20.sp
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppColors.PrimaryRed,
                ),
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 15.dp)
                    .height(120.dp)
                    .navigationBarsPadding()
                    .imePadding()
                    .clip(RoundedCornerShape(20.dp))
                    .border(1.dp, AppColors.DarkBlue, RoundedCornerShape(20.dp)),
                actions = {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        IconButton(
                            onClick = {}
                        ) {
                            Image(
                                painter = painterResource(R.drawable.gallery_icon),
                                contentDescription = "Anexar"
                            )
                        }

                        TextField(
                            value = textoState,
                            onValueChange = { textoState = it },
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 4.dp),
                            placeholder = { Text("Mensagem...") },
                            maxLines = 5,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )

                        IconButton(
                            onClick = {

                            },
                            enabled = textoState.isNotBlank()
                        ) {
                            Image(
                                painter = painterResource(R.drawable.send_icon),
                                contentDescription = "Enviar",
                            )
                        }
                    }
                },
                containerColor = AppColors.CardBackground
            )
        },
        containerColor = AppColors.BackgroundConversaYellow
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            LazyColumn(
                contentPadding = PaddingValues(vertical = 20.dp),
                state = listState
            ) {
                items(listaDeMensagens) { item ->
                    BalaoChat(item, false)
                }
            }
            BotaoFlutuante(onClick = {
                showCard = true
            })
        }
    }
    if (showCard) {
        CardConfirmacao(pergunta = "Solicitar Prestador?", onBackClick = { showCard = false },
            onConfirmClick = { navController.navigate(route = "home/cliente/pedido/false") })
    }

}
