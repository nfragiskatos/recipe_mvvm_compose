package com.nfragiskatos.recipe_mvvm_compose.data.repository

import com.nfragiskatos.recipe_mvvm_compose.data.model.RecipeAPIResponse
import com.nfragiskatos.recipe_mvvm_compose.data.repository.datasource.RecipeRemoteDataSource
import com.nfragiskatos.recipe_mvvm_compose.data.util.Resource
import com.nfragiskatos.recipe_mvvm_compose.domain.repository.RecipeRepository
import retrofit2.Response

class RecipeRepositoryImpl(
    private val recipeRemoteDataSource: RecipeRemoteDataSource
) : RecipeRepository {

    override suspend fun getSearchedRecipes(
        page: Int,
        query: String
    ): Resource<RecipeAPIResponse> {
        return responseToResource(
            recipeRemoteDataSource.getSearchedRecipes(
                page,
                query
            )
        )
    }

    private fun responseToResource(response: Response<RecipeAPIResponse>): Resource<RecipeAPIResponse> {
        if (response.isSuccessful) {
            response.body()
                ?.let {
                    return Resource.Success(it)
                }
        }
        return Resource.Error(response.message())
    }
}