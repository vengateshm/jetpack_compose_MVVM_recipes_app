package com.vengateshm.recipesplaza.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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
    private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
    val mealApi: MealApi = retrofit.create(MealApi::class.java)
}