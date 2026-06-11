package com.example.drivez.ui.home_prestador

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.drivez.R
import com.example.drivez.ui.components.BottomPrestadorBar
import com.example.drivez.ui.components.CardCliente

//import com.mapbox.geojson.Point
//import com.mapbox.maps.CameraOptions
//import com.mapbox.maps.MapView
//import com.mapbox.maps.Style

@Composable
fun HomePrestadorScreen(
    navController: NavController,
    viewModel: HomePrestadorViewModel
) {
    val state = viewModel.uiState
    var isListVisible by remember { mutableStateOf(false) }

    val progresso = remember { Animatable(1f) }
    var resetKey by remember { mutableStateOf(0) }

    LaunchedEffect(resetKey) {
        progresso.snapTo(0f)
        progresso.animateTo(
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 3 * 60 * 1000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
//        AndroidView(
//            factory = { context ->
//                MapView(context).apply {
//                    getMapboxMap().loadStyleUri(Style.MAPBOX_STREAMS) {
//                        getMapboxMap().setCamera(
//                            CameraOptions.Builder()
//                                .center(Point.fromLngLat(state.longitudePrestador, state.latitudePrestador))
//                                .zoom(15.5)
//                                .build()
//                        )
//                    }
//                }
//            },
//            modifier = Modifier.fillMaxSize()
//        )

        Column(modifier = Modifier.align(Alignment.BottomCenter)) {

            // Botão Flutuante para expandir a lista de pedidos
            if (!isListVisible) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    FloatingActionButton(
                        onClick = { isListVisible = true },
                        containerColor = Color(0xFFB34B44),
                        contentColor = Color.White,
                        shape = CircleShape,
                        elevation = FloatingActionButtonDefaults.elevation(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.outline_keyboard_double_arrow_up_24),
                            contentDescription = "Abrir lista"
                        )
                    }
                }
            }

            if (isListVisible) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.6f)
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                    color = Color.White,
                    shadowElevation = 16.dp
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        IconButton(
                            onClick = { isListVisible = false },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.outline_keyboard_double_arrow_down_24),
                                contentDescription = "Fechar lista",
                                tint = Color.Gray
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Solicitações próximas a você", fontSize = 14.sp, color = Color.Black)
                            IconButton(
                                onClick = {
                                    resetKey++
                                    viewModel.carregarPedidosEClientes()
                                }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.baseline_refresh_24),
                                    contentDescription = "Atualizar",
                                    tint = Color(0xFF0F2042)
                                )
                            }
                        }

                        LinearProgressIndicator(
                            progress = { progresso.value },
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .height(4.dp),
                            color = Color.Red
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        if (state.isLoading) {
                            Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(color = Color(0xFFB34B44))
                            }
                        } else if (state.erro != null) {
                            Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                                Text(text = state.erro, color = Color.Red, fontSize = 14.sp)
                            }
                        } else {
                            LazyColumn(modifier = Modifier.weight(1f)) {
                                items(state.listaClientes) { cliente ->
                                    CardCliente(cliente, navController)
                                }
                            }
                        }
                    }
                }
            }

            BottomPrestadorBar(navController = navController)
        }
    }
}