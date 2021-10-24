package com.nfragiskatos.recipe_mvvm_compose.domain.usecase

import android.util.Log
import com.nfragiskatos.recipe_mvvm_compose.data.util.Resource
import com.nfragiskatos.recipe_mvvm_compose.domain.model.RecipeAPIResponse
import com.nfragiskatos.recipe_mvvm_compose.domain.repository.RecipeRepository

class GetSearchedRecipesUseCase(private val recipeRepository: RecipeRepository) {

    suspend fun execute(
        page: Int,
        query: String
    ): Resource<RecipeAPIResponse> {

        val resource = recipeRepository.getSearchedRecipes(
            page,
            query
        )
        
        resource.data?.results?.forEachIndexed { index, recipe ->
            Log.i("MY_TAG", "item $index - ${recipe.title}")
        }

        return recipeRepository.getSearchedRecipes(
            page,
            query
        )
    }
}