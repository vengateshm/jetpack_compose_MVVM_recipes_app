package com.vengateshm.recipesplaza.repository

import com.vengateshm.recipesplaza.model.ApiResult
import com.vengateshm.recipesplaza.network.MealApiClient
import com.vengateshm.recipesplaza.network.response.RecipeDetailResponse
import com.vengateshm.recipesplaza.network.response.RecipesResponse

class RecipesRepositoryImpl : RecipesRepository {
    override suspend fun getMealsByCategory(category: String): ApiResult<RecipesResponse> {
        return MealApiClient.safeApiCall {
            MealApiClient.RECIPE_API.getMealsByCategory(category)
        }
    }

    override suspend fun getMealDetail(mealId: String): ApiResult<RecipeDetailResponse> {
        return MealApiClient.safeApiCall {
            MealApiClient.RECIPE_API.getMealDetails(mealId)
        }
    }
}