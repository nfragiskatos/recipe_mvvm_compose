package com.nfragiskatos.recipe_mvvm_compose.ui.di

import android.app.Application
import com.nfragiskatos.recipe_mvvm_compose.domain.usecase.GetSearchedRecipesUseCase
import com.nfragiskatos.recipe_mvvm_compose.ui.viewmodel.RecipeListViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Provides
    @Singleton
    fun provideRecipeListViewModelFactory(
        app: Application,
        getSearchedRecipesUseCase: GetSearchedRecipesUseCase
    ): RecipeListViewModelFactory {
        return RecipeListViewModelFactory(
            app,
            getSearchedRecipesUseCase
        )
    }
}