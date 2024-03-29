package com.nfragiskatos.recipe_mvvm_compose.data.api

import com.nfragiskatos.recipe_mvvm_compose.data.model.network.RecipeDTO
import com.nfragiskatos.recipe_mvvm_compose.data.model.network.RecipeAPIResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RecipeAPIService {

    @GET("search")
    suspend fun getSearchedRecipes(

        @Query("page")
        page: Int,

        @Query("query")
        query: String,

        @Header("Authorization")
        authorization: String = "Token 9c8b06d329136da358c2d00e76946b0111ce2c48"
    ): Response<RecipeAPIResponseDTO>

    @GET("get")
    suspend fun getRecipeById(
        @Query("id")
        id: Int,
        
        @Header("Authorization")
        authorization: String = "Token 9c8b06d329136da358c2d00e76946b0111ce2c48"
    ): Response<RecipeDTO>
}