package com.nfragiskatos.recipe_mvvm_compose.ui.fragments.recipe_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.nfragiskatos.recipe_mvvm_compose.ui.RecipeApp
import com.nfragiskatos.recipe_mvvm_compose.ui.components.*
import com.nfragiskatos.recipe_mvvm_compose.ui.fragments.recipe_list.RecipeListEvent.*
import com.nfragiskatos.recipe_mvvm_compose.ui.theme.Recipe_mvvm_composeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalUnitApi
@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    @Inject
    lateinit var application: RecipeApp

    private val viewModel: RecipeListViewModel by viewModels()

    private val snackbarController = SnackbarController(lifecycleScope)

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
                val scaffoldState = rememberScaffoldState()

                Recipe_mvvm_composeTheme(darkTheme = application.isDark.value) {
                    val resource = viewModel.resource.value
                    val recipes = viewModel.recipes.value
                    val query = viewModel.query.value
                    val selectedCategory = viewModel.selectedCategory.value
                    val chipPosition = viewModel.chipPosition
                    val loading = viewModel.loading.value

                    val page = viewModel.page.value

                    Scaffold(
                        topBar = {
                            SearchAppBar(
                                query = query,
                                onQueryChanged = viewModel::onQueryChange,
                                getSearchedRecipes = {
                                    if (viewModel.selectedCategory.value?.value == "Milk") {
                                        snackbarController.getScope().launch {
                                            snackbarController.showSnackbar(
                                                scaffoldState = scaffoldState,
                                                message = "Invalid category: MILK!",
                                                actionLabel = "Hide"
                                            )
                                        }
                                    } else {
                                        viewModel.onTriggerEvent(NewSearchEvent)
                                    }
                                },
                                chipPosition = chipPosition,
                                selectedCategory = selectedCategory,
                                onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                                onToggleTheme = application::toggleLightTheme
                            )
                        },
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        }

                    ) {
                        RecipeList(
                            loading = loading,
                            recipes = recipes,
                            page = page,
                            scaffoldState = scaffoldState,
                            navController = findNavController(),
                            onChangeRecipeScrollPosition = viewModel::onChangeRecipeScrollPosition,
                            onTriggerEvent = viewModel::onTriggerEvent
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DecoupledSnackbarDemo(snackbarHostState: SnackbarHostState) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val snackbar = createRef()
        SnackbarHost(
            modifier = Modifier.constrainAs(snackbar) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            hostState = snackbarHostState,
            snackbar = {
                Snackbar(
                    action = {
                        TextButton(onClick = { snackbarHostState.currentSnackbarData?.dismiss() }) {
                            Text(
                                text = snackbarHostState.currentSnackbarData?.actionLabel ?: "Hide",
                                style = MaterialTheme.typography.h5
                            )
                        }
                    }
                ) {
                    Text(
                        text = snackbarHostState.currentSnackbarData?.message
                            ?: "Default Snackbar Message"
                    )
                }
            }
        )
    }
}

@Composable
fun SnackbarDemo(
    isShowing: Boolean,
    onHideSnackbar: () -> Unit
) {
//    ConstraintLayout()
    if (isShowing) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val snackbar = createRef()
            Snackbar(modifier = Modifier.constrainAs(snackbar) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
                action = {
                    Text(
                        text = "Hide",
                        modifier = Modifier.clickable(onClick = onHideSnackbar),
                        style = MaterialTheme.typography.h5
                    )
                }

            ) {
                Text(text = "Hey it's a snackbar")
            }
        }
    }
}

@Composable
fun MyBottomBar(
    navController: NavController
) {

    BottomNavigation(elevation = 12.dp) {
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = ""
                )
            },
            selected = false,
            onClick = { })
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.ThumbUp,
                    contentDescription = ""
                )
            },
            selected = true,
            onClick = { })
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = ""
                )
            },
            selected = false,
            onClick = { })
    }
}

@Composable
fun MyDrawer() {
    Column() {
        Text(text = "item 1")
        Text(text = "item 2")
        Text(text = "item 3")
        Text(text = "item 4")
        Text(text = "item 5")
    }
}


@Composable
fun GradientDemo() {
    val colors = listOf<Color>(
        Color.Blue,
        Color.Red,
        Color.Blue
    )

    val brush = linearGradient(
        colors,
        start = Offset(
            200f,
            200f
        ),
        end = Offset(
            400f,
            400f
        )
    )

    Surface(shape = MaterialTheme.shapes.small) {
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = brush)
        )
    }
}