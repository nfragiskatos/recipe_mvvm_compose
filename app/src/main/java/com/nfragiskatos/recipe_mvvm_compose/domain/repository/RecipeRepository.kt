package com.nfragiskatos.recipe_mvvm_compose.domain.repository

import com.nfragiskatos.recipe_mvvm_compose.data.model.RecipeAPIResponse
import com.nfragiskatos.recipe_mvvm_compose.data.util.Resource

interface RecipeRepository {

    suspend fun getSearchedRecipes(
        page: Int,
        query: String
    ): Resource<RecipeAPIResponse>
}