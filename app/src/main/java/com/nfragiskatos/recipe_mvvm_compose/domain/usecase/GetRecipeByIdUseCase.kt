package com.nfragiskatos.recipe_mvvm_compose.domain.usecase

import com.nfragiskatos.recipe_mvvm_compose.data.model.network.RecipeDTO
import com.nfragiskatos.recipe_mvvm_compose.data.util.Resource
import com.nfragiskatos.recipe_mvvm_compose.domain.repository.RecipeRepository

class GetRecipeByIdUseCase(private val recipeRepository: RecipeRepository) {

    suspend fun execute(
        id: Int
    ): Resource<RecipeDTO> {
        return recipeRepository.getRecipeById(id)
    }
}