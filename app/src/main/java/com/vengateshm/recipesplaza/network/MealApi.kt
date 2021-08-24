package com.vengateshm.recipesplaza.network

import com.vengateshm.recipesplaza.network.response.CategoriesResponse
import com.vengateshm.recipesplaza.network.response.MealDetailResponse
import com.vengateshm.recipesplaza.network.response.MealsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("categories.php")
    fun getAllCategories(): Call<CategoriesResponse>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") category: String): Call<MealsResponse>

    @GET("lookup.php")
    fun getMealDetails(@Query("i") mealId: String): Call<MealDetailResponse>
}