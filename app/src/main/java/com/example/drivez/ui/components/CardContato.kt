package com.example.drivez.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.drivez.R
import com.example.drivez.data.model.Contato
import com.example.drivez.fontFamily
import com.example.drivez.core.network.theme.AppColors
import coil3.compose.rememberAsyncImagePainter
import androidx.compose.foundation.Image
import androidx.compose.ui.platform.LocalContext
import coil3.request.ImageRequest
import coil3.request.crossfade

@Composable
fun PrestadorCardContato(contato: Contato, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .border(1.dp, AppColors.DarkBlue, RoundedCornerShape(15.dp))
            .clickable{
                navController.navigate(route = "home/cliente/contatos/conversa/${contato.id}")
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.CardBackground
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            AsyncImage(
                model = "${contato.perfilImgUrl}",
                placeholder = painterResource(R.drawable.baseline_person_24),
                error = painterResource(R.drawable.baseline_person_24),
                contentDescription = null,
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(100))
                    .border(1.dp, AppColors.DarkBlue, RoundedCornerShape(100)),
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth(1f),
            ) {
                Text(
                    text = contato.name,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.SecondaryRed,
                    fontSize = 22.sp
                )
                Text(
                    text = contato.ultimaMensagem,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.DarkBlue,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun ClienteCardContato(contato: Contato, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .border(1.dp, AppColors.DarkBlue, RoundedCornerShape(15.dp))
            .clickable {
                navController.navigate(route = "home/prestador/contatos/conversa/${contato.id}")
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.CardBackground)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {

            val imagemModelo = if (!contato.perfilImgUrl.isNullOrBlank() &&
                contato.perfilImgUrl != "null" &&
                contato.perfilImgUrl.trim().startsWith("http")) {
                contato.perfilImgUrl.trim()
            } else {
                null
            }

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imagemModelo)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.baseline_person_24),
                error = painterResource(R.drawable.baseline_person_24),
                contentDescription = "Foto de perfil de ${contato.name}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(100))
                    .border(1.dp, AppColors.DarkBlue, RoundedCornerShape(100))
            )

            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = contato.name,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.SecondaryRed,
                    fontSize = 22.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}