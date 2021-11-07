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
import androidx.lifecycle.viewModelScope
import com.nfragiskatos.recipe_mvvm_compose.domain.model.Recipe
import com.nfragiskatos.recipe_mvvm_compose.domain.usecase.GetRecipeByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val app: Application,
    private val getRecipeByIdUseCase: GetRecipeByIdUseCase
) : AndroidViewModel(app) {

    val recipeId = mutableStateOf(-1)
    val recipe: MutableState<Recipe?> = mutableStateOf(null)
    val loading = mutableStateOf(false)

    fun onRecipeIdChange(id: Int) {
        setRecipeId(id)
        viewModelScope.launch {
            getRecipeById(id)
        }
    }

    private fun setRecipeId(id: Int) {
        recipeId.value = id
    }

    private suspend fun getRecipeById(id: Int) {
        loading.value = true

        try {
            if (isNetworkAvailable(app)) {
                val resource = getRecipeByIdUseCase.execute(id)
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