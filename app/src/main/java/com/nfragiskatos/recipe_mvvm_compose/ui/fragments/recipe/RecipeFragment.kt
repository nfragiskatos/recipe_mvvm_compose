package com.nfragiskatos.recipe_mvvm_compose.ui.fragments.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nfragiskatos.recipe_mvvm_compose.ui.fragments.recipe.RecipeEvent.GetRecipeEvent
import com.nfragiskatos.recipe_mvvm_compose.ui.theme.Recipe_mvvm_composeTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalUnitApi
@AndroidEntryPoint
class RecipeFragment : Fragment() {

    private val viewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("recipeId")?.let {
            viewModel.onTriggerEvent(GetRecipeEvent(it))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Recipe_mvvm_composeTheme() {

                    val recipe = viewModel.recipe.value
                    val loading = viewModel.loading.value

                    Surface() {
                        Text(
                            text = "RECIPE FRAGMENT with recipe: ${recipe?.id}, ${recipe?.title}",
                            style = TextStyle(
                                fontSize = TextUnit(25f, TextUnitType.Sp)
                            )
                        )
                    }
                }
            }
        }
    }
}