package com.nfragiskatos.recipe_mvvm_compose.data.repository

import com.nfragiskatos.recipe_mvvm_compose.data.model.network.RecipeDTO
import com.nfragiskatos.recipe_mvvm_compose.data.model.network.RecipeAPIResponseDTO
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
    ): Resource<RecipeAPIResponseDTO> {
        return responseToResourceFromSearch(
            recipeRemoteDataSource.getSearchedRecipes(
                page,
                query
            )
        )
    }

    override suspend fun getRecipeById(id: Int): Resource<RecipeDTO> {
        return responseToResourceFromId(recipeRemoteDataSource.getRecipeById(id))
    }

    private fun responseToResourceFromSearch(response: Response<RecipeAPIResponseDTO>): Resource<RecipeAPIResponseDTO> {
        if (response.isSuccessful) {
            response.body()
                ?.let {
                    return Resource.Success(it)
                }
        }
        return Resource.Error(response.message())
    }

    private fun responseToResourceFromId(response: Response<RecipeDTO>): Resource<RecipeDTO> {
        if (response.isSuccessful) {
            response.body()
                ?.let {
                    return Resource.Success(it)
                }
        }
        return Resource.Error(response.message())
    }
}