package com.nfragiskatos.recipe_mvvm_compose.ui.components

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nfragiskatos.recipe_mvvm_compose.R
import com.nfragiskatos.recipe_mvvm_compose.domain.util.loadPictures
import com.nfragiskatos.recipe_mvvm_compose.ui.components.HeartAnimationDefinition.HeartButtonState.ACTIVE
import com.nfragiskatos.recipe_mvvm_compose.ui.components.HeartAnimationDefinition.HeartButtonState.IDLE
import com.nfragiskatos.recipe_mvvm_compose.ui.components.HeartAnimationDefinition.expandedIconSize
import com.nfragiskatos.recipe_mvvm_compose.ui.components.HeartAnimationDefinition.idleIconSize

object HeartAnimationDefinition {
    enum class HeartButtonState {
        IDLE, ACTIVE
    }

    val idleIconSize = 50.dp
    val expandedIconSize = 80.dp
}


@Composable
fun AnimatedHeartButton(
    modifier: Modifier,
    buttonState: MutableState<HeartAnimationDefinition.HeartButtonState>,
    onToggle: () -> Unit
) {
    val toState =
        if (buttonState.value == IDLE) ACTIVE else IDLE

//    targetValue = if (buttonState.value == ACTIVE) 50.1.dp else 50.dp,
    val size by animateDpAsState(
        targetValue = if (buttonState.value == ACTIVE) 50.1.dp else idleIconSize,
        animationSpec = keyframes {
            durationMillis = 500
            expandedIconSize at 100
            idleIconSize at 200
        },
        finishedListener = {
            Log.i(
                "MY_TAG",
                "ANIMATION VALUE = ${it.toString()}"
            )
        }
    )

    HeartButton(
        modifier = modifier,
        buttonState = buttonState,
        size = size,
        onToggle = onToggle,
    )

}

@Composable
private fun HeartButton(
    modifier: Modifier,
    buttonState: MutableState<HeartAnimationDefinition.HeartButtonState>,
    size: Dp,
    onToggle: () -> Unit,
) {


//    var currentState by remember {
//        mutableStateOf(state)
//    }
//
//    val transition = updateTransition(
//        currentState,
//        label = "hearStateTransition"
//    )
//
//
//    val heartColor: Color by transition.animateColor(label = "heartColor",
//        transitionSpec = {
//            tween(500)
//
//        }
//    ) { state ->
//        when (state) {
//            IDLE -> Color.LightGray
//            ACTIVE -> Color.Red
//        }
//    }
//
//    val heartSize by transition.animateDp(label = "heartSize",
//        transitionSpec = {
//            keyframes {
//                durationMillis = 500
//                expandedIconSize at 100
//                idleIconSize at 200
//            }
//        }
//    ) { state ->
//        when (state) {
//            IDLE -> idleIconSize
//            ACTIVE -> expandedIconSize
//        }
//    }

    if (buttonState.value == ACTIVE) {
        loadPictures(drawable = R.drawable.heart_red).value?.let { image ->
            Image(
                bitmap = image.asImageBitmap(),
                contentDescription = "red heart",
                modifier = modifier
                    .clickable(onClick = onToggle)
                    .size(size)
            )
        }
    } else {
        loadPictures(drawable = R.drawable.heart_grey).value?.let { image ->
            Image(
                bitmap = image.asImageBitmap(),
                contentDescription = "red heart",
                modifier = modifier
                    .clickable(onClick = onToggle)
                    .size(size)
            )
        }
    }


}