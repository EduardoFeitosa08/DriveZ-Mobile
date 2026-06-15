package com.example.drivez.ui.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.drivez.R
import com.example.drivez.core.network.theme.AppColors
import com.example.drivez.data.model.Prestador
import com.example.drivez.fontFamily

@Composable
fun CardAvaliacao(
    navController: NavController,
    clienteNome: String,
    clienteFotoUrl: String,
    modifier: Modifier = Modifier,
    onAvaliacaoEnviada: (Int, String) -> Unit
) {
    var rating by remember { mutableIntStateOf(0) }
    var comentario by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(Color.White)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "O atendimento foi finalizado,\navalie o cliente!",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontFamily = fontFamily,
                modifier = Modifier.padding(bottom = 12.dp),
                fontSize = 18.sp
            )

            Text(
                text = clienteNome,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                fontFamily = fontFamily,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 16.dp),
                fontSize = 16.sp
            )

            Surface(
                modifier = Modifier.size(80.dp),
                shape = CircleShape,
                border = BorderStroke(1.dp, AppColors.DarkBlue)
            ) {
                val fotoValida = if (clienteFotoUrl != "null" && clienteFotoUrl.startsWith("http")) clienteFotoUrl else null
                AsyncImage(
                    model = fotoValida,
                    contentDescription = "Foto do cliente solicitado",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.baseline_person_24),
                    fallback = painterResource(id = R.drawable.baseline_person_24),
                    error = painterResource(id = R.drawable.baseline_person_24)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(5) { index ->
                    val isSelected = index < rating
                    Icon(
                        painter = painterResource(R.drawable.baseline_star_24),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clickable { rating = index + 1 },
                        tint = if (isSelected) Color(0xFFFFC107) else Color.LightGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Fazer comentário sobre o cliente (Opcional)",
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                fontFamily = fontFamily,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = comentario,
                onValueChange = { comentario = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color(0xFFE57373),
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { onAvaliacaoEnviada(rating, comentario) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336)),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(text = "Enviar", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}