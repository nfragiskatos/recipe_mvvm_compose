package com.nfragiskatos.recipe_mvvm_compose.data.model.network


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RecipeDTO(
    @SerializedName("pk")
    val id: Int,

    @SerializedName("cooking_instructions")
    val cookingInstructions: String?,
    @SerializedName("date_added")
    val dateAdded: String?,
    @SerializedName("date_updated")
    val dateUpdated: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("featured_image")
    val featuredImage: String?,
    @SerializedName("ingredients")
    val ingredients: List<String>?,
    @SerializedName("long_date_added")
    val longDateAdded: Long?,
    @SerializedName("long_date_updated")
    val longDateUpdated: Long?,
    @SerializedName("publisher")
    val publisher: String?,
    @SerializedName("rating")
    val rating: Int?,
    @SerializedName("source_url")
    val sourceUrl: String?,
    @SerializedName("title")
    val title: String?
) : Serializable