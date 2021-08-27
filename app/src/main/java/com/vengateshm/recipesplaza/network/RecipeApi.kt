package com.vengateshm.recipesplaza.network

import com.vengateshm.recipesplaza.network.response.RecipeCategoriesResponse
import com.vengateshm.recipesplaza.network.response.RecipeDetailResponse
import com.vengateshm.recipesplaza.network.response.RecipesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {
    // Return Call<T> to convert retrofit callback to coroutines using suspendCancellableCoroutine
    @GET("categories.php")
    fun getAllCategories(): Call<RecipeCategoriesResponse>

    // Using retrofit in-built support for suspend function
    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): RecipesResponse

    @GET("lookup.php")
    suspend fun getMealDetails(@Query("i") mealId: String): RecipeDetailResponse
}