package com.example.nutrifind.data.network

import com.example.nutrifind.BuildConfig
import com.example.nutrifind.data.model.ApiEdamam
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

object NetworkConfig {
    const val READ_TIMEOUT_SECONDS = 60L
    const val CONNECT_TIMEOUT_SECONDS = 60L
}

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .readTimeout(NetworkConfig.READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(NetworkConfig.CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun nutriFindAPiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}

interface ApiService {

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


