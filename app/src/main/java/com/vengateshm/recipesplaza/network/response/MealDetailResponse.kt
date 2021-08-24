package com.vengateshm.recipesplaza.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.vengateshm.recipesplaza.model.MealDetail

@JsonClass(generateAdapter = true)
data class MealDetailResponse(@Json(name = "meals") val mealDetails: List<MealDetail>)