package com.nfragiskatos.recipe_mvvm_compose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerRecipeCardItem(
    cardHeight: Dp,
    padding: Dp,
    brush: Brush
) {
    Column(modifier = Modifier.padding(padding)) {
        Surface(shape = MaterialTheme.shapes.small) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(cardHeight)
                    .background(brush = brush)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Surface(
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(cardHeight / 10)
                    .background(brush = brush)
            )
        }
    }

}