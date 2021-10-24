package com.nfragiskatos.recipe_mvvm_compose.data.repository

import com.nfragiskatos.recipe_mvvm_compose.data.model.network.RecipeAPIResponseDTO
import com.nfragiskatos.recipe_mvvm_compose.data.model.network.RecipeAPIResponseDTOMapper
import com.nfragiskatos.recipe_mvvm_compose.data.model.network.RecipeDTO
import com.nfragiskatos.recipe_mvvm_compose.data.model.network.RecipeDTOMapper
import com.nfragiskatos.recipe_mvvm_compose.data.repository.datasource.RecipeRemoteDataSource
import com.nfragiskatos.recipe_mvvm_compose.data.util.Resource
import com.nfragiskatos.recipe_mvvm_compose.domain.model.Recipe
import com.nfragiskatos.recipe_mvvm_compose.domain.model.RecipeAPIResponse
import com.nfragiskatos.recipe_mvvm_compose.domain.repository.RecipeRepository
import retrofit2.Response

class RecipeRepositoryImpl(
    private val recipeRemoteDataSource: RecipeRemoteDataSource,
    private val recipeAPIResponseDTOMapper: RecipeAPIResponseDTOMapper,
    private val recipeDTOMapper: RecipeDTOMapper
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

    private fun responseToResourceFromSearch(response: Response<RecipeAPIResponseDTO>): Resource<RecipeAPIResponse> {
        if (response.isSuccessful) {
            response.body()
                ?.let {
                    return Resource.Success(recipeAPIResponseDTOMapper.mapFromData(it))
                }
        }
        return Resource.Error(response.message())
    }

    private fun responseToResourceFromId(response: Response<RecipeDTO>): Resource<Recipe> {
        if (response.isSuccessful) {
            response.body()
                ?.let {
                    return Resource.Success(recipeDTOMapper.mapFromData(it))
                }
        }
        return Resource.Error(response.message())
    }
}