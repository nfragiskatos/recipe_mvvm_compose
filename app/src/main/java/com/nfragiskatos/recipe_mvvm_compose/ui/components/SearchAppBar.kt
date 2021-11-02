package com.nfragiskatos.recipe_mvvm_compose.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.nfragiskatos.recipe_mvvm_compose.ui.fragments.recipe_list.FoodCategory

@Composable
fun SearchAppBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    getSearchedRecipes: () -> Unit,
    chipPosition: Int,
    selectedCategory: FoodCategory?,
    onSelectedCategoryChanged: (FoodCategory, Int) -> Unit,
    onToggleTheme: () -> Unit

) {


    Surface(
        elevation = 8.dp,
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.surface
    ) {

        Column() {

            SearchAppBarTextField(
                query = query,
                onQueryChanged = onQueryChanged,
                getSearchedRecipes = getSearchedRecipes,
                onToggleTheme = onToggleTheme
            )

            SearchAppBarCategorySelector(
                chipPosition = chipPosition,
                selectedCategory = selectedCategory,
                getSearchedRecipes = getSearchedRecipes,
                onSelectedCategoryChanged = onSelectedCategoryChanged
            )

        }
    }
}

@Composable
private fun SearchAppBarTextField(
    query: String,
    onQueryChanged: (String) -> Unit,
    getSearchedRecipes: () -> Unit,
    onToggleTheme: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(8.dp),
            value = query,
            onValueChange = { query ->
                onQueryChanged(query)
            },
            label = {
                Text(text = "Search")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "search"
                )
            },
            keyboardActions = KeyboardActions(onSearch = {
                getSearchedRecipes()
                focusManager.clearFocus()
            }),
            textStyle = MaterialTheme.typography.button,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface
            )
        )
        IconButton(
            onClick = onToggleTheme,
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = ""
            )

        }
    }
}

@Composable
private fun SearchAppBarCategorySelector(
    chipPosition: Int,
    selectedCategory: FoodCategory?,
    getSearchedRecipes: () -> Unit,
    onSelectedCategoryChanged: (FoodCategory, Int) -> Unit

) {
    val categories = FoodCategory.values()

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 8.dp,
                bottom = 8.dp
            ),
        state = LazyListState(chipPosition)
    ) {
        itemsIndexed(
            items = categories
        ) { index, category ->
            FoodCategoryChip(
                category = category.value,
                isSelected = selectedCategory == category,
                onExecuteSearch = {
                    getSearchedRecipes()
                },
                onSelectedCategoryChanged = {
                    onSelectedCategoryChanged(
                        category,
                        index
                    )
                }
            )
        }
    }
}