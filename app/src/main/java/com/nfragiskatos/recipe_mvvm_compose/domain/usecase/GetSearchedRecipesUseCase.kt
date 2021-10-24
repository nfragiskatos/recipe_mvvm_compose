package com.nfragiskatos.recipe_mvvm_compose.domain.usecase

import com.nfragiskatos.recipe_mvvm_compose.data.model.network.RecipeAPIResponseDTO
import com.nfragiskatos.recipe_mvvm_compose.data.util.Resource
import com.nfragiskatos.recipe_mvvm_compose.domain.repository.RecipeRepository

class GetSearchedRecipesUseCase(private val recipeRepository: RecipeRepository) {

    suspend fun execute(
        page: Int,
        query: String
    ): Resource<RecipeAPIResponseDTO> {
        return recipeRepository.getSearchedRecipes(
            page,
            query
        )
    }
}