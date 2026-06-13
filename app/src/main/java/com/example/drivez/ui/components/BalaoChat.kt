package com.example.drivez.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.drivez.R
import com.example.drivez.core.network.theme.AppColors
import com.example.drivez.data.model.Mensagem
import com.example.drivez.data.model.RemetenteMensagem
import com.example.drivez.data.model.StatusMensagem
import com.example.drivez.fontFamily
@Composable
fun BalaoChat(mensagem: Mensagem, souOPrestador: Boolean) {
    val eMinhaMensagem = if (souOPrestador) {
        mensagem.remetenteMensagem == RemetenteMensagem.PRESTADOR
    } else {
        mensagem.remetenteMensagem == RemetenteMensagem.CLIENTE
    }

    val alinhamento = if (eMinhaMensagem) Alignment.CenterEnd else Alignment.CenterStart

    // Definição dinâmica das cores dos textos e ícones com base em quem enviou
    val corDoTextoPrincipal = if (eMinhaMensagem) Color.White else Color.Black
    val corDoTextoHorario = if (eMinhaMensagem) Color.White.copy(alpha = 0.7f) else Color.Gray

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 10.dp),
        contentAlignment = alinhamento
    ) {
        Surface(
            modifier = Modifier.widthIn(max = 300.dp),
            tonalElevation = 6.dp,
            shadowElevation = 5.dp,
            shape = RoundedCornerShape(
                topStart = 15.dp,
                topEnd = 15.dp,
                bottomStart = if (eMinhaMensagem) 15.dp else 0.dp,
                bottomEnd = if (eMinhaMensagem) 0.dp else 15.dp
            ),
            color = if (eMinhaMensagem) AppColors.PrimaryRed else Color.White
        ) {
            Column {
                if (mensagem.texto != null) {
                    Text(
                        text = mensagem.texto,
                        fontFamily = fontFamily,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 15.dp, start = 12.dp, end = 12.dp, bottom = 4.dp),
                        color = corDoTextoPrincipal // 🔥 Agora muda de cor dinamicamente
                    )
                }

                if (mensagem.imgUrl != null) {
                    AsyncImage(
                        model = mensagem.imgUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 300.dp)
                            .padding(4.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(start = 12.dp, end = 8.dp, bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = mensagem.horario,
                        fontFamily = fontFamily,
                        fontSize = 12.sp,
                        color = corDoTextoHorario, // 🔥 Corrigido o contraste do horário
                        fontWeight = FontWeight.Bold
                    )
                    Icon(
                        painter = when (mensagem.status) {
                            StatusMensagem.LIDA -> painterResource(R.drawable.outline_done_all_24)
                            StatusMensagem.ENVIADA -> painterResource(R.drawable.baseline_done_24)
                            StatusMensagem.ENTREGUE -> painterResource(R.drawable.outline_done_all_24)
                            else -> painterResource(R.drawable.outline_error_24)
                        },
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = if (mensagem.status == StatusMensagem.LIDA) {
                            if (eMinhaMensagem) Color.Cyan else Color.Blue
                        } else {
                            if (eMinhaMensagem) Color.White.copy(alpha = 0.6f) else Color.Gray
                        }
                    )
                }
            }
        }
    }
}