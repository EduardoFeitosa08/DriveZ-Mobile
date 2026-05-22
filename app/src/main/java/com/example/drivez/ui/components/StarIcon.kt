package com.example.drivez.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.example.drivez.R

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