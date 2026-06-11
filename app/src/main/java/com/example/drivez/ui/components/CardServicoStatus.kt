package com.example.drivez.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.drivez.R
import com.example.drivez.fontFamily
import com.example.drivez.core.network.theme.AppColors

@Composable
fun CardServicoStatus(
    modifier: Modifier = Modifier,
    nome: String,
    id: String,
    isCliente: Boolean = true,
    avaliacao: Double,
    distancia: String,
    origem: String,
    destino: String,
    cancelarOnClick: () -> Unit,
    chatRapidoClick: () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Nome no topo
            Text(
                text = nome,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.DarkBlue,
                fontFamily = fontFamily
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Avaliação
            Avaliacao(avaliacao = avaliacao, tamanho = 24.dp, espacamento = 4.dp)

            Spacer(modifier = Modifier.height(16.dp))

            // Foto embaixo
            Surface(
                modifier = Modifier.size(80.dp),
                shape = CircleShape,
                border = BorderStroke(1.dp, AppColors.DarkBlue)
            ) {
                val imageUrl = if (isCliente) {
                    "https://backend-drivez-atgfavb2cuccgrah.eastus2-01.azurewebsites.net/v1/drivez/clientes/foto/$id"
                } else {
                    id // Caso seja prestador, 'id' pode ser a URL direta ou tratada em outro lugar
                }

                AsyncImage(
                    model = imageUrl,
                    placeholder = painterResource(R.drawable.baseline_person_24),
                    error = painterResource(R.drawable.baseline_person_24),
                    contentDescription = "Foto de Perfil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Distância
            Surface(
                color = Color(0xFFA54D4D),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = distancia,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Endereços
            AddressTimeline(
                origin = origem,
                destination = destino
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botões de Ação
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = cancelarOnClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(55.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AppColors.PrimaryRed),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text(
                        text = "Cancelar",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = fontFamily
                    )
                }

                IconButton(
                    onClick = chatRapidoClick,
                    modifier = Modifier
                        .weight(0.4f)
                        .height(55.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = AppColors.ConfirmGreen
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_chat_bubble_24),
                        contentDescription = "Chat",
                        modifier = Modifier.size(32.dp),
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun AddressTimeline(
    origin: String,
    destination: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 4.dp, end = 12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .border(2.dp, Color.Black)
                    .background(Color.White)
            )

            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(45.dp)
                    .background(Color.Black)
            )

            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(Color.Black, CircleShape)
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = origin,
                fontSize = 16.sp,
                color = Color.DarkGray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = destination,
                fontSize = 16.sp,
                color = Color.DarkGray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
