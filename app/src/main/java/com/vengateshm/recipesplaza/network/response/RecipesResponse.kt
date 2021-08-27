package com.vengateshm.recipesplaza.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.vengateshm.recipesplaza.model.Recipe

@JsonClass(generateAdapter = true)
data class RecipesResponse(@Json(name = "meals") val recipes: List<Recipe>)