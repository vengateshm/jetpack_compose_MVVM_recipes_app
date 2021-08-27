package com.vengateshm.recipesplaza.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.vengateshm.recipesplaza.model.RecipeDetail
import com.vengateshm.recipesplaza.model.ScreenState
import com.vengateshm.recipesplaza.ui.viewmodels.RecipeViewModel

class MealDetailsFragment : Fragment() {

    private val recipeViewModel: RecipeViewModel by activityViewModels()

    @ExperimentalUnitApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnLifecycleDestroyed(
                viewLifecycleOwner))
            recipeViewModel.getMealDetailsById(recipeViewModel.selectedMealId)
            setContent {
                Surface {
                    val screenState = recipeViewModel.mealsDetail.observeAsState()
                    screenState.value?.let { mealDetailList ->
                        MealsDetailScreen(mealDetailList)
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance() = MealDetailsFragment()
    }
}

@ExperimentalUnitApi
@Composable
private fun MealsDetailScreen(screenState: ScreenState<List<RecipeDetail>>) {
    Box(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()
        .fillMaxHeight(),
        contentAlignment = Alignment.TopStart) {
        when (screenState) {
            is ScreenState.Loading -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is ScreenState.Success -> {
                Column {
                    val mealDetail = if (screenState.data.isEmpty()) null else screenState.data[0]
                    if (mealDetail != null) {
                        Text(text = mealDetail.strMeal!!,
                            style = TextStyle(
                                color = Color(0xFF333333),
                                fontSize = TextUnit(18f, TextUnitType.Sp)))
                        MealsInstructions(instructions = (mealDetail.strInstructions!!))
                    }
                }
            }
            is ScreenState.Error -> {

            }
        }
    }
}

@ExperimentalUnitApi
@Composable
fun MealsInstructions(instructions: String) {
    Column {
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Instructions")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = instructions,
            style = TextStyle(color = Color(red = 55, green = 55, blue = 55),
                fontSize = TextUnit(14f, TextUnitType.Sp)))
    }
}