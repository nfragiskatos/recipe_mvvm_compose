package com.nfragiskatos.recipe_mvvm_compose.ui.fragments.recipe_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nfragiskatos.recipe_mvvm_compose.R
import com.nfragiskatos.recipe_mvvm_compose.ui.theme.Recipe_mvvm_composeTheme
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalUnitApi
@AndroidEntryPoint
class RecipeListFragment : Fragment() {
    val viewModel: RecipeListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("MY_TAG", "View Model: $viewModel")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                Recipe_mvvm_composeTheme() {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Recipe List",
                            style = TextStyle(
                                fontSize = TextUnit(
                                    17f,
                                    TextUnitType.Sp
                                )
                            )
                        )
                        Spacer(modifier = Modifier.padding(10.dp))
                        Button(onClick = {
                            findNavController().navigate(R.id.action_recipeListFragment_to_recipeFragment)
//                            viewModel.getSearchedRecipes(
//                                1,
//                                "chicken"
//                            )
//                            viewModel.getRecipeById(9)
                        }) {
                            Text(text = "TO RECIPE FRAGMENT")
                        }
                    }
                }
            }
        }
    }
}