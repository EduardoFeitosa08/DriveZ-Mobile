package com.example.drivez.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drivez.fontFamily

@Composable
fun TituloCampo(campoNome: String) {
    Text(
        text = campoNome,
        fontFamily = fontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF1B2D45),
        modifier = Modifier
            .padding(start = 15.dp)
    )
}