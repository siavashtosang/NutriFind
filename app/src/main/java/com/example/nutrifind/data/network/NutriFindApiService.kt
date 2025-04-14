package com.example.nutrifind.data.network

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
import javax.inject.Singleton

object Constant {
    const val BASE_URL = "https://api.edamam.com/"
}

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .readTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
            .connectTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
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
        @Query("type") type: String,
        @Query("q") searchFood: String,
        @Query("app_id") app_id: String,
        @Query("app_key") app_key: String
    ): ApiEdamam
}

