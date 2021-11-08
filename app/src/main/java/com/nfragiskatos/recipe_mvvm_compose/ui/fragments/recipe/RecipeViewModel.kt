package com.nfragiskatos.recipe_mvvm_compose.ui.fragments.recipe

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
import com.nfragiskatos.recipe_mvvm_compose.domain.model.Recipe
import com.nfragiskatos.recipe_mvvm_compose.domain.usecase.GetRecipeByIdUseCase
import com.nfragiskatos.recipe_mvvm_compose.ui.fragments.recipe.RecipeEvent.GetRecipeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val STATE_KEY_RECIPE_ID = "recipe.state.recipe.id"

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val app: Application,
    private val getRecipeByIdUseCase: GetRecipeByIdUseCase,
    private val savedStateHandle: SavedStateHandle
) : AndroidViewModel(app) {

    private val recipeId = mutableStateOf(-1)
    val recipe: MutableState<Recipe?> = mutableStateOf(null)
    val loading = mutableStateOf(false)

    init {
        savedStateHandle.get<Int>(STATE_KEY_RECIPE_ID)
            ?.let { id ->
                setRecipeId(id)
                onTriggerEvent(GetRecipeEvent(id))
            }
    }

    fun onTriggerEvent(event: RecipeEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is GetRecipeEvent -> {
                        setRecipeId(event.id)
                        getRecipeById()
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

    private fun setRecipeId(id: Int) {
        recipeId.value = id
        savedStateHandle.set(
            STATE_KEY_RECIPE_ID,
            id
        )
    }

    private suspend fun getRecipeById() {
        loading.value = true

        try {
            if (isNetworkAvailable(app) && recipeId.value !== -1) {
                val resource = getRecipeByIdUseCase.execute(recipeId.value)
                this.recipe.value = resource.data
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