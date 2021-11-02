package com.nfragiskatos.recipe_mvvm_compose.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Cyan800,
    primaryVariant = Color.White,
    onPrimary = Color.White,
    secondary = Black1,
    onSecondary = Color.White,
    error = RedErrorLight,
    background = Color.Black,
    onBackground = Color.White,
    surface = Black1,
    onSurface = Color.White,
    )

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = Cyan600,
    primaryVariant = Cyan300,
    onPrimary = Black2,
    secondary = Color.White,
    secondaryVariant = Red300,
    onSecondary = Color.Black,
    background = Grey1,
    surface = Color.White,
    onSurface = Black2,
    error = RedErrorDark,
    onError = RedErrorLight,
    onBackground = Color.Black,
)

@Composable
fun Recipe_mvvm_composeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}