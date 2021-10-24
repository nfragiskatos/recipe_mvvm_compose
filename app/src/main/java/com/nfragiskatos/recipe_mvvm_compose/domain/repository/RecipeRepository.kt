package com.nfragiskatos.recipe_mvvm_compose.domain.repository

import com.nfragiskatos.recipe_mvvm_compose.data.model.network.RecipeDTO
import com.nfragiskatos.recipe_mvvm_compose.data.model.network.RecipeAPIResponseDTO
import com.nfragiskatos.recipe_mvvm_compose.data.util.Resource

interface RecipeRepository {

    suspend fun getSearchedRecipes(
        page: Int,
        query: String
    ): Resource<RecipeAPIResponseDTO>

    suspend fun getRecipeById(id: Int): Resource<RecipeDTO>
}