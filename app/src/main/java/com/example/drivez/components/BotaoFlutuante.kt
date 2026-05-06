package com.example.drivez.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.drivez.R
import kotlin.math.roundToInt

@Composable
fun BotaoFlutuante() {
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(500f) } // Posição inicial vertical

    val configuration = LocalConfiguration.current
    val screenWidthPx = with(LocalDensity.current) { configuration.screenWidthDp.dp.toPx() }
    val fabSizePx = with(LocalDensity.current) { 56.dp.toPx() } // Tamanho padrão do FAB

    val animatedOffsetX by animateFloatAsState(
        targetValue = offsetX,
        animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy),
        label = "snapAnimation"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            onClick = {},
            modifier = Modifier
                .offset { IntOffset(animatedOffsetX.roundToInt(), offsetY.roundToInt()) }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragEnd = {
                            val center = screenWidthPx / 2
                            offsetX = if (offsetX + fabSizePx / 2 < center) {
                                0f
                            } else {
                                screenWidthPx - fabSizePx
                            }
                        },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            offsetX += dragAmount.x
                            offsetY += dragAmount.y
                        }
                    )
                },
            containerColor = Color.Red,
            contentColor = Color.White,
            shape = CircleShape
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_add_24),
                contentDescription = "Adicionar"
            )
        }
    }
}