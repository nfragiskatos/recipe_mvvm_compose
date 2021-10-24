package com.nfragiskatos.recipe_mvvm_compose.domain.model

import java.io.Serializable

data class RecipeAPIResponse(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<Recipe>
) : Serializable
