package com.nfragiskatos.recipe_mvvm_compose.ui.components

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nfragiskatos.recipe_mvvm_compose.R
import com.nfragiskatos.recipe_mvvm_compose.domain.model.Recipe
import com.nfragiskatos.recipe_mvvm_compose.ui.fragments.recipe_list.PAGE_SIZE
import com.nfragiskatos.recipe_mvvm_compose.ui.fragments.recipe_list.RecipeListEvent

@Composable
fun RecipeList(
    loading: Boolean,
    recipes: List<Recipe>,
    page: Int,
    scaffoldState: ScaffoldState,
    navController: NavController,
    onChangeRecipeScrollPosition: (Int) -> Unit,
    onTriggerEvent: (RecipeListEvent) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        if (loading && recipes.isEmpty()) {
            LoadingRecipeListShimmer(cardHeight = 250.dp)
        } else {
            LazyColumn() {
                itemsIndexed(
                    items = recipes
                ) { index, item ->
                    onChangeRecipeScrollPosition(index)
                    if ((index + 1) >= (page * PAGE_SIZE) && !loading) {
                        onTriggerEvent(RecipeListEvent.NextPageEvent)
                    }
                    RecipeCard(
                        recipe = item,
                        onClick = {
                            val bundle = Bundle()
                            bundle.putInt("recipeId", item.id)
                            navController.navigate(R.id.action_recipeListFragment_to_recipeFragment, bundle)
                        }
                    )
                }
            }
        }
        CircularIndeterminateProgressBar(isDisplayed = loading)
        DefaultSnacbar(
            snackbarHostState = scaffoldState.snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
        }
    }
}