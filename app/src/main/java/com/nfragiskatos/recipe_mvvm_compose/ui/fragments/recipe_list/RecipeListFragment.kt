package com.nfragiskatos.recipe_mvvm_compose.ui.fragments.recipe_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nfragiskatos.recipe_mvvm_compose.ui.components.RecipeCard
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
                    val focusManager = LocalFocusManager.current
                    val categories = FoodCategory.values()

                    Column() {

                        Surface(
                            elevation = 8.dp,
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colors.primary
                        ) {
                            Column() {
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    TextField(
                                        modifier = Modifier
                                            .fillMaxWidth(0.9f)
                                            .padding(8.dp),
                                        value = query,
                                        onValueChange = { query ->
                                            viewModel.onQueryChange(query)
                                        },
                                        label = {
                                            Text(text = "Search")
                                        },
                                        keyboardOptions = KeyboardOptions(
                                            keyboardType = KeyboardType.Text,
                                            imeAction = ImeAction.Search
                                        ),
                                        leadingIcon = {
                                            Icon(
                                                imageVector = Icons.Filled.Search,
                                                contentDescription = "search"
                                            )
                                        },
                                        keyboardActions = KeyboardActions(onSearch = {
                                            viewModel.getSearchedRecipes(
                                                1,
                                                query
                                            )
                                            focusManager.clearFocus()
                                        }),
                                        textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                                        colors = TextFieldDefaults.textFieldColors(
                                            backgroundColor = MaterialTheme.colors.surface
                                        )
                                    )
                                }

                                LazyRow(modifier = Modifier.fillMaxWidth()) {
                                    items(
                                        items = categories
                                    ) { category ->
                                        Text(
                                            text = category.value,
                                            style = MaterialTheme.typography.body2,
                                            color = MaterialTheme.colors.onSecondary,
                                            modifier = Modifier
                                                .padding(8.dp)
                                                .background(color = MaterialTheme.colors.secondary),
                                        )
                                    }
                                }
                            }
                        }
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