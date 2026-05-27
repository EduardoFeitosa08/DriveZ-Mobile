package com.example.drivez.ui.home_cliente

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.drivez.R
import com.example.drivez.core.network.theme.AppColors
import com.example.drivez.fontFamily
import com.example.drivez.ui.components.BottomClienteBar
import com.example.drivez.ui.components.CardCategorias
import com.example.drivez.ui.components.CardPrestador

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun HomeClienteScreen(
//    navController: NavController,
//    viewModel: HomeClienteViewModel = viewModel()
//) {
//    val listaPrestadores by viewModel.prestadores.collectAsState()
//    val notificacoesAtivas by viewModel.notificacoesAtivas.collectAsState()
//
//    val progresso = remember { Animatable(1f) }
//    var resetKey by remember { mutableStateOf(0) }
//
//    LaunchedEffect(resetKey) {
//        progresso.snapTo(0f)
//        progresso.animateTo(
//            targetValue = 1f,
//            animationSpec = infiniteRepeatable(
//                animation = tween(durationMillis = 3 * 60 * 1000, easing = LinearEasing),
//                repeatMode = RepeatMode.Restart
//            )
//        )
//        viewModel.buscarPrestadoresProximos()
//    }
//
//    Scaffold(
//        containerColor = Color.White,
//        topBar = {
//            TopAppBar(
//                title = {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth(),
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.Center
//                    ) {
//                        Image(
//                            painter = painterResource(R.drawable.logo_home),
//                            contentDescription = "DriveZ",
//                            modifier = Modifier
//                                .width(220.dp)
//                                .height(50.dp)
//                        )
//                    }
//                },
//                actions = {
//                    IconButton(
//                        onClick = {
//                            viewModel.alternarNotificacoes()
//                        }
//                    ) {
//                        Icon(
//                            painter = painterResource(
//                                if (notificacoesAtivas) R.drawable.baseline_notifications_active_24
//                                else R.drawable.baseline_notifications_off_24
//                            ),
//                            contentDescription = if (notificacoesAtivas) "Notificação Ativa" else "Notificação Desativada",
//                            tint = AppColors.DarkBlue,
//                            modifier = Modifier
//                                .size(50.dp)
//                                .padding(end = 15.dp)
//                        )
//                    }
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = Color.White
//                )
//            )
//        },
//        bottomBar = { BottomClienteBar(navController = navController) }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .padding(paddingValues)
//                .fillMaxSize()
//                .padding(horizontal = 10.dp)
//        ) {
//            Column(
//                modifier = Modifier.padding(16.dp)
//            ) {
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Column() {
//                        Text(
//                            text = "Prestadores disponiveis na sua região",
//                            color = Color.Black,
//                            fontFamily = fontFamily,
//                            fontWeight = FontWeight.SemiBold,
//                            fontSize = 15.sp
//                        )
//                        Spacer(modifier = Modifier.height(8.dp))
//                        LinearProgressIndicator(
//                            progress = { progresso.value },
//                            modifier = Modifier
//                                .fillMaxWidth(0.8f)
//                                .height(4.dp),
//                            color = Color.Red
//                        )
//                    }
//                    Icon(
//                        painter = painterResource(R.drawable.baseline_refresh_24),
//                        contentDescription = "Atualizar",
//                        tint = AppColors.DarkBlue,
//                        modifier = Modifier
//                            .size(35.dp)
//                            .clickable {
//                                viewModel.buscarPrestadoresProximos()
//                                resetKey++
//                            }
//                    )
//                }
//                Button(
//                    onClick = {
//                    },
//                    modifier = Modifier
//                        .padding(top = 8.dp),
//                    shape = RoundedCornerShape(15.dp),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = AppColors.SecondaryRed,
//                        contentColor = Color.White
//                    )
//                ) {
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.SpaceEvenly
//                    ) {
//                        Icon(
//                            painter = painterResource(R.drawable.baseline_filter_alt_24),
//                            contentDescription = "Filtrar",
//                            tint = Color.White
//                        )
//                        Spacer(modifier = Modifier.width(10.dp))
//                        Text(
//                            text = "Filtrar"
//                        )
//                    }
//                }
//            }
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .fillMaxHeight()
//                    .padding(bottom = 40.dp),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.spacedBy(20.dp)
//            ) {
//                items(listaPrestadores) { prestador ->
//                    CardPrestador(prestador = prestador, navController = navController)
//                }
//            }
//        }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeClienteScreen(
    navController: NavController,
    viewModel: HomeClienteViewModel = viewModel()
) {
    val listaPrestadores by viewModel.prestadores.collectAsState()
    val notificacoesAtivas by viewModel.notificacoesAtivas.collectAsState()

    val progresso = remember { Animatable(1f) }
    var resetKey by remember { mutableStateOf(0) }

    var mostrarCategorias by remember { mutableStateOf(false) }
    var categoriaFiltroAtual by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(resetKey) {
        progresso.snapTo(0f)
        progresso.animateTo(
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 3 * 60 * 1000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )
        viewModel.buscarPrestadoresProximos()
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.logo_home),
                            contentDescription = "DriveZ",
                            modifier = Modifier
                                .width(220.dp)
                                .height(50.dp)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.alternarNotificacoes() }
                    ) {
                        Icon(
                            painter = painterResource(
                                if (notificacoesAtivas) R.drawable.baseline_notifications_active_24
                                else R.drawable.baseline_notifications_off_24
                            ),
                            contentDescription = if (notificacoesAtivas) "Notificação Ativa" else "Notificação Desativada",
                            tint = AppColors.DarkBlue,
                            modifier = Modifier
                                .size(50.dp)
                                .padding(end = 15.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = { BottomClienteBar(navController = navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Prestadores disponiveis na sua região",
                            color = Color.Black,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = { progresso.value },
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .height(4.dp),
                            color = Color.Red
                        )
                    }
                    Icon(
                        painter = painterResource(R.drawable.baseline_refresh_24),
                        contentDescription = "Atualizar",
                        tint = AppColors.DarkBlue,
                        modifier = Modifier
                            .size(35.dp)
                            .clickable {
                                viewModel.buscarPrestadoresProximos()
                                resetKey++
                            }
                    )
                }

                Button(
                    onClick = { mostrarCategorias = true },
                    modifier = Modifier.padding(top = 8.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.SecondaryRed,
                        contentColor = Color.White
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_filter_alt_24),
                            contentDescription = "Filtrar",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = categoriaFiltroAtual ?: "Filtrar"
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(bottom = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(listaPrestadores) { prestador ->
                    CardPrestador(prestador = prestador, navController = navController)
                }
            }
        }

        if (mostrarCategorias) {
            CardCategorias(
                categoriaAtual = categoriaFiltroAtual, // <-- PASSA O ESTADO ATUAL AQUI
                onDismissRequest = { mostrarCategorias = false },
                onCategoriaSelecionada = { categoria ->
                    categoriaFiltroAtual = categoria
                    // viewModel.buscarPrestadoresFiltrados(categoria)
                }
            )
        }
    }
}