package com.vengateshm.recipesplaza.repository

import com.vengateshm.recipesplaza.model.ApiResult
import com.vengateshm.recipesplaza.network.MealApiClient
import com.vengateshm.recipesplaza.network.response.CategoriesResponse
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class CategoryRepositoryImpl : CategoryRepository {
    override suspend fun getAllCategories(): ApiResult<CategoriesResponse> =
        suspendCancellableCoroutine { cancellableContinuation ->
            MealApiClient.mealApi.getAllCategories()
                .enqueue(object : Callback<CategoriesResponse> {
                    override fun onResponse(
                        call: Call<CategoriesResponse>,
                        response: Response<CategoriesResponse>,
                    ) {
                        if (response.isSuccessful && response.body() != null)
                            cancellableContinuation.resume(ApiResult.Success(response.body()!!))
                        else
                            cancellableContinuation.resumeWithException(Exception("Failure response."))
                    }

                    override fun onFailure(call: Call<CategoriesResponse>, t: Throwable) {
                        cancellableContinuation.resumeWithException(t)
                    }
                })
        }
}