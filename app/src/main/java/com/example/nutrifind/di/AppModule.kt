package com.example.nutrifind.di

import com.example.nutrifind.BuildConfig
import com.example.nutrifind.data.remote.network.EdamamApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

object NetworkConfig {
    const val READ_TIMEOUT_SECONDS = 60L
    const val CONNECT_TIMEOUT_SECONDS = 60L
}

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {


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
    fun edamamAPiService(retrofit: Retrofit): EdamamApi {
        return retrofit.create(EdamamApi::class.java)
    }
}