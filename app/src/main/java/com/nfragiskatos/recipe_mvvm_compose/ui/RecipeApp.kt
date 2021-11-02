package com.nfragiskatos.recipe_mvvm_compose.ui

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RecipeApp : Application() {

    // should be saved in data store in real app
    val isDark = mutableStateOf(false)

    fun toggleLightTheme() {
        isDark.value = !isDark.value
    }
}