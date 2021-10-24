package com.nfragiskatos.recipe_mvvm_compose.domain.model

import java.io.Serializable

data class Recipe(
    val id: Int,
    val cookingInstructions: String,
    val dateAdded: String,
    val dateUpdated: String,
    val description: String,
    val featuredImage: String,
    val ingredients: List<String>,
    val longDateAdded: Long,
    val longDateUpdated: Long,
    val publisher: String,
    val rating: Int,
    val sourceUrl: String,
    val title: String
) : Serializable