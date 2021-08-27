package com.vengateshm.recipesplaza.network

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.vengateshm.recipesplaza.model.ApiResult
import com.vengateshm.recipesplaza.utils.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object MealApiClient {
    private val httpClient = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor()
        .also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }).build()
    private val moshi = Moshi.Builder()
        .add(NullToStringAdapter)
        .addLast(KotlinJsonAdapterFactory()).build()
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
    val RECIPE_API: RecipeApi = retrofit.create(RecipeApi::class.java)

    suspend fun <T> safeApiCall(apiCall: suspend () -> T): ApiResult<T> {
        return try {
            ApiResult.Success(apiCall.invoke())
        } catch (e: Exception) {
            ApiResult.Error(e)
        }
    }
}

object NullToStringAdapter {
    @FromJson
    fun fromJson(reader: JsonReader): String {
        if (reader.peek() != JsonReader.Token.NULL) {
            return reader.nextString()
        }
        reader.nextNull<Unit>()
        return ""
    }
}