package com.example.drivez.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.drivez.R

//@Composable
//fun Avaliacao(avaliacao: Double, tamanho: Dp, espacamento: Dp, modifier: Modifier = Modifier) {
//    Row(
//        modifier = modifier,
//        horizontalArrangement = Arrangement.spacedBy(espacamento)
//    ) {
//        repeat(5){ index ->
//            StarIcon(tamanho = tamanho)
//        }
//    }
//}
//
//
//@Composable
//fun StarIcon(tamanho: Dp) {
//    Box(
//        contentAlignment = Alignment.CenterStart
//    ) {
//        Icon(
//            painter = painterResource(R.drawable.baseline_star_24),
//            contentDescription = null,
//            tint = Color(0xFFAEACAC),
//            modifier = Modifier.size(tamanho)
//        )
//
//        Box(
//            modifier = Modifier
//                .size(tamanho)
//                .clip(RectangleShape)
//        ){
//            Icon(
//                painter = painterResource(R.drawable.baseline_star_24),
//                contentDescription = null,
//                tint = Color(0xFFFFC300),
//                modifier = Modifier.size(tamanho)
//            )
//        }
//    }
//}

@Composable
fun Avaliacao(avaliacao: Double, tamanho: Dp, espacamento: Dp, modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(espacamento)
    ) {
        repeat(5) { index ->
            val isFilled = (index + 1) <= avaliacao
            StarIcon(tamanho = tamanho, isFilled = isFilled)
        }
    }
}

@Composable
fun StarIcon(tamanho: Dp, isFilled: Boolean) {
    Icon(
        painter = painterResource(R.drawable.baseline_star_24),
        contentDescription = "Estrela",
        tint = if (isFilled) Color(0xFFFFC300) else Color(0xFFAEACAC),
        modifier = Modifier.size(tamanho)
    )
}