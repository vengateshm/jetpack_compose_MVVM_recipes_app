package com.vengateshm.recipesplaza.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.vengateshm.recipesplaza.R
import com.vengateshm.recipesplaza.ui.fragments.MealListFragment
import com.vengateshm.recipesplaza.ui.viewmodels.RecipeViewModel
import com.vengateshm.recipesplaza.utils.KEY_RECIPE_CATEGORY_ID

class RecipesActivity : AppCompatActivity() {

    private val recipeViewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_landing)

        intent?.also {
            val category = it.getStringExtra(KEY_RECIPE_CATEGORY_ID)
            if (category.isNullOrEmpty()) return@also

            recipeViewModel.setSelectedCategory(category)
            supportFragmentManager.beginTransaction()
                .replace(R.id.mealLandingFragmentContainer, MealListFragment.newInstance())
                .commitAllowingStateLoss()
        }
    }
}