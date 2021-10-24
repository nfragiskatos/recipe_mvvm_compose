package com.nfragiskatos.recipe_mvvm_compose.ui.fragments.recipe_list.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nfragiskatos.recipe_mvvm_compose.domain.usecase.GetRecipeByIdUseCase
import com.nfragiskatos.recipe_mvvm_compose.domain.usecase.GetSearchedRecipesUseCase

class RecipeListViewModelFactory(
    private val app: Application,
    private val getSearchedRecipesUseCase: GetSearchedRecipesUseCase,
    private val getRecipeByIdUseCase: GetRecipeByIdUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RecipeListViewModel(
            app,
            getSearchedRecipesUseCase,
            getRecipeByIdUseCase
        ) as T
    }
}