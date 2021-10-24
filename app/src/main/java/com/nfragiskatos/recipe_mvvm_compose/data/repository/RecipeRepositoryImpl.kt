package com.nfragiskatos.recipe_mvvm_compose.data.repository

import com.nfragiskatos.recipe_mvvm_compose.data.model.Recipe
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
        return responseToResourceFromSearch(
            recipeRemoteDataSource.getSearchedRecipes(
                page,
                query
            )
        )
    }

    override suspend fun getRecipeById(id: Int): Resource<Recipe> {
        return responseToResourceFromId(recipeRemoteDataSource.getRecipeById(id))
    }

    private fun responseToResourceFromSearch(response: Response<RecipeAPIResponse>): Resource<RecipeAPIResponse> {
        if (response.isSuccessful) {
            response.body()
                ?.let {
                    return Resource.Success(it)
                }
        }
        return Resource.Error(response.message())
    }

    private fun responseToResourceFromId(response: Response<Recipe>): Resource<Recipe> {
        if (response.isSuccessful) {
            response.body()
                ?.let {
                    return Resource.Success(it)
                }
        }
        return Resource.Error(response.message())
    }
}