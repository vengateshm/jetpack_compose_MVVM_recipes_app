package com.vengateshm.recipesplaza.repository

import com.vengateshm.recipesplaza.model.ApiResult
import com.vengateshm.recipesplaza.network.response.CategoriesResponse

interface CategoryRepository {
    suspend fun getAllCategories(): ApiResult<CategoriesResponse>
}