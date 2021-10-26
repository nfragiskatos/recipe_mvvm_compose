package com.nfragiskatos.recipe_mvvm_compose.ui.fragments.recipe_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.TextField
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nfragiskatos.recipe_mvvm_compose.ui.components.RecipeCard
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalUnitApi
@AndroidEntryPoint
class RecipeListFragment : Fragment() {
    val viewModel: RecipeListViewModel by viewModels()

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

                val resource = viewModel.resource.value
                val recipes = resource.data?.results ?: listOf()

                Column() {

                    TextField(
                        value = viewModel.query.value,
                        onValueChange = { query ->
                            viewModel.onQueryChange(query)
                        }
                    )

                    Spacer(modifier = Modifier.padding(10.dp))

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