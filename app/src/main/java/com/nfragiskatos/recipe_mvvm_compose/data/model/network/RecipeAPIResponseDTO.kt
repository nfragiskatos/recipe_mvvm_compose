package com.nfragiskatos.recipe_mvvm_compose.data.model.network


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RecipeAPIResponseDTO(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String?,
    @SerializedName("previous")
    val previous: String?,
    @SerializedName("results")
    val results: List<RecipeDTO>
) : Serializable