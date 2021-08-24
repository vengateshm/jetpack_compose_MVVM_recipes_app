package com.vengateshm.recipesplaza.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.vengateshm.recipesplaza.model.Category

@JsonClass(generateAdapter = true) // Code gen instead reflection
data class CategoriesResponse(@Json(name = "categories") val categories: List<Category>)