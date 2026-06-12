package com.example.drivez.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.request.ImageRequest
import coil3.compose.AsyncImage
import coil3.request.crossfade
import com.example.drivez.R
import com.example.drivez.core.network.theme.AppColors
import com.example.drivez.data.dto.ClientePedidoDto

@Composable
fun CardPedido(
    pedidoDto: ClientePedidoDto,
    navController: NavController
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {
                navController.navigate("home/prestador/detalhes_solicitacao/emergencia/${pedidoDto.pedidoId}")
            },
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFEEEEEE)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.CardBackground)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color(0xFFE3F2FD),
                    modifier = Modifier.size(60.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(pedidoDto.fotoUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Foto de ${pedidoDto.nomeCliente}",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        error = painterResource(R.drawable.baseline_person_24),
                        placeholder = painterResource(R.drawable.baseline_person_24)
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                val notaApi = if (pedidoDto.mediaNota > 0.0) pedidoDto.mediaNota else 5.0

                Avaliacao(
                    avaliacaoOriginal = notaApi,
                    tamanho = 14.dp,
                    espacamento = 2.dp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = pedidoDto.nomeCliente,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Surface(
                    color = AppColors.SecondaryRed,
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.padding(top = 4.dp, end = 20.dp).fillMaxWidth()
                ) {
                    Text(
                        text = pedidoDto.descricaoItem,
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                }
            }
        }
    }
}