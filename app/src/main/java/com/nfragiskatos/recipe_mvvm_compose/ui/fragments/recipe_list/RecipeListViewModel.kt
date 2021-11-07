package com.nfragiskatos.recipe_mvvm_compose.ui.fragments.recipe_list

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.nfragiskatos.recipe_mvvm_compose.data.util.Resource
import com.nfragiskatos.recipe_mvvm_compose.domain.model.Recipe
import com.nfragiskatos.recipe_mvvm_compose.domain.model.RecipeAPIResponse
import com.nfragiskatos.recipe_mvvm_compose.domain.usecase.GetRecipeByIdUseCase
import com.nfragiskatos.recipe_mvvm_compose.domain.usecase.GetSearchedRecipesUseCase
import com.nfragiskatos.recipe_mvvm_compose.ui.fragments.recipe_list.RecipeListEvent.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


const val PAGE_SIZE = 30

const val STATE_KEY_PAGE = "recipe.state.page.key"
const val STATE_KEY_QUERY = "recipe.state.query.key"
const val STATE_KEY_LIST_POSITION = "recipe.state.query.list_position"
const val STATE_KEY_SELECTED_CATEGORY = "recipe.state.query.selected_category"


@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val app: Application,
    private val getSearchedRecipesUseCase: GetSearchedRecipesUseCase,
    private val getRecipeByIdUseCase: GetRecipeByIdUseCase,
     private val savedStateHandle: SavedStateHandle
) : AndroidViewModel(app) {

    val resource: MutableState<Resource<RecipeAPIResponse>> = mutableStateOf(Resource.Loading())
    val recipes: MutableState<List<Recipe>> = mutableStateOf(ArrayList())
    val query = mutableStateOf("chicken")
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)
    var chipPosition: Int = 0
    val loading = mutableStateOf(false)
    val page = mutableStateOf(1)

    private var scrollPosition = 0

    init {
        savedStateHandle.get<Int>(STATE_KEY_PAGE)
            ?.let { p -> setPage(p) }
        savedStateHandle.get<String>(STATE_KEY_QUERY)
            ?.let { q -> setQuery(q) }
        savedStateHandle.get<Int>(STATE_KEY_LIST_POSITION)
            ?.let { p -> setScrollPosition(p) }
        savedStateHandle.get<FoodCategory>(STATE_KEY_SELECTED_CATEGORY)
            ?.let { c -> setSelectedCategory(c) }

        if (scrollPosition != 0) {
            onTriggerEvent(RestoreStateEvent)
        } else {
            onTriggerEvent(NewSearchEvent)
        }

    }

    fun onTriggerEvent(event: RecipeListEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    NewSearchEvent -> {
                        getSearchedRecipes()
                    }
                    NextPageEvent -> {
                        nextPage()
                    }
                    RestoreStateEvent -> {
                        restoreState()
                    }
                }

            } catch (e: Exception) {
                Log.e(
                    "MY_TAG",
                    "onTriggerEvent Exception: $e, ${e.cause}"
                )
            }
        }
    }

    private suspend fun restoreState() {
        loading.value = true
        val results: MutableList<Recipe> = mutableListOf()
        for (p in 1 .. page.value) {
            val result = getSearchedRecipesUseCase.execute(
                page.value,
                query.value
            )
            results.addAll(result.data?.results ?: ArrayList())
        }
        recipes.value = results
        loading.value = false
    }

    fun onQueryChange(query: String) {
        setQuery(query)
    }

    fun onSelectedCategoryChanged(
        category: FoodCategory,
        position: Int
    ) {
        setSelectedCategory(category)
        chipPosition = position
        onQueryChange(category.value)
    }

    private fun clearSelectedCategory() {
        setSelectedCategory(null)
    }

    private fun resetSearchState() {
        resource.value = Resource.Success(
            RecipeAPIResponse(
                count = 0,
                next = "",
                previous = "",
                results = emptyList()
            )
        )
        recipes.value = ArrayList()
        page.value = 1
        onChangeRecipeScrollPosition(0)

        if (selectedCategory.value?.value?.lowercase() != query.value.lowercase()) {
            clearSelectedCategory()
        }
    }

    private suspend fun getSearchedRecipes() {
        loading.value = true
        resetSearchState()
        try {
            if (isNetworkAvailable(app)) {
                val response: Resource<RecipeAPIResponse> = getSearchedRecipesUseCase.execute(
                    1,
                    query.value
                )
                resource.value = response
                recipes.value = response.data?.results ?: ArrayList()
            } else {
                resource.value = Resource.Error("Internet Not Available")
            }

        } catch (e: Exception) {
            Log.i(
                "MY_TAG",
                e.message.toString()
            )
            loading.value = false
        }
        loading.value = false
    }

    private suspend fun nextPage() {
        // prevent duplicate events due to recompose happening to quickly
        if ((scrollPosition + 1) >= (page.value * PAGE_SIZE)) {
            loading.value = true
            incrementPage()
            Log.d(
                "MY_TAG",
                "nextPage triggered: ${page.value}"
            )
            if (page.value > 1) {
                val result = getSearchedRecipesUseCase.execute(
                    page.value,
                    query.value
                )
                Log.d(
                    "MY_TAG",
                    "nextPage: $result"
                )
                appendRecipes(result.data?.results ?: ArrayList())
            }
            loading.value = false
        }
    }

    private fun incrementPage() {
        setPage(page.value + 1)
    }

    fun onChangeRecipeScrollPosition(position: Int) {
        setScrollPosition(position)
    }

    private fun appendRecipes(recipes: List<Recipe>) {
        val current = ArrayList(this.recipes.value)
        current.addAll(recipes)
        this.recipes.value = current
    }

    fun getRecipeById(id: Int) = viewModelScope.launch {
        try {
            if (isNetworkAvailable(app)) {
                val response = getRecipeByIdUseCase.execute(id)

            } else {

            }

        } catch (e: Exception) {
            Log.i(
                "MY_TAG",
                e.message.toString()
            )
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }

        } else {
            val activeNetwork = connectivityManager.activeNetworkInfo
            if (activeNetwork != null && activeNetwork.isConnected) {
                return true
            }
        }
        return false
    }

    private fun setScrollPosition(position: Int) {
        scrollPosition = position
        savedStateHandle.set(
            STATE_KEY_LIST_POSITION,
            position
        )
    }

    private fun setPage(page: Int) {
        this.page.value = page
        savedStateHandle.set(
            STATE_KEY_PAGE,
            page
        )
    }

    private fun setSelectedCategory(category: FoodCategory?) {
        selectedCategory.value = category
        savedStateHandle.set(
            STATE_KEY_SELECTED_CATEGORY,
            category
        )
    }

    private fun setQuery(query: String) {
        this.query.value = query
        savedStateHandle.set(
            STATE_KEY_QUERY,
            query
        )
    }
}