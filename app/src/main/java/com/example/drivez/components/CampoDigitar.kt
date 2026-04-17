package com.example.drivez.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drivez.R
import com.example.drivez.fontFamily

@Composable
fun CampoDigitar(campoNome: String, placeholder: String = "", valorPadrao: String = "",
                 painter: Painter? = null,
                 painterTransform: Painter? = null,
                 alteravel: Boolean = true) {
    var campoState by remember { mutableStateOf(valorPadrao) }

    var iconState by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = campoState,
        onValueChange = {campoState = if(alteravel) it else valorPadrao},
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .border(1.dp, Color(0XFF6D6D6D), RoundedCornerShape(15.dp)),
        placeholder = {
            Text(
                text = if(placeholder == "") campoNome else placeholder,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0XFF6D6D6D),
                fontFamily = fontFamily
            )
        },
        shape = RoundedCornerShape(15.dp),
        trailingIcon = if (painter != null && painterTransform != null){
            {
                IconButton(
                    onClick = {
                        if(iconState){
                            iconState = false
                        }else{
                            iconState = true
                        }
                    }
                ) {
                    Icon(
                        painter = if(iconState) painterTransform else painter,
                        contentDescription = "",
                        modifier = Modifier
                            .size(15.dp),
                        tint = Color(0XFF6D6D6D)
                    )
                }
            }
        }else null,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0XFFD53035),
            unfocusedBorderColor = Color(0XFF6D6D6D),
            focusedLabelColor = Color(0XFF6D6D6D),
            unfocusedLabelColor = Color(0XFF6D6D6D),
            focusedPlaceholderColor = Color(0XFFD53035),
            unfocusedPlaceholderColor = Color(0XFF6D6D6D),
            disabledContainerColor = Color.White,
            focusedTextColor = Color(0xFF1B2D45),
            unfocusedTextColor = Color(0xFF1B2D45),
            unfocusedContainerColor = if(campoState != "") Color(0xFFE6EEF8) else Color.White,
            focusedContainerColor = Color.White
        ),

    )
}