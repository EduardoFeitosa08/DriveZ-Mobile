package com.example.drivez.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.drivez.R
import com.example.drivez.fontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AplicationTopBar(navController: NavController, titulo: String = "") {
    if(titulo == ""){
        TopAppBar(
            title = {},
            actions = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 16.dp, start = 15.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_arrow_back_24),
                            contentDescription = "Voltar",
                            tint = Color(0xFF1B2D45),
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }
        )
    }else{
        TopAppBar(
            title = {
                Row(
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 16.dp, start = 15.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_arrow_back_24),
                            contentDescription = "Voltar",
                            tint = Color(0xFF1B2D45),
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    Text(
                        text = titulo,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        color = Color(0xFF3E3F3F),
                        modifier = Modifier
                            .weight(1f)
                    )
                }
            },
        )
    }

}