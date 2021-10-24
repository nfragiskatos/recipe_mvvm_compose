package com.nfragiskatos.recipe_mvvm_compose.ui.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nfragiskatos.recipe_mvvm_compose.data.model.RecipeAPIResponse
import com.nfragiskatos.recipe_mvvm_compose.data.util.Resource
import com.nfragiskatos.recipe_mvvm_compose.domain.usecase.GetSearchedRecipesUseCase
import kotlinx.coroutines.launch

class RecipeListViewModel(
    private val app: Application,
    private val getSearchedRecipesUseCase: GetSearchedRecipesUseCase
) : AndroidViewModel(app) {

    val recipes: MutableLiveData<Resource<RecipeAPIResponse>> = MutableLiveData()

    fun getSearchedRecipes(
        page: Int,
        query: String
    ) = viewModelScope.launch {
        try {
            if (isNetworkAvailable(app)) {
                val response: Resource<RecipeAPIResponse> = getSearchedRecipesUseCase.execute(
                    page,
                    query
                )
                recipes.postValue(response)
            } else {
                recipes.postValue(Resource.Error("Internet Not Available"))
            }

        } catch (e: Exception) {
            Log.i(
                "MY_TAG",
                e.message.toString()
            )
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        if (context == null) return false

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