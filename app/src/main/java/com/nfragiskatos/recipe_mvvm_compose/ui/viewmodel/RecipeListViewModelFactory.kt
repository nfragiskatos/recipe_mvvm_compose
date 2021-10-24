package com.nfragiskatos.recipe_mvvm_compose.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nfragiskatos.recipe_mvvm_compose.domain.usecase.GetSearchedRecipesUseCase

class RecipeListViewModelFactory(
    private val app: Application,
    private val getSearchedRecipesUseCase: GetSearchedRecipesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RecipeListViewModel(
            app,
            getSearchedRecipesUseCase
        ) as T
    }
}