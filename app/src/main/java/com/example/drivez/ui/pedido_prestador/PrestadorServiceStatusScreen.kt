package com.example.drivez.ui.pedido_prestador

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.example.drivez.BuildConfig
import com.example.drivez.R
import com.example.drivez.core.network.RetrofitClient
import com.example.drivez.core.network.theme.AppColors
import com.example.drivez.ui.components.AddressTimeline
import com.example.drivez.ui.components.CardConfirmacao
import com.example.drivez.ui.home_prestador.HomePrestadorViewModel
import com.mapbox.geojson.Point
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.createPolylineAnnotationManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

@Composable
fun PrestadorServiceStatusScreen(
    navController: NavController,
    pedidoId: Long,
    clienteNome: String,
    clienteFotoUrl: String,
    enderecoOrigemTexto: String,
    enderecoDestinoTexto: String,
    viewModel: HomePrestadorViewModel
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var cardState by remember { mutableStateOf(false) }
    var statusDoPedidoLocal by remember { mutableStateOf("EM_ANDAMENTO") }

    var pontosDaRotaDinamica by remember { mutableStateOf<List<Point>>(emptyList()) }
    var isMapStyleLoaded by remember { mutableStateOf(false) }
    var isLoadingRota by remember { mutableStateOf(true) }

    val mapView = remember { MapView(context) }
    val token = remember { BuildConfig.MAPBOX_TOKEN }

    // Estado para recuperar o id_cliente vindo do estado de emergência do seu ViewModel
    val idCliente = viewModel.emergenciaState.idPedido // Usa o estado global mapeado no polling

    LaunchedEffect(pedidoId) {
        while (statusDoPedidoLocal == "EM_ANDAMENTO") {
            delay(4000)
            try {
                val resposta = RetrofitClient.drivezApiService.obterPedidosPendentes()
                val pedidoNoBanco = resposta.response.firstOrNull { it.id_pedido?.toLong() == pedidoId }

                if (pedidoNoBanco == null || pedidoNoBanco.status?.lowercase() == "cancelado") {
                    statusDoPedidoLocal = "CANCELADO"
                    break
                }
            } catch (e: Exception) {
                println("DriveZ-Sincronismo: Monitorando status do pedido #$pedidoId...")
            }
        }
    }

    suspend fun obterCoordenadasDoEndereco(address: String): Point? {
        return withContext(Dispatchers.IO) {
            try {
                val urlEncoded = URLEncoder.encode(address, "UTF-8")
                val urlStr = "https://api.mapbox.com/geocoding/v5/mapbox.places/$urlEncoded.json?access_token=$token&country=br&limit=1"
                val connection = URL(urlStr).openConnection() as HttpURLConnection
                val responseText = connection.inputStream.bufferedReader().use { it.readText() }
                val jsonObject = JSONObject(responseText)
                val features = jsonObject.getJSONArray("features")
                if (features.length() > 0) {
                    val centerArray = features.getJSONObject(0).getJSONArray("center")
                    Point.fromLngLat(centerArray.getDouble(0), centerArray.getDouble(1))
                } else null
            } catch (e: Exception) { null }
        }
    }

    LaunchedEffect(enderecoOrigemTexto, enderecoDestinoTexto) {
        isLoadingRota = true
        val pontoOrigem = obterCoordenadasDoEndereco(enderecoOrigemTexto)
        val pontoDestino = obterCoordenadasDoEndereco(enderecoDestinoTexto)

        if (pontoOrigem != null && pontoDestino != null) {
            withContext(Dispatchers.IO) {
                try {
                    val urlStr = "https://api.mapbox.com/directions/v5/mapbox/driving/${pontoOrigem.longitude()},${pontoOrigem.latitude()};${pontoDestino.longitude()},${pontoDestino.latitude()}?geometries=geojson&overview=full&access_token=$token"
                    val connection = URL(urlStr).openConnection() as HttpURLConnection
                    val responseText = connection.inputStream.bufferedReader().use { it.readText() }
                    val jsonObject = JSONObject(responseText)
                    val routes = jsonObject.getJSONArray("routes")
                    if (routes.length() > 0) {
                        val coordinates = routes.getJSONObject(0).getJSONObject("geometry").getJSONArray("coordinates")
                        val lista = mutableListOf<Point>()
                        for (i in 0 until coordinates.length()) {
                            val coordArray = coordinates.getJSONArray(i)
                            lista.add(Point.fromLngLat(coordArray.getDouble(0), coordArray.getDouble(1)))
                        }
                        withContext(Dispatchers.Main) {
                            pontosDaRotaDinamica = lista
                            isLoadingRota = false
                        }
                    }
                } catch (e: Exception) { isLoadingRota = false }
            }
        } else { isLoadingRota = false }
    }

    LaunchedEffect(mapView) {
        mapView.mapboxMap.loadStyle(Style.MAPBOX_STREETS) { isMapStyleLoaded = true }
    }

    LaunchedEffect(pontosDaRotaDinamica, isMapStyleLoaded) {
        if (pontosDaRotaDinamica.isNotEmpty() && isMapStyleLoaded) {
            val annotationApi = mapView.annotations
            val polylineAnnotationManager = annotationApi.createPolylineAnnotationManager()
            polylineAnnotationManager.deleteAll()

            polylineAnnotationManager.create(PolylineAnnotationOptions().withPoints(pontosDaRotaDinamica).withLineColor("#FFFFFF").withLineWidth(9.0).withLineOpacity(0.7))
            polylineAnnotationManager.create(PolylineAnnotationOptions().withPoints(pontosDaRotaDinamica).withLineColor("#DC2626").withLineWidth(5.0).withLineOpacity(1.0))

            val pointAnnotationManager = annotationApi.createPointAnnotationManager()
            pointAnnotationManager.deleteAll()
            pointAnnotationManager.create(PointAnnotationOptions().withPoint(pontosDaRotaDinamica.first()).withIconColor("#2563EB").withIconSize(1.3))
            pointAnnotationManager.create(PointAnnotationOptions().withPoint(pontosDaRotaDinamica.last()).withIconColor("#DC2626").withIconSize(1.3))

            val cameraOptions = mapView.mapboxMap.cameraForCoordinates(pontosDaRotaDinamica, EdgeInsets(120.0, 100.0, 400.0, 100.0), 0.0, 0.0)
            mapView.mapboxMap.setCamera(cameraOptions)
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_STOP -> mapView.onStop()
                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(factory = { mapView }, modifier = Modifier.fillMaxSize())

        if (isLoadingRota) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = Color(0xFF1A237E))
        }

        if (statusDoPedidoLocal == "CANCELADO") {
            AlertDialog(
                onDismissRequest = { },
                confirmButton = {
                    Button(
                        onClick = { navController.navigate("home/prestador") { popUpTo(0) } },
                        colors = ButtonDefaults.buttonColors(containerColor = AppColors.PrimaryRed)
                    ) { Text("Voltar ao Início", color = Color.White) }
                },
                title = { Text("Corrida Cancelada", fontWeight = FontWeight.Bold, color = Color.Red) },
                text = { Text("O cliente cancelou este chamado de socorro mecânico.") }
            )
        }

        IconButton(
            onClick = { cardState = true },
            modifier = Modifier.padding(top = 24.dp, start = 16.dp).align(Alignment.TopStart).background(Color.White, CircleShape).size(45.dp)
        ) {
            Icon(painter = painterResource(R.drawable.baseline_arrow_back_24), contentDescription = "Voltar", tint = AppColors.DarkBlue, modifier = Modifier.size(28.dp))
        }

        CardPedidoEmAndamentoPrestador(
            origem = enderecoOrigemTexto,
            destino = enderecoDestinoTexto,
            modifier = Modifier.align(Alignment.BottomCenter),
            onChatClick = {
                val idPedidoAtual = viewModel.emergenciaState.idPedido
                val nomeParam = Uri.encode(clienteNome)
                navController.navigate("chat_descartavel/$idPedidoAtual/$nomeParam")
            },
            onConcluirClick = {
                viewModel.concluirPedido(pedidoId) {
                    statusDoPedidoLocal = "CONCLUIDO"
                    val nomeParam = Uri.encode(clienteNome)
                    val fotoParam = Uri.encode(clienteFotoUrl)

                    navController.navigate("home/prestador/avaliacao/$pedidoId/$nomeParam/$fotoParam") {
                        popUpTo("home/prestador") { inclusive = false }
                    }
                }
            }
        )
    }

    if (cardState) {
        CardConfirmacao(
            pergunta = "Cancelar Atendimento?",
            onBackClick = { cardState = false },
            onConfirmClick = {
                navController.navigate("home/prestador") {
                    popUpTo("home/prestador") { inclusive = true }
                }
            }
        )
    }
}

@Composable
fun CardPedidoEmAndamentoPrestador(
    origem: String,
    destino: String,
    modifier: Modifier = Modifier,
    onChatClick: () -> Unit,
    onConcluirClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(48.dp))

                Text(
                    text = "Atendimento Ativo",
                    fontWeight = FontWeight.Bold,
                    color = AppColors.DarkBlue,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )

                FilledIconButton(
                    onClick = onChatClick,
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = Color(0xFF0F2042),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.size(44.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_chat_bubble_24),
                        contentDescription = "Chat Rápido",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            AddressTimeline(
                origin = origem,
                destination = destino
            )

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = onConcluirClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .padding(horizontal = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = "Concluir Serviço",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    }
}