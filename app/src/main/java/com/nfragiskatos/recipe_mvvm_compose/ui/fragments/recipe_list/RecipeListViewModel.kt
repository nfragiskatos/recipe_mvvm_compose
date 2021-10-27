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
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nfragiskatos.recipe_mvvm_compose.data.util.Resource
import com.nfragiskatos.recipe_mvvm_compose.domain.model.Recipe
import com.nfragiskatos.recipe_mvvm_compose.domain.model.RecipeAPIResponse
import com.nfragiskatos.recipe_mvvm_compose.domain.usecase.GetRecipeByIdUseCase
import com.nfragiskatos.recipe_mvvm_compose.domain.usecase.GetSearchedRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val app: Application,
    private val getSearchedRecipesUseCase: GetSearchedRecipesUseCase,
    private val getRecipeByIdUseCase: GetRecipeByIdUseCase
) : AndroidViewModel(app) {
    init {
        Log.i("MY_TAG", "RecipeListViewModel Dep - getSearchedRecipesUseCase = $getSearchedRecipesUseCase")
        Log.i("MY_TAG", "RecipeListViewModel Dep - getRecipeByIdUseCase = $getRecipeByIdUseCase")
    }

    val resource: MutableState<Resource<RecipeAPIResponse>> = mutableStateOf(Resource.Loading())
    val query = mutableStateOf("chicken")
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)
    var chipPosition: Int = 0

    init {
        getSearchedRecipes(1)
    }

    fun onQueryChange(query: String) {
        this.query.value = query
    }

    fun onSelectedCategoryChanged(category: FoodCategory, position: Int) {
        selectedCategory.value = category
        chipPosition = position
        onQueryChange(category.value)
    }

    fun getSearchedRecipes(
        page: Int,
    ) = viewModelScope.launch {
        try {
            if (isNetworkAvailable(app)) {
                val response: Resource<RecipeAPIResponse> = getSearchedRecipesUseCase.execute(
                    page,
                    query.value
                )
                resource.value = response
            } else {
                resource.value = Resource.Error("Internet Not Available")
            }

        } catch (e: Exception) {
            Log.i(
                "MY_TAG",
                e.message.toString()
            )
        }
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
}