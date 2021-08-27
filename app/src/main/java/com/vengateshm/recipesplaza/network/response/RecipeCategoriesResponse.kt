package com.vengateshm.recipesplaza.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.vengateshm.recipesplaza.model.RecipeCategory

@JsonClass(generateAdapter = true) // Code gen instead reflection
data class RecipeCategoriesResponse(@Json(name = "categories") val recipeCategories: List<RecipeCategory>)