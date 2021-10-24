package com.nfragiskatos.recipe_mvvm_compose.domain.di

import com.nfragiskatos.recipe_mvvm_compose.data.model.network.RecipeAPIResponseDTOMapper
import com.nfragiskatos.recipe_mvvm_compose.data.model.network.RecipeDTOMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MapperModule {

    @Provides
    @Singleton
    fun provideRecipeDTOMapper(): RecipeDTOMapper {
        return RecipeDTOMapper()
    }

    @Provides
    @Singleton
    fun provideRecipeAPIResponseDTOMapper(recipeDTOMapper: RecipeDTOMapper): RecipeAPIResponseDTOMapper {
        return RecipeAPIResponseDTOMapper(recipeDTOMapper)
    }
}