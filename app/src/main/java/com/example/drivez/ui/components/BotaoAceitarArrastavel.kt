package com.example.drivez.ui.components

import androidx.compose.animation.core.tween
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

enum class DragState { START, END }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BotaoAceitarArrastavel(
    modifier: Modifier = Modifier,
    onAccept: () -> Unit
) {
    val density = LocalDensity.current
    val botaoTamanhoPx = with(density) { 60.dp.toPx() }
    val decaySpec = rememberSplineBasedDecay<Float>()

    // 1. CORREÇÃO DO CONSTRUTOR: Na API nova, o "animationSpec" e os "thresholds"
    // são passados de forma diferente ou possuem parâmetros nomeados específicos.
    val state = remember(decaySpec) {
        AnchoredDraggableState(
            initialValue = DragState.START,
            positionalThreshold = { distance: Float -> distance * 0.7f },
            velocityThreshold = { with(density) { 100.dp.toPx() } },
            snapAnimationSpec = tween(durationMillis = 300),
            decayAnimationSpec = decaySpec
        )
    }

    LaunchedEffect(state.currentValue) {
        if (state.currentValue == DragState.END) {
            onAccept()
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(84.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(Color(0xFFC2F4A1), Color(0xFF56D352))
                )
            )
            .onSizeChanged { layoutSize ->
                val larguraTotal = layoutSize.width.toFloat()
                val fimDaAncora = larguraTotal - botaoTamanhoPx

                val novasAncoras = DraggableAnchors {
                    DragState.START at 0f
                    DragState.END at fimDaAncora
                }
                state.updateAnchors(novasAncoras)
            },
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = "Arraste para Aceitar",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )

        Box(
            modifier = Modifier
                .offset {
                    val offset = try { state.requireOffset().roundToInt() } catch (_: Exception) { 0 }
                    IntOffset(x = offset, y = 0)
                }
                .anchoredDraggable(state = state, orientation = Orientation.Horizontal)
                .size(80.dp)
                .padding(2.dp)
                .clip(CircleShape)
                .background(Color(0xFFFDECEE)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Aceitar",
                color = Color(0xFF1A237E),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}