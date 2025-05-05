package com.example.nutrifind.data.remote.network

import com.example.nutrifind.BuildConfig
import com.example.nutrifind.data.remote.model.ApiEdamam
import retrofit2.http.GET
import retrofit2.http.Query


interface EdamamApi {

    @GET("api/recipes/v2")
    suspend fun getFoods(
        @Query("type") type: String = "public",
        @Query("q") searchFood: String,
        @Query("app_id") appId: String = BuildConfig.APP_ID,
        @Query("app_key") appKey: String = BuildConfig.APP_KEY,
        @Query("diet") dietFilter: String?,
        @Query("dishType") dishTypeFilter: String?,
        @Query("mealType") mealTypeFilter: String?,
        @Query("cuisineType") cuisineTypeFilter: String?
    ): ApiEdamam
}


