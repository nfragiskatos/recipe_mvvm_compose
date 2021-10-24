package com.nfragiskatos.recipe_mvvm_compose.domain.usecase

import com.nfragiskatos.recipe_mvvm_compose.data.model.Recipe
import com.nfragiskatos.recipe_mvvm_compose.data.util.Resource
import com.nfragiskatos.recipe_mvvm_compose.domain.repository.RecipeRepository

class GetRecipeByIdUseCase(private val recipeRepository: RecipeRepository) {

    suspend fun execute(
        id: Int
    ): Resource<Recipe> {
        return recipeRepository.getRecipeById(id)
    }
}