package com.nfragiskatos.recipe_mvvm_compose.di

import com.nfragiskatos.recipe_mvvm_compose.BuildConfig
import com.nfragiskatos.recipe_mvvm_compose.data.api.RecipeAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.RECIPE_API_BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideRecipeApiService(retrofit: Retrofit): RecipeAPIService {
        return retrofit.create(RecipeAPIService::class.java)
    }
}