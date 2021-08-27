package com.vengateshm.recipesplaza.repository

import com.vengateshm.recipesplaza.model.ApiResult
import com.vengateshm.recipesplaza.network.MealApiClient
import com.vengateshm.recipesplaza.network.response.RecipeCategoriesResponse
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class RecipeCategoryRepositoryImpl : RecipeCategoryRepository {
    override suspend fun getAllCategories(): ApiResult<RecipeCategoriesResponse> =
        suspendCancellableCoroutine { cancellableContinuation ->
            MealApiClient.RECIPE_API.getAllCategories()
                .enqueue(object : Callback<RecipeCategoriesResponse> {
                    override fun onResponse(
                        call: Call<RecipeCategoriesResponse>,
                        response: Response<RecipeCategoriesResponse>,
                    ) {
                        if (response.isSuccessful && response.body() != null)
                            cancellableContinuation.resume(ApiResult.Success(response.body()!!))
                        else
                            cancellableContinuation.resumeWithException(Exception("Failure response."))
                    }

                    override fun onFailure(call: Call<RecipeCategoriesResponse>, t: Throwable) {
                        cancellableContinuation.resumeWithException(t)
                    }
                })
        }
}