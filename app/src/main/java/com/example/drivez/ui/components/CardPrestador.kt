package com.example.drivez.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.drivez.R
import com.example.drivez.core.network.theme.AppColors
import com.example.drivez.data.model.Prestador
import com.example.drivez.fontFamily

@Composable
fun CardPrestador(prestador: Prestador, modifier: Modifier = Modifier, navController: NavController) {


    Card(
        modifier = modifier
            .border(1.dp, AppColors.DarkBlue, RoundedCornerShape(15.dp))
            .clickable {
                navController.navigate("home/cliente/servico/${prestador.id}")
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
        ),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.CardBackground
        ),
        shape = RoundedCornerShape(15.dp)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 15.dp, horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            //Depois substituir por um asyc image
//            Icon(
//                painter = painterResource(R.drawable.baseline_person_24),
//                contentDescription = "Prestador",
//                tint = AppColors.DarkBlue,
//                modifier = Modifier
//                    .size(70.dp)
//                    .border(1.dp, AppColors.DarkBlue, RoundedCornerShape(100))
//            )
            AsyncImage(
                model = "${prestador.perfilImgUrl}",
                placeholder = painterResource(R.drawable.baseline_person_24),
                error = painterResource(R.drawable.baseline_person_24),
                contentDescription = null,
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(100))
                    .border(1.dp, AppColors.DarkBlue, RoundedCornerShape(100)),
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = prestador.nome,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = AppColors.SecondaryRed
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Avaliacao(prestador.avaliacao, 24.dp, 2.dp)

                    Text(
                        text = "(${prestador.totalAvaliacoes})",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    prestador.categorias.forEach { item ->
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .border(1.dp, AppColors.BorderGray, RoundedCornerShape(10.dp))
                        ) {
                            Text(
                                text = item.nome,
                                fontFamily = fontFamily,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black,
                                modifier = Modifier
                                    .padding(vertical = 5.dp, horizontal = 15.dp)
                            )
                        }
                    }
                }

            }
        }
    }
}