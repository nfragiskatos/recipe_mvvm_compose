package com.nfragiskatos.recipe_mvvm_compose.data.model.network

import com.nfragiskatos.recipe_mvvm_compose.domain.model.Recipe
import com.nfragiskatos.recipe_mvvm_compose.domain.util.DomainMapper

class RecipeDTOMapper : DomainMapper<RecipeDTO, Recipe> {
    override fun mapToDomainModel(model: RecipeDTO): Recipe {
        return Recipe(
            id = model.id,
            cookingInstructions = model.cookingInstructions ?: "",
            dateAdded = model.dateAdded ?: "",
            dateUpdated = model.dateUpdated ?: "",
            description = model.description ?: "",
            featuredImage = model.featuredImage ?: "",
            ingredients = model.ingredients ?: listOf(),
            longDateAdded = model.longDateAdded ?: 0,
            longDateUpdated = model.longDateUpdated ?: 0,
            publisher = model.publisher ?: "",
            rating = model.rating ?: 0,
            sourceUrl = model.sourceUrl ?: "",
            title = model.title ?: "",
        )
    }

    override fun mapFromDomainModel(domainModel: Recipe): RecipeDTO {
        return RecipeDTO(
            id = domainModel.id,
            cookingInstructions = domainModel.cookingInstructions,
            dateAdded = domainModel.dateAdded,
            dateUpdated = domainModel.dateUpdated,
            description = domainModel.description,
            featuredImage = domainModel.featuredImage,
            ingredients = domainModel.ingredients,
            longDateAdded = domainModel.longDateAdded,
            longDateUpdated = domainModel.longDateUpdated,
            publisher = domainModel.publisher,
            rating = domainModel.rating,
            sourceUrl = domainModel.sourceUrl,
            title = domainModel.title,
        )
    }

    fun mapToDomainModelList(models: List<RecipeDTO>): List<Recipe> {
        return models.map {
            mapToDomainModel(it)
        }
    }

    fun mapFromDomainModelList(domainModels: List<Recipe>): List<RecipeDTO> {
        return domainModels.map {
            mapFromDomainModel(it)
        }
    }
}