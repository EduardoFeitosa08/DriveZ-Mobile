package com.example.drivez.ui.pedido_prestador

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
import com.example.drivez.core.network.theme.AppColors
import com.example.drivez.ui.components.AddressTimeline
import com.example.drivez.ui.components.CardConfirmacao
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
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

@Composable
fun PrestadorServiceStatusScreen(
    navController: NavController,
    userId: String,
    isSOS: Boolean,
    // AGORA RECEBE TEXTO TEXTO PURO (STRINGS) DA VEZ DA AZURE
    enderecoOrigemTexto: String,
    enderecoDestinoTexto: String
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var cardState by remember { mutableStateOf(false) }

    // Estados para controlar o fluxo de tradução Geocoding -> Directions -> Desenho
    var pontosDaRotaDinamica by remember { mutableStateOf<List<Point>>(emptyList()) }
    var isMapStyleLoaded by remember { mutableStateOf(false) }
    var isLoadingRota by remember { mutableStateOf(true) }

    val mapView = remember { MapView(context) }
    val token = remember { BuildConfig.MAPBOX_TOKEN }

    // FUNÇÃO AUXILIAR DE GEOCODING (Igual ao forwardGeocode do seu JS)
    suspend fun obterCoordenadasDoEndereco(address: String): Point? {
        return withContext(Dispatchers.IO) {
            try {
                val urlEncoded = URLEncoder.encode(address, "UTF-8")
                val urlStr = "https://api.mapbox.com/geocoding/v5/mapbox.places/$urlEncoded.json" +
                        "?access_token=$token&country=br&limit=1"

                val connection = URL(urlStr).openConnection() as HttpURLConnection
                val responseText = connection.inputStream.bufferedReader().use { it.readText() }

                val jsonObject = JSONObject(responseText)
                val features = jsonObject.getJSONArray("features")

                if (features.length() > 0) {
                    val firstResult = features.getJSONObject(0)
                    val centerArray = firstResult.getJSONArray("center")
                    val lng = centerArray.getDouble(0)
                    val lat = centerArray.getDouble(1)
                    Point.fromLngLat(lng, lat)
                } else null
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    // FLUXO PRINCIPAL ASSÍNCRONO: Executa o Geocoding duplo e depois busca o Trajeto de Ruas
    LaunchedEffect(enderecoOrigemTexto, enderecoDestinoTexto) {
        isLoadingRota = true

        // 1. Converte Texto de Origem em Coordenadas Matemáticas
        val pontoOrigem = obterCoordenadasDoEndereco(enderecoOrigemTexto)
        // 2. Converte Texto de Destino em Coordenadas Matemáticas
        val pontoDestino = obterCoordenadasDoEndereco(enderecoDestinoTexto)

        if (pontoOrigem != null && pontoDestino != null) {
            // 3. Com os pontos criados com sucesso, busca as ruas da API de Directions
            withContext(Dispatchers.IO) {
                try {
                    val urlStr = "https://api.mapbox.com/directions/v5/mapbox/driving/" +
                            "${pontoOrigem.longitude()},${pontoOrigem.latitude()};" +
                            "${pontoDestino.longitude()},${pontoDestino.latitude()}" +
                            "?geometries=geojson&overview=full&access_token=$token"

                    val connection = URL(urlStr).openConnection() as HttpURLConnection
                    val responseText = connection.inputStream.bufferedReader().use { it.readText() }

                    val jsonObject = JSONObject(responseText)
                    val routes = jsonObject.getJSONArray("routes")

                    if (routes.length() > 0) {
                        val coordinates = routes.getJSONObject(0).getJSONObject("geometry").getJSONArray("coordinates")
                        val listaCoordenadasConstruida = mutableListOf<Point>()

                        for (i in 0 until coordinates.length()) {
                            val coordArray = coordinates.getJSONArray(i)
                            listaCoordenadasConstruida.add(Point.fromLngLat(coordArray.getDouble(0), coordArray.getDouble(1)))
                        }

                        withContext(Dispatchers.Main) {
                            pontosDaRotaDinamica = listaCoordenadasConstruida
                            isLoadingRota = false
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    isLoadingRota = false
                }
            }
        } else {
            isLoadingRota = false
        }
    }

    LaunchedEffect(mapView) {
        mapView.mapboxMap.loadStyle(Style.MAPBOX_STREETS) { isMapStyleLoaded = true }
    }

    // Monitora e desenha a rota na tela assim que os cálculos de Geocoding e Trajeto terminam
    LaunchedEffect(pontosDaRotaDinamica, isMapStyleLoaded) {
        if (pontosDaRotaDinamica.isNotEmpty() && isMapStyleLoaded) {
            val annotationApi = mapView.annotations

            // Desenha a Rota Premium (Casing + Linha principal)
            val polylineAnnotationManager = annotationApi.createPolylineAnnotationManager()
            polylineAnnotationManager.deleteAll()

            polylineAnnotationManager.create(
                PolylineAnnotationOptions()
                    .withPoints(pontosDaRotaDinamica)
                    .withLineColor("#FFFFFF").withLineWidth(9.0).withLineOpacity(0.7)
            )
            polylineAnnotationManager.create(
                PolylineAnnotationOptions()
                    .withPoints(pontosDaRotaDinamica)
                    .withLineColor("#DC2626").withLineWidth(5.0).withLineOpacity(1.0)
            )

            // Desenha os Pins nos extremos calculados
            val pointAnnotationManager = annotationApi.createPointAnnotationManager()
            pointAnnotationManager.deleteAll()

            pointAnnotationManager.create(
                PointAnnotationOptions().withPoint(pontosDaRotaDinamica.first()).withIconColor("#2563EB").withIconSize(1.3)
            )
            pointAnnotationManager.create(
                PointAnnotationOptions().withPoint(pontosDaRotaDinamica.last()).withIconColor("#DC2626").withIconSize(1.3)
            )

            // Centraliza o enquadramento do mapa
            val cameraOptions = mapView.mapboxMap.cameraForCoordinates(
                coordinates = pontosDaRotaDinamica,
                coordinatesPadding = EdgeInsets(120.0, 100.0, 400.0, 100.0),
                bearing = 0.0, pitch = 0.0
            )
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

        // Exibe um indicador discreto enquanto faz a geocodificação em segundo plano
        if (isLoadingRota) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color(0xFF1A237E)
            )
        }

        IconButton(
            onClick = { cardState = true },
            modifier = Modifier
                .padding(top = 24.dp, start = 16.dp)
                .align(Alignment.TopStart)
                .background(Color.White, CircleShape)
                .size(45.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_arrow_back_24),
                contentDescription = "Voltar",
                tint = AppColors.DarkBlue,
                modifier = Modifier.size(28.dp)
            )
        }

        CardPedidoEmAndamentoPrestador(
            origem = enderecoOrigemTexto,
            destino = enderecoDestinoTexto,
            modifier = Modifier.align(Alignment.BottomCenter),
            onConcluirClick = {
                navController.navigate("home/prestador/avaliacao")
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
            Text(
                text = "Atendimento em Andamento",
                fontWeight = FontWeight.Bold,
                color = AppColors.DarkBlue,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Exibe os endereços do chamado mapeado na rota
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
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)), // Verde sucesso
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