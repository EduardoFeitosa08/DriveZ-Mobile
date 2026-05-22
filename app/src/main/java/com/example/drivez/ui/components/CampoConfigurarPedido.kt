package com.example.drivez.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drivez.fontFamily
import com.example.drivez.core.network.theme.AppColors

@Composable
fun CampoConfigurarPedido(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isSingleLine: Boolean = true
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            fontSize = 18.sp,
            fontFamily = fontFamily,
            modifier = Modifier.padding(start = 14.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = label,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                )
                          },
            modifier = modifier.fillMaxWidth(),
            singleLine = isSingleLine,
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = AppColors.BackgroundGray,
                focusedContainerColor = AppColors.BackgroundGray
            )
        )
    }
}