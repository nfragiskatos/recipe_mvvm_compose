package com.nfragiskatos.recipe_mvvm_compose.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun LoadingRecipeListShimmer(
    cardHeight: Dp,
    padding: Dp = 16.dp
) {
    val infiniteTransitionX = rememberInfiniteTransition()
    val infiniteTransitionY = rememberInfiniteTransition()

    BoxWithConstraints {

        val cardWidthPx = with(LocalDensity.current) {
            (maxWidth - (padding * 2)).toPx()
        }

        val cardHeightPx = with(LocalDensity.current) {
            (cardHeight - padding).toPx()
        }

        var gradientWidth: Float = (0.5f * cardHeightPx)

        val xPos by infiniteTransitionX.animateFloat(
            initialValue = 0f,
            targetValue = cardWidthPx + gradientWidth,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1000,
                    delayMillis = 100,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            )
        )
        val yPos by infiniteTransitionY.animateFloat(
            initialValue = 0f,
            targetValue = cardHeightPx + gradientWidth,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1000,
                    delayMillis = 100,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            )
        )

        val colors = listOf(
            Color.LightGray.copy(alpha = 0.9f),
            Color.LightGray.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.9f),
        )

        val brush = Brush.linearGradient(
            colors,
            start = Offset(
                xPos - gradientWidth,
                yPos - gradientWidth
            ),
            end = Offset(
                xPos,
                yPos
            )
        )

        val scrollState = remember {
            ScrollState(0)
        }

        Column(
            modifier = Modifier.verticalScroll(
                scrollState,
                enabled = true
            )
        ) {
            repeat(5) {
                ShimmerRecipeCardItem(
                    cardHeight = cardHeight,
                    padding = padding,
                    brush = brush
                )
            }
        }


    }
}