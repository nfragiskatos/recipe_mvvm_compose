package com.nfragiskatos.recipe_mvvm_compose.data.model.network

import com.nfragiskatos.recipe_mvvm_compose.domain.model.Recipe
import com.nfragiskatos.recipe_mvvm_compose.domain.util.DataMapper

class RecipeDTOMapper : DataMapper<RecipeDTO, Recipe> {
    override fun mapFromData(data: RecipeDTO): Recipe {
        return Recipe(
            id = data.id,
            cookingInstructions = data.cookingInstructions ?: "",
            dateAdded = data.dateAdded ?: "",
            dateUpdated = data.dateUpdated ?: "",
            description = data.description ?: "",
            featuredImage = data.featuredImage ?: "",
            ingredients = data.ingredients ?: listOf(),
            longDateAdded = data.longDateAdded ?: 0,
            longDateUpdated = data.longDateUpdated ?: 0,
            publisher = data.publisher ?: "",
            rating = data.rating ?: 0,
            sourceUrl = data.sourceUrl ?: "",
            title = data.title ?: "",
        )
    }

    override fun mapToData(domain: Recipe): RecipeDTO {
        return RecipeDTO(
            id = domain.id,
            cookingInstructions = domain.cookingInstructions,
            dateAdded = domain.dateAdded,
            dateUpdated = domain.dateUpdated,
            description = domain.description,
            featuredImage = domain.featuredImage,
            ingredients = domain.ingredients,
            longDateAdded = domain.longDateAdded,
            longDateUpdated = domain.longDateUpdated,
            publisher = domain.publisher,
            rating = domain.rating,
            sourceUrl = domain.sourceUrl,
            title = domain.title,
        )
    }

    fun mapFromDataList(dtos: List<RecipeDTO>): List<Recipe> {
        return dtos.map {
            mapFromData(it)
        }
    }

    fun mapToDataList(recipes: List<Recipe>): List<RecipeDTO> {
        return recipes.map {
            mapToData(it)
        }
    }
}