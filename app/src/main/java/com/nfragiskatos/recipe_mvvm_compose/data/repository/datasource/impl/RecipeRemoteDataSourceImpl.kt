package com.nfragiskatos.recipe_mvvm_compose.data.repository.datasource.impl

import com.nfragiskatos.recipe_mvvm_compose.data.api.RecipeAPIService
import com.nfragiskatos.recipe_mvvm_compose.data.model.network.RecipeDTO
import com.nfragiskatos.recipe_mvvm_compose.data.model.network.RecipeAPIResponseDTO
import com.nfragiskatos.recipe_mvvm_compose.data.repository.datasource.RecipeRemoteDataSource
import retrofit2.Response

class RecipeRemoteDataSourceImpl(
    private val recipeAPIService: RecipeAPIService
) : RecipeRemoteDataSource {
    override suspend fun getSearchedRecipes(
        page: Int,
        query: String
    ): Response<RecipeAPIResponseDTO> {
        return recipeAPIService.getSearchedRecipes(
            page,
            query
        )
    }

    override suspend fun getRecipeById(id: Int): Response<RecipeDTO> {
        return recipeAPIService.getRecipeById(id)
    }
}