package com.nfragiskatos.recipe_mvvm_compose.data.model.network

import com.nfragiskatos.recipe_mvvm_compose.domain.model.RecipeAPIResponse
import com.nfragiskatos.recipe_mvvm_compose.domain.util.DataMapper

class RecipeAPIResponseDTOMapper(private val recipeDTOMapper: RecipeDTOMapper) : DataMapper<RecipeAPIResponseDTO, RecipeAPIResponse> {
    override fun mapFromData(data: RecipeAPIResponseDTO): RecipeAPIResponse {
        return RecipeAPIResponse(
            count = data.count,
            next = data.next ?: "",
            previous = data.previous ?: "",
            results = recipeDTOMapper.mapFromDataList(data.results)
        )
    }

    override fun mapToData(domain: RecipeAPIResponse): RecipeAPIResponseDTO {
        return RecipeAPIResponseDTO(
            count = domain.count,
            next = domain.next,
            previous = domain.previous,
            results = recipeDTOMapper.mapToDataList(domain.results)
        )
    }
}