package com.nfragiskatos.recipe_mvvm_compose.data.repository.datasource.impl

import com.nfragiskatos.recipe_mvvm_compose.data.api.RecipeAPIService
import com.nfragiskatos.recipe_mvvm_compose.data.model.RecipeAPIResponse
import com.nfragiskatos.recipe_mvvm_compose.data.repository.datasource.RecipeRemoteDataSource
import retrofit2.Response

class RecipeRemoteDataSourceImpl(
    private val recipeAPIService: RecipeAPIService
) : RecipeRemoteDataSource {
    override suspend fun getSearchedRecipes(
        page: Int,
        query: String
    ): Response<RecipeAPIResponse> {
        return recipeAPIService.getSearchedRecipes(
            page,
            query
        )
    }
}