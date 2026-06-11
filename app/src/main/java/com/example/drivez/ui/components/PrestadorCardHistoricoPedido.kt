package com.example.drivez.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.drivez.R
import com.example.drivez.core.network.theme.fontFamily
import com.example.drivez.core.network.theme.AppColors
import com.example.drivez.data.model.Pedido
import com.example.drivez.util.FormatarData

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PrestadorCardHistoricoPedido(pedido: Pedido, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Data do pedido no topo à direita
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = FormatarData(pedido.dataSolicitacao),
                    fontFamily = fontFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Seção de Locais
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF8F9FA), RoundedCornerShape(12.dp))
                    .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                // Origem
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(AppColors.DarkBlue)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = pedido.enderecoOrigem,
                        fontFamily = fontFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.DarkGray
                    )
                }

                // Linha conectora
                Box(
                    modifier = Modifier
                        .padding(start = 3.dp)
                        .padding(vertical = 4.dp)
                        .width(2.dp)
                        .height(16.dp)
                        .background(Color.LightGray)
                )

                // Destino
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(Color.Red)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = pedido.enderecoDestino,
                        fontFamily = fontFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.DarkGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // FOTO E NOME DO CLIENTE (DEBAIXO DOS LOCAIS)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier.size(50.dp),
                    shape = CircleShape,
                    border = androidx.compose.foundation.BorderStroke(1.dp, AppColors.DarkBlue)
                ) {
                    AsyncImage(
                        model = "https://backend-drivez-atgfavb2cuccgrah.eastus2-01.azurewebsites.net/v1/drivez/clientes/foto/${pedido.clienteId}",
                        placeholder = painterResource(R.drawable.baseline_person_24),
                        error = painterResource(R.drawable.baseline_person_24),
                        contentDescription = "Foto do Cliente",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = pedido.clienteNome ?: "Cliente DriveZ",
                        fontFamily = fontFamily,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.DarkBlue
                    )
                    Text(
                        text = "Solicitante",
                        fontFamily = fontFamily,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}
