package com.nfragiskatos.recipe_mvvm_compose.ui.fragments.recipe_list

sealed class RecipeListEvent {
    object NewSearchEvent: RecipeListEvent()
    object NextPageEvent: RecipeListEvent()
}
