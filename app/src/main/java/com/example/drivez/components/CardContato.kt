package com.example.drivez.components

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
import androidx.compose.material3.Icon
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
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.drivez.R
import com.example.drivez.data.model.Contato
import com.example.drivez.fontFamily
import com.example.drivez.ui.theme.AppColors

@Composable
fun CardContato(contato: Contato, navController: NavController) {
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
//            Icon(
//                painter = painterResource(R.drawable.baseline_person_24),
//                contentDescription = null,
//                modifier = Modifier
//                    .size(70.dp)
//                    .border(1.dp, AppColors.DarkBlue, RoundedCornerShape(100)),
//            )

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