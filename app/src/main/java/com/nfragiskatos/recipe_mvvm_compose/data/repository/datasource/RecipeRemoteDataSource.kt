package com.nfragiskatos.recipe_mvvm_compose.data.repository.datasource

import com.nfragiskatos.recipe_mvvm_compose.data.model.network.RecipeDTO
import com.nfragiskatos.recipe_mvvm_compose.data.model.network.RecipeAPIResponseDTO
import retrofit2.Response

interface RecipeRemoteDataSource {

    suspend fun getSearchedRecipes(
        page: Int,
        query: String
    ): Response<RecipeAPIResponseDTO>

    suspend fun getRecipeById(id: Int): Response<RecipeDTO>
}