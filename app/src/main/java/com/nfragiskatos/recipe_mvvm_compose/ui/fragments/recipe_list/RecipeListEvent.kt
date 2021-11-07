package com.nfragiskatos.recipe_mvvm_compose.ui.fragments.recipe_list

sealed class RecipeListEvent {
    object NewSearchEvent: RecipeListEvent()
    object NextPageEvent: RecipeListEvent()

    // restore after process death
    object RestoreStateEvent: RecipeListEvent()
}
