package com.nfragiskatos.recipe_mvvm_compose.data.repository.datasource

import com.nfragiskatos.recipe_mvvm_compose.data.model.RecipeAPIResponse
import retrofit2.Response

interface RecipeRemoteDataSource {

    suspend fun getSearchedRecipes(
        page: Int,
        query: String
    ) : Response<RecipeAPIResponse>
}