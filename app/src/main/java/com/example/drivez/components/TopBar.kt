package com.example.drivez.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.drivez.ui.theme.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AplicationTopBar(navController: NavController, titulo: String = "", retornavel: Boolean = true, backgroundColor: Color = Color.White,
                     textColor: Color = AppColors.TitleGray, iconColor: Color = AppColors.DarkBlue
) {
    var contato by remember {
        mutableStateOf("")
    }

    if(titulo === ""){
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
                            tint = iconColor,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = backgroundColor
            )
        )
    }else{
        if(retornavel){
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 16.dp, start = 15.dp, end = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_arrow_back_24),
                                contentDescription = "Voltar",
                                tint = iconColor,
                                modifier = Modifier.size(40.dp)
                            )
                        }

                        Text(
                            text = titulo,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            color = textColor,
                            modifier = Modifier
                                .weight(1f)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor
                )
            )
        }else{
            if(titulo == "Contatos"){
                TopAppBar(
                    title = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp, bottom = 16.dp, start = 15.dp, end = 16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(15.dp)
                            ) {
                                Text(
                                    text = titulo,
                                    fontFamily = fontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 28.sp,
                                    color = textColor,
                                    modifier = Modifier
                                        .weight(1f)
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp, bottom = 16.dp, start = 15.dp, end = 25.dp)
                            ) {
                                OutlinedTextField(
                                    value = contato,
                                    onValueChange = { contato = it },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .border(1.dp, Color.White, RoundedCornerShape(100)),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        unfocusedContainerColor = Color.White,
                                        focusedContainerColor = Color.White,
                                        unfocusedTextColor = AppColors.DarkBlue,
                                        focusedTextColor = AppColors.DarkBlue,
                                        unfocusedPlaceholderColor = AppColors.PlaceholderGray,
                                        unfocusedLeadingIconColor = AppColors.PlaceholderGray,
                                        focusedLeadingIconColor = AppColors.PlaceholderGray
                                    ),
                                    shape = RoundedCornerShape(100),
                                    placeholder = {
                                        Text(
                                            text = "Pesquisar Contato",
                                            fontFamily = fontFamily,
                                            color = AppColors.PlaceholderGray,
                                            fontSize = 22.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    },
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(R.drawable.search),
                                            contentDescription = null,
                                            modifier = Modifier.size(35.dp)
                                        )
                                    }
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = backgroundColor
                    )
                )
            }else{
                TopAppBar(
                    title = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp, bottom = 16.dp, start = 15.dp, end = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(15.dp)
                        ) {
                            Text(
                                text = titulo,
                                fontFamily = fontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 28.sp,
                                color = textColor,
                                modifier = Modifier
                                    .weight(1f)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = backgroundColor
                    )
                )
            }
        }
    }

}