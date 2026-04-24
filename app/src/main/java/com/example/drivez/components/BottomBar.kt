package com.example.drivez.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.drivez.R
import com.example.drivez.fontFamily
import com.example.drivez.data.model.BottomNavItem

@Composable
fun BottomClienteBar(navController: NavController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val rotaAtual = navBackStackEntry?.destination?.route

    val items = listOf(
        BottomNavItem("home/cliente", R.drawable.baseline_home_24, "Home"),
        BottomNavItem("contatos", R.drawable.baseline_chat_bubble_24, "Chat"),
        BottomNavItem("home/cliente/historico", R.drawable.baseline_history_24, "Histórico"),
        BottomNavItem("perfil", R.drawable.baseline_person_24, "Perfil")
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        contentAlignment = Alignment.BottomCenter
    ){
        NavigationBar(
            containerColor = Color.White,
            tonalElevation = 0.dp,
            modifier = Modifier
                .fillMaxWidth()
                .height(165.dp)
                .drawBehind {
                    val shadowColor = Color.Black.copy(alpha = 0.3f)
                    val shadowHeight = 8.dp.toPx()

                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(shadowColor, Color.Transparent),
                            startY = -shadowHeight,
                            endY = 0f
                        ),
                        topLeft = Offset(0f, -shadowHeight),
                        size = Size(size.width, shadowHeight)
                    )
                }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEachIndexed { index, item ->
                    if(index == 2){
                        Spacer(modifier = Modifier.width(60.dp))
                    }

                    NavigationBarItem(
                        selected = rotaAtual == item.route,
                        onClick = {
                            navController.navigate("${item.route}")
                        },
                        icon = {
                            Icon(
                                painter = painterResource(item.icon),
                                contentDescription = item.label,
                                modifier = Modifier.size(30.dp)
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFFE53935),
                            unselectedIconColor = Color.Gray,
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
        FloatingActionButton(
            onClick = {
                //Depois adicionar a troca de pagina para o SOS
            },
            containerColor = Color(0xFFE53935),
            contentColor = Color.White,
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 8.dp),
            modifier = Modifier
                .padding(bottom = 20.dp)
                .size(80.dp)
                .offset(y = (-90).dp)
        ) {
            Text(
                text = "SOS",
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}