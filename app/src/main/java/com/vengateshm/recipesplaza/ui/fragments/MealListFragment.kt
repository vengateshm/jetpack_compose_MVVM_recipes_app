package com.vengateshm.recipesplaza.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.vengateshm.recipesplaza.R
import com.vengateshm.recipesplaza.model.Recipe
import com.vengateshm.recipesplaza.model.ScreenState
import com.vengateshm.recipesplaza.ui.composeViews.loadPicture
import com.vengateshm.recipesplaza.ui.viewmodels.RecipeViewModel

class MealListFragment : Fragment() {
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
            recipeViewModel.getMealsByCategory(recipeViewModel.selectedMealCategory)
            setContent {
                val screenState = recipeViewModel.recipeList.observeAsState()
                screenState.value?.let {
                    MealListScreen(screenState = it) { meal ->
                        if (meal.idMeal.isEmpty())
                            return@MealListScreen
                        recipeViewModel.setSelectedMealId(meal.idMeal)
                        with(requireActivity()) {
                            this.supportFragmentManager.beginTransaction()
                                .add(R.id.mealLandingFragmentContainer,
                                    MealDetailsFragment.newInstance())
                                .addToBackStack(null)
                                .commitAllowingStateLoss()
                        }
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance() = MealListFragment()
    }
}

@ExperimentalUnitApi
@Composable
fun MealListScreen(
    screenState: ScreenState<List<Recipe>>,
    onMealClicked: (Recipe) -> Unit,
) {
    Box(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()
        .fillMaxHeight(),
        contentAlignment = Alignment.Center) {
        when (screenState) {
            is ScreenState.Loading -> {
                CircularProgressIndicator()
            }
            is ScreenState.Success -> {
                MealsList(mealsList = screenState.data, onMealClicked)
            }
            is ScreenState.Error -> {

            }
        }
    }
}

@ExperimentalUnitApi
@Composable
fun MealsList(mealsList: List<Recipe>, onMealClicked: (Recipe) -> Unit) {
    LazyColumn {
        items(mealsList) { meal ->
            MealItem(recipe = meal, onMealClicked)
        }
    }
}

@ExperimentalUnitApi
@Composable
fun MealItem(recipe: Recipe, onMealClicked: (Recipe) -> Unit) {
    Card(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()) {
        Row(modifier = Modifier.clickable(onClick = {
            onMealClicked(recipe)
        })) {
            MealsThumbnail(thumbnailUrl = recipe.strMealThumb)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                modifier = Modifier.padding(8.dp),
                text = recipe.strMeal,
                style = TextStyle(color = Color(0xFF333333),
                    fontSize = TextUnit(20f, TextUnitType.Sp)))
        }
    }
}

@Composable
fun MealsThumbnail(thumbnailUrl: String) {
    loadPicture(url = thumbnailUrl, defaultImage = R.drawable.thumbnail_bg).value?.apply {
        Image(bitmap = this.asImageBitmap(), contentDescription = "",
            modifier = Modifier
                .width(100.dp)
                .height(80.dp),
            contentScale = ContentScale.Crop)
    }
}