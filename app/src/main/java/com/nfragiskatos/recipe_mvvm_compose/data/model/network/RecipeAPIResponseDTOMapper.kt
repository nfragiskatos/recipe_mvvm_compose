package com.nfragiskatos.recipe_mvvm_compose.data.model.network

import com.nfragiskatos.recipe_mvvm_compose.domain.model.RecipeAPIResponse
import com.nfragiskatos.recipe_mvvm_compose.domain.util.DataMapper

class RecipeAPIResponseDTOMapper() : DataMapper<RecipeAPIResponseDTO, RecipeAPIResponse> {
    override fun mapFromData(data: RecipeAPIResponseDTO): RecipeAPIResponse {
        val dtoMapper = RecipeDTOMapper()

        return RecipeAPIResponse(
            count = data.count,
            next = data.next ?: "",
            previous = data.previous ?: "",
            results = dtoMapper.mapFromDataList(data.results)
        )
    }

    override fun mapToData(domain: RecipeAPIResponse): RecipeAPIResponseDTO {
        val dtoMapper = RecipeDTOMapper()

        return RecipeAPIResponseDTO(
            count = domain.count,
            next = domain.next,
            previous = domain.previous,
            results = dtoMapper.mapToDataList(domain.results)
        )
    }
}