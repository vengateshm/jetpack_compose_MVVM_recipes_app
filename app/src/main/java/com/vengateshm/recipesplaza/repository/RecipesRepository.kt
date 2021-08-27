package com.vengateshm.recipesplaza.repository

import com.vengateshm.recipesplaza.model.ApiResult
import com.vengateshm.recipesplaza.network.response.RecipeDetailResponse
import com.vengateshm.recipesplaza.network.response.RecipesResponse

interface RecipesRepository {
    suspend fun getMealsByCategory(category: String): ApiResult<RecipesResponse>
    suspend fun getMealDetail(mealId: String): ApiResult<RecipeDetailResponse>
}