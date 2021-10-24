package com.nfragiskatos.recipe_mvvm_compose.data.di

import com.nfragiskatos.recipe_mvvm_compose.data.api.RecipeAPIService
import com.nfragiskatos.recipe_mvvm_compose.data.repository.datasource.RecipeRemoteDataSource
import com.nfragiskatos.recipe_mvvm_compose.data.repository.datasource.impl.RecipeRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Provides
    @Singleton
    fun provideRecipeRemoteDataSource(recipeAPIService: RecipeAPIService) : RecipeRemoteDataSource {
        return RecipeRemoteDataSourceImpl(recipeAPIService)
    }
}