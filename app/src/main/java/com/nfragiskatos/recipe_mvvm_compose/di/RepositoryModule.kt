package com.nfragiskatos.recipe_mvvm_compose.di

import com.nfragiskatos.recipe_mvvm_compose.data.model.network.RecipeAPIResponseDTOMapper
import com.nfragiskatos.recipe_mvvm_compose.data.model.network.RecipeDTOMapper
import com.nfragiskatos.recipe_mvvm_compose.data.repository.RecipeRepositoryImpl
import com.nfragiskatos.recipe_mvvm_compose.data.repository.datasource.RecipeRemoteDataSource
import com.nfragiskatos.recipe_mvvm_compose.domain.repository.RecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRecipeRepository(
        recipeRemoteDataSource: RecipeRemoteDataSource,
        recipeAPIResponseDTOMapper: RecipeAPIResponseDTOMapper,
        recipeDTOMapper: RecipeDTOMapper
    ): RecipeRepository {
        return RecipeRepositoryImpl(
            recipeRemoteDataSource,
            recipeAPIResponseDTOMapper,
            recipeDTOMapper
        )
    }
}