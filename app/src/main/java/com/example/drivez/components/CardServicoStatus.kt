package com.example.drivez.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.drivez.R
import com.example.drivez.data.model.Prestador
import com.example.drivez.ui.theme.AppColors

@Composable
fun CardServicoStatus(modifier: Modifier = Modifier, prestador: Prestador,
                      cancelarOnClick: () -> Unit) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Avaliacao(avaliacao = prestador.avaliacao, tamanho = 24.dp, espacamento = 4.dp)

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                modifier = Modifier.size(80.dp),
                shape = CircleShape,
                border = BorderStroke(1.dp, AppColors.DarkBlue)
            ) {
                AsyncImage(
                    model = "${prestador.perfilImgUrl}",
                    placeholder = painterResource(R.drawable.baseline_person_24),
                    error = painterResource(R.drawable.baseline_person_24),
                    contentDescription = null,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))


            Surface(
                color = Color(0xFFA54D4D),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "423m de distancia",
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            AddressTimeline(
                origin = "R. João Blesa - 45. Alphaville",
                destination = "R. João Blesa - 45. Alphaville"
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(26.dp)
            ) {
                Button(
                    onClick = cancelarOnClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(55.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AppColors.PrimaryRed),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text("Cancelar", color = Color.White)
                }

                IconButton(
                    onClick = { /* Chat */ },
                    modifier = Modifier
                        .weight(1f)
                        .height(55.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = AppColors.ConfirmGreen
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_chat_bubble_24),
                        contentDescription = "Chat",
                        modifier = Modifier.size(48.dp),
                        tint = Color.White)
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
                fontSize = 14.sp,
                color = Color.DarkGray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(32.dp)) // Espaço para alinhar com a linha

            Text(
                text = destination,
                fontSize = 14.sp,
                color = Color.DarkGray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}