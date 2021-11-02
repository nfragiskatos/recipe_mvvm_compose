package com.nfragiskatos.recipe_mvvm_compose.di

import android.content.Context
import com.nfragiskatos.recipe_mvvm_compose.ui.RecipeApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): RecipeApp {
        return app as RecipeApp
    }
}