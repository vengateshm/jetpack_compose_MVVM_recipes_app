package com.vengateshm.recipesplaza.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.vengateshm.recipesplaza.model.Meal

@JsonClass(generateAdapter = true)
data class MealsResponse(@Json(name = "meals") val meals: List<Meal>)