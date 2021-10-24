package com.nfragiskatos.recipe_mvvm_compose.data.model.network

import com.nfragiskatos.recipe_mvvm_compose.domain.model.RecipeAPIResponse
import com.nfragiskatos.recipe_mvvm_compose.domain.util.DomainMapper

class RecipeAPIResponseDTOMapper(private val recipeDTOMapper: RecipeDTOMapper) : DomainMapper<RecipeAPIResponseDTO, RecipeAPIResponse> {
    override fun mapToDomainModel(model: RecipeAPIResponseDTO): RecipeAPIResponse {
        return RecipeAPIResponse(
            count = model.count,
            next = model.next ?: "",
            previous = model.previous ?: "",
            results = recipeDTOMapper.mapToDomainModelList(model.results)
        )
    }

    override fun mapFromDomainModel(domainModel: RecipeAPIResponse): RecipeAPIResponseDTO {
        return RecipeAPIResponseDTO(
            count = domainModel.count,
            next = domainModel.next,
            previous = domainModel.previous,
            results = recipeDTOMapper.mapFromDomainModelList(domainModel.results)
        )
    }
}