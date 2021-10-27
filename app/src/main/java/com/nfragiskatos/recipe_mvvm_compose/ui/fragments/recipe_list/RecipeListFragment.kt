package com.nfragiskatos.recipe_mvvm_compose.ui.fragments.recipe_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nfragiskatos.recipe_mvvm_compose.ui.components.RecipeCard
import com.nfragiskatos.recipe_mvvm_compose.ui.components.SearchAppBar
import com.nfragiskatos.recipe_mvvm_compose.ui.theme.Recipe_mvvm_composeTheme
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalUnitApi
@AndroidEntryPoint
class RecipeListFragment : Fragment() {
    private val viewModel: RecipeListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(
            "MY_TAG",
            "View Model: $viewModel"
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {

                Recipe_mvvm_composeTheme() {
                    val resource = viewModel.resource.value
                    val recipes = resource.data?.results ?: listOf()
                    val query = viewModel.query.value
                    val selectedCategory = viewModel.selectedCategory.value
                    val chipPosition = viewModel.chipPosition

                    Column() {

                        SearchAppBar(
                            query = query,
                            onQueryChanged = viewModel::onQueryChange,
                            getSearchedRecipes = viewModel::getSearchedRecipes,
                            chipPosition = chipPosition,
                            selectedCategory = selectedCategory,
                            onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged
                        )

                        LazyColumn() {
                            itemsIndexed(
                                items = recipes
                            ) { index, item ->
                                RecipeCard(
                                    recipe = item,
                                    onClick = {
                                        Log.i(
                                            "MY_TAG",
                                            "You clicked on ${item.title}"
                                        )
                                    })
                            }
                        }
                    }
                }
            }
        }
    }
}