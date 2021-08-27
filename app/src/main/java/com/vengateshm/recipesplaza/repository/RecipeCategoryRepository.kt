package com.vengateshm.recipesplaza.repository

import com.vengateshm.recipesplaza.model.ApiResult
import com.vengateshm.recipesplaza.network.response.RecipeCategoriesResponse

interface RecipeCategoryRepository {
    suspend fun getAllCategories(): ApiResult<RecipeCategoriesResponse>
}