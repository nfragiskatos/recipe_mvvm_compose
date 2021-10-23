package com.nfragiskatos.recipe_mvvm_compose

import android.os.Bundle
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
import androidx.navigation.findNavController
import com.nfragiskatos.recipe_mvvm_compose.ui.theme.Recipe_mvvm_composeTheme


@ExperimentalUnitApi
class RecipeListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                Recipe_mvvm_composeTheme() {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Recipe List",
                            style = TextStyle(
                                fontSize = TextUnit(17f, TextUnitType.Sp)
                            )
                        )
                        Spacer(modifier = Modifier.padding(10.dp))
                        Button(onClick = {
                            findNavController().navigate(R.id.action_recipeListFragment_to_recipeFragment)
                        }) {
                            Text(text = "TO RECIPE FRAGMENT")
                        }
                    }
                }
            }
        }
    }
}