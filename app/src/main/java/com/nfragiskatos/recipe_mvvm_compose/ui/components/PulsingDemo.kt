package com.nfragiskatos.recipe_mvvm_compose.ui.components

import android.util.Log
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

//object PulseAnimationsDefinitions {
//    enum class PulseState {
//        INITIAL, FINAL
//    }
//
//    val infiniteTransition = rememberInfiniteTransition()
//}



@Composable
fun PulseDemo() {

    val resource: Painter
    val infiniteTransition = rememberInfiniteTransition()
    val state = infiniteTransition.animateFloat(
        initialValue = 10f,
        targetValue = 20f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing,
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

//    Log.i("MY_TAG", "state value: ${state.value}")


    val color = MaterialTheme.colors.primary

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .height(state.value.dp)
                .width(state.value.dp),
            imageVector = Icons.Default.Favorite,
            contentDescription = ""
        )
    }

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
    ) {
        drawCircle(
            radius = state.value,
            brush = SolidColor(color)
        )
    }
}