package com.nfragiskatos.recipe_mvvm_compose.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FoodCategoryChip(
    category: String,
    isSelected: Boolean = false,
    onSelectedCategoryChanged: () -> Unit,
    onExecuteSearch: () -> Unit
) {

    Surface(
        modifier = Modifier.padding(end = 8.dp),
        elevation = 8.dp,
        shape = MaterialTheme.shapes.medium,
        color = if (isSelected) MaterialTheme.colors.secondary else MaterialTheme.colors.primary
    ) {
        Row(
            modifier = Modifier.toggleable(
                value = isSelected,
                onValueChange = {
                    onSelectedCategoryChanged()
                    onExecuteSearch()
                })
        ) {
            Text(
                text = category,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}