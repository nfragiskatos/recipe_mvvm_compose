package com.nfragiskatos.recipe_mvvm_compose.ui.fragments.recipe

sealed class RecipeEvent {
    data class GetRecipeEvent(val id: Int) : RecipeEvent()
}
