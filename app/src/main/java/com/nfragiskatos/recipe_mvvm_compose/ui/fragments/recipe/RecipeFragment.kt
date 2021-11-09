package com.nfragiskatos.recipe_mvvm_compose.ui.fragments.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.nfragiskatos.recipe_mvvm_compose.ui.RecipeApp
import com.nfragiskatos.recipe_mvvm_compose.ui.components.CircularIndeterminateProgressBar
import com.nfragiskatos.recipe_mvvm_compose.ui.components.DefaultSnacbar
import com.nfragiskatos.recipe_mvvm_compose.ui.components.RecipeView
import com.nfragiskatos.recipe_mvvm_compose.ui.components.SnackbarController
import com.nfragiskatos.recipe_mvvm_compose.ui.fragments.recipe.RecipeEvent.GetRecipeEvent
import com.nfragiskatos.recipe_mvvm_compose.ui.theme.Recipe_mvvm_composeTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalUnitApi
@AndroidEntryPoint
class RecipeFragment : Fragment() {

    @Inject
    lateinit var application: RecipeApp

    private val snackbarController = SnackbarController(lifecycleScope)

    private val viewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("recipeId")
            ?.let {
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

                val recipe = viewModel.recipe.value
                val loading = viewModel.loading.value

                val scaffoldState = rememberScaffoldState()

                Recipe_mvvm_composeTheme {

                    Scaffold(
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        }

                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            if (loading && recipe == null) {
                                Text(text = "Loading...")
                            } else {
                                recipe?.let {
                                    if (it.id == -1) {
                                        snackbarController.showSnackbar(
                                            scaffoldState = scaffoldState,
                                            message = "An error occurred",
                                            actionLabel = "OK"
                                        )
                                    } else {
                                        RecipeView(recipe = it)
                                    }
                                }
                            }
                            CircularIndeterminateProgressBar(isDisplayed = loading)
                            DefaultSnacbar(
                                snackbarHostState = scaffoldState.snackbarHostState,
                                onDismis = {
                                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                                },
                                modifier = Modifier.align(Alignment.BottomCenter)
                            )
                        }
                    }
                }
            }
        }
    }
}