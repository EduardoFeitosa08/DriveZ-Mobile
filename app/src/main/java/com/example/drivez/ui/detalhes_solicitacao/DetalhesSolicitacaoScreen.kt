package com.example.drivez.ui.detalhes_solicitacao

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.drivez.R
import com.example.drivez.core.network.theme.AppColors
import com.example.drivez.core.network.theme.fontFamily
import com.example.drivez.ui.components.Avaliacao
import com.example.drivez.ui.components.BotaoAceitarArrastavel
import com.example.drivez.ui.components.BottomPrestadorBar

@Composable
fun DetalhesSolicitacaoScreen(
    navController: NavController,
    clienteId: String,
    viewModel: DetalhesSolicitacaoViewModel = viewModel()
) {
    LaunchedEffect(clienteId) {
        viewModel.carregarDetalhesCliente(clienteId.toInt())
    }

    val state = viewModel.uiState

    Scaffold(
        bottomBar = {
            BottomPrestadorBar(navController = navController)
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_arrow_back_24),
                        contentDescription = "Voltar",
                        modifier = Modifier.size(32.dp),
                        tint = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Surface(
                modifier = Modifier.size(150.dp),
                shape = CircleShape,
                border = BorderStroke(4.dp, Color(0xFFC0C0C0)),
                color = Color.White
            ) {
                AsyncImage(
                    model = state.cliente?.imgPerfil,
                    placeholder = painterResource(id = R.drawable.baseline_person_24),
                    error = painterResource(id = R.drawable.baseline_person_24),
                    contentDescription = "Foto do Cliente",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Avaliacao(3.0, 20.dp, 3.dp)

            Text(
                text = state.cliente?.nome ?: "Carregando...",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp),
                color = AppColors.DarkBlue
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Local de Solicitação",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp),
                    color = AppColors.DarkBlue
                )

                Card(
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(AppColors.PlaceholderGray)
                    ) { }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            if (state.isAccepting) {
                CircularProgressIndicator(color = AppColors.PrimaryRed)
            } else {
                Button(
                    onClick = {
                        // Id do prestador fixo para teste, ou vindo de algum estado global
                        viewModel.aceitarPedido(
                            idPedido = clienteId.toInt(), // Assumindo que clienteId aqui é o ID do pedido ou relacionado
                            idPrestador = 1,
                            onSuccess = {
                                navController.navigate("home/prestador/servico_status/$clienteId/false")
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(bottom = 24.dp, end = 30.dp, start = 30.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Aceitar Serviço",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalhesSolicitacaoEmergenciaScreen(
    onCorridaAceita: () -> Unit,
    clienteId: String,
    navController: NavController,
    viewModel: DetalhesSolicitacaoViewModel = viewModel()
) {
    LaunchedEffect(clienteId) {
        viewModel.carregarDetalhesCliente(clienteId.toInt())
    }

    val state = viewModel.uiState

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box() {
                        IconButton(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier.align(Alignment.TopStart)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_arrow_back_24),
                                contentDescription = "Voltar",
                                tint = AppColors.DarkBlue,
                                modifier = Modifier.size(50.dp)
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                                .align(Alignment.Center)
                        ) {
                            Text(
                                text = "🚨",
                                fontSize = 22.sp
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = "Emergência",
                                color = AppColors.PrimaryRed,
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                fontFamily = fontFamily
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomPrestadorBar(navController = navController)
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 25.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .border(2.dp, Color(0xFF6D6D6D), CircleShape)
                        .padding(4.dp)
                ) {
                    AsyncImage(
                        model = state.cliente?.imgPerfil,
                        placeholder = painterResource(R.drawable.baseline_person_24),
                        error = painterResource(R.drawable.baseline_person_24),
                        contentDescription = "Foto do Cliente",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Avaliacao(3.0, 25.dp, 3.dp)

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = state.cliente?.nome ?: "Carregando...",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Column() {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Origem:",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.DarkBlue
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Rodovia dos Bandeirantes, KM 152",
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Destino:",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.DarkBlue
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Centro Automotivo Porto, Rua das Flores, 500.",
                        fontSize = 15.sp,
                        color = Color.Black,
                        lineHeight = 20.sp
                    )
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Descrição:",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.DarkBlue
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Range Rover Evoque, Perda Total",
                        fontSize = 15.sp,
                        color = Color.Black,
                        lineHeight = 20.sp
                    )
                }
            }

            if (state.isAccepting) {
                CircularProgressIndicator(color = AppColors.PrimaryRed)
            } else {
                BotaoAceitarArrastavel(
                    modifier = Modifier.padding(bottom = 32.dp),
                    onAccept = {
                        viewModel.aceitarPedido(
                            idPedido = clienteId.toInt(),
                            idPrestador = 1,
                            onSuccess = {
                                onCorridaAceita()
                                navController.navigate("home/prestador/servico_status/$clienteId/true")
                            }
                        )
                    }
                )
            }
        }
    }
}
