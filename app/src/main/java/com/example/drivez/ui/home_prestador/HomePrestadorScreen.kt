package com.example.drivez.ui.home_prestador

import android.graphics.BitmapFactory
import com.example.drivez.ui.components.BottomPrestadorBar
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.drivez.R
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.annotations
import com.example.drivez.util.obterCoordenadasDoEndereco
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createCircleAnnotationManager

@Composable
fun HomePrestadorScreen(
    navController: NavController,
    viewModel: HomePrestadorViewModel
) {
    val state = viewModel.uiState
    var isListVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val mapView = remember { MapView(context) }

    val progresso = remember { Animatable(1f) }
    var resetKey by remember { mutableStateOf(0) }

    var annotationManager by remember { mutableStateOf<CircleAnnotationManager?>(null) }

    LaunchedEffect(Unit) {
        viewModel.carregarPedidosEClientes()
    }

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

    LaunchedEffect(state.listaClientes, annotationManager) {
        annotationManager?.let { manager ->
            manager.deleteAll()

            val pontoPrestador = Point.fromLngLat(state.longitudePrestador, state.latitudePrestador)

            manager.create(
                CircleAnnotationOptions()
                    .withPoint(pontoPrestador)
                    .withCircleRadius(14.0)
                    .withCircleColor("#B34B44")
                    .withCircleOpacity(0.2)
            )
            manager.create(
                CircleAnnotationOptions()
                    .withPoint(pontoPrestador)
                    .withCircleRadius(7.0)
                    .withCircleColor("#B34B44")
                    .withCircleStrokeWidth(2.5)
                    .withCircleStrokeColor("#FFFFFF")
            )

            state.listaClientes.forEach { pedidoDto ->
                val enderecoTexto = pedidoDto.enderecoOrigem

                val pontoPedido = obterCoordenadasDoEndereco(context, enderecoTexto)

                if (pontoPedido != null) {
                    val auraPedido = CircleAnnotationOptions()
                        .withPoint(pontoPedido)
                        .withCircleRadius(12.0)
                        .withCircleColor("#2196F3")
                        .withCircleOpacity(0.25)

                    val centroPedido = CircleAnnotationOptions()
                        .withPoint(pontoPedido)
                        .withCircleRadius(6.0)
                        .withCircleColor("#2196F3")
                        .withCircleStrokeWidth(2.0)
                        .withCircleStrokeColor("#FFFFFF")

                    manager.create(auraPedido)
                    manager.create(centroPedido)
                } else {
                    val latSimulada = state.latitudePrestador + (kotlin.random.Random.nextDouble(-0.008, 0.008))
                    val lngSimulada = state.longitudePrestador + (kotlin.random.Random.nextDouble(-0.008, 0.008))
                    val pontoReserva = Point.fromLngLat(lngSimulada, latSimulada)

                    val centroPedido = CircleAnnotationOptions()
                        .withPoint(pontoReserva)
                        .withCircleRadius(6.0)
                        .withCircleColor("#2196F3")
                        .withCircleStrokeWidth(2.0)
                        .withCircleStrokeColor("#FFFFFF")

                    manager.create(centroPedido)
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        AndroidView(
            factory = { mapView },
            modifier = Modifier.fillMaxSize(),
            update = { mv ->
                mv.mapboxMap.loadStyle(Style.STANDARD)
                mv.mapboxMap.setCamera(
                    CameraOptions.Builder()
                        .center(Point.fromLngLat(state.longitudePrestador, state.latitudePrestador))
                        .zoom(14.0)
                        .build()
                )

                if (annotationManager == null) {
                    annotationManager = mv.annotations.createCircleAnnotationManager()
                }
            }
        )

        Column(modifier = Modifier.align(Alignment.BottomCenter)) {

            // Botão Flutuante animado para sumir suavemente quando a lista sobe
            AnimatedVisibility(
                visible = !isListVisible,
                enter = fadeIn(animationSpec = tween(200)),
                exit = fadeOut(animationSpec = tween(200)),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
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
                            painter = painterResource(id = R.drawable.outline_keyboard_double_arrow_up_24),
                            contentDescription = "Abrir lista"
                        )
                    }
                }
            }

            AnimatedVisibility(
                visible = isListVisible,
                enter = expandVertically(
                    animationSpec = tween(durationMillis = 400, easing = LinearEasing),
                    expandFrom = Alignment.Bottom
                ) + fadeIn(animationSpec = tween(300)),
                exit = shrinkVertically(
                    animationSpec = tween(durationMillis = 350, easing = LinearEasing),
                    shrinkTowards = Alignment.Bottom
                ) + fadeOut(animationSpec = tween(250))
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.6f)
                        .padding(horizontal = 16.dp),
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
                                painter = painterResource(id = R.drawable.outline_keyboard_double_arrow_down_24),
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
                                    painter = painterResource(id = R.drawable.baseline_refresh_24),
                                    contentDescription = "Atualizar",
                                    tint = Color(0xFF0F2042)
                                )
                            }
                        }

                        LinearProgressIndicator(
                            progress = { progresso.value },
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .height(12.dp)
                                .padding(vertical = 4.dp)
                                .align(Alignment.CenterHorizontally),
                            color = Color.Red
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        when {
                            state.isLoading -> {
                                Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                                    CircularProgressIndicator(color = Color(0xFFB34B44))
                                }
                            }
                            state.erro != null -> {
                                Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                                    Text(text = state.erro, color = Color.Red, fontSize = 14.sp)
                                }
                            }
                            else -> {
                                PainelPedidos(
                                    lista = state.listaClientes,
                                    navController = navController
                                )
                            }
                        }
                    }
                }
            }

            BottomPrestadorBar(navController = navController)
        }
    }

    DisposableEffect(mapView) {
        onDispose {
            mapView.onDestroy()
        }
    }
}