package com.nfragiskatos.recipe_mvvm_compose.domain.di

import com.nfragiskatos.recipe_mvvm_compose.domain.repository.RecipeRepository
import com.nfragiskatos.recipe_mvvm_compose.domain.usecase.GetRecipeByIdUseCase
import com.nfragiskatos.recipe_mvvm_compose.domain.usecase.GetSearchedRecipesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetSearchedRecipesUseCase(recipeRepository: RecipeRepository): GetSearchedRecipesUseCase {
        return GetSearchedRecipesUseCase(recipeRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetRecipeByIdUseCase(recipeRepository: RecipeRepository): GetRecipeByIdUseCase {
        return GetRecipeByIdUseCase(recipeRepository)
    }
}