package com.vengateshm.recipesplaza.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.vengateshm.recipesplaza.model.RecipeDetail

@JsonClass(generateAdapter = true)
data class RecipeDetailResponse(@Json(name = "meals") val recipeDetails: List<RecipeDetail>)